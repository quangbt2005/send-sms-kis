package Utilities;

public class TestWriteLog implements Runnable {
	int count = 5;
	
	TestWriteLog(){};

	public void run(){
		try{
			while(count > 0){
				//MainEntry.logger.Add(new LogContent(Logger.COMMON_LOG, "", "", "Test ghi log lan " + String.valueOf(count)));
				//MainEntry.logger.notifyMe();
				Logger.WriteLog(Logger.COMMON_LOG, "HELLO JAVORA");
				System.out.println("TestWriteLog run lan " + String.valueOf(count));
				count--;
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.out.println("TestWriteLog exception");
			System.out.println(e.getMessage());
		}
	}
}
