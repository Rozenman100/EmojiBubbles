import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
 
class Client {
 
   	private static ObjectOutputStream toServer;
   	private static ObjectInputStream fromServer;
 
   	private Socket socket;
 
   	public Client() {
 
          	try {
                  	// create a socket to connect to the server
                  	socket = new Socket("localhost", 8000);
 
                  	// object to send data to the server
                  	toServer = new ObjectOutputStream(socket.getOutputStream());
 
                  	// object to receive data from the server
                  	fromServer = new ObjectInputStream(socket.getInputStream());
 
          	} catch (IOException ex) {
          }
   	}
 
   	public void writeFeedbackDataServer(FeedbackData data) {
          	try {
                  	toServer.writeObject(data);
                  	toServer.flush();
          	} catch (IOException e) {  e.printStackTrace();    }
   	}
   	
   	public void writeStatisticDataServer(StatisticData data) {
      	try {
              	toServer.writeObject(data);
              	toServer.flush();
      	} catch (IOException e) {  e.printStackTrace();    }
	}
 
   	public EmotionData readEmojiDataFromServer() {
 
          	try {
                  	return (EmotionData) fromServer.readObject();
          	} catch (ClassNotFoundException e) { e.printStackTrace(); }
            	catch (IOException e) { e.printStackTrace(); }
 
          	return null;
   	}
 
}
