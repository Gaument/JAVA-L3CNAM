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
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.log4j.Logger;


public class Client extends Thread {
	
	//composant de ma classe
	private static final Logger LOG = Logger.getLogger(Main.class.getName());
	
	private static Socket s = null;
	private static Chat chat;
	private String msg;
	
	private static boolean bConn;

	public Client(String ip, String pseudo) {
	//on récupère le pseudo et l'ip, si l'ip est ok et la connexion effectué on ouvre la socket	
		try {
			bConn = true;
			s = new Socket(ip, 6667);
			chat = new Chat(this);
			
			LOG.info("Start Socket Client");
		} catch (UnknownHostException e) {
			bConn = false;
			LOG.error("Error during host choosing", e);
		} catch (IOException e) {
			bConn = false;
			LOG.error("Error in socket IO", e);
		}

	}
	//résultat de Client, si true ça connecte le client au chat
	public boolean getResultConnexion() {
		return bConn;
	}


	//Ferme la socket lors de la fermeture du chat
	public static void Deconnexion() {
		
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
		try {
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.info("Stop Socket & Stream Client");

	}
	
	//envoi les msg avec la commande #MSG
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
	
	//lire les msg que renvoit le serveur dans le chat
	public void displayMsg(String msg){
		chat.displayMsg(msg);
	}

	//récupère la liste des channels pour la mettre dans une ComboBox
	public static void channelsList(String str){
		List<String> channelsList = new ArrayList<String>();
		
		try {
            JSONObject jsonObj = new JSONObject(str);
            if (jsonObj != null) {
            	chat.emptyChannelsList();
                JSONArray channelsJsonArray = jsonObj.getJSONArray("channels");
               
                for(int i = 0 ; i < channelsJsonArray.length();i++) {
                	channelsList.add(channelsJsonArray.getString(i));
                	if (channelsJsonArray.getString(i).isEmpty()){
                		chat.setDarkChannelsList();
                	} else {
                		chat.setOkChannelsList();
                		chat.displayChannelsList(channelsJsonArray.getString(i));
                	}
                }         
                chat.countChannelsList();
            }
           
        }catch(JSONException e) {
            e.printStackTrace();
        }finally {
            
        }
	}
	
	//récupère en json les msg envoyé par le serveur et leur contenu
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
				
					chat.displayMsg(pseudo + " : " + content + "\n");
				} else if (jsonObj.has("connect")){
					String pseudo = jsonObj.getJSONObject("connect").getString("pseudo");
					chat.displayMsg("Vous êtes connecté sous le pseudo : " + pseudo + "\n");
				} else if (jsonObj.has("join")){
					String channel = jsonObj.getJSONObject("join").getString("channel");
					chat.displayMsg("Vous avez rejoint le channel : " + channel + "\n");
				} else if (jsonObj.has("discochannel")){
					String channel = jsonObj.getJSONObject("discochannel").getString("channel");
					chat.displayMsg("Vous avez quitté le channel : " + channel + "\n");
				} else if (jsonObj.has("discoserver")){
					
				} else if (jsonObj.has("channels")){
					channelsList(msg);
				}
				
			}
			
		} catch (IOException e) {
			LOG.error("Problème de connection: " + e);
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} 
	}
	
}