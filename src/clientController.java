
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

import java.awt.*;
import clientSrc.*;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.EventListener;

/**
 * Created by Luna on 1/10/2016.
 */
public class clientController     {



    public clientController(String user){


        try{
            socket =  new Socket("localhost", 4444);
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

        MessageSendThread.queue.add("#Who");
        MessageSendThread.queue.add("#List");

    }

    Socket socket=null;


}
