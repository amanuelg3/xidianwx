package edu.xidian.andpc;

import java.io.IOException;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PPTClient extends Activity {
	private Button start;
	private Button escape;
	private ImageButton forward;
	private Button back;
	private Button close;
	//private Button connect;
	//private EditText ipEditText;
	private ObjectOutputStream outputStream;

	private final static int RIGHT = 1;
	private final static int LEFT = 2;
	private final static int SHIFTF5 = 0;
	private final static int ESC = 3;
	private final static int ALTF4 = 4;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ppt);
		
		//String ip = getIntent().getStringExtra("ipString");
	//System.out.println("ppt client ip >>> " + ip);
		//System.out.println("ppt socket >>> " + MainActivity.socket);
		
		outputStream = MainActivity.outputStream;

		start = (Button)findViewById(R.id.start);
		escape = (Button)findViewById(R.id.escape);
		forward = (ImageButton)findViewById(R.id.froward);
		back = (Button)findViewById(R.id.back);
		close = (Button)findViewById(R.id.close);
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int w = displayMetrics.widthPixels;
		start.setWidth(w/2);
		escape.setWidth(w/2);
		back.setWidth(w/2);
		close.setWidth(w/2);

		start.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Choices choice = new Choices(SHIFTF5,Choices.TYPE_NUM);
				try {
					outputStream.writeObject(choice);
					System.out.println("send the start shift + f5");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});

		escape.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Choices choice = new Choices(ESC, Choices.TYPE_NUM);
				try {
					outputStream.writeObject(choice);
					System.out.println("send the escape");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		forward.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Choices choice = new Choices(RIGHT, Choices.TYPE_NUM);
				try {
					outputStream.writeObject(choice);
					System.out.println("send the right (the next)");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		});
		back.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Choices choice = new Choices(LEFT, Choices.TYPE_NUM);
				try {
					outputStream.writeObject(choice);
					System.out.println("send the left (the last)");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		close.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Choices choices = new Choices(ALTF4, Choices.TYPE_NUM);
				try {
					outputStream.writeObject(choices);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("exit app");
			builder.setMessage("You will exit the app...");
			// builder.setIcon(R.drawable.stat_sys_warning);
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent startMain = new Intent(Intent.ACTION_MAIN);
							startMain.addCategory(Intent.CATEGORY_HOME);
							startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(startMain);
							System.exit(0);
						}
					});
			builder.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});
			builder.show();
		}
		return super.onKeyDown(keyCode, event);
	}
}