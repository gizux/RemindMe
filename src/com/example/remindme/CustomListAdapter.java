package com.example.remindme;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {


private LayoutInflater mInflater;
private ArrayList<Not> notlar;
private NotDefteriDatabase dba;

public CustomListAdapter(Context context) {
// Her defas�nda tekrardan sorulmas�ndan ka��nmak i�in LayoutInflate�i belle�e al�yoruz.
mInflater = LayoutInflater.from(context);
notlar = new ArrayList<Not>();

//Veritaban�ndan t�m notlar� al ve notlar isimli ArrayList�e ata.
dba = new NotDefteriDatabase(context);
dba.ac();
notlar=dba.tumNotlar();
dba.kapat();
}

public int getCount() {
return notlar.size();
}

public long getItemId(int position) {
return position;
}

public Not getItem(int i) {
return notlar.get(i);
}

public ArrayList<Not> notListesi() {
return notlar;
}

public View getView(int position, View convertView, ViewGroup parent) {

/* ViewHolder�� di�er g�rsellerin referans�n� tutmak i�in kullan�yoruz.
* Bu sayede belirli tekniklerle her defas�nda findViewById() �a�r�m�ndan ka��n�yoruz.
*/
ViewHolder holder;
/*
* convertView null de�ilse tekrardan inflate yapmadan etkin bir �ekilde �a��r�yoruz,
* e�er null ise viewholder�daki referanslar ilkleniyor.
*
*/
if (convertView == null) {
//adapterin kullan�ld��� durumlarda notsatiri.xml dosyas�n�n yap�s� kullan�lacak.
convertView = mInflater.inflate(R.layout.notsatiri, null);

//viewholder olu�tur ve de�erleri ba�la.
holder = new ViewHolder();
holder.mKonu = (TextView) convertView.findViewById(R.id.konuText);
holder.mTarih = (TextView) convertView.findViewById(R.id.tarihText);
} else {

//var olan viewholder�� etkin bir �ekilde geri �a��r�yoruz.
holder = (ViewHolder) convertView.getTag();
}

/*
* veritaban�ndaki her de�eri custom olarak belirledi�imiz ve 2 adet textview�den olu�an
* layouttaki tv�lere at�yoruz.
*/

holder.mTarih.setText(notlar.get(position).kayittarihi);
holder.mKonu.setText(notlar.get(position).konu);

// holder�� ba�la, getTag olarak eri�ebilece�iz.
convertView.setTag(holder);

return convertView;
}

//TextView tipinde iki de�eri tutuyoruz.
public class ViewHolder {
TextView mKonu;
TextView mTarih;
}
}
