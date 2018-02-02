package dao;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;


import model.beans.Message;
import model.beans.User;
import model.dao.DAOMessage;
import model.dao.exceptions.DAOException;
import model.dao.utils.DAOUtils;
import model.dao.DAOFactory;

public class DAOMessageTests {
	private static final Logger LOG = Logger.getLogger(Message.class.getName());
	@Test
	public void createMessageTest(){
		
		final DAOMessage messageDAO;
		
		Connection c = null;

        try {
            c = DAOFactory.getConnection();
		
		messageDAO = DAOFactory.getDAOMessage(c);
		
		Message m = new Message();
		m.setContent("test message2");
        m.setIdUser(1); 
        
        m = messageDAO.create(m);
        
        Message m2 = messageDAO.find(m.getId());
		
		Assert.assertEquals(m, m2);
		
		messageDAO.delete(m);
		
        }catch(DAOException e) {
            LOG.error("Error during DAO manipulation.", e);
        } finally {
            DAOUtils.close(c);
        }
		
	}
	
	@Test
	public void findMessageTest(){
		DAOMessage messageDAO;
		
		Connection c = null;

        try {
            c = DAOFactory.getConnection();
		
		messageDAO = DAOFactory.getDAOMessage(c);

        Message m3 = new Message();
        m3.setIdUser(1);
        m3.setContent("test message");
        
        m3 = messageDAO.create(m3);
        
        Message m4 = messageDAO.find(m3.getId());
    	
    	Assert.assertNotNull(m4);
    	
    	messageDAO.delete(m3);
		
        }catch(DAOException e) {
            LOG.error("Error during DAO manipulation.", e);
        } finally {
            DAOUtils.close(c);
        }
	}
	
	@Test
	public void updateMessageTest(){
	DAOMessage messageDAO;
	
	Connection c = null;

    try {
        c = DAOFactory.getConnection();
	
	messageDAO = DAOFactory.getDAOMessage(c);
	
	Message m5 = new Message();
    m5.setContent("message_base"); 
    
    m5 = messageDAO.create(m5);
    
    m5.setContent("changement message");
    
    m5 = messageDAO.update(m5);
    
    Message m6 = messageDAO.find(m5.getId());
    
    Assert.assertEquals(m5, m6);	
	
	messageDAO.delete(m5);
	
    }catch(DAOException e) {
        LOG.error("Error during DAO manipulation.", e);
    } finally {
        DAOUtils.close(c);
    }
	
}
	
	@Test 
	public void deleteMessageTest(){
		DAOMessage messageDAO;
		
		Connection c = null;

        try {
            c = DAOFactory.getConnection();
		
		System.out.println("Je fais mon test.");
		
		messageDAO = DAOFactory.getDAOMessage(c);
		
		Message m7 = new Message();
        m7.setContent("suppr msg"); 
        
        m7 = messageDAO.create(m7);
    	
    	messageDAO.delete(m7);
    	
    		
        }catch(DAOException e) {
            LOG.error("Error during DAO manipulation.", e);
        } finally {
            DAOUtils.close(c);
        }
		
	}
	

}