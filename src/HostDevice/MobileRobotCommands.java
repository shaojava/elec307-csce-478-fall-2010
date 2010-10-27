package HostDevice;

/**
 * The Class MobileRobotCommands.
 */
public class MobileRobotCommands {

	/** The serial interface. */
	private ComInterface serialInterface;
	
	/**
	 * Instantiates a new mobile robot commands.
	 *
	 * @param serial the serial
	 */
	public MobileRobotCommands(ComInterface serial) {
		serialInterface = serial;
	}
	
	/**
	 * Gets the accelerometer x.
	 *
	 * @return the accelerometer x
	 * @throws Exception the exception
	 */
	public int getAccelerometerX() throws Exception {
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 2)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveResult().trim());
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Gets the accelerometer y.
	 *
	 * @return the accelerometer y
	 * @throws Exception the exception
	 */
	public int getAccelerometerY() throws Exception {
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 3)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveResult().trim());
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Gets the accelerometer z.
	 *
	 * @return the accelerometer z
	 * @throws Exception the exception
	 */
	public int getAccelerometerZ() throws Exception {
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 4)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveResult().trim());
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Sets the accelerometer sensitivity.
	 *
	 * @param sensitivity the sensitivity
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean setAccelerometerSensitivity(int sensitivity) throws Exception {
		
		//If the sensitivity value is not in the correct range, return false
		if (sensitivity < 0 || sensitivity > 3) {
			return false;
		}
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 5)) {
			
			//If the command byte was received, send the sensitivity value
			//Return true if the command was executed successfully
			return serialInterface.sendByte((byte) sensitivity);
		}
		else {
			return false;
		}
	}
	
	/**
	 * Sets the accelerometer sleep.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean setAccelerometerSleep() throws Exception {
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 6)) {
			
			//If the command byte was received, return true
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Sets the accelerometer wake.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean setAccelerometerWake() throws Exception {
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 7)) {
			
			//If the command byte was received, return true
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Gets the iR front.
	 *
	 * @return the iR front
	 * @throws Exception the exception
	 */
	public int getIRFront() throws Exception {
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 8)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveResult().trim());
		}
		else {
			return -1;
		}
	}
	
	public double getIRFrontDistance() throws Exception {
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 8)) {
			
			//Read the analog value
			double analogValue = Double.parseDouble(serialInterface.receiveResult().trim());
			System.out.println(analogValue);
			
			//Calculate the Distance from the object in inches
			//Regression equation:
			double distance = (9.382006587694517 * Math.pow(10, -13)) * Math.pow(analogValue, 8)
			+ (-6.171065704450589 * Math.pow(10, -9)) * Math.pow(analogValue, 7)
			+ (1.775384841987104 * Math.pow(10, -5)) * Math.pow(analogValue, 6)
			+ -0.029179397787392 * Math.pow(analogValue, 5)
			+ 29.966042094684830 * Math.pow(analogValue, 4)
			+ (-1.969025572946275 * Math.pow(10, 4)) * Math.pow(analogValue, 3)
			+ (8.084292243594736 * Math.pow(10, 6)) * Math.pow(analogValue, 2)
			+ (-1.896195006052438 * Math.pow(10, 9)) * analogValue
			+ 1.945316146252669 * Math.pow(10, 11);
			
			//The distance can not be less than 0
			if(distance < 0) {
				distance = 0;
			}
			
			//If the command byte was received, return the value
			return distance;
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Gets the iR left.
	 *
	 * @return the iR left
	 * @throws Exception the exception
	 */
	public int getIRLeft() throws Exception {
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 9)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveResult().trim());
		}
		else {
			return -1;
		}
	}
	
	public double getIRLeftDistance() throws Exception {
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 9)) {
			
			//Read the analog value
			double analogValue = Double.parseDouble(serialInterface.receiveResult().trim());
			System.out.println(analogValue);
			
			//Calculate the Distance from the object in inches
			//Regression equation:
			double distance = -8.521291832401360 * Math.pow(10, -14) * Math.pow(analogValue, 9)
			+ 6.191848911359777 * Math.pow(10,-10) * Math.pow(analogValue, 8)
			+ -1.993571970164780 * Math.pow(10, -6) * Math.pow(analogValue, 7)
			+ 0.003733068834258 * Math.pow(analogValue, 6)
			+ -4.480593034728872 * Math.pow(analogValue, 5)
			+ 3.574695569355396* Math.pow(10, 3) * Math.pow(analogValue, 4)
			+ -1.895703142288277 * Math.pow(10, 6) * Math.pow(analogValue, 3)
			+ 6.443439788151265 * Math.pow(10, 8) * Math.pow(analogValue, 2)
			+ -1.273664516042564 * Math.pow(10, 11) * analogValue
			+ 1.115428972350089 * Math.pow(10, 13);
			
			//The distance can not be less than 0
			if(distance < 0) {
				distance = 0;
			}
			
			//If the command byte was received, return the value
			return distance;
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Gets the iR right.
	 *
	 * @return the iR right
	 * @throws Exception the exception
	 */
	public int getIRRight() throws Exception {
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 10)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveResult().trim());
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Gets the iR back.
	 *
	 * @return the iR back
	 * @throws Exception the exception
	 */
	public int getIRBack() throws Exception {
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 11)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveResult().trim());
		}
		else {
			return -1;
		}
	}
	
	public double getIRBackDistance() throws Exception {
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 11)) {
			
			//Read the analog value
			double analogValue = Double.parseDouble(serialInterface.receiveResult().trim());
			System.out.println(analogValue);
			
			//Calculate the Distance from the object in inches
			//Regression equation:
			double distance = 1.971175506926995 * Math.pow(10, -17) * Math.pow(analogValue, 9)
			+ -1.255650857113823 * Math.pow(10,-13) * Math.pow(analogValue, 8)
			+ 3.540577131634198 * Math.pow(10, -10) * Math.pow(analogValue, 7)
			+ -5.798760336017483 * Math.pow(10, -7) * Math.pow(analogValue, 6)
			+ 6.077624710261133 * Math.pow(10, -4) * Math.pow(analogValue, 5)
			+ -0.422606118882685 * Math.pow(analogValue, 4)
			+ 1.948935684338441 * Math.pow(10, 2) * Math.pow(analogValue, 3)
			+ -5.745984810581629 * Math.pow(10, 4) * Math.pow(analogValue, 2)
			+ 9.823335437786222 * Math.pow(10, 6) * analogValue
			+ -7.416153148746724 * Math.pow(10, 8);
			
			//The distance can not be less than 0
			if(distance < 0) {
				distance = 0;
			}
			
			//If the command byte was received, return the value
			return distance;
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Gets the iR bottom.
	 *
	 * @return the iR bottom
	 * @throws Exception the exception
	 */
	public int getIRBottom() throws Exception {
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 12)) {
			
			//If the command byte was received, send the power level
			//If the power level was received, return true
			return Integer.parseInt(serialInterface.receiveResult().trim());
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Motor drive forward.
	 *
	 * @param power the power of the command (between 0 and 255)
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean motorDriveForward(int power) throws Exception {
		
		//If the supplied power is not within the accepted range, return false
		if  (power < 0 || power > 255) {
			return false;
		}
		
		//If the command byte was received, send the power level
		//If the power level was received, return true
		if (serialInterface.sendCommand((byte) 13)) {
			return serialInterface.sendByte((byte) power);
		}
		else {
			return false;
		}
	}
	
	/**
	 * Motor drive reverse.
	 *
	 * @param power the power of the command (between 0 and 255)
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean motorDriveReverse(int power) throws Exception {
		
		//If the supplied power is not within the accepted range, return false
		if  (power < 0 || power > 255) {
			return false;
		}
		
		//If the command byte was received, send the power level
		//If the power level was received, return true
		if (serialInterface.sendCommand((byte) 14)) {
			return serialInterface.sendByte((byte) power);
		}
		else {
			return false;
		}
	}
	
	/**
	 * Motor turn left.
	 *
	 * @param power the power of the command (between 0 and 255)
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean motorTurnLeft(int power) throws Exception {
		
		//If the supplied power is not within the accepted range, return false
		if  (power < 0 || power > 255) {
			return false;
		}
		
		//If the command byte was received, send the power level
		//If the power level was received, return true
		if (serialInterface.sendCommand((byte) 15)) {
			return serialInterface.sendByte((byte) power);
		}
		else {
			return false;
		}
	}
	
	/**
	 * Motor turn right.
	 *
	 * @param power the power of the command (between 0 and 255)
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean motorTurnRight(int power) throws Exception {
		
		//If the supplied power is not within the accepted range, return false
		if  (power < 0 || power > 255) {
			return false;
		}
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 16)) {
			
			//If the command byte was received, send the power level
			//If the power level was received, return true
			return serialInterface.sendByte((byte) power);
		}
		else {
			return false;
		}
	}
	
	
}
