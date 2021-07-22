package com.example.securesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityNewWorker extends AppCompatActivity {

    //Declaracion de variables
    EditText et_NombreTrabajador;
    EditText et_ApellidoTrabajador;
    EditText et_DniTrabajador;
    EditText et_TlfTrabajador;
    EditText et_SegSocTrabajador;
    EditText et_CueBancTrabajador;

    Button btn_CrearTrabajador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_worker);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Asigna las variables a suu parte grafica
        et_NombreTrabajador=(EditText)findViewById(R.id.et_NombreTrabajador);
        et_ApellidoTrabajador=(EditText)findViewById(R.id.et_ApellidoTrabajador);
        et_DniTrabajador=(EditText)findViewById(R.id.et_DniTrabajador);
        et_TlfTrabajador=(EditText)findViewById(R.id.et_TlfTrabajador);
        et_SegSocTrabajador=(EditText)findViewById(R.id.et_SegSocTrabajador);
        et_CueBancTrabajador=(EditText)findViewById(R.id.et_CueBancTrabajador);

        btn_CrearTrabajador=(Button)findViewById(R.id.btn_CrearTrabajador);

        //Coge el nombre de campaña que le llega del ACTIVITYRECYCLEDWORKERS
        final String sNombreCampania=getIntent().getStringExtra("NombreCampaniaARW_ANW");

        //Boton que registra un trabajador
        btn_CrearTrabajador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registraTrabajador(view, sNombreCampania);
            }
        });
    }

    //Lleva a la ACTIVITYRECYCLEDWORKERS pasando el nombre de campaña
    public void intentRecycledView(String sNombreCampania){
        Intent intentRecycledView=new Intent(this, ActivityRecycledWorkers.class);
        intentRecycledView.putExtra("NombreCampaniaANW_ARW", sNombreCampania);
        startActivity(intentRecycledView);
    }

    //Metodo que registra un trabajador en la base de datos con su campaña correspondiente
    public void registraTrabajador(View view, String sNombreCampania){

        //Crea una conexion a la base de datos
        ClassSQLiteOpenHelper db=new ClassSQLiteOpenHelper(this, ClassUtilidades.NombreBasedeDatos, null, 1);
        SQLiteDatabase conexion=db.getWritableDatabase();

        //Recoge en variables los datos de los edit text
        String sNombreTrabajador=et_NombreTrabajador.getText().toString();
        String sApellidoTrabajador=et_ApellidoTrabajador.getText().toString();
        String sDniTrabajador=et_DniTrabajador.getText().toString();
        String sTlfTrabajador=et_TlfTrabajador.getText().toString();
        String sSegSocTrabajador=et_SegSocTrabajador.getText().toString();
        String sCueBancTrabajador=et_CueBancTrabajador.getText().toString();

        //Si ningun campo esta vacio
        if(!sNombreTrabajador.isEmpty() && !sApellidoTrabajador.isEmpty() && !sDniTrabajador.isEmpty() && !sTlfTrabajador.isEmpty()
                && !sSegSocTrabajador.isEmpty() && !sCueBancTrabajador.isEmpty()){

            //Si el trabajador no existia previamente
            if(!existeTrabajador(sDniTrabajador, sNombreCampania)){

                //Guarda los datos en un ContentValues
                ContentValues registroTrabajador=new ContentValues();
                registroTrabajador.put(ClassUtilidades.Campo_NombreTrabajador, sNombreTrabajador);
                registroTrabajador.put(ClassUtilidades.Campo_NombreCampania, sNombreCampania);
                registroTrabajador.put(ClassUtilidades.Campo_ApellidoTrabajador, sApellidoTrabajador);
                registroTrabajador.put(ClassUtilidades.Campo_DniTrabajador, sDniTrabajador);
                registroTrabajador.put(ClassUtilidades.Campo_TlfTrabajador, sTlfTrabajador);
                registroTrabajador.put(ClassUtilidades.Campo_SegSocTrabajador, sSegSocTrabajador);
                registroTrabajador.put(ClassUtilidades.Campo_CueBancTrabajador, sCueBancTrabajador);

                //Inserta el nuevo trabajador en la base de datos y la cierra
                conexion.insert(ClassUtilidades.Tabla_Trabajadores,null,registroTrabajador);
                conexion.close();

                Toast.makeText(this, "El trabajador se ha registrado correctamente", Toast.LENGTH_SHORT).show();

                //Metodo que lleva a la ACTIVITYRECYCLEDVIEW
                intentRecycledView(sNombreCampania);

                this.finish();
            }else {
                Toast.makeText(this, "Este trabajador ya existe", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Todos los campo deben estar rellenos", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo que comprueba si existe un trabajador
    public boolean existeTrabajador(String sDniTrabajador, String sNombreCampania){

        boolean bExisteTrabajador=false;

        //Crea un objeto para la base de datos
        ClassSQLiteOpenHelper data=new ClassSQLiteOpenHelper(this, ClassUtilidades.NombreBasedeDatos, null, 1);
        SQLiteDatabase db=data.getWritableDatabase();

        //Busca que el dni exista en la base de datos
        Cursor cursor=db.rawQuery("SELECT " + ClassUtilidades.Campo_DniTrabajador + " FROM " + ClassUtilidades.Tabla_Trabajadores + " WHERE "
                + ClassUtilidades.Campo_DniTrabajador + "='" + sDniTrabajador + "' AND " + ClassUtilidades.Campo_NombreCampania
                + "='" + sNombreCampania + "'", null);

        //Si el nombre existe
        if(cursor.moveToFirst()){
            bExisteTrabajador=true;
        }else{
            bExisteTrabajador=false;
        }

        return bExisteTrabajador;
    }
}