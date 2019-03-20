/**
 * @author: Navin Raman
 */
package schooldomain.studentname.connecteddevices.labs.module07;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.InetSocketAddress;

import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.EndpointManager;

import com.labbenchstudios.edu.connecteddevices.common.ConfigUtil;
import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;

/**
 * This class is used to initializes the server
 */
public class CoapServerConnector extends CoapServer {
	private final int COAP_PORT;
	
	public CoapServerConnector(ConfigUtil config) throws SocketException {
		COAP_PORT = config.getIntegerProperty(ConfigConst.COAP_GATEWAY_SECTION, ConfigConst.PORT_KEY);
				
		//Adding the end points and the server resources, while initializing the server
		addEndpoints();
		
		add(new TempResourceHandler());		
	}
	
	/**
	 * This methods is used to bind the CoAP server to the necessary endpoints
	 */
	private void addEndpoints()
	{
		for (InetAddress addr : EndpointManager.getEndpointManager().getNetworkInterfaces()) {
    		// This will only bind to the localhost
			if (addr.isLoopbackAddress()) {
				InetSocketAddress bindToAddress = new InetSocketAddress(addr, COAP_PORT);
				addEndpoint(new CoapEndpoint(bindToAddress));
			}
		}
	}
}