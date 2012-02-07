package edu.xidian.andpc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FirstActivity extends Activity{
	
	private Button connect;
	private EditText ipEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first);
		
		connect = (Button)findViewById(R.id.connect);
		ipEditText = (EditText)findViewById(R.id.ip);
		
		connect.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				String ipsString = ipEditText.getText().toString();
				if(ipsString.trim().equals("")){
					Toast.makeText(FirstActivity.this, "IP不能为空!", Toast.LENGTH_LONG).show();
					return;
				}else if(!ipcheck(ipsString.trim())){
					Toast.makeText(FirstActivity.this, "IP格式错误!", Toast.LENGTH_LONG).show();
					return;
				}else{
					String ipString = ipEditText.getText().toString();
					Intent intent = new Intent(FirstActivity.this, MainActivity.class);
					intent.putExtra("ip", ipString);
					startActivity(intent);
				}
			}
		});
	}
	
	private boolean ipcheck(String ip){
		Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
		Matcher matcher = pattern.matcher(ip); 
		return matcher.matches();
	}
}
