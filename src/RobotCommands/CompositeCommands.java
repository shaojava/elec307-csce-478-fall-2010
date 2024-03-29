package RobotCommands;

import HostDevice.CommInterface;

// TODO: Auto-generated Javadoc
/**
 * The Class CompositeCommands.
 */
public class CompositeCommands extends BaseCommands {

	/**
	 * Instantiates a new composite commands.
	 *
	 * @param serial the serial
	 */
	public CompositeCommands(CommInterface serial) {
		super(serial);
	}
	
	/**
	 * Gets the iR front distance.
	 *
	 * @return the iR front distance
	 * @throws Exception the exception
	 */
	public double getIRFrontDistance() throws Exception {
		
		double analogValue = super.getIRFront();
		
		//Send the command byte
		if (analogValue > -1) {
			
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
	 * Gets the iR left distance.
	 *
	 * @return the iR left distance
	 * @throws Exception the exception
	 */
	public double getIRLeftDistance() throws Exception {
		
		double analogValue = super.getIRLeft();
		
		//Send the command byte
		if (analogValue > -1) {
			
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
	 * Gets the iR back distance.
	 *
	 * @return the iR back distance
	 * @throws Exception the exception
	 */
	public double getIRBackDistance() throws Exception {
		
		double analogValue = super.getIRBack();
		
		//Send the command byte
		if (analogValue > -1) {
			
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
	 * Gets the hall distance.
	 *
	 * @return the hall distance
	 * @throws Exception the exception
	 */
	public double getDistanceTraveled() throws Exception {

		double hallCount = super.getHallCount();
		super.resetHallCount();
		double hallDistance = ((Math.PI * 2) * hallCount) / 6;

		return hallDistance;
	}
	
	/**
	 * Accelerate.
	 *
	 * @param startPower the start power
	 * @param finalPower the final power
	 * @param timeMilliseconds the time milliseconds
	 * @throws Exception the exception
	 */
	public void accelerate(int startPower, int finalPower, int timeMilliseconds) throws Exception {
		
		assert (finalPower >= startPower);
		
		double incrementInterval = timeMilliseconds / (finalPower-startPower);
		
		int currentPower = startPower;
		
		while (currentPower < finalPower) {
			super.motorDriveForward(currentPower);
			currentPower++;
			Thread.sleep(Math.round(incrementInterval));
		}
		
	}
	
	/**
	 * Deaccelerate.
	 *
	 * @param startPower the start power
	 * @param finalPower the final power
	 * @param timeMilliseconds the time milliseconds
	 * @throws Exception the exception
	 */
	public void deaccelerate(int startPower, int finalPower, int timeMilliseconds) throws Exception {
		
		assert (startPower <= finalPower);
		
		double incrementInterval = timeMilliseconds / (startPower - finalPower);
		
		int currentPower = startPower;
		
		while (currentPower > finalPower) {
			super.motorDriveForward(currentPower);
			currentPower--;
			Thread.sleep(Math.round(incrementInterval));
		}
		
	}
	
	/**
	 * Turn degrees.
	 *
	 * @param degrees the degrees
	 * @param power the power
	 * @throws Exception the exception
	 */
	public void turnLeftDegrees(double degrees, int motorPower) throws Exception{
		
		//Get the current=initial compass heading
		double initialCompassHeading = super.compassGetHeading();
		double currentCompassHeading = initialCompassHeading;
		double finalCompassHeading = 0;
		
		if (initialCompassHeading < 270) {
			finalCompassHeading = initialCompassHeading + 90;
		}
		else if (initialCompassHeading >= 270){
			double temp = 360 - initialCompassHeading;
			finalCompassHeading = 90 - temp;
		}
		
		//Wait a bit
		Thread.sleep(10);
		
		//If positive power, drive forward
		super.motorTurnRight(255);
		
		//If positive power, drive forward
		if (motorPower > 0) {
			super.motorDriveForward(Math.abs(motorPower));
		}
		
		//Else if negative power, drive reverse
		else if (motorPower < 0) {
			super.motorDriveReverse(Math.abs(motorPower));
		}
		
		//While the difference in compass headings is less than the required degree change
		while (currentCompassHeading <= finalCompassHeading) {
			
			System.out.println(Math.abs(initialCompassHeading - currentCompassHeading));
			
			//Wait a period of time
			Thread.sleep(20);
			
			currentCompassHeading = super.compassGetHeading();
		}
		
		//Stop the motor
		super.motorStop();
		
		//Center the steering
		super.motorCenter();
		
	}

}
