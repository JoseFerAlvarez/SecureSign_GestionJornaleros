package com.example.securesign;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ActivitySeason extends AppCompatActivity {

    //Declaracion de variables
    TextView txt_NombreCampania;
    TextView txt_PrecioHora;
    TextView txt_FechaInicio;
    TextView txt_FechaFin;

    Button btn_SeleccionCampania;
    Button btn_FinalizarCampania;
    Button btn_BorrarCampania;

    ClassSQLiteOpenHelper conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Declara las variables con el grafico
        txt_NombreCampania=(TextView)findViewById(R.id.txt_NombreCampaniaAS);
        txt_PrecioHora=(TextView)findViewById(R.id.txt_PrecioHoraAS);
        txt_FechaInicio=(TextView)findViewById(R.id.txt_FechaInicioAS);
        txt_FechaFin=(TextView)findViewById(R.id.txt_FechaFinAS);

        btn_SeleccionCampania=(Button)findViewById(R.id.btn_SeleccionCampaniaAS);
        btn_FinalizarCampania=(Button)findViewById(R.id.btn_FinalizarCampaniaAS);
        btn_BorrarCampania=(Button)findViewById(R.id.btn_BorrarCampaniaAS);

        //Conexion a base de datos
        conexion=new ClassSQLiteOpenHelper(getApplicationContext(), ClassUtilidades.NombreBasedeDatos, null, 1);

        //Bundle que coge los datos del activity recycler
        Bundle bundleRecyclerSeasons=getIntent().getExtras();
        final String sNombreUsuario=bundleRecyclerSeasons.getString("usuarioAS");
        final String sNombreCampania=bundleRecyclerSeasons.getString("nombreCampaniaAS");

        final ObjectSeason osCampania=new ObjectSeason();

        //Metodo que coge los datos de la campaña seleccionada
        rellenaTemporada(sNombreUsuario, sNombreCampania, osCampania);

        txt_NombreCampania.setText(osCampania.getsNombreTemporada());
        txt_PrecioHora.setText(osCampania.getfPrecioHora().toString());
        txt_FechaInicio.setText(osCampania.getsFechaInicio());
        txt_FechaFin.setText(osCampania.getsFechaFin());

        if(!txt_FechaFin.getText().toString().equals("En Campaña")){
            btn_FinalizarCampania.setVisibility(View.INVISIBLE);
        }

        //Boton que lleva al activity menu
        btn_SeleccionCampania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentActivityMenu(osCampania.getsNombreTemporada(), osCampania.getsNombreUsuario());
            }
        });

        btn_FinalizarCampania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalizaCampania(sNombreCampania, sNombreUsuario);
            }
        });

        btn_BorrarCampania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrarCampania(sNombreCampania, sNombreUsuario);
            }
        });
    }

    //Intent que lleva al menu principal
    public void intentActivityMenu(String sNombreCampania, String sNombreUsuario){
        Bundle bundleMenu=new Bundle();
        bundleMenu.putString("nombreUsuarioAS_AM", sNombreUsuario);
        bundleMenu.putString("nombreCampaniaAS_AM", sNombreCampania);

        Intent intentActivityMenu=new Intent(this, ActivityMenu.class);
        intentActivityMenu.putExtras(bundleMenu);
        startActivity(intentActivityMenu);

        this.finish();
    }

    public void intentActivityRecycledViewCampanias(String sNombreUsuario){
        Intent intentARVCampanias=new Intent(this, ActivityRecycledSeasons.class);
        intentARVCampanias.putExtra("usuarioAS_ARV", sNombreUsuario);
        startActivity(intentARVCampanias);

        this.finish();
    }

    //Metodo que rellena la temporada seleccionada
    public void rellenaTemporada(String sNombreUsuario, String sNombreCampania, ObjectSeason osCampania){

        //Crea un db
        SQLiteDatabase db=conexion.getReadableDatabase();

        //Cursor que recoge el NombreCampaña, FechaInicio y FechaFin
        Cursor cursor=db.rawQuery("SELECT * FROM " + ClassUtilidades.Tabla_Temporadas + " WHERE " + ClassUtilidades.Campo_usuarioTemporada
                + " ='" + sNombreUsuario + "' AND " + ClassUtilidades.Campo_nombreTemp + "='" + sNombreCampania + "'" , null);

        //Si tiene datos
        if(cursor.moveToFirst()){

            //Los datos se guardan como los atributos del objeto
            osCampania.setsNombreTemporada(cursor.getString(0));
            osCampania.setsNombreUsuario(cursor.getString(1));
            osCampania.setsFechaInicio(cursor.getString(2));
            osCampania.setsFechaFin(cursor.getString(3));
            osCampania.setfPrecioHora(cursor.getFloat(4));

        }else{
            Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para finalizar una campaña
    public void finalizaCampania(String sNombreTemporada, String sNombreUsuario){
        //Crea una conexion a la base de datos
        SQLiteDatabase db=conexion.getWritableDatabase();

        String [] parametros={sNombreTemporada, sNombreUsuario};//Parametros de busqueda (Nombre de campaña y nombre de usuario)

        //Crea un string con la fecha actual
        Calendar cal=Calendar.getInstance();
        int iDia=cal.get(Calendar.DAY_OF_MONTH);
        int iMes=cal.get(Calendar.MONTH);
        int iAnio=cal.get(Calendar.YEAR);
        String fechaActual=iDia + "/" + iMes + "/" + iAnio;

        //Crea un content values con la fecha actual
        ContentValues cv=new ContentValues();
        cv.put(ClassUtilidades.Campo_fechaFin, fechaActual);

        //Modifica la tabla Temporadas con la nueva fecha
        db.update(ClassUtilidades.Tabla_Temporadas, cv, ClassUtilidades.Campo_nombreTemp + "=? AND "
                + ClassUtilidades.Campo_usuarioTemporada + "=?", parametros);
        db.close();//Cierra la conexion a la base de datos

        Toast.makeText(this, "Campaña finalizada el dia " + fechaActual, Toast.LENGTH_SHORT).show();

        //Metodo que crea un intent a la activity del recycledview de campañas
        intentActivityRecycledViewCampanias(sNombreUsuario);
    }

    //Metodo para borrar una campaña
    public void borrarCampania(String sNombreCampania, String sNombreUsuario){
        //Crea una conexion a la base de datos
        SQLiteDatabase db=conexion.getWritableDatabase();

        String [] parametros={sNombreCampania, sNombreUsuario};//Parametros de busqueda (Nombre de campaña y nombre de usuario)

        //Borra la campaña con el nombre de usuario y temporada seleccionados
        db.delete(ClassUtilidades.Tabla_Temporadas, ClassUtilidades.Campo_nombreTemp + "=? AND "
                + ClassUtilidades.Campo_usuarioTemporada +"=?", parametros);
        db.close();//Cierra la conexion de la base de datos
        Toast.makeText(this, "Campaña eliminada", Toast.LENGTH_SHORT).show();

        //Intent que lleva a la activity de recycledview de campañas
        intentActivityRecycledViewCampanias(sNombreUsuario);
    }
}