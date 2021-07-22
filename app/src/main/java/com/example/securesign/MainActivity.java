package com.example.securesign;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ImageView lggarlic;

    private EditText et_nombre;
    private EditText et_pass;

    private Button btn_login;
    private Button btn_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        lggarlic = (ImageView) findViewById(R.id.logo);

        lggarlic.setImageResource(R.drawable.lg_garlic);

        btn_login=(Button) findViewById(R.id.btnLogIn);
        btn_signin=(Button) findViewById(R.id.btnSignIn);

        et_nombre=(EditText) findViewById(R.id.etUsername);
        et_pass=(EditText) findViewById(R.id.etPass);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Consulta(view);
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iRegistro(view);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        this.onCreate(null);

    }


    public void iRegistro(View view){
        Intent registro=new Intent(this, ActivitySignIn.class);
        startActivity(registro);
    }

    //Consulta que el nombre y la contraseña son correctos para pasar al menu principal
    public void Consulta(View view){
        //Crea un objeto de la base de datos
        ClassSQLiteOpenHelper data=new ClassSQLiteOpenHelper(this, "ADMINISTRACION", null, 1);
        SQLiteDatabase db=data.getWritableDatabase();

        //Introduce los valores de los EditText en strings
        String sNombre=et_nombre.getText().toString();
        String sPass=et_pass.getText().toString();
        String sPassDB="";

        //Si el nombre y la contraseña no estan vacias
        if(!sNombre.isEmpty() && !sPass.isEmpty()){
            //Se crea un cursor con un select
            Cursor fila=db.rawQuery("SELECT " + ClassUtilidades.Campo_pass + " FROM " + ClassUtilidades.Tabla_usuario
                    + " WHERE " + ClassUtilidades.Campo_nombreUsuario + "='" + sNombre + "'", null);

            //Si recibe la contraseña
            if(fila.moveToFirst()){
                sPassDB=fila.getString(0);//Mete la contraseña en un string
                db.close();//Cierra la base de datos

                //Si la contraseña introducida es igual a la que hay en la base de datos
                if(sPass.equals(sPassDB)) {
                    //Pasamos a la activity menu
                    Intent intentActivityMenu = new Intent(this, ActivityMenu.class);
                    intentActivityMenu.putExtra("usuario", sNombre);
                    startActivity(intentActivityMenu);
                }else{
                    Toast.makeText(this, "La contraseña introducida no es correcta", Toast.LENGTH_SHORT).show();

                    et_pass.setText("");
                }

            }else{
                Toast.makeText(this, "Este usuario no existe", Toast.LENGTH_SHORT).show();

                et_nombre.setText("");
                et_pass.setText("");

                db.close();//Cierra la base de datos
            }

        }else{
            Toast.makeText(this, "Debes introducir todos los datos para continuar", Toast.LENGTH_SHORT).show();
        }
    }
}