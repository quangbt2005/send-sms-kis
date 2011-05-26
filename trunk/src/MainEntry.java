import SMS.*;
import Utilities.Logger;

// import DataAccess.ConnectDB;

public class MainEntry {
	public static void main(String[] args) {
		try{
			Logger.StartThread();
			// Logger.WriteLog(0, "HELLO JAVORA");
			
			smssendresultupdator updator = new smssendresultupdator();
			Thread.sleep(1000);
			/*updator.AddMsg(new smsmessage("12594","84932527869","TESTTTTTT"));
			updator.Notify();*/
			
			smssender sender = new smssender(updator);
			Thread.sleep(1000);
			/*sender.AddMsg(new smsmessage("12594","84932527869","TESTTTTTT"));
			sender.Notify();*/
			
			smswatcher watcher = new smswatcher(sender);
						
			watcher.join();
			sender.join();
			
			//Thread.sleep(1000);
			//Logger.StopThread();
			//System.out.println("Main da chay xong");
			
//			ConnectDB connection = new ConnectDB("172.25.2.110", "sms", "smskis", "VIETNAM");
//			//ConnectDB connection = new ConnectDB("172.25.2.111", "vnmon", "vnmon", "VIETNAM");
//			if(connection.TestConnection()){
//				String query = String.format("UPDATE vn.xcs01m00 SET send_st=1 WHERE sms_seq_no=8051");
//				//String query = String.format("UPDATE table1 SET column1='HELLO WORLD'");
//				connection.executeUpdate(query);
//				connection.Close();
//			}
		} catch (Exception e){
			System.out.println("MAIN FAILED !");
			System.out.println(e.getMessage());
		}
	}
}
