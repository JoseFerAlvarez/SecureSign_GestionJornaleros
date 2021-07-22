package com.example.securesign;

public class ClassUtilidades {

    //Base de datos
    public static final String NombreBasedeDatos="ADMINISTRACION";

    //Tabla USUARIO
    public static final String Tabla_usuario="USUARIO";
    public static final String Campo_nombreUsuario ="NOMBRE";
    public static final String Campo_pass="PASS";
    public static final String creaUsuario="CREATE TABLE " + Tabla_usuario + "(" + Campo_nombreUsuario + " TEXT PRIMARY KEY, " + Campo_pass + " TEXT)";

    //TABLA TEMPORADAS
    public static final String Tabla_Temporadas="TEMPORADA";
    public static final String Campo_nombreTemp="NOMBRETEMPORADA";
    public static final String Campo_usuarioTemporada="NOMBREUSUARIO";
    public static final String Campo_fechaInicio="FECHAINICIO";
    public static final String Campo_fechaFin="FECHAFIN";
    public static final String Campo_precioHora="PRECIOHORA";
    public static final String creaTemporada="CREATE TABLE " + Tabla_Temporadas + "(" + Campo_nombreTemp + " TEXT, "
            + Campo_usuarioTemporada + " TEXT, " + Campo_fechaInicio + " TEXT, " + Campo_fechaFin + " TEXT, "
            + Campo_precioHora  + " REAL, PRIMARY KEY (" + Campo_nombreTemp + ", " + Campo_usuarioTemporada + "), FOREIGN KEY ("
            + Campo_usuarioTemporada + ") REFERENCES " + Tabla_usuario + "(" + Campo_nombreUsuario + "))";

    //TABLA TRABAJADORES
    public static final String Tabla_Trabajadores="TRABAJADOR";
    public static final String Campo_NombreTrabajador="NOMBRETRABAJADOR";
    public static final String Campo_NombreCampania="NOMBRECAMPANIA";
    public static final String Campo_ApellidoTrabajador="APELLIDO";
    public static final String Campo_DniTrabajador="DNI";
    public static final String Campo_TlfTrabajador="TELEFONO";
    public static final String Campo_SegSocTrabajador="SEGURIDADSOCIAL";
    public static final String Campo_CueBancTrabajador="CUENTABANCO";
    public static final String creaTrabajadores="CREATE TABLE " + Tabla_Trabajadores + "(" + Campo_NombreTrabajador + " TEXT, "
            + Campo_NombreCampania + " TEXT, " + Campo_ApellidoTrabajador + " TEXT, " + Campo_DniTrabajador + " TEXT, "
            + Campo_TlfTrabajador + " TEXT, " + Campo_SegSocTrabajador + " TEXT, " + Campo_CueBancTrabajador +  " TEXT, PRIMARY KEY ("
            + Campo_NombreCampania + ", " + Campo_DniTrabajador +"), FOREIGN KEY (" + Campo_NombreCampania
            + ") REFERENCES " + Tabla_Temporadas + "(" + Campo_nombreTemp+ "))";

    //TABLA JORNADAS
    public static final String Tabla_Jornadas="JORNADA";
    public static final String Campo_IdJornada="IDJORNADA";
    public static final String Campo_JndFecha="FECHAJORNADA";
    public static final String Campo_JndDniTrabajador="DNITRABAJADOR";
    public static final String Campo_JndHoraEntrada="HORAENTRADA";
    public static final String Campo_JndHoraSalida="HORASALIDA";
    public static final String Campo_JndHoras="HORAS";
    public static final String Campo_JndDinero="DINERO";
    public static final String creaJornadas="CREATE TABLE " + Tabla_Jornadas + "(" + Campo_IdJornada + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Campo_JndFecha + " TEXT, " + Campo_JndDniTrabajador + " TEXT, " + Campo_JndHoraEntrada + " TEXT, "
            + Campo_JndHoraSalida + " TEXT, " + Campo_JndHoras + " TEXT, " + Campo_JndDinero + " REAL, FOREIGN KEY (" + Campo_JndDniTrabajador + ") REFERENCES " + Tabla_Trabajadores + "("
            + Campo_DniTrabajador + "))";

    //NOMBRE ARCHIVO SHAREDPREFERENCES
    public static final String Nombre_Archivo="Preferences";
    public static final String sp_HoraEntrada="HoraEntrada";
    public static final String sp_listaTrabajadores="ListaTrabajadores";
    public static final String sp_cargaActivity="CargaActivity";
}
