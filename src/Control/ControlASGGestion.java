package Control;

import Modelo.*;
import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControlASGGestion implements ActionListener{
    private final UsuariosDAO usrDao;
    private final PermisosDAO permDao;
    private final ViewGest vistGest;
    
    public ControlASGGestion(UsuariosDAO usrDao, PermisosDAO permDao, ViewGest vistGest){
        this.usrDao = usrDao;
        this.vistGest = vistGest;
        this.permDao = permDao;
    }
    
    public void initButtons(){
        
        // Botones de pestaña 1
        vistGest.deleteUsr.addActionListener(this);
        vistGest.insertUsr.addActionListener(this);
        vistGest.updateUsr.addActionListener(this);
        vistGest.mostrarTodo.addActionListener(this);
        vistGest.searchCode.addActionListener(this);
        vistGest.searchUsr.addActionListener(this);
        
        // Botones de pestaña 2
        vistGest.otorgarPerm.addActionListener(this);
        vistGest.denegarPerm.addActionListener(this);
        vistGest.aplicarBusquedaPerm.addActionListener(this);
        vistGest.aplicarFiltroPerm.addActionListener(this);
        vistGest.aplicarOrdenPerm.addActionListener(this);
    }
    
    public void initView(){
        
        // Comprobamos si la conexíon con la bd es posible
        if(usrDao.testConexion()){
            
            // Colocamos los posibles roles otorgados para el usuario
            for(int i = 0; i < usrDao.getRoles().length; i++)
                vistGest.roles.addItem(usrDao.getRoles()[i]);
            
            // Para la pestaña de gestión de permisos
            permDao.insertAulasTable(vistGest.modelReportAulas);
            permDao.insertRolesTable(vistGest.modelReportRoles);
            permDao.insertPermisosOrdenInTable(1, 1, vistGest.modelReportPerm);
            
        }else{
            vistGest.mostrarTodo.setEnabled(false);
            vistGest.insertUsr.setEnabled(false);
            vistGest.deleteUsr.setEnabled(false);
            vistGest.updateUsr.setEnabled(false);
            vistGest.searchCode.setEnabled(false);
            vistGest.searchUsr.setEnabled(false);
            vistGest.aplicarBusquedaPerm.setEnabled(false);
            vistGest.aplicarFiltroPerm.setEnabled(false);
            vistGest.aplicarOrdenPerm.setEnabled(false);
            vistGest.otorgarPerm.setEnabled(false);
            vistGest.denegarPerm.setEnabled(false);
        }
        vistGest.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Funcionalidad a todos los botones
        if(e.getSource() == vistGest.insertUsr){
            if(!vistGest.inputRegisUsr[0].getText().equals(""))
                if(!vistGest.inputRegisUsr[1].getText().equals("")&&!vistGest.inputRegisUsr[2].getText().equals(""))
                    // Si la contraseña y la confimación son iguales
                    if(vistGest.inputRegisUsr[1].getText().equals(vistGest.inputRegisUsr[2].getText())){
                
                        // Obtención de id_rol para usuario
                        int id_Rol = usrDao.getIdRol((String)vistGest.roles.getSelectedItem());
                        usrDao.insertIntoUsr("USUARIOS",vistGest.inputRegisUsr[0].getText(),
                                      vistGest.inputRegisUsr[1].getText(),id_Rol);
                        
                        e.setSource(vistGest.mostrarTodo);
                        actionPerformed(e);
                    }else
                        JOptionPane.showMessageDialog(vistGest, "Las contraseñas no coinciden","Error Valid Password",2);
                else
                    JOptionPane.showMessageDialog(vistGest, "Debe insertar una contraseña","Error input Password",2);
            else
                JOptionPane.showMessageDialog(vistGest, "El campo usuario no puede estar vacio","Error input Login",2);
        }else if(e.getSource() == vistGest.updateUsr){
            if(!vistGest.inputRegisUsr[0].getText().equals(""))
                // Si la contraseña y la confimación son iguales
                if(vistGest.inputRegisUsr[1].getText().equals(vistGest.inputRegisUsr[2].getText())){
                
                    String id_usr = JOptionPane.showInputDialog(vistGest,"Ingrese el id del usuario a actualizar");
                
                    if(!id_usr.equals("")){
                
                        String[] datosUsr = usrDao.selectWhere("codigo", vistGest.modeloReporte.getColumnCount(), id_usr);
                        if(datosUsr[1]!=null&&datosUsr[2]!=null&&datosUsr[3]!=null){
                            String usrNow = datosUsr[1];
                            String pasNow = datosUsr[2];
                            String rolNow = datosUsr[3];
                
                            String mensaje = "Desea actualizar: "+
                                     "\nUsuario: %s -> ".formatted(usrNow)+vistGest.inputRegisUsr[0].getText()+
                                     "\nContraseña: %s -> ".formatted(pasNow)+vistGest.inputRegisUsr[1].getText()+
                                    "\nRol: %s -> ".formatted(rolNow)+vistGest.roles.getSelectedItem();
                
                            if(JOptionPane.showConfirmDialog(vistGest, mensaje, "Confirmacion de Update", JOptionPane.YES_NO_OPTION) != 1){ // 1 = NO
                    
                                // Obtención de id_rol para usuario
                                int id_Rol;
                                for(id_Rol = 1; id_Rol <= usrDao.getRoles().length; id_Rol++)
                                    if(vistGest.roles.getSelectedItem().equals(usrDao.getRoles()[id_Rol-1]))
                                        break;
                    
                                // Actualizamos los datos del usuario
                                usrDao.updateSet("usuarios", vistGest.inputRegisUsr[0].getText(),
                                                vistGest.inputRegisUsr[1].getText(),
                                                id_Rol, id_usr);
                                
                                e.setSource(vistGest.mostrarTodo);
                                actionPerformed(e);
                            }
                        }else
                            JOptionPane.showMessageDialog(vistGest, "Usuario no encontrado", "Not Found", 2);
                    }
                else
                    JOptionPane.showMessageDialog(vistGest, "El campo Usuario no puede estar vacio", "Error input Login", 2);
            }else
                JOptionPane.showMessageDialog(vistGest, "Las contraseñas no coinciden","Error Valid Password",2);
        }else if(e.getSource() == vistGest.deleteUsr){
            try{
                int id_usr = Integer.parseInt(JOptionPane.showInputDialog(vistGest,"Ingrese el id del usuario a eliminar"));
                usrDao.deleteFrom("USUARIOS", id_usr);
                
                e.setSource(vistGest.mostrarTodo);
                actionPerformed(e);
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(vistGest, "Ingrese un ID valido","NumberFormat Error",2);
            }
            
        }else if(e.getSource() == vistGest.mostrarTodo){
            
            // Actualizamos las filas y columnas del reporte
            while(vistGest.modeloReporte.getRowCount()>0)
                vistGest.modeloReporte.removeRow(0);
            
            usrDao.selectAll(vistGest.modeloReporte.getColumnCount(),
                    vistGest.modeloReporte);
            
        }else if(e.getSource() == vistGest.searchCode){
            
            // Extraemos los datos del usuario con codigo especifico
            String codigo = JOptionPane.showInputDialog(vistGest,"Indique el codigo del usuario");
            String[] datos = usrDao.selectWhere("codigo", vistGest.modeloReporte.getColumnCount(),codigo);
            
            // Actualizamos el modelo del reporte
            while(vistGest.modeloReporte.getRowCount()>0)
                vistGest.modeloReporte.removeRow(0);
            
            // Colocamos los datos en el modelo
            vistGest.modeloReporte.addRow(datos);
        }else if(e.getSource() == vistGest.searchUsr){
            
            // Extraemos los datos del usuario con usuario especifico
            String usr = JOptionPane.showInputDialog(vistGest,"Indique el usuario");
            String[] datos = usrDao.selectWhere("usuario", vistGest.modeloReporte.getColumnCount(),usr);
            
            // Actualizamos el modelo del reporte
            while(vistGest.modeloReporte.getRowCount()>0)
                vistGest.modeloReporte.removeRow(0);
            
            // Colocamos los datos en el modelo
            vistGest.modeloReporte.addRow(datos);
            
        // En pestaña de gestión de permisos  
        }else if(e.getSource() == vistGest.otorgarPerm){
            try{
                // Extraemos el codigo de aula e id de rol
                int codAula = Integer.parseInt(vistGest.inputRegisPerm[0].getText());
                int idRol = Integer.parseInt(vistGest.inputRegisPerm[1].getText());
                
                // Insertamos en los registros de Permisos
                permDao.insertPerm(codAula, idRol);
                
                // Actualizamos la tabla de permisos
                permDao.insertPermisosOrdenInTable(0, 0, vistGest.modelReportPerm);
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(vistGest, "Caracteres no validos", "Number Exception", 2);
                ex.printStackTrace();
            }
        }else if(e.getSource() == vistGest.denegarPerm){
            try{
                // Extraemos el codigo de aula e id de rol
                int codAula = Integer.parseInt(vistGest.inputRegisPerm[0].getText());
                int idRol = Integer.parseInt(vistGest.inputRegisPerm[1].getText());
                
                // Insertamos en los registros de Permisos
                permDao.deletePerm(codAula, idRol);
                
                // Actualizamos la tabla de permisos
                permDao.insertPermisosOrdenInTable(0, 0, vistGest.modelReportPerm);
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(vistGest, "Caracteres no validos", "Number Exception", 2);
                ex.printStackTrace();
            }
        }else if(e.getSource() == vistGest.aplicarOrdenPerm){
            int posCampOrden = vistGest.input1OrdenPerm.getSelectedIndex();
            int formaOrden;
            if(vistGest.input2OrdenPerm.getSelection() == vistGest.ordenAsc.getModel())
                formaOrden = 0; // Ascendente
            else if(vistGest.input2OrdenPerm.getSelection() == vistGest.ordenDesc.getModel())
                formaOrden = 1; // Descendente
            else // Por defecto, ascendente
                formaOrden = 0;
            permDao.insertPermisosOrdenInTable(posCampOrden, formaOrden, vistGest.modelReportPerm);
        }else if(e.getSource() == vistGest.aplicarFiltroPerm){
            int posCampOrden = vistGest.input1FiltPerm.getSelectedIndex();
            
            // Condición en lenguaje SQL comenzando por el operador de comparación
            String condition = vistGest.input2FiltPerm.getText();
            
            permDao.insertPermisosFiltInTable(posCampOrden, condition, vistGest.modelReportPerm);
        }else if(e.getSource() == vistGest.aplicarBusquedaPerm){
            int posCampOrden = vistGest.input1BusqPerm.getSelectedIndex();
            
            // Extraemos el valor para la búsqueda
            String value = vistGest.input2BusqPerm.getText();
            
            permDao.insertPermBusquedaInTable(posCampOrden, value, vistGest.modelReportPerm);
        }
    }
    
    public static void main(String[] args) {
        
        UsuariosDAO usrDao  = new UsuariosDAO();
        PermisosDAO permDao = new PermisosDAO();
        ViewGest   vistGest = new ViewGest();
        ControlASGGestion  control = new ControlASGGestion(usrDao, permDao, vistGest);
        
        control.initView();
        control.initButtons();
        
    }
}
