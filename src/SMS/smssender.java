package SMS;

import java.util.*;

import Utilities.Logger;

public class smssender extends Thread{
	private ArrayList<smsmessage> sms_queue;
	private ArrayList<Integer> sms_seq_queue;
	private smssendresultupdator updator;
	
	public smssender(smssendresultupdator _updator){
		this.updator = _updator;
		this.sms_queue = new ArrayList<smsmessage>();
		this.sms_seq_queue = new ArrayList<Integer>();
		new Thread(this).start();
	}
	public void run(){
		while(true){
			synchronized(this){
				//int position = 0;
				try{
					if(!this.sms_queue.isEmpty()) System.out.println("Sender started !");
					while(!this.sms_queue.isEmpty()){
						//position = 0;
						smsmessage msg = this.sms_queue.remove(0);
						//position++;
						if(msg != null){
							Logger.WriteLog(Logger.SMS_LOG, String.format("%s\t%s\t%s", msg.sms_seq_no, msg.recv_phone_no, msg.sms_msg), "sms_sender_log.txt");
							msg.Send();
							//position++;
							this.updator.AddMsg(msg);
							//position++;
							this.updator.Notify();
							//position++;
							this.sms_seq_queue.remove(0);
						}
					}
					System.out.println("Sender suspended !");
					wait();
				} catch(Exception e){
					//System.out.println("Sender Exception" + position);
					Logger.WriteLog(Logger.SMS_LOG, String.format("SMSSender exception: %s", e.getMessage()), "error_log.txt");
				}
			}
		}
	}
	
	synchronized public void Notify(){
		this.notify();
	}
	
	synchronized public void AddMsg(smsmessage msg){
		this.sms_queue.add(msg);
		this.sms_seq_queue.add(Integer.parseInt(msg.sms_seq_no));
	}
	
	synchronized public boolean IsAdreadyInQueue(int seq){
		return this.sms_seq_queue.contains(seq);
	}
}
