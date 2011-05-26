package Utilities;
import java.util.*;


public class LoggerThread extends Thread {
	public ArrayList<LogContent> msg_queue = new ArrayList<LogContent>();
		
	LoggerThread(){
		new Thread(this).start();
	}
	
	public void run(){
		try{
			// System.out.println("Logger start");
			synchronized (this) {
				while(Logger.running){
					while(!this.msg_queue.isEmpty()){
						// System.out.println("Logger run");
						LogContent msg = this.msg_queue.remove(0);
						msg.WriteLog();
					}
					// System.out.println("Logger wait");
					wait();
				}
			}
		} catch (Exception e){
			System.out.println("LoggerThread exception");
			System.out.println(e.getMessage());
		}
	}
	
	public synchronized void Add(LogContent log){
		this.msg_queue.add(log);
	}
	
	public synchronized void notifyMe(){
		// System.out.println("Logger notified");
		notify();
	}
}
