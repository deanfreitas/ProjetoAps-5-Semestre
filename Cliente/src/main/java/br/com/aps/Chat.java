package br.com.aps;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.apsusuarios.Mensagem;
import br.com.apsusuarios.Usuario;

public class Chat extends JFrame implements ActionListener, KeyListener,
		Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6367417585951603198L;
	private Usuario usuario;
	private Usuario ipUsuario;
	private Usuario senhaUsuario;
	private static final String newline = "\n";
	private JButton enviar;
	private JButton arquivo;
	private JButton receber;
	private JTextField texto;
	private JScrollPane tela;
	private static JTextArea informacao;
	private JFileChooser abrirArquivo;
	private static File file;
	private JLabel contato;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private static Socket serverSocket;
	private static Usuario dadosUsuario;
	private String mensagemDigitada;
	private Chat chat;
	private FileInputStream in;
	private OutputStream out;

	public Chat(Usuario usuario) throws UnknownHostException, IOException {
		setUsuario(usuario);

		setUsuario(usuario);
		setTitle("Quick.Text");
		setSize(800, 600);
		setLocation(300, 70);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("fundo.png"))));

		setResizable(false);
		pack();
		setVisible(false);

		texto = new JTextField("");
		texto.setSize(550, 150);
		texto.setLocation(50, 400);
		texto.setFont(new Font("Terminal", Font.PLAIN, 18));
		texto.addKeyListener(this);
		texto.setBorder(BorderFactory.createLineBorder(new Color(0, 20, 147)));

		informacao = new JTextArea();
		informacao.setEnabled(false);
		informacao
				.setText("Digite um texto para enviar mensagem, /desconectar para sair:");

		tela = new JScrollPane(informacao);
		tela.setSize(700, 290);
		tela.setLocation(50, 70);
		tela.setFont(new Font("Terminal", Font.PLAIN, 18));
		tela.setBackground(Color.white);
		tela.setBorder(BorderFactory.createLineBorder(new Color(0, 20, 147)));
		tela.setEnabled(false);
		tela.setForeground(Color.RED);

		contato = new JLabel("Contato");
		contato.setSize(100, 50);
		contato.setLocation(50, 10);
		contato.setFont(new Font("Terminal", Font.PLAIN, 25));

		enviar = new JButton("Enviar");
		enviar.setSize(100, 150);
		enviar.setLocation(630, 400);
		enviar.addActionListener(this);
		enviar.setBackground(new Color(0, 255, 255));
		enviar.setFont(new Font("Terminal", Font.PLAIN, 18));
		enviar.setForeground(Color.black);
		enviar.setBorder(BorderFactory.createLineBorder(new Color(0, 20, 147)));

		arquivo = new JButton("Enviar arquivo...");
		arquivo.setSize(100, 20);
		arquivo.setLocation(500, 370);
		arquivo.addActionListener(this);
		arquivo.setBackground(new Color(255, 105, 180));
		arquivo.setFont(new Font("Terminal", Font.PLAIN, 12));
		arquivo.setForeground(Color.black);
		arquivo.setBorder(BorderFactory.createLineBorder(new Color(0, 20, 147)));

		receber = new JButton("Receber arquivo...");
		receber.setSize(100, 20);
		receber.setLocation(50, 370);
		receber.addActionListener(this);
		receber.setBackground(new Color(255, 105, 180));
		receber.setFont(new Font("Terminal", Font.PLAIN, 12));
		receber.setForeground(Color.black);
		receber.setBorder(BorderFactory.createLineBorder(new Color(0, 20, 147)));

		abrirArquivo = new JFileChooser();

		getContentPane().setLayout(null);
		getContentPane().add(texto);
		getContentPane().add(enviar);
		getContentPane().add(receber);
		getContentPane().add(tela);
		getContentPane().add(arquivo);
		getContentPane().add(contato);

		serverSocket = new Socket("127.0.0.1", 4545);
		oos = new ObjectOutputStream(serverSocket.getOutputStream());
		ois = new ObjectInputStream(serverSocket.getInputStream());
		JOptionPane.showMessageDialog(null, usuario.getLogin());
		dadosUsuario = getUsuario();
		oos.writeObject(dadosUsuario);
		oos.reset();
	}

	public void actionPerformed(ActionEvent e)

	{
		if (e.getSource().equals(enviar)) {
			try {
				enviar();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if (e.getSource().equals(receber)) {
			ReceberArquivo();
		}

		if (e.getSource().equals(arquivo)) {
			{
				int returnVal = abrirArquivo.showOpenDialog(this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = abrirArquivo.getSelectedFile();
					informacao.setText("Abrindo: " + file.getName() + "."
							+ newline);
					System.out.println(file.getPath());
					System.out.println();
					System.out.println("Abrindo: " + file.getName() + "."
							+ newline);
				} else {
					informacao.setText("Comando cancelado" + newline);
				}
				informacao.setCaretPosition(informacao.getDocument()
						.getLength());

				try {
					EnviarArquivo();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			try {
				enviar();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}

	public void enviar() throws IOException {

		String mensagemEscrita = texto.getText();

		try {
			if (!mensagemEscrita.equals("/desconectar")) {

				Mensagem clientMsg = new Mensagem();
				clientMsg.setDadosUsuarioLogin(dadosUsuario);
				clientMsg.setMensagem(mensagemEscrita);
				texto.setText("");
				oos.writeObject(clientMsg);
				oos.flush();
				oos.reset();
			}

			else {
				chat.setVisible(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1000);
		}
	}

	public void EnviarArquivo() throws UnknownHostException, IOException {

		String ipServidorArquivo = JOptionPane
				.showInputDialog("Escreva o IP do Servidor que vai receber o arquivo");
		in = new FileInputStream(file);

		Socket socket = new Socket(ipServidorArquivo, 5678);
		out = socket.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(out);
		BufferedWriter writer = new BufferedWriter(osw);
		writer.write(file.getName() + "\n");
		writer.flush();
		int tamanho = 4096;
		byte[] buffer = new byte[tamanho];
		int lidos = -1;
		while ((lidos = in.read(buffer, 0, tamanho)) != -1) {
			out.write(buffer, 0, lidos);
		}

		socket.close();

	}

	public void ReceberArquivo() {

		try {

			ServerSocket server = new ServerSocket(5678);
			Socket clSocket = server.accept();
			InputStream in = clSocket.getInputStream();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader reader = new BufferedReader(isr);
			String fName = reader.readLine();
			System.out.println(fName);
			String userDir = System.getenv("USERPROFILE");
			File f1 = new File(userDir + "/Desktop/" + fName);
			System.out.println();
			System.out.println(f1);
			FileOutputStream out = new FileOutputStream(f1);

			int tamanho = 4096; // buffer de 4KB
			byte[] buffer = new byte[tamanho];
			int lidos = -1;
			while ((lidos = in.read(buffer, 0, tamanho)) != -1) {
				System.out.println(lidos);
				out.write(buffer, 0, lidos);
			}
			out.flush();
			server.close();
			out.close();

		} catch (IOException e) {

		}

	}

	public void Pessoas() {

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getIpUsuario() {
		return ipUsuario;
	}

	public void setIpUsuario(Usuario ipUsuario) {
		this.ipUsuario = ipUsuario;
	}

	public Usuario getSenha() {
		return senhaUsuario;
	}

	public void setSenha(Usuario senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}

	public void run() {

		Mensagem msgServidor;

		try {
			while (true) {
				msgServidor = (Mensagem) ois.readObject();
				mensagemDigitada = (msgServidor.getDadosUsuarioLogin()
						.getLogin() + ": " + msgServidor.getMensagem());
				informacao.setText(informacao.getText() + "\n"
						+ mensagemDigitada);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
