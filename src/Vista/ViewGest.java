package Vista;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class ViewGest extends JFrame{
    
    // Atributo contenedor para pestañas del Frame
    private final JTabbedPane pestañas;
    
    // Atributos para pestaña 1 (Gestion Usuarios)
    private final JPanel pGestionUSR;  // Pestaña 1
    private final JPanel panRegisUsr; // Panel de registro para usuarios
    private final JPanel panReportUsr; // Panel para mostrar el reporte (tabla)
    private final JPanel panBotones1 ; // Panel para seccion 1 de opciones
    private final JPanel panBotones2 ; // Panel para seccion 2 de opciones
    
    private final JLabel[] etiqRegisUsr; // Etiquetas para registro de usuario
    
    public JTextField[] inputRegisUsr;  // Inputs para registro (usr,pass,confirmpass)
    
    public JComboBox<String> roles; // Input de Rol para el registro de usuario
    
    public JButton  insertUsr  , deleteUsr , updateUsr, // Acciones DAO
                    mostrarTodo, searchCode, searchUsr;
    private final JButton nextOptions;  // Avanzar a la 2da seccion de opciones
    private final JButton prevOptions;  // Retroceder a la 1ra seccion de opciones
    
    private final JScrollPane scrollReporte;  // Panel con Scroll para reporte 
    
    private final JTable reporte;     // Tabla de reporte
    
    public DefaultTableModel modeloReporte; // Modelo de tabla (filas y columnas)
    
    // Atributos para pestaña 2 (Gestión permisos)
    private final JPanel pGestionPerm; // Pestaña para gestion de permisos
    
    private final JPanel panRegisPerm;
    private final JLabel[] etiqRegisPerm; // Etiquetas para el registro
    public JTextField[] inputRegisPerm;
    public JButton otorgarPerm;
    public JButton denegarPerm;
    
    private final JPanel panAulas;
    private final JScrollPane scrollAulas;
    private final JTable reportAulas;
    public DefaultTableModel modelReportAulas; // Modificable desde controlador

    private final JPanel panRoles;
    private final JScrollPane scrollRoles;
    private final JTable reportRoles;
    public DefaultTableModel modelReportRoles; // Modificable desde controlador
    
    private final JPanel panReportPerm;
    private final JScrollPane scrollPerm;
    private final JTable reportPerm;
    public DefaultTableModel modelReportPerm; // Modificable desde controlador
    
    // Elementos para realizar el ordenamiento del reporte de permisos
    public JPanel panOption1Perm;
    private final JLabel[] etiqOrdenReportPerm;   
    public JComboBox<String> input1OrdenPerm;
    public ButtonGroup input2OrdenPerm;
    public JRadioButton ordenAsc;
    public JRadioButton ordenDesc;
    
    // Elementos para realizar el filtro del reporte de permisos
    public JPanel panOption2Perm;
    private final JLabel[] etiqFiltroReportPerm;
    public JComboBox<String> input1FiltPerm;
    public JTextField input2FiltPerm;
    
    // Elementos para realizar busquedas en reporte de permisos
    public JPanel panOption3Perm;
    private final JLabel[] etiqBusqReportPerm;
    public JComboBox<String> input1BusqPerm;
    public JTextField input2BusqPerm;
    
    private JButton prevOptionsPerm;
    private JButton nextOptionsPerm;
    public JButton aplicarOrdenPerm;
    public JButton aplicarFiltroPerm;
    public JButton aplicarBusquedaPerm;
    
    // Atributos para pestaña 3 (Gestión adicional)
    private final JTabbedPane pGestionAd;
    
    // Primera sub pestaña (Gestión usuarios y carreras)
    private final JPanel pGestionAd_UsrCarr;
    
    private final JPanel panUsrCarr_Regis;
    private final JLabel[] etiqUsrCarr;
    public JTextField[] inputUsrCarr;
    public JButton addUsrCarr;
    public JButton dropUsrCarr;
    
    private final JPanel panReportUsrCarr;
    public DefaultTableModel modelReportUsrCarr;
    private final JScrollPane contentReportUsrCarr;
    private final JTable reportUsrCarr;
    
    private final JPanel panReportCarr;
    public DefaultTableModel modelReportCarr;
    private final JScrollPane contentReportCarr;
    private final JTable reportCarr;
    
    private final JPanel panReportDept;
    public DefaultTableModel modelReportDept;
    private final JScrollPane contentReportDept;
    private final JTable reportDept;
    
    // Segunda sub pestaña (Arduinos Disponibles)
    private final JPanel pArduinos;
    
    // Sub panel para consulta de arduinos disponibles
    private final JPanel panConsultArd;
    
    // Etiquetas para sub panel
    private final JLabel[][] etiqConsultArd;
    public JComboBox<String> input1ConsultArdOrd;
    public JComboBox<String> input1ConsultArdFilt;
    public JComboBox<String> input1ConsultArdBusq;
    
    public ButtonGroup input2ConsultArdOrd;
    public JRadioButton consultArdAsc;
    public JRadioButton consultArdDesc;
    
    public JTextField input2ConsultArdFilt;
    public JTextField input2ConsultArdBusq;
    
    public JButton aplicarArdOrd;
    public JButton aplicarArdFilt;
    public JButton aplicarArdBusq;
    
    // Sub panel para reporte de arduinos
    private final JPanel panReportArd;
    public DefaultTableModel modelReportArd;
    private final JScrollPane contReportArd;
    private final JTable reportArd;
    
    // Inicialización de Frame y atributos 
    public ViewGest(){
        setSize(500,500);
        setTitle("Gestión Administrativa");
        
        pestañas = new JTabbedPane();
        
        // PESTAÑA 1    ******************** GESTION DE USUARIO ****************
        
        pGestionUSR = new JPanel();
        pestañas.addTab("Gestión Users",pGestionUSR);
        pGestionUSR.setLayout(null);
        
        // Para inicializar el frame
        
        panRegisUsr = new JPanel();
        panRegisUsr.setBorder(BorderFactory.createTitledBorder("Registro de Usuarios"));
        pGestionUSR.add(panRegisUsr);
        panRegisUsr.setBounds(20, 20, 290, 200);
        panRegisUsr.setLayout(null);
        
        panBotones1 = new JPanel();
        panBotones1.setBorder(BorderFactory.createTitledBorder("Menú de acciones 1"));
        pGestionUSR.add(panBotones1);
        panBotones1.setBounds(310, 20, 160, 200);
        panBotones1.setLayout(null);
        
        panBotones2 = new JPanel();
        panBotones2.setBorder(BorderFactory.createTitledBorder("Menú de acciones 2"));
        pGestionUSR.add(panBotones2);
        panBotones2.setBounds(310, 20, 160, 200);
        panBotones2.setLayout(null);
        panBotones2.setVisible(false);
        
        panReportUsr = new JPanel();
        panReportUsr.setBorder(BorderFactory.createTitledBorder("Reporte"));
        pGestionUSR.add(panReportUsr);
        panReportUsr.setBounds(20, 220, 450, 200);
        panReportUsr.setLayout(null);
        
        // Etiquetas e inputs para el panel de registro de productos
        
        String[] inputCampos = {"Usuario","Contraseña","Confirmar","Rol de Usuario"};
        etiqRegisUsr = new JLabel[inputCampos.length];
        inputRegisUsr = new JTextField[inputCampos.length-1];
        for(int i = 0; i < inputCampos.length; i++){
            etiqRegisUsr[i] = new JLabel(inputCampos[i]);
            panRegisUsr.add(etiqRegisUsr[i]);
            etiqRegisUsr[i].setBounds(20, 37*(i+1), 100, 15);
            
            if(i!=inputCampos.length-1){
                inputRegisUsr[i] = new JTextField();
                panRegisUsr.add(inputRegisUsr[i]);
                inputRegisUsr[i].setBounds(120,37*(i+1), 140, 20);
                inputRegisUsr[i].setMargin(new Insets(1,3,1,3));
            }else{  // ComboBox para roles de usuario
                roles = new JComboBox<>();
                panRegisUsr.add(roles);
                roles.setBounds(120,37*(i+1), 140, 20);
            }
        }
        
        // Tabla o reporte de datos
        
        String[] camposUsr = {"Id_Usr","Usuario","Contraseña","Rol_Usr"};
        
        scrollReporte = new JScrollPane();
        reporte = new JTable();
        modeloReporte = new DefaultTableModel();
        for(String campo : camposUsr)
            modeloReporte.addColumn(campo);
        reporte.setModel(modeloReporte);
        scrollReporte.setViewportView(reporte);
        panReportUsr.add(scrollReporte);
        scrollReporte.setBounds(10, 25, 430, 165);
        
        // Botones y funcionalidad del primer menu de opciones
        
        insertUsr  = new JButton("Registrar" );
        deleteUsr  = new JButton("Eliminar"  );
        updateUsr  = new JButton("Actualizar");
        nextOptions = new JButton("Menú 2 >>");
        
        panBotones1.add(insertUsr );
        panBotones1.add(deleteUsr );
        panBotones1.add(updateUsr );
        panBotones1.add(nextOptions);
        
        insertUsr  .setBounds(18, 40 , 122, 20);
        deleteUsr  .setBounds(18, 80 , 122, 20);
        updateUsr  .setBounds(18, 120, 122, 20);
        nextOptions.setBounds(18, 160, 122, 20);
        
        nextOptions.addActionListener((ActionEvent nextO )-> {
            panBotones1.setVisible(false);
            panBotones2.setVisible(true);
        });
        
        // Botones y funcionalidad del segundo menu de opciones
        
        mostrarTodo = new JButton("Mostrar Todo"  );
        searchCode  = new JButton("Buscar por Id" );
        searchUsr   = new JButton("Buscar usuario");
        prevOptions = new JButton("<< Menú 1" );
        
        panBotones2.add(mostrarTodo );
        panBotones2.add(searchCode );
        panBotones2.add(searchUsr );
        panBotones2.add(prevOptions);
        
        mostrarTodo .setBounds(18, 40 , 122, 20);
        searchCode  .setBounds(18, 80 , 122, 20);
        searchUsr   .setBounds(18, 120, 122, 20);
        prevOptions .setBounds(18, 160, 122, 20);
         
        prevOptions.addActionListener((ActionEvent prevO)->   { 
            panBotones2.setVisible(false);
            panBotones1.setVisible(true);
        });
        
        // PESTAÑA 2 ******************** GESTIÓN DE PERMISOS ********************
        
        pGestionPerm = new JPanel();
        pestañas.add("Gestion Permisos", pGestionPerm);
        pGestionPerm.setLayout(null);
        
        panRegisPerm = new JPanel();
        pGestionPerm.add(panRegisPerm);
        panRegisPerm.setBorder(BorderFactory.createTitledBorder("Registro de Permisos"));
        panRegisPerm.setBounds(20, 20, 150, 200);
        panRegisPerm.setLayout(null);
        
        panAulas = new JPanel();
        pGestionPerm.add(panAulas);
        panAulas.setBorder(BorderFactory.createTitledBorder("Aulas Disponibles"));
        panAulas.setBounds(170,20,170,200);
        panAulas.setLayout(null);
        
        panRoles = new JPanel();
        pGestionPerm.add(panRoles);
        panRoles.setBorder(BorderFactory.createTitledBorder("Roles Disponibles"));
        panRoles.setBounds(340,20,130,200);
        panRoles.setLayout(null);
        
        panReportPerm = new JPanel();
        pGestionPerm.add(panReportPerm);
        panReportPerm.setBorder(BorderFactory.createTitledBorder("Permisos otorgados"));
        panReportPerm.setBounds(20, 220, 300, 200);
        panReportPerm.setLayout(null);
        
        panOption1Perm = new JPanel();
        pGestionPerm.add(panOption1Perm);
        panOption1Perm.setBorder(BorderFactory.createTitledBorder("Ordenamiento"));
        panOption1Perm.setBounds(320, 220, 150, 200);
        panOption1Perm.setLayout(null);
        panOption1Perm.setVisible(true);
        
        panOption2Perm = new JPanel();
        pGestionPerm.add(panOption2Perm);
        panOption2Perm.setBorder(BorderFactory.createTitledBorder("Filtrado"));
        panOption2Perm.setBounds(320, 220, 150, 200);
        panOption2Perm.setLayout(null);
        panOption2Perm.setVisible(false);
        
        panOption3Perm = new JPanel();
        pGestionPerm.add(panOption3Perm);
        panOption3Perm.setBorder(BorderFactory.createTitledBorder("Busqueda"));
        panOption3Perm.setBounds(320, 220, 150, 200);
        panOption3Perm.setLayout(null);
        panOption3Perm.setVisible(false);
        
        // Creación de elementos para panel de registro de permisos
        
        String[] inputCamposPerm = {"Cod Aula","Id Rol"};
        etiqRegisPerm = new JLabel[inputCamposPerm.length];
        inputRegisPerm = new JTextField[inputCamposPerm.length];
        int i;
        for(i = 0; i < inputCamposPerm.length; i++){
            etiqRegisPerm[i] = new JLabel(inputCamposPerm[i]);
            panRegisPerm.add(etiqRegisPerm[i]);
            etiqRegisPerm[i].setBounds(20, 37*(i+1), 50, 15);
            
            inputRegisPerm[i] = new JTextField();
            panRegisPerm.add(inputRegisPerm[i]);
            inputRegisPerm[i].setBounds(80, 36*(i+1), 50, 20);
            inputRegisPerm[i].setMargin(new Insets(1,3,1,3));
        }
        otorgarPerm = new JButton("Otorgar");
        panRegisPerm.add(otorgarPerm);
        otorgarPerm.setBounds(20, 40*(i+1), 110, 20);
        i++;
        denegarPerm = new JButton("Denegar");
        panRegisPerm.add(denegarPerm);
        denegarPerm.setBounds(20, 40*(i+1), 110, 20);
       
        // Creación de elementos para panel de aula
        String[] camposAulas = {"Cod","Num","Pabellon"}; // Cod(primario)
        modelReportAulas = new DefaultTableModel();
        for(String campo : camposAulas)
            modelReportAulas.addColumn(campo);
        reportAulas = new JTable(modelReportAulas);
        scrollAulas = new JScrollPane(reportAulas);
        panAulas.add(scrollAulas);
        scrollAulas.setBounds(10, 30, 150, 150);
        
        // Creación de elementos para panel de Roles
        String[] camposRoles = {"Id","Rol"}; // Id(primario) Rol(Nombre)
        modelReportRoles = new DefaultTableModel();
        for(String campo : camposRoles)
            modelReportRoles.addColumn(campo);
        reportRoles = new JTable(modelReportRoles);
        scrollRoles = new JScrollPane(reportRoles);
        panRoles.add(scrollRoles);
        scrollRoles.setBounds(10, 30, 110, 150);
        
        // Creación de elementos para reporte de Permisos
        String[] camposPerm = {"Num Aula","Pabellon","Roles Permitidos"};
        modelReportPerm = new DefaultTableModel();
        for(String campo : camposPerm)
            modelReportPerm.addColumn(campo);
        reportPerm = new JTable(modelReportPerm);
        scrollPerm = new JScrollPane(reportPerm);
        panReportPerm.add(scrollPerm);
        scrollPerm.setBounds(10, 25, 280, 165);
        
        // Elementos para la aplicación de cambios y navegación entre opciones
        nextOptionsPerm = new JButton(">>");
        nextOptionsPerm.setMargin(new Insets(0, 0, 0, 0));
        nextOptionsPerm.addActionListener((ActionEvent next)->{
            // Verificamos en qué panel de opciones nos encontramos
            if(panOption1Perm.isVisible()){ // Si estamos en el 1 avanzamos al 2
                panOption1Perm.setVisible(false);
                panOption2Perm.setVisible(true);
                prevOptionsPerm.setEnabled(true);
                
                panOption2Perm.add(prevOptionsPerm);
                panOption2Perm.add(nextOptionsPerm);
                
            }else if(panOption2Perm.isVisible()){ // Si estamos en 2 avanzamos a 3
                panOption2Perm.setVisible(false);
                panOption3Perm.setVisible(true);
                prevOptionsPerm.setEnabled(true);
                nextOptionsPerm.setEnabled(false); // Límite
                
                panOption3Perm.add(prevOptionsPerm);
                panOption3Perm.add(nextOptionsPerm);
            }
        });
        prevOptionsPerm = new JButton("<<");
        prevOptionsPerm.setMargin(new Insets(0, 0, 0, 0));
        prevOptionsPerm.setEnabled(false);
        prevOptionsPerm.addActionListener((ActionEvent prev)->{
            // Verificamos en qué panel de opciones nos encontramos
            if(panOption3Perm.isVisible()){ // Si estamos en el 3 retrocedemos al 2
                panOption3Perm.setVisible(false);
                panOption2Perm.setVisible(true);
                nextOptionsPerm.setEnabled(true);
                
                panOption2Perm.add(prevOptionsPerm);
                panOption2Perm.add(nextOptionsPerm);
                
            }else if(panOption2Perm.isVisible()){ // Si estamos en 2 vamos al 1
                panOption2Perm.setVisible(false);
                panOption1Perm.setVisible(true);
                nextOptionsPerm.setEnabled(true);
                prevOptionsPerm.setEnabled(false); // Límite
                
                panOption1Perm.add(prevOptionsPerm);
                panOption1Perm.add(nextOptionsPerm);
                
            }  
        });
        
        // Elementos para el panel de opciones de ordenamiento para permisos
        String[] etiquetasOrdenReport = {"Ordenar por","Forma"};
        etiqOrdenReportPerm = new JLabel[etiquetasOrdenReport.length];
        for(i = 0; i < etiqOrdenReportPerm.length; i++){
            etiqOrdenReportPerm[i] = new JLabel(etiquetasOrdenReport[i]);
            panOption1Perm.add(etiqOrdenReportPerm[i]);
            etiqOrdenReportPerm[i].setBounds(20, 30+55*i, 100, 15);
        }
        input1OrdenPerm = new JComboBox<>(camposPerm);
        panOption1Perm.add(input1OrdenPerm);
        input1OrdenPerm.setBounds(20, 30+25*(i-1), 100, 20);
        
        input2OrdenPerm = new ButtonGroup();
        
        ordenAsc = new JRadioButton("Ascendente");
        panOption1Perm.add(ordenAsc);
        ordenAsc.setBounds(25, 30+40*i, 105, 20);
        
        ordenDesc = new JRadioButton("Descendente");
        panOption1Perm.add(ordenDesc);
        ordenDesc.setBounds(25, 60+40*i, 105, 20);
        
        input2OrdenPerm.add(ordenAsc);
        input2OrdenPerm.add(ordenDesc);
        
        aplicarOrdenPerm = new JButton("Aplicar");
        aplicarOrdenPerm.setMargin(new Insets(0,0,0,0));
        panOption1Perm.add(aplicarOrdenPerm);
        aplicarOrdenPerm.setBounds(45, 90+40*i, 60, 20);
        
        panOption1Perm.add(prevOptionsPerm);
        prevOptionsPerm.setBounds(10,90+40*i,30,20);
        panOption1Perm.add(nextOptionsPerm);
        nextOptionsPerm.setBounds(110,90+40*i,30,20);
        
        // Elementos para el panel de opciones de filtrado para permisos
        String[] etiquetasFiltroReport = {"Filtrar por","Condicion"};
        etiqFiltroReportPerm = new JLabel[etiquetasFiltroReport.length];
        for(i = 0; i < etiqFiltroReportPerm.length; i++){
            etiqFiltroReportPerm[i] = new JLabel(etiquetasFiltroReport[i]);
            panOption2Perm.add(etiqFiltroReportPerm[i]);
            etiqFiltroReportPerm[i].setBounds(20, 30+65*i, 100, 15);
        }
        input1FiltPerm = new JComboBox<>(camposPerm);
        panOption2Perm.add(input1FiltPerm);
        input1FiltPerm.setBounds(20, 30+30*(i-1), 100, 20);
        
        input2FiltPerm = new JTextField();
        panOption2Perm.add(input2FiltPerm);
        input2FiltPerm.setBounds(20, 90+40*(i-1), 100, 20);
        
        aplicarFiltroPerm= new JButton("Aplicar");
        aplicarFiltroPerm.setMargin(new Insets(0,0,0,0));
        panOption2Perm.add(aplicarFiltroPerm);
        aplicarFiltroPerm.setBounds(45, 90+40*i, 60, 20);

        // Elementos para el panel de opciones de busqueda para permisos
        String[] etiquetasBusqReport = {"Buscar por","Valor"};
        etiqBusqReportPerm = new JLabel[etiquetasBusqReport.length];
        for(i = 0; i < etiqBusqReportPerm.length; i++){
            etiqBusqReportPerm[i] = new JLabel(etiquetasBusqReport[i]);
            panOption3Perm.add(etiqBusqReportPerm[i]);
            etiqBusqReportPerm[i].setBounds(20, 30+65*i, 100, 15);
        }
        input1BusqPerm = new JComboBox<>(camposPerm);
        panOption3Perm.add(input1BusqPerm);
        input1BusqPerm.setBounds(20, 30+30*(i-1), 100, 20);
        
        input2BusqPerm = new JTextField();
        panOption3Perm.add(input2BusqPerm);
        input2BusqPerm.setBounds(20, 90+40*(i-1), 100, 20);
        
        aplicarBusquedaPerm = new JButton("Aplicar");
        aplicarBusquedaPerm.setMargin(new Insets(0,0,0,0));
        panOption3Perm.add(aplicarBusquedaPerm);
        aplicarBusquedaPerm.setBounds(45, 90+40*i, 60, 20);
        
        // PESTAÑA 3 ********************** GESTION ADICIONAL ******************
        pGestionAd = new JTabbedPane();
        pestañas.addTab("Gestion Adicional", pGestionAd);
        
        // Sub pestaña 1 (Gestión de usuarios y carreras)
        pGestionAd_UsrCarr = new JPanel(null);
        pGestionAd.addTab("Usuarios y carreras", pGestionAd_UsrCarr);
        
        // Iniciamos un sub panel para el registro de usuarios y carreras
        panUsrCarr_Regis = new JPanel(null);
        panUsrCarr_Regis.setBorder(BorderFactory.createTitledBorder("Inserción Usuario - Carrera"));
        pGestionAd_UsrCarr.add(panUsrCarr_Regis);
        panUsrCarr_Regis.setBounds(20, 20, 190, 160);
        
        String[] camposInputUsrCarr = {"Id de Usuario","Id de Carrera"};
        etiqUsrCarr = new JLabel[camposInputUsrCarr.length];
        inputUsrCarr = new JTextField[camposInputUsrCarr.length];
        for(i = 0; i < etiqUsrCarr.length; i++){
            // Etiquetas Label
            etiqUsrCarr[i] = new JLabel(camposInputUsrCarr[i]);
            panUsrCarr_Regis.add(etiqUsrCarr[i]);
            etiqUsrCarr[i].setBounds(20, 37+(40*i), 100, 16);
            
            // Inputs
            inputUsrCarr[i] = new JTextField();
            panUsrCarr_Regis.add(inputUsrCarr[i]);
            inputUsrCarr[i].setBounds(120, 37+(40*i), 50, 20);
        }
        
        addUsrCarr = new JButton("Asignar");
        dropUsrCarr = new JButton("Quitar");
        
        panUsrCarr_Regis.add(addUsrCarr);
        panUsrCarr_Regis.add(dropUsrCarr);
        
        addUsrCarr.setBounds(20, 37+(40*i), 70, 20);
        addUsrCarr.setMargin(new Insets(0, 0, 0, 0));
        dropUsrCarr.setBounds(100, 37+(40*i), 70, 20);
        dropUsrCarr.setMargin(new Insets(0, 0, 0, 0));
        
        // Sub panel para el reporte de carreras
        
        panReportCarr = new JPanel(null);
        panReportCarr.setBorder(BorderFactory.createTitledBorder("Reporte de Carreras"));
        pGestionAd_UsrCarr.add(panReportCarr);
        panReportCarr.setBounds(210, 20, 255, 160);
        
        String[] camposCarr = {"Id Carrera","Nom Carrera","Id Depto"};
        modelReportCarr = new DefaultTableModel();
        for(String campo : camposCarr)
            modelReportCarr.addColumn(campo);
        reportCarr = new JTable(modelReportCarr);
        
        contentReportCarr = new JScrollPane(reportCarr);
        panReportCarr.add(contentReportCarr);
        contentReportCarr.setBounds(20, 30, 215, 110);
        
        // Sub panel para el reporte de departamentos
        
        panReportDept = new JPanel(null);
        panReportDept.setBorder(BorderFactory.createTitledBorder("Reporte de Departamentos"));
        pGestionAd_UsrCarr.add(panReportDept);
        panReportDept.setBounds(230, 180, 235, 215);
        
        String[] camposReportDept = {"Id Depto","Nom Depto"};
        modelReportDept = new DefaultTableModel();
        for(String campo : camposReportDept)
            modelReportDept.addColumn(campo);
        reportDept = new JTable(modelReportDept);
        
        contentReportDept = new JScrollPane(reportDept);
        panReportDept.add(contentReportDept);
        contentReportDept.setBounds(20, 30, 195, 175);
        
        // Sub panel para reporte de usuarios y carreras
        
        panReportUsrCarr = new JPanel(null);
        panReportUsrCarr.setBorder(BorderFactory.createTitledBorder("Reporte de Usuarios y Carreras"));
        pGestionAd_UsrCarr.add(panReportUsrCarr);
        panReportUsrCarr.setBounds(20, 180, 210, 215);
        
        String[] camposReportUsrCarr = {"Id Usuario","Id Carrera"};
        modelReportUsrCarr = new DefaultTableModel();
        for(String campo : camposReportUsrCarr)
            modelReportUsrCarr.addColumn(campo);
        
        reportUsrCarr = new JTable(modelReportUsrCarr);
        
        contentReportUsrCarr = new JScrollPane(reportUsrCarr);
        panReportUsrCarr.add(contentReportUsrCarr);
        contentReportUsrCarr.setBounds(20, 30, 170, 175);
        
        // Sub pestaña 2 (Consulta de arduinos disponibles)
        pArduinos = new JPanel(null);
        pGestionAd.add("Arduinos",pArduinos);
        
        // Sub panel para las consultas de arduinos
        panConsultArd = new JPanel(null);
        panConsultArd.setBorder(BorderFactory.createTitledBorder("Controlador de Consultas"));
        pArduinos.add(panConsultArd);
        panConsultArd.setBounds(20, 20, 440, 160);
        
        String[][] consultasArd =  {{"Ordenamiento","Filtrado","Búsqueda"},
                                    {"Forma","Condición","Valor"}};
        String[] camposReportArd = {"Id Arduino","Cod Aula","Num Aula","Id Pab","Pabellon"};
        input1ConsultArdBusq = new JComboBox<>(camposReportArd);
        input1ConsultArdFilt = new JComboBox<>(camposReportArd);
        input1ConsultArdOrd = new JComboBox<>(camposReportArd);
        
        etiqConsultArd = new JLabel[consultasArd.length][3];
        for(i = 0; i < etiqConsultArd.length; i++){
            for(int j = 0; j < etiqConsultArd[i].length; j++){
                etiqConsultArd[i][j] = new JLabel(consultasArd[i][j],SwingConstants.CENTER);
                panConsultArd.add(etiqConsultArd[i][j]);
                if(i==0)
                    etiqConsultArd[i][j].setBounds(20+150*j, 25, 100, 16);
                else{
                    panConsultArd.add(input1ConsultArdBusq);
                    input1ConsultArdBusq.setBounds(20, 45, 100, 20);
                    
                    panConsultArd.add(input1ConsultArdFilt);
                    input1ConsultArdFilt.setBounds(170, 45, 100, 20);
                    
                    panConsultArd.add(input1ConsultArdOrd);
                    input1ConsultArdOrd.setBounds(320, 45, 100, 20);
                    
                    etiqConsultArd[i][j].setBounds(20+150*j, 75, 100, 16);
                }
            }
        }
        
        // Ordenamiento
        input2ConsultArdOrd = new ButtonGroup();
        consultArdAsc = new JRadioButton("Asc");
        input2ConsultArdOrd.add(consultArdAsc);
        panConsultArd.add(consultArdAsc);
        consultArdAsc.setBounds(17, 95, 50, 16);
        
        consultArdDesc = new JRadioButton("Desc");
        input2ConsultArdOrd.add(consultArdDesc);
        panConsultArd.add(consultArdDesc);
        consultArdDesc.setBounds(67, 95, 70, 16);
        
        aplicarArdOrd = new JButton("Aplicar Orden");
        aplicarArdOrd.setMargin(new Insets(0, 0, 0, 0));
        panConsultArd.add(aplicarArdOrd);
        aplicarArdOrd.setBounds(20, 125, 100, 20);
        
        // Filtrado
        input2ConsultArdFilt = new JTextField();
        input2ConsultArdFilt.setMargin(new Insets(2,1,2,1));
        panConsultArd.add(input2ConsultArdFilt);
        input2ConsultArdFilt.setBounds(170, 95, 100, 20);
        
        aplicarArdFilt = new JButton("Aplicar Filtro");
        aplicarArdFilt.setMargin(new Insets(0, 0, 0, 0));
        panConsultArd.add(aplicarArdFilt);
        aplicarArdFilt.setBounds(170, 125, 100, 20);
        
        // Búsqueda
        input2ConsultArdBusq = new JTextField();
        input2ConsultArdBusq.setMargin(new Insets(2,1,2,1));
        panConsultArd.add(input2ConsultArdBusq);
        input2ConsultArdBusq.setBounds(320, 95, 100, 20);
        
        aplicarArdBusq = new JButton("Aplicar Búsq");
        aplicarArdBusq.setMargin(new Insets(0, 0, 0, 0));
        panConsultArd.add(aplicarArdBusq);
        aplicarArdBusq.setBounds(320, 125, 100, 20);
        
        // Sub panel para el reporte de arduinos
        panReportArd = new JPanel(null);
        panReportArd.setBorder(BorderFactory.createTitledBorder("Reporte de Arduinos"));
        pArduinos.add(panReportArd);
        panReportArd.setBounds(20, 180, 440, 210);

        modelReportArd = new DefaultTableModel();
        for(String campo : camposReportArd)
            modelReportArd.addColumn(campo);
        
        reportArd = new JTable(modelReportArd);
        
        contReportArd = new JScrollPane(reportArd);
        panReportArd.add(contReportArd);
        contReportArd.setBounds(10, 30, 420, 170);
        
        // PARAMETROS FINALES DE JFRAME
        
        getContentPane().add(pestañas); // Añade como ontenido al panel de pestañas
        setLocationRelativeTo(null); // Frame centrado en pantalla
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Finaliza el programa al cerrar Frame
        setResizable(false); // Tamaño fijo
    }
    /*   Prueba de vista
    public static void main(String[] args) { // Main para probar la vista
        ViewGest a = new ViewGest();
        a.setVisible(true);
    }
    */
}
