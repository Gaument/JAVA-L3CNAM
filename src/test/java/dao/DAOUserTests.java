package dao;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import model.beans.User;
import model.dao.DAOFactory;
import model.dao.DAOUser;
import model.dao.exceptions.DAOException;
import model.dao.utils.DAOUtils;


public class DAOUserTests {
	private static final Logger LOG = Logger.getLogger(User.class.getName());
	

@Test
	public void createUserTest(){
		DAOUser userDAO;
		
		Connection c = null;

        try {
            c = DAOFactory.getConnection();
		
		
		User u = new User();
		userDAO = DAOFactory.getDAOUser(c);
		
		u.setPseudo("Fanny-test10");
        
        u = userDAO.create(u);
        
        User u2 = userDAO.find(u.getId());
		
		Assert.assertEquals(u, u2);
		
		userDAO.delete(u);
		
        }catch(DAOException e) {
            LOG.error("Error during DAO manipulation.", e);
        } finally {
            DAOUtils.close(c);
        }   DAOUtils.close(c);
}

@Test
public void findUserTest(){
	DAOUser userDAO;
	
	Connection c = null;

    try {
        c = DAOFactory.getConnection();
	
        userDAO = DAOFactory.getDAOUser(c);
	
	
	User u3 = new User();
    u3.setPseudo("Fanny_22");
    
    u3 = userDAO.create(u3);
    
    User u4 = userDAO.find(u3.getId());
	
	Assert.assertNotNull(u4);
	
	userDAO.delete(u3);
	
    }catch(DAOException e) {
        LOG.error("Error during DAO manipulation.", e);
    } finally {
        DAOUtils.close(c);
    }
}

@Test
public void updateUserTest(){
	DAOUser userDAO;
	
	Connection c = null;

    try {
        c = DAOFactory.getConnection();
	
        userDAO = DAOFactory.getDAOUser(c);
        
        User u5 = new User();
        u5.setPseudo("Fanny_77"); 
        
        u5 = userDAO.create(u5);
        
        u5.setPseudo("Fanny77");
        
        u5 = userDAO.update(u5);
        
        User u6 = userDAO.find(u5.getId());
        
        Assert.assertEquals(u5, u6);	
    	
    	userDAO.delete(u5);
        
    }catch(DAOException e) {
        LOG.error("Error during DAO manipulation.", e);
    } finally {
        DAOUtils.close(c);
    }
}

@Test
public void deleteUserTest(){
	DAOUser userDAO;
	
	Connection c = null;

    try {
        c = DAOFactory.getConnection();
	
        userDAO = DAOFactory.getDAOUser(c);
        
        User u7 = new User();
        u7.setPseudo("Fanny_77"); 
        
        u7 = userDAO.create(u7);
        
        userDAO.delete(u7);
        
    	
        
    }catch(DAOException e) {
        LOG.error("Error during DAO manipulation.", e);
    } finally {
        DAOUtils.close(c);
    }
}


	
}