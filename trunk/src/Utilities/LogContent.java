package Utilities;

import java.io.*;
import java.util.*;
import java.text.*;

public class LogContent {
	public int type;
	public String path;
	public String filename;
	public String content;
	
	LogContent(int m_type, String m_filename, String m_path, String m_content){
		this.type = m_type;
		this.filename = m_filename;
		this.path = m_path;
		this.content = m_content;
		
		if(this.type > Logger.MAX_LOG_ID || this.type < 0) this.type = 0;
	}
	
	public void WriteLog(){
		try{
			int int_month = Calendar.getInstance().get(Calendar.MONTH)+1;
			int int_date  = Calendar.getInstance().get(Calendar.DATE);
			
			String year  = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
			String month = Integer.toString(Calendar.getInstance().get(Calendar.MONTH)+1);
			String date  = Integer.toString(Calendar.getInstance().get(Calendar.DATE));
			
			if(int_month < 10) month = "0" + month;
			if(int_date < 10) date = "0" + date;
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        String time = dateFormat.format(new Date());
			
			String path = Logger.DEFAULT_LOGPATHS[this.type];
			String filename = Logger.DEFAULT_LOGFILENAME[this.type];
			
			if(this.path != null && !this.path.isEmpty()){
				path = this.path;
			}
			
			if(this.filename != null && !this.filename.isEmpty()){
				filename = this.filename;
			}
			
			path = path + year + "/" + month + "/";
			filename = year + month + date + "_" + filename;
			
			File directory = new File(path);
			if(!directory.exists()){
				directory.mkdirs();
			}
			
			if(directory.exists()){
				Writer logAppendWriter = new BufferedWriter(new FileWriter(path + filename, true));
				logAppendWriter.write("--> " + time + " " + this.content + "\n");
				logAppendWriter.flush();
				logAppendWriter.close();
				
				// System.out.println("Writelog to path:" + path + " filename:" + filename);
			} else {
				System.out.println("Log directory not found or can't create log directory !");
			}
		} catch(Exception e){
			System.out.println("Write log error");
			System.out.println(e.getMessage());
		}
	}
}
