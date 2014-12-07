package com.example.remindme;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;


public class NotDefteriDBHelper extends SQLiteOpenHelper{


public NotDefteriDBHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

private static final String CREATE_TABLE = "create table "
+ "notlar"+ " (" + Sabitler.KEY_ID
+ " integer primary key autoincrement, " + Sabitler.KONU
+ " text not null, " + Sabitler.ICERIK + " text not null, "
+ Sabitler.TARIH + " long);";


@Override
public void onCreate(SQLiteDatabase db) {
Log.v("NotDefteriDBHelper OnCreate", "Tablolar oluþturuyor…");
try {
// db.execSQL("drop table if exists"+Sabitler.TABLO);
db.execSQL(CREATE_TABLE);
} catch (SQLiteException ex) {
Log.v("Tablo olusturma hatasi tespit edildi", ex.getMessage());

}
}

@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
Log.w("Upgrade islemi","Tum verile silinecek !");
/*
* Yenisi geldiðinde eski tablodaki tüm veriler silinecek ve
* tablo yeniden oluþturulacak.
*/
db.execSQL("drop table if exists " + Sabitler.TABLO);
onCreate(db);
}
}