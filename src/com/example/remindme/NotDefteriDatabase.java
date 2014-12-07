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
//Dphelper opjesiyle yeni veritaban� olu�turuluyor.
dbhelper = new NotDefteriDBHelper(context, Sabitler.DATABASE, null,
Sabitler.DATABASE_VERSION);
}

/*
* Veritaban�n� operasyonlara kapatmak
* i�in kulland���m�z method.
*/
public void kapat() {
db.close();
}
/*
* Veritaban�n� yazma ve okuma i�in a�t���m�z method
* **!**
* ->yazmak i�in a�, yazma operasyonu de�ilse exception ver catch blo�unda okumak i�in a�
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
* Veritaban�na not ekledi�imiz method.
* insert Yap�s�:
* �-db.insert(String table, String nullColumnHack, ContentValues icerikDegerleri)
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
* Se�ilen bir notu g�ncellemek i�in kulland���m�z method
* Update yap�s�:
* ��update(String table, ContentValues icerikDegerleri, String whereCumlecigi,
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
* Veritaban�ndan t�m notlar� keyID azalan s�rada getirmek i�in
* kulland���m�z method.
* Son eklenen notun ilk s�rada gelmesi i�in.(Ayn� i�lem i�in Tarih baz�nda da s�ralayabilirdik!)
*
* query yap�s� :
* �db.query(String table, String[] columns, String selection, String[] selectionArgs,
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
//Curson tipinde gelen notlar� teker teker dola��yoruz
if (c.moveToFirst()) {
do {
int id1 = c.getInt(c.getColumnIndex(Sabitler.KEY_ID));
String konu = c.getString(c.getColumnIndex(Sabitler.KONU));

String icerik = c.getString(c.getColumnIndex(Sabitler.ICERIK));
DateFormat dateFormat = DateFormat.getDateTimeInstance();
String tarih = dateFormat.format(new Date(c.getLong(c
.getColumnIndex(Sabitler.TARIH))).getTime());
Not gecici = new Not(id1, konu, icerik, tarih);
//Veritaban�ndaki t�m notlar� birer birer ArrayList�e kaydediyoruz.
notlar.add(gecici);

} while (c.moveToNext());
}
return notlar;
}

/*
* Se�ilen bir notu silmek i�in kulland���m�z method.
*Delete Yap�s� :
* ��delete(String table, String whereCumlecigi, String[] whereArgumanlar�)
*/

public void idIleNotSil(int id) {

db.delete(Sabitler.TABLO, Sabitler.KEY_ID + "=" + id, null);

}
}

