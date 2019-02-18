package schooldomain.studentname.connecteddevices.labs.module05;

import java.io.*;

//This class is used for File reading and file writing
public class FileTransaction {

	/*
	 * This method is used to read the JSON string data from the text file
	 * Exception handling is used to separate the error handling the code
	 * from the regular code and propagation errors up to call stack
	 * 
	 * @param file: file as argument, to retrieve the JSON data
	 * @return : JSON string 
	 */
	public static String FileReader(String file)
	{
		String json = new String();
		try
		{
			FileReader fr = new FileReader(file);
			int ch;
			while((ch=fr.read())!=-1)
			{
				json = json + (char)ch;
			}
			fr.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return json;
	}
	
	/*
	 * This method is used to write the JSON string data
	 * 
	 * @param fileWriteEnable: write the data from the sensor to JSON string
	 * @param file: data for the file to be written
	 * @param json: JSON string
	 */
	public static void fileWrite(String fileWriteEnable, String file, String json)
	{
		File jsonFile = new File(file);
		try 
		{	
			jsonFile.createNewFile();
			FileWriter writer = new FileWriter(jsonFile);
			writer.write(json);
			writer.flush();
			writer.close();	
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
