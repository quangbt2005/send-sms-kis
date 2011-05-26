package Utilities;

public class Logger {
	public static final int COMMON_LOG = 0;
	public static final int DATABASE_LOG = 1;
	public static final int SMS_LOG = 2;
	
	public static final int MAX_LOG_ID = 2;
	
	static final String[] DEFAULT_LOGPATHS = {
												"/home/java/logs/common/",
												"/home/java/logs/database/",
												"/home/java/logs/sms/"
											 };
	static final String[] DEFAULT_LOGFILENAME = {
													"common.log",
													"database.log",
													"sms.log"
												};
	public static boolean running = true;
	private static LoggerThread m_thread;
	
	private Logger(){}
	
	public static void SetLoggerThread(LoggerThread thread){
		Logger.m_thread = thread;
	}
	public static LoggerThread GetThread(){
		return Logger.m_thread;
	}
	public static void StartThread(){
		Logger.running = true;
		Logger.m_thread = new LoggerThread();
	}
	public static boolean WriteLog(int type, String content, Object... p){
		if(Logger.m_thread != null){
			int pl = p.length;
			String path = "";
			String filename = "";
			
			if(pl>1){
				try{
					path = (String)p[1];
				} catch (Exception e){ path = ""; }
			}
			
			if(pl>0){
				try{
					filename = (String)p[0];
				} catch (Exception e){ filename = ""; }
			}
			
			Logger.m_thread.Add(new LogContent(type, filename, path, content));
			Logger.m_thread.notifyMe();
			
			return true;
		} else {
			System.out.println("Logger thread is null");
			return false;
		}
	}
	
	public static void StopThread(){
		Logger.running = false;
		Logger.m_thread.notifyMe();
	}
}
