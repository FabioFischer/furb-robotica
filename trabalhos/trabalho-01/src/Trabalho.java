
import java.util.Timer;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;

public class Trabalho {
    static ColorSensor lgt;
    static UltrasonicSensor Son;
    static int movimentosFrente;

    public static boolean isBlack(ColorSensor sensor) {
        return sensor.getLightValue() <= 52;
    }

    public static boolean esquerda() {
        if (isBlack(lgt)) {
            normal();
            return true;
        };

        Motor.C.setSpeed(200);
        Motor.B.setSpeed(100);

        for (int i = 0; i < (250 * (movimentosFrente + 1)); i++) {
            Motor.C.forward();
            Motor.B.backward();

            if (isBlack(lgt)) {
                normal();
                return true;
            };
        }

        return false;
    }

    public static boolean voltarEsquerda() {
        return direita();
    }

    public static boolean direita() {
        if (isBlack(lgt)) {
            normal();
            return true;
        };

        Motor.C.setSpeed(200);
        Motor.B.setSpeed(100);

        for (int i = 0; i < (250 * (movimentosFrente + 1)); i++) {
            Motor.C.backward();
            Motor.B.forward();

            if (isBlack(lgt)) {
                normal();
                return true;
            };
        }

        return false;
    }

    public static boolean voltarDireita() {
        return esquerda();
    }

    public static void normal() {
        Motor.B.setSpeed(600);
        Motor.C.setSpeed(600);
    }

    public static void main(String args[]) throws InterruptedException {
        Son = new UltrasonicSensor(SensorPort.S2);

        lgt = new ColorSensor(SensorPort.S1);
        lgt.setFloodlight(true);

        lgt.setHigh(61);
        lgt.setLow(28);

        Son = new UltrasonicSensor(SensorPort.S2);

        lgt = new ColorSensor(SensorPort.S1);
        lgt.setFloodlight(true);

        lgt.setHigh(61);
        lgt.setLow(28);

        normal();

        int direcao = 0;

        while(!Button.ENTER.isDown());

        movimentosFrente = 0;

        while (true) {
            if (isBlack(lgt)) {
                Motor.B.forward();
                Motor.C.forward();
                movimentosFrente = 0;

                if (Son.getDistance() < 20) {
                    Motor.C.rotate(430, true);
                    Motor.B.rotate(-430);

                    Motor.C.setSpeed(200);
                    Motor.B.setSpeed(600);

                    while (true) {
                        Motor.C.forward();
                        Motor.B.forward();

                        if (isBlack(lgt)) {
                            break;
                        }
                    }

                    normal();
                }
            } else {
                while (true) {
                    if (direcao == 0) {
                        if (esquerda()) {
                            direcao = 0;
                            movimentosFrente = 0;
                            break;
                        }

                        if (voltarEsquerda() || direita()) {
                            direcao = 1;
                            movimentosFrente = 0;
                            break;
                        }

                        voltarDireita();
                    } else {
                        if (direita()) {
                            direcao = 1;
                            movimentosFrente = 0;
                            break;
                        }

                        if (voltarDireita() || esquerda()) {
                            direcao = 0;
                            movimentosFrente = 0;
                            break;
                        }

                        voltarEsquerda();
                    }

                    Motor.B.setSpeed(800);
                    Motor.C.setSpeed(800);

                    for (int i = 0; i < 560; i++) {
                        Motor.B.forward();
                        Motor.C.forward();
                    }

                    movimentosFrente++;
                    normal();
                }
            }
        }
    }
}