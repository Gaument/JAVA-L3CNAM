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


	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public List<Message> getMessage() {
		return message;
	}

	public void setMessage(List<Message> message) {
		this.message = message;
	}
	
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