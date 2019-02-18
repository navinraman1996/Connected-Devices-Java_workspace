/*
 * @author NAVIN RAMAN
 */

package schooldomain.studentname.connecteddevices.labs.module05;

import schooldomain.studentname.connecteddevices.common.DataUtil;
import schooldomain.studentname.connecteddevices.common.SensorData;

/*
 * This class instantiates the DataUtil object inside the demo function
 * and read the sensor data text file to retrieve the JSON string and
 * printing the console output 
 */
public class TempManagementApp {

	/* 
	 * This method is used for Object instantiation and to read the sensor
	 * data text file to retrieve the JSON string and print the console output
	 */
	public static void demo() {
		DataUtil sensor = new DataUtil();
		SensorData sensor_variable = sensor.JsonToSensorData(null , "C:\\Test2\\workspace\\iot-device\\apps\\labs\\module05\\sensordata.txt");
		System.out.println(sensor_variable);
	}
}
