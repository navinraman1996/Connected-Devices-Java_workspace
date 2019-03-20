/**
 * @author: Navin Raman
 */
package schooldomain.studentname.connecteddevices.labs.module07;

import java.io.IOException;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

import schooldomain.studentname.connecteddevices.common.DataUtil;
import schooldomain.studentname.connecteddevices.common.SensorData;

/**
 * This class extends the CoAP resource for storing temperature data. 
 */
public class TempResourceHandler extends CoapResource{
	private SensorData data = new SensorData(30.0,0.0,"time","Temperature Sensor");
	private DataUtil dat = new DataUtil();
	
	public TempResourceHandler() {
		super("temperature");
		getAttributes().setTitle("Temperature Resource");
	}
	
	/**
	 * This method is used to respond to a GET request on the resource
	 */
	@Override
	public void handleGET(CoapExchange exchange)
	{
			if(data == null)
			{
				System.out.println("The Object doesn't exist. Sending a response message as NOT_FOUND to the GET request from " + exchange.getSourceAddress());
				exchange.respond(ResponseCode.NOT_FOUND, "Data object is need to be initialized", MediaTypeRegistry.TEXT_PLAIN);
			}
			else
			{
				System.out.println(data);
				System.out.println("Sending JSON in response to GET request from " + exchange.getSourceAddress());
				
				/**
				 * If the data is not null, then it sends the JSON 
				 * representation as a response payload
				 */
				exchange.respond(ResponseCode.CONTENT, dat.SensorDataToJson(data), MediaTypeRegistry.APPLICATION_JSON);	
			}			
	}
	
	/**
	 * This method is used to respond to the POST request on the resource
	 */
	@Override
	public void handlePOST(CoapExchange exchange)
	{
		if(data != null)
		{
			System.out.println("Object is already exist. Sending the BAD_REQUEST in response to the POST request from " + exchange.getSourceAddress());
			exchange.respond(ResponseCode.BAD_REQUEST, "Data object is already exist", MediaTypeRegistry.TEXT_PLAIN);
		}
		else
		{
			String jsonData = new String(exchange.getRequestPayload());
			
			System.out.println("Received JSON data in POST request from " + exchange.getSourceAddress());
			System.out.println(jsonData);
			
			data = new SensorData(30.0,0.0,"time","Temperature");
			
			//If the data is null, then initialize it from the request payload
			dat.JsonToSensorData(jsonData,null); 
			exchange.respond(ResponseCode.CREATED, "Data object is created", MediaTypeRegistry.TEXT_PLAIN);
		}
	}
	
	/**
	 * This method is used to respond to the PUT request on the resource
	 */
	@Override
	public void handlePUT(CoapExchange exchange)
	{
		if(data == null)
		{			
			exchange.respond(ResponseCode.NOT_FOUND, "The Data object needs to be initialized", MediaTypeRegistry.TEXT_PLAIN);
		}
		else
		{
			String jsonData = new String(exchange.getRequestPayload());
			
			System.out.println("Received the JSON data in PUT request from " + exchange.getSourceAddress());
			System.out.println(jsonData);
						
			//Updating the SensorData object from the request payload
			dat.JsonToSensorData(jsonData,null); 
			exchange.respond(ResponseCode.CHANGED, "The Data object is updated", MediaTypeRegistry.TEXT_PLAIN);
		}
	}
	
	/**
	 * This method is used to respond to the DELETE request on the resource.
	 */
	@Override
	public void handleDELETE(CoapExchange exchange)
	{
		if(data == null)
		{
			exchange.respond(ResponseCode.BAD_REQUEST, "ThisS Data object doesn't exist", MediaTypeRegistry.TEXT_PLAIN);
		}
		else
		{
			System.out.println("Setting the object to null in response to DELETE request from " + exchange.getSourceAddress());
			
			/**
			 * Setting the SensorData object to null, subsequent GET requests
			 * will return an error until a POST request is made
			 */
			data = null; 
			exchange.respond(ResponseCode.DELETED, "Data object deleted", MediaTypeRegistry.TEXT_PLAIN);
		}	
	}
}