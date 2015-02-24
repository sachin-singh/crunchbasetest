package crunchbase;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import com.csvreader.CsvWriter;

public class CSVWriter {
	
	static boolean writeCSV(String[] headers, Map<String,String> data, String outputfileName){
		
		
		boolean alreadyExists = new File(outputfileName).exists(); 
		try
		{
			CsvWriter csvOutput = new CsvWriter(new FileWriter(outputfileName, true), ',');
			 
			if(!alreadyExists){
				for(int i=0;i<headers.length;i++){
					csvOutput.write(headers[i]);
				}
				csvOutput.endRecord();
			}  
			
			for(int i=0;i<headers.length;i++){
				csvOutput.write(data.get(headers[i]));
			} 
			csvOutput.endRecord();
			csvOutput.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		} 	
	}

}
