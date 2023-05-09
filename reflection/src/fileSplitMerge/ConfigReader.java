package fileSplitMerge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigReader {

	public static void main(String[] args) throws IOException {
		
		//read configuration file
		File configFile = new File("/Users/haoshe/Desktop/splitDir/config.properties");
		readConfigFile(configFile);
	}
	
	public static void readConfigFile(File configFile) throws IOException {
		
		//BufferedReader reads the file line by line
		BufferedReader bufReader = new BufferedReader(new FileReader(configFile));
		
		String line = null;
		while((line=bufReader.readLine()) != null) {
			
			//split each line based on "="  partcount=2
			String[] arr = line.split("=");
			
			//there are some other sentences in the configuration file 
			//but we only need filename=... partcount=..., and ignore the other stuff
			if(line.startsWith("filename")) {
				System.out.println("filename: "+arr[0]);
			}else if(line.startsWith("partcount")) {
				System.out.println("parcount: "+arr[1]);
			}
		}
		
		bufReader.close();
	}
}
