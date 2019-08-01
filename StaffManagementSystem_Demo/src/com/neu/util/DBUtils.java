package com.neu.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUtils {
	/*private String driverClassName = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=UTF-8";
	private String user = "root";
	private String password = "root";*/

/*	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	//��ͨ����������ʼ�������ߵ���get/set���ʻ��޸ĳ�ʼ�û�����Ĭ�ϲ���root/root�˻�
	public DBUtils(String user, String password) {
		setUser(user);
		setPassword(password);
	}*/
	public DBUtils() {
		
	}
	
	//������Ͽ����ݿ�
	public Connection getConnection() throws ClassNotFoundException,SQLException, NamingException{
		/*Class.forName(driverClassName);
		Connection connection = DriverManager.getConnection(url, user, password);*/
		
		Context ds = new InitialContext();
		DataSource lookup = (DataSource)ds.lookup("java:comp/env/jdbc/mydatasource");
		Connection connection = lookup.getConnection();
		return connection;
	}
	public void closeConnection(Connection connection) throws SQLException{
		connection.close();
	}
	
	//ִ����ɾ�Ĳ����������������ݿ������ֻ��Ҫ����sql�����ռλ���������ֵ
	public int executeUpdate(String sql,Object...params) throws ClassNotFoundException,SQLException, NamingException{
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(sql);	
		if(params != null) {
			for(int i = 0; i < params.length; i++) {
				statement.setObject(i+1, params[i]);
			}
		}		
		int i = statement.executeUpdate();
		statement.close();
		closeConnection(connection);
		return i;
	}
	
	//ִ�в�ѯ������ʹ��ʱ��Ҫ�ֶ�������ر����ݿ⣬��Ҫ����sql��䡢���ӡ�ռλ���������ֵ
	public ResultSet executeQuery(Connection connection,String sql,Object...params) throws ClassNotFoundException,SQLException{
		
		PreparedStatement statement = connection.prepareStatement(sql);	
		if(params != null) {
			for(int i = 0; i < params.length; i++) {
				statement.setObject(i+1, params[i]);
			}
		}		
		ResultSet rs = statement.executeQuery();
		return rs;
	}
	
}