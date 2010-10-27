package HostDevice;

public class MobileRobot {

	public static void main(String[] args) throws Exception{
		ComInterface serialInterface = new ComInterface("/dev/tty.usbserial-A700fj0f");

		if (serialInterface.establishConnection()) {
			
			MobileRobotCommands commandInterface = new MobileRobotCommands(serialInterface);
			
			while(true) {
				System.out.println(commandInterface.getIRFrontDistance());
				System.out.println(commandInterface.getIRFront());
				//System.out.println(commandInterface.getAccelerometerY());
				//System.out.println(commandInterface.getAccelerometerZ());
				Thread.sleep(1000);
			}
		}
	}
}