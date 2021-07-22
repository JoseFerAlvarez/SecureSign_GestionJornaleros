package com.example.securesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ActivityRecycledWorkers extends AppCompatActivity {

    //Declaracion de variables
    Button btn_NuevoTrabajador;

    ArrayList<ObjectWorker> listTrabajadores;
    RecyclerView rvTrabajadores;

    ClassSQLiteOpenHelper conexion;

    String sNombreCampania;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycled_workers);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Declaracion de variables
        btn_NuevoTrabajador=(Button)findViewById(R.id.btn_EmpezarJornada);

        //Coge el nombre de campaña que le llega de MENU
        sNombreCampania=getIntent().getStringExtra("NombreCampaniaAM_ARW");

        //Si el intent no le llega de menu, le llega de borrar un trabajador
        if(sNombreCampania==null){
            sNombreCampania=getIntent().getStringExtra("NomCampAW_ARW");
        }

        //Si el nombre de campaña que le llega de MENU es null, lo coge de la ACTIVITY CREAR TRABAJADOR
        if(sNombreCampania==null){
            sNombreCampania=getIntent().getStringExtra("NombreCampaniaANW_ARW");
        }

        //Conexion a la base de datos
        conexion=new ClassSQLiteOpenHelper(getApplicationContext(), ClassUtilidades.NombreBasedeDatos, null, 1);

        listTrabajadores=new ArrayList<>();//Arraylist de trabajadores

        //Declaramos el recycledview y lo ponemos en vertical
        LinearLayoutManager llm=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvTrabajadores=(RecyclerView)findViewById(R.id.rvJndTrabajadores);
        rvTrabajadores.setLayoutManager(llm);

        //Creamos un divisor de items
        DividerItemDecoration did=new DividerItemDecoration(this, llm.getOrientation());
        rvTrabajadores.addItemDecoration(did);

        //Metodo para rellenar el recycled con trabajadores
        rellenaListaTrabajadores();

        //Le asignamos el adaptador al recycled
        AdapterRecycledWorker adaptador=new AdapterRecycledWorker(listTrabajadores);
        rvTrabajadores.setAdapter(adaptador);

        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentSeleccionTrabajador(view);
            }
        });

        //Boton que lleva a crear un nuevo trabajador
        btn_NuevoTrabajador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentCrearTrabajador(sNombreCampania);
            }
        });
    }

    //Metodo que lleva a la ACTIVITYNEWWORKER
    public void intentCrearTrabajador(String sNombreCampania){
        Intent intentCrearTemporada=new Intent(this, ActivityNewWorker.class);
        intentCrearTemporada.putExtra("NombreCampaniaARW_ANW", sNombreCampania);
        startActivity(intentCrearTemporada);
        this.finish();
    }

    public void intentSeleccionTrabajador(View view){
        String sDniTrabajador=listTrabajadores.get(rvTrabajadores.getChildAdapterPosition(view)).getsDNI();

        Bundle bundleTrabajador=new Bundle();
        bundleTrabajador.putString("TrDniART_AT", sDniTrabajador);
        bundleTrabajador.putString("TrNomCampART_AT", sNombreCampania);

        Intent intentActivityTrabajador=new Intent(this, ActivityWorker.class);
        intentActivityTrabajador.putExtras(bundleTrabajador);
        startActivity(intentActivityTrabajador);

        this.finish();
    }

    //Metodo que rellena el recycledview con un arraylist de trabajadores
    public void rellenaListaTrabajadores(){

        //Crea una conexion a la base de datos
        SQLiteDatabase db=conexion.getReadableDatabase();

        //Crea un objeto trabajador al que se le asignaran sus atributos
        ObjectWorker trabajador=null;

        //Cursor con los datos de los trabajadores encontrados
        Cursor cursor=db.rawQuery("SELECT " + ClassUtilidades.Campo_NombreTrabajador + "," + ClassUtilidades.Campo_ApellidoTrabajador
                + "," + ClassUtilidades.Campo_DniTrabajador + "," + ClassUtilidades.Campo_TlfTrabajador + " FROM "
                + ClassUtilidades.Tabla_Trabajadores + " WHERE " + ClassUtilidades.Campo_NombreCampania  + "='" + sNombreCampania
                + "'", null );

        //Mientras cursor tenga trabajadores encontrados, crea un nuevo objeto trabajador y le asigna los datos a los atributos
        while (cursor.moveToNext()){
            trabajador=new ObjectWorker();
            trabajador.setsNombreTrabajador(cursor.getString(0));
            trabajador.setsApellido(cursor.getString(1));
            trabajador.setsDNI(cursor.getString(2));
            trabajador.setsTelefono(cursor.getString(3));

            listTrabajadores.add(trabajador);
        }
    }
}