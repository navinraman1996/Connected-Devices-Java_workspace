package schooldomain.studentname.connecteddevices.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * this class contains sensor data's and attributes
 */
public class SensorData {
	
	private Integer sampleCount = 0;
	private Double curValue;
	private Double maxValue;
	private Double minValue;
	private Double totValue;
	private Double diffValue;
	private Double avgValue;
	private String time;
	private String name;
	final 	String degree = "\u00b0";
	
	/*
	 * Sensor data constructor
	 * 
	 *@param maxValue: Maximum temperature
	 *@param mixValue: Minimum temperature
	 *@param time: timestamp of the data
	 *@param name: name of the data
	 */
	public SensorData(Double maxValue, Double minValue, String time, String name) {
		super();
		this.maxValue 	= maxValue;
		this.minValue	= minValue;
		this.time 		= time;
		this.name 		= name;
	}

	//@return : returns the current temp value
	public Double getCurValue() {
		return curValue;
	}

	//@param : Accepts the Double datatype current value
	public void setCurValue(Double curValue) {
		this.curValue = curValue;
	}

	//@return : returns the Maximum temp value
	public Double getMaxValue() {
		return maxValue;
	}

	//@param : Accepts the Double datatype Maximum value
	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	//@return : returns the Minimum temp value
	public Double getMinValue() {
		return minValue;
	}

	//@param : Accepts the Double datatype Minimum value
	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	//@return : returns the total temp value
	public Double getTotValue() {
		return totValue;
	}

	//@param : Accepts the Double datatype total temp value
	public void setTotValue(Double totValue) {
		this.totValue = totValue;
	}

	//@return : returns the temp difference value
	public Double getDiffValue() {
		return diffValue;
	}

	//@param : Accepts the Double datatype temp difference value
	public void setDiffValue(Double diffValue) {
		this.diffValue = diffValue;
	}

	//@return : returns the average temp value
	public Double getAvgValue() {
		return avgValue;
	}

	//@param : Accepts the Double datatype average temp value
	public void setAvgValue(Double avgValue) {
		this.avgValue = avgValue;
	}

	//@return : returns the timestamp of the data
	public String getTime() {
		return time;
	}

	//@param : Accepts the String datatype timestamp value
	public void setTime(String time) {
		this.time = time;
	}

	//@return : returns the samplecount value
	public Integer getSampleCount() {
		return sampleCount;
	}

	//@param : Accepts the Integer datatype samplecount value
	public void setSampleCount(Integer sampleCount) {
		this.sampleCount = sampleCount;
	}

	//@return : returns the name of the data
	public String getName() {
		return name;
	}

	//@param : Accepts the String datatype name of the data
	public void setName(String name) {
		this.name = name;
	}

	//This method is used to display all the sensor data values
	//@Override
	//public String toString() {
	//	String string = "\nTime: " + this.getTime() + "\n"+
	//			"\nCURRENT TEMPERATURE VALUE: " + this.getCurValue() + degree + "C" +
	//			"\nAverage Temperature Value: " + this.getAvgValue() + degree + "C" +
	//			"\nMinimum Temperature Value: " + this.getMinValue() + degree + "C" +
	//			"\nMaximum Temperature Value: " + this.getMaxValue() + degree + "C" + "\n";
	//	return string;
	//}
	
	@Override
	public String toString() {
		String string = "\nTime: " + this.getTime() + "\n"+
				"\ncurValue: " + this.getCurValue() +
				"\navgValue: " + this.getAvgValue() +
				"\nminValue: " + this.getMinValue() +
				"\nmaxValue: " + this.getMaxValue() + "\n";
				return string;
	}
	
	//This method is used for increment in samplecount value
	public void addValue()
	{
		this.setSampleCount(this.getSampleCount()+1);	
	}
	
	/*
	 * Updates the timestamp to current time
	 */
	public void updateTimeStamp() {
		this.time = new SimpleDateFormat("yyyy.MM.dd HH:mm.ss").format(new Date());
	}
	
	/*
	 * Function adds current value to calculate average value
	 * @param val - Current sensor value
	 */
	public void updateValue(float val) {
		updateTimeStamp();
		++this.sampleCount;
		this.curValue = (double) val;
		this.totValue += val;
		if (this.curValue < this.minValue) {
			this.minValue = this.curValue;
		}
		if (this.curValue > this.maxValue) {
			this.maxValue = this.curValue;
		}
		if (this.totValue != 0 && this.sampleCount > 0) {
			this.avgValue = this.totValue / this.sampleCount;
		}
	}
	

}