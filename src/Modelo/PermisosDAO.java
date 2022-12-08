package Modelo;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
public class PermisosDAO {
    private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String USER = "GESTION_ASG";
    private final String PASS = "oracle123";
    public void insertAulasTable(DefaultTableModel modelo){
        try {
            // Conectamos con la BD
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            // Preparamos el llamado a la función getAulas de la BD
            CallableStatement sent = con.prepareCall("{? = call getAulas}");
            
            // Insertamos el parámetro de salida CURSOR en la sentencia
            sent.registerOutParameter(1, OracleTypes.CURSOR);
            
            // Ejecutamos
            sent.execute();
            
            // Extraemos el CURSOR
            ResultSet rest = ((OracleCallableStatement) sent).getCursor(1);
            
            // Borramos las filas de la tabla
            while(modelo.getRowCount() > 0)
                modelo.removeRow(0);
            
            // Insertamos nuevas filas
            String[] datos = new String[modelo.getColumnCount()];
            while(rest.next()){
                for(int i = 0; i < datos.length; i++)
                    datos[i] = rest.getString(i+1);
                modelo.addRow(datos);
            }
            
            sent.close();
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al momento de extraer Aulas","Error getAulas",2);
            ex.printStackTrace();
        }
        
    }
    
    public void insertRolesTable(DefaultTableModel modelo){
        try {
            // Creamos la conexión con la BD
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            // Llamamos a la función getRoles de la BD
            CallableStatement sent = con.prepareCall("{? = call getRoles}");
            
            // Insertamos el parámetro de salida
            sent.registerOutParameter(1, OracleTypes.CURSOR);
            
            // Ejecutamos la sentencia
            sent.execute();
            
            // Extraemos los resultados
            ResultSet rest = ((OracleCallableStatement) sent).getCursor(1);
            
            // Limpiamos la tabla
            while(modelo.getRowCount() > 0)
                modelo.removeRow(0);
            
            // Insertamos los resultados en la tabla
            String[] data = new String[modelo.getColumnCount()];
            while(rest.next()){
                for(int i = 0; i < data.length; i++)
                    data[i] = rest.getString(i+1);
                modelo.addRow(data);
            }
            
            sent.close();
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al momento de extraer Roles", "Error getRoles", 2);
            ex.printStackTrace();
        }
    }
    
    public void insertPermisosOrdenInTable(int campo, int forma, DefaultTableModel modelo){
        try{
            String[] campos = {"NUM_AULA","NOM_PAB","ROL"};
            String[] formas = {"ASC","DESC"};
            
            // Establecemos conexión
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            // Creamos una sentencia preparecall
            PreparedStatement sent = con.prepareCall("SELECT A.NUM_AULA, PAB.NOM_PAB, R.ROL " +
                                                     "FROM PERMISOS P " +
                                                     "JOIN AULAS A ON P.COD_AULA = A.COD_AULA " +
                                                     "JOIN PABELLONES PAB ON A.ID_PAB = PAB.ID_PAB " +
                                                     "JOIN ROLES_USER R ON P.ID_ROL = R.ID_ROL " +
                                                     "ORDER BY %s %s".formatted(campos[campo],
                                                                      formas[forma]));
            ResultSet rest = sent.executeQuery();
            
            // Limpiamos la tabla
            while(modelo.getRowCount() > 0)
                modelo.removeRow(0);
            
            // Extraemos los datos y los ingresamos en la tabla
            String[] data = new String[modelo.getColumnCount()];
            while(rest.next()){
                for(int i = 0; i < data.length; i++)
                    data[i] = rest.getString(i+1);
                modelo.addRow(data);
            }
            
            sent.close();
            con.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public void insertPermisosFiltInTable(int campo, String condition, DefaultTableModel modelo){
        try{
            String[] campos = {"NUM_AULA","NOM_PAB","ROL"};
            
            // Establecemos conexión
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            // Creamos una sentencia preparecall
            PreparedStatement sent = con.prepareCall("SELECT A.NUM_AULA, PAB.NOM_PAB, R.ROL " +
                                                     "FROM PERMISOS P " +
                                                     "JOIN AULAS A ON P.COD_AULA = A.COD_AULA " +
                                                     "JOIN PABELLONES PAB ON A.ID_PAB = PAB.ID_PAB " +
                                                     "JOIN ROLES_USER R ON P.ID_ROL = R.ID_ROL " +
                                                     "WHERE %s %s".formatted(campos[campo],
                                                                    condition));
            ResultSet rest = sent.executeQuery();
            
            // Limpiamos la tabla
            while(modelo.getRowCount() > 0)
                modelo.removeRow(0);
            
            // Extraemos los datos y los ingresamos en la tabla
            String[] data = new String[modelo.getColumnCount()];
            while(rest.next()){
                for(int i = 0; i < data.length; i++)
                    data[i] = rest.getString(i+1);
                modelo.addRow(data);
            }
            
            sent.close();
            con.close();
        }catch(SQLException ex){
            if(ex instanceof SQLSyntaxErrorException)
                JOptionPane.showMessageDialog(null, "Condición no valida", "SQLSyntax Error", 2);
            ex.printStackTrace();
        }
    }
    
    public void insertPermBusquedaInTable(int campo, String val, DefaultTableModel modelo){
        try{
            String[] campos = {"NUM_AULA","NOM_PAB","ROL"};
            
            // Establecemos conexión
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            // Creamos una sentencia preparecall
            PreparedStatement sent = con.prepareCall("SELECT A.NUM_AULA, PAB.NOM_PAB, R.ROL " +
                                                     "FROM PERMISOS P " +
                                                     "JOIN AULAS A ON P.COD_AULA = A.COD_AULA " +
                                                     "JOIN PABELLONES PAB ON A.ID_PAB = PAB.ID_PAB " +
                                                     "JOIN ROLES_USER R ON P.ID_ROL = R.ID_ROL " +
                                                     "WHERE %s=?".formatted(campos[campo]));
            if(campo == 0) // Si es NUM AULA (número)
                sent.setInt(1, Integer.parseInt(val));
            else // Si no es Num Aula, entonces es String o varchar
                sent.setString(1, val);
            
            ResultSet rest = sent.executeQuery();
            
            // Limpiamos la tabla
            while(modelo.getRowCount() > 0)
                modelo.removeRow(0);
            
            // Extraemos los datos y los ingresamos en la tabla
            String[] data = new String[modelo.getColumnCount()];
            while(rest.next()){
                for(int i = 0; i < data.length; i++)
                    data[i] = rest.getString(i+1);
                modelo.addRow(data);
            }
            
            sent.close();
            con.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public void insertPerm(int codAula, int idRol){
        try {
            // Establecemos la conexión
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            // Creamos una sentencia para llamar al procedimiento insertPerm en BD
            CallableStatement sent = con.prepareCall("{call insertPerm(?,?)}");
            
            sent.setInt(1, codAula);
            sent.setInt(2, idRol);
            
            // Ejecutamos la sentencia y extraemos la respuesta
            sent.execute();
            JOptionPane.showMessageDialog(null, "Permiso asignado","Insert Perm",1);
        } catch (SQLException ex) {
            if(ex instanceof SQLIntegrityConstraintViolationException)
                JOptionPane.showMessageDialog(null, "Ese permiso ya fue otorgado", "Error insert", 2);
            else
                JOptionPane.showMessageDialog(null, "Error al momento de conectar o insertar registro", "DB Error", 2);
        }
        
    }
    
    public void deletePerm(int codAula, int idRol){
        try {
            // Establecemos la conexión
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            // Creamos una sentencia para llamar al procedimiento insertPerm en BD
            CallableStatement sent = con.prepareCall("{call deletePerm(?,?)}");
            
            sent.setInt(1, codAula);
            sent.setInt(2, idRol);
            
            // Ejecutamos la sentencia y extraemos la respuesta
            sent.execute();
            JOptionPane.showMessageDialog(null, "Permiso eliminado","Delete Perm",1);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al momento de conectar o eliminar registro", "DB Error", 2);
            ex.printStackTrace();
        }
        
    }
}
