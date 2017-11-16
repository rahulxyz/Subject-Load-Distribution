import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Alloter {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/base";

	static final String USER = "root";
	static final String PASS = "1234";
	   
	   
	Connection conn;
	Statement stmt;
    PreparedStatement ps;
	
    public Alloter() throws ClassNotFoundException, SQLException {
		conn=Distributer.conn;
	}
    
/*	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Alloter obj= new Alloter();
		Class.forName(JDBC_DRIVER);		
	    obj.conn = DriverManager.getConnection(DB_URL,USER,PASS);
		obj.tester();

	}
	*/
	//to allot lid to a faculty (in finalfaculty)
	public void allotLab(int row,String lid) throws SQLException, ClassNotFoundException {
		//insert into finalfaculty
		this.insertIntoFinalFaculty(row, lid);
		
		this.deductCredit(row, lid);
		//delete from Lab table
		this.removeFromLab(lid);
	}
	
	//to allot given sid to a faculty (finalfaculty)
	public void allotSubject(int row,String sid) throws SQLException, ClassNotFoundException {
		//insert into finalfaculty
		this.insertIntoFinalFaculty(row, sid);
		
		this.deductCredit(row, sid);
		//delete from subject table
		this.removeFromSubject(sid);
	}
	
	// to insert sid or lid in finalfaculty int a particular row
	public void insertIntoFinalFaculty(int row,String sid) throws SQLException, ClassNotFoundException {
		Fetcher fetch= new Fetcher();
		ResultSet rs=fetch.getSpecificRowFinalFaculty(row);	
		String sapid=rs.getString("sapid");
		int column= fetch.getColumnIndexFinalFaculty(row);
		
		ResultSetMetaData rsmd=rs.getMetaData();
		String columnName= rsmd.getColumnName(column);
				
		Statement stmt=this.conn.createStatement();
		String q="update "+Faculty.FINALFACULTY_INFO_TABLE_NAME+" set "+columnName+"='"+sid+"' where "+Faculty.SAPID+"='"+sapid+"'"; 
		stmt.executeUpdate(q);
		stmt.close();
	}
	
	//deducts credit(= sid's credit) from credit of given row in facultysorted
	public void deductCredit(int row,String sid) throws SQLException, ClassNotFoundException {
	
		Fetcher fetch=new Fetcher();
		ResultSet rs=fetch.getSpecificRowFacultySorted(row);
		String sapid= rs.getString("sapid");
		int creditAvailable = rs.getInt("credits");
		Double d= new Double(fetch.getCreditOfSid(sid));
		int creditOfSubject= d.intValue();
		//System.out.println("Credit available- "+creditAvailable+" CreditOfsubject"+creditOfSubject);
		int newCreditOfFaculty= creditAvailable - creditOfSubject;
		
		Statement stmt=this.conn.createStatement();
		String query="update "+Faculty.FACULTY_INFO_TABLE_NAME_Sorted+" set credits="+newCreditOfFaculty+" where sapid='"+sapid+"'";
		stmt.executeUpdate(query);
		stmt.close();
	}
	
	//to remove lid from Lab table if available
	public void removeFromLab(String lid) throws SQLException {
			this.stmt=this.conn.createStatement();
			
			String deleteQuery="delete from "+Subject.LAB_INFO_TABLE_NAME+" where "+Subject.LAB_ID+"='"+lid+"'";
			int i=stmt.executeUpdate(deleteQuery);
			/*if(i>0)
				System.out.println("Removed-> "+lid);
			else
				System.out.println("NotAvailabel-> "+lid);*/
			
			stmt.close();
		}
		
	//to remove sid from Subject table if available
	public void removeFromSubject(String sid) throws SQLException {
		this.stmt=this.conn.createStatement();
		
		String deleteQuery="delete from "+Subject.SUBJECT_INFO_TABLE_NAME+" where "+Subject.SUBJECT_ID+"='"+sid+"'";
		int i=stmt.executeUpdate(deleteQuery);
		/*if(i>0)
			System.out.println("Removed-> "+sid);
		else
			System.out.println("NotAvailabel-> "+sid);*/
		
		stmt.close();
	}
	
	public void tester() throws SQLException, ClassNotFoundException {
		
		this.allotLab(1, "1.0/BFSI/Introduction to C Lab/301.0/2.0/");
		
		/*this.allotSubject(1, "1.0/BFSI/Introduction to C/301.0/3.0/");*/
		
		/*this.insertIntoFinalFaculty(1, "rahul");*/
		
		/*this.removeFromLab("1.0/BFSI/Data Structure Lab/302.0/2.0/");*/
		
		/*this.removeFromSubject("1.0/BFSI/Banking/303.0/2.0/");*/
	}

}
