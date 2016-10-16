package clientSrc;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.parser.ParseException;

public class Client {

	Socket socket=null;
	public void login(String user,String pwd){

		try{
			socket =  new Socket("localhost", 44);
//			State state = new State(user, "",pwd);
			State state = new State(user, "");
			// start sending thread
			MessageSendThread messageSendThread = new MessageSendThread(socket, state);
			Thread sendThread = new Thread(messageSendThread);
			sendThread.start();
			
			// start receiving thread
			Thread receiveThread = new Thread(new MessageReceiveThread(socket, state, messageSendThread, true));
			receiveThread.start();
			
		} catch (UnknownHostException e) {
			System.out.println("Unknown host");
		} catch (IOException e) {
			System.out.println("Communication Error: " + e.getMessage());
		}
	}

}
