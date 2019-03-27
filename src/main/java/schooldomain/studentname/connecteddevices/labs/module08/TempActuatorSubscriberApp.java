/**
 * Created on Feb 26, 2019
 * 
 * @author Navin Raman
 */
package schooldomain.studentname.connecteddevices.labs.module08;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import schooldomain.studentname.connecteddevices.labs.module08.MqttClientConnector;

/**
 * This class is used to receive message using MQTT protocol
 * 
 * @variable subApp: MQTT subscriber class self instance
 * @variable mqttClient: MQTT connector helper class instance
 */
public class TempActuatorSubscriberApp {

	private MqttClientConnector mqttClient;
	private static TempActuatorSubscriberApp subApp;
	
	private String _authToken 	= "A1E-yvddAEn7t9o8qfPVDV6auJywPyoFcZ";
	private String _host 		= ConfigConst.DEFAULT_UBIDOTS_SERVER;
	private String _pemFileName = "C:\\Test\\workspace\\iot-gateway\\src\\main\\java\\schooldomain\\studentname\\connecteddevices\\labs\\module08\\"
			+ ConfigConst.UBIDOTS + ConfigConst.CERT_FILE_EXT;
	
	public static final String UBIDOTS_VARIABLE_LABEL 	= "/tempactuator";
	public static final String UBIDOTS_DEVICE_LABEL 	= "/demo";
	public static final String UBIDOTS_TOPIC_DEFAULT 	= "/v1.6/devices" + UBIDOTS_DEVICE_LABEL + UBIDOTS_VARIABLE_LABEL + "/lv";

	//constructor
	public TempActuatorSubscriberApp() {
		super();
	}

	/**
	 * This method is used to start, connect the MQTT subscriber and receive
	 * message
	 * 
	 * @param topicName: name of the MQTT session topic
	 */
	public void start(String topicName) {
		try {
			
			mqttClient = new MqttClientConnector(_host, _authToken, _pemFileName);
			mqttClient.connect();
			
			while (true) {
				mqttClient.subscribeToTopic(topicName);
				// minimum wait time which is 60 seconds
				Thread.sleep(60000); 
			}
		} catch (InterruptedException e) {
			
			mqttClient.disconnect();
			e.printStackTrace();
		}
	}

	/**
	 * Main method for MQTT subscriber class
	 * 
	 * @param args: arguments list
	 */
	public static void main(String[] args) {
		subApp = new TempActuatorSubscriberApp();
		
		try {
			subApp.start(UBIDOTS_TOPIC_DEFAULT);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}