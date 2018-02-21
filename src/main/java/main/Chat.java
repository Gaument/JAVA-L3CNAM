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
	
	public Chat(Client c){
		
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
        mainPanel.setPreferredSize(new Dimension(1000,400));

        JPanel southPanel = new JPanel();
        // tester si c'est utile GridBagLatout
        southPanel.setLayout(new GridBagLayout());
        
        this.addWindowListener(new FrameListener());
        
        messageBox.requestFocusInWindow();
        messageBox.setBackground(Color.YELLOW);
        messageBox.setForeground(Color.BLUE);
        messageBox.setEditable(false);
        
        quitChan.setEnabled(false);
        
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
        
		menuBar.setPreferredSize(new Dimension(200,20));
		
		JMenu fichier = new JMenu("Menu");
		JMenuItem quitter = new JMenuItem("Quitter");
		JMenuItem exit = new JMenuItem("Se déconnecter");
		fichier.add(exit);
		fichier.addSeparator();
		fichier.add(quitter);
		menuBar.add(fichier);

		
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				c.send("#EXIT");
				Client.Deconnexion();
				dispose();
				new Connect();
			}
		});
		
		quitter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Client.Deconnexion();
				System.exit(0);

			}
		});
        
        //tester si c'est utile
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
		
		joinChan.setPreferredSize(new Dimension(150,30));
		joinChan.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event) {
            		String leChan = cbCanaux.getSelectedItem().toString();
            		c.send("#JOIN " + leChan);
            		c.send("#CHANNELS");
            		messageBox.setEditable(true);
            		createChan.setEnabled(false);
            		joinChan.setEnabled(false);
            		quitChan.setEnabled(true);
            }
		});
		quitChan.setPreferredSize(new Dimension(150,30));
		quitChan.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event) {
            		quitChan.setEnabled(false);
            		joinChan.setEnabled(true);
            		createChan.setEnabled(true);
            		c.send("#QUIT");
            		c.send("#CHANNELS");
            		chatBox.setText(null);
            		messageBox.setText(null);
            		messageBox.setEditable(false);
            }
		});
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
            	joinChan.setEnabled(false);
            	createChan.setEnabled(false);
            	messageBox.setText(null);
            	messageBox.setEditable(true);
            	} else {
            		JOptionPane.showMessageDialog(chatBox, "Champ Vide");
            	}
            }
		});
		
		ChannelPan.add(channel);
		ChannelPan.add(createChan);
		ChannelPan.add(cbCanaux);
		ChannelPan.add(joinChan);
		ChannelPan.add(quitChan);
		mainPanel.add(BorderLayout.EAST, ChannelPan);

    }
	
	public void displayMsg(String msg){
		chatBox.append(msg);
	}
	
	public void displayChannelsList(String msg){
		cbCanaux.addItem(msg);
	}
	
	
	class FrameListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			Client.Deconnexion();
		}
	}

}