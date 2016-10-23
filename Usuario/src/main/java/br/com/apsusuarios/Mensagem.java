package br.com.apsusuarios;

import java.io.Serializable;

public class Mensagem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6319650000964160535L;
	private String mensagem;
	private Usuario dadosUsuarioLogin;

	public Usuario getDadosUsuarioLogin() {
		return dadosUsuarioLogin;
	}

	public void setDadosUsuarioLogin(Usuario dadosUsuario) {
		this.dadosUsuarioLogin = dadosUsuario;
	}

	public String getMensagem() {
		return this.mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
