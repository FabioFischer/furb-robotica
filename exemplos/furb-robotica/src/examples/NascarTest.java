package examples;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

// MotorTeste
public class NascarTest {	
	public static void main(String args[]) throws InterruptedException {

		UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S1);
		
		Motor.B.setSpeed(700);
		Motor.C.setSpeed(700);

		while(!Button.ESCAPE.isDown()) {
			if (sonic.getDistance() < 15) {		
				Motor.C.rotate(-315, true);
				Motor.B.rotate(360);
			} else {				
				Motor.B.forward();
				Motor.C.forward();
			}			

			LCD.drawInt(sonic.getDistance(), 0, 0);
		}
	}	
}
