/**
 * Created on Feb 26, 2019
 * 
 * @author Navin Raman
 */

package schooldomain.studentname.connecteddevices.labs.module08;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.BufferedInputStream;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.KeyStoreException;
import java.security.cert.CertificateFactory;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * This class is implements MqttCallback class
 * 
 * SSL stands for secure socket layer this is used to secure our connection.
 * Ubidots is the host which we are connecting to.
 * Using different port for Ubidots not the MQTT port.
 * Pem file is saved to in the file with the credentials certificate
 */
public class MqttClientConnector implements MqttCallback{

	private int _port 			= 8883;
	private String _protocol 	= "ssl";
	private String _host 		= "things.ubidots.com";
	private boolean _isSecureConn 		= false;
	private static final Logger _logger = Logger.getLogger(MqttClientConnector.class.getName());
	
	private String _clientID;
	private String _brokerAddr;
	private String _userName;
	private String _authToken;
	private String _pemFileName;
	private MqttClient _mqttClient;
	
	// Constructor
	public MqttClientConnector()
	{
		this(null, false);
	}

	/**
	 * Constructor for MqttClientConnector
	 * 
	 * @param host: string type
	 * @param isSecure: boolean value
	 */
	public MqttClientConnector(String host, boolean isSecure)
	{
		super();

		if(host!=null && host.trim().length()>0)
		{
			this._host = host;
		}

		_clientID = MqttClient.generateClientId();
		_logger.info("client ID for broker connection: "+_clientID);

		_brokerAddr = _protocol+ "://"+_host+":"+_port;
		_logger.info("URL for broker connection: "+_brokerAddr);
	}

	/**
	 * Constructor which takes these parameters
	 * 
	 * @param host: String type
	 * @param userName: String type
	 * @param pemFileName: String type
	 */
	 public MqttClientConnector(String host, String userName, String pemFileName)
	 {
		 super();
		 if (host != null && host.trim().length() > 0)
		 {
			 _host = host;
		 }

		 if (userName != null && userName.trim().length() > 0) {
			 _userName = userName;
		 }

		 if (pemFileName != null) {
			 File file = new File(pemFileName);
			 if (file.exists()) {
				 _protocol     = "ssl";
				 _port         = 8883;
				 _pemFileName  = pemFileName;
				 _isSecureConn = true;
				 _logger.info("PEM file valid. Using secure connection: " + _pemFileName);
			 } else {
				 _logger.warning("PEM file invalid. Using insecure connection: " + pemFileName);
			 }
		 }
		 _clientID   = MqttClient.generateClientId();
		 _brokerAddr = _protocol + "://" + _host + ":" + _port;
		 _logger.info("Using URL for broker conn: " + _brokerAddr);
	 }

	//Method for connecting to the MQTT server
	public void connect()
	{
		if(_mqttClient==null)
		{
			MemoryPersistence persistence = new MemoryPersistence();
			
			try {
				_mqttClient = new MqttClient(_brokerAddr, _clientID, persistence);
				MqttConnectOptions connOpts = new MqttConnectOptions();

				connOpts.setUserName("A1E-vMmWCA7a2S1ypxMeeMdm2GNqnuBxPA");
				connOpts.setCleanSession(false);

				if(_userName!=null)
				{
					connOpts.setUserName(_userName);
				}
				
				if(_isSecureConn)
					initSecureConnection(connOpts);

				_mqttClient.setCallback(this);
				_mqttClient.connect(connOpts);

				_logger.info("Connected to broker: "+_brokerAddr);

			}catch(MqttException e) {
				_logger.log(Level.SEVERE,"Failed to connect to broker: "+_brokerAddr, e);
			}
		}
	}

	//Method for disconnecting after the work is done
	public void disconnect()
	{
		try {
			_mqttClient.disconnect();
			_logger.info("connected to the broker: "+_brokerAddr);
		}catch(Exception e)
		{
			_logger.log(Level.SEVERE, "failed to disconnect from the broker: "+_brokerAddr, e);
		}
	}

	/**
	 * This method is used for publishing message to the server with 
	 * the following parameters.
	 * 
	 * @param topic
	 * @param qosLevel
	 * @param payload
	 * @return success: boolean 
	 */
	public boolean publishMessage(String topic, int qosLevel, byte[] payload)
	{
		boolean success = false;
		
		try {
			_logger.info("publishing message to the topic: "+ topic);

			MqttMessage mqttMsg = new MqttMessage();
			mqttMsg.setPayload(payload);
			mqttMsg.setQos(qosLevel);

			_mqttClient.publish(topic, mqttMsg);
			_logger.info("published message ID: "+mqttMsg.getId());

			success=true;
		}catch(Exception e)
		{
			_logger.log(Level.SEVERE, "Failed to publish MQTT message: "+e.getMessage());
		}
		return success;
	}
	
	/**
	 * Method used to subscribe to All client
	 * 
	 * @return boolean
	 */
	public boolean subscribeToAll()
	{
		try {
			_mqttClient.subscribe("$SYS/#");
			_logger.log(Level.INFO, "Subscribe to all successfully.");
			
			return true;
		}catch(Exception e)
		{
			_logger.log(Level.WARNING, "failed to subscribe to all topics: ", e);
		}
		return false;
	}
	
	/**
	 * Method used to subscribe to a specific mqtt topic
	 * 
	 * @param topic
	 * @return boolean
	 */
	public boolean subscribeToTopic(String topic)
	{
		try {
			_mqttClient.subscribeWithResponse(topic, 2);
			_logger.info("Subscribed to the topic: "+topic);
			return true;
		}catch(Exception e)
		{
			_logger.log(Level.WARNING, "failed to subscribe to the topic: "+topic, e);
		}
		return false;
	}

	/**
	 * Method for connection lost
	 * 
	 * @param cause
	 */
	public void connectionLost(Throwable cause) {
		_logger.log(Level.WARNING, "connection to broker is lost", cause);
		connect();
	}

	/**
	 * Method for indicating that message is arrived
	 * 
	 * @param topic
	 * @param message: mqtt message
	 */
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		//	message.getPayload().
		_logger.info("Message arrived: "+ "  "+message.toString()+ "  "+ "from topic: "+topic);
	}

	/**
	 * Method for delivery complete
	 * 
	 * @param token
	 */
	public void deliveryComplete(IMqttDeliveryToken token) 
	{
		try {
			_logger.info("Delivery complete: "+token.getMessageId()+"-"+token.getResponse()+"-"+token.getMessage());
		}catch(Exception e)
		{
			_logger.log(Level.SEVERE, "Failed to retrieve message from the token", e);
		}
	}

	/**
	 * Method for Initiate secure connection
	 * 
	 * @param connOpts
	 */
	private void initSecureConnection(MqttConnectOptions connOpts)
	{
		try {
			_logger.info("Configuring TLS...");
			SSLContext sslContext = SSLContext.getInstance("SSL");
			KeyStore keystore = readCertificate();
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(keystore);
			sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
			connOpts.setSocketFactory(sslContext.getSocketFactory());
		}catch(Exception e)
		{
			_logger.log(Level.SEVERE, "failed to initialize MqTT connection", e);
		}
	}
	
	/**
	 * Method for reading certificate
	 * 
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @return ks
	 */
	private KeyStore readCertificate() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
	{
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		FileInputStream fis = new FileInputStream(_pemFileName);
		BufferedInputStream bis = new BufferedInputStream(fis);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		ks.load(null);

		while(bis.available()>0)
		{
			Certificate cert = cf.generateCertificate(bis);
			ks.setCertificateEntry("asm_store"+bis.available(), cert);
		}
		return ks;
	}
	
	/**
	 * Method to unsubscribe to a specific mqtt topic
	 * 
	 * @param topic
	 * @return boolean
	 */
	public boolean unSubscribeToTopic(String topic) {
		try {	
			_mqttClient.unsubscribe(topic);
			_logger.log(Level.INFO, "Unsubscribe to topic " + topic + " successfully.");
			return true;
		} catch (MqttException e) {
			_logger.log(Level.WARNING, "Failed to unsubscribe to topic " + topic, e);
			e.printStackTrace();
		}
		return false;
	}
}