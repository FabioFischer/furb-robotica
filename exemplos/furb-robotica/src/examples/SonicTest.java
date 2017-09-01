package examples;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

// UltrasonicSensor
public class SonicTest {	
	
	public static void main(String args[]) {
		UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S1);
		
		while(!Button.ESCAPE.isDown()) {
			LCD.drawInt(sonic.getDistance(), 0, 0);
		}
	}	
}
