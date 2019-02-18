/**
 * 
 */
package schooldomain.studentname.connecteddevices.labs.module05;

import java.util.logging.Logger;

import com.labbenchstudios.edu.connecteddevices.common.BaseDeviceApp;
import com.labbenchstudios.edu.connecteddevices.common.DeviceApplicationException;


/**
 *This class extends BaseDeviceApp
 */
public class Module05App extends BaseDeviceApp
{
	// static
	
	private static final Logger _Logger =
		Logger.getLogger(Module05App.class.getSimpleName());
	
	/**
	 * This main method is used to start the application 
	 * and stop the application
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		Module05App app = new Module05App();
		try {
			app.start();
			app.stop();
		} catch (DeviceApplicationException e) {
			e.printStackTrace();
		}
	}
	
	// private var's
	
	
	// constructors
	
	/**
	 * Default Construction
	 * 
	 */
	public Module05App()
	{
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param appName: name of the Application
	 */
	public Module05App(String appName)
	{
		super(appName);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param appName: name of the Application
	 * @param args
	 */
	public Module05App(String appName, String[] args)
	{
		super(appName, args);
	}
	
	// protected methods
	
	/* (non-Javadoc)
	 * @see com.labbenchstudios.edu.connecteddevices.common.BaseDeviceApp#start()
	 * 
	 * calls the demo method in the TempManaementApp
	 */
	@Override
	protected void start() throws DeviceApplicationException
	{
		_Logger.info("Hello - module05 here!");
		TempManagementApp.demo();
	}
	
	/* (non-Javadoc)
	 * @see com.labbenchstudios.edu.connecteddevices.common.BaseDeviceApp#stop()
	 */
	@Override
	protected void stop() throws DeviceApplicationException
	{
		_Logger.info("Stopping module05 app...");
	}
	
}