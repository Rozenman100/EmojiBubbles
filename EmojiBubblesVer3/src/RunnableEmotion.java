import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

class RunnableEmotion implements Runnable {
	private Socket socket; // A connected socket

	private ObjectInputStream inputFromClient;
	private ObjectOutputStream outputToClient;

	private EmotionData emotionData;
	private FeedbackData feedbackData;
	private Object data;
	private StatisticData statisticData;

	public RunnableEmotion(Socket socket) {
		this.socket = socket;
		emotionData = new EmotionData();
		this.feedbackData = null;
	}

	public void run() {

		// create object input and output streams
		try {
			outputToClient = new ObjectOutputStream(socket.getOutputStream());
			inputFromClient = new ObjectInputStream(socket.getInputStream());
		

		

			// first read all sheets names (EmotionResult)
			emotionData = ReadExcel.readEmotionResultNames("excelEmotionFolder//emotions.xls", emotionData);

			// second read to each EmotionResult it's emotions
			emotionData = ReadExcel.ReadEmotionData("excelEmotionFolder//emotions.xls", emotionData);

			// display emotionData details before send to client
			System.out.println("server details: " + emotionData);

			// send emotionData back to client
			outputToClient.writeObject(emotionData);

			while (true) {
				data = inputFromClient.readObject();
				if (data instanceof FeedbackData) {
				
					feedbackData = (FeedbackData) data;
					System.out.println(feedbackData);
					SQLQuery.insertFeedback(feedbackData);
				} else if (data instanceof StatisticData) {
					
					statisticData = (StatisticData) data;
					System.out.println(statisticData);
					SQLQuery.insertStatisticData(statisticData);
				}
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (java.net.SocketException e) {
			e.printStackTrace();
			System.out.println("client communication lost");
		} catch (IOException e) {
			e.printStackTrace();

		}

	}
}
