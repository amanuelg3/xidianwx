package edu.xidian.andpc;

import java.io.IOException;
import java.io.ObjectOutputStream;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

public class MouseClient extends Activity implements OnTouchListener, OnGestureListener{
	private static final int SINGLECLICK = 11;
	private static final int DOUBLECLICK = 12;
	private static final int RIGHTCLICK = 13;
	
	private static final int TOUCHPANEL1 = -1;
	private static final int TOUCHPANEL2 = 0;
	
	private Button singleClick, doubleClick, rightClick;
	private TextView panel;
	private TextView panel2;
	private int touchnum;
	
	private ObjectOutputStream outputStream;
	private GestureDetector gestureDetector = null;
	private GestureDetector gestureDetector2 = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mouse);
		outputStream = MainActivity.outputStream;
		
		singleClick = (Button)findViewById(R.id.singleclick);
		doubleClick = (Button)findViewById(R.id.doubleclick);
		rightClick = (Button)findViewById(R.id.rightclick);
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int w = displayMetrics.widthPixels;
		singleClick.setWidth(w/3);
		doubleClick.setWidth(w/3);
		rightClick.setWidth(w/3);
		
		
		
		panel = (TextView)findViewById(R.id.mouse_tv);
		panel.setFocusable(true);  
		panel.setClickable(true);  
		panel.setLongClickable(true);  
		panel.setOnTouchListener(this);
		
		panel2 = (TextView)findViewById(R.id.mouse_tv2);
		panel2.setFocusable(true);  
		panel2.setClickable(true);  
		panel2.setLongClickable(true);  
		panel2.setOnTouchListener(this);
		
		gestureDetector = new GestureDetector(this);  
		gestureDetector.setIsLongpressEnabled(true);
		gestureDetector2 = new GestureDetector(this);  
		gestureDetector2.setIsLongpressEnabled(true);
		
		singleClick.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Choices choices = new Choices(SINGLECLICK, Choices.TYPE_NUM);
				try {
					outputStream.writeObject(choices);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		doubleClick.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Choices choices = new Choices(DOUBLECLICK, Choices.TYPE_NUM);
				try {
					outputStream.writeObject(choices);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		rightClick.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Choices choices = new Choices(RIGHTCLICK, Choices.TYPE_NUM);
				try {
					outputStream.writeObject(choices);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("onDown");
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float x,
			float y) {
		System.out.println("onFling");
		
		
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("longpress");
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		System.out.println("onscroll");
		float x1 = e1.getX();
		float y1 = e1.getY();
		float x2 = e2.getX();
		float y2 = e2.getY();
		if(touchnum == TOUCHPANEL1){
			Position pos = new Position((int)((x2-x1)/10), (int)((y2-y1)/10));
			Choices choices = new Choices(pos, Choices.TYPE_POS);
			try {
				outputStream.writeObject(choices);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(touchnum == TOUCHPANEL2){
			Choices choice = new Choices((int)(distanceY/10), Choices.TYPE_MOUSEWHEEL);
			try {
				outputStream.writeObject(choice);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("onShowPress");
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("onSingleTapUp");

		Choices choices = new Choices(SINGLECLICK, Choices.TYPE_NUM);
		try {
			outputStream.writeObject(choices);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		System.out.println("onTouch");
		if(v.getId() == panel.getId()){
			touchnum = TOUCHPANEL1;
			return gestureDetector.onTouchEvent(event);
		}else{
			touchnum = TOUCHPANEL2;
			return gestureDetector2.onTouchEvent(event);
		}
		
	}
	
	
}
