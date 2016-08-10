package com.server;

import java.io.Serializable;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 100L;
	private byte[] msg;
	private int idTo;
	private int idFrom;
	private int id;
	private boolean isSend;
	
	
	public Message(byte[] msg, int idTo, int idFrom, boolean isSend) {
		this.msg = msg;
		this.idTo = idTo;
		this.idFrom = idFrom;
		this.isSend = isSend;
	}
	public Message(){

	}
	public byte[] getMsg() {
		return msg;
	}
	public void setMsg(byte[] msg) {
		this.msg = msg;
	}
	public int getIdTo() {
		return idTo;
	}
	public void setIdTo(int idTo) {
		this.idTo = idTo;
	}
	public int getIdFrom() {
		return idFrom;
	}
	public void setIdFrom(int idFrom) {
		this.idFrom = idFrom;
	}
	public boolean isSend() {
		return isSend;
	}
	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
