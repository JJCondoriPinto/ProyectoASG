package Control;

import Modelo.ArduinoDAO;
import Modelo.UsuariosDAO;
import Vista.ViewLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControlASGLogin implements ActionListener{
    private final UsuariosDAO usrDao;
    private final ViewLogin vistLogin;
    
    private final int COD_AULA = 1;
    
    public ControlASGLogin(UsuariosDAO usrDao, ViewLogin vistLogin){
        this.usrDao = usrDao;
        this.vistLogin = vistLogin;
    }
    
    public void initButtons(){
        vistLogin.login.addActionListener(this);
    }
    
    public void initView(){
        // Comprobamos que la conexión es posible
        if(!usrDao.testConexion())
            vistLogin.login.setEnabled(false);
        
        vistLogin.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // En pestaña de login
        if(e.getSource() == vistLogin.login){ 
            String usr = vistLogin.inputUsr.getText();
            if(!usr.equals("")){
                String pass = vistLogin.inputPass.getText();
                String resultPass = usrDao.conectarPass(usr,COD_AULA);
            
                // Para la prueba, se muestra la contraseña
                if(resultPass == null){
                    JOptionPane.showMessageDialog(vistLogin, "Usted no tiene acceso", "User Not Allowed", 2);
                    vistLogin.inputPass.setText("");
                }else if(resultPass.equals(pass)){
                
                    // Iniciamos la vista de abrir y cerrar puerta
                    ArduinoDAO ard = new ArduinoDAO();
                    
                    // Comprobamos si la conexión con el arduino fue exitosa
                    if(ard.getStatus() == 1){
                        vistLogin.inputPass.setText("");
                        ard.abrir();
                        ard.cerrar();
                    }
                
                }else{
                    JOptionPane.showMessageDialog(vistLogin, "Contraseña incorrecta", "Error Login", 2);
                    vistLogin.inputPass.setText("");
                }
            }else{
                JOptionPane.showMessageDialog(vistLogin, "Ingrese un usuario", "User Null", 2);
                vistLogin.inputPass.setText("");
            }
        }
    }
    
    public static void main(String[] args) {
        UsuariosDAO usrDao = new UsuariosDAO();
        ViewLogin vistLog = new ViewLogin();
        
        ControlASGLogin control = new ControlASGLogin(usrDao, vistLog);
        
        control.initButtons();
        control.initView();
    }
}

        