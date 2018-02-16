package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
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
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

import model.beans.Message;
import model.dao.DAOFactory;
import model.dao.DAOMessage;
import model.dao.exceptions.DAOException;
import model.dao.utils.DAOUtils;

public class Chat extends JFrame implements ActionListener{
    private static final Logger LOG = Logger.getLogger(Main.class.getName());
    private Chat view;

    public static JTextField messageBox = new JTextField("Bonjour, quelle est votre humeur ?");
    private JButton sendMessage = new JButton("Envoyer");
    private JButton btDeconnexion = new JButton("Déconnexion");
    public static JTextPane chatBox = new JTextPane();
    private JScrollPane scrollPane = new JScrollPane(chatBox, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    private JLabel lIP = new JLabel("Adresse IP : ");
    private JLabel lIPChoisie = new JLabel();
    private JLabel lPseudo = new JLabel("Pseudo : ");
    public static JLabel lPseudoChoisi = new JLabel();

    private String msg = new String();

	public Chat(String IP, String Pseudo){

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.setLayout(null);
        this.setResizable(true);
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        this.addWindowListener(new FrameListener());

        this.setTitle("SuperChat");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        this.add(messageBox);
        this.add(sendMessage);
        this.add(btDeconnexion);
        this.add(scrollPane);
        this.add(lIP);
        this.add(lPseudo);
        this.add(lIPChoisie);
        this.add(lPseudoChoisi);

        messageBox.requestFocusInWindow();
        messageBox.setPreferredSize(new Dimension(800,400));
        messageBox.setBackground(Color.YELLOW);
        messageBox.setForeground(Color.BLUE);
        messageBox.setEnabled(false);
        messageBox.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // System.out.println(tbMessage.getText());
                String SaisirMessage = "Saisir un Message ...";
                String textFieldValue = messageBox.getText().toString();

                if (messageBox.isEnabled()) {
                    if (textFieldValue.equals(SaisirMessage)) {
                        messageBox.setText("");
                        sendMessage.setEnabled(true);
                    } else {
                        sendMessage.setEnabled(true);
                    }

                } else {
                    sendMessage.setEnabled(false);
                }
            }
        });

        sendMessage.setBackground(Color.RED);
        sendMessage.setForeground(Color.WHITE);
        sendMessage.setBounds(1150, 650, 150, 50);
        sendMessage.addActionListener(this);
        sendMessage.setEnabled(false);
        sendMessage.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    EnvoyerMessage();
                sendMessage.setEnabled(false);
            }

            public void keyReleased(KeyEvent e) {
            }
        });

        chatBox.setBounds(450, 80, 850, 550);
        chatBox.setEditable(false);
        chatBox.setForeground(Color.WHITE);
        chatBox.setBackground(Color.DARK_GRAY);
        scrollPane.setBounds(new Rectangle(450, 80, 850, 550));

        lIP.setBounds(450, 30, 120, 50);
        lIP.setForeground(Color.BLUE);
        lIPChoisie.setBounds(570, 30, 150, 50);
        lIPChoisie.setText(IP);
        lPseudo.setBounds(800, 30, 80, 50);
        lPseudo.setForeground(Color.RED);
        lPseudoChoisi.setBounds(880, 30, 150, 50);
        lPseudoChoisi.setText(Pseudo);
        this.setVisible(true);

    }
	
	/**
	 * Action listener sur boutton envoyer message
	 *
	 */
public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        StyledDocument doc = chatBox.getStyledDocument();

        Style style = chatBox.addStyle("I'm a Style", null);

        if (source == sendMessage) {
            EnvoyerMessage();
            sendMessage.setEnabled(false);
        } else if (source == btDeconnexion) {
            Client.Deconnexion();
            this.dispose();
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
            
            chatBox.setText("");

//            c = null;
//            try {
//                c = DAOFactory.getConnection();
//                DAOMessage msgDAO;
//                msgDAO = DAOFactory.getDAOMessage(c);
//
//            } catch (DAOException e3) {
//                LOG.error("Error during DAO manipulation.", e3);
//            } finally {
//                DAOUtils.close(c);
//            }

        Client.EnvoiMessage(msg);

        messageBox.setText("Bonjour, quelle est votre humeur aujourd'hui ?");
    }

    class FrameListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            Client.Deconnexion();
        }
    }

}