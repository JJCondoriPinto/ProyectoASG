import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class Prueba{
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "GESTION_ASG";
    private static final String PASS = "oracle123";
    public static void main(String[] args) {
        
    }
    public static ArrayList<String[]> selectAll(String tabla, int countColumns){
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            
            Statement sent = con.createStatement();
            
            // ArrayList que contendrá cada fila de la tabla recuperada
            ArrayList<String[]> filas = new ArrayList<>();
            
            try ( 
                // CallableStatement para hacer llamado del procedimiento
                CallableStatement cstmt = con.prepareCall("{? = call selectAllUsr}")) {
                
                // Datos para cada fila
                String[] datos;
                
                // Insertamos el parámetro de salida CURSOR en la sentencia
                cstmt.registerOutParameter(1, OracleTypes.CURSOR);
                
                // Ejecutamos la sentencia
                cstmt.execute();
                
                // Obtenemos la tabla
                ResultSet result = ((OracleCallableStatement)cstmt).getCursor(1);

                // Extraemos las filas de la tabla en el arraylist creado
                while(result.next()){
                    datos = new String[countColumns];
                    for(int i = 0; i < datos.length; i++)
                        datos[i] = result.getString(i+1);
                    filas.add(datos);
                }   
                con.close();
            }
            return filas;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al tratar de mostrar el registro","Error Select",2);
            e.printStackTrace();
            return null;
        }
    }
}
