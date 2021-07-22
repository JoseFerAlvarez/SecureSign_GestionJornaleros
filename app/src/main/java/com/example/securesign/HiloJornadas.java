package com.example.securesign;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class HiloJornadas implements Runnable {
    //Declaracion de variables
    Thread t;
    String sHoraEntrada;
    String sHoraSalida;
    String sFecha;
    String sHoras;
    Context ContextActivity;
    Set<String> listaTrabajadores;

    boolean isAlive=true;

    //Constructor del hilo
    public HiloJornadas(String sHoraEntrada, String sHoraSalida, String sFecha, Set<String> listaTrabajadores, Context ContextActivity){
        t=new Thread(this, "Nuevo Hilo");

        this.sHoraEntrada=sHoraEntrada;
        this.sHoraSalida=sHoraSalida;
        this.sFecha=sFecha;
        this.listaTrabajadores=listaTrabajadores;
        this.ContextActivity=ContextActivity;
    }

    //Metodo que se ejecutara en segundo plano
    @Override
    public void run() {
        //ArrayList con el DNI de los trabajadores
        ArrayList<String> Trlista=new ArrayList<>();
        Trlista.addAll(listaTrabajadores);

        //Declaramos las variables de conexion a la base de datos
        ClassSQLiteOpenHelper db = new ClassSQLiteOpenHelper(ContextActivity, ClassUtilidades.NombreBasedeDatos, null, 1);
        SQLiteDatabase conexion = db.getWritableDatabase();

        //Calcula las horas totales entre la hora de entrada y la hora de salida
        sHoras = calculaHoras();

        //Bucle que registra las jornadas de los trabajadores en la base de datos
        for(int i=0;i<listaTrabajadores.size();i++){
            registraJornada(Trlista.get(i), conexion);
        }

        //Cerramos la conexion una vez hayamos registrado todas las temporadas
        conexion.close();
        db.close();

        isAlive=false;
    }

    //Metodo que calcula las horas que han pasado entre la hora de entrada y salida
    public String calculaHoras(){

        //Declaracion de variables de tiempo
        String tiempo = "";
        final int segundosDia = 86400;
        final int segundosHora = 3600;
        final int segundosMinuto = 60;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date hEntrada = sdf.parse(sHoraEntrada);
            Date hSalida = sdf.parse(sHoraSalida);

            long diferencia = hSalida.getTime() - hEntrada.getTime();

            if (diferencia < 0) {
                Date hMaxima = sdf.parse("24:00");
                Date hMinima = sdf.parse("00:00");

                diferencia = (hMaxima.getTime() - hEntrada.getTime()) + (hSalida.getTime() - hMinima.getTime());
            }

            int dias = (int) (diferencia / (1000 * segundosDia));
            int horas = (int) ((diferencia - (1000 * segundosDia * dias)) / (1000 * segundosHora));
            int minutos = (int) (diferencia - (1000 * segundosDia * dias) - (1000 * segundosHora * horas)) / (1000 * segundosMinuto);

            tiempo = horas + ":" + minutos;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return tiempo;
    }

    public void start(){
        t.start();
    }

    public void registraJornada(String sDniTrabajador, SQLiteDatabase conexion) {

        //Cogemos las horas y minutos
        String[] partes= sHoras.split(":");
        String horas=partes[0];
        String minutos=partes[1];

        //Convertimos las horas y minutos a float
        float iHoras=Float.parseFloat(horas);
        float iMinutos=Float.parseFloat(minutos);
        iMinutos=((iMinutos*100)/60)/100;

        //Declaramos variables
        String sNombreCampania="";
        float fPrecioHora=0;
        float fDineroGanado;

        //Cursor con el nombre de campaña del trabajador
        Cursor cursor=conexion.rawQuery("SELECT " + ClassUtilidades.Campo_NombreCampania + " FROM " + ClassUtilidades.Tabla_Trabajadores
                + " WHERE " + ClassUtilidades.Campo_DniTrabajador + "='" + sDniTrabajador + "'", null);
        if(cursor.moveToFirst()){
            sNombreCampania=cursor.getString(0);
        }

        Cursor cursor2=conexion.rawQuery("SELECT " + ClassUtilidades.Campo_precioHora + " FROM " + ClassUtilidades.Tabla_Temporadas
                + " WHERE " + ClassUtilidades.Campo_nombreTemp + "='" + sNombreCampania + "'", null);
        if(cursor2.moveToFirst()){
            fPrecioHora=cursor2.getFloat(0);
        }

        //Calculamos el tiempo ganado
        fDineroGanado=(iHoras*fPrecioHora) + (iMinutos*fPrecioHora);

        //Guarda los datos en un ContentValues
        ContentValues registroJornada = new ContentValues();
        registroJornada.put(ClassUtilidades.Campo_JndFecha, sFecha);
        registroJornada.put(ClassUtilidades.Campo_JndDniTrabajador, sDniTrabajador);
        registroJornada.put(ClassUtilidades.Campo_JndHoraEntrada, sHoraEntrada);
        registroJornada.put(ClassUtilidades.Campo_JndHoraSalida, sHoraSalida);
        registroJornada.put(ClassUtilidades.Campo_JndHoras, sHoras);
        registroJornada.put(ClassUtilidades.Campo_JndDinero, fDineroGanado);

        //Inserta la nueva jornada en la base de datos y la cierra
        conexion.insert(ClassUtilidades.Tabla_Jornadas, null, registroJornada);

        System.out.println(sDniTrabajador + "registrado");
    }

    public boolean isAlive(){
        return isAlive;
    }
}
