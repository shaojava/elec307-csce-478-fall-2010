package HostDevice;

public class MobileRobotCommands {

	private ComInterface serialInterface;
	
	public MobileRobotCommands(ComInterface serial) {
		serialInterface = serial;
	}
	
	public int getAccelerometerX() throws Exception, Exception {
		if (serialInterface.sendCommand((byte) 2)) {
			return Integer.parseInt(serialInterface.recieveResult().trim());
		}
		else {
			return -1;
		}
	}
	
	public int getAccelerometerY() throws Exception, Exception {
		if (serialInterface.sendCommand((byte) 3)) {
			return Integer.parseInt(serialInterface.recieveResult().trim());
		}
		else {
			return -1;
		}
	}
	
	public int getAccelerometerZ() throws Exception, Exception {
		if (serialInterface.sendCommand((byte) 4)) {
			return Integer.parseInt(serialInterface.recieveResult().trim());
		}
		else {
			return -1;
		}
	}
	
	public boolean setAccelrometerSensitivity(int sensitivity) throws Exception, Exception {
		assert(sensitivity >= 0 && sensitivity <= 3);
		if (serialInterface.sendCommand((byte) 5)) {
			return serialInterface.sendByte((byte) sensitivity);
		}
		else {
			return false;
		}
	}
	
	public boolean setAccelrometerSleep() throws Exception, Exception {
		if (serialInterface.sendCommand((byte) 6)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean setAccelrometerWake() throws Exception, Exception {
		if (serialInterface.sendCommand((byte) 7)) {
			return true;
		}
		else {
			return false;
		}
	}
}
