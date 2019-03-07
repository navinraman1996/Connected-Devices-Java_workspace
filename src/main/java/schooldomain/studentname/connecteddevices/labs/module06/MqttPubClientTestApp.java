package schooldomain.studentname.connecteddevices.labs.module06;

import java.util.logging.Logger;

import schooldomain.studentname.connecteddevices.common.DataUtil;
import schooldomain.studentname.connecteddevices.common.SensorData;

public class MqttPubClientTestApp {
	
	private static final Logger logger = Logger.getLogger(MqttPubClientTestApp.class.getName());
	private static MqttPubClientTestApp SubApp;
	private MqttClientConnector mqttClient;
	public 	SensorData sensorData;
	public 	DataUtil dataUtil;

	/*
	 * Constructor
	 */
	public 	MqttPubClientTestApp() {
		super();
	}
	
	/*This method is used to create the JSON data from SensorData
	 * 
	 * @param sensorData: variable of type SensorData
	 * @return: JSON object
	 */
	public String createJSON(SensorData sensorData) {
		dataUtil = new DataUtil();
		String SJobject = dataUtil.SensorDataToJson(setSensorData(sensorData));
		return SJobject;
	}
	
	/*
	 * This function is used to intialize or to update the Sensor Data
	 * 
	 * @param sensorData: It is the variable of SensorData Class
	 * @return: to Update SensorData variable
	 */
	public SensorData setSensorData(SensorData sensor_data) {
		sensor_data.setName("Temperature Sensor");
		sensor_data.updateTimeStamp();
		sensor_data.setCurValue((double)21.81);
		sensor_data.setAvgValue((double)25.67);
		sensor_data.setSampleCount(5);
		sensor_data.setMinValue((double)0);
		sensor_data.setMaxValue((double)30);
		
		return sensor_data;
	}
	
	/*
	 * This Method is used to initialize the publish action
	 * 
	 * @param topicName: Topic of the message
	 */
	public void start(String topicName)
	{
		mqttClient = new MqttClientConnector();
		sensorData = new SensorData(30.0,0.0,"name","Temperature");
		logger.info("SensorData before converting into Json");
		System.out.println(sensorData);
		mqttClient.connect();
		mqttClient.publishMessage(topicName, 2 , createJSON(sensorData).getBytes());
	}
	
	/*
	 * Main Method
	 * 
	 * @param args: Arguments
	 */
	public static void main(String[] args) 
	{
		SubApp = new MqttPubClientTestApp();
		String topic = "Temperature Sensor";
		try {
			SubApp.start(topic);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}