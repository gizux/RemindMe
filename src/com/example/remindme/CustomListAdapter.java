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
// Her defasýnda tekrardan sorulmasýndan kaçýnmak için LayoutInflate’i belleðe alýyoruz.
mInflater = LayoutInflater.from(context);
notlar = new ArrayList<Not>();

//Veritabanýndan tüm notlarý al ve notlar isimli ArrayList’e ata.
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

/* ViewHolder’ý diðer görsellerin referansýný tutmak için kullanýyoruz.
* Bu sayede belirli tekniklerle her defasýnda findViewById() çaðrýmýndan kaçýnýyoruz.
*/
ViewHolder holder;
/*
* convertView null deðilse tekrardan inflate yapmadan etkin bir þekilde çaðýrýyoruz,
* eðer null ise viewholder’daki referanslar ilkleniyor.
*
*/
if (convertView == null) {
//adapterin kullanýldýðý durumlarda notsatiri.xml dosyasýnýn yapýsý kullanýlacak.
convertView = mInflater.inflate(R.layout.notsatiri, null);

//viewholder oluþtur ve deðerleri baðla.
holder = new ViewHolder();
holder.mKonu = (TextView) convertView.findViewById(R.id.konuText);
holder.mTarih = (TextView) convertView.findViewById(R.id.tarihText);
} else {

//var olan viewholder’ý etkin bir þekilde geri çaðýrýyoruz.
holder = (ViewHolder) convertView.getTag();
}

/*
* veritabanýndaki her deðeri custom olarak belirlediðimiz ve 2 adet textview’den oluþan
* layouttaki tv’lere atýyoruz.
*/

holder.mTarih.setText(notlar.get(position).kayittarihi);
holder.mKonu.setText(notlar.get(position).konu);

// holder’ý baðla, getTag olarak eriþebileceðiz.
convertView.setTag(holder);

return convertView;
}

//TextView tipinde iki deðeri tutuyoruz.
public class ViewHolder {
TextView mKonu;
TextView mTarih;
}
}
