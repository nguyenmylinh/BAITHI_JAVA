package com.example.baucua;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class Custom_GridView_BanCo extends ArrayAdapter<Integer> {

	Context context;
	int resource;
	Integer[] objects;
	Integer[] giatien = {0,1,2,5,10,20,50,100,200,500};
	ArrayAdapter<Integer> adapter;
	
	public Custom_GridView_BanCo(Context context, int resource,Integer[] objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.resource = resource;
		this.objects = objects;
		adapter = new ArrayAdapter<Integer>(context, android.R.layout.simple_spinner_item,giatien);
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = View.inflate(context, resource, null);
		ImageView imgbaucua = (ImageView) view.findViewById(R.id.imgbaucua);
		Spinner spngiatien = (Spinner) view.findViewById(R.id.spngiatien);
		imgbaucua.setImageResource(objects[position]);
		spngiatien.setAdapter(adapter);
		spngiatien.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int positionspn, long id) {
				MainActivity.gtdatcuoc[position] = giatien[positionspn];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			
		});
		return view;
	}
}
