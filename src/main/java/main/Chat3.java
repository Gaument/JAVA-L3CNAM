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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.OutputStreamWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class Chat3 extends JFrame {
	private static final long serialVersionUID = 1L;
	//	private Chat3 view;
	private JButton sendMessage = new JButton("Envoyer");
	public static JTextArea chatBox = new JTextArea();
	private JPanel mainPanel = new JPanel();
	public static JTextField messageBox = new JTextField();
	
	public Chat3(Client c){
		
		super("SuperChat IRC");
		
		Container contents = getContentPane();
		
		contents.add(mainPanel, BorderLayout.CENTER);

		pack(); setVisible(true);
		setSize(700, 400);
		setResizable(true);
		setVisible(true);
		setLocationRelativeTo(null);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(1000,400));

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridBagLayout());
        
//        this.addWindowListener(new FrameListener());
//        
        messageBox.requestFocusInWindow();
        messageBox.setBackground(Color.YELLOW);
        messageBox.setForeground(Color.BLUE);
        
        sendMessage.setBackground(Color.RED);
        sendMessage.setForeground(Color.WHITE);
        sendMessage.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event) {
            c.send("#MSG " + messageBox.getText());
            messageBox.setText(null);
            }
        });
//        sendMessage.addKeyListener(new KeyListener() {
//			public void keyTyped(KeyEvent e) {
//				}
//			public void keyPressed(KeyEvent e) {
//				if (e.getKeyCode() == KeyEvent.VK_ENTER)
//	                chatBox.append(Pseudo + " :#MSG" + messageBox.getText()
//                    		+ "\n");
//					messageBox.setText("");
//				}
//			public void keyReleased(KeyEvent e) {
//				}
//		});

        chatBox.setEditable(false);
//        chatBox.setLineWrap(true);

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

    }
	
	public void displayMsg(String msg){
		chatBox.append(msg);
	}
	
	
//	class FrameListener extends WindowAdapter {
//		public void windowClosing(WindowEvent e) {
//			Client.Deconnexion();
//		}
//	}

}