import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabaseOfUniqueSubject {
		
		static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		static final String DB_URL = "jdbc:mysql://localhost/base";
		static final String USER = "root";
		static final String PASS = "1234";
	   
	   
	   Connection conn;
	   Statement stmt;
	   PreparedStatement ps;
	  
	   public void addSubjectsToDatabase() throws SQLException, IOException {
		 this.createTable();  
		
		 String[] fileList= {"Department Of Analytics.xls"};

		 ExcelHandler excel=new ExcelHandler();
		 Subject sub=new Subject();
		 String[] subjectArray;
          for(int i=0;i< fileList.length;i++) {
                subjectArray=excel.read(fileList[i]);
                for(int j=0;j< subjectArray.length;j++) {
                	sub.sid=subjectArray[j];
                	System.out.println(sub.sid+" "+j);
                	if(!this.rowExists(sub.sid.toString()))
                	    this.insertIntoSubject(sub);
                }
                	
          }
	   }
	   
	   public void addLabsToDatabase() throws SQLException, IOException {
			 this.createLabTable();  
			
			 String[] fileList= {"Department Of Analytics Lab.xls"};

			 ExcelHandler excel=new ExcelHandler();
			 Subject sub=new Subject();
			 String[] labArray;
	          for(int i=0;i< fileList.length;i++) {
	                labArray=excel.read(fileList[i]);
	                for(int j=0;j< labArray.length;j++) {
	                	sub.lid=labArray[j];
	                	System.out.println(sub.lid+" "+j);
	                	if(!this.rowExists(sub.lid.toString()))
	                	    this.insertIntoLab(sub);
	                }
	                	
	          }
		   }
	   
	   public void insertIntoSubject(Subject subjectObj) throws SQLException {
		   Connection conn= this.conn;
		   PreparedStatement ps;
		   
		   String insertQuery="insert into "+Subject.SUBJECT_INFO_TABLE_NAME+" values(?)";
		   this.ps=conn.prepareStatement(insertQuery);
		   ps=this.ps;
		   
		   ps.setString(1, subjectObj.sid);
		   ps.executeUpdate();
		  ps.close();
	   }
	   
	   
	   public void insertIntoLab(Subject subjectObj) throws SQLException {
		   Connection conn= this.conn;
		   PreparedStatement ps;
		   
		   String insertQuery="insert into "+Subject.LAB_INFO_TABLE_NAME+" values(?)";
		   this.ps=conn.prepareStatement(insertQuery);
		   ps=this.ps;
		   
		   ps.setString(1, subjectObj.lid);
		   ps.executeUpdate();
		  ps.close();
	   
	  }
	  
	   public boolean labRowExists(String lid) throws SQLException {
			  boolean flag=false;
			 
			  Statement stmt=this.stmt;
			 
			 String selectQuery="select * from "+Subject.LAB_INFO_TABLE_NAME+" where "+Subject.LAB_ID+"='"+lid+"'";
			  //String selectQuery= "Select * from "+Subject.SUBJECT_INFO_TABLE_NAME+
				//	  " where "+Subject.SUBJECT_ID+"="+sid;
			  
			  ResultSet rs= stmt.executeQuery(selectQuery);
			  //returns true if a row was selected in the rs object
			  if(rs.next()) {
				  flag=true;
				  System.out.println(lid+" exists");
			  }
				  return flag;
		  }
	   
	   public boolean rowExists(String sid) throws SQLException {
			  boolean flag=false;
			 
			  Statement stmt=this.stmt;
			 
			 String selectQuery="select * from "+Subject.SUBJECT_INFO_TABLE_NAME+" where "+Subject.SUBJECT_ID+"='"+sid+"'";
			  //String selectQuery= "Select * from "+Subject.SUBJECT_INFO_TABLE_NAME+
				//	  " where "+Subject.SUBJECT_ID+"="+sid;
			  
			  ResultSet rs= stmt.executeQuery(selectQuery);
			  //returns true if a row was selected in the rs object
			  if(rs.next()) {
				  flag=true;
				  System.out.println(sid+" exists");
			  }
				  return flag;
		  }
	   
	   public void createTable() throws SQLException {
			  Statement stmt=this.stmt;
			  String sql="desc "+Subject.SUBJECT_INFO_TABLE_NAME;
			  ResultSet rs=null;
			  try {
			  rs=stmt.executeQuery(sql);
			  }catch(Exception e) {
				  
			  }
			  if(rs !=null){
				  System.out.println("Subject table exist");
				  
			  }else {
				  System.out.println("Subject table not exist");
				  String subjectTableQuery="Create table "+Subject.SUBJECT_INFO_TABLE_NAME+"("+
		                    	  Subject.SUBJECT_ID+" varchar(100)"+
				                 ")";
		          stmt.executeUpdate(subjectTableQuery);
			  }
	       }
	   
	   public void createLabTable() throws SQLException {
			  Statement stmt=this.stmt;
			  String sql="desc "+Subject.LAB_INFO_TABLE_NAME;
			  ResultSet rs=null;
			  try {
			  rs=stmt.executeQuery(sql);
			  }catch(Exception e) {
				  
			  }
			  if(rs !=null){
				  System.out.println("LAb table exist");
				  
			  }else {
				  System.out.println("Lab table not exist");
				  String subjectTableQuery="Create table "+Subject.LAB_INFO_TABLE_NAME+"("+
		                    	  Subject.LAB_ID+" varchar(100)"+
				                 ")";
		          stmt.executeUpdate(subjectTableQuery);
			  }
	       }
	   
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		CreateDatabaseOfUniqueSubject obj= new CreateDatabaseOfUniqueSubject();
		Class.forName(JDBC_DRIVER);
		System.out.println("Connecting to database...");		
	    obj.conn = DriverManager.getConnection(DB_URL,USER,PASS);	    
	    obj.stmt= obj.conn.createStatement();
	    System.out.println("Successfully connected ");
	    
	    obj.addSubjectsToDatabase();
	    obj.addLabsToDatabase();
	    
	}

}
