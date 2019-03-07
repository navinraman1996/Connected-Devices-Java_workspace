package schooldomain.studentname.connecteddevices.labs.module06;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import schooldomain.studentname.connecteddevices.common.DataUtil;
import schooldomain.studentname.connecteddevices.common.SensorData;

public class MqttSubClientTestApp {
	
	private static final Logger logger = Logger.getLogger(MqttSubClientTestApp.class.getName());
	private static MqttSubClientTestApp app;
	private MqttClientConnector mqttClient;
	
	/*
	 * Constructor
	 */
	public MqttSubClientTestApp()
	 {
	 super();
	 }
	
	/*
	 * This method is used to initialize the subscribe action
	 * 
	 * @param topicName: Topic to be subscribed
	 */
	public void start(String topicName)
	{
		mqttClient = new MqttClientConnector();
		mqttClient.connect();
		mqttClient.subscribeToTopic(topicName);
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mqttClient.disconnect();
	}

	/*
	 * Main method, where the Topic name is set,
	 * Retrieving of JSON data, 
	 * and converting the JSON data to sensor data,
	 * and again converting the sensor data to JSON data.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		app = new MqttSubClientTestApp();
		String topic = "Temperature Sensor";

		try
		{
			app.start(topic);
			String message = MqttClientConnector.getMessag();
			logger.info("Received Json Data\n");
			System.out.println("Received Json Message is :" + "\n" + message + "\n");
			DataUtil data = new DataUtil();								
			SensorData sensor = data.JsonToSensorData(message,null);
			logger.info("Printing the Sensor Data:\n");
			System.out.println("After the Json data to Sensor data Conversion:\n" + sensor);
			String json = data.SensorDataToJson(sensor);
			logger.info("Printing the Json Data:\n");
			System.out.println("After Sensor data to Json data Conversion:\n" + json);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}