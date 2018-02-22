package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chat extends JFrame {
	
	//Composant de la fenêtre de chat
	private static final long serialVersionUID = 1L;
	private JButton sendMessage = new JButton("Envoyer");
	public static JTextArea chatBox = new JTextArea();
	public static JComboBox<String> cbCanaux = new JComboBox<String>();
	private JPanel mainPanel = new JPanel();
	private JMenuBar menuBar = new JMenuBar();
	public static JTextField messageBox = new JTextField();
	private static JButton quitChan = new JButton("Déconnecter");
	private static JButton joinChan = new JButton("Joindre");
	private static JButton createChan = new JButton("Créer");
	private static JButton refreshChan = new JButton("Rafraichir");
	
	public Chat(Client c){
		//Paramêtre et contenu de ma fenetre de chat
		super("SuperChat IRC");
		
		Container contents = getContentPane();
		
		contents.add(mainPanel, BorderLayout.CENTER);
		contents.add(menuBar, BorderLayout.NORTH);
		
		pack(); setVisible(true);
		setSize(700, 400);
		setResizable(true);
		setVisible(true);
		setLocationRelativeTo(null);
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridBagLayout());
        
        this.addWindowListener(new FrameListener());
        
        messageBox.requestFocusInWindow();
        messageBox.setBackground(Color.YELLOW);
        messageBox.setForeground(Color.BLUE);
        messageBox.setEditable(false);
        quitChan.setEnabled(false);
        
        //listener sur le bouton envoyer msg
        sendMessage.setBackground(Color.RED);
        sendMessage.setForeground(Color.WHITE);
        sendMessage.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event) {
                	if (!messageBox.getText().isEmpty()){
                		c.send("#MSG " + messageBox.getText());
                		messageBox.setText(null);
                    } else {
            			JOptionPane.showMessageDialog(chatBox, "Veuillez saisir un message !");
                    }
                }
        });
        sendMessage.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				}
			public void keyPressed(KeyEvent e) {
            	if (!messageBox.getText().isEmpty()){
            		if (e.getKeyCode() == KeyEvent.VK_ENTER){
            			c.send("#MSG " + messageBox.getText());
            			messageBox.setText("");
						}
                	} else {
                		JOptionPane.showMessageDialog(chatBox, "Veuillez saisir un message !");
                	}
				}
			public void keyReleased(KeyEvent e) {
				}
		});


        chatBox.setEditable(false);
        chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);
        
        //ajout des composants de la barre de menu en haut de la fenêtre
		menuBar.setPreferredSize(new Dimension(200,20));
		
		JMenu fichier = new JMenu("Menu");
		JMenuItem quitter = new JMenuItem("Quitter");
		JMenuItem exit = new JMenuItem("Se déconnecter");
		fichier.add(exit);
		fichier.addSeparator();
		fichier.add(quitter);
		menuBar.add(fichier);

		//se déconnecter du serveur, renvoi sur la fenêtre de connexion Connect();
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
            	if (messageBox.isEditable()){
            		c.send("#QUIT");
            	}
				c.send("#EXIT");
				Client.Deconnexion();
				dispose();
				new Connect();
			}
		});
		//fermer totalement le programme
		quitter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Client.Deconnexion();
				System.exit(0);

			}
		});
        
        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;
        
        //ajout des composant de panel situé en bas de la fenêtre, entre-autre le bouton envoyé et la zone de texte
        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);
        
        mainPanel.add(BorderLayout.SOUTH, southPanel);
        JPanel ChannelPan = new JPanel();
        ChannelPan.setPreferredSize(new Dimension(200,400));
        ChannelPan.setBackground(Color.BLUE);
		JLabel channel = new JLabel("Gestion Channel");	
		cbCanaux.setPreferredSize(new Dimension(150,40));
		cbCanaux.setEditable(false);
		channel.setForeground(Color.WHITE);
		
		refreshChan.setPreferredSize(new Dimension(150,30));
		refreshChan.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event) {
            		c.send("#CHANNELS");
            }
		});
		
		//Bouton pour joindre un channel selectionné dans la liste des channels de la combobox
		joinChan.setPreferredSize(new Dimension(150,30));
		joinChan.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event) {
            	if (messageBox.isEditable()){
            		c.send("#QUIT");
            	}
            		String leChan = cbCanaux.getSelectedItem().toString();
            		cbCanaux.requestFocus();
            		c.send("#JOIN " + leChan);
            		c.send("#CHANNELS");
            		messageBox.setEditable(true);
            		createChan.setEnabled(false);
            		joinChan.setEnabled(false);
            		quitChan.setEnabled(true);
            }
		});
		//bouton permetant de quitter le channel où on se trouve
		quitChan.setPreferredSize(new Dimension(150,30));
		quitChan.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event) {
	        		c.send("#QUIT");
	        		c.send("#CHANNELS");
	        		quitChan.setEnabled(false);
	        		createChan.setEnabled(true);
            		chatBox.setText(null);
            		messageBox.setText(null);
            		messageBox.setEditable(false);
            }
		});
		//bouton permetant de créer un channel
		createChan.setPreferredSize(new Dimension(150,30));
		createChan.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event) {
            	JOptionPane jop;
            	jop = new JOptionPane();
            	JPanel pan = new JPanel();
            	JTextField chanField = new JTextField();
            	JLabel chanLabel = new JLabel("Nom du channel :");
            	chanField.setPreferredSize(new Dimension(120,30));
            	pan.add(chanLabel);
            	pan.add(chanField);
            	
            	jop.showMessageDialog(null, pan, "Créer un channel", JOptionPane.INFORMATION_MESSAGE, null);
            	if (!chanField.getText().isEmpty()){
            	c.send("#JOIN " + chanField.getText());
            	c.send("#CHANNELS");
            	quitChan.setEnabled(true);
            	createChan.setEnabled(false);
            	messageBox.setText(null);
            	messageBox.setEditable(true);
            	joinChan.setEnabled(false);
            	} else {
            		JOptionPane.showMessageDialog(chatBox, "Champ Vide");
            	}
            }
		});
		//ajout des composants du panel de channels
		ChannelPan.add(channel);
		ChannelPan.add(createChan);
		ChannelPan.add(cbCanaux);
		ChannelPan.add(joinChan);
		ChannelPan.add(quitChan);
		ChannelPan.add(refreshChan);
		
		mainPanel.add(BorderLayout.EAST, ChannelPan);

    }
	
	//afficher un msg dans la fenetre de chat
	public void displayMsg(String msg){
		if (messageBox.isEditable()){
			chatBox.append(msg);
		}
	}
	
	//afficher la liste des channels dans la combobox
	public void displayChannelsList(String msg){
		cbCanaux.addItem(msg);
	}
	
	public void countChannelsList(){
		if (cbCanaux.getItemCount() == 0){
			joinChan.setEnabled(false);
		} else {
			joinChan.setEnabled(true);
		}
	}
	
	//vider le contenu de la liste afin de rafraichir celle-ci
	public void emptyChannelsList(){
		cbCanaux.removeAllItems();
	}
	
	public void setDarkChannelsList(){
		cbCanaux.setEnabled(false);
	}
	
	public void setOkChannelsList(){
		cbCanaux.setEnabled(true);
	}
	
	//à la fermeture de la fenetre ça appel Deconnexion afin de fermer la socket
	class FrameListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			Client.Deconnexion();
		}
	}

}