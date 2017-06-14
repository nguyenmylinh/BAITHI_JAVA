package com.example.baucua;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	GridView gridView;
	Custom_GridView_BanCo adapter;
	Integer[] dsbaucua = {R.drawable.nai,R.drawable.bau,R.drawable.ga,R.drawable.ca,R.drawable.cua,R.drawable.tom};
    AnimationDrawable cdXiNgau1, cdXiNgau2, cdXiNgau3;
    ImageView hinhXiNgau1,hinhXiNgau2,hinhXiNgau3;
    Random randomXiNgau;
    int giatriXiNgau1,giatriXiNgau2,giatriXiNgau3;
    Timer timer = new Timer();
    Handler handler;
    int tienthuong;
    Callback callback = new Callback() {
		@Override
		public boolean handleMessage(Message arg0) {
			randomXiNgau1();
			randomXiNgau2();
			randomXiNgau3();
			
			for(int i = 0; i < gtdatcuoc.length;i++){
				if(gtdatcuoc[i] != 0){
					if(i == giatriXiNgau1){
						tienthuong += gtdatcuoc[i];
					}
					if(i == giatriXiNgau2){
						tienthuong += gtdatcuoc[i];
					}
					if(i == giatriXiNgau3){
						tienthuong += gtdatcuoc[i];
					}
					if(i != giatriXiNgau1 && i != giatriXiNgau2 && i != giatriXiNgau3){
						tienthuong -= gtdatcuoc[i];
					}
				}
			}
			if(tienthuong > 0){
				Toast.makeText(getApplicationContext(), "Bạn thắng được " + tienthuong, Toast.LENGTH_SHORT).show();
			}else if(tienthuong == 0){
				Toast.makeText(getApplicationContext(), "Bạn hòa tiền", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getApplicationContext(), "Bạn đã thua " + tienthuong, Toast.LENGTH_SHORT).show();
			}
			
			LuuDuLieu(tienthuong);
			tvwTien.setText(String.valueOf(tongtienmoi));
			Log.d("KetQua",giatriXiNgau1 + " " + giatriXiNgau2 + " " + giatriXiNgau3 + " " + tienthuong);
			return false;
			
		}
	};
    public static Integer[] gtdatcuoc = new Integer[6];
    SharedPreferences Luutru;
    TextView tvwTien,tvwthoigian;
    int tongtiencu, tongtienmoi , kiemtra;
    CountDownTimer demthoigian;
    
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        hinhXiNgau1 = (ImageView) findViewById(R.id.imagexingau1);
        hinhXiNgau2 = (ImageView) findViewById(R.id.imagexingau2);
        hinhXiNgau3 = (ImageView) findViewById(R.id.imagexingau3);
        
        gridView = (GridView) findViewById(R.id.gvwBauCua);
        adapter = new Custom_GridView_BanCo(this,R.layout.banbaucua_layout, dsbaucua);
        gridView.setAdapter(adapter);
        
        tvwTien = (TextView) findViewById(R.id.textViewTien);
        tvwthoigian = (TextView) findViewById(R.id.textViewthoigian);
        Luutru = getSharedPreferences("luutruthongtin", Context.MODE_PRIVATE);
        tongtiencu = Luutru.getInt("tongtien", 1000);
        tvwTien.setText(String.valueOf(tongtiencu));
        
        demthoigian = new CountDownTimer(60000,1000) {
			
			@SuppressLint("NewApi") @Override
			public void onTick(long millisUntilFinished) {
				long millis = millisUntilFinished;
				long gio = TimeUnit.MILLISECONDS.toHours(millis);
				long phut = TimeUnit.MILLISECONDS.toMinutes(millis)- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
				long giay = TimeUnit.MILLISECONDS.toSeconds(millis)- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
				
				String giophutgiay = String.format("%02d:%02d:%02d", gio,phut,giay);
				tvwthoigian.setText(giophutgiay);
			}
			
			@Override
			public void onFinish() {
				Editor edit = Luutru.edit();
				tongtiencu = Luutru.getInt("tongtien", 1000);
				tongtienmoi = tongtiencu + 500;
				edit.putInt("tongtien", tongtienmoi);
				edit.commit();
				tvwTien.setText(String.valueOf(tongtienmoi));
				demthoigian.cancel();
				demthoigian.start();
			}
		};
		demthoigian.start();
        handler = new Handler(callback);
        
        
    }
	
	private void LuuDuLieu(int tienthuong){
		Editor edit = Luutru.edit();
		tongtiencu = Luutru.getInt("tongtien", 1000);
		tongtienmoi = tongtiencu + tienthuong;
		edit.putInt("tongtien", tongtienmoi);
		edit.commit();
	}
	public void DoXiNgau(View v)
	{
		hinhXiNgau1.setImageResource(R.drawable.xingau);
		hinhXiNgau2.setImageResource(R.drawable.xingau);
		hinhXiNgau3.setImageResource(R.drawable.xingau);
		
		cdXiNgau1 = (AnimationDrawable) hinhXiNgau1.getDrawable();
		cdXiNgau2 = (AnimationDrawable) hinhXiNgau2.getDrawable();
		cdXiNgau3 = (AnimationDrawable) hinhXiNgau3.getDrawable();
		
		kiemtra = 0;
		for (int i = 0; i < gtdatcuoc.length; i++) {
			kiemtra += gtdatcuoc[i];
			
		}
		if(kiemtra == 0){
			Toast.makeText(getApplicationContext(), "Bạn chưa đặt cược", Toast.LENGTH_SHORT).show();
		}
		else{
			if(kiemtra > tongtiencu){
				Toast.makeText(getApplicationContext(), "Bạn không đủ tiền đặt cược", Toast.LENGTH_SHORT).show();
			}
			else{
				cdXiNgau1.start();
				cdXiNgau2.start();
				cdXiNgau3.start();
				tienthuong = 0;
				timer.schedule(new TimerTask() {	
					public void run() {
						handler.sendEmptyMessage(0);
					}
				}, 1000);
			}
		}
	}
	
	private void randomXiNgau1(){
		randomXiNgau = new Random();
		int rd = randomXiNgau.nextInt(6);
		switch (rd) {
		case 0:
			hinhXiNgau1.setImageResource(dsbaucua[0]);
			giatriXiNgau1 = rd;
			break;
		case 1:
			hinhXiNgau1.setImageResource(dsbaucua[1]);
			giatriXiNgau1 = rd;
			break;
		case 2:
			hinhXiNgau1.setImageResource(dsbaucua[2]);
			giatriXiNgau1 = rd;
			break;
		case 3:
			hinhXiNgau1.setImageResource(dsbaucua[3]);
			giatriXiNgau1 = rd;
			break;
		case 4:
			hinhXiNgau1.setImageResource(dsbaucua[4]);
			giatriXiNgau1 = rd;
			break;
		case 5:
			hinhXiNgau1.setImageResource(dsbaucua[5]);
			giatriXiNgau1 = rd;
			break;
		}
	}
	
	private void randomXiNgau2(){
		randomXiNgau = new Random();
		int rd = randomXiNgau.nextInt(6);
		switch (rd) {
		case 0:
			hinhXiNgau2.setImageResource(dsbaucua[0]);
			giatriXiNgau2 = rd;
			break;
		case 1:
			hinhXiNgau2.setImageResource(dsbaucua[1]);
			giatriXiNgau2 = rd;
			break;
		case 2:
			hinhXiNgau2.setImageResource(dsbaucua[2]);
			giatriXiNgau2 = rd;
			break;
		case 3:
			hinhXiNgau2.setImageResource(dsbaucua[3]);
			giatriXiNgau2 = rd;
			break;
		case 4:
			hinhXiNgau2.setImageResource(dsbaucua[4]);
			giatriXiNgau2 = rd;
			break;
		case 5:
			hinhXiNgau2.setImageResource(dsbaucua[5]);
			giatriXiNgau2 = rd;
			break;
		}
	}
	
	private void randomXiNgau3(){
		randomXiNgau = new Random();
		int rd = randomXiNgau.nextInt(6);
		switch (rd) {
		case 0:
			hinhXiNgau3.setImageResource(dsbaucua[0]);
			giatriXiNgau3 = rd;
			break;
		case 1:
			hinhXiNgau3.setImageResource(dsbaucua[1]);
			giatriXiNgau3 = rd;
			break;
		case 2:
			hinhXiNgau3.setImageResource(dsbaucua[2]);
			giatriXiNgau3 = rd;
			break;
		case 3:
			hinhXiNgau3.setImageResource(dsbaucua[3]);
			giatriXiNgau3 = rd;
			break;
		case 4:
			hinhXiNgau3.setImageResource(dsbaucua[4]);
			giatriXiNgau3 = rd;
			break;
		case 5:
			hinhXiNgau3.setImageResource(dsbaucua[5]);
			giatriXiNgau3 = rd;
			break;
		}
	}
}
