package com.server;

import java.io.Serializable;

public class User implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 100L;
	private String name;
	private byte[] foto;
	private	int id;
	private String number;

	public User(String name, byte[] foto, int id, String number) {
		this.name = name;
		setFoto(foto);
		this.id = id;
		this.number=number;
	}
	public User(){

	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte[] getFoto(){
		return this.foto;
	}
	public void setFoto(byte[] foto) {
		this.foto=foto;
	}
}
