package main;

import java.awt.event.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Connect2 extends JFrame implements ActionListener {

	private Chat2 chatIRC;

	JLabel labelIP = new JLabel("Server IP : ");
	static JTextField textfieldIP = new JTextField(10);

	JLabel labelPseudo = new JLabel("Pseudo : ");
	JTextField textfieldPseudo = new JTextField(10);

	JButton buttonConnect = new JButton("Se connecter");

	JFrame view = new JFrame("SuperChat");

	public Connect2() {

		this.setLayout(null);
		this.setResizable(false);
		this.getContentPane().setBackground(Color.RED);

		this.add(labelIP);
		this.add(textfieldIP);

		this.add(labelPseudo);
		this.add(textfieldPseudo);

		this.add(buttonConnect);

		labelIP.setBounds(20, 40, 180, 30);
		labelIP.setForeground(Color.BLUE);

		textfieldIP.setBounds(210, 40, 160, 30);

		labelPseudo.setBounds(20, 90, 180, 30);
		labelPseudo.setForeground(Color.RED);

		textfieldPseudo.setBounds(210, 90, 160, 30);

		buttonConnect.setForeground(Color.black);
		buttonConnect.setBackground(Color.blue);
		buttonConnect.setPreferredSize(new Dimension(150,40));

		buttonConnect.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					Connexion();
			}

			public void keyReleased(KeyEvent e) {
			}
		});

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buttonConnect.addActionListener(this);
		this.setSize(600, 150);
		this.setVisible(true);
		this.setLocationRelativeTo(null);

	}

	public void Connexion() {

		if (!textfieldIP.getText().equals("") && !textfieldPseudo.getText().equals("")) {

			boolean b;
			b = Pattern.matches(
					"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)",
					textfieldIP.getText());

			if (b == true) {
				Client.Connexion();
				if (Client.getResultConnexion() != false) {
					this.dispose();
					view = new Chat2(textfieldIP.getText(), textfieldPseudo.getText());
					view.setSize(1400, 800);
					view.setVisible(true);
					view.setLocationRelativeTo(null);
					Chat2.zoneDeTexte.setText("");
				} else {
					JOptionPane.showMessageDialog(this,
							"Erreur de connexion !");
				}
			} else {
				JOptionPane.showMessageDialog(this, "IP Invalide !");
			}
		}
	}

	public static String getIP() {
		return textfieldIP.getText();
	}

	public void actionPerformed(ActionEvent arg0) {
		Connexion();
	}

}