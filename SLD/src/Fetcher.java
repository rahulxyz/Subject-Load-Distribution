import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Fetcher {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/base";

	static final String USER = "root";
	static final String PASS = "1234";
	   
	   
	Connection conn;
	Statement stmt;
    PreparedStatement ps;
    
    
	public Fetcher() throws ClassNotFoundException, SQLException {	
	    this.conn = Distributer.conn;
	}
    
	/*public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Fetcher obj=new Fetcher();
		Class.forName(JDBC_DRIVER);		
	    obj.conn = DriverManager.getConnection(DB_URL,USER,PASS);
	    String sid="1.0/BFSI/Banking/303.0/2.0/";
	    String sid2="8.0/BFSI/Insurance/502.0/1.0/";	    
	    //System.out.println(obj.isAvailable(sid)+" \n"+obj.isAvailable(sid2));
	   // obj.distribute();
	    obj.tester();
	}*/
	
	//to get the Sem/Branch/SubjectName 
	public String getSubjectNameForLab(String sid) {
		String temp="";
		int c=0,pos=sid.length();
		for(int i=0;i< sid.length();i++) {
			if(sid.charAt(i)== '/'){
					c++;
					if(c==3) {
						pos=i;
						break;
					}
			}			
		}
		temp= sid.substring(0,pos);
		return temp;
	}
	

	//to get Sem+branch substring from given sid
	public String getSemAndBranchOfSid(String sid) {
		String temp="";
		int c=0,pos=sid.length();
		for(int i=0;i< sid.length();i++) {
			if(sid.charAt(i)== '/'){
					c++;
					if(c==2) {
						pos=i;
						break;
					}
			}			
		}
		temp= sid.substring(0,pos+1);
		return temp;
	}
	
	//returns credit of given Sid
	public Double getCreditOfSid(String sid) {
		String c="";
    	int pos=0;
    	int l= sid.length();
    	boolean flag=true;
    	// l-2 because last element is '/' 
    	for(int i=l-2; i>=0; i--) {
    		if(sid.charAt(i)=='/') {
    			pos=i;
    			flag=false;
    			break;
    		//System.out.print(data.charAt(i));
    		}
    	}
    	if(flag)
    		System.out.println("Credit was not assigned properly in AssignCreditToDesignation");
    	
    	c= sid.substring(pos+1,l-1);
    	
    	return Double.parseDouble(c);
	}
	
	// to get the leftmost column in subject table which has null 
	public int getColumnIndexFinalFaculty(int row) throws SQLException {
			ResultSet rs= this.getSpecificRowFinalFaculty(row);
			int index=0;
			for(int i=Faculty.Subject1;i<=Faculty.Subject10;i++) {
				if(rs.getString(i) == null) {
					index= i;
					break;
				}
			}
			return index;
	}
	
	//to get specific row from table FinalFaculty 
	public ResultSet getSpecificRowFinalFaculty(int row) throws SQLException {
		ResultSet rs=this.getFinalFaculty();
		rs.absolute(row);
		return rs;
	}
	
	//to get specific row from table FacultySorted 
    public ResultSet getSpecificRowFacultySorted(int row) throws SQLException {
		ResultSet rs=this.getFacultySorted();
		rs.absolute(row);
		return rs;
	}
	
    public ResultSet getLab() throws SQLException{
    	this.stmt=this.conn.createStatement();
		String selectQuery="select * from "+Subject.LAB_INFO_TABLE_NAME;
		ResultSet rs= stmt.executeQuery(selectQuery);
		return rs;
    }
    
    public ResultSet getSubject() throws SQLException{
    	this.stmt=this.conn.createStatement();
		String selectQuery="select * from "+Subject.SUBJECT_INFO_TABLE_NAME;
		ResultSet rs= stmt.executeQuery(selectQuery);
		return rs;
    }
    
	//to get FinalFaculty rows
	public ResultSet getFinalFaculty() throws SQLException {
		this.stmt=this.conn.createStatement();
		String selectQuery="select * from "+Faculty.FINALFACULTY_INFO_TABLE_NAME;
		ResultSet rs= stmt.executeQuery(selectQuery);
		return rs;
	}
	
	 //to get FacultySorted table 
    public ResultSet getFacultySorted() throws SQLException {
		this.stmt=this.conn.createStatement();
		String selectQuery="select * from "+Faculty.FACULTY_INFO_TABLE_NAME_Sorted;
		ResultSet rs= stmt.executeQuery(selectQuery);
		return rs;
	}

    public void tester() throws SQLException {
    	
    	System.out.println(this.getSubjectNameForLab("8.0/BFSI/Insurance/502.0/100.0/"));
    	
    	/*System.out.println(this.getSemAndBranchOfSid("8.0/BFSI/Insurance/502.0/100.0/"));*/
    	
    	/*System.out.println(this.getCreditOfSid("8.0/BFSI/Insurance/502.0/100.0/"));*/
    	
    	/*System.out.println(this.getColumnIndexFinalFaculty(1));*/
    	
    	/*System.out.println(this.isAvailable("8.0/BFSI/Insurance/502.0/1.0/"));*/
    	
    	/*ResultSet rs=this.getSpecificRowFinalFaculty(2);
	    System.out.println(rs.getString(2));*/
    	
    	/* ResultSet rs=this.getSpecificRowFacultySorted(1);
	     System.out.println(rs.getString(2));*/
    	
    	/*ResultSet rs=this.getFinalFaculty();
    	while(rs.next())
	     System.out.println(rs.getString(4));*/
    	
    	/*ResultSet rs=this.getFacultySorted();
    	while(rs.next())
	     System.out.println(rs.getString(4));*/
    }
}
