package RobotCommands;

import HostDevice.ComInterface;

public class IRCommands extends Commands{

	public IRCommands(ComInterface serial) {
		super(serial);
	}
	

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
	


}
