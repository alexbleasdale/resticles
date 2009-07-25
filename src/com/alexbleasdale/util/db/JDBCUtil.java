package com.alexbleasdale.util.db;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JDBCUtil {

	private static final Log LOG = LogFactory.getLog(JDBCUtil.class);

	/**
	 * Rudimentary function to output any sql messages - currenly only handles
	 * warnings.
	 * 
	 * @param rs
	 * @throws SQLException
	 */
	public static void logSQLMessages(PreparedStatement st) throws SQLException {
		SQLWarning warn = st.getWarnings();
		while (warn != null) {
			LOG.warn("SQL Warning: " + warn.getMessage());
			System.out.println(warn.getMessage());
			warn = warn.getNextWarning();
		}
	}

	/**
	 * Rudimentary function to output any sql messages - currenly only handles
	 * warnings.
	 * 
	 * @param rs
	 * @throws SQLException
	 */
	public static void logSQLMessages(CallableStatement cs) throws SQLException {
		SQLWarning warn = cs.getWarnings();
		while (warn != null) {
			LOG.warn("SQL Warning: " + warn.getMessage());
			System.out.println(warn.getMessage());
			warn = warn.getNextWarning();
		}
	}

	/**
	 * Rudimentary function to output any sql messages - currenly only handles
	 * warnings.
	 * 
	 * @param rs
	 * @throws SQLException
	 */
	public static void logSQLMessages(ResultSet rs) throws SQLException {
		SQLWarning warn = rs.getWarnings();
		while (warn != null) {
			LOG.warn("SQL Warning: " + warn.getMessage());
			System.out.println(warn.getMessage());
			warn = warn.getNextWarning();
		}
	}

	/**
	 * Will close the result set, statement, and connection (in that order). If
	 * an exception is thrown, it'll still try to close the remaining objects.
	 * Exceptions are logged using apache commons logging, at the WARN level.
	 * 
	 * @param c
	 * @param stmt
	 * @param r
	 */
	public static void silentlyClose(Connection c, PreparedStatement stmt,
			ResultSet r) {
		try {
			r.close();
		} catch (Exception e) {
			LOG.warn("Error closing result set.", e);
		}
		silentlyClose(c, stmt);
	}

	/**
	 * Will close the result set, statement, and connection (in that order). If
	 * an exception is thrown, it'll still try to close the remaining objects.
	 * Exceptions are logged using apache commons logging, at the WARN level.
	 * 
	 * @param c
	 * @param stmt
	 * @param r
	 */
	public static void silentlyClose(Connection c, CallableStatement stmt,
			ResultSet r) {
		try {
			r.close();
		} catch (Exception e) {
			LOG.warn("Error closing result set.", e);
		}
		silentlyClose(c, stmt);
	}

	/**
	 * Will close the statement, and connection (in that order). If an exception
	 * is thrown, it'll still try to close the remaining objects. Exceptions are
	 * logged using apache commons logging, at the WARN level.
	 * 
	 * @param c
	 * @param stmt
	 */
	public static void silentlyClose(Connection c, PreparedStatement stmt) {
		try {
			stmt.close();
		} catch (Exception e) {
			LOG.warn("Error closing prepared statement.", e);
		}
		silentlyClose(c);
	}

	/**
	 * Will close the statement, and connection (in that order). If an exception
	 * is thrown, it'll still try to close the remaining objects. Exceptions are
	 * logged using apache commons logging, at the WARN level.
	 * 
	 * @param c
	 * @param stmt
	 */
	public static void silentlyClose(Connection c, CallableStatement stmt) {
		try {
			stmt.close();
		} catch (Exception e) {
			LOG.warn("Error closing callable statement.", e);
		}
		silentlyClose(c);
	}

	/**
	 * Will close the connection (in that order). Exceptions are logged using
	 * apache commons logging, at the WARN level.
	 * 
	 * @param c
	 */
	public static void silentlyClose(Connection c) {
		try {
			c.close();
		} catch (Exception e) {
			LOG.warn("Error closing connection.", e);
		}
	}

	/**
	 * Will close the connection (in that order). Exceptions are logged using
	 * apache commons logging, at the WARN level.
	 * 
	 * @param c
	 */
	public static void silentlyClose(CallableStatement c) {
		try {
			c.close();
		} catch (Exception e) {
			LOG.warn("Error closing connection.", e);
		}
	}

	/**
	 * Will close the connection (in that order). Exceptions are logged using
	 * apache commons logging, at the WARN level.
	 * 
	 * @param c
	 */
	public static void silentlyClose(PreparedStatement c) {
		try {
			c.close();
		} catch (Exception e) {
			LOG.warn("Error closing connection.", e);
		}
	}

	/**
	 * Will close the connection (in that order). Exceptions are logged using
	 * apache commons logging, at the WARN level.
	 * 
	 * @param c
	 */
	public static void silentlyClose(ResultSet rs) {
		try {
			rs.close();
		} catch (Exception e) {
			LOG.warn("Error closing connection.", e);
		}
	}

}
