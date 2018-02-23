package model.beans;

import java.util.List;

public class User {
	private int id;
	private String pseudo;
	private List<Message> message;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}
	
	/**
	 * @param pseudo the id to pseudo
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * @return the message list
	 */
	public List<Message> getMessage() {
		return message;
	}

	/**
	 * @param message list the id to message list
	 */
	public void setMessage(List<Message> message) {
		this.message = message;
	}
	
	/*
	 * equals method
	 * @param Object o
	 * @return boolean
	 * */
	@Override
	public boolean equals(Object o){
		if(!(o instanceof User)){
			return false;
		}else{
			User u = (User) o;
			return u.pseudo.equals(pseudo) && u.id == id;
		} 
    }
}