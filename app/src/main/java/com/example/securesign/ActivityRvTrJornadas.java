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
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ActivityRvTrJornadas extends AppCompatActivity {

    ArrayList<ObjectJornadas> listaJornadas;
    TextView txt_JndTrNombre;
    TextView txt_JndHorasTotal;
    TextView txt_JndDineroTotal;

    RecyclerView rvJornadas;
    ClassSQLiteOpenHelper conexion;

    String sTrDni;
    String sTrNombre;
    String sTrApellido;
    String euro=" \u20AC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rvtrjornadas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txt_JndTrNombre=(TextView)findViewById(R.id.txt_JndTrNombre);
        txt_JndHorasTotal=(TextView)findViewById(R.id.txt_JndHorasTotal);
        txt_JndDineroTotal=(TextView)findViewById(R.id.txt_JndDineroTotal);

        listaJornadas=new ArrayList<>();

        conexion=new ClassSQLiteOpenHelper(getApplicationContext(), ClassUtilidades.NombreBasedeDatos, null, 1);

        Bundle bnd=getIntent().getExtras();

        sTrDni=bnd.getString("TrDniAW_ARTJ");
        sTrNombre=bnd.getString("TrNombreAW_ARTJ");
        sTrApellido=bnd.getString("TrApellidoAW_ARTJ");

        txt_JndTrNombre.setText(sTrNombre + " " + sTrApellido);

        LinearLayoutManager llm=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvJornadas=(RecyclerView)findViewById(R.id.rvJornadas);
        rvJornadas.setLayoutManager(llm);
        DividerItemDecoration did=new DividerItemDecoration(this, llm.getOrientation());
        rvJornadas.addItemDecoration(did);

        rellenaJornadas(sTrDni);

        AdapterJornadasTrabajador adaptador=new AdapterJornadasTrabajador(listaJornadas);
        rvJornadas.setAdapter(adaptador);

        txt_JndHorasTotal.setText("" + horasTotal());
        txt_JndDineroTotal.setText("" + dineroTotal() + euro);


    }

    public void rellenaJornadas(String sTrDni) {

        //Crea una conexion a la base de datos
        SQLiteDatabase db = conexion.getReadableDatabase();

        //Crea un objeto jornada al que se le asignaran sus atributos
        ObjectJornadas jornada = null;

        //Cursor con los datos de las jornadas de un trabajador
        Cursor cursor = db.rawQuery("SELECT " + ClassUtilidades.Campo_JndFecha + "," + ClassUtilidades.Campo_JndHoraEntrada
                + "," + ClassUtilidades.Campo_JndHoraSalida + ", " + ClassUtilidades.Campo_JndHoras + ", "
                + ClassUtilidades.Campo_JndDinero + " FROM " + ClassUtilidades.Tabla_Jornadas + " WHERE "
                + ClassUtilidades.Campo_JndDniTrabajador + "='" + sTrDni + "'", null);

        //Mientras cursor tenga jornadas, las añade a un arraylist
        while (cursor.moveToNext()) {
            jornada = new ObjectJornadas();
            jornada.setsFecha(cursor.getString(0));
            jornada.setsHoraEntrada(cursor.getString(1));
            jornada.setsHoraSalida(cursor.getString(2));
            jornada.setsHoras(cursor.getString(3));
            jornada.setfDinero((float) Math.round(cursor.getFloat(4)*100f)/100f);

            listaJornadas.add(jornada);
        }
        db.close();
    }

    public float dineroTotal(){
        float fDineroTotal=0;

        for(int i=0;i<listaJornadas.size();i++){
            fDineroTotal+=listaJornadas.get(i).getfDinero();
        }

        return fDineroTotal;
    }

    public String horasTotal(){
        String [] partes;
        String sTiempoTotal;
        int iHoras=0;
        int iMinutos=0;

        int iMinutosaHoras;
        int iMinutosResto;

        for (int i=0;i<listaJornadas.size();i++){
            partes=listaJornadas.get(i).getsHoras().split(":");
            iHoras+=Integer.parseInt(partes[0]);
            iMinutos+= Integer.parseInt(partes[1]);
        }

        iMinutosaHoras=iMinutos/60;
        iMinutosResto=iMinutos%60;

        iHoras=iHoras+iMinutosaHoras;
        iMinutos=iMinutosResto;

        sTiempoTotal=iHoras + ":" + iMinutos;

        return sTiempoTotal;
    }
}