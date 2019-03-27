/**
 * Created on Feb 26, 2019 
 * 
 * @author Navin Raman
 */

package schooldomain.studentname.connecteddevices.labs.module08;

import java.util.Random;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import schooldomain.studentname.connecteddevices.labs.module08.MqttClientConnector;

/**
 * This class is used to publish message using MQTT protocol
 * 
 * @variable mqttClient: MQTT connector helper class instance
 * @variable pubApp: MQTT publisher class self instance
 */
public class TempSensorPublisherApp {
	
	private MqttClientConnector mqttClient;
	private static TempSensorPublisherApp pubApp;
	
	private String _host 		= ConfigConst.DEFAULT_UBIDOTS_SERVER;
	private String _authToken 	= "A1E-yvddAEn7t9o8qfPVDV6auJywPyoFcZ";
	private String _pemFileName = "C:\\Test\\workspace\\iot-gateway\\src\\main\\java\\schooldomain\\studentname\\connecteddevices\\labs\\module08\\"
			+ ConfigConst.UBIDOTS + ConfigConst.CERT_FILE_EXT;
	
	public static final String UBIDOTS_VARIABLE_LABEL 	= "/temperature";
	public static final String UBIDOTS_DEVICE_LABEL 	= "/demo";
	public static final String UBIDOTS_TOPIC_DEFAULT 	= "/v1.6/devices" + UBIDOTS_DEVICE_LABEL + UBIDOTS_VARIABLE_LABEL;

	// Constructor 
	public TempSensorPublisherApp() {
		super();
	}

	/**
	 * This method is used to generate a random temperature value
	 * 
	 * @param minimum: minimum value of temperature
	 * @param maximum: maximum value of temperature
	 * @return random string value temperature
	 */
	public String generateRandomvalue(float minimum, float maximum) {
		Random r = new Random();
		float random = minimum + r.nextFloat() * (maximum - minimum);
		return Float.toString(random);
	}

	/**
	 * This method is used to start, connect the MQTT publisher and 
	 * the publish message
	 * 
	 * @param topicName: It is the name of the MQTT session topic
	 */
	public void start(String topicName) {
		float minimum = 0f;
		float maximum = 45f;
		
		try {
			mqttClient = new MqttClientConnector(_host, _authToken, _pemFileName);
			mqttClient.connect();
			while (true) {
				mqttClient.publishMessage(topicName, ConfigConst.DEFAULT_QOS_LEVEL,
						generateRandomvalue(minimum, maximum).getBytes());
				Thread.sleep(60000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			mqttClient.disconnect();
			e.printStackTrace();
		}
	}

	/**
	 * Main method of MQTT publisher app
	 * 
	 * @param args: arguments list
	 */
	public static void main(String[] args) {
		pubApp = new TempSensorPublisherApp();
		
		try {
			pubApp.start(UBIDOTS_TOPIC_DEFAULT);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}