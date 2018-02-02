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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Connect extends JFrame implements ActionListener{
	private String pseudoUser;
	public Connect(){
		super("Connection au Superchat");
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
		JPanel mainPanel = new JPanel();
		JPanel southPanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		mainPanel.setPreferredSize(new Dimension(600,150));
		mainPanel.setBackground(Color.RED);
		
		JLabel pseudoLabel = new JLabel("Pseudo :");
		JLabel serverLabel = new JLabel("IP :");

		JTextField pseudoField = new JTextField();		
		JTextField serverField = new JTextField();
		
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
		buttonPanel.add(okButton,left);
		mainPanel.add(BorderLayout.SOUTH, buttonPanel);
		

	    okButton.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	    	  
	    	  String serverAddress = serverField.getText();
	    	  String pseudoUser    = pseudoField.getText();
			  System.out.println(pseudoUser);

	    	  if(serverAddress.isEmpty() || pseudoUser.isEmpty()) {
	    		  JOptionPane popup;      
				  popup = new JOptionPane();
				  JPanel panel = new JPanel();
				  JLabel chanLabel = new JLabel("Champ(s) vide(s) !");
				  panel.add(chanLabel);
				  popup.showMessageDialog(null, panel, "Attention", JOptionPane.INFORMATION_MESSAGE, null);
	    	  }else {
	    		  System.out.println(pseudoUser);
	    		  setVisible(false);
	    		  final Chat window = new Chat();
	    		  window.setLocationRelativeTo(null);
	    		  window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    		  window.setResizable(false);
	    		  window.setVisible(true);
	    		  JPanel panel = new JPanel();
	    		  window.setTitle("Superchat://" + serverAddress + "/" + pseudoUser);
	    		  window.setuserPseudo(pseudoUser);	  
	    	  }
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
		ImageIcon icon = new ImageIcon("/home/gauthier/eclipseworkspace/Superchat/img/superman.gif");
		JLabel img = new JLabel(icon);
		imgPanel.add(img);
		imgPanel.setBackground(Color.RED);
		return imgPanel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
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

}