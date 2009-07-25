package com.alexbleasdale.util.db;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.microsoft.sqlserver.jdbc.SQLServerConnectionPoolDataSource;

/**
 * This class will create and bind a datasource using JNDI. Probably only useful
 * in unit testing, as during production, you'd get this from the app server...
 * 
 * @author jgrey
 * 
 */
public class SQLServerDataSourceFactory {

	/**
	 * Creates a SQLServerConnectionPoolDataSource.
	 * 
	 * @param server
	 * @param database
	 * @param username
	 * @param password
	 * @param jndiName
	 * @param useCursorSelectMethod
	 * @throws NamingException
	 */
	public static void mountTestDatasource(String server, String database,
			String username, String password, String jndiName,
			boolean useCursorSelectMethod) throws NamingException {

		SQLServerConnectionPoolDataSource ds = new SQLServerConnectionPoolDataSource();
		ds.setServerName(server);
		ds.setDatabaseName(database);
		ds.setUser(username);
		ds.setPassword(password);
		if (useCursorSelectMethod) {
			ds.setSelectMethod("cursor");
		}
		InitialContext ctx = new InitialContext();
		ctx.bind(jndiName, ds);
	}

}
