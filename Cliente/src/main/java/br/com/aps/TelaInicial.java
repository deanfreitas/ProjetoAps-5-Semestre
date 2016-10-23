package br.com.aps;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TelaInicial extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8909339615246878496L;
	private JButton contatos;
	private JButton contato1;
	private JButton contato2;
	private JButton contato3;
	private JButton contato4;
	private JButton sair;
	private JComboBox<String> status;

	public TelaInicial() {

		setTitle("Quick.Text");
		setSize(556, 700);
		setLocation(400, 40);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {

			setContentPane(new JLabel(
					new ImageIcon(
							ImageIO.read(getClass().getClassLoader().getResource("telainicial.png")))));

		} catch (IOException e) {
			System.out.println("imagem não existe");
		}

		setResizable(false);
		pack();
		setVisible(false);

		contatos = new JButton("Contatos:");
		contatos.setLocation(40, 250);
		contatos.setSize(400, 20);
		contatos.setFont(new Font("Terminal", Font.PLAIN, 18));
		contatos.setForeground(Color.white);
		contatos.setBackground(new Color(30, 144, 255));

		status = new JComboBox<String>();
		status.setBackground(new Color(30, 144, 255));
		status.setSize(120, 20);
		status.setLocation(300, 140);
		status.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		status.addItem("Disponível");
		status.addItem("Ocupado");
		status.addItem("Em Reunião");
		status.setForeground(Color.white);

		contato1 = new JButton("Contato 1:");
		contato1.setLocation(40, 300);
		contato1.setSize(400, 20);
		contato1.setFont(new Font("Terminal", Font.PLAIN, 16));
		contato1.addActionListener(this);
		contato1.setForeground(Color.black);
		contato1.setBackground(Color.white);

		contato2 = new JButton("Contato 2:");
		contato2.setLocation(40, 330);
		contato2.setSize(400, 20);
		contato2.setFont(new Font("Terminal", Font.PLAIN, 16));
		contato2.addActionListener(this);
		contato2.setForeground(Color.black);
		contato2.setBackground(Color.white);

		contato3 = new JButton("Contato 3:");
		contato3.setLocation(40, 360);
		contato3.setSize(400, 20);
		contato3.setFont(new Font("Terminal", Font.PLAIN, 16));
		contato3.addActionListener(this);
		contato3.setForeground(Color.black);
		contato3.setBackground(Color.white);

		contato4 = new JButton("Contato 4:");
		contato4.setLocation(40, 390);
		contato4.setSize(400, 20);
		contato4.addActionListener(this);
		contato4.setFont(new Font("Terminal", Font.PLAIN, 16));
		contato4.setForeground(Color.black);
		contato4.setBackground(Color.white);

		sair = new JButton("Sair");
		sair.setLocation(350, 610);
		sair.setSize(70, 20);
		sair.setFont(new Font("Terminal", Font.PLAIN, 16));
		sair.addActionListener(this);
		sair.setForeground(Color.black);
		sair.setBackground(new Color(255, 20, 147));
		sair.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		getContentPane().setLayout(null);
		getContentPane().add(contatos);
		getContentPane().add(status);
		getContentPane().add(contato1);
		getContentPane().add(contato2);
		getContentPane().add(contato3);
		getContentPane().add(contato4);
		getContentPane().add(sair);

	}

	public void actionPerformed(ActionEvent e) // essa é a parte para alterar //
												// vc tem que linkar essa página
												// com a tela de contatos
	{
		if (e.getSource().equals(contato1)) {

		}
	}

	public static void main(String[] args) {

		new TelaInicial().setVisible(true);
	}
}
