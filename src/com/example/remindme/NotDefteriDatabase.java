package com.example.remindme;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;


public class NotDefteriDatabase {


private SQLiteDatabase db;
private final Context context;
private final NotDefteriDBHelper dbhelper;

//constructor
public NotDefteriDatabase(Context c) {
context = c;
//Dphelper opjesiyle yeni veritabaný oluþturuluyor.
dbhelper = new NotDefteriDBHelper(context, Sabitler.DATABASE, null,
Sabitler.DATABASE_VERSION);
}

/*
* Veritabanýný operasyonlara kapatmak
* için kullandýðýmýz method.
*/
public void kapat() {
db.close();
}
/*
* Veritabanýný yazma ve okuma için açtýðýmýz method
* **!**
* ->yazmak için aç, yazma operasyonu deðilse exception ver catch bloðunda okumak için aç
*/
public void ac() throws SQLiteException {
try {
db = dbhelper.getWritableDatabase();
} catch (SQLiteException ex) {
Log.v("Open database exception caught", ex.getMessage());
db = dbhelper.getReadableDatabase();
}
}

/*
* Veritabanýna not eklediðimiz method.
* insert Yapýsý:
* —-db.insert(String table, String nullColumnHack, ContentValues icerikDegerleri)
*/
public long notEkle(String konu, String icerik) {
try {
ContentValues yeniDegerler = new ContentValues();

yeniDegerler.put(Sabitler.KONU, konu);
yeniDegerler.put(Sabitler.ICERIK, icerik);
yeniDegerler.put(Sabitler.TARIH,
java.lang.System.currentTimeMillis());
return db.insert(Sabitler.TABLO, null, yeniDegerler);

} catch (SQLiteException ex) {

Log.v("Veritabanina ekleme isleminde hata tespit edildi !",
ex.getMessage());
return -1;
}
}

/*
* Seçilen bir notu güncellemek için kullandýðýmýz method
* Update yapýsý:
* ——update(String table, ContentValues icerikDegerleri, String whereCumlecigi,
* String[] whereArgumanlari)
*/

public void notGuncelle(int id, String konu, String icerik) {

ContentValues guncelDegerler = new ContentValues();
String[] idArray = { String.valueOf(id) };

guncelDegerler.put(Sabitler.KONU, konu);
guncelDegerler.put(Sabitler.ICERIK, icerik);
guncelDegerler
.put(Sabitler.TARIH, java.lang.System.currentTimeMillis());
db.update(Sabitler.TABLO, guncelDegerler, Sabitler.KEY_ID + "=?",
idArray);
}

/*
* Veritabanýndan tüm notlarý keyID azalan sýrada getirmek için
* kullandýðýmýz method.
* Son eklenen notun ilk sýrada gelmesi için.(Ayný iþlem için Tarih bazýnda da sýralayabilirdik!)
*
* query yapýsý :
* —db.query(String table, String[] columns, String selection, String[] selectionArgs,
* String groupBy, String having, String orderBy)
*/

public Cursor tumNotlariGetir() {

Cursor c = db.query(Sabitler.TABLO, null, null, null, null, null,
Sabitler.KEY_ID + " desc");

return c;
}

public ArrayList<Not> tumNotlar() {
ArrayList<Not> notlar=new ArrayList<Not>();

Cursor c = tumNotlariGetir();
//Curson tipinde gelen notlarý teker teker dolaþýyoruz
if (c.moveToFirst()) {
do {
int id1 = c.getInt(c.getColumnIndex(Sabitler.KEY_ID));
String konu = c.getString(c.getColumnIndex(Sabitler.KONU));

String icerik = c.getString(c.getColumnIndex(Sabitler.ICERIK));
DateFormat dateFormat = DateFormat.getDateTimeInstance();
String tarih = dateFormat.format(new Date(c.getLong(c
.getColumnIndex(Sabitler.TARIH))).getTime());
Not gecici = new Not(id1, konu, icerik, tarih);
//Veritabanýndaki tüm notlarý birer birer ArrayList’e kaydediyoruz.
notlar.add(gecici);

} while (c.moveToNext());
}
return notlar;
}

/*
* Seçilen bir notu silmek için kullandýðýmýz method.
*Delete Yapýsý :
* ——delete(String table, String whereCumlecigi, String[] whereArgumanlarý)
*/

public void idIleNotSil(int id) {

db.delete(Sabitler.TABLO, Sabitler.KEY_ID + "=" + id, null);

}
}

