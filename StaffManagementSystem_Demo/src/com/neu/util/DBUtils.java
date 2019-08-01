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
	
	//可通过构造器初始化，或者调用get/set访问或修改初始用户名，默认采用root/root账户
	public DBUtils(String user, String password) {
		setUser(user);
		setPassword(password);
	}*/
	public DBUtils() {
		
	}
	
	//连接与断开数据库
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
	
	//执行增删改操作，内置连接数据库操作，只需要传递sql语句与占位符所代表的值
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
	
	//执行查询操作，使用时需要手动连接与关闭数据库，需要传递sql语句、连接、占位符所代表的值
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