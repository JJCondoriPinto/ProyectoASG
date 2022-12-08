package Modelo;

import panamahitek.Arduino.PanamaHitek_Arduino;
import javax.swing.JOptionPane;

public class ArduinoDAO{

    private final PanamaHitek_Arduino Arduino = new PanamaHitek_Arduino();
    private int status;

    public ArduinoDAO() {
        try {
            Arduino.arduinoTX("COM3", 9600);
            status = 1;
        } catch (Exception ex) {
            status = 0;
        }
    }
    public void abrir() {
        try {
            Arduino.sendData("0");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar abrir", "SendData Error", 2);
        }


    }

    public void cerrar() {
        try {
            Arduino.sendData("1");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar cerrar", "SendData Error", 2);
        }
    }
    
    public int getStatus(){
        return this.status;
    }
}
