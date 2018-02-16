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
	}
}

