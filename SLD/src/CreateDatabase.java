import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CreateDatabase {
	
		   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		   static final String DB_URL = "jdbc:mysql://localhost/base";

		   static final String USER = "root";
		   static final String PASS = "1234";
		   
		   
		   Connection conn;
		   Statement stmt;
		   PreparedStatement ps;
		   
		  
		   
		  public void addJsonDataToDatabase() throws SQLException, FileNotFoundException, IOException, ParseException {
			  TrimJSONfile t= new TrimJSONfile();
				t.trim();
			  
			   this.createTable();
			   this.createTableFinal();
			   Faculty facultyObj=new Faculty();
				try {

		            JSONParser parser = new JSONParser();
		            Object obj = parser.parse(new FileReader(Files.INPUT_FOR_CREATEDATABASE));//"E:/other/Sub_empty/All Subjects/outputSldMany.json"));
		            JSONObject jsonObject = (JSONObject) obj;
		            
		            //using for-each loop for iteration
		            for (Object facultyObject : jsonObject.keySet()) {
		                //based on you key types
		            	//System.out.println("1 "+facultyObject);
		            	String sapid=facultyObject.toString();
		            	facultyObj.sapid= sapid;
		            	
		            	//rowExists(String sapid) return true if row exists
		  		   	    if(!rowExists(facultyObj.sapid)) {
		  		   	   
		                String facultyName = (String)facultyObject;
		                Object allSubject = jsonObject.get(facultyName);
		                JSONObject jobj=(JSONObject)allSubject;
		                int c=2;
		                for(Object subjectsObject : jobj.keySet()) {
		                	
		                	String priorityNo = (String)subjectsObject;
		                    Object valueInPriority = jobj.get(priorityNo);
		                    //System.out.println(c++ +" "+priorityNo+" "+valueInPriority.toString());
		                    switch(priorityNo.toString()) {
		                    case "name":
		                    	facultyObj.name= valueInPriority.toString();
		                    	//System.out.println("key: "+ priorityNo + "\t\t value: " + valueInPriority.toString());
		                        break;
		                    case "designation":
		                    	facultyObj.designation=valueInPriority.toString();
		                    	//System.out.println("key: "+ priorityNo + "\t\t value: " + valueInPriority.toString());
		                    	break;
		                    case "year":
		                    	facultyObj.year=valueInPriority.toString();
		                    	//System.out.println("key: "+ priorityNo + "\t\t value: " + valueInPriority.toString());
		                    	break;
		                    case "credits":
		                    	facultyObj.credits=valueInPriority.toString();
		                    	//System.out.println("key: "+ priorityNo + "\t\t value: " + valueInPriority.toString());
		                    	break;
		                    case "priority_core_1":
		                    	facultyObj.pc1=valueInPriority.toString();
		                    	break;
		                    case "priority_core_2":
		                    	facultyObj.pc2=valueInPriority.toString();
		                    	break;
		                    case "priority_core_3":
		                    	facultyObj.pc3=valueInPriority.toString();
		                    	break;
		                    case "priority_core_4":
		                    	facultyObj.pc4=valueInPriority.toString();
		                    	break;
		                    case "priority_core_5":
		                    	facultyObj.pc5=valueInPriority.toString();
		                    	break;
		                    case "priority_domain_1":
		                    	facultyObj.pd1=valueInPriority.toString();
		                    	break;
		                    case "priority_domain_2":
		                    	facultyObj.pd2=valueInPriority.toString();
		                    	break;
		                    case "priority_domain_3":
		                    	facultyObj.pd3=valueInPriority.toString();
		                    	break;
		                    case "priority_domain_4":
		                    	facultyObj.pd4=valueInPriority.toString();
		                    	break;
		                    case "priority_domain_5":
		                    	facultyObj.pd5=valueInPriority.toString();
		                    	break;
		                    default:;
		                    }
		                  /**/
                         }
		                //System.out.println(facultyObj.sapid+" "+facultyObj.designation+" "+facultyObj.name+" "+facultyObj.year);
		                this.insertIntoFaculty(facultyObj);
		                System.out.println();
		  		   	    }
		            }
		         } catch (Exception e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
			}
		 
		  public void createFinalFaculty() throws SQLException {
			  String query="insert into "+Faculty.FINALFACULTY_INFO_TABLE_NAME+"("+Faculty.SAPID+
					  ","+Faculty.NAME+") select "+Faculty.SAPID+","+Faculty.NAME+
					  " from "+Faculty.FACULTY_INFO_TABLE_NAME_Sorted;
			  Statement stmt=this.stmt;
			  stmt.executeUpdate(query);
			  stmt.close();
		  }
		  
		  public void sortAndStore() throws SQLException{
			  Connection conn=this.conn;
			  Statement stmt=this.stmt;
			  String sql="desc "+Faculty.FACULTY_INFO_TABLE_NAME_Sorted;
			  ResultSet rs=null;
			  try {
			  rs=stmt.executeQuery(sql);
			  }catch(Exception e) {
				  
			  }
			  if(rs !=null){
				  System.out.println("Sorted table exists");				  
			  }else {
			  String facultyTableQuery="Create table "+Faculty.FACULTY_INFO_TABLE_NAME_Sorted+"("+
	                    	  Faculty.SAPID+" varchar(15),"+
			                  Faculty.NAME+" varchar(50),"+
			                  Faculty.DESIGNATION+" int,"+
			                  Faculty.YEAR+" double,"+
			                  Faculty.CREDITS+" int,"+
	                          Faculty.PRIORITY_CORE_1+" varchar(100),"+
	                          Faculty.PRIORITY_CORE_2+" varchar(100),"+
	                          Faculty.PRIORITY_CORE_3+" varchar(100),"+
	                          Faculty.PRIORITY_CORE_4+" varchar(100),"+
	                          Faculty.PRIORITY_CORE_5+" varchar(100),"+
	                          Faculty.PRIORITY_DOMAIN_1+" varchar(100),"+
	                          Faculty.PRIORITY_DOMAIN_2+" varchar(100),"+
	                          Faculty.PRIORITY_DOMAIN_3+" varchar(100),"+
	                          Faculty.PRIORITY_DOMAIN_4+" varchar(100),"+
	                          Faculty.PRIORITY_DOMAIN_5+" varchar(100)"+
	                          		 ")";
	           stmt.executeUpdate(facultyTableQuery);
			  
			  String sort="insert into FacultySorted select * from Faculty order by designation asc,year desc";
			  stmt.execute(sort);
			  }
			  System.out.println("SortandStore");
		  }
		   
		  public void insertIntoFaculty(Faculty facultyObj) throws SQLException, IOException {
			  Connection conn=this.conn;
		   	  PreparedStatement ps;
		   	  
		   		  
			  String insertQuery ="insert into "+
					  			   Faculty.FACULTY_INFO_TABLE_NAME+
					  			   " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			  this.ps=conn.prepareStatement(insertQuery);
			  ps=this.ps;
			  AssignCreditsToDesignation CreditObj=new AssignCreditsToDesignation();
			  //System.out.println("Database-> "+facultyObj.sapid+" "+facultyObj.designation+" "+facultyObj.name+" "+facultyObj.year);
              
			  ps.setString(1, facultyObj.sapid);
			  ps.setString(2, facultyObj.name);
			  ps.setInt(3, Integer.parseInt(facultyObj.designation));
			  ps.setDouble(4,Double.parseDouble(facultyObj.year));
			  ps.setInt(5, (int)Double.parseDouble(CreditObj.Credit[Integer.parseInt(facultyObj.designation)]));
			  //System.out.println(Integer.parseInt(AssignCreditsToDesignation.Credit[Integer.parseInt(facultyObj.designation)]));
			  
			  ps.setString(6, facultyObj.pc1);
			  ps.setString(7, facultyObj.pc2);
			  ps.setString(8, facultyObj.pc3);
			  ps.setString(9, facultyObj.pc4);
			  ps.setString(10, facultyObj.pc5);
			  ps.setString(11, facultyObj.pd1);
			  ps.setString(12, facultyObj.pd2);
			  ps.setString(13, facultyObj.pd3);
			  ps.setString(14, facultyObj.pd4);
			  ps.setString(15, facultyObj.pd5);
			  try {
			  ps.executeUpdate();
			  }catch(SQLException e) {
				  System.out.println("Sapid exists");
			  }
			  ps.close();
			
		   	  
		  }
		  		   
		  public boolean rowExists(String sapid) throws SQLException {
			  boolean flag=false;
			  stmt=this.stmt;
			  String selectQuery= "Select * from "+Faculty.FACULTY_INFO_TABLE_NAME+
					  " where "+Faculty.SAPID+"= "+sapid;
			  
			  ResultSet rs= stmt.executeQuery(selectQuery);
			  //returns true if a row was selected in the rs object
			  if(rs.next()) {
				  flag=true;
				  System.out.println(sapid+" exists");
			  }
			  else {
				  //System.out.println(sapid+" not exists");
			  }
				  return flag;
		  }
		  
		  public void createTable() throws SQLException {
			  Statement stmt=this.stmt;
			  String sql="desc "+Faculty.FACULTY_INFO_TABLE_NAME;
			  
			
			  ResultSet rs=null;
			  try {
			  rs=stmt.executeQuery(sql);
			  }catch(Exception e) {
				  
			  }
			  if(rs !=null){
				  System.out.println("table exist");
				  
			  }else {
				  System.out.println("table not exist");
				  //Never Put ',' after last varchar()
				  String facultyTableQuery="Create table "+Faculty.FACULTY_INFO_TABLE_NAME+"("+
		                    	  Faculty.SAPID+" varchar(15),"+
				                  Faculty.NAME+" varchar(50),"+
				                  Faculty.DESIGNATION+" int,"+
				                  Faculty.YEAR+" double,"+
				                  Faculty.CREDITS+" int,"+
		                          Faculty.PRIORITY_CORE_1+" varchar(100),"+
		                          Faculty.PRIORITY_CORE_2+" varchar(100),"+
		                          Faculty.PRIORITY_CORE_3+" varchar(100),"+
		                          Faculty.PRIORITY_CORE_4+" varchar(100),"+
		                          Faculty.PRIORITY_CORE_5+" varchar(100),"+
		                          Faculty.PRIORITY_DOMAIN_1+" varchar(100),"+
		                          Faculty.PRIORITY_DOMAIN_2+" varchar(100),"+
		                          Faculty.PRIORITY_DOMAIN_3+" varchar(100),"+
		                          Faculty.PRIORITY_DOMAIN_4+" varchar(100),"+
		                          Faculty.PRIORITY_DOMAIN_5+" varchar(100)"+
		                          		 ")";
		           stmt.executeUpdate(facultyTableQuery);
			  }
	       }
		  
		  public void createTableFinal() throws SQLException {
			  Statement stmt=this.stmt;
			  String sql="desc "+Faculty.FINALFACULTY_INFO_TABLE_NAME;
			  
			
			  ResultSet rs=null;
			  try {
			  rs=stmt.executeQuery(sql);
			  }catch(Exception e) {
				  
			  }
			  if(rs !=null){
				  System.out.println("table exist");
				  
			  }else {
				  System.out.println("table not exist");
				  //Never Put ',' after last varchar()
				  String facultyTableQuery="Create table "+Faculty.FINALFACULTY_INFO_TABLE_NAME+"("+
                    	  Faculty.SAPID+" varchar(15),"+
		                  Faculty.NAME+" varchar(50),"+
		                  Faculty.CREDITS+" varchar(50),"+
                          Faculty.SUBJECT_1+" varchar(100),"+
                          Faculty.SUBJECT_2+" varchar(100),"+
                          Faculty.SUBJECT_3+" varchar(100),"+
                          Faculty.SUBJECT_4+" varchar(100),"+
                          Faculty.SUBJECT_5+" varchar(100),"+
                          Faculty.SUBJECT_6+" varchar(100),"+
                          Faculty.SUBJECT_7+" varchar(100),"+
                          Faculty.SUBJECT_8+" varchar(100),"+
                          Faculty.SUBJECT_9+" varchar(100),"+
                          Faculty.SUBJECT_10+" varchar(100)"+
                          		 ")";
				  
				  /*String facultyTableQuery="Create table "+Faculty.FINALFACULTY_INFO_TABLE_NAME+"("+
		                    	  Faculty.SAPID+" varchar(15),"+
				                  Faculty.NAME+" varchar(50),"+
		                          Faculty.PRIORITY_CORE_1+" varchar(100),"+
		                          Faculty.PRIORITY_CORE_2+" varchar(100),"+
		                          Faculty.PRIORITY_CORE_3+" varchar(100),"+
		                          Faculty.PRIORITY_CORE_4+" varchar(100),"+
		                          Faculty.PRIORITY_CORE_5+" varchar(100),"+
		                          Faculty.PRIORITY_DOMAIN_1+" varchar(100),"+
		                          Faculty.PRIORITY_DOMAIN_2+" varchar(100),"+
		                          Faculty.PRIORITY_DOMAIN_3+" varchar(100),"+
		                          Faculty.PRIORITY_DOMAIN_4+" varchar(100),"+
		                          Faculty.PRIORITY_DOMAIN_5+" varchar(100)"+
		                          		 ")";*/
				  
		           stmt.executeUpdate(facultyTableQuery);
			  }
	       }
		  
		  
		  public static void main(String[] args) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException, ParseException {
			CreateDatabase obj=new CreateDatabase();
			//Register JDBC driver
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");		
			//Creating Connection
		     obj.conn = DriverManager.getConnection(DB_URL,USER,PASS);	    
		    //creating statement object
		     obj.stmt= obj.conn.createStatement();
		     
		    System.out.println("Successfully connected "+obj.stmt);
		   
		    
		    obj.addJsonDataToDatabase();
		    //obj.createTable();
		    //obj.insertIntoFaculty();
		    obj.sortAndStore();
		    //obj.display();
		    obj.createFinalFaculty();
		    obj.stmt.close();
		    obj.conn.close();
	}

}
