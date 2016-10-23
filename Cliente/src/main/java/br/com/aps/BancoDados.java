package br.com.aps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.apsusuarios.Usuario;

public class BancoDados {
	public static Connection getConexaoMySQL() {
		try {
			String driverName = "com.mysql.jdbc.Driver";
			Class.forName(driverName);
			String serverName = "";
			String mydatabase = "";
			String url = "jdbc:mysql://" + serverName + ":3306/" + mydatabase;
			String username = "root";
			String password = "";
			Connection connection = DriverManager.getConnection(url, username,
					password);
			return connection;
		} catch (ClassNotFoundException e) {
			System.out.println("\n O driver expecificado nao foi encontrado.");
			return null;
		} catch (SQLException e) {
			System.out.println("\n Nao foi possivel conectar ao Banco de Dados.");
			return null;
		}
	}

	public void Insert(String login, String senhaCriptografada) {
		try {

			String sql = "Insert usuario (login, senha) " + "Values (?, ?)";
			PreparedStatement st = getConexaoMySQL().prepareStatement(sql);
			
			st.setString(1, login);
			st.setString(2, senhaCriptografada);
			
			st.executeUpdate();
		}

		catch (SQLException sqlex) {
			System.out.println("erro sql " + sqlex);
			sqlex.printStackTrace();
		}
	}

	public boolean Select(Usuario usuario) {
		try {
			Statement stmt = getConexaoMySQL().createStatement();
			String sql = "SELECT * FROM usuario WHERE login = '" + usuario.getLogin() + "'";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String login = rs.getString("login");
				String senha = rs.getString("senha");
				
				System.out.println();
				System.out.println("Login: " + login);
				System.out.println("Senha: " + senha);
				
				return true;
			}

			rs.close();
		}

		catch (SQLException sqlex) {
			System.out.println("erro sql " + sqlex);
			sqlex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean Select(String loginUsuario) {
		try {
			Statement stmt = getConexaoMySQL().createStatement();
			String sql = "SELECT * FROM usuario WHERE login = '" + loginUsuario + "'";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String login = rs.getString("login");
				String senha = rs.getString("senha");
				
				System.out.println();
				System.out.println("Login: " + login);
				System.out.println("Senha: " + senha);
				
				if(loginUsuario.equals(login))
				return false;
			}

			rs.close();
		}

		catch (SQLException sqlex) {
			System.out.println("erro sql " + sqlex);
			sqlex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}


}
