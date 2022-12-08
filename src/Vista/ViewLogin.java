package Vista;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class ViewLogin extends JFrame{
    // Subclase especial para crear el panel Login con fondo de imagen
    class PLogin extends JPanel{
        private Image img;
        @Override
        public void paint(Graphics g){
            img = new ImageIcon(pLogin.getClass().getResource("/Imagenes/background1_login.jpg")).getImage();
            g.drawImage(img, 0, 0, pLogin.getWidth(), pLogin.getHeight(), pLogin);
            setOpaque(false);
            super.paint(g);
        }
    }
    
    // Atributos para pestaña 1 (Login)
    private final PLogin pLogin; // Pestaña 1 (JPanel modificado con imagen)
    private final JPanel inputLogin;
    
    public JTextField inputUsr;
    public JPasswordField inputPass; // Inputs (usuario y contraseña)

    public JButton login; // Boton de "Login"
    
    private final JLabel tit1; // Título "Tecsup ASG Login"
    private final JLabel etiqUsr; // Etiqueta "Usuario"
    private final JLabel etiqPass; // Etiqueta "Contraseña"
    
    public ViewLogin(){
        setSize(500,500);
        setTitle("Inicio de Sesión");
        // PESTAÑA 1    ************************** LOGIN ***********************
        
        pLogin = new PLogin();
        pLogin.setLayout(null);

        inputLogin = new JPanel();
        inputLogin.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        pLogin.add(inputLogin);
        inputLogin.setBounds(90, 50, 300, 300);
        inputLogin.setLayout(null);
        
        tit1 = new JLabel("Tecsup ASG Login");
        etiqUsr = new JLabel("Usuario");
        etiqPass = new JLabel("Contraseña");
        
        inputLogin.add(tit1);
        inputLogin.add(etiqPass);
        inputLogin.add(etiqUsr);
        
        tit1.setBounds(97, 40, 106, 16);
        etiqPass.setBounds(50, 140, 80, 16);
        etiqUsr.setBounds(50, 100, 50, 16);
        
        inputPass = new JPasswordField();
        inputLogin.add(inputPass);
        inputUsr = new JTextField();
        inputLogin.add(inputUsr);
            
        inputPass.setBounds(140, 140, 100, 20);
        inputPass.setMargin(new Insets(0, 2, 2, 2));
        inputUsr.setBounds(140, 100, 100, 20);
        inputUsr.setMargin(new Insets(0, 2, 2, 2));
        
        login = new JButton("Login");
        inputLogin.add(login);
        login.setBounds(115, 200, 70, 20);
        
        setContentPane(pLogin);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    public static void main(String[] args) {
        ViewLogin a = new ViewLogin();
    }
}
