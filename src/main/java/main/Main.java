package main;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import model.beans.Message;
import model.beans.User;
import model.dao.DAOMessage;
import model.dao.DAOFactory;
import model.dao.DAOUser;
import model.dao.exceptions.DAOException;
import model.dao.utils.DAOUtils;
/**
 * 
 * @author gauthier
 *
 */
public class Main {
	private static final Logger LOG = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {
		new Connect();
        final List<Message> message = new LinkedList<>();
        final DAOUser userDAO;
        final DAOMessage messageDAO;
        
        Connection c = null;

        try {
            c = DAOFactory.getConnection();
            
            final User u = new User();
            userDAO = DAOFactory.getDAOUser(c);
            messageDAO = DAOFactory.getDAOMessage(c);
            
            u.setPseudo("Fanny11");

            userDAO.create(u);
            
            userDAO.delete(u);

            //userDAO.update(u);
            
            //userDAO.find(u.getId());
            
            
            //final Message m = new Message();
            //m.setIdUser(u.getId());
            //m.setContent("Contenu d'un message");

            
            //messageDAO.create(m);
            //a.setCity("Saint-Étienne");
            
            //message.add(m);
            //u.setMessage(message);
            
            
            LOG.info(u.getPseudo());

        } catch (DAOException e) {
            LOG.error("Error during DAO manipulation.", e);
        } finally {
            DAOUtils.close(c);
        }

	}
}

