package br.com.servidoraps;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.com.apsusuarios.Mensagem;
import br.com.apsusuarios.Usuario;

public class ChatThread extends Thread {

	private Socket cliente;

	public Socket getCliente() {
		return cliente;
	}

	public void setCliente(Socket cliente) {
		this.cliente = cliente;
	}

	private ObjectInputStream dis;
	private ObjectOutputStream dos;

	public ObjectOutputStream getDos() {
		return dos;
	}

	public void setDos(ObjectOutputStream dos) {
		this.dos = dos;
	}

	public ChatThread(Socket cliente) throws IOException,
			ClassNotFoundException {
		this.cliente = cliente;
		dos = new ObjectOutputStream(cliente.getOutputStream());
		dis = new ObjectInputStream(cliente.getInputStream());
		Usuario usuario = (Usuario) dis.readObject();
		System.out.print("Usuário " + usuario.getLogin()
				+ " entrou no chat... \n");
		Servidor.acresentarUsuarios.add(usuario);
		Servidor.clienteSockets.add(this);
		start();
	}

	public void run() {
		try {
			while (true) {
				Mensagem msg = (Mensagem) dis.readObject();
				System.out.println(msg.getDadosUsuarioLogin().getLogin() + ": "
						+ msg.getMensagem());
				for (ChatThread ct : Servidor.clienteSockets) {
					ct.getDos().writeObject(msg);
					ct.getDos().flush();
					ct.getDos().reset();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
	}

}
