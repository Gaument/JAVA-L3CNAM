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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
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