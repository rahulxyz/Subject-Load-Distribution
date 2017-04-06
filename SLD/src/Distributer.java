import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Distributer {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/base";

	static final String USER = "root";
	static final String PASS = "1234";
	   
	   
	public static Connection conn;
	Statement stmt;
   	PreparedStatement ps;
    
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Distributer obj= new Distributer();
		Class.forName(JDBC_DRIVER);		
	    	obj.conn = DriverManager.getConnection(DB_URL,USER,PASS);
		obj.tester();
		
		System.out.println("\nDistribution Completed");
	}
	
	//this will initiate the distribution
	public void distribute() throws ClassNotFoundException, SQLException {
		this.distributeIn(Faculty.CORE_1,Faculty.CORE_5);
		this.distributeIn(Faculty.DOMAIN_1,Faculty.DOMAIN_5);
	}
	
	//to start distribution from particular priority column to particular priority column
	public void distributeIn(int start, int last) throws ClassNotFoundException, SQLException {
		this.sweepColumnWise(start, last);
	}
	
	//final algorithm application
	public void applyAlgo(int row,int column,int last) throws ClassNotFoundException, SQLException {
		Fetcher fetch= new Fetcher();
		ResultSet rs= fetch.getSpecificRowFacultySorted(row);
		String sid=rs.getString(column);
		if(sid.contains("Priority")) {
			System.out.println("Null data-> "+sid);
			return;
		}
		Checker check= new Checker();
		String lid=null;
		Alloter allot=new Alloter();
		System.out.println("Checking-> "+sid);
		if(check.isSubjectAvailable(sid)){
			System.out.println("Subject is available");
			if(check.isCreditAvailable(row, sid)) {
				System.out.println("Credit is available");
				if(check.isSubjectHoursAvailable(row, sid)) {
					System.out.println("SubjectHours is available");	
					if(!check.isSameBranchAndSemAvailable(sid, row)) {
					allot.allotSubject(row, sid);
					System.out.println("Alloted subject->"+sid);
					while((lid=check.isLabAvailable(sid))!=null) {
						System.out.println("Lab Available");
						if(check.isCreditAvailable(row, lid)) {
							if(check.isLabHoursAvailable(row, lid)) {
								System.out.println("Lab hours Available");
								allot.allotLab(row, lid);
								System.out.println("Alloted lab->"+lid);
							}
						}else {
							break;
						}
					}
				}
			}	
			}
		}else {
			System.out.println("SweepingRowWise");
				this.sweepRowWise(row, column+1, last);
		}
		System.out.println();
		
	}
	
	//move through sid of a particular row horizontally
	public void sweepRowWise(int row,int start, int last) throws ClassNotFoundException, SQLException {
		if(last<start) {
		System.out.println("End Reached");	
			return;
		}
		Fetcher fetch= new Fetcher();
		ResultSet rs= fetch.getSpecificRowFacultySorted(row);
		
		for(int column=start; column<=last; column++) {
			/*this is where you get sid column wise*/
			this.applyAlgo(row, column, last);
		}
	}
	
	//moves through sid's in facultysorted column wise 
	public void sweepColumnWise(int start, int last) throws ClassNotFoundException, SQLException {
		Fetcher fetch= new Fetcher();
		ResultSet rs= fetch.getFacultySorted();
		
		for(int column=start; column<=last; column++) {
			rs.beforeFirst();
			while(rs.next()) {
				/*this is where you get sid column wise
				System.out.println(rs.getString(column));*/
				this.applyAlgo(rs.getRow(),column,last);
			}
			System.out.println();
			
		}
	}
	
	public void tester() throws ClassNotFoundException, SQLException {
		
		this.distribute();
		
		/*this.sweepRowWise(2, 1, 5);*/
		
		/*this.sweepColumnWise(Faculty.CORE_1, Faculty.CORE_5);*/
	}

}
