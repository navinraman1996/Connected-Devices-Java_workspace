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
	 * This method responds to a POST request on the resource
	 */
	@Override
	public void handlePOST(CoapExchange exchange)
	{
		if(data != null)
		{
			System.out.println("Object already exists. Sending BAD_REQUEST in response to POST request from " + exchange.getSourceAddress());
			exchange.respond(ResponseCode.BAD_REQUEST, "Data object already exists", MediaTypeRegistry.TEXT_PLAIN);
		}
		else
		{
			String jsonData = new String(exchange.getRequestPayload());
			
			System.out.println("Received JSON in POST request from " + exchange.getSourceAddress());
			System.out.println(jsonData);
			
			data = new SensorData(30.0,0.0,"time","Temperature");
			//If data is null, then initialize it from the request payload
			dat.JsonToSensorData(jsonData,null);
			exchange.respond(ResponseCode.CREATED, "Data object created", MediaTypeRegistry.TEXT_PLAIN);
			
		}
	}
	
	/**
	 * This method responds to a PUT request on the resource
	 * 
	 */
	@Override
	public void handlePUT(CoapExchange exchange)
	{
		if(data == null)
		{			
			exchange.respond(ResponseCode.NOT_FOUND, "Data object needs to be initialized", MediaTypeRegistry.TEXT_PLAIN);
		}
		else
		{
			String jsonData = new String(exchange.getRequestPayload());
			
			System.out.println("Received JSON in PUT request from " + exchange.getSourceAddress());
			System.out.println(jsonData);
						
			dat.JsonToSensorData(jsonData,null); //Update the SensorData object from the request payload
			exchange.respond(ResponseCode.CHANGED, "Data object updated", MediaTypeRegistry.TEXT_PLAIN);
			
		}
	}
	
	/**
	 * This method is used to respond to a DELETE request on the resource
	 */
	@Override
	public void handleDELETE(CoapExchange exchange)
	{
		if(data == null)
		{
			exchange.respond(ResponseCode.BAD_REQUEST, "Data object doesn't exist", MediaTypeRegistry.TEXT_PLAIN);
		}
		else
		{
			System.out.println("Setting object to null in response to DELETE request from " + exchange.getSourceAddress());
			data = null; //Set the SensorData object to null, subsequent GET requests will return an error until a POST request is made
			exchange.respond(ResponseCode.DELETED, "Data object deleted", MediaTypeRegistry.TEXT_PLAIN);
		}	
	}
}
