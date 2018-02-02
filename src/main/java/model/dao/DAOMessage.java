package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import model.beans.Message;
import model.beans.User;
import model.dao.exceptions.DAOException;
import model.dao.utils.DAOUtils;

public class DAOMessage extends DAO<Message> {

	protected DAOMessage(Connection c) {
		super(c);
	}
	
	public List<Message> listMessageFromUser (User u) throws DAOException{
		final String sql="SELECT * FROM message WHERE author = ?";
		final List<Message> messageList = new LinkedList<>();
		
		PreparedStatement st = null;
		ResultSet r = null;
		
		try{
			st = connect.prepareStatement(sql);
			st.setInt(1,  u.getId());
			r = st.executeQuery();
			
			while (r.next()){
				messageList.add(find(r.getInt(1)));
			}
			return messageList;
			
		}catch (SQLException e){
			throw new DAOException("Error during DAO Message Listing.", e);
		}finally{
			DAOUtils.close(r);
			DAOUtils.close(st);
		}
	}

	@Override
	public Message find(Object id) throws DAOException {
		final String sql = "SELECT * FROM message WHERE `id`= ? ";
		final Message m = new Message();

		PreparedStatement st = null;
		ResultSet r = null;

		if (!(id instanceof Integer)) {
			throw new DAOException("Error in id format.");
		}

		try {
			st = connect.prepareStatement(sql);
			st.setInt(1, (int) id);
			r = st.executeQuery();

			if (r.next()) {
				m.setId(r.getInt("id"));
				m.setIdUser(r.getInt("author"));
				m.setContent(r.getString("content"));

				return m;
			}

			throw new DAOException("Message not found in the database.");

		} catch (SQLException e) {
			throw new DAOException("Error during DAO message finding.", e);
		} finally {
			DAOUtils.close(r);
			DAOUtils.close(st);
		}
	}

	@Override
	public Message create(Message obj) throws DAOException {
		final String sql = "INSERT INTO message VALUES(NULL, ? , ?)";

		PreparedStatement st = null;
		ResultSet r = null;

		try {
			st = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, obj.getIdUser());
			st.setString(2, obj.getContent());
			st.executeUpdate();

			r = st.getGeneratedKeys();

			if (r.next()) {
				obj.setId(r.getInt(1));
				return obj;
			}

			throw new DAOException("Error during DAO creation.");

		} catch (SQLException e) {
			throw new DAOException("Error during DAO Message creation.", e);
		} finally {
			DAOUtils.close(r);
			DAOUtils.close(st);
		}
	}

	@Override
	public Message update(Message obj) throws DAOException {
		final String sql = "UPDATE message SET `id_user`= ? , `content`= ? WHERE `id`= ? ";

		PreparedStatement st = null;

		try {
			st = connect.prepareStatement(sql);
			st.setInt(1, obj.getIdUser());
			st.setString(2, obj.getContent());
			st.setInt(3, obj.getId());
			st.executeUpdate();
			
			return obj;

		} catch (SQLException e) {
			throw new DAOException("Error during DAO Message update.", e);
		} finally {
			DAOUtils.close(st);
		}
	}

	@Override
	public void delete(Message obj) throws DAOException {
		final String sql = "DELETE FROM message WHERE `id`= ? ";
		
		PreparedStatement st = null;
		try {
			st = connect.prepareStatement(sql);
			st.setInt(1, obj.getId());
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DAOException("Error during DAO Message update.", e);
		} finally {
			DAOUtils.close(st);
		}
	}
}