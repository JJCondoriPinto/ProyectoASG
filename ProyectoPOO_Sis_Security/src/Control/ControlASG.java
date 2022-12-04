package Control;
import Modelo.UsuariosDAO;
import Vista.ViewPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Paq1.Window;
import javax.swing.JOptionPane;
public class ControlASG implements ActionListener{
    private final UsuariosDAO usrDao;
    private final ViewPanel vistPan;
    public ControlASG(UsuariosDAO usrDao, ViewPanel vistPan){
        this.usrDao = usrDao;
        this.vistPan = vistPan;
    }
    
    public void initButtons(){
        vistPan.login.addActionListener(this);
        vistPan.deleteUsr.addActionListener(this);
        vistPan.insertUsr.addActionListener(this);
        vistPan.updateUsr.addActionListener(this);
        vistPan.mostrarTodo.addActionListener(this);
        vistPan.searchCode.addActionListener(this);
        vistPan.searchUsr.addActionListener(this);
    }
    
    public void initView(){
        
        // Comprobamos si la conexíon con la bd es posible
        if(usrDao.testConexion()){
            
            // Colocamos los posibles roles otorgados para el usuario
            for(int i = 0; i < usrDao.getRoles().length; i++)
                vistPan.roles.addItem(usrDao.getRoles()[i]);
        }else{
            vistPan.mostrarTodo.setEnabled(false);
            vistPan.login.setEnabled(false);
            vistPan.insertUsr.setEnabled(false);
            vistPan.deleteUsr.setEnabled(false);
            vistPan.updateUsr.setEnabled(false);
            vistPan.searchCode.setEnabled(false);
            vistPan.searchUsr.setEnabled(false);
        }
        vistPan.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Funcionalidad a todos los botones
        
        // En pestaña de login
        if(e.getSource() == vistPan.login){ 
            String usr = vistPan.inputUsr.getText();
            String pass = vistPan.inputPass.getText();
            String resultPass = usrDao.conectarPass(usr,1);
            System.out.println(resultPass);
            if(resultPass.equals(pass)){
                Window a = new Window();
                a.setVisible(true);
                a.setLocationRelativeTo(null);
            }
        
        // En pestaña de Gestion
        }else if(e.getSource() == vistPan.insertUsr){
            if(!vistPan.inputRegisUsr[0].getText().equals(""))
                if(!vistPan.inputRegisUsr[1].getText().equals("")&&!vistPan.inputRegisUsr[2].getText().equals(""))
                    // Si la contraseña y la confimación son iguales
                    if(vistPan.inputRegisUsr[1].getText().equals(vistPan.inputRegisUsr[2].getText())){
                
                        // Obtención de id_rol para usuario
                        int id_Rol = usrDao.getIdRol((String)vistPan.roles.getSelectedItem());
                        usrDao.insertIntoUsr("USUARIOS",vistPan.inputRegisUsr[0].getText(),
                                      vistPan.inputRegisUsr[1].getText(),id_Rol);
                        
                        e.setSource(vistPan.mostrarTodo);
                        actionPerformed(e);
                    }else
                        JOptionPane.showMessageDialog(vistPan, "Las contraseñas no coinciden","Error Valid Password",2);
                else
                    JOptionPane.showMessageDialog(vistPan, "Debe insertar una contraseña","Error input Password",2);
            else
                JOptionPane.showMessageDialog(vistPan, "El campo usuario no puede estar vacio","Error input Login",2);
        }else if(e.getSource() == vistPan.updateUsr){
            if(!vistPan.inputRegisUsr[0].getText().equals(""))
                // Si la contraseña y la confimación son iguales
                if(vistPan.inputRegisUsr[1].getText().equals(vistPan.inputRegisUsr[2].getText())){
                
                    String id_usr = JOptionPane.showInputDialog(vistPan,"Ingrese el id del usuario a actualizar");
                
                    if(!id_usr.equals("")){
                
                        String[] datosUsr = usrDao.selectWhere("codigo", vistPan.modeloReporte.getColumnCount(), id_usr);
                        if(datosUsr[1]!=null&&datosUsr[2]!=null&&datosUsr[3]!=null){
                            String usrNow = datosUsr[1];
                            String pasNow = datosUsr[2];
                            String rolNow = datosUsr[3];
                
                            String mensaje = "Desea actualizar: "+
                                     "\nUsuario: %s -> ".formatted(usrNow)+vistPan.inputRegisUsr[0].getText()+
                                     "\nContraseña: %s -> ".formatted(pasNow)+vistPan.inputRegisUsr[1].getText()+
                                    "\nRol: %s -> ".formatted(rolNow)+vistPan.roles.getSelectedItem();
                
                            if(JOptionPane.showConfirmDialog(vistPan, mensaje, "Confirmacion de Update", JOptionPane.YES_NO_OPTION) != 1){ // 1 = NO
                    
                                // Obtención de id_rol para usuario
                                int id_Rol;
                                for(id_Rol = 1; id_Rol <= usrDao.getRoles().length; id_Rol++)
                                    if(vistPan.roles.getSelectedItem().equals(usrDao.getRoles()[id_Rol-1]))
                                        break;
                    
                                // Actualizamos los datos del usuario
                                usrDao.updateSet("usuarios", vistPan.inputRegisUsr[0].getText(),
                                                vistPan.inputRegisUsr[1].getText(),
                                                id_Rol, id_usr);
                                
                                e.setSource(vistPan.mostrarTodo);
                                actionPerformed(e);
                            }
                        }else
                            JOptionPane.showMessageDialog(vistPan, "Usuario no encontrado", "Not Found", 2);
                    }
                else
                    JOptionPane.showMessageDialog(vistPan, "El campo Usuario no puede estar vacio", "Error input Login", 2);
            }else
                JOptionPane.showMessageDialog(vistPan, "Las contraseñas no coinciden","Error Valid Password",2);
        }else if(e.getSource() == vistPan.deleteUsr){
            try{
                int id_usr = Integer.parseInt(JOptionPane.showInputDialog(vistPan,"Ingrese el id del usuario a eliminar"));
                usrDao.deleteFrom("USUARIOS", id_usr);
                
                e.setSource(vistPan.mostrarTodo);
                actionPerformed(e);
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(vistPan, "Ingrese un ID valido","NumberFormat Error",2);
            }
            
        }else if(e.getSource() == vistPan.mostrarTodo){
            
            // Actualizamos las filas y columnas del reporte
            while(vistPan.modeloReporte.getRowCount()>0)
                vistPan.modeloReporte.removeRow(0);
            
            usrDao.selectAll("USUARIO", vistPan.modeloReporte.getColumnCount(),
                    vistPan.modeloReporte);
            
        }else if(e.getSource() == vistPan.searchCode){
            
            // Extraemos los datos del usuario con codigo especifico
            String codigo = JOptionPane.showInputDialog(vistPan,"Indique el codigo del usuario");
            String[] datos = usrDao.selectWhere("codigo", vistPan.modeloReporte.getColumnCount(),codigo);
            
            // Actualizamos el modelo del reporte
            while(vistPan.modeloReporte.getRowCount()>0)
                vistPan.modeloReporte.removeRow(0);
            
            // Colocamos los datos en el modelo
            vistPan.modeloReporte.addRow(datos);
        }else if(e.getSource() == vistPan.searchUsr){
            
            // Extraemos los datos del usuario con usuario especifico
            String usr = JOptionPane.showInputDialog(vistPan,"Indique el usuario");
            String[] datos = usrDao.selectWhere("usuario", vistPan.modeloReporte.getColumnCount(),usr);
            
            // Actualizamos el modelo del reporte
            while(vistPan.modeloReporte.getRowCount()>0)
                vistPan.modeloReporte.removeRow(0);
            
            // Colocamos los datos en el modelo
            vistPan.modeloReporte.addRow(datos);
        }
    }
    
    public static void main(String[] args) {
        
        UsuariosDAO usrDao  = new UsuariosDAO();
        ViewPanel   vistPan = new ViewPanel();
        ControlASG  control = new ControlASG(usrDao, vistPan);
        
        control.initView();
        control.initButtons();
        
    }
}
