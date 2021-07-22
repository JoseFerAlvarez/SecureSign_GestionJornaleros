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

public class ActivityRecycledSeasons extends AppCompatActivity {

    //Declaracion de variables
    Button btn_crearTemporada;

    ArrayList<ObjectSeason> listTemporadas;
    RecyclerView rvTemporadas;

    ClassSQLiteOpenHelper conexion;

    //Recupera el nombre de usuario del activity anterior
    String sUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycled_seasons);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btn_crearTemporada=(Button) findViewById(R.id.btn_crearTemporada);

        sUsuario=getIntent().getStringExtra("usuario");
        if(sUsuario==null){
            sUsuario=getIntent().getStringExtra("nombreUsuario");
        }
        if(sUsuario==null){
            sUsuario=getIntent().getStringExtra("usuarioAS_ARV");
        }

        //Conexion a la base de datos
        conexion=new ClassSQLiteOpenHelper(getApplicationContext(), ClassUtilidades.NombreBasedeDatos, null, 1);

        //ArrayList para guardar todas las campañas de un mismo usuario
        listTemporadas=new ArrayList<>();

        LinearLayoutManager llm=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvTemporadas=(RecyclerView) findViewById(R.id.rvTemporadas);
        rvTemporadas.setLayoutManager(llm);

        DividerItemDecoration did=new DividerItemDecoration(this, llm.getOrientation());
        rvTemporadas.addItemDecoration(did);

        //Metodo
        rellenaListaTemporadas();

        //Crea un adaptador para el recycled view
        AdapterRecycledSeason adaptador=new AdapterRecycledSeason(listTemporadas);

        //Metodo OnClick de recycled que lleva a la campaña seleccionada
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentSeleccionarCampania(view);
            }
        });

        rvTemporadas.setAdapter(adaptador);

        //Boton que lleva a la activity de crear una nueva temporada
        btn_crearTemporada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intentCrearCampania(sUsuario);
            }
        });
    }

    //Metodo que lleva a crear una nueva campaña
    public void intentCrearCampania(String sUsuario){
        Intent intentCrearTemporada=new Intent(ActivityRecycledSeasons.this, ActivityNewSeason.class);
        intentCrearTemporada.putExtra("usuarioARS_ANS", sUsuario);
        startActivity(intentCrearTemporada);
        this.finish();
    }

    //Metodo que lleva a la seleccion de una campaña
    public void intentSeleccionarCampania(View view){
        String sNombreCampania=listTemporadas.get(rvTemporadas.getChildAdapterPosition(view)).getsNombreTemporada();

        Bundle bundleCampania=new Bundle();
        bundleCampania.putString("usuarioAS", sUsuario);
        bundleCampania.putString("nombreCampaniaAS", sNombreCampania);

        Intent intentActivitySeason=new Intent(this, ActivitySeason.class);
        intentActivitySeason.putExtras(bundleCampania);
        startActivity(intentActivitySeason);

        this.finish();
    }

    //Metodo que rellena el RecycledView
    public void rellenaListaTemporadas(){

        //Crea un db
        SQLiteDatabase db=conexion.getReadableDatabase();

        //Crea un nuevo objeto campaña
        ObjectSeason temporada=null;

        //Cursor que recoge el NombreCampaña, FechaInicio y FechaFin
        Cursor cursor=db.rawQuery("SELECT " + ClassUtilidades.Campo_nombreTemp + ","
                + ClassUtilidades.Campo_fechaInicio + "," + ClassUtilidades.Campo_fechaFin + " FROM "
                + ClassUtilidades.Tabla_Temporadas + " WHERE "
                + ClassUtilidades.Campo_usuarioTemporada + "='" + sUsuario + "'" , null);

        //Mientras haya temporadas
        while (cursor.moveToNext()){
            temporada=new ObjectSeason();
            temporada.setsNombreTemporada(cursor.getString(0));
            temporada.setsFechaInicio(cursor.getString(1));
            temporada.setsFechaFin(cursor.getString(2));

            listTemporadas.add(temporada);
        }
    }
}