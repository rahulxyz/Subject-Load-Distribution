import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TrimJSONfile {

	public void trim() throws FileNotFoundException, IOException, ParseException {

		 JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(Files.INPUT_FOR_TRIMJSON));
        JSONObject jsonObject = (JSONObject) obj;
        
        JSONObject output=new JSONObject();
        JSONObject dataNew=new JSONObject(); 
        for (Object facultyObject : jsonObject.keySet()) {
       	 String sapid = (String)facultyObject;
            Object keyObject = jsonObject.get(sapid);
            JSONObject dataObject=(JSONObject)keyObject;
            for(Object subjectsObject : dataObject.keySet()) {
            	String key = (String)subjectsObject;
                Object data = dataObject.get(key);
               //Print key and value
                dataNew = (JSONObject) data;
               }
            output.put(sapid, dataNew);
                
           	try(FileWriter file = new FileWriter(Files.OUTPUT_FOR_TRIMJSON)){
    			file.write(output.toJSONString());
    			file.flush();
           	}
            
        }
        System.out.println("trim done");
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		TrimJSONfile obj= new TrimJSONfile();
		obj.trim();

	}

	}
