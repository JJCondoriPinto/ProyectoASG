import java.util.logging.Level;
import java.util.logging.Logger;
import panamahitek.Arduino.PanamaHitek_Arduino;
public class ventana {
    static PanamaHitek_Arduino ard = new PanamaHitek_Arduino();
    public static void main(String[] args) {
        try {
            String port = "COM5";
            int set = 9600;
            ard.arduinoTX(port, set);
        } catch (Exception ex) {
            Logger.getLogger(ventana.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
