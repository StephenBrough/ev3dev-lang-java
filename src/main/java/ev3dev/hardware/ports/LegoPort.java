package ev3dev.hardware.ports;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.List;

import ev3dev.exception.InvalidPortException;
import ev3dev.io.Sysclass;

/***
 * LegoPort class for fetching data from a specified port.<br>
 * <br>
 * You can also use a <b>MotorPort</b> or a <b>SensorPort</b> for your device.
 * @author Anthony
 *
 */
public class LegoPort {
	
	private int port = 0;
	
	public static final String SYSTEM_CLASS_NAME = "lego-port";
	
	public static final int PORT_1 = 0;
	
	public static final int PORT_2 = 1;
	
	public static final int PORT_3 = 2;
	
	public static final int PORT_4 = 3;
	
	public static final int PORT_A = 4;
	
	public static final int PORT_B = 5;
	
	public static final int PORT_C = 6;
	
	public static final int PORT_D = 7;
	
	public LegoPort(int port) throws InvalidPortException{
		if (port < 0){
			throw new InvalidPortException("Port is lower than 0, Port: " + port);
		} else if (port > 7){
			throw new InvalidPortException("Port is higher than 7, Port: " + port);
		}
		this.port = port;
	}
	
	public String getAddress() throws IOException{
		String address = Sysclass.getProperty(SYSTEM_CLASS_NAME, "port" + port, "address");
		return address;
	}
	
	public String getDriverName() throws IOException{
		String drivername = Sysclass.getProperty(SYSTEM_CLASS_NAME, "port" + port, "driver_name");
		return drivername;
	}
	
	public String[] getModes() throws IOException{
		String modesstr = Sysclass.getProperty(SYSTEM_CLASS_NAME, "port" + port, "modes");
		return separateSpace(modesstr);
	}
	
	public String getMode() throws IOException{
		String mode = Sysclass.getProperty(SYSTEM_CLASS_NAME, "port" + port, "mode");
		return mode;
	}
	
	public void setMode(String mode) throws IOException{
		Sysclass.setProperty(SYSTEM_CLASS_NAME, "port" + port, "mode", mode);
	}
	
	public void setDevice(String driver) throws IOException{
		Sysclass.setProperty(SYSTEM_CLASS_NAME, "port" + port, "set_device", driver);
	}
	
	public String getStatus() throws IOException{
		String status = Sysclass.getProperty(SYSTEM_CLASS_NAME, "port" + port, "status");
		return status;
	}
	
	private static String[] separateSpace(String space_array){
		int i;
		int j;
		String sep;
		List<String> list = new ArrayList<String>(50);
		for (i = 0; i < space_array.length(); i++){
			for (j = 0; j < space_array.length(); j++){
				if (space_array.charAt(j) == ' '){
					break;
				}
			}
			if (j == space_array.length()){
				break;
			}
			sep = space_array.substring(i, j);
			list.add(sep);
			i = j + 1;
		}
		Object[] objarr = list.toArray();
		String[] strarr = new String[objarr.length];
		for (i = 0; i < strarr.length; i++){
			strarr[i] = (String) objarr[i];
		}
		return strarr;
	}
}