package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import org.apache.log4j.Logger;


public class Client extends Thread {
	private static final Logger LOG = Logger.getLogger(Main.class.getName());
	
	private Socket s = null;
	private Chat3 chat3;
	private String msg;
	
	private static boolean bConn;

	public Client(String ip, String pseudo) {
		
		try {
			bConn = true;
			s = new Socket(ip, 6667);
			chat3 = new Chat3(this);
			
			LOG.info("Start Socket Client");
		} catch (UnknownHostException e) {
			bConn = false;
			LOG.error("Error during host choosing", e);
		} catch (IOException e) {
			bConn = false;
			LOG.error("Error in socket IO", e);
		}

	}
	
	public boolean getResultConnexion() {
		return bConn;
	}

	// }

//	public static void Deconnexion() {
//
////		DAOUtils.close(bw);
////		DAOUtils.close(osw);
////		DAOUtils.close(out);
////		DAOUtils.close(s);
//		try {
//			bw.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			osw.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			out.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			s.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		LOG.info("Stop Socket & Stream Client");
//
//	}
//	

	public boolean send(String mot) {
		boolean reussite = true;		
		OutputStream out = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			out = s.getOutputStream();
			osw = new OutputStreamWriter(out, "UTF-8");
			bw = new BufferedWriter(osw, 8192);
			
			bw.write(mot + "\n");
			bw.flush();
		}catch(IOException e) {
			LOG.error("Error during Socket manipulation.", e);
			reussite = false;
		}
		return reussite;
	}
	
	public void run() {
		readMessage(); 
	}

	public void displayMsg(String msg){
		chat3.displayMsg(msg);
	
	}

	public void readMessage(){
		InputStream in = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		JSONObject jsonObj;
		try {
			
			in = s.getInputStream();
			isr = new InputStreamReader(in, "UTF-8");
			br = new BufferedReader(isr);
			
			while(true) {
				
				msg = br.readLine();
				msg += "\n";
				jsonObj = new JSONObject(msg);
				
				if (jsonObj.has("message")){
					String pseudo = jsonObj.getJSONObject("message").getString("pseudo");
					String content =jsonObj.getJSONObject("message").getString("content");
				
					chat3.displayMsg(pseudo + " : " + content + "\n");
				}
				
				
//				if(br.readLine() != null){	
//					str = br.readLine();
//					LOG.info("Message du client a convertir: " + str);
//					displayMsg(str);
//				}
			}
			
		} catch (IOException e) {
			LOG.error("Problème de connection: " + e);
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
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