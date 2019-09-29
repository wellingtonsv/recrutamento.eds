package br.com.recrutamento.eds.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateTableJDBC {

	public static void main(String[] args) {

		Connection con = null;
		Statement stmt = null;
		int result = 0;

		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE produto ( ");
		sb.append("codigo INT NOT NULL, ");
		sb.append("descricao VARCHAR(200) NOT NULL, ");
		sb.append(" PRIMARY KEY (codigo));");

		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/comprasdb", "SA", "");
			stmt = con.createStatement();

			result = stmt.executeUpdate(sb.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		System.out.println("Table created successfully " + result);
	}
}