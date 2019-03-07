package schooldomain.studentname.connecteddevices.labs.module06;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import schooldomain.studentname.connecteddevices.common.SensorData;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;

public class MqttClientConnector implements MqttCallback {
	
	private static final Logger logger 	= Logger.getLogger(MqttClientConnector.class.getName());
	private String 	protocol			= ConfigConst.DEFAULT_MQTT_PROTOCOL;
	private String 	host               	= ConfigConst.DEFAULT_MQTT_SERVER;
	private int 	port                = ConfigConst.DEFAULT_MQTT_PORT;
	
	private String 			clientID;
	private String 			brokerAddr;
	private MqttClient 		mqttClient;
	private SensorData 		sensorData;
	private static String 	messag;
	
	/*
	 * Constructor to create an object
	 */
	public MqttClientConnector()
	{
		if(host!=null && host.trim().length()>0)
		{
			this.sensorData = new SensorData(30.0, 0.0, "time", "name");
			//this.host = host;
			this.clientID = mqttClient.generateClientId();
			logger.info("Using client id for broker connection: " + clientID);
			this.brokerAddr = protocol + "://" + host + ":" + port;
			logger.info("Using URL for broker connection: " + brokerAddr);
		}
	}
	
	/*
	 * Method which is used to connect to MQTT broker
	 */
	public void connect()
	{
		if (mqttClient == null) {
			MemoryPersistence persistence = new MemoryPersistence();
		try
		{
			mqttClient = new MqttClient(brokerAddr, clientID, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			logger.info("Connecting to broker: " + brokerAddr);
			mqttClient.setCallback(this);
			mqttClient.connect(connOpts);
			logger.info("connected to broker: " + brokerAddr);
		}
		catch(MqttException ex)
		{
			logger.log(Level.SEVERE, "Failed to connect to broker" + brokerAddr, ex);
		}
		
		}
	}
	
	/*
	 * Method which is used to disconnect from MQTT broker
	 */
	public void disconnect()
	{
		try {
			mqttClient.disconnect();
			logger.info("Disconnect from broker: " + brokerAddr);
		}catch(Exception ex)
		{
			logger.log(Level.SEVERE, "Failed to disconnect from broker: " + brokerAddr , ex);
		}
	}
	
	/* Method which is used to publish message to the MQTT broker
	 * 
	 * @param topic: Topic of the message
	 * @param qosLevel: Quality of Service
	 * @param payload: Message to be sent
	 * @return: Message successfully sent or not(Boolean)
	 */
	public boolean publishMessage(String topic, int qosLevel, byte[] payload) {
		boolean messageSent = false;
		try
		{
			logger.info("Publishing message to topic: " + topic + "payload : " + Arrays.toString(payload));
			MqttMessage msg = new MqttMessage(payload);
			msg.setQos(qosLevel);
			mqttClient.publish(topic, msg);
			logger.info("Message Published " + msg.getId());
			messageSent = true;
		}catch(Exception ex)
		{
			logger.log(Level.SEVERE, "Failed to publish Mqtt message " + ex.getMessage());
		}
		return messageSent;
	}

	/*
	 * Method used to subscribe to a topic
	 *  
	 * @param topic: Topic to be subscribed
	 * @return: Whether the Subscription is success or not(boolean)
	 */
	public boolean subscribeToTopic(String topic)
	{
		boolean success = false;
		try {
			mqttClient.subscribe(topic);
			success = true;
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.Throwable)
	 */
	public void connectionLost(Throwable cause) {
		
		logger.log(Level.WARNING, "Connection to broker lost. Will retry soon.", cause);
	}
	
	/*
	 * Method which is used to Log the message received from MQTT broker
	 * 
	 * (non-Javadoc)
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String, org.eclipse.paho.client.mqttv3.MqttMessage)
	 * 
	 * @param topic: Topic of the message
	 * @param message
	 */
	public void messageArrived(String topic, MqttMessage message) throws Exception 
	{	
		MqttClientConnector.setMessag(message);
		logger.info("Message arrived: " + topic + ", " + message.getId()+"\n");
		System.out.print("Message arrived: " + topic + ", " + message.getId() + "\n" + message + "\n");
	}
	
	/*
	 * Method to show the Delivery complete logger output
	 *
	 * (non-Javadoc)
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken)
	 */
	public void deliveryComplete(IMqttDeliveryToken token) 
	{	
		logger.info("Deleviry Complete: " + token.getMessageId() + "-" + token.getResponse());	
	}
	
	/*
	 * Method to return the messag
	 * 
	 * @returns messag
	 */
	public static String getMessag()
	{
		return messag;
	}
	
	//Method to retrieve the message from MQTT Broker 
	public static void setMessag(MqttMessage message) 
	{
		MqttClientConnector.messag = message.toString();
	}	
}