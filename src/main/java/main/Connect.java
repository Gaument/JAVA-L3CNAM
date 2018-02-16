package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Connect extends JFrame implements ActionListener{
	
	private Chat2 chatIRC;

	JLabel serverLabel = new JLabel(" IP : ");

	static JTextField serverField = new JTextField(20);

	JLabel pseudoLabel = new JLabel("Pseudo : ");
	JTextField pseudoField = new JTextField(20);

	JButton okButton = new JButton("Se connecter");

	JFrame view = new JFrame("SuperChat");
	private String pseudoUser;
	public Connect(){
		super("Connexion au Superchat");
		Container contents = getContentPane();
		
		contents.add(getConnectionChannel(), BorderLayout.NORTH);
		contents.add(getGifPanel(), BorderLayout.CENTER);
		pack(); setVisible(true);
	}
	/**
	 * Block de connection
	 * @return le main pannel
	 */
	public JPanel getConnectionChannel() {
		this.add(serverLabel);
		this.add(serverField);

		this.add(pseudoLabel);
		this.add(pseudoField);

		this.add(okButton);
		
		JPanel mainPanel = new JPanel();
		JPanel southPanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		mainPanel.setPreferredSize(new Dimension(600,150));
		mainPanel.setBackground(Color.RED);
		
		GridBagConstraints left = new GridBagConstraints();

        GridBagConstraints right = new GridBagConstraints();
		
		pseudoField.setPreferredSize(new Dimension(170,50));
		serverField.setPreferredSize(new Dimension(170,50));
		southPanel.add(pseudoLabel,right);
		southPanel.add(pseudoField, right);
		southPanel.add(serverLabel, right);
		southPanel.add(serverField, right);
		southPanel.setBackground(Color.yellow);
		mainPanel.add(BorderLayout.SOUTH, southPanel);
		JButton okButton = new JButton("OK");
		okButton.setBackground(Color.blue);
		okButton.setForeground(Color.black);
		okButton.setPreferredSize(new Dimension(150,40));
		okButton.addActionListener(this);
		buttonPanel.add(okButton,left);
		mainPanel.add(BorderLayout.SOUTH, buttonPanel);
		
		okButton.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					Connexion();
			}

			public void keyReleased(KeyEvent e) {
			}
		});   
		return mainPanel;

	}
	/**
	 * Récuperer le GIF
	 * @return imgPanel
	 */
	public JPanel getGifPanel(){
		JPanel imgPanel = new JPanel();
		ImageIcon icon = new ImageIcon("img/superman.gif");
		JLabel img = new JLabel(icon);
		imgPanel.add(img);
		imgPanel.setBackground(Color.RED);
		return imgPanel;
	}
	/**
	 * Getter et Setter du pseudo de l'user
	 * @return pseudoUser
	 */
	public String getUserPseudo() {
		return pseudoUser;
	}

	public void setUserPseudo(String pseudoUser) {
		this.pseudoUser = pseudoUser;
	}

	public void Connexion() {

		if (!serverField.getText().equals("") && !pseudoField.getText().equals("")) {

			boolean b;
			b = Pattern.matches(
					"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)",
					serverField.getText());

			if (b == true) {
				Client.Connexion();
				if (Client.getResultConnexion() != false) {
					this.dispose();
					view = new Chat3(serverField.getText(), pseudoField.getText());
					view.setSize(700, 400);
					view.setResizable(true);
					view.setVisible(true);
					view.setLocationRelativeTo(null);
					Chat2.zoneDeTexte.setText("");
				} else {
					JOptionPane.showMessageDialog(this,
							"Problème de connexion !");
				}
			} else {
				JOptionPane.showMessageDialog(this, "IP incorrect !");
			}
		}
	}

	public static String getIP() {
		return serverField.getText();
	}

	public void actionPerformed(ActionEvent arg0) {
		Connexion();
	}
	
}