package SMS;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import DataAccess.ConnectDB;
import Utilities.Logger;

public class smssendresultupdator extends Thread{
	private ArrayList<smsmessage> queue;
	
	public smssendresultupdator(){
		this.queue = new ArrayList<smsmessage>();
		new Thread(this).start();
	}
	
	public void run(){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		while(true){
			try{
				synchronized(this){				
					ConnectDB connection = new ConnectDB("172.25.2.110", "sms", "smskis", "VIETNAM");
					while(!this.queue.isEmpty()){
						smsmessage msg = this.queue.remove(0);
						try{
							if(connection.TestConnection()){
						        String time = dateFormat.format(new Date());
								
								String query = String.format("UPDATE vn.xcs01m00 SET send_st='1',sent_dt='%s' WHERE sms_seq_no=%s", time, msg.sms_seq_no);
								if(!msg.send_result){
									query = String.format("UPDATE vn.xcs01m00 SET send_st='0',sent_dt='%s',re_try_cnt=re_try_cnt-1 WHERE sms_seq_no=%s", time, msg.sms_seq_no);
								}
								Logger.WriteLog(Logger.SMS_LOG, String.format("%s\t%s\t%s", msg.sms_seq_no, msg.recv_phone_no, msg.sms_msg), "sms_update_result_log.txt");
								// System.out.println(query);
								connection.executeUpdate(query);
								// System.out.println(query);
								connection.Close();
							}
						} catch (Exception e){
							Logger.WriteLog(Logger.SMS_LOG, String.format("SMSUpdateResult exception: %s\t%s\t%s",msg.sms_seq_no, String.valueOf(msg.send_result), e.getMessage()), "error_log.txt");
						}
					}
					//System.out.println("Updator suspended");
					wait();
				}
			} catch(Exception e){
				Logger.WriteLog(Logger.COMMON_LOG, String.format("SMSUpdateResult Thread exception: %s", e.getMessage()), "error_log.txt");
			}
		}
	}
	
	synchronized public void Notify(){
		this.notify();
	}
	
	synchronized public void AddMsg(smsmessage msg){
		this.queue.add(msg);
	}
}
