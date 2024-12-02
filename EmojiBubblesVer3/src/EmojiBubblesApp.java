import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class EmojiBubblesApp extends JFrame implements MouseListener {

	private static EmojiBubblesApp selfReference;
	private StatisticData statisticData;

	private JPanel mainPanel, pnlPlayerturnName, pnlMiddle, pnlButtons, pnlPlayers;
	private JLabel lblPlayerName, lblTheTurnOf, lblDisplayTime;
	private JLabel lblEndGame;

	private EmojiLabel elAddPlayer, currentPlayer;

	private BubbleGamePanel bubbleGamePanel;
	private Cursor cursor;

	private ArrayList<EmojiLabel> namesArrayList;
	private static int playerNum = 1;
	private String lastPlayerName;

	private JTextField txtAddURL;
	private Font font;

	private Client client;

	private EmojiTimer durationStatistics;
	private boolean isDataCollectionPermitted;

	public static String imagesPath = "images/";
	public static EmotionData emotionData;

	public EmojiBubblesApp(String caption) {
		super(caption);

		// create cursor
		this.cursor = (new EmojiCursor(6)).getCursor();
		this.statisticData = new StatisticData();

		// for communicating with server
		this.client = new Client();
		
		setCursor(this.cursor);
		selfReference = this;

		// player name section - > on top
		pnlPlayerturnName = new JPanel(new FlowLayout());
		pnlPlayerturnName.setOpaque(true);
		lblTheTurnOf = new JLabel("turn");
		lblPlayerName = new JLabel();
		lblTheTurnOf.setFont(EmojiLabel.font);
		lblPlayerName.setFont(EmojiLabel.font);
		pnlPlayerturnName.add(lblTheTurnOf);
		pnlPlayerturnName.add(lblPlayerName);
		lblDisplayTime = new JLabel();
		pnlPlayerturnName.add(lblDisplayTime);

		// Players - > on right
		pnlPlayers = new JPanel(new GridLayout(0, 1));
		pnlPlayers.setOpaque(true);
		elAddPlayer = new EmojiLabel("Add");
		pnlPlayers.add(elAddPlayer);

		// button panel -> on bottom
		pnlButtons = new JPanel(new FlowLayout());
		pnlButtons.setOpaque(true);
		pnlButtons.setBackground(Color.white);
		lblEndGame = new EmojiLabel("End Game");
		pnlButtons.add(lblEndGame);
		lblEndGame.addMouseListener(this);

		// main panel (contain all panels)
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(Color.white);
		mainPanel.add(pnlPlayerturnName, BorderLayout.NORTH);
		mainPanel.add(pnlPlayers, BorderLayout.EAST);
		// create bubble game, add it to panel and run it as thread

		pnlMiddle = new JPanel(new BorderLayout());
		pnlMiddle.setOpaque(true);
		pnlMiddle.setBackground(Color.white);
		pnlMiddle.add(new JLabel(new ImageIcon("images/red.png")), BorderLayout.CENTER);

		mainPanel.add(pnlMiddle, BorderLayout.CENTER);
		mainPanel.add(pnlButtons, BorderLayout.SOUTH);

		// array list of EmotionLabel for players list
		namesArrayList = new ArrayList<EmojiLabel>();

		elAddPlayer.addMouseListener(this);

		setContentPane(mainPanel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setExtendedState(JFrame.MAXIMIZED_BOTH);

		

		font = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
		UIManager.put("OptionPane.messageFont", font);
		UIManager.put("OptionPane.buttonFont", font);

		// allow share data
		int isPermitted = JOptionPane.showConfirmDialog(null, "Do you agree to share play habits to make us better?",
				"share data permission", JOptionPane.YES_NO_OPTION);

		if (isPermitted == 0)
			isDataCollectionPermitted = true;

		// create timer for statistics
		this.durationStatistics = new EmojiTimer();
		this.durationStatistics.getTimer().start();

		// emotionData = new EmotionData();
		// read data to emotionData

		emotionData = client.readEmojiDataFromServer();

		
		
		

		// when closing -> open feedback frame
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {

				new FeedbackFrame(namesArrayList.size(), client);

			}
		});
		
		setSize(1000, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setVisible(true);

	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new EmojiBubblesApp("EmotionTalkApp");

			}
		});

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == elAddPlayer) {
			// add new player with his name to arrayList
			lastPlayerName = JOptionPane.showInputDialog(null, "Enter player name");

			// make sure a player is not created if no name is provided
			if (lastPlayerName == null || lastPlayerName.trim().isEmpty()) {
	                System.out.println("No name found");
	                return;
	           	}
			namesArrayList.add(new EmojiLabel("" + playerNum++, lastPlayerName));
			
			if (isDataCollectionPermitted ) {
			statisticData.setPlayerCounter(statisticData.getPlayerCounter() + 1);
			System.out.println("number of players: " + statisticData.getPlayerCounter());
			}
			
			// add listener to new player label
			namesArrayList.get(namesArrayList.size() - 1).addMouseListener(this);

			// add the last player to players panel
			pnlPlayers.add(namesArrayList.get(namesArrayList.size() - 1));

			pnlPlayers.validate();

			repaint();
		} else if (e.getSource() == lblEndGame) { // pressed end game
			System.out.println("end game was pressed");
			durationStatistics.getTimer().stop();
			System.out.println("duration of play: " + durationStatistics.getCurrentTime());

			
			
			// add duration of play to statistics object
			
			if (isDataCollectionPermitted) {
			statisticData.setDurationOfPlay(durationStatistics.getCurrentTime());
			System.out.println(statisticData);

			// add date and time to statistic data object
			java.util.Date date = new java.util.Date();
			statisticData.setDate(date);
			}
			
			// send StatisticData to server
			client.writeStatisticDataServer(statisticData);

			// check if user wants to make feedback
			boolean shouldCallFeedbackFrame = false;
			
			int doesWantToFeedback = JOptionPane.showConfirmDialog(null, "Would you like to fill a feedback form?", "Form filling offer", JOptionPane.YES_NO_OPTION);

			if (doesWantToFeedback == 0)
				shouldCallFeedbackFrame = true;
			
			if (shouldCallFeedbackFrame) {
			
			FeedbackFrame feedbackFrame = new FeedbackFrame(namesArrayList.size(), client);
			} else {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
			
		} else { // pressed on player

			// display player turn on top (without his score)
			String playerPressedName = ((EmojiLabel) e.getSource()).getText();
			lblPlayerName.setText(playerPressedName.substring(0, playerPressedName.indexOf('-')));

			pnlMiddle.removeAll();

			// display option to choose color (send label of player for update his score at
			// the end of round)
			pnlMiddle.add(new EmotionChooseColorsPanel((EmojiLabel) e.getSource(), pnlMiddle));
			validate();

		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public JPanel getPnlMiddle() {
		return pnlMiddle;
	}

	public void setPnlMiddle(JPanel pnlMiddle) {
		this.pnlMiddle = pnlMiddle;
	}

	public static EmojiBubblesApp getMainObject() {
		return selfReference;
	}

	public JLabel getLblDisplayTime() {
		return lblDisplayTime;
	}

	public void setLblDisplayTime(JLabel lblDisplayTime) {
		this.lblDisplayTime = lblDisplayTime;
	}

	public ArrayList<EmojiLabel> getNamesArrayList() {
		return namesArrayList;
	}

	public void setNamesArrayList(ArrayList<EmojiLabel> namesArrayList) {
		this.namesArrayList = namesArrayList;
	}

	public StatisticData getStatisticData() {
		return statisticData;
	}

	public void setStatisticData(StatisticData statisticData) {
		this.statisticData = statisticData;
	}

	public boolean isDataCollectionPermitted() {
		return isDataCollectionPermitted;
	}

	public void setDataCollectionPermitted(boolean isDataCollectionPermitted) {
		this.isDataCollectionPermitted = isDataCollectionPermitted;
	}
	
	

}