import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelHandler {
		
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ExcelHandler e=new ExcelHandler();
		e.readAndDisplay();
	}
	
	 
	
	 public void readAndDisplay() throws IOException {
		 String[] fileList= {"Department Of Analytics.xls",
				 			 "Department Of Analytics Lab.xls"};
		 
		 for(int i=0;i< fileList.length;i++)
		 this.display(this.read(fileList[i]));
	 }
	 
	
	 public void display(String[] uniqueArray) {
		 int currRow,currCol;
		 int rowTotal=uniqueArray.length;
		 for( currRow=1; currRow < rowTotal; currRow++ ){
	        	System.out.println("Data-> "+uniqueArray[currRow-1]);
	        }
	 }
	
	  public String[] read(String fileName) throws IOException {

	       /* File path = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DCIM + "/subjectList/");
	        File localFile= new File(path,fileName);*/
		    String path = Files.INPUT_PATH_FOR_UNIQUE_SUBJECT;
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

	        String[] uniqueArray= new String[subjectArray.length-1];
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
	
	

}
