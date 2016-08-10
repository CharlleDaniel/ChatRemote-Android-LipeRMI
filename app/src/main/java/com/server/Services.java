package com.server;

import java.util.List;

public interface Services {
	User login(String name, byte[] foto, String number);
	boolean logout(String name);
	boolean hasLogged(String number);
	List<User> getUsers(int id);
	boolean sendMsg(Message msg);
	List<Message> getMsg(int id);
	User getUser(String number);
	boolean hasMessage(int id);
	double getVersion();

}
