import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Produce {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/base";

	static final String USER = "root";
	static final String PASS = "1234";
	   
	   
	Connection conn;
	Statement stmt;
	PreparedStatement ps;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		Produce p= new Produce();
		Class.forName(JDBC_DRIVER);		
	    p.conn = DriverManager.getConnection(DB_URL,USER,PASS);	
	   
	    //p.writeFacultySorted(Files.OUTPUT_FOR_FACULTY_SORTED);
	     p.writeFinalFaculty(Files.OUTPUT_FOR_FINALFACULTY);
	    p.writeSubject(Files.OUTPUT_FOR_SUBJECT);
	    p.writeLab(Files.OUTPUT_FOR_LAB);
	    p.writeCreditsAvailable(Files.OUTPUT_FOR_CREDITS_AVAILABLE);
	}
	
	//Lab left
		public void writeCreditsAvailable(String destination) throws SQLException, ClassNotFoundException, IOException {
			FileOutputStream fileOut = new FileOutputStream(new File(destination));
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("SLD Worksheet");
			

			this.stmt=this.conn.createStatement();
			String selectQuery="select * from "+Faculty.FACULTY_INFO_TABLE_NAME_Sorted+" where credits>0";
			ResultSet rs= stmt.executeQuery(selectQuery);
			
			HSSFRow row;
			HSSFCell cell;
			row= worksheet.createRow(0);
			cell= row.createCell(0);
			cell.setCellValue("Sapid");
			cell= row.createCell(1);
			cell.setCellValue("Name");
			cell= row.createCell(2);
			cell.setCellValue("Credits Available");
			
			int j=1;
			row= worksheet.createRow(j++);
			while(rs.next()) {
				row= worksheet.createRow(j++);
				for(int i=1;i<=2;i++) {
				cell= row.createCell(i-1);
				cell.setCellValue(rs.getString(i));
				}
				cell= row.createCell(2);
				cell.setCellValue(rs.getString(5));
			}
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			System.out.println("done");
			
		}
	
	//Lab left
	public void writeLab(String destination) throws SQLException, ClassNotFoundException, IOException {
		FileOutputStream fileOut = new FileOutputStream(new File(destination));
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet worksheet = workbook.createSheet("SLD Worksheet");
		
		this.stmt=this.conn.createStatement();
		String selectQuery="select * from "+Subject.LAB_INFO_TABLE_NAME;
		ResultSet rs= stmt.executeQuery(selectQuery);
		
		HSSFRow row;
		HSSFCell cell;
		row= worksheet.createRow(0);
		cell= row.createCell(0);
		cell.setCellValue("Semester");
		cell= row.createCell(1);
		cell.setCellValue("Program");
		cell= row.createCell(2);
		cell.setCellValue("Lab");
		cell= row.createCell(3);
		cell.setCellValue("Lab Id");
		cell= row.createCell(4);
		cell.setCellValue("Credits");
		
		int j=1;
		row= worksheet.createRow(j++);
		while(rs.next()) {
			row= worksheet.createRow(j++);
			String[] array= getDetails(rs.getString(1));
			for(int k=0;k<5;k++) {
				System.out.println(array[k]);
				cell= row.createCell(k);
				cell.setCellValue(array[k]);
			}
			
		}
		
		workbook.write(fileOut);
		fileOut.flush();
		fileOut.close();
		System.out.println("done");
		
		
	}

	
	//subject left
	public void writeSubject(String destination) throws SQLException, ClassNotFoundException, IOException {
		FileOutputStream fileOut = new FileOutputStream(new File(destination));
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet worksheet = workbook.createSheet("SLD Worksheet");
		
		this.stmt=this.conn.createStatement();
		String selectQuery="select * from "+Subject.SUBJECT_INFO_TABLE_NAME;
		ResultSet rs= stmt.executeQuery(selectQuery);
		
		HSSFRow row;
		HSSFCell cell;
		row= worksheet.createRow(0);
		cell= row.createCell(0);
		cell.setCellValue("Semester");
		cell= row.createCell(1);
		cell.setCellValue("Program");
		cell= row.createCell(2);
		cell.setCellValue("Subject");
		cell= row.createCell(3);
		cell.setCellValue("Subject Id");
		cell= row.createCell(4);
		cell.setCellValue("Credits");
		
		int j=1;
		row= worksheet.createRow(j++);
		while(rs.next()) {
			row= worksheet.createRow(j++);
			String[] array= getDetails(rs.getString(1));
			for(int k=0;k<5;k++) {
				System.out.println(array[k]);
				cell= row.createCell(k);
				cell.setCellValue(array[k]);
			}
			
		}
		
		workbook.write(fileOut);
		fileOut.flush();
		fileOut.close();
		System.out.println("done");
		
		
	}
	
	
	
	public void writeFinalFaculty(String destination) throws IOException, SQLException, ClassNotFoundException {
			//destination= path+ fileName
			FileOutputStream fileOut = new FileOutputStream(new File(destination));
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("SLD Worksheet");
			
			/*Fetcher fetch=new Fetcher();
			ResultSet rs= fetch.getFinalFaculty();*/
			this.stmt=this.conn.createStatement();
			String selectQuery="select * from "+Faculty.FINALFACULTY_INFO_TABLE_NAME;
			ResultSet rs= stmt.executeQuery(selectQuery);
			
			HSSFRow row;
			HSSFCell cell;
			row= worksheet.createRow(0);
			cell= row.createCell(0);
			cell.setCellValue("Sapid");
			cell= row.createCell(1);
			cell.setCellValue("Name");
			cell= row.createCell(2);
			cell.setCellValue("Semester");
			cell= row.createCell(3);
			cell.setCellValue("Program");
			cell= row.createCell(4);
			cell.setCellValue("Subject");
			cell= row.createCell(5);
			cell.setCellValue("Subject Id");
			cell= row.createCell(6);
			cell.setCellValue("Credits");
			
			row=worksheet.createRow(1);
			int j=2;
			String temp;
			while(rs.next()) {
				row= worksheet.createRow(j++);
				for(int i=1;i<=13;i++) {
					
					if(i>=4) {
						temp=rs.getString(i);
						if(i!=4 && temp!=null)
						row= worksheet.createRow(j++);
						
						
						if(temp==null)
							continue;
						String[] array= getDetails(temp);
						
						for(int k=0;k<5;k++) {
							System.out.println(array[k]);
							cell= row.createCell(2+k);
							cell.setCellValue(array[k]);
						}
					}else {
						//for sapid, name, credit
						cell= row.createCell(i-1);
						cell.setCellValue(rs.getString(i));
					}
					System.out.print(rs.getString(i)+"  ");
				}
				row= worksheet.createRow(j++);
				System.out.println();			
			}
			
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			System.out.println("done");
		
	 }

	public void writeFacultySorted(String destination) throws IOException, SQLException, ClassNotFoundException {
		//destination= path+ fileName
		FileOutputStream fileOut = new FileOutputStream(new File(destination));
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet worksheet = workbook.createSheet("SLD Worksheet");
		
		this.stmt=this.conn.createStatement();
		String selectQuery="select * from "+Faculty.FACULTY_INFO_TABLE_NAME_Sorted;
		ResultSet rs= stmt.executeQuery(selectQuery);
		
		HSSFRow row;
		HSSFCell cell;
		row= worksheet.createRow(0);
		cell= row.createCell(0);
		cell.setCellValue("Sapid");
		cell= row.createCell(1);
		cell.setCellValue("Name");
		cell= row.createCell(2);
		cell.setCellValue("Credits Of Faculty");
		cell= row.createCell(3);
		cell.setCellValue("Semester");
		cell= row.createCell(4);
		cell.setCellValue("Program");
		cell= row.createCell(5);
		cell.setCellValue("Subject");
		cell= row.createCell(6);
		cell.setCellValue("Subject Id");
		cell= row.createCell(7);
		cell.setCellValue("Credits Of Subject");
		
		row=worksheet.createRow(1);
		int j=2;
		String temp;
		while(rs.next()) {
			row= worksheet.createRow(j++);
			for(int i=1;i<=15;i++) {
				
				if(i>=6) {
					temp=rs.getString(i);
					if(i!=6 && temp!=null)
					row= worksheet.createRow(j++);
					
					
					if(temp==null)
						continue;
					String[] array= getDetails(temp);
					
					for(int k=0;k<5;k++) {
						System.out.println(array[k]);
						cell= row.createCell(3+k);
						cell.setCellValue(array[k]);
					}
				}else {
					//for sapid, name, credit
					if(i==1||i==2) {
					cell= row.createCell(i-1);
					cell.setCellValue(rs.getString(i));
					}
					if(i==5) {
						cell= row.createCell(2);
						cell.setCellValue(rs.getString(i));
						}
				}
				System.out.print(rs.getString(i)+"  ");
			}
			row= worksheet.createRow(j++);
			System.out.println();			
		}
		
		workbook.write(fileOut);
		fileOut.flush();
		fileOut.close();
		System.out.println("done");
	
 }

	
	public String[] getDetails(String data) {
		String[] array=new String[5];
		String temp="";
		int count=0;
		for(int i=0;i< data.length();i++) {
			if(data.charAt(i)=='/') {
				array[count++]=temp;
				temp="";
			}else {
				temp=temp+data.charAt(i);
			}
		}
		return array;
	}

}
