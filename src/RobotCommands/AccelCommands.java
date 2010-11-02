package RobotCommands;

import HostDevice.ComInterface;

public class AccelCommands extends Commands{

	public AccelCommands(ComInterface serial) {
		super(serial);
	}

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

}
