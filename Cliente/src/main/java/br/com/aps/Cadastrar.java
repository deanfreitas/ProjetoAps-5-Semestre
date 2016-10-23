package br.com.aps;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Cadastrar extends JFrame implements ActionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6603048248548449274L;
	private JButton cadastrar;
	private JTextField textLogin;
	private JTextField textSenha;
	private JLabel usuarioLabel;
	private JLabel senha;
	private String senhaDigitada;
	private String senhaCriptografada;
	private String login;
	private BancoDados bancoDados = new BancoDados();
	private Login loginClasse = new Login();

	public Cadastrar() {

		setTitle("Quick.Text");
		setSize(400, 600);
		setLocation(450, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {

			setContentPane(new JLabel(
					new ImageIcon(
							ImageIO.read(getClass().getClassLoader().getResource("backgroudlogin.png")))));

		} catch (IOException e) {
			System.out.println("imagem não existe");
		}

		setResizable(false);
		pack();
		setVisible(false);

		usuarioLabel = new JLabel("Usuário:");
		usuarioLabel.setLocation(170, 270);
		usuarioLabel.setSize(250, 20);

		senha = new JLabel("Senha:");
		senha.setLocation(170, 320);
		senha.setSize(150, 20);

		textLogin = new JTextField("");
		textLogin.setSize(150, 30);
		textLogin.setLocation(120, 290);

		textSenha = new JPasswordField("");
		textSenha.setSize(150, 30);
		textSenha.setLocation(120, 350);
		textSenha.addKeyListener(this);

		cadastrar = new JButton("Cadastrar");
		cadastrar.setSize(80, 40);
		cadastrar.setLocation(150, 410);
		cadastrar.addActionListener(this);
		cadastrar.addKeyListener(this);
		cadastrar.setBackground(new Color(0, 0, 255));
		cadastrar.setForeground(Color.WHITE);
		cadastrar.setBorder(BorderFactory.createLineBorder(new Color(255, 20,
				147)));

		getContentPane().setLayout(null);
		getContentPane().add(usuarioLabel);
		getContentPane().add(senha);
		getContentPane().add(cadastrar);
		getContentPane().add(textLogin);
		getContentPane().add(textSenha);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

			login = textLogin.getText();
			senhaDigitada = textSenha.getText();

			MessageDigest algorithm = null;
			try {
				algorithm = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
			byte messageDigest[] = null;
			try {
				messageDigest = algorithm.digest(senhaDigitada
						.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			senhaCriptografada = hexString.toString();
			System.out.print(senhaCriptografada);

			setLogin(login);
			setSenhaCriptografada(senhaCriptografada);

			if (bancoDados.Select(getLogin()) == true) {
				bancoDados.Insert(getLogin(), getSenhaCriptografada());
				loginClasse.setVisible(true);
				dispose();
			}

			else {
				JOptionPane.showMessageDialog(null,
						"Esse usuario já está cadastrado");
				loginClasse.setVisible(true);
				dispose();
			}
		}

	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(cadastrar)) {

			login = textLogin.getText();
			senhaDigitada = textSenha.getText();

			MessageDigest algorithm = null;
			try {
				algorithm = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
			byte messageDigest[] = null;
			try {
				messageDigest = algorithm.digest(senhaDigitada
						.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			senhaCriptografada = hexString.toString();
			System.out.print(senhaCriptografada);

			setLogin(login);
			setSenhaCriptografada(senhaCriptografada);

			if (bancoDados.Select(getLogin()) == true) {
				bancoDados.Insert(getLogin(), getSenhaCriptografada());
			}

			else {
				JOptionPane.showMessageDialog(null,
						"Esse usuario já está cadastrado");
			}
		}
	}

	public String getSenhaCriptografada() {
		return senhaCriptografada;
	}

	public void setSenhaCriptografada(String senhaCriptografada) {
		this.senhaCriptografada = senhaCriptografada;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
