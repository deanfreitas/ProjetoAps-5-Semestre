package br.com.aps;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.apsusuarios.Usuario;

public class Login extends JFrame implements ActionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -644445872486232459L;
	private Usuario usuario = new Usuario();
	private JButton entrar;
	private JButton cadatrarBotao;
	private JTextField textUsuario;
	private JTextField textSenha;
	private JLabel usuarioLabel;
	private JLabel senha;
	private String ip;
	private String senhaCriptografada;
	private BancoDados bancoDados = new BancoDados();
	private Thread thread;

	public Login() {
		setTitle("Quick.Text");
		setSize(400, 600);
		setLocation(450, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("backgroudlogin.png"))));

		setResizable(false);
		pack();
		setVisible(false);

		usuarioLabel = new JLabel("Usuário:");
		usuarioLabel.setLocation(170, 270);
		usuarioLabel.setSize(250, 20);

		senha = new JLabel("Senha:");
		senha.setLocation(170, 320);
		senha.setSize(150, 20);

		textUsuario = new JTextField("");
		textUsuario.setSize(150, 30);
		textUsuario.setLocation(120, 290);

		textSenha = new JPasswordField("");
		textSenha.setSize(150, 30);
		textSenha.setLocation(120, 350);
		textSenha.addKeyListener(this);

		entrar = new JButton("Entrar");
		entrar.setSize(80, 40);
		entrar.setLocation(150, 410);
		entrar.addActionListener(this);
		entrar.addKeyListener(this);
		entrar.setBackground(new Color(0, 0, 255));
		entrar.setForeground(Color.WHITE);
		entrar.setBorder(BorderFactory
				.createLineBorder(new Color(255, 20, 147)));

		cadatrarBotao = new JButton("Cadastrar");
		cadatrarBotao.setSize(80, 40);
		cadatrarBotao.setLocation(150, 460);
		cadatrarBotao.addActionListener(this);
		cadatrarBotao.setBackground(new Color(0, 0, 255));
		cadatrarBotao.setForeground(Color.WHITE);
		cadatrarBotao.setBorder(BorderFactory.createLineBorder(new Color(255,
				20, 147)));

		getContentPane().setLayout(null);
		getContentPane().add(usuarioLabel);
		getContentPane().add(senha);
		getContentPane().add(entrar);
		getContentPane().add(cadatrarBotao);
		getContentPane().add(textUsuario);
		getContentPane().add(textSenha);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(entrar)) {

			try {
				Logar();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
		}

		if (e.getSource().equals(cadatrarBotao)) {

			Cadastrar cadastrar = new Cadastrar();
			cadastrar.setVisible(true);
			dispose();

		}
	}

	public static void main(String[] args) {
		new Login().setVisible(true);
	}

	public void Logar() throws UnknownHostException {

		MessageDigest algorithm = null;
		try {
			algorithm = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		byte messageDigest[] = null;

		try {
			messageDigest = algorithm.digest(textSenha.getText().getBytes(
					"UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		StringBuilder hexString = new StringBuilder();
		for (byte b : messageDigest) {
			hexString.append(String.format("%02X", 0xFF & b));
		}
		senhaCriptografada = hexString.toString();

		usuario.setLogin(textUsuario.getText());
		usuario.setSenha(senhaCriptografada);
		ip = InetAddress.getLocalHost().getHostAddress();
		usuario.setIp(ip);

		if (bancoDados.Select(usuario) == true) {
			try {
				Chat chat = new Chat(usuario);
				thread = new Thread(chat);
				thread.start();
				chat.setVisible(true);
				dispose();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		else {
			JOptionPane.showMessageDialog(null,
					"Esse usuario não está cadastrado");
		}

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

			try {
				Logar();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
		}

	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}
}
