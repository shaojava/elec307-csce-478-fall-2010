package RobotCommands;

import HostDevice.ComInterface;

public class MotorCommands extends Commands {

	public MotorCommands(ComInterface serial) {
		super(serial);
	}

	public boolean motorDriveForward(int power) throws Exception {
		
		//If the supplied power is not within the accepted range, return false
		if  (power < 0 || power > 255) {
			return false;
		}
		
		//If the command byte was received, send the power level
		//If the power level was received, return true
		if (serialInterface.sendCommand((byte) 13)) {
			return serialInterface.sendByte(new Integer(power).byteValue());
		}
		else {
			return false;
		}
	}
	
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
	
	public boolean motorStop() throws Exception {
		
		//If the command byte was received, send the power level
		//If the power level was received, return true
		if (serialInterface.sendCommand((byte) 17)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean motorCenter() throws Exception {
		
		//If the command byte was received, send the power level
		//If the power level was received, return true
		if (serialInterface.sendCommand((byte) 18)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
}
