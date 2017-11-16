import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Distribution {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/base";

	static final String USER = "root";
	static final String PASS = "1234";
	   
	   
	Connection conn;
	Statement stmt;
    PreparedStatement ps;
    int subject1=4;
    int subject10=13;
	
	public void distribute() throws SQLException {
		 int core1=6;
		 int core5=10;
		 int domain1=11;
		 int domain5=15;
		 
		 this.distributeIn(core1, core5);
		 this.distributeIn(domain1, domain5);
	
	}

	public void distributeIn(int start,int last) throws SQLException {
		ResultSet faculty= this.getMyRow(1);
		faculty.beforeFirst();
		String sid;
		for(int i=start; i<=last; i++) {
			
			while(faculty.next()) {
			sid=faculty.getString(start);
			if(this.isAvailable(sid)) {
				this.checkCreditConstraint(faculty.getRow(), i, last);
			}else {
				this.checkLowerPriority(faculty.getRow(), i, last);
			}
			}
		System.out.println("Priority "+i+" Done");
		}
	}
	
	public void allot(String sapid,String sid,int column) throws SQLException {
		//insert into finalfaculty
		// column is used to retrieve column name like priority_core_1 where we will insert the given sid
		this.insertIntoFinalFaculty(sapid,sid,column);
		//delete from subject table
		this.delete(sid);
	}
	
	public void checkLowerPriority(int row,int current,int last) throws SQLException {
		ResultSet rs=this.getMyRow(row);
		for(int i=current; i<=last; i++ ) {
			if(this.isAvailable(rs.getString(i))) {
				this.checkCreditConstraint(row, i, last);
			}else {
				break;
			}
		}
	}
	
	/*public void checkHourConstraint(int row,int current,int last) throws SQLException {
		ResultSet rs=this.getMyRow(row);
		double hourFaculty, hourSubject;
		hourFaculty= Double.parseDouble(rs.getString("hours_available"));
		hourSubject= getCredits(rs.getString(current));
		if(hourFaculty >= hourSubject)
			this.allot(rs.getString("sapid"),rs.getString(current),current);
		else
			this.checkLowerPriority(row, current+1, last);		
	}
	*/
	public void checkCreditConstraint(int row,int currentColumn,int lastColumn) throws SQLException {
		// last column of core or domain
		ResultSet rs=this.getMyRow(row);//row of faculty
		double creditFaculty, creditSubject;
		creditFaculty= Double.parseDouble(rs.getString("credits"));
		creditSubject= getCredits(rs.getString(currentColumn));
		if(creditFaculty >= creditSubject) {
			double sub=creditFaculty - creditSubject;
			this.deductCredit(rs.getString("sapid"),sub);//we pass the new credit
			this.allot(rs.getString("sapid"),rs.getString(currentColumn),currentColumn);
		}else
			this.checkLowerPriority(row, currentColumn+1, lastColumn);		
	}
	
	public void deductCredit(String sapid,double newCredit) throws SQLException {
		Statement stmt=this.conn.createStatement();
		String query="update "+Faculty.FACULTY_INFO_TABLE_NAME_Sorted+" set credits="+newCredit+" where sapid='"+sapid+"'";
		stmt.executeUpdate(query);
		stmt.close();
	}
	
	public boolean isAvailable(String sid) throws SQLException {
		boolean flag=false;
		ResultSet rs1=this.getGivenSubject(sid);
		  //returns true if a row was selected in the rs object
		  if(rs1.next()) {
			  flag=true;
			  //System.out.println(sid +" exists");
		  }
		  //System.out.println("inside isavailable");
		return flag;
	}
	
	public void insertIntoFinalFaculty(String sapid,String sid,int column) throws SQLException {
		ResultSet rs=this.getFaculties();
		ResultSetMetaData rsmd=rs.getMetaData();
		Statement stmt=this.conn.createStatement();
		String columnName= rsmd.getColumnName(column);
		
		String q="update "+Faculty.FINALFACULTY_INFO_TABLE_NAME+" set "+columnName+"='"+sid+"' where "+Faculty.SAPID+"='"+sapid+"'"; 
		stmt.executeUpdate(q);
		stmt.close();
	}
	
	public void delete(String sid) throws SQLException {
		this.stmt=this.conn.createStatement();
		
		String deleteQuery="delete from "+Subject.SUBJECT_INFO_TABLE_NAME+" where "+Subject.SUBJECT_ID+"='"+sid+"'";
		int i=stmt.executeUpdate(deleteQuery);
		if(i>0)
			System.out.println("Removed-> "+sid);
		
		//System.out.println("inside delete");
		stmt.close();
	}
	
	public int getColumnIndexFinalFaculty(int row) throws SQLException {
		ResultSet rs= this.getMyRowFinalFaculty(row);
		int index=0;
		for(int i=subject1;i<=subject10;i++) {
			if(rs.getString(i) == null) {
				index= i;
				break;
			}
		}
		return index;
	}
	
	public ResultSet getMyRowFinalFaculty(int row) throws SQLException {
		ResultSet rs=this.getFinalFaculties();
		rs.absolute(row);
		return rs;
	}
	
	public ResultSet getMyRow(int row) throws SQLException {
		ResultSet rs=this.getFaculties();
		rs.absolute(row);
		return rs;
	}
	public Double getCredits(String data) {
    	String c="";
    	int pos=0;
    	int l= data.length();
    	boolean flag=true;
    	// l-2 bcoz last element is / 
    	for(int i=l-2; i>=0; i--) {
    		if(data.charAt(i)=='/') {
    			pos=i;
    			flag=false;
    			break;
    		//System.out.print(data.charAt(i));
    		}
    	}
    	if(flag)
    		System.out.println("Credit was not assigned properly in AssignCreditToDesignation");
    	
    	c= data.substring(pos+1,l-1);
    	
    	return Double.parseDouble(c);
    }
	
	
	public ResultSet getFinalFaculties() throws SQLException {
		this.stmt=this.conn.createStatement();
		String selectQuery="select * from "+Faculty.FINALFACULTY_INFO_TABLE_NAME;
		ResultSet rs= stmt.executeQuery(selectQuery);
		return rs;
	}
	
	public ResultSet getFaculties() throws SQLException {
		this.stmt=this.conn.createStatement();
		String selectQuery="select * from "+Faculty.FACULTY_INFO_TABLE_NAME_Sorted;
		ResultSet rs= stmt.executeQuery(selectQuery);
		return rs;
	}
	
	public ResultSet getGivenSubject(String sid) throws SQLException {
		this.stmt=this.conn.createStatement();
		String selectQuery="select * from "+Subject.SUBJECT_INFO_TABLE_NAME+" where "+Subject.SUBJECT_ID+"='"+sid+"'";
		ResultSet rs= stmt.executeQuery(selectQuery);
		return rs;
	}
	
	public ResultSet getSubjects() throws SQLException {
		this.stmt=this.conn.createStatement();
		String selectQuery="select * from "+Subject.SUBJECT_INFO_TABLE_NAME;
		ResultSet rs= stmt.executeQuery(selectQuery);
		return rs;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Distribution obj=new Distribution();
		Class.forName(JDBC_DRIVER);		
	    obj.conn = DriverManager.getConnection(DB_URL,USER,PASS);	 
	  
	    // 1.0/BFSI/Banking/303.0/2.0/
	    // 8.0/BFSI/Insurance/502.0/1.0/
	   // System.out.println(obj.getCredits("8.0/BFSI/Insurance/502.0/100.0/"));//obj.isAvailable("8.0/BFSI/Insurance/502.0/1.0/"));
	   //obj.allot("500062345", "1.0/BFSI/Banking/303.0/2.0/", 6);
	    //obj.distribute();
	    //System.out.println(obj.getColumnIndexFinalFaculty(1));
	    
	}

}
