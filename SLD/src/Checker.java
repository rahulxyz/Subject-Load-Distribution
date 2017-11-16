import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Checker {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/base";

	static final String USER = "root";
	static final String PASS = "1234";
	   
	   
	Connection conn;
	Statement stmt;
    PreparedStatement ps;
   
    
    public Checker() throws ClassNotFoundException, SQLException {		
	    this.conn = Distributer.conn;
	}
	
	/*public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
		Checker obj= new Checker();
		Class.forName(JDBC_DRIVER);		
	    obj.conn = DriverManager.getConnection(DB_URL,USER,PASS);
		obj.tester();

	}*/


	// to check if faculty (in facultysorted) has credits available for the given subject(sid)
	public Boolean isCreditAvailable(int row, String sid) throws ClassNotFoundException, SQLException {
		boolean flag= false;
		Fetcher fetch=new Fetcher();
		ResultSet rs=fetch.getSpecificRowFacultySorted(row);
		int creditAvailable = rs.getInt("credits");
		Double d= new Double(fetch.getCreditOfSid(sid));
		int creditOfSubject= d.intValue();
		//System.out.println("Credit available- "+creditAvailable+" CreditOfsubject"+creditOfSubject);
		if(creditAvailable >= creditOfSubject)
			flag=true;
		stmt.close();
		return flag;
	}

	// to check if the given given faculty has been already alloted in this sem and branch 
	public Boolean isSameBranchAndSemAvailable(String sid, int row) throws SQLException, ClassNotFoundException {
		Fetcher fetch= new Fetcher();
		Boolean flag=false;
		ResultSet rs=fetch.getSpecificRowFinalFaculty(row);
		String semBranchToBeChecked= fetch.getSemAndBranchOfSid(sid);
		for(int i=Faculty.Subject1;i<=Faculty.Subject10;i++) {
			if(rs.getString(i)==null)
				break;
			String semBranchAlreadyPresent = fetch.getSemAndBranchOfSid(rs.getString(i));
		    if(semBranchToBeChecked.equals(semBranchAlreadyPresent)) {
		    	flag=true;
		    	break;
		    }	
		}
		stmt.close();
		return flag;
	}
	
	//accepts a sid from subject table and returns if its lab is available or not
	public String isLabAvailable(String sid) throws SQLException, ClassNotFoundException {
		String temp=null;
		this.stmt=this.conn.createStatement();
		Fetcher fetch= new Fetcher();
		String s= fetch.getSubjectNameForLab(sid);
		String lid= s.concat(" Lab/");
		//System.out.println("Lab from sid-> "+lid);
		
		String selectQuery="select * from "+Subject.LAB_INFO_TABLE_NAME+" where "+Subject.LAB_ID+" like '"+lid+"%'";
		ResultSet rs= stmt.executeQuery(selectQuery);
		
		boolean flag=false;		
		  //returns true if a row was selected in the rs object
		  if(rs.next()) {
			  flag=true;
			  temp=rs.getString(1);
			 // System.out.println(temp);
		  }
		  stmt.close();
		return temp;
	}
	
	//to check availability of SID in Subject Table  
	public Boolean isSubjectAvailable(String sid) throws SQLException {
				this.stmt=this.conn.createStatement();
				String selectQuery="select * from "+Subject.SUBJECT_INFO_TABLE_NAME+" where "+Subject.SUBJECT_ID+"='"+sid+"'";
				ResultSet rs= stmt.executeQuery(selectQuery);
				
				boolean flag=false;		
				  //returns true if a row was selected in the rs object
				  if(rs.next()) {
					  flag=true;
				  }
				return flag;
		}
	
	public void tester() throws SQLException, ClassNotFoundException {
		
		/*System.out.println(this.isLabAvailable("1.0/BFSI/Introduction o C/301.0/3.0/"));*/ //new islabavailable
		
		/*System.out.println(this.isCreditAvailable(1,"1.0/BFSI/Introduction to C/301.0/3.0/"));*/
		
		/*System.out.println(this.isLabAvailable("1.0/BFSI/Introduction to C/301.0/3.0/"));*/
		
		/*System.out.println(this.isSameBranchAndSemAvailable("8.0/BAO/Insurance/502.0/100.0/",1));*/
		
		/*System.out.println(this.isSubjectAvailable("8.0/BFSI/Insurance/502.0/100.0/"));*/
	}
}
