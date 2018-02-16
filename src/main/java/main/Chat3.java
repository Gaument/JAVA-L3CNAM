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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chat3 extends JFrame{
	private Chat3 view;
	private JButton sendMessage = new JButton("Envoyer");
	public static JTextField messageBox = new JTextField();
	private String pseudoUser;
	
    JTextArea   chatBox;

	public Chat3(String IP, String Pseudo){
		
		super("SuperChat IRC");
		Container contents = getContentPane();
		
		contents.add(getAllChat(), BorderLayout.CENTER);

		pack(); setVisible(true);
	}
	/**
	 * Block de la fenêtre de chat 
	 * @return mainPanel
	 */
	public JPanel getAllChat(){
		JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(1000,400));

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridBagLayout());

        messageBox.requestFocusInWindow();
        messageBox.setBackground(Color.YELLOW);
        messageBox.setForeground(Color.BLUE);

        sendMessage.setBackground(Color.RED);
        sendMessage.setForeground(Color.WHITE);
        sendMessage.addActionListener(new sendMessageButtonListener());
        sendMessage.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
	                chatBox.append("<" + getuserPseudo() + ">:  " + messageBox.getText()
                    		+ "\n");
					messageBox.setText("");;
				}
			public void keyReleased(KeyEvent e) {
				}
		});

        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

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
        ChannelPan.setPreferredSize(new Dimension(200,200));
        ChannelPan.setBackground(Color.BLUE);
		JLabel channel = new JLabel("Channels List:");	
		channel.setForeground(Color.WHITE);
		ChannelPan.add(channel);
		mainPanel.add(BorderLayout.EAST, ChannelPan);
        return mainPanel;

    }
	
	/**
	 * Action listener sur boutton envoyer message
	 *
	 */
    class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
                chatBox.append("<" + pseudoUser + ">:  " + messageBox.getText()
                        + "\n");
                messageBox.setText("");
        }
    }
	/**
	 * Getter et setter pseudo de l'user
	 * @return pseudoUser
	 */
	public String getuserPseudo() {
		return this.pseudoUser;
	}
	
	public void setuserPseudo(String pseudoUser) {
		this.pseudoUser = pseudoUser;
	}

}