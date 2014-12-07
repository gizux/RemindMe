package com.example.remindme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class GuncelleActivity extends Activity {

	private EditText konuET, icerikET;
	private Button guncelleBTN;
	private NotDefteriDatabase dba;
	private int id;

	@Override
	public void onCreate(Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);
	setContentView(R.layout.ekleguncelle);

	//listele sayfasýndan gelen ID güncellenecek not id’sini temsil ediyor.

	id=Integer.parseInt(getIntent().getExtras().get("ID").toString());

	konuET = (EditText) findViewById(R.id.konuText);
	icerikET = (EditText) findViewById(R.id.icerikText);
	guncelleBTN = (Button) findViewById(R.id.opButton);
	guncelleBTN.setText("Güncelle");

	konuET.setText(getIntent().getExtras().get("KONU").toString());
	icerikET.setText(getIntent().getExtras().get("ICERIK").toString());

	guncelleBTN.setOnClickListener(new OnClickListener() {
	public void onClick(View v) {
	try {
	dba = new NotDefteriDatabase(GuncelleActivity.this);
	dba.ac();
	notGuncelle();

	} catch (Exception e) {
	e.printStackTrace();
	}
	}
	});
	}

	/*
	* Yeni deðerlerimizi alýp o ID’deki notu güncelliyoruz.
	*/

	public void notGuncelle() {

	dba.notGuncelle(id,konuET.getText().toString(), icerikET.getText()
	.toString());
	dba.kapat();

	Intent i = new Intent(GuncelleActivity.this, NotlariListeleActivity.class);
	startActivity(i);
	finish();
	}
}
