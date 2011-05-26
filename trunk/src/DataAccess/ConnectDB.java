package DataAccess;

import java.sql.*;
import java.util.ArrayList;

public class ConnectDB {
    
    String strHost;
    String strUserName;
    String strPassWord;
    String strDataBase;
    
    Connection connect = null;
    Statement statement = null;
    ResultSet resultset = null;
    
    public ConnectDB(){
        
    }
    
    public ConnectDB(String host, String username, String password, String database) {    
        this.strHost = host;
        this.strUserName = username;
        this.strPassWord = password;
        this.strDataBase = database;
    }
    
    public Boolean TestConnection(){
    	try{
    		driverTest();
    		//System.out.println("Driver OK !");
    		return true;
    	} catch(Exception e){
    		System.out.println("Driver FAIL !");
    		return false;
    	}
    }
    
    public void GetConnection(){
    	try{
    		this.getConnect();
    		this.Close();
    	} catch (Exception e){
    		System.out.println("Get Connection FAIL by Exception !");
    		System.out.println(e.getMessage());
    	}
    }
    
    protected void driverTest () throws Exception{
        try {
            //Kiểm tra Class Name.
            Class.forName("oracle.jdbc.OracleDriver");
        }
        // Nếu chưa tồng tại thì mém lỗi ra ngoài.
        catch (java.lang.ClassNotFoundException e) {
                throw new Exception("Oracle JDBC Driver not found ... ");
        } 
    }
    
    protected Connection getConnect() throws Exception{
        if(this.connect == null){
            driverTest();
            String url = "jdbc:oracle:thin:@"+ this.strHost+":1521:"+this.strDataBase;
            try {
                // Tạo Connection thông qua Url
            	//System.out.println("Url Ket noi: " + url + "\n");
                this.connect = DriverManager.getConnection(url, this.strUserName, this.strPassWord);
                //System.out.println("Ket noi thanh cong !\n");
            }
            // Nếu không thành công ném lỗi ra ngoài.
            catch (java.sql.SQLException e) {
            	System.out.println("Ket noi DB that bai !\n");
                throw new Exception("Khong the ket noi den DataBase Server: " + url + e.getMessage());
            }
       }
        // Trả connection ra ngoài.
        return this.connect;
    }
    
    protected Statement getStatement() throws Exception {
        // Kiểm tra statement nếu = null hoặc đã đóng.
        if(this.statement == null){
            // Khởi tạo một statement mới.
            this.statement = this.getConnect().createStatement();
        }
        // Trả staement ra ngoài.
        return this.statement;
    }
    
    public ResultSet excuteQuery(String Query) throws Exception{
        try{
            // Thực thi câu lệnh.
            this.resultset = getStatement().executeQuery(Query);
            //System.out.println("Thuc thi query thanh cong !\n");
        }
        // Nếu không thành công ném lỗi ra ngoài.
        catch(Exception e){
        	System.out.println("Thuc thi query that bai !\n");
            throw new Exception("Error : " +e.getMessage() +" - "+Query);
        }
        // Trả kết quả ra ngoài.
        return this.resultset;
    }
    
    // Thức thi các câu lệnh Inser, Update, Delete
    public int executeUpdate(String Query) throws Exception{
//        //Khai báo biến int lưu trữ kết quả tình trạng thực thi câu lệnh Query.
//        int res =Integer.MIN_VALUE;
//        try{
//            //Thực thi câu lệnh.
//            getStatement().execute(Query);
//        }
//        //Nếu không thành công ném lỗi ra ngoài.
//        catch(Exception e){
//        	System.out.println("Thuc thi update query that bai !\n");
//            throw new Exception("Error: " +e.getMessage() +" - "+Query);}
//        finally{
//            //Đóng kết nối.
//            this.Close();
//        }
//        //Trả kết quả ra ngoài.
//        return res;
    	try{
    		Statement s = this.getConnect().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
    		s.executeUpdate(Query);
    	}catch(Exception e){
        	System.out.println("Thuc thi update query that bai !\n");
          throw new Exception("Error: " +e.getMessage() +" - "+Query);}
      finally{
          //Đóng kết nối.
          this.Close();
      }
      return 0;
    }
    
    public void Close() throws SQLException{
        try{
        	// Nếu Result chưa đóng. Đóng result
        	if(this.resultset!=null){
        		this.resultset.close();
        		this.resultset = null;
        	}
        } catch (Exception e){System.out.println(e.getMessage());}
        
        try{
        	// Nếu statement chưa đóng. Đóng statement.
        	if(this.statement!=null){
        		this.statement.close();
        		this.statement = null;
        	}
        } catch (Exception e){System.out.println(e.getMessage());}
        
        try{	
        	// Nếu connection chưa đóng. Đóng connection.
        	if(this.connect!=null){
        		this.connect.close();
        		this.connect =null;
        	}
        } catch (Exception e){System.out.println(e.getMessage());}
    }
}  