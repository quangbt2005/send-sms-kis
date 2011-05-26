package SMS;

import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import Utilities.Logger;

public class smsmessage {
	public String sms_seq_no;
	public String recv_phone_no;
	public String sms_msg;
	public boolean send_result;
	
	public smsmessage(String _sms_seq_no, String _recv_phone_no, String _sms_msg){
		this.sms_seq_no = _sms_seq_no;
		this.recv_phone_no = _recv_phone_no;
		this.sms_msg = _sms_msg;
	}
	
	public boolean Send(){
		try{
			String content = ReplaceVietnameseCharacters(this.sms_msg);
			content = ReplaceSpaceInContent(content);
			
			String phone_number = ReplaceSpecialCharInPhoneNumber(this.recv_phone_no);
			
			String url = String.format("http://172.25.2.6:8888/?PhoneNumber=%s&Text=%s", "0932527869", content);
			URLConnection conn = new URL(url).openConnection();			    
			
		    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    //String s = br.readLine();
		    /*while (s != null) {
		    	if(s.toLowerCase().indexOf("") >= 0){
		    		br.close();
		    		send_result = true;
		    		return true;
		    	}
		    	s = br.readLine();
		    }*/
		    br.close();
		    Logger.WriteLog(Logger.SMS_LOG, String.format("%s\t%s\t%s", this.sms_seq_no, phone_number, content), "sms_send_log.txt");
		    //Thread.sleep(2000);
		    send_result = true;
		    return true;
		} catch (Exception e){
			Logger.WriteLog(Logger.SMS_LOG, String.format("SMSMessage Send exception: %s", e.getMessage()), "error_log.txt");
			send_result = false;
			return false;
		}
	}
	
	private String ReplaceSpecialCharInPhoneNumber(String number){
		number = number.replace(" ", "");
		number = number.replace("-", "");
		number = number.replace(".", "");
		
		return number;
	}
	private String ReplaceSpaceInContent(String content){
		content = content.replace(" ", "+");
		 return content;
	}
	private String ReplaceVietnameseCharacters(String content)
    {
        content = content.replace('\340', 'a');
        content = content.replace('\341', 'a');
        content = content.replace('\u1EA3', 'a');
        content = content.replace('\343', 'a');
        content = content.replace('\u1EA1', 'a');
        content = content.replace('\u0103', 'a');
        content = content.replace('\u1EB1', 'a');
        content = content.replace('\u1EAF', 'a');
        content = content.replace('\u1EB3', 'a');
        content = content.replace('\u1EB5', 'a');
        content = content.replace('\u1EB7', 'a');
        content = content.replace('\342', 'a');
        content = content.replace('\u1EA7', 'a');
        content = content.replace('\u1EA5', 'a');
        content = content.replace('\u1EA9', 'a');
        content = content.replace('\u1EAB', 'a');
        content = content.replace('\u1EAD', 'a');
        content = content.replace('\300', 'a');
        content = content.replace('\301', 'a');
        content = content.replace('\u1EA2', 'a');
        content = content.replace('\303', 'a');
        content = content.replace('\u1EA0', 'a');
        content = content.replace('\u0102', 'a');
        content = content.replace('\u1EB0', 'a');
        content = content.replace('\u1EAE', 'a');
        content = content.replace('\u1EB2', 'a');
        content = content.replace('\u1EB4', 'a');
        content = content.replace('\u1EB6', 'a');
        content = content.replace('\302', 'a');
        content = content.replace('\u1EA6', 'a');
        content = content.replace('\u1EA4', 'a');
        content = content.replace('\u1EA8', 'a');
        content = content.replace('\u1EAA', 'a');
        content = content.replace('\u1EAC', 'a');
        content = content.replace('\u0111', 'd');
        content = content.replace('\u0110', 'd');
        content = content.replace('\350', 'e');
        content = content.replace('\351', 'e');
        content = content.replace('\u1EBB', 'e');
        content = content.replace('\u1EBD', 'e');
        content = content.replace('\u1EB9', 'e');
        content = content.replace('\352', 'e');
        content = content.replace('\u1EC1', 'e');
        content = content.replace('\u1EBF', 'e');
        content = content.replace('\u1EC3', 'e');
        content = content.replace('\u1EC5', 'e');
        content = content.replace('\u1EC7', 'e');
        content = content.replace('\310', 'e');
        content = content.replace('\311', 'e');
        content = content.replace('\u1EBA', 'e');
        content = content.replace('\u1EBC', 'e');
        content = content.replace('\u1EB8', 'e');
        content = content.replace('\312', 'e');
        content = content.replace('\u1EC0', 'e');
        content = content.replace('\u1EBE', 'e');
        content = content.replace('\u1EC2', 'e');
        content = content.replace('\u1EC4', 'e');
        content = content.replace('\u1EC6', 'e');
        content = content.replace('\354', 'i');
        content = content.replace('\355', 'i');
        content = content.replace('\u1EC9', 'i');
        content = content.replace('\u0129', 'i');
        content = content.replace('\u1ECB', 'i');
        content = content.replace('\314', 'i');
        content = content.replace('\315', 'i');
        content = content.replace('\u1EC8', 'i');
        content = content.replace('\u0128', 'i');
        content = content.replace('\u1ECA', 'i');
        content = content.replace('\362', 'o');
        content = content.replace('\363', 'o');
        content = content.replace('\u1ECF', 'o');
        content = content.replace('\365', 'o');
        content = content.replace('\u1ECD', 'o');
        content = content.replace('\364', 'o');
        content = content.replace('\u1ED3', 'o');
        content = content.replace('\u1ED1', 'o');
        content = content.replace('\u1ED5', 'o');
        content = content.replace('\u1ED7', 'o');
        content = content.replace('\u1ED9', 'o');
        content = content.replace('\u01A1', 'o');
        content = content.replace('\u1EDD', 'o');
        content = content.replace('\u1EDB', 'o');
        content = content.replace('\u1EDF', 'o');
        content = content.replace('\u1EE1', 'o');
        content = content.replace('\u1EE3', 'o');
        content = content.replace('\322', 'o');
        content = content.replace('\323', 'o');
        content = content.replace('\u1ECE', 'o');
        content = content.replace('\325', 'o');
        content = content.replace('\u1ECC', 'o');
        content = content.replace('\324', 'o');
        content = content.replace('\u1ED2', 'o');
        content = content.replace('\u1ED0', 'o');
        content = content.replace('\u1ED4', 'o');
        content = content.replace('\u1ED6', 'o');
        content = content.replace('\u1ED8', 'o');
        content = content.replace('\u01A0', 'o');
        content = content.replace('\u1EDC', 'o');
        content = content.replace('\u1EDA', 'o');
        content = content.replace('\u1EDE', 'o');
        content = content.replace('\u1EE0', 'o');
        content = content.replace('\u1EE2', 'o');
        content = content.replace('\371', 'u');
        content = content.replace('\372', 'u');
        content = content.replace('\u1EE7', 'u');
        content = content.replace('\u0169', 'u');
        content = content.replace('\u1EE5', 'u');
        content = content.replace('\u01B0', 'u');
        content = content.replace('\u1EEB', 'u');
        content = content.replace('\u1EE9', 'u');
        content = content.replace('\u1EED', 'u');
        content = content.replace('\u1EEF', 'u');
        content = content.replace('\u1EF1', 'u');
        content = content.replace('\331', 'u');
        content = content.replace('\332', 'u');
        content = content.replace('\u1EE6', 'u');
        content = content.replace('\u0168', 'u');
        content = content.replace('\u1EE4', 'u');
        content = content.replace('\u01AF', 'u');
        content = content.replace('\u1EEA', 'u');
        content = content.replace('\u1EE8', 'u');
        content = content.replace('\u1EEC', 'u');
        content = content.replace('\u1EEE', 'u');
        content = content.replace('\u1EF0', 'u');
        content = content.replace('\u1EF3', 'y');
        content = content.replace('\375', 'y');
        content = content.replace('\u1EF7', 'y');
        content = content.replace('\u1EF9', 'y');
        content = content.replace('\u1EF5', 'y');
        content = content.replace('Y', 'y');
        content = content.replace('\u1EF2', 'y');
        content = content.replace('\335', 'y');
        content = content.replace('\u1EF6', 'y');
        content = content.replace('\u1EF8', 'y');
        content = content.replace('\u1EF4', 'y');
        return content;
    }
}
