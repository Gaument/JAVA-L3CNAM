package main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;


public class Client {

	private static final Logger LOG = Logger.getLogger(Main.class.getName());

	static Socket s = null;

	static OutputStream out = null;
	static OutputStreamWriter osw = null;
	static BufferedWriter bw = null;
	
	private static boolean bConn;

	public static void Connexion() {
		try {
			bConn = true;
			s = new Socket(Connect.getIP(), 6667);

			out = s.getOutputStream();
			osw = new OutputStreamWriter(out, "UTF-8");
			bw = new BufferedWriter(osw);

			LOG.info("Start Socket Client");
		} catch (UnknownHostException e) {
			bConn = false;
			LOG.error("Error during host choosing", e);
		} catch (IOException e) {
			bConn = false;
			LOG.error("Error in socket IO", e);
		}

	}
	
	public static boolean getResultConnexion() {
		return bConn;
	}

	public static void EnvoiMessage(String msg) {
		try {

			bw.write(msg);
			bw.flush();

		} catch (UnknownHostException e) {
			LOG.error("Error during host choosing", e);
		} catch (IOException e) {
			LOG.error("Error in socket IO", e);
		} finally {
			try {
				if (s == null) {
					s.close();
					LOG.info("Stop Socket Client");
				}
			} catch (IOException e) {
				LOG.error("Error during socket stream closing.", e);
			}
		}

	}
	// }

	public static void Deconnexion() {

//		DAOUtils.close(bw);
//		DAOUtils.close(osw);
//		DAOUtils.close(out);
//		DAOUtils.close(s);
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			osw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.info("Stop Socket & Stream Client");

	}

	// private static Socket s = null;
	// private static OutputStream out = null;
	// private static OutputStreamWriter osw = null;
	// private static BufferedWriter bw = null;
	//
	// public static void Connexion() {
	// try {
	//
	// s = new Socket(Interface.getIP(), 6667);
	// out = s.getOutputStream();
	//
	// osw = new OutputStreamWriter(out, "UTF-8");
	// bw = new BufferedWriter(osw, 8192);
	//
	// } catch (IOException e) {
	// LOG.error("Error during Socket manipulator", e);
	// }
	// }
	//
	// public static void EnvoiMessage(String msg) {
	//
	// try {
	// //s = new Socket(Interface.getIP(), 6667);
	// out = s.getOutputStream();
	//
	// osw = new OutputStreamWriter(out, "UTF-8");
	// bw = new BufferedWriter(osw, 8192);
	//
	// bw.write(msg + "\n");
	// bw.flush();
	//
	// } catch (IOException e) {
	// LOG.error("Error during Socket manipulator", e);
	// } finally {
	// Utils.close(bw);
	// Utils.close(osw);
	// Utils.close(out);
	// }
	//
	// }
	// // }
	//
	// public static void Deconnexion() {
	//
	// Utils.close(bw);
	// Utils.close(osw);
	// Utils.close(out);
	// Utils.close(s);
	//
	// }

	// Ca marche !
	// public static void EnvoiMessage(String msg) {
	//
	// try {
	//
	// s = new Socket(Interface.getIP(), 6667);
	// out = s.getOutputStream();
	// osw = new OutputStreamWriter(out, "UTF-8");
	// bw = new BufferedWriter(osw, 8192);
	//
	// bw.write(msg);
	// bw.flush();
	//
	// } catch (IOException e) {
	// LOG.error("Error during Socket manipulator", e);
	// } finally {
	// Utils.close(bw);
	// Utils.close(osw);
	// Utils.close(out);
	// Utils.close(s);
	// // Utils.close(sc);
	// }
	// }
}