package com.example.securesign;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ClassSQLiteOpenHelper extends SQLiteOpenHelper {

    public ClassSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClassUtilidades.creaUsuario);
        db.execSQL(ClassUtilidades.creaTemporada);
        db.execSQL(ClassUtilidades.creaTrabajadores);
        db.execSQL(ClassUtilidades.creaJornadas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
