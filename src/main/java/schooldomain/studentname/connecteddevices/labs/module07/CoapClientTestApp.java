/**
 * @author: Navin Raman
 */
package schooldomain.studentname.connecteddevices.labs.module07;

import java.io.IOException;

import schooldomain.studentname.connecteddevices.common.DataUtil;
import schooldomain.studentname.connecteddevices.common.SensorData;

/**
 * This application is used to connect to the CoAP server and runs several
 * tests
 */
public class CoapClientTestApp {
	public static void main(String[] args) throws IOException
	{		
		runTests();
	}
	
	/**
	 * This method is used to run tests on the CoAP server with the GET, PUT,
	 * POST and DELETE actions
	 * 
	 * @throws IOException
	 */
	public static void runTests() throws IOException
	{
		System.out.println("Starting the client...");
		CoapClientConnector coapClient = new CoapClientConnector("coap://127.0.0.1/temperature");
				
		SensorData data = new SensorData(30.0,0.0,"time","Temperature");
		data.updateValue(4);
		DataUtil dat = new DataUtil();
		System.out.println(data);
		
		//for Pinging the server
		coapClient.ping();
		
		/**
		 * Posting to the server to initialize the data, 
		 * and then get the data back as JSON data
		 */
		coapClient.get();
		coapClient.post(dat.SensorDataToJson(data));
		coapClient.get();
		
		//for Updating the resource on server
		data.updateValue(5);
		System.out.println(data);
		coapClient.put(dat.SensorDataToJson(data));
		coapClient.get();
		
		//for Deleting the data
		coapClient.delete();
		coapClient.get();
	}
}