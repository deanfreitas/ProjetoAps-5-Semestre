package br.com.servidoraps;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.HashSet;

import javax.swing.JOptionPane;

import br.com.apsusuarios.Usuario;

public class Servidor extends Thread {
	private static ServerSocket chatServidor;
	public static HashSet<Usuario> acresentarUsuarios;
	public static HashSet<ChatThread> clienteSockets;

	public Servidor() {

		acresentarUsuarios = new HashSet<Usuario>();
		clienteSockets = new HashSet<ChatThread>();
	}

	public void run() {
		try {
			chatServidor = new ServerSocket(4545);
			while (true) {
				new ChatThread(chatServidor.accept());
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Finalizando Execução do servidor...");
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"Erro de inclusão de cliente...");
		}
	}

	public void closeSocket() throws IOException {
		try {
			chatServidor.close();
		} catch (SocketException e) {
			JOptionPane.showMessageDialog(null,
					"Execução do servidor finalizada.");
		}

	}
}