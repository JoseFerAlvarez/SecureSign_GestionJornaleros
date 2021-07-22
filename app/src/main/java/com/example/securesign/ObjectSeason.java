package com.example.securesign;

public class ObjectSeason {

    private String sNombreUsuario;
    private String sNombreTemporada;
    private int iAnio;
    private String sFechaInicio;
    private String sFechaFin;
    private Float fPrecioHora;

    public ObjectSeason(){
    }

    public ObjectSeason(String sNombreTemporada, String sFechaInicio, String sFechaFin){
        this.sNombreTemporada=sNombreTemporada;
        this.sFechaInicio=sFechaInicio;
        this.sFechaFin=sFechaFin;
    }

    public ObjectSeason(String sNombreUsuario, String sNombreTemporada, int iAnio, String sFechaInicio, String sFechaFin, Float fPrecioHora) {
        this.sNombreUsuario = sNombreUsuario;
        this.sNombreTemporada = sNombreTemporada;
        this.iAnio = iAnio;
        this.sFechaInicio = sFechaInicio;
        this.sFechaFin = sFechaFin;
        this.fPrecioHora = fPrecioHora;
    }


    public String getsNombreUsuario() {
        return sNombreUsuario;
    }

    public void setsNombreUsuario(String sNombreUsuario) {
        this.sNombreUsuario = sNombreUsuario;
    }

    public String getsNombreTemporada() {
        return sNombreTemporada;
    }

    public void setsNombreTemporada(String sNombreTemporada) {
        this.sNombreTemporada = sNombreTemporada;
    }

    public int getiAnio() {
        return iAnio;
    }

    public void setiAnio(int iAnio) {
        this.iAnio = iAnio;
    }

    public String getsFechaInicio() {
        return sFechaInicio;
    }

    public void setsFechaInicio(String sFechaInicio) {
        this.sFechaInicio = sFechaInicio;
    }

    public String getsFechaFin() {
        return sFechaFin;
    }

    public void setsFechaFin(String sFechaFin) {
        this.sFechaFin = sFechaFin;
    }

    public Float getfPrecioHora() {
        return fPrecioHora;
    }

    public void setfPrecioHora(Float fPrecioHora) {
        this.fPrecioHora = fPrecioHora;
    }

}
