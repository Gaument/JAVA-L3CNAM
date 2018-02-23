package model.beans;

public class Message {
	private int id;
	private int idUser;
	private String content;


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
	 * @return the idUser
	 */
	public int getIdUser() {
		return idUser;
	}

	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/*
	 * equals method
	 * @param Object o
	 * @return boolean
	 * */
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Message)){
			return false;
		}else{
			Message m = (Message) o;
			return m.content.equals(content) && m.idUser == idUser;
		} 
    }  
		
}