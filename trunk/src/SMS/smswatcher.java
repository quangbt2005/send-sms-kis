package SMS;

import java.sql.*;
import java.util.*;
import java.io.*;

import DataAccess.ConnectDB;
import Utilities.Logger;


public class smswatcher extends Thread{
	int waiting_count = 0;
	int loop_count = 15;
	int loop_interval = 1000;
	
	public smssender sender;
	public smswatcher(smssender _sender){
		this.sender = _sender;
		this.readConfig();
		System.out.println("Watcher interval: " + this.loop_interval);
		new Thread(this).start();
	}
	
	public void run(){
		while(true){
		// while(loop_count > 0){
			System.out.println("NEW WATCHER LOOP");
			try{
				ConnectDB connection = new ConnectDB("172.25.2.110", "sms", "smskis", "VIETNAM");
				if(connection.TestConnection()){
					try{
						String query = "SELECT COUNT(1) AS waiting_count FROM VN.XCS01M00 WHERE send_st='9' OR (send_st='0' AND re_try_cnt>0)";
						ResultSet result = connection.excuteQuery(query);
						
						if(result != null && result.next()){
							waiting_count = result.getInt("waiting_count");
						}
						if(waiting_count > 0){
							query = "SELECT sms_seq_no,recv_phone_no,sms_msg FROM VN.XCS01M00 WHERE (send_st='9' OR (send_st='0' AND re_try_cnt>0)) AND rownum <= 5";
							result = connection.excuteQuery(query);
							while(result != null && result.next()){
								String sms_seq_no = result.getString("sms_seq_no");
								String recv_phone_no = result.getString("recv_phone_no");
								String sms_msg = result.getString("sms_msg");
								if(!this.sender.IsAdreadyInQueue(Integer.parseInt(sms_seq_no))){
									Logger.WriteLog(Logger.SMS_LOG, String.format("%s\t%s\t%s", sms_seq_no, recv_phone_no, sms_msg), "sms_watch_log.txt");
									this.sender.AddMsg(new smsmessage(sms_seq_no, recv_phone_no, sms_msg));
								}
							}
							this.sender.Notify();
						}
						connection.Close();
					} catch (Exception e){
						Logger.WriteLog(Logger.SMS_LOG, String.format("SMSWatch exception: %s", e.getMessage()), "error_log.txt");
					}
				}
				System.out.println("END WATCHER LOOP");
				Thread.sleep(this.loop_interval);
			} catch (Exception e){
				Logger.WriteLog(Logger.COMMON_LOG, String.format("SMSWatch exception: %s", e.getMessage()), "error_log.txt");
			}
			// System.out.println("WATCHER LOOP: " + loop_count);
		}
		//System.out.println("WATCHER STOPED");
		//Logger.WriteLog(Logger.SMS_LOG, "WATCHER STOPED", "error_log.txt");
	}
	
	private void readConfig(){
		try{
			Properties p = new Properties();
			p.load(new FileInputStream("configure.props"));
			this.loop_interval = Integer.parseInt(p.getProperty("WatcherLoopInterval"));
			System.out.println(p.getProperty("WatcherLoopInterval"));
		}
		catch (Exception e) {
			System.out.println("Read config exception !");
			System.out.println(e.getMessage());
			try{
			System.out.println(new File(".").getCanonicalPath());
			} catch (Exception fe){}
			System.out.println(System.getProperty("user.dir"));
			this.loop_interval = 1000;
		}
	}
}
