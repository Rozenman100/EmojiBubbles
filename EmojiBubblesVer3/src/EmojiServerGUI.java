import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class EmojiServerGUI extends JFrame {

	private JTextArea jta = new JTextArea();
	private JPanel contentPane;
	private ServerSocket serverSocket;
	private Socket socket;

	public static void main(String[] args) {
		new EmojiServerGUI();
	}

	public EmojiServerGUI() {
		SQLQuery.connect("root", "123456");

		contentPane = new JPanel(new BorderLayout());
		contentPane.add(new JScrollPane(jta), BorderLayout.CENTER);
		setContentPane(contentPane);

		setTitle("Emoji Server GUI");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		jta.append("Emoji Server GUI started \n");

		int clientNo = 1;

		try {
			serverSocket = new ServerSocket(8000);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while (true) {

			try {
				socket = serverSocket.accept(); // new connection request

				// display the client number
				jta.append("client " + clientNo + " connected \n ");

				// create a new task for the connection
				new Thread(new RunnableEmotion(socket)).start();

				clientNo++;
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} // end of while
	}
}
