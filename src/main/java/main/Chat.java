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
	
	/*
	 * Component of the Chat window
	 * 
	 * */
	private static final long serialVersionUID = 1L;
	private static JButton quitChan = new JButton("Déconnecter");
	private static JButton joinChan = new JButton("Joindre");
	private static JButton createChan = new JButton("Créer");
	private static JButton refreshChan = new JButton("Rafraichir");
	private JButton sendMessage = new JButton("Envoyer");
	private JPanel mainPanel = new JPanel();
	private JMenuBar menuBar = new JMenuBar();
	public static JTextArea chatBox = new JTextArea();
	public static JComboBox<String> cbCanaux = new JComboBox<String>();
	public static JTextField messageBox = new JTextField();

	
	public Chat(Client c){
		
		/*
		 * Settings of the Chat window 
		 * 
		 * */
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
        
        this.addWindowListener(new FrameListener());
        
        messageBox.requestFocusInWindow();
        messageBox.setBackground(Color.YELLOW);
        messageBox.setForeground(Color.BLUE);
        messageBox.setEditable(false);
        
        /*
         * set default settings to component
         * 
         * */
        quitChan.setEnabled(false);
        chatBox.setEditable(false);
        chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);
        
        
        /*
         * action listener on the send msg button and enter key
         * if true, send the msg with #MSG, popup with a message 
         * 
         * */
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
        
        /*
         * Adding a menu bar to the window
         * 
         * */
		menuBar.setPreferredSize(new Dimension(200,20));
		
		JMenu fichier = new JMenu("Menu");
		JMenuItem quitter = new JMenuItem("Quitter");
		JMenuItem exit = new JMenuItem("Se déconnecter");
		fichier.add(exit);
		fichier.addSeparator();
		fichier.add(quitter);
		menuBar.add(fichier);

		/*
		 * Action listener on the exit button, which quit a channel if user is in, and exit the serveur,
		 * close the socket and open a new Connect Window
		 *  
		 * */
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
		
		/*
		 * Totally close the program
		 * 
		 * */
		quitter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Client.Deconnexion();
				System.exit(0);
			}
		});
        
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridBagLayout());
		
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
        
        /*
         * Adding textfield and button to the window using gridbadconstraints
         * and adding this panel to the mainPanel
         * 
         * */
        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);
        mainPanel.add(BorderLayout.SOUTH, southPanel);
        
        /*
         * Adding component to the main panel and create pannel chan
         * 
         * */
        JPanel ChannelPan = new JPanel();
        ChannelPan.setPreferredSize(new Dimension(200,400));
        ChannelPan.setBackground(Color.BLUE);
		JLabel channel = new JLabel("Gestion Channel");	
		cbCanaux.setPreferredSize(new Dimension(150,40));
		cbCanaux.setEditable(false);
		channel.setForeground(Color.WHITE);
		
		/*
		 * action listener on refresh button, refresh the channels list
		 * 
		 * */
		refreshChan.setPreferredSize(new Dimension(150,30));
		refreshChan.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event) {
            		c.send("#CHANNELS");
            }
		});
		
		/*
		 * Action listener on the join button, who join the selected channel of the combobox
		 * 
		 * */
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
		
		/*
		 * action listener on the quit button, uses to quit a channel 
		 * 
		 * */
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
		
		/*
		 * action listener on the create channel button, create and join the channel 
		 * 
		 * */
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
            	if (!chanField.getText().isEmpty() && !containsWhiteSpace(chanField.getText())){
            	c.send("#JOIN " + chanField.getText());
            	c.send("#CHANNELS");
            	quitChan.setEnabled(true);
            	createChan.setEnabled(false);
            	messageBox.setText(null);
            	messageBox.setEditable(true);
            	joinChan.setEnabled(false);
            	} else {
            		JOptionPane.showMessageDialog(chatBox, "Champ vide ou contient des espaces !");
            	}
            }
		});
		
		/*
		 * Adding component to the chan panel
		 * 
		 * */
		ChannelPan.add(channel);
		ChannelPan.add(createChan);
		ChannelPan.add(cbCanaux);
		ChannelPan.add(joinChan);
		ChannelPan.add(quitChan);
		ChannelPan.add(refreshChan);
		
		/*
		 * Adding the chan panel on east of the mainPanel
		 * 
		 * */
		mainPanel.add(BorderLayout.EAST, ChannelPan);

    }
	
	/* display a msg sent by others in the chat box
	 * @param String msg
	 * 
	 * */
	public void displayMsg(String msg){
		if (messageBox.isEditable()){
			chatBox.append(msg);
		}
	}
	
	/* Add the channels list to the combo box
	 * @param String msg
	 * 
	 * */
	public void displayChannelsList(String msg){
		cbCanaux.addItem(msg);
	}
	
	/*
	 * count how many channel are in the list
	 * 
	 * */
	public void countChannelsList(){
		if (cbCanaux.getItemCount() == 1 && messageBox.isEditable()){
			joinChan.setEnabled(false);
		} else if (cbCanaux.getItemCount() == 0){
			joinChan.setEnabled(false);
		} else {
			joinChan.setEnabled(true);
		}
	}
	
	/* 
	 * empty the channels list to refresh it
	 * 
	 * */
	public void emptyChannelsList(){
		cbCanaux.removeAllItems();
	}
	
	/*
	 * set enabled or not the chan list if the is more than 0 chan
	 * 
	 * */
	public void setDarkChannelsList(){
		cbCanaux.setEnabled(false);
	}
	
	public void setOkChannelsList(){
		cbCanaux.setEnabled(true);
	}
	
	/*containsWhiteSpace
	 * test if there is space in the channels name on creation
	 * @param String testCode
	 * @return boolean
	 * 
	 * */
	public static boolean containsWhiteSpace(final String testCode){
	    if(testCode != null){
	        for(int i = 0; i < testCode.length(); i++){
	            if(Character.isWhitespace(testCode.charAt(i))){
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
	/*
	 * On close: close the socket, the outputstream, the bufferedwriter and the outputstreamwriter
	 * 
	 * */
	class FrameListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			Client.Deconnexion();
		}
	}

}