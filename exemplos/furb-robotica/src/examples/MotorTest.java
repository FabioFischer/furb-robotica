package examples;

import lejos.nxt.Button;
import lejos.nxt.Motor;

// MotorTeste
public class MotorTest {	
	public static void main(String args[]) throws InterruptedException {
		//Motor.B.setAcceleration(100);
		//Motor.C.setAcceleration(100);
		Motor.B.setSpeed(900);
		
		while(!Button.ESCAPE.isDown()) {
			Motor.B.rotate(-360, true);
			Motor.C.rotate(360);			
		}
		
//		Motor.B.forward();
//		Motor.C.forward();
//
//		Thread.sleep(2000);
//		
//		Motor.B.stop();
//		Motor.C.stop();
//		
//		Motor.B.backward();
//		Motor.C.backward();
//		
//		Thread.sleep(2000);
//		
//		Motor.B.flt();
//		Motor.C.flt();
	}	
}
