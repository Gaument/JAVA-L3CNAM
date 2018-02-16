package main;

import model.beans.Message;
import model.dao.DAOFactory;
import model.dao.DAOMessage;
import model.dao.exceptions.DAOException;
import model.dao.utils.DAOUtils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.sql.Connection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.log4j.Logger;

public class Chat2 extends JFrame implements ActionListener {// , KeyListener {


	private static final Logger LOG = Logger.getLogger(Main.class.getName());
	private Chat2 view;

	public static JComboBox<String> cbCanaux = new JComboBox<String>();
	public static JTextField tbMessage = new JTextField("Saisir un Message ...");
	private JButton btEnvoyer = new JButton("Envoyer");
	private JButton btChoixCanal = new JButton("Choix Canal");
	private JButton btDeconnexion = new JButton("Déconnexion");
	public static JTextPane zoneDeTexte = new JTextPane();
	private JScrollPane scrollPane = new JScrollPane(zoneDeTexte, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	private JLabel lCanaux = new JLabel("Liste des Canaux : ");
	private JLabel lIP = new JLabel("Adresse IP : ");
	private JLabel lIPChoisie = new JLabel();
	private JLabel lPseudo = new JLabel("Pseudo : ");
	public static JLabel lPseudoChoisi = new JLabel();
	private JLabel lCanal = new JLabel("Canal : ");
	private JLabel lCanalChoisi = new JLabel();

	private String msg = new String();
	public static int canalChoisi;

	public Chat2(String IP, String Pseudo) {

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


		this.setLayout(null);
		this.setResizable(true);
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
		this.addWindowListener(new FrameListener());

		this.setTitle("SuperChat");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);

		// ajout des boutons à la fen�tre
		this.add(cbCanaux);
		this.add(tbMessage);
		this.add(btEnvoyer);
		this.add(btChoixCanal);
		this.add(btDeconnexion);
		this.add(scrollPane);
		this.add(lCanaux);
		this.add(lIP);
		this.add(lPseudo);
		this.add(lIPChoisie);
		this.add(lPseudoChoisi);
		this.add(lCanal);
		this.add(lCanalChoisi);


		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				btEnvoyer.setEnabled(false);
				tbMessage.setText("Saisir un Message ...");
			}
		});

		tbMessage.setBounds(450, 650, 650, 50);
		tbMessage.setEnabled(false);
		tbMessage.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// System.out.println(tbMessage.getText());
				String SaisirMessage = "Saisir un Message ...";
				String textFieldValue = tbMessage.getText().toString();

				if (tbMessage.isEnabled()) {
					if (textFieldValue.equals(SaisirMessage)) {
						tbMessage.setText("");
						btEnvoyer.setEnabled(true);
					} else {
						btEnvoyer.setEnabled(true);
					}

				} else {
					btEnvoyer.setEnabled(false);
				}
			}
		});

		btEnvoyer.setBounds(1150, 650, 150, 50);
		btEnvoyer.addActionListener(this);
		btEnvoyer.setEnabled(false);
		btEnvoyer.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					EnvoyerMessage();
				btEnvoyer.setEnabled(false);
			}

			public void keyReleased(KeyEvent e) {
			}
		});

		btChoixCanal.setEnabled(false);
		btChoixCanal.setBounds(50, 650, 150, 50);
		btChoixCanal.setForeground(new Color(23, 151, 37));
		btChoixCanal.addActionListener(this);

		btDeconnexion.setBounds(220, 650, 150, 50);
		btDeconnexion.setForeground(Color.RED);
		btDeconnexion.addActionListener(this);
		btDeconnexion.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				btEnvoyer.setEnabled(false);
				tbMessage.setText("Saisir un Message ...");
			}
		});

		zoneDeTexte.setBounds(450, 80, 850, 550);
		zoneDeTexte.setForeground(Color.WHITE);
		zoneDeTexte.setBackground(Color.DARK_GRAY);
		scrollPane.setBounds(new Rectangle(450, 80, 850, 550));
		zoneDeTexte.setEditable(false);

		lIP.setBounds(450, 30, 120, 50);
		lIP.setForeground(Color.BLUE);
		lIPChoisie.setBounds(570, 30, 150, 50);
		lIPChoisie.setText(IP);
		lPseudo.setBounds(800, 30, 80, 50);
		lPseudo.setForeground(Color.RED);
		lPseudoChoisi.setBounds(880, 30, 150, 50);
		lPseudoChoisi.setText(Pseudo);
		lCanal.setBounds(1090, 30, 80, 50);
		lCanal.setForeground(new Color(23, 151, 37));
		lCanalChoisi.setBounds(1160, 30, 100, 50);

		// dimensionnement en affichage de la fen�tre
		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		StyledDocument doc = zoneDeTexte.getStyledDocument();

		Style style = zoneDeTexte.addStyle("I'm a Style", null);

		if (source == btEnvoyer) {
			EnvoyerMessage();
			btEnvoyer.setEnabled(false);
		} else if (source == btDeconnexion) {
			Client.Deconnexion();
			cbCanaux.removeAllItems();
			this.dispose();
			Test fen = new Test();
			fen.setVisible(true);
		} else if (source == btChoixCanal) {
			lCanalChoisi.setText(cbCanaux.getSelectedItem().toString());

			btEnvoyer.setEnabled(true);
			tbMessage.setEnabled(true);
		}  

	}

	// Récupérer le nom du PC Hôte
	public static String getComputerFullName() {
		String hostName = null;
		try {
			final InetAddress addr = InetAddress.getLocalHost();
			hostName = new String(addr.getHostName());
		} catch (final Exception e) {
		} // end try
		return hostName;
	}

	public void EnvoyerMessage() {

		final Message m = new Message();

			Connection c = null;

//			try {
//				c = DAOFactory.getConnection();
//				DAOCanaux cnDAO;
//				cnDAO = DAOFactory.getDAOCanaux(c);
//				cnDAO.find_Canal_Choisi(cbCanaux.getSelectedItem().toString());
//			} catch (DAOException e1) {
//				LOG.error("Error during DAO manipulation.", e1);
//			} finally {
//				DAOUtils.close(c);
//			}
//
//			c = null;
//			try {
//				c = DAOFactory.getConnection();
//				DAOMessage msgDAO;
//				msgDAO = DAOFactory.getDAOMessage(c);
//				msgDAO.create(m);
//			} catch (DAOException e2) {
//				LOG.error("Error during DAO manipulation.", e2);
//			} finally {
//				DAOUtils.close(c);
//			}

			zoneDeTexte.setText("");

//			c = null;
//			try {
//				c = DAOFactory.getConnection();
//				DAOMessage msgDAO;
//				msgDAO = DAOFactory.getDAOMessage(c);
//				msgDAO.find(canalChoisi);
//
//			} catch (DAOException e3) {
//				LOG.error("Error during DAO manipulation.", e3);
//			} finally {
//				DAOUtils.close(c);
//			}
//
//			lCanalChoisi.setText(cbCanaux.getSelectedItem().toString());
//			if (lCanalChoisi.getText() != "") {
//				tbMessage.setEnabled(true);
//			}

		

		Client.EnvoiMessage(msg);

		tbMessage.setText("Saisir un Message ...");
	}

	class FrameListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			Client.Deconnexion();
		}
	}

}