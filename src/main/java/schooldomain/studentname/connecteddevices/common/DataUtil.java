package schooldomain.studentname.connecteddevices.common;

import com.google.gson.Gson;

import schooldomain.studentname.connecteddevices.labs.module05.FileTransaction;

/*
 * This class is used to retrieve the JSON string data from the text file
 * which is created by the FileWrite method to a sensor readable data
 * this function is done by the FileReader method
 */
public class DataUtil {

	/*
	 * This method will accept a SensorData object as a parameter, convert 
	 * and then return its contents as a JSON string
     *
     * @param SensorData: data from the sensor
     * @return: returning the JSON instance 
	 */
	public String SensorDataToJson(SensorData sensordata)
	{
		String jsonSd;
		Gson gson = new Gson();
		jsonSd = gson.toJson(sensordata);
		return jsonSd;
	}
	
	/*
	 * This method will accept a JSON Data and filename object as a parameter,
	 * convert and then return its contents as a sensor data
     *
     * @param jsondata: data from the JSON string
     * @param filename: name of the file
     * @return: returning the Sensor data
	 */
	public SensorData JsonToSensorData(String jsondata,String filename)
	{
		SensorData sensorData=null;
		
		if(filename==null)
		{	
			Gson gson = new Gson();
			sensorData = gson.fromJson(jsondata, SensorData.class);
			return sensorData;
		}
		
		else
		{
			Gson gson = new Gson();
			String data = FileTransaction.FileReader(filename);
			sensorData = gson.fromJson(data, SensorData.class);
			return sensorData;	
		}
	}
}
