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

public class ActivityNewSeason extends AppCompatActivity {

    EditText et_nombreCampania;
    EditText et_precioHora;
    EditText et_fechaInicio;

    Button btn_crearCampania;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_season);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Declaracion de variables
        et_nombreCampania=(EditText) findViewById(R.id.et_nombreCampania);
        et_precioHora=(EditText) findViewById(R.id.et_precioHora);
        et_fechaInicio=(EditText) findViewById(R.id.et_fechaInicio);

        btn_crearCampania=(Button) findViewById(R.id.btn_crearCampania);

        //Coge el usuario de la activity anterior
        final String sUsuario=getIntent().getStringExtra("usuarioARS_ANS");

        btn_crearCampania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registro(view, sUsuario);
            }
        });
    }

    //Intent al recycled
    public void intentRecycledView(String sUsuario){
        Intent intentRecycledView=new Intent(this, ActivityRecycledSeasons.class);
        intentRecycledView.putExtra("nombreUsuario", sUsuario);
        startActivity(intentRecycledView);
    }

    //Metodo registrar nueva campaña
    public void Registro(View view, String sUsuario){

        //Crea un objeto para la base de datos
        ClassSQLiteOpenHelper data = new ClassSQLiteOpenHelper(this, ClassUtilidades.NombreBasedeDatos, null, 1);
        SQLiteDatabase db=data.getWritableDatabase();

        //Declaracion de variables
        String sNombreCampania;
        String sFechaInicio;
        String sPrecioHora;

        float fPrecioHora=0;

        //Guarda en un string lo que aparece en los EditText
        sNombreCampania=et_nombreCampania.getText().toString();
        sFechaInicio=et_fechaInicio.getText().toString();
        sPrecioHora=et_precioHora.getText().toString();

        //Convierte a float el PrecioHora
        if(!sPrecioHora.isEmpty()) {
            fPrecioHora = Float.valueOf(sPrecioHora);
        }

        //Si los EditText no estan vacios
        if(!sNombreCampania.isEmpty() && !sFechaInicio.isEmpty() && !sPrecioHora.isEmpty()) {

            //Si la campaña no existe
            if(existeCampania(sNombreCampania, sUsuario)==false) {

                //Guarda los valores NombreCampania, Usuario, FechaInicio, FechaFin y PrecioHora en un registro
                ContentValues registro = new ContentValues();
                registro.put(ClassUtilidades.Campo_nombreTemp, sNombreCampania);
                registro.put(ClassUtilidades.Campo_usuarioTemporada, sUsuario);
                registro.put(ClassUtilidades.Campo_fechaInicio, sFechaInicio);
                registro.put(ClassUtilidades.Campo_fechaFin, "En Campaña");
                registro.put(ClassUtilidades.Campo_precioHora, fPrecioHora);

                //Inserta el registro con los valores en la base de datos
                db.insert(ClassUtilidades.Tabla_Temporadas, null, registro);
                db.close();

                Toast.makeText(this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();

                intentRecycledView(sUsuario);
                //Cierra la activity, volviendo a la activity RecycledView
                this.finish();

            }else{
                Toast.makeText(this, "Esta campaña ya existe", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Ningun campo debe estar vacio", Toast.LENGTH_SHORT).show();
        }

    }

    //Metodo que devuelve true si el usuario existe y false si no existe
    public boolean existeCampania(String sNombreCampania, String sUsuario){

        boolean bExisteCampania=false;

        //Crea un objeto para la base de datos
        ClassSQLiteOpenHelper data=new ClassSQLiteOpenHelper(this, "ADMINISTRACION", null, 1);
        SQLiteDatabase db=data.getWritableDatabase();

        //Busca que el nombre exista en la base de datos
        Cursor cursor=db.rawQuery("SELECT " + ClassUtilidades.Campo_nombreTemp + " FROM " + ClassUtilidades.Tabla_Temporadas + " WHERE "
                + ClassUtilidades.Campo_nombreTemp + "='" + sNombreCampania + "' AND " + ClassUtilidades.Campo_usuarioTemporada
                + "='" + sUsuario + "'", null);

        //Si el nombre existe
        if(cursor.moveToFirst()){
            bExisteCampania=true;
        }else{
            bExisteCampania=false;
        }

        return bExisteCampania;
    }
}