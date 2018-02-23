package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import model.beans.Message;
import model.beans.User;
import model.dao.exceptions.DAOException;
import model.dao.utils.DAOUtils;

public class DAOUser extends DAO<User> {

	protected DAOUser(Connection c) {
		super(c);
	}
	
	/*
	 * method to find user
	 * @param Object id
	 * @return User
	 * 
	 * */	
	@Override
	public User find(Object id) throws DAOException {
		final String sql = "SELECT * FROM users WHERE `id`= ?";

		PreparedStatement st = null;
		ResultSet r = null;
		
		if(!(id instanceof Integer)){
			throw new DAOException("Error id not supproted.");
		}
		
		
		try {
			st = connect.prepareStatement(sql);
			st.setInt(1, (int) id);
			r = st.executeQuery();

			if (r.next()) {
				final User u = new User();
				final List<Message>messageList;
				u.setId(r.getInt("id"));
				u.setPseudo(r.getString("pseudo"));

				messageList = new DAOMessage(connect).listMessageFromUser(u);
				u.setMessage(messageList);
				
				return u;
			}
			
			throw new DAOException("User not found !");
			
		} catch (SQLException e) {
			throw new DAOException("Error during USER dao manipulation.", e);
		} finally {
			DAOUtils.close(r);
			DAOUtils.close(st);
		}
	}

	/*
	 * method to create user
	 * @param User obj
	 * @return User
	 * 
	 * */	
	@Override
	public User create(User obj) throws DAOException {
		final String sql = "INSERT INTO users VALUES(NULL, ?)";
		
		PreparedStatement st = null;
		ResultSet r = null;
		
		try {
			st = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getPseudo());
			st.executeUpdate();
			
			r = st.getGeneratedKeys();
			
			if(r.next()) {
				obj.setId(r.getInt(1));
				return obj;
			}
			
			throw new DAOException("Error during user insertion.");
			
		} catch (SQLException e) {
			throw new DAOException("Error during DAO User manipulation.", e);
		} finally {
			DAOUtils.close(r);
			DAOUtils.close(st);
		}
	}

	/*
	 * method to update user
	 * @param User obj
	 * @return obj
	 * 
	 * */
	@Override
	public User update(User obj) throws DAOException {
		final String sql = "UPDATE users SET `pseudo`= ? WHERE `id`= ?";
	
		PreparedStatement st = null;
		
		
		try {
			st = connect.prepareStatement(sql);
			st.setString(1, obj.getPseudo());
			st.setInt(2, obj.getId());
			st.executeUpdate();
			
			return obj;

		} catch (SQLException e) {
			throw new DAOException("Error during USER update", e);
		} finally {
			DAOUtils.close(st);
		}
	}

	/*
	 * method to delete user
	 * @param User obj
	 * 
	 * */
	@Override
	public void delete(User obj) throws DAOException {
		final String sql = "DELETE FROM users WHERE `id`= ? ";
		
		PreparedStatement st = null;
		
		try {
			st = connect.prepareStatement(sql);
			st.setInt(1, obj.getId());
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DAOException("Error during DAO user deletion.", e);
		} finally {
			DAOUtils.close(st);
		}
	}

}