package com.example.remindme;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class NotlariListeleActivity extends ListActivity {

	private ArrayList<Not> notlar;
	private NotDefteriDatabase dba;
	private CustomListAdapter cla;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.listele);

	dba = new NotDefteriDatabase(this);
	dba.ac();

	//Adapterimizi ayarl�yoruz.
	cla = new CustomListAdapter(this);
	setListAdapter(cla);
	notlar = cla.notListesi();

	//E�er tabloda hi� not bulunmuyorsa, not ekleme sayfas�na y�nlendirme yap�yoruz.
	if (notlar.size() == 0) {
	yeniNot();
	}

	//T�klamalar
	kisaUzunTiklama();

	}

	public void kisaUzunTiklama() {

	getListView().setOnItemClickListener(new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> a, View v, int position,
	long id) {

	Context context = getApplicationContext();

	int duration = Toast.LENGTH_SHORT;

	Toast toast = Toast.makeText(context,
	notlar.get(position).icerik.toString(), duration);
	toast.show();
	}
	});

	/*
	*
	* Listedeki itemlere uzun s�reli t�klama:
	* Ekrana alert dialog i�inde, not ekle, g�ncelle ve sil se�enekleri gelir
	*
	*/

	getListView().setOnItemLongClickListener(
	new AdapterView.OnItemLongClickListener() {
	@Override
	public boolean onItemLongClick(AdapterView<?> av, View v,
	int pos, long id) {
	final int ps = pos;

	final CharSequence[] items = { "Yeni Not Ekle"," Notu D�zenle", "Notu Sil" };

	AlertDialog.Builder builder = new AlertDialog.Builder(
	NotlariListeleActivity.this);
	builder.setTitle(notlar.get(ps).konu.toString());
	builder.setItems(items,
	new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog,
	int item) {

	switch (item) {
	case 0:
	yeniNot();
	break;
	case 1:
	notGuncelle(ps);
	break;
	case 2:
	notSil(ps);
	break;
	default:
	break;
	}
	}
	});

	AlertDialog alert = builder.create();
	alert.show();

	return false;

	}
	});
	}

	/*
	* Not ekleme activity s�n�f�na y�nlendiren method
	*/

	public void yeniNot() {
	Intent i = new Intent(NotlariListeleActivity.this,
	NotEkleActivity.class);
	startActivity(i);
	finish();

	}

	/*
	* Nota daie verileri al�p, not g�ncelleme activity s�n�f�na
	* y�nlendiren method.
	*(Veriler putextra ve benzerleriyle birlikte isim,de�er ikilisi olarak
	*di�er aktivity s�n�flar�na rahatl�kla ge�irilebilir.)
	*/
	public void notGuncelle(int ps) {
	Intent i = new Intent(NotlariListeleActivity.this,
	GuncelleActivity.class);
	i.putExtra("ID", notlar.get(ps)._id);
	i.putExtra("KONU", notlar.get(ps).konu);
	i.putExtra("ICERIK", notlar.get(ps).icerik);
	
	startActivity(i);
	finish();
	}

	/*
	* Not silmek i�in kulland���m�z method.
	* Alert dialog i�erisinde kullan�c�ya notu silmek eyleminden emin olup
	* olmad��� sorulur. Emin ise not silinir de�ilse i�lem iptal edilir.
	*/

	public void notSil(int p) {
	final int ps = p;

	AlertDialog.Builder builder = new AlertDialog.Builder(
	NotlariListeleActivity.this);
	builder.setMessage(
	notlar.get(ps).konu
	+ " konulu notu silmek istedi�inizden emin misiniz ?")
	.setCancelable(true)
	.setPositiveButton("Evet",
	new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog, int id) {
	dba.idIleNotSil(notlar.get(ps)._id);

	int duration = Toast.LENGTH_SHORT;
	//Not silindikten sonra silindi olarak bildir.
	Toast toast = Toast.makeText(getApplicationContext(),
	notlar.get(ps).konu.toString()
	+ " silinmi�tir !",
	duration);
	toast.show();
	//Not listesini yenile
	yenile();
	}
	})
	.setNegativeButton("Hay�r",	new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog, int id) {
	dialog.cancel();
	}
	});
	AlertDialog alert = builder.create();
	alert.show();

	}
	/*
	* Bir not silindikten sonra tekrardan adapter atayarak veritaban�ndan gelen verilerle
	* not listesini yeniliyoruz.
	*/
	public void yenile()
	{
	cla = null;
	cla = new CustomListAdapter(getApplicationContext());
	setListAdapter(cla);
	if ((notlar = cla.notListesi()).size() == 0)
	yeniNot();

	}
}
