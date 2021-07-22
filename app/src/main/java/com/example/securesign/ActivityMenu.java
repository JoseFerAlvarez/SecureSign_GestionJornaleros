package com.example.securesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ActivityMenu extends AppCompatActivity {

    private ImageButton ib_seasons;
    private ImageButton ib_workers;
    private ImageButton ib_workday;
    private ImageButton ib_signout;
    private TextView txt_usuario;
    private TextView txt_Campania;
    private TextView txt_fecha;

    private String sUsuario;
    private String sNombreCampania;
    private String sFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txt_usuario=(TextView) findViewById(R.id.txt_Usuario);
        txt_Campania=(TextView)findViewById(R.id.txt_Campania);
        txt_fecha=(TextView) findViewById(R.id.txt_fecha);

        ib_seasons=(ImageButton) findViewById(R.id.ib_seasons);
        ib_workers=(ImageButton) findViewById(R.id.ib_workers);
        ib_workday=(ImageButton) findViewById(R.id.ib_workday);
        ib_signout=(ImageButton) findViewById(R.id.ib_signout);

        ib_seasons.setImageResource(R.drawable.ic_campaign);
        ib_workers.setImageResource(R.drawable.ic_workers);
        ib_workday.setImageResource(R.drawable.ic_flechajornadagrande);
        ib_signout.setImageResource(R.drawable.ic_signout);

        Calendar cal=Calendar.getInstance();
        int iDia=cal.get(Calendar.DAY_OF_MONTH);
        int iMes=cal.get(Calendar.MONTH)+1;
        int iAnio=cal.get(Calendar.YEAR);

        sUsuario =getIntent().getStringExtra("usuario");

        if(sUsuario==null) {
            Bundle bundleMenu = getIntent().getExtras();
            sUsuario = bundleMenu.getString("nombreUsuarioAS_AM");
            sNombreCampania = getIntent().getStringExtra("nombreCampaniaAS_AM");
        }
        sFecha =iDia + "/" + iMes + "/" + iAnio;

        txt_usuario.setText("" + sUsuario);
        txt_fecha.setText("" + sFecha);

        if(sNombreCampania!=null){
            txt_Campania.setText("" + sNombreCampania);
        }


        ib_seasons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irActivityRecycledSeasons(sUsuario);
            }
        });

        ib_workers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irActivityRecycledTrabajadores(sNombreCampania);
            }
        });

        ib_workday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irActivityRecycledJndTrabajadores(sNombreCampania);
            }
        });

        ib_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salirApp();
            }
        });
    }

    public void irActivityRecycledSeasons(String sUsuario){
        Intent intentTemporadas=new Intent(this, ActivityRecycledSeasons.class);
        intentTemporadas.putExtra("usuario", sUsuario);
        startActivity(intentTemporadas);
    }

    public void irActivityRecycledJndTrabajadores(String sNombreCampania){
        if(sNombreCampania==null){
            Toast.makeText(this, "Selecciona una campaña para empezar la jornada", Toast.LENGTH_SHORT).show();
        }else {
            Intent intentRecycledJndTrabajadores = new Intent(this, ActivityRecycledJndWorkers.class);
            intentRecycledJndTrabajadores.putExtra("NombreCampaniaAM_ARJW", sNombreCampania);
            startActivity(intentRecycledJndTrabajadores);
        }
    }

    public void irActivityRecycledTrabajadores(String sNombreCampania){
        if(sNombreCampania==null){
            Toast.makeText(this, "Selecciona una campaña para ver sus trabajadores", Toast.LENGTH_SHORT).show();
        }else {
            Intent intentRecycledTrabajadores = new Intent(this, ActivityRecycledWorkers.class);
            intentRecycledTrabajadores.putExtra("NombreCampaniaAM_ARW", sNombreCampania);
            startActivity(intentRecycledTrabajadores);
        }
    }

    public void salirApp(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}