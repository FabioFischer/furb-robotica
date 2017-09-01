package examples;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class SensorTest {			
	
	public static void main(String args[]) {
		
		//LightSensor lgt = new LightSensor(SensorPort.S1); // Versões 2.0
		ColorSensor lgt = new ColorSensor(SensorPort.S1); // Versões 1.0
		
		LCD.drawString("Ponto claro", 0, 0);
		Button.ENTER.waitForPress();
		lgt.setHigh(lgt.getLightValue());
		
		LCD.drawString("Ponto escuro", 0, 2);
		Button.ENTER.waitForPress();
		lgt.setLow(lgt.getLightValue());
		
		while(!Button.ESCAPE.isDown()) {
			if (lgt.getLightValue() > lgt.getLow()) {
				System.out.println("Sensor de luz ativado");				
			} else {
				System.out.println("Sensor de luz normal");
			}
		}
		
	}	
}
