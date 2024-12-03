import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EmotionConversationPanel extends JPanel implements MouseListener {

	private JPanel pnlUpper, pnlQuestion, pnlPlay;

	private JLabel lblEmotion, lblDefinition, lblEmotionImage;
	private JLabel lblSwapQuestion, lblQuestionText, lblPlayGame;
	private JLabel  lblchooseGameOption, lblRegularGame, lblRiskGame, lblDoubleRiskGame;

	private EmojiLabel playerEmotionLabel;
	private JPanel mainClassPnlMiddle;
	private String colorName;
	private Font titleFont, descriptionFont, questionFont;

	private ArrayList<String> emotionQuestionArrayList;
	private int emotionQuestionArrayListIndex;
	
	private int clickedIndex;

	public EmotionConversationPanel(EmojiLabel playerEmotionLabel, String colorName, JPanel mainClassPnlMiddle, int clickedIndex) {

		this.clickedIndex = clickedIndex;
		this.mainClassPnlMiddle = mainClassPnlMiddle;
		this.playerEmotionLabel = playerEmotionLabel;
		this.colorName = colorName;
		//this.setOpaque(true);
		this.setBackground(Color.white);
		titleFont = new Font("Arial", Font.PLAIN, 70);
		descriptionFont = new Font("Arial", Font.PLAIN, 50);
		questionFont = new Font("Arial", Font.PLAIN, 50);

		int index = 0;

		// find index of color for background color of title
		for (int i = 0; i < EmojiBubblesApp.emotionData.getEmotionResultArraylist().size(); i++)
			if (GameColors.colorsStringArr[i].equals(colorName)) {
				index = i;
				break;
			}

		// prepare data to display
		ArrayList<Emotion> emotionsArrayList = EmojiBubblesApp.emotionData.getEmotionResultArraylist().get(index)
				.getEmotionsArraylist();
		int size = emotionsArrayList.size();
		Emotion randomEmotion = emotionsArrayList.get((int) (Math.random() * emotionsArrayList.size()));

		String emotionName = randomEmotion.getName();
		String emotionDefinition = randomEmotion.getDefinition();
		emotionQuestionArrayList = randomEmotion.getQuestionArraylist();
		emotionQuestionArrayListIndex = 0;
		Collections.shuffle(emotionQuestionArrayList);

		// upper panel
		pnlUpper = new JPanel(new BorderLayout());
		lblEmotion = new JLabel(emotionName, SwingConstants.CENTER);
		lblEmotion.setFont(titleFont);
		lblDefinition = new JLabel(emotionDefinition, SwingConstants.RIGHT);
		lblDefinition.setFont(descriptionFont);
		pnlUpper.add(lblEmotion, BorderLayout.CENTER);
		pnlUpper.add(lblDefinition, BorderLayout.SOUTH);
		pnlUpper.setBackground(GameColors.colorsArr[index]);

		// question panel
		pnlQuestion = new JPanel(new BorderLayout());
		lblSwapQuestion = new JLabel("?", SwingConstants.LEFT);
		lblSwapQuestion.setFont(questionFont);
		lblSwapQuestion.addMouseListener(this);
		lblQuestionText = new JLabel("", SwingConstants.RIGHT);
		lblQuestionText.setFont(questionFont);

		pnlQuestion.add(lblSwapQuestion, BorderLayout.WEST);
		pnlQuestion.add(lblQuestionText, BorderLayout.CENTER);
		pnlQuestion.setBackground(Color.white);

		// start to play panel
		pnlPlay = new JPanel(new FlowLayout());
		pnlPlay.setBackground(Color.white);
		
		
		lblchooseGameOption=new JLabel("Choose game option:");
		lblchooseGameOption.setFont(new Font("Arial", Font.PLAIN, 40));
		lblRegularGame = new EmojiLabel("Regular", 1, 0);
		lblRiskGame = new EmojiLabel("Risk", 2, -1);
		lblDoubleRiskGame = new EmojiLabel("Double Risk", 4, -2);
		lblRegularGame.setBackground(Color.white);
		lblRiskGame.setBackground(Color.white);
		lblDoubleRiskGame.setBackground(Color.white);
		lblRegularGame.addMouseListener(this);
		lblRiskGame.addMouseListener(this);
		lblDoubleRiskGame.addMouseListener(this);

		lblRegularGame.setToolTipText("each bubble you explode is 1 point");
		lblRiskGame.setToolTipText("each buuble is 2 points, each miss is 1 point");
		lblDoubleRiskGame.setToolTipText("get x2 points or time of bubble size");
		
		pnlPlay.add(lblchooseGameOption);
		pnlPlay.add(lblRegularGame);
		pnlPlay.add(lblRiskGame);
		pnlPlay.add(lblDoubleRiskGame);

		this.setLayout(new GridLayout(0, 1));
		this.add(pnlUpper);
		this.add(pnlQuestion);
		this.add(pnlPlay);

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent me) {
		// swap question -> get next question from array
		// and if you got to end -> start from beginning
		if (me.getSource() == lblSwapQuestion) {
			if (emotionQuestionArrayListIndex == emotionQuestionArrayList.size())
				emotionQuestionArrayListIndex = 0;
			lblQuestionText.setText(emotionQuestionArrayList.get(emotionQuestionArrayListIndex++));

			// remove conversation and add game panel with player and selected color
		} else  {

			//String gameType=((JLabel)me.getSource()).getText();
			EmojiLabel gameType=(EmojiLabel)me.getSource();
			mainClassPnlMiddle.removeAll();
			
			
			
			mainClassPnlMiddle.add(new BubbleGamePanel(this.playerEmotionLabel, gameType, this.colorName, this.clickedIndex));
			System.out.println("game type: " + gameType);
			
			mainClassPnlMiddle.validate();
			
			// add chosen color to statistic data
			
			if (EmojiBubblesApp.getMainObject().isDataCollectionPermitted()) {
			EmojiBubblesApp.getMainObject().getStatisticData().addToColorCounter(GameColors.colorsArr[clickedIndex]);
			System.out.println(EmojiBubblesApp.getMainObject().getStatisticData().getColorUsageCounter());
			}
			
			new EmojiTimer(EmojiBubblesApp.getMainObject().getPnlMiddle()).getTimer().start(); // start 10 second timer
			
			EmojiBubblesApp.getMainObject().getLblDisplayTime().setForeground(GameColors.colorsArr[this.clickedIndex]);
			
			this.validate();
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
