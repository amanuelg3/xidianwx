package edu.xidian.andpc;
 

import java.io.Serializable;

public class Choices implements Serializable{
	 
	private static final long serialVersionUID = -3540151055940680375L;
	
	public static final int TYPE_NUM = 1234;
	public static final int TYPE_POS = 2345;
	public static final int TYPE_TEXT = 3456;
	public static final int TYPE_MOUSEWHEEL = 4567;
	 
	private int key;
	private Position pos;
	private int type;

	public Choices(int key, int type) {
		super();
		this.key = key;
		this.type = type;
	}
	
	public Choices(Position pos, int type){
		this.pos = pos;
		this.type = type;
	}
	
	public Choices(int x, int y, int type){
		this.pos.setX(x);
		this.pos.setY(y);
		this.type = type;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
