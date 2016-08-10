package com.chatremotemsg.database;


/**
 * Created by Thainan on 28/06/15.
 */
public class ScriptSQL {

    public String getCreateUser(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("create table user(");
        sqlBuilder.append("_id integer primary key not null,");
        sqlBuilder.append("nome text not null,");
        sqlBuilder.append("foto blob not null,");
        sqlBuilder.append("number text not null");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public String getCreateMessage(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("create table message(");
        sqlBuilder.append("_id integer primary key autoincrement,");
        sqlBuilder.append("idFrom integer not null,");
        sqlBuilder.append("idTo integer not null,");
        sqlBuilder.append("msg blob not null,");
        sqlBuilder.append("isSend text not null");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }


    public String getCreateProfile(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("create table profile(");
        sqlBuilder.append("_id integer primary key not null,");
        sqlBuilder.append("Nome text not null,");
        sqlBuilder.append("Foto blob not null,");
        sqlBuilder.append("version double,");
        sqlBuilder.append("number text not null");
        sqlBuilder.append(");");


        return sqlBuilder.toString();
    }
}
