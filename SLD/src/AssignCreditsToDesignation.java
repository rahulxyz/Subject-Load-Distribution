import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


//Excel sheet should never have blank cell under the three columns
public class AssignCreditsToDesignation {

	
	//if there are 6 designation then we write credit array like this 
	public String[] Credit= {"0","1","2","3","4","5","6"};
	//if the number of designation is increased
	//edit [] Credit={"0","1","2","3","4","5","6","7"}; and so on
	
	public void read() throws IOException {
		String fileName= "CreditsRatio.xls";
		this.assignCredit(this.read(fileName));
	}
	
	public void readAndDisplay() throws IOException {
		 String fileName= "CreditsRatio.xls";
		 
		 this.assignCredit(this.read(fileName));
		 this.display();
	 }
	
	public void display() {
		for(int i=1;i<this.Credit.length;i++)
			System.out.println(this.Credit[i]);
	}
	
	 public void assignCredit(String[] uniqueArray) {
		 int currRow,currCol;
		 int rowTotal=uniqueArray.length;
		 for( currRow=1; currRow < rowTotal; currRow++ ){
			 this.Credit[currRow]= getCredit(uniqueArray[currRow-1]);
	      }
	 }
	
	 public String[] read(String fileName) throws IOException {

		    String path = "E:/other/Sub_empty/All Subjects/";
		    File localFile= new File(path+fileName);
	        FileInputStream fis=new FileInputStream(localFile);
	        HSSFWorkbook wb=new HSSFWorkbook(fis);
	        HSSFSheet sheet=wb.getSheetAt(0);

	        int rowTotal,colTotal;
	        //rows start with 0 and column with 1
	        // add 1 to getRow and dont add in getCol
	        //but reading index start from 0 itself
	        rowTotal = sheet.getLastRowNum() + 1;
	        colTotal = sheet.getRow(sheet.getFirstRowNum()).getLastCellNum(); // im getting first row and finding the number of columns in it
	        String[][] subjectArray = new String[rowTotal][colTotal];
	        int currRow,currCol;
	        for( currRow=0; currRow < rowTotal; currRow++ ){
	            HSSFRow row= sheet.getRow(currRow);
	            for( currCol=0; currCol < colTotal; currCol++ ){
	                //Log.d(FacultyInfo.TAG,currRow+" - "+currCol);
	                HSSFCell cell= row.getCell(currCol);
	                String value=cellToString(cell);
	                subjectArray[currRow][currCol]= value;
	            }
	        }

	        String[] uniqueArray= new String[subjectArray.length];
	        for( currRow=1; currRow < rowTotal; currRow++ ){
	        	String temp="";
	            for( currCol=0; currCol < colTotal; currCol++ ) {
	                temp = temp.concat(subjectArray[currRow][currCol]+"/");
	                uniqueArray[currRow-1]=temp;
	            }
	            //System.out.println("Data-> "+uniqueArray[currRow-1]);
	        }

	        
	        return uniqueArray;
	    }

	  /*  To check which data type is present in each cell of .xls file*/
	    public static String cellToString(HSSFCell cell){
	        int type;
	        Object result;
	        type= cell.getCellType();
	        switch(type){
	            case 0:
	                result= cell.getNumericCellValue();
	                break;
	            case 1:
	                result=cell.getStringCellValue();
	                break;
	            default:
	                throw new RuntimeException("This cell type not supported: Make sure it is numeric or string");

	        }
	        return result.toString();
	    }
	
	    public String getCredit(String data) {
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
	    	
	    	return c;
	    }
	
	    public AssignCreditsToDesignation() throws IOException {
	    	this.read();
	    }
	    
	public static void main(String[] args) throws IOException {
		AssignCreditsToDesignation obj= new AssignCreditsToDesignation();
		//obj.readAndDisplay();
		System.out.println("Number-> "+obj.Credit[4]);
	}

}
