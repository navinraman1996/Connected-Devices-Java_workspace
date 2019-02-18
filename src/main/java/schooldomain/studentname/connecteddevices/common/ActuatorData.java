package schooldomain.studentname.connecteddevices.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author NAVIN RAMAN
 *
 */

/**
 * ActuatorData - class defines the ActuatorData object of Actuator
 * 
 * @param timeStamp: contains the present date and time
 * @param name: name of actuator data
 * @param hasError: boolean to check error
 * @param command: type of command
 * @param errCode: type of error
 * @param statusCode: type of status
 * @param stateData: data of the ActuatorData's state
 * @param val: ActuatorData value
 */
public class ActuatorData {

	
	// Command, Status and Error case type definition
	public static final int COMMAND_OFF 		= 0;
	public static final int COMMAND_ON 			= 1;
	public static final int COMMAND_SET 		= 2;
	public static final int COMMAND_RESET 		= 3;
	public static final int STATUS_IDLE 		= 0;
	public static final int STATUS_ACTIVE 		= 1;
	public static final int ERROR_OK 			= 0;
	public static final int ERROR_COMMAND_FAILED 	= 1;
	public static final int ERROR_NON_RESPONSIBLE 	= -1;

	private String name 		= "Actuator Data";
	private String timeStamp 	= null;
	private boolean hasError 	= false;
	private int command 		= 0;
	private int errCode 		= 0;
	private int statusCode 		= 0;
	private String stateData 	= null;
	private float val 			= 0.0f;

	/**
	 * ActuatorData constructor.
	 */
	public ActuatorData() {
		super();
		updateTimeStamp();
	}

	/**
	 * updates the date and time.
	 */
	private void updateTimeStamp() {
		timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm.ss").format(new Date());
	}

	/**
	 * This method is to get the ActuatorData name
	 * 
	 * @return: 'name',String
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method is to set the ActuatorData name
	 * 
	 * @param name: String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method is get the date and time
	 * 
	 * @return 'timeStamp', String in yyyy.MM.dd HH:mm.ss
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * This method is set the date and time
	 * 
	 * @param timeStamp: String of Date and time in yyyy.MM.dd HH:mm.ss
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * This method is to check error
	 * 
	 * @return: 'hasError', true if ActuatorData has error else false
	 */
	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	/**
	 * This method is to get the command type
	 * 
	 * @return: 'command',0 - OFF, 1 - ON, 2 - SET, 3 - RESET command type
	 */
	public int getCommand() {
		return command;
	}

	/**
	 * This method is to set the command type
	 * 
	 * @param command: 0 - OFF, 1 - ON, 2 - SET, 3 - RESET command type
	 */
	public void setCommand(int command) {
		this.command = command;
	}

	/**
	 * This method is to return error code type
	 * 
	 * @return: 'errCode', 0 - Okay, 1 - failed, -1 - not responsive
	 */
	public int getErrCode() {
		return errCode;
	}

	/**
	 * This method is to set error code type
	 * 
	 * @param 'errCode': 0 - Okay, 1 - failed, -1 - not responsive
	 */
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	/**
	 * This method is to get the status type
	 * 
	 * @return: 'statusCode', 0 for Idle else 1 for Active
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * This method is to set the status type
	 * 
	 * @param statusCode: 0 for Idle else 1 for Active
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * This method is to get the data of the ActuatorData's state
	 * 
	 * @return: 'stateData',String
	 */
	public String getStateData() {
		return stateData;
	}

	/**
	 * This method is to set the data of the ActuatorData's state
	 * 
	 * @param stateData: String data
	 */
	public void setStateData(String stateData) {
		this.stateData = stateData;
	}

	/**
	 * This method is to get the ActuatorData value
	 * 
	 * @return: 'val', float
	 */
	public float getVal() {
		return val;
	}

	/**
	 * This method is to set the ActuatorData value
	 * 
	 * @param val: float
	 */
	public void setVal(float val) {
		this.val = val;
	}

	/**
	 * This method is to update the ActuatorData
	 * 
	 * @param data: ActuatorData
	 */
	public void updateData(ActuatorData data) {
		this.command = data.getCommand();
		this.statusCode = data.getStatusCode();
		this.errCode = data.getErrCode();
		this.stateData = data.getStateData();
		this.val = data.getVal();
	}

	/**
	 * This method returns the string display output of the ActuatorData object.
	 * 
	 * @return: 'st' : ActuatorData string object.
	 */
	public String toString() {
		String st;
		st = ("Name: " + name + "\n" + "time: " + timeStamp + "\n" + "Command: " + command + "\n" + "Status Code: "
				+ statusCode + "\n" + "Error Code: " + errCode + "\n" + "State Data: " + stateData + "\n" + "Value: "
				+ val + "\n");
		return st;
	}
}