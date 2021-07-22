package com.example.securesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityWorker extends AppCompatActivity {

    TextView txt_TrNom;
    TextView txt_TrApe;
    TextView txt_TrDni;
    TextView txt_TrTlf;
    TextView txt_TrSegSoc;
    TextView txt_TrCueBanc;

    Button btn_TrVerJornadas;
    Button btn_TrBorrar;

    ClassSQLiteOpenHelper conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txt_TrNom=(TextView)findViewById(R.id.txt_AtTrNom);
        txt_TrApe=(TextView)findViewById(R.id.txt_AtTrApe);
        txt_TrDni=(TextView)findViewById(R.id.txt_AtTrDni);
        txt_TrTlf=(TextView)findViewById(R.id.txt_AtTrTlf);
        txt_TrSegSoc=(TextView)findViewById(R.id.txt_AtTrSegSoc);
        txt_TrCueBanc=(TextView)findViewById(R.id.txt_AtTrCueBanc);

        btn_TrVerJornadas=(Button)findViewById(R.id.btn_AtTrVerJornadas);
        btn_TrBorrar=(Button)findViewById(R.id.btn_AtTrBorrarTrabajador);

        conexion=new ClassSQLiteOpenHelper(getApplicationContext(), ClassUtilidades.NombreBasedeDatos, null, 1);

        Bundle bnd=getIntent().getExtras();
        final String sTrDni=bnd.getString("TrDniART_AT");
        final String sTrNomCamp=bnd.getString("TrNomCampART_AT");

        final ObjectWorker OtTrabajador=new ObjectWorker();

        rellenaTemporada(sTrDni, sTrNomCamp, OtTrabajador);

        txt_TrNom.setText(OtTrabajador.getsNombreTrabajador());
        txt_TrApe.setText(OtTrabajador.getsApellido());
        txt_TrDni.setText(OtTrabajador.getsDNI());
        txt_TrTlf.setText(OtTrabajador.getsTelefono());
        txt_TrSegSoc.setText(OtTrabajador.getsNumSeguridadSocial());
        txt_TrCueBanc.setText(OtTrabajador.getsCuentaBanco());

        btn_TrVerJornadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentARTJ(sTrDni, OtTrabajador.getsNombreTrabajador(), OtTrabajador.getsApellido());
            }
        });

        btn_TrBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrarTrabajador(sTrNomCamp, sTrDni);
            }
        });

    }

    public void intentARW(String sNomCamp){
        Intent intentARW=new Intent(this, ActivityRecycledWorkers.class);
        intentARW.putExtra("NomCampAW_ARW", sNomCamp);
        startActivity(intentARW);
        this.finish();
    }

    public void intentARTJ(String sTrDni, String sTrNombre, String sTrApellido){
        Bundle bnd=new Bundle();
        bnd.putString("TrDniAW_ARTJ", sTrDni);
        bnd.putString("TrNombreAW_ARTJ", sTrNombre);
        bnd.putString("TrApellidoAW_ARTJ", sTrApellido);

        Intent intentARTJ=new Intent(this, ActivityRvTrJornadas.class);
        intentARTJ.putExtras(bnd);
        startActivity(intentARTJ);
    }

    public void rellenaTemporada(String sTrDni, String sTrNomCamp, ObjectWorker OtTrabajador){

        //Crea un db
        SQLiteDatabase db=conexion.getReadableDatabase();

        //Cursor que recoge el NombreCampaña, FechaInicio y FechaFin
        Cursor cursor=db.rawQuery("SELECT * FROM " + ClassUtilidades.Tabla_Trabajadores + " WHERE " + ClassUtilidades.Campo_NombreCampania
                + " ='" + sTrNomCamp + "' AND " + ClassUtilidades.Campo_DniTrabajador + "='" + sTrDni + "'" , null);

        //Si tiene datos
        if(cursor.moveToFirst()){

            //Los datos se guardan como los atributos del objeto
            OtTrabajador.setsNombreTrabajador(cursor.getString(0));
            OtTrabajador.setsTrNomCamp(cursor.getString(1));
            OtTrabajador.setsApellido(cursor.getString(2));
            OtTrabajador.setsDNI(cursor.getString(3));
            OtTrabajador.setsTelefono(cursor.getString(4));
            OtTrabajador.setsNumSeguridadSocial(cursor.getString(5));
            OtTrabajador.setsCuentaBanco(cursor.getString(6));

        }else{
            Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }
    }

    public void borrarTrabajador(String sNomCamp, String sTrDni){
        //Crea una conexion a la base de datos
        SQLiteDatabase db=conexion.getWritableDatabase();

        String [] parametros={sTrDni, sNomCamp};//Parametros de busqueda (Nombre de campaña y dni de trabajador)

        //Borra la campaña con el nombre de trabajador y campaña
        db.delete(ClassUtilidades.Tabla_Trabajadores, ClassUtilidades.Campo_DniTrabajador + "=? AND "
                + ClassUtilidades.Campo_NombreCampania +"=?", parametros);
        db.close();//Cierra la conexion de la base de datos
        Toast.makeText(this, "Trabajador eliminado", Toast.LENGTH_SHORT).show();

        //Intent que lleva a la activity de recycledview de trabajadores
        intentARW(sNomCamp);
    }
}