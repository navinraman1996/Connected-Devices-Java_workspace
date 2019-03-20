/**
 * @author: Navin Raman
 */
package schooldomain.studentname.connecteddevices.labs.module07;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

/**
 * This class will help to connect to the CoAP server
 */
public class CoapClientConnector {
	private CoapClient client;
	private String serverURI;
	public CoapClientConnector(String serverUri)
	{
		this.serverURI = serverUri;
		client = new CoapClient(this.serverURI);
	}
	
	/**
	 * This method is used to ping the CoAP server and wait for the feedback
	 */
	public void ping()	
	{
		if(!client.ping(2000))
		{
			System.out.println(this.serverURI + " is not responding to the ping, exiting...");
			System.exit(-1);
		}
		else
		{
			System.out.println(this.serverURI + " is responding to the ping");
		}
	}
	
	/**
	 * This Wrapper method is used for GET action
	 */
	public void get()
	{
		printResponse(client.get());
	}
	
	/**
	 * This Wrapper method is used for POST action
	 * 
	 * @param jsonData the JSON data which is to be posted to the server
	 */
	public void post(String jsonData)
	{		
		printResponse(client.post(jsonData, MediaTypeRegistry.APPLICATION_JSON));
	}
	
	/**
	 * This Wrapper method is used for PUT action
	 * 
	 * @param jsonData JSON data to be posted to server
	 */
	public void put(String jsonData)
	{		
		printResponse(client.put(jsonData, MediaTypeRegistry.APPLICATION_JSON));
	}
	
	/**
	 * This Wrapper method is used for DELETE action
	 */
	public void delete()
	{
		printResponse(client.delete());
	}
	
	/**
	 * This is a Helper method  which is used to print out the response from the CoAP server
	 * 
	 * @param response Response received from the server
	 */
	private void printResponse(CoapResponse response)
	{
		if (response!=null) {
	        
        	System.out.println( response.getCode() );
        	System.out.println( response.getOptions() );
        	System.out.println( response.getResponseText() );
        	
        } else {
        	
        	System.out.println("Request failed");
        	
        }
	}
}