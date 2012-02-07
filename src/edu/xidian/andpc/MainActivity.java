package edu.xidian.andpc;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends TabActivity {

	private String ipString;
	
	//public static Socket socket;
	public static ObjectOutputStream outputStream;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ipString = getIntent().getStringExtra("ip");

		try {
			Socket socket = new Socket(InetAddress.getByName(ipString), 2012);
			outputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			Toast.makeText(this, "连接服务器失败,请重试!", Toast.LENGTH_LONG).show();
			startActivity(new Intent(MainActivity.this,FirstActivity.class));
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(this, "连接服务器失败,请重试!", Toast.LENGTH_LONG).show();
			startActivity(new Intent(MainActivity.this,FirstActivity.class));
			e.printStackTrace();
		}finally{
			
		}
		
		Toast.makeText(MainActivity.this, "连接服务器成功!", Toast.LENGTH_LONG).show();
		
        final TabHost tabHost = getTabHost();
        
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("PPT",getResources().getDrawable(R.drawable.ppt))
				.setContent(new Intent(this, PPTClient.class)));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Mouse",getResources().getDrawable(R.drawable.mouse))
				.setContent(new Intent(this, MouseClient.class))); 
        
    }
}