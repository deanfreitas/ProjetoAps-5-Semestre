package br.com.servidoraps;

import javax.swing.JOptionPane;

public class ExecutarServidor {

	private static Servidor servidor = null;

	public static void main(String[] args) {

		Servidor();
	}

	public static void Servidor() {

		if (servidor == null) {
			servidor = new Servidor();
			servidor.start();
			JOptionPane.showMessageDialog(null,
					"Execução do servidor inicializada.");
		} else {
			JOptionPane
					.showMessageDialog(null, "Servidor já foi inicializado.");
		}
	}

}
