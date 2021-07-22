package com.example.securesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ActivityRecycledJndWorkers extends AppCompatActivity {

    Button btn_EmpezarJornada;
    Button btn_TerminarJornada;
    Button btn_BorrarSeleccion;

    ConstraintLayout cl_RecycledJndTrabajadores;

    ArrayList<ObjectWorker> listJndTrabajadores;
    ArrayList<String> listaTrSeleccionados;

    RecyclerView rvJndTrabajadores;
    ClassSQLiteOpenHelper conexion;
    String sNombreCampania;
    boolean bCargaActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycled_jnd_workers);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btn_EmpezarJornada = (Button) findViewById(R.id.btn_EmpezarJornada);
        btn_TerminarJornada = (Button) findViewById(R.id.btn_TerminarJornada);
        btn_BorrarSeleccion=(Button) findViewById(R.id.btn_BorrarSeleccion);

        cl_RecycledJndTrabajadores=(ConstraintLayout)findViewById(R.id.cl_RecycledJndTrabajadores);

        SharedPreferences pref = getSharedPreferences(ClassUtilidades.Nombre_Archivo, Context.MODE_PRIVATE);

        bCargaActivity = pref.getBoolean(ClassUtilidades.sp_cargaActivity, false);

        if (bCargaActivity == true) {
            btn_EmpezarJornada.setEnabled(false);
            btn_TerminarJornada.setEnabled(true);
            btn_BorrarSeleccion.setEnabled(false);

            cl_RecycledJndTrabajadores.setBackgroundColor(Color.LTGRAY);
        } else {
            btn_EmpezarJornada.setEnabled(true);
            btn_TerminarJornada.setEnabled(false);
            btn_BorrarSeleccion.setEnabled(true);
        }

        sNombreCampania = getIntent().getStringExtra("NombreCampaniaAM_ARJW");

        conexion = new ClassSQLiteOpenHelper(getApplicationContext(), ClassUtilidades.NombreBasedeDatos, null, 1);

        listJndTrabajadores = new ArrayList<>();
        listaTrSeleccionados = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvJndTrabajadores = (RecyclerView) findViewById(R.id.rvJndTrabajadores);
        rvJndTrabajadores.setLayoutManager(llm);
        DividerItemDecoration did = new DividerItemDecoration(this, llm.getOrientation());
        rvJndTrabajadores.addItemDecoration(did);

        rellenaListaJndTrabajadores();

        final AdapterRecycledWorkerJnd adaptador = new AdapterRecycledWorkerJnd(listJndTrabajadores);
        rvJndTrabajadores.setAdapter(adaptador);

        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean esta = false;//Para ver si el trabajador ya esta seleccionado

                //Creamos un nuevo objeto trabajador
                String TrDni = listJndTrabajadores.get(rvJndTrabajadores.getChildAdapterPosition(view)).getsDNI();

                //Cuando seleccionamos un trabajador, cambia a color verde
                view.setBackgroundColor(getResources().getColor(R.color.greenapp));

                //Comprueba si el trabajador ya estaba seleccionado
                for (int i = 0; i < listaTrSeleccionados.size(); i++) {
                    if (listaTrSeleccionados.get(i) == TrDni) {
                        esta = true;
                    }
                }

                //Si no estaba seleccionado antes, se añade al arraylist
                if (esta == false) {
                    listaTrSeleccionados.add(TrDni);
                }
            }
        });

        btn_EmpezarJornada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrGuardaPreferences(listaTrSeleccionados);
                BorrarSeleccion();
            }
        });

        btn_TerminarJornada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HiloJornadas hilo;
                String sHoraEntrada;
                String sHoraSalida;
                String sFecha;
                String sHoras;
                Set<String> listaTrabajadores;

                //Cogemos los valores que guardamos como preferences
                SharedPreferences pref = getSharedPreferences(ClassUtilidades.Nombre_Archivo, Context.MODE_PRIVATE);

                //Cogemos la hora de entrada de preferences y la hora actual (hora de salida)
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                sHoraEntrada = pref.getString(ClassUtilidades.sp_HoraEntrada, "Sin hora");
                sHoraSalida = sdf.format(new Date());

                //Cogemos la lista de trabajadores de preferences
                listaTrabajadores = new HashSet<>();
                Set<String> listaNada = new HashSet<>();
                listaTrabajadores = pref.getStringSet(ClassUtilidades.sp_listaTrabajadores, listaNada);

                //Cogemos la fecha actual
                Calendar cal = Calendar.getInstance();
                int iDia = cal.get(Calendar.DAY_OF_MONTH);
                int iMes = cal.get(Calendar.MONTH);
                int iAnio = cal.get(Calendar.YEAR);
                sFecha = iDia + "/" + iMes + "/" + iAnio;


                hilo=new HiloJornadas(sHoraEntrada, sHoraSalida, sFecha, listaTrabajadores, getApplicationContext());
                hilo.start();

                Toast.makeText(getApplicationContext(), "La jornada se ha guardado", Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor spe=pref.edit();
                spe.remove(ClassUtilidades.sp_cargaActivity);
                spe.remove(ClassUtilidades.sp_listaTrabajadores);
                spe.remove(ClassUtilidades.sp_HoraEntrada);
                spe.commit();

                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });

        btn_BorrarSeleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BorrarSeleccion();
            }
        });
    }

    public void rellenaListaJndTrabajadores() {

        //Crea una conexion a la base de datos
        SQLiteDatabase db = conexion.getReadableDatabase();

        //Crea un objeto trabajador al que se le asignaran sus atributos
        ObjectWorker trabajador = null;

        //Cursor con los datos de los trabajadores encontrados
        Cursor cursor = db.rawQuery("SELECT " + ClassUtilidades.Campo_NombreTrabajador + "," + ClassUtilidades.Campo_ApellidoTrabajador
                + "," + ClassUtilidades.Campo_DniTrabajador + " FROM "
                + ClassUtilidades.Tabla_Trabajadores + " WHERE " + ClassUtilidades.Campo_NombreCampania + "='" + sNombreCampania
                + "'", null);

        //Mientras cursor tenga trabajadores encontrados, crea un nuevo objeto trabajador y le asigna los datos a los atributos
        while (cursor.moveToNext()) {
            trabajador = new ObjectWorker();
            trabajador.setsNombreTrabajador(cursor.getString(0));
            trabajador.setsApellido(cursor.getString(1));
            trabajador.setsDNI(cursor.getString(2));

            listJndTrabajadores.add(trabajador);
        }
        db.close();
    }

    public void TrGuardaPreferences(ArrayList<String> listaTrSeleccionados) {

        if(listaTrSeleccionados.size()==0){
            Toast.makeText(this, "Selecciona al menos un trabajador para empezar la jornada", Toast.LENGTH_SHORT).show();
        }else {
            SharedPreferences pref = getSharedPreferences(ClassUtilidades.Nombre_Archivo, Context.MODE_PRIVATE);
            SharedPreferences.Editor spe = pref.edit();

            boolean bCargaActivity = true;

            spe.putBoolean(ClassUtilidades.sp_cargaActivity, bCargaActivity);

            //Guardamos la hora actual
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String horaEntrada = sdf.format(new Date());

            //Guardamos la lista de los trabajadores seleccionados
            Set<String> listaTrabajadores = new HashSet<>();
            listaTrabajadores.addAll(listaTrSeleccionados);
            spe.putString(ClassUtilidades.sp_HoraEntrada, horaEntrada);
            spe.putStringSet(ClassUtilidades.sp_listaTrabajadores, listaTrabajadores);

            spe.commit();

            Toast.makeText(this, "La jornada ha empezado", Toast.LENGTH_SHORT).show();
        }

    }

    public void BorrarSeleccion(){
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}