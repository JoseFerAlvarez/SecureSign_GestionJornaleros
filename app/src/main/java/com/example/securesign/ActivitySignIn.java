package com.example.securesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivitySignIn extends AppCompatActivity {

    //Declaracion de variables
    private EditText et_Nombre;
    private EditText et_pass;
    private EditText et_reppass;

    private Button btnCrearUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Relacion de las variables y los EditText
        et_Nombre=(EditText) findViewById(R.id.etNomReg);
        et_pass=(EditText) findViewById(R.id.etContraReg);
        et_reppass=(EditText) findViewById(R.id.etRepContraReg);

        btnCrearUsuario =(Button) findViewById(R.id.btnAcepReg);

        //Metodo onClick para registrar un usuario
        btnCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Registro(view);//Llama al metodo Registro
            }
        });
    }

    //Metodo para registrar un usuario en la base de datos
    public void Registro(View view){

        //Crea un objeto para la base de datos
        ClassSQLiteOpenHelper data = new ClassSQLiteOpenHelper(this, "ADMINISTRACION", null, 1);
        SQLiteDatabase db=data.getWritableDatabase();

        //Guarda en un string lo que aparece en los EditText
        String sNombre=et_Nombre.getText().toString();
        String sPass=et_pass.getText().toString();
        String sReppass=et_reppass.getText().toString();

        int iNombre=sNombre.length();
        int iPass=sNombre.length();

        //Si los EditText no estan vacios
        if(!sNombre.isEmpty() && !sPass.isEmpty() && !sReppass.isEmpty()) {

            //Si el nombre y la contraseña tienen mas de 4 caracteres
            if(iNombre>=4 && iPass>=4) {

                //Si el usuario introducido no existe
                if (existeUsuario() == false) {

                    //Si ambas contraseñas son iguales
                    if (sPass.equals(sReppass)) {

                        //Guarda los valores usuario y contraseña en un registro
                        ContentValues registro = new ContentValues();
                        registro.put(ClassUtilidades.Campo_nombreUsuario, sNombre);
                        registro.put(ClassUtilidades.Campo_pass, sPass);

                        //Inserta el registro con los valores en la base de datos
                        db.insert(ClassUtilidades.Tabla_usuario, null, registro);
                        db.close();

                        Toast.makeText(this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();

                        //Cierra la activity, volviendo a la activity LogIn
                        this.finish();

                    } else {
                        Toast.makeText(this, "Ambas contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "Este usuario ya existe", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(this, "Tanto el nombre como la contraseña deben tener 4 caracteres como minimo", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Ningun campo debe estar vacio", Toast.LENGTH_SHORT).show();
        }

    }

    //Metodo que devuelve true si el usuario existe y false si no existe
    public boolean existeUsuario(){

        boolean bExisteUsuario=false;

        //Crea un objeto para la base de datos
        ClassSQLiteOpenHelper data=new ClassSQLiteOpenHelper(this, "ADMINISTRACION", null, 1);
        SQLiteDatabase db=data.getWritableDatabase();

        String sNombre=et_Nombre.getText().toString();

        //Busca que el nombre exista en la base de datos
        Cursor fila=db.rawQuery("SELECT " + ClassUtilidades.Campo_nombreUsuario + " FROM " + ClassUtilidades.Tabla_usuario
                + " WHERE " + ClassUtilidades.Campo_nombreUsuario + "='" + sNombre + "'", null);

        //Si el nombre existe
        if(fila.moveToFirst()){
            bExisteUsuario=true;
        }else{
            bExisteUsuario=false;
        }

        return bExisteUsuario;
    }
}