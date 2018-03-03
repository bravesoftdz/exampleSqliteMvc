package by.gvozdev.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Programm {

	public static final String SENTENCE_CONNECT = "jdbc:sqlite:C:\\Users\\first\\eclipse-workspace\\SqliteMVCDemo\\src\\by\\gvozdev\\sqlite\\users.db";
	public static final String CLASS_FOR_NAME = "org.sqlite.JDBC";

	Connection cn;

	public static void main(String... args) {

		Programm programm = new Programm();
		if (programm.open()) {
			programm.insert();
			programm.select();
			programm.delete();
			programm.close();
		} else {
			System.out.println("Didn't connect");
		}

	}

	boolean open() {
		try {

			try {
				Class.forName(CLASS_FOR_NAME);
			} catch (ClassNotFoundException eString) {
				System.err.println("Could not init JDBC driver - driver not found");
			}

			cn = DriverManager.getConnection(SENTENCE_CONNECT);
			System.out.println("Connected");
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}

	private void insert() {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter user name: ");
			String name = scanner.nextLine();
			System.out.println("Enter user phone: ");
			String phone = scanner.nextLine();

			String query = "INSERT INTO users (name, phone) " + "VALUES ('" + name + "', '" + phone + "');";
			Statement statement = cn.createStatement();
			statement.executeUpdate(query);

			System.out.println("ROW ADDED");
			statement.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void select() {
		try {
			String query = "SELECT id, name, phone " + "FROM users";
			Statement statement = cn.createStatement();
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				System.out.println(id + "\t|   " + name + "\t|   " + phone);
			}

			rs.close();
			statement.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void delete() {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter user name: ");
			int id = scanner.nextInt();

			String query = "DELETE FROM users WHERE id=" + id + ";";
			Statement statement = cn.createStatement();
			statement.executeUpdate(query);

			System.out.println("ROW (" + id + ") WAS DELETED");
			statement.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void close() {
		try {
			cn.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
