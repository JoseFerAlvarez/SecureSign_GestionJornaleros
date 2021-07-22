package com.example.securesign;

public class ObjectJornadas {
    private String sDniTrabajador;
    private String sFecha;
    private String sHoraEntrada;
    private String sHoraSalida;
    private String sHoras;
    private float fDinero;

    public ObjectJornadas(){

    }

    public ObjectJornadas(String sDniTrabajador, String sFecha, String sHoraEntrada, String sHoraSalida, String sHoras, float fDinero) {
        this.sDniTrabajador = sDniTrabajador;
        this.sFecha = sFecha;
        this.sHoraEntrada = sHoraEntrada;
        this.sHoraSalida = sHoraSalida;
        this.sHoras = sHoras;
        this.fDinero = fDinero;
    }

    public ObjectJornadas(String sFecha, String sHoraEntrada, String sHoraSalida, String sHoras, float fDinero) {
        this.sFecha = sFecha;
        this.sHoraEntrada = sHoraEntrada;
        this.sHoraSalida = sHoraSalida;
        this.sHoras = sHoras;
        this.fDinero = fDinero;
    }

    public String getsDniTrabajador() {
        return sDniTrabajador;
    }

    public void setsDniTrabajador(String sDniTrabajador) {
        this.sDniTrabajador = sDniTrabajador;
    }

    public String getsFecha() {
        return sFecha;
    }

    public void setsFecha(String sFecha) {
        this.sFecha = sFecha;
    }

    public String getsHoraEntrada() {
        return sHoraEntrada;
    }

    public void setsHoraEntrada(String sHoraEntrada) {
        this.sHoraEntrada = sHoraEntrada;
    }

    public String getsHoraSalida() {
        return sHoraSalida;
    }

    public void setsHoraSalida(String sHoraSalida) {
        this.sHoraSalida = sHoraSalida;
    }

    public String getsHoras() {
        return sHoras;
    }

    public void setsHoras(String sHoras) {
        this.sHoras = sHoras;
    }

    public float getfDinero() {
        return fDinero;
    }

    public void setfDinero(float fDinero) {
        this.fDinero = fDinero;
    }
}
