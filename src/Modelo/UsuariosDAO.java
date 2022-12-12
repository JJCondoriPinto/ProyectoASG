package Modelo;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
public class UsuariosDAO {
    private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String USER = "GESTION_ASG";
    private final String PASS = "oracle123";
    
    public String conectarPass(String usr, int codAula){
        try{
            // Establecemos conexión
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            Statement sent = con.createStatement();
            
            // Extraemos el rol del usuario y su contraseña
            ResultSet rest = sent.executeQuery("SELECT contrasenia,"
                                             + "id_rol FROM USUARIOS "
                                             + "WHERE usuario='"+usr+"'");
            rest.next();

            String pass = rest.getString(1);
            int idRol = rest.getInt(2);
            
            // Validamos que el rol de este usuario tenga acceso a la puerta
            rest = sent.executeQuery("SELECT ID_ROL FROM ROLES_USER " +
                                     "WHERE ID_ROL IN (SELECT ID_ROL "+
                                                      "FROM PERMISOS "+
                                                      "WHERE COD_AULA = %d)".formatted(codAula));
            
            // Comprobamos que el rol del usuario esté contenido dentro de la lista que si tiene acceso
            boolean tieneAcceso = false;
            while(rest.next())
                if(rest.getInt(1) == idRol){
                    tieneAcceso = true;
                    break;
                }
            
            // Verificamos esa comprobación y enviamos la contraseña para la posterior validación el ControlASG
            if(tieneAcceso) return pass;
            else return null;
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Usuario no registrado","Login Error",2);
            return null;
        }
    }
    
    public void insertIntoUsr(String tabla, String usr, String pass, int id_rol){
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)){
                
            // Preparamos el llamado al procedimiento insertUsr
            CallableStatement sent = con.prepareCall("{call insertUser(?,?,?)}");
            
            
            sent.setString(1, usr);
            sent.setString(2, pass);
            sent.setInt(3, id_rol);
            
            // Ejecutamos el procedimiento
            sent.execute();
            
            con.close();
            
            // Si no ocurre ningún error
            JOptionPane.showMessageDialog(null, "Se logró insertar un usuario","Confirmación Sentencia Insert",1);
        }catch(SQLException e){
            if(e instanceof SQLIntegrityConstraintViolationException)
                JOptionPane.showMessageDialog(null, "Usuario ya registrado", "Error insert", 2);
            else
                JOptionPane.showMessageDialog(null, "Error al tratar de insertar el usuario","Error Insert",2);
            e.printStackTrace();
        }
    }
    
    public void deleteFrom(String tabla, int id_usr) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            
            Statement sent = con.createStatement();
            
            // Sentencia en String
            String sentencia = "DELETE FROM %s WHERE ID_USR=%d".formatted(tabla,id_usr);
            
            String mensaje;
            
            // Comprobamos que la sentencia ejecutada eliminó al usuario
            if(sent.executeUpdate(sentencia) == 1)
                mensaje = "Se logró eliminar al usuario";
            else
                mensaje = "No se encontró ningun usuario con ese ID";
            
            sent.close();
            con.close();
            
            JOptionPane.showMessageDialog(null, mensaje,"Confirmación Sentencia Delete",1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al tratar de eliminar el usuario","Error Delete",2);
            e.printStackTrace();
        }
    }
    
    public void updateSet(String tabla, String usr, String pass, int id_rol, String id_usr){
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            Statement sent = con.createStatement();
            
            // Se decide si esque se va a actualizar el usuario también
            String optionalSent;
            if(usr.equals("")) optionalSent = "";
            else optionalSent = "usuario='%s',".formatted(usr);
            
            // Creamos una pre sentencia en String
            String sentencia = "UPDATE %s SET ".formatted(tabla)+
                               optionalSent+
                               "contrasenia='%s',".formatted(pass)+
                               "id_rol=%d ".formatted(id_rol)+
                               "WHERE id_usr=%s".formatted(id_usr);
            sent.executeUpdate(sentencia);
            sent.close();
            con.close();
            
            JOptionPane.showMessageDialog(null, "Se logró actualizar un usuario ","Confirmación Sentencia Update",1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al tratar de actualizar el usuario","Error Update",2);
            e.printStackTrace();
        }
    }
    
    public String[] getRoles(){
        try {
            // Establecemos conexión
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            // Creamos un objeto para la ejeucion de sentencias
            Statement sent = con.createStatement();
            
            // Sentencia para obtener el número de roles creados
            ResultSet numRoles = sent.executeQuery("SELECT COUNT(*) FROM ROLES_USER");
            numRoles.next();
            
            // Array que contendrá los nombres de cada rol
            String[] roles = new String[numRoles.getInt(1)];
            
            // Sentencia para obtener el nombre de cada rol
            ResultSet nameRoles = sent.executeQuery("SELECT ROL FROM ROLES_USER");
            
            // Introducimos los roles en el array
            int i = 0;
            while(nameRoles.next()){
                roles[i] = nameRoles.getString(1);
                i++;
            }
            
            sent.close();
            con.close();
            
            return roles;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        
    }
    
    public int getIdRol(String rol){
        try {
            // Establecemos conexión
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            // Creamos un objeto para la ejeucion de sentencias
            Statement sent = con.createStatement();
            
            // Sentencia para obtener el número de roles creados
            ResultSet numRoles = sent.executeQuery("SELECT id_rol FROM ROLES_USER WHERE rol='"+rol+"'");
            numRoles.next();
            return numRoles.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
        
    }
    
    public DefaultTableModel selectAll(int countColumns, DefaultTableModel modelo){
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            
            // ArrayList que contendrá cada fila de la tabla recuperada
            ArrayList<String[]> filas;
            
            try ( 
                // CallableStatement para hacer llamado del procedimiento
                CallableStatement cstmt = con.prepareCall("{? = call selectAllUsr}")) {
                
                // Datos para cada fila
                String[] datos;
                
                // Insertamos el parámetro de salida CURSOR en la sentencia
                cstmt.registerOutParameter(1, OracleTypes.CURSOR);
                
                // Ejecutamos la sentencia
                cstmt.execute();
                
                try ( 
                    // Obtenemos la tabla
                    ResultSet result = ((OracleCallableStatement)cstmt).getCursor(1)) {
                    
                    // Extraemos las filas de la tabla en el arraylist creado
                    datos = new String[countColumns];
                    
                    while(result.next()){
                        for(int i = 0; i < datos.length; i++)
                            datos[i] = result.getString(i+1);
                        modelo.addRow(datos);
                    }
                    con.close();
                }
                cstmt.close();
            }
            return modelo;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al tratar de mostrar el registro","Error Select",2);
            e.printStackTrace();
            return null;
        }
    }
    
    public String[] selectWhere(String campo, int countColumns,String valor){
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            
            // Preparamos la sentencia para encontrar al usuario
            CallableStatement sent;
            if(campo.equals("Usuario")){
                sent = con.prepareCall("{? = call selectOneUsr_User(?)}");
                sent.setString(2, valor);
            }else{
                sent = con.prepareCall("{? = call selectOneUsr(?)}");
                sent.setInt(2, Integer.parseInt(valor));
            }
            sent.registerOutParameter(1, OracleTypes.CURSOR);
            
            // Ejecutamos la sentencia 
            sent.execute();
            
            // Extraemos el conjunto de datos arrojado
            ResultSet rest = ((OracleCallableStatement)sent).getCursor(1);
            
            // Insertamos los datos en un array
            String[] usrFind = new String[countColumns];
            while(rest.next())
                for(int i = 0; i < usrFind.length; i++)
                    usrFind[i] = rest.getString(i+1);
            
            // Devolvemos un único array porque usuarios es UNIQUE y codigo es PK
            return usrFind;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de búsqueda con respecto a "+campo,"Error Select",2);
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean testConexion(){
        try{
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            con.close();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al tratar de conectar con la base de datos","Error Connection",2);
            return false;
        }
    }

}
