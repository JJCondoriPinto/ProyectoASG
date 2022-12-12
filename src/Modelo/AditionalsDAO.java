package Modelo;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class AditionalsDAO {
    private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String USER = "GESTION_ASG";
    private final String PASS = "oracle123";
    
    public void insertUsrCarr(int idUsr, String idCarr){
        try {
            // Establecemos conexión
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            // Creamos un callabeStatement
            CallableStatement sent = con.prepareCall("{call insertUsrCarr(?,?)}");
            sent.setInt(1, idUsr);
            sent.setString(2, idCarr);
            
            if(sent.execute())
                JOptionPane.showMessageDialog(null, "Inserción fallida", "Insert Usr-Carr", 2);
            else
                JOptionPane.showMessageDialog(null, "Inserción exitosa", "Insert Usr-Carr", 1);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de conexión");
        }
    }
    
    public void deleteUsrCarr(int idUsr, String idCarr){
        try {
            // Establecemos conexión
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            // Creamos un callabeStatement
            CallableStatement sent = con.prepareCall("{call deleteUsrCarr(?,?)}");
            sent.setInt(1, idUsr);
            sent.setString(2, idCarr);
            
            if(sent.execute())
                JOptionPane.showMessageDialog(null, "Eliminación fallida", "Delete Usr-Carr", 2);
            else
                JOptionPane.showMessageDialog(null, "Eliminación exitosa", "Delete Usr-Carr", 1);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error de conexión");
        }        
    }
    
    public void insertUsrCarrInTable(DefaultTableModel modelo){
        try {
            // Establecemos conexión
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            // Consultamos los valores de la base de datos (Id Usr e Id Carr)
            Statement sent = con.createStatement();
            
            ResultSet rest = sent.executeQuery("SELECT * FROM USUARIOS_CARRERAS "
                                             + "ORDER BY ID_USR,ID_CARR");
            
            // Actualizamos el modelo de la tabla
            while(modelo.getRowCount() > 0)
                modelo.removeRow(0);
            
            // Ingresamos valores al modelo
            String[] data = new String[modelo.getColumnCount()];
            while(rest.next()){
                for(int i = 0; i < data.length; i++)
                    data[i] = rest.getString(i+1);
                modelo.addRow(data);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al ingresar datos en Usuarios - Carreras", "Error Select", 2);
        }
    }
    
    public void insertCarrInTable(DefaultTableModel modelo){
        try{
            // Extablecemos conexión
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            // Consultamos valores en la base de datos (Id_Carr, Nom_Carr, Id_Depto)
            Statement sent = con.createStatement();
            
            ResultSet rest = sent.executeQuery("SELECT * FROM CARRERAS");
            
            // Limpiamos datos de tabla
            while(modelo.getRowCount() > 0)
                modelo.removeRow(0);
            
            // Insertamos datos en modelo de tabla
            String[] data = new String[modelo.getColumnCount()];
            while(rest.next()){
                for(int i = 0; i < data.length; i++)
                    data[i] = rest.getString(i+1);
                modelo.addRow(data);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al ingresar datos en Carreras", "Error Select", 2);
        }
    }
    
    public void insertDeptoInTable(DefaultTableModel modelo){
        try{
            // Extablecemos conexión
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            // Consultamos valores en la base de datos (Id_Depto, Nom_Depto)
            Statement sent = con.createStatement();
            
            ResultSet rest = sent.executeQuery("SELECT * FROM DEPARTAMENTOS");
            
            // Limpiamos datos de tabla
            while(modelo.getRowCount() > 0)
                modelo.removeRow(0);
            
            // Insertamos datos en modelo de tabla
            String[] data = new String[modelo.getColumnCount()];
            while(rest.next()){
                for(int i = 0; i < data.length; i++)
                    data[i] = rest.getString(i+1);
                modelo.addRow(data);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al ingresar datos en Departamentos", "Error Select", 2);
        }
    }
    
    public void insertArdOrdenInTable(int campo, int forma, DefaultTableModel modelo){
        try{
            String[] campos = {"ID_ARD","COD_AULA","NUM_AULA","ID_PAB","NOM_PAB"};
            String[] formas = {"ASC","DESC"};
            
            // Establecemos conexión
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            // Creamos una sentencia preparecall
            PreparedStatement sent = con.prepareCall("SELECT A.ID_ARD,AU.COD_AULA,AU.NUM_AULA,P.ID_PAB,P.NOM_PAB " +
                                                     "FROM ARDUINOS A " +
                                                     "JOIN AULAS AU ON A.COD_AULA = AU.COD_AULA " +
                                                     "JOIN PABELLONES P ON AU.ID_PAB = P.ID_PAB " +
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
            JOptionPane.showMessageDialog(null, "Error al tratar de ordenar valores en Reporte Arduino", "Error Report Arduinos", 2);
        }
    }
    
    public void insertArdFiltInTable(int campo, String condition, DefaultTableModel modelo){
        try{
            String[] campos = {"ID_ARD","COD_AULA","NUM_AULA","ID_PAB","NOM_PAB"};
            
            // Establecemos conexión
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            // Creamos una sentencia preparecall
            PreparedStatement sent = con.prepareCall("SELECT A.ID_ARD,AU.COD_AULA,AU.NUM_AULA,P.ID_PAB,P.NOM_PAB " +
                                                     "FROM ARDUINOS A " +
                                                     "JOIN AULAS AU ON A.COD_AULA = AU.COD_AULA " +
                                                     "JOIN PABELLONES P ON AU.ID_PAB = P.ID_PAB " +
                                                     "WHERE BY %s %s".formatted(campos[campo],
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
            ex.printStackTrace();
            if(ex instanceof SQLSyntaxErrorException)
                JOptionPane.showMessageDialog(null, "Condición no valida", "SQLSyntax Error", 2);
            else
                JOptionPane.showMessageDialog(null, "Error al tratar de filtrar valores en Reporte Arduino", "Error Report Arduinos", 2);
        }
    }
    
    public void insertArdBusqInTable(int campo, String val, DefaultTableModel modelo){
        try{
            String[] campos = {"ID_ARD","COD_AULA","NUM_AULA","ID_PAB","NOM_PAB"};
            
            // Establecemos conexión
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            // Creamos una sentencia preparecall
            PreparedStatement sent = con.prepareCall("SELECT A.ID_ARD,AU.COD_AULA,AU.NUM_AULA,P.ID_PAB,P.NOM_PAB " +
                                                     "FROM ARDUINOS A " +
                                                     "JOIN AULAS AU ON A.COD_AULA = AU.COD_AULA " +
                                                     "JOIN PABELLONES P ON AU.ID_PAB = P.ID_PAB " +
                                                     "WHERE BY %s=?".formatted(campos[campo]));
            
            // Si es ID ARD, NUM AULA O ID PAB (números)
            if(campo == 0||campo == 2||campo == 3) 
                try{
                    sent.setInt(1, Integer.parseInt(val));
                }catch(NumberFormatException ex){
                    throw new SQLSyntaxErrorException();
                }
            
            // Si no es, entonces es String o varchar2
            else 
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
            if(ex instanceof SQLSyntaxErrorException)
                JOptionPane.showMessageDialog(null, "Valor no valido", "Number Format Exception", 2);
        }
    }
}
