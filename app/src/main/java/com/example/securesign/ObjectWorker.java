package com.example.securesign;

public class ObjectWorker {
    private String sNombreTrabajador;
    private String sTrNomCamp;
    private String sApellido;
    private String sDNI;
    private String sTelefono;
    private String sNumSeguridadSocial;
    private String sCuentaBanco;

    public ObjectWorker(){
    }

    public ObjectWorker(String sNombreTrabajador, String sApellido, String sDNI, String sTelefono){
        this.sNombreTrabajador = sNombreTrabajador;
        this.sApellido = sApellido;
        this.sDNI = sDNI;
        this.sTelefono = sTelefono;
    }

    public ObjectWorker(String sNombreTrabajador, String sApellido, String sDNI){
        this.sNombreTrabajador = sNombreTrabajador;
        this.sApellido = sApellido;
        this.sDNI = sDNI;
    }

    public ObjectWorker(String sNombreTrabajador, String sTrNomCamp, String sApellido, String sDNI, String sTelefono, String sNumSeguridadSocial, String sCuentaBanco) {
        this.sNombreTrabajador = sNombreTrabajador;
        this.sTrNomCamp = sTrNomCamp;
        this.sApellido = sApellido;
        this.sDNI = sDNI;
        this.sTelefono = sTelefono;
        this.sNumSeguridadSocial = sNumSeguridadSocial;
        this.sCuentaBanco = sCuentaBanco;
    }

    public String getsNombreTrabajador() {
        return sNombreTrabajador;
    }

    public void setsNombreTrabajador(String sNombreTrabajador) {
        this.sNombreTrabajador = sNombreTrabajador;
    }

    public String getsTrNomCamp() {
        return sTrNomCamp;
    }

    public void setsTrNomCamp(String sTrNomCamp) {
        this.sTrNomCamp = sTrNomCamp;
    }

    public String getsApellido() {
        return sApellido;
    }

    public void setsApellido(String sApellido) {
        this.sApellido = sApellido;
    }

    public String getsDNI() {
        return sDNI;
    }

    public void setsDNI(String sDNI) {
        this.sDNI = sDNI;
    }

    public String getsTelefono() {
        return sTelefono;
    }

    public void setsTelefono(String sTelefono) {
        this.sTelefono = sTelefono;
    }

    public String getsNumSeguridadSocial() {
        return sNumSeguridadSocial;
    }

    public void setsNumSeguridadSocial(String sNumSeguridadSocial) {
        this.sNumSeguridadSocial = sNumSeguridadSocial;
    }

    public String getsCuentaBanco() {
        return sCuentaBanco;
    }

    public void setsCuentaBanco(String sCuentaBanco) {
        this.sCuentaBanco = sCuentaBanco;
    }
}
