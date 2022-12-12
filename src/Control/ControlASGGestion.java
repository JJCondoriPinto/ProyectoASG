package Control;

import Modelo.*;
import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControlASGGestion implements ActionListener{
    private final UsuariosDAO usrDao;
    private final PermisosDAO permDao;
    private final AditionalsDAO aditDao;
    private final ViewGest vistGest;
    
    public ControlASGGestion(UsuariosDAO usrDao, PermisosDAO permDao, 
                             AditionalsDAO aditDao, ViewGest vistGest){
        this.usrDao = usrDao;
        this.vistGest = vistGest;
        this.permDao = permDao;
        this.aditDao = aditDao;
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
        
        // Botones de pestaña 3 
        
        // Sub panel 1 (Usuarios y carreras)
        vistGest.addUsrCarr.addActionListener(this);
        vistGest.dropUsrCarr.addActionListener(this);
        
        // Sub panel 2 (Arduinos)
        vistGest.aplicarArdOrd.addActionListener(this);
        vistGest.aplicarArdFilt.addActionListener(this);
        vistGest.aplicarArdBusq.addActionListener(this);
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
            
            // Para la sub pestaña de Usuarios y Carreras en Gestión Adicional
            aditDao.insertCarrInTable(vistGest.modelReportCarr);
            aditDao.insertDeptoInTable(vistGest.modelReportDept);
            aditDao.insertUsrCarrInTable(vistGest.modelReportUsrCarr);
            
            // Para la sub pestaña de Arduinos en Gestión Adicional
            aditDao.insertArdOrdenInTable(0, 0, vistGest.modelReportArd);
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
        
        // Registrar un usuario
        if(e.getSource() == vistGest.insertUsr){
            if(!vistGest.inputRegisUsr[0].getText().equals(""))
                if(!vistGest.inputRegisUsr[1].getText().equals("")&&!vistGest.inputRegisUsr[2].getText().equals(""))
                    // Si la contraseña y la confimación son iguales
                    if(vistGest.inputRegisUsr[1].getText().equals(vistGest.inputRegisUsr[2].getText())){
                
                        // Obtención de id_rol para usuario
                        int id_Rol = usrDao.getIdRol((String)vistGest.roles.getSelectedItem());
                        
                        // Se inserta al usuario con un rol
                        usrDao.insertIntoUsr("USUARIOS",vistGest.inputRegisUsr[0].getText(),
                                      vistGest.inputRegisUsr[1].getText(),id_Rol);
                        
                        // Mostramos los cambios
                        e.setSource(vistGest.mostrarTodo);
                        actionPerformed(e);
                    }else
                        JOptionPane.showMessageDialog(vistGest, "Las contraseñas no coinciden","Error Valid Password",2);
                else
                    JOptionPane.showMessageDialog(vistGest, "Debe insertar una contraseña","Error input Password",2);
            else
                JOptionPane.showMessageDialog(vistGest, "El campo usuario no puede estar vacio","Error input Login",2);
            
        // Actualizar un usuario
        }else if(e.getSource() == vistGest.updateUsr){
            
            // Validamos que el campo de usuario no esté vacio
            if(!vistGest.inputRegisUsr[0].getText().equals(""))
                
                // Validamos que el campo de contraseña no esté vacio
                if(!vistGest.inputRegisUsr[1].getText().equals(""))
                
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
                                    int id_Rol = usrDao.getIdRol((String)vistGest.roles.getSelectedItem());
                    
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
                    }else
                        JOptionPane.showMessageDialog(vistGest, "Las contraseñas no coinciden","Error Valid Password",2);
                else
                    JOptionPane.showMessageDialog(vistGest, "Debe ingresar una contraseña", "Error valid Password", 2);
           else
                JOptionPane.showMessageDialog(vistGest, "El campo Usuario no puede estar vacio", "Error input Login", 2);
        
        // Eliminar a un usuario    
        }else if(e.getSource() == vistGest.deleteUsr){
            try{
                int id_usr = Integer.parseInt(JOptionPane.showInputDialog(vistGest,"Ingrese el id del usuario a eliminar"));
                usrDao.deleteFrom("USUARIOS", id_usr);
                
                e.setSource(vistGest.mostrarTodo);
                actionPerformed(e);
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(vistGest, "Ingrese un ID valido","NumberFormat Error",2);
            }
        
        // Mostrar los usuarios
        }else if(e.getSource() == vistGest.mostrarTodo){
            
            // Actualizamos las filas y columnas del reporte
            while(vistGest.modeloReporte.getRowCount()>0)
                vistGest.modeloReporte.removeRow(0);
            
            usrDao.selectAll(vistGest.modeloReporte.getColumnCount(),
                    vistGest.modeloReporte);
        
        // Buscar por id de usuario
        }else if(e.getSource() == vistGest.searchCode){
            
            // Extraemos los datos del usuario con codigo especifico
            String codigo = JOptionPane.showInputDialog(vistGest,"Indique el Id del usuario");
            String[] datos = usrDao.selectWhere("Id Usuario", vistGest.modeloReporte.getColumnCount(),codigo);
            
            // Actualizamos el modelo del reporte
            while(vistGest.modeloReporte.getRowCount()>0)
                vistGest.modeloReporte.removeRow(0);
            
            // Colocamos los datos en el modelo
            vistGest.modeloReporte.addRow(datos);
        
        // Buscar por nombre de usuario
        }else if(e.getSource() == vistGest.searchUsr){
            
            // Extraemos los datos del usuario con usuario especifico
            String usr = JOptionPane.showInputDialog(vistGest,"Indique el usuario");
            String[] datos = usrDao.selectWhere("Usuario", vistGest.modeloReporte.getColumnCount(),usr);
            
            // Actualizamos el modelo del reporte
            while(vistGest.modeloReporte.getRowCount()>0)
                vistGest.modeloReporte.removeRow(0);
            
            // Colocamos los datos en el modelo
            vistGest.modeloReporte.addRow(datos);
            
        // En pestaña de gestión de permisos
        
        // Otorgar un permiso
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
            
        // Denegar un permiso
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
            
        // Aplicar cambios para la visualización en orden
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
            
        // Aplicar cambios para la visualización de filtro
        }else if(e.getSource() == vistGest.aplicarFiltroPerm){
            int posCampOrden = vistGest.input1FiltPerm.getSelectedIndex();
            
            // Condición en lenguaje SQL comenzando por el operador de comparación
            String condition = vistGest.input2FiltPerm.getText();
            
            permDao.insertPermisosFiltInTable(posCampOrden, condition, vistGest.modelReportPerm);
        
        // Aplicar cambios para la visualización de búsqueda
        }else if(e.getSource() == vistGest.aplicarBusquedaPerm){
            int posCampOrden = vistGest.input1BusqPerm.getSelectedIndex();
            
            // Extraemos el valor para la búsqueda
            String value = vistGest.input2BusqPerm.getText();
            
            permDao.insertPermBusquedaInTable(posCampOrden, value, vistGest.modelReportPerm);
        
        // Insertar Usuario - Carrera
        }else if(e.getSource() == vistGest.addUsrCarr){
            try{
                // Extraepmos id de usuario e id de carrera ingresados
                int idUsr = Integer.parseInt(vistGest.inputUsrCarr[0].getText());
                String idCarr = vistGest.inputUsrCarr[1].getText();
                
                // Enviamos valores a AditionalsDAO
                aditDao.insertUsrCarr(idUsr, idCarr);
                
                // Mostramos cambios en tabla
                aditDao.insertUsrCarrInTable(vistGest.modelReportUsrCarr);
                
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(vistGest, "Caracteres no validos");
                vistGest.inputUsrCarr[0].setText("");
                vistGest.inputUsrCarr[1].setText("");
            }
        
        // Borrar Usuario - Carrera
        }else if(e.getSource() == vistGest.dropUsrCarr){
            try{
                // Extraepmos id de usuario e id de carrera ingresados
                int idUsr = Integer.parseInt(vistGest.inputUsrCarr[0].getText());
                String idCarr = vistGest.inputUsrCarr[1].getText();
                
                // Enviamos valores a AditionalsDAO
                aditDao.deleteUsrCarr(idUsr, idCarr);
                
                // Mostramos cambios en tabla
                aditDao.insertUsrCarrInTable(vistGest.modelReportUsrCarr);
                
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(vistGest, "Caracteres no validos");
                vistGest.inputUsrCarr[0].setText("");
                vistGest.inputUsrCarr[1].setText("");
            }
        
        // Aplicar cambios de orden en Arduinos
        }else if(e.getSource() == vistGest.aplicarArdOrd){
            int posCampOrden = vistGest.input1ConsultArdOrd.getSelectedIndex();
            int formaOrden;
            if(vistGest.input2OrdenPerm.getSelection() == vistGest.consultArdAsc.getModel())
                formaOrden = 0; // Ascendente
            else if(vistGest.input2OrdenPerm.getSelection() == vistGest.consultArdDesc.getModel())
                formaOrden = 1; // Descendente
            else // Por defecto, ascendente
                formaOrden = 0;
            
            // Mostramos valores en orden para la tabla
            aditDao.insertArdOrdenInTable(posCampOrden, formaOrden, vistGest.modelReportArd);
            
        // Aplicar cambios de filtro en Arduinos
        }else if(e.getSource() == vistGest.aplicarArdFilt){
            int posCampOrden = vistGest.input1ConsultArdFilt.getSelectedIndex();
            
            // Condición en lenguaje SQL comenzando por el operador de comparación
            String condition = vistGest.input2ConsultArdFilt.getText();
            
            // Mostramos valores de filtro en tabla
            aditDao.insertArdFiltInTable(posCampOrden, condition, vistGest.modelReportArd);
            
        // Aplicar cambios de busqueda en Arduinos
        }else if(e.getSource() == vistGest.aplicarArdBusq){
            int posCampOrden = vistGest.input1ConsultArdBusq.getSelectedIndex();
            
            // Extraemos el valor para la búsqueda
            String value = vistGest.input2ConsultArdBusq.getText();
            
            // Mostramos valores de búsqueda en tabla
            aditDao.insertArdBusqInTable(posCampOrden, value, vistGest.modelReportArd);
        }
    }
    
    public static void main(String[] args) {
        
        UsuariosDAO usrDao  = new UsuariosDAO();
        PermisosDAO permDao = new PermisosDAO();
        AditionalsDAO aditDao = new AditionalsDAO();
        ViewGest   vistGest = new ViewGest();
        ControlASGGestion  control = new ControlASGGestion(usrDao, permDao, aditDao, vistGest);
        
        control.initView();
        control.initButtons();
        
    }
}
