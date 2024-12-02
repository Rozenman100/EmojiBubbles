import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BubbleGamePanel extends JPanel implements Runnable {

	private ArrayList<Bubble> arrBubbles;
	private Bubble mouseBubble;
	public static Color colorsArr[] = { Color.orange, Color.pink, Color.gray, Color.green, new Color(102, 0, 153),
			Color.red };
	private String colorName;
	private EmojiLabel playerEmotionLabel;
	private EmojiLabel gameType;

	Thread runner;
	
	private int clickedIndex;

	public BubbleGamePanel(EmojiLabel playerEmotionLabel, EmojiLabel gameType, String colorName, int clickedIndex) {

		this.clickedIndex = clickedIndex;
		this.playerEmotionLabel = playerEmotionLabel;
		this.colorName = colorName;
		this.gameType = gameType;
		this.arrBubbles = new ArrayList<Bubble>();

		setCursor(new EmojiCursor(clickedIndex).getCursor());
		
		// run the game
		setBackground(Color.WHITE);

		for (int i = 0; i < 5; i++)
			arrBubbles.add(new Bubble(GameColors.colorsArr[this.clickedIndex]));

		for (int i = 0; i <= 15; i++) {
			arrBubbles.add(new Bubble());

			// mouseBubble = new Bubble();
			// vecBubbles.lastElement().setPosition((int)(Math.random()*500),(int)(Math.random()*500));

		}
		runner = new Thread(this);
		runner.start();

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				Bubble b = null;
				
				int size = arrBubbles.size();

				for (int i = 0; i < size; i++) {

					b = arrBubbles.get(i);

					// check if explode
					if (b.insideBubble(e.getX(), e.getY())) {
						if (b.getColor().equals(GameColors.colorsArr[clickedIndex])) {

							
							playerEmotionLabel.setText(updateScore(playerEmotionLabel.getText(), gameType.getIncrement()));
							
							//System.out.println("add points \n updated points: " + playerEmotionLabel.getPoints());
							//playerEmotionLabel.setText(
							//		playerEmotionLabel.getPlayerName() + " - " + playerEmotionLabel.getPoints());

						} else {
							playerEmotionLabel.setText(updateScore(playerEmotionLabel.getText(), gameType.getDecrement()));
							
							//System.out.println("remove points \n updated points: " + playerEmotionLabel.getPoints());
							//playerEmotionLabel.setText(
									//playerEmotionLabel.getPlayerName() + " - " + playerEmotionLabel.getPoints());
						}
						
						arrBubbles.remove(b);
						arrBubbles.add(new Bubble(b.getColor()));
						
						// size changing when delete bubbles
						size = arrBubbles.size();
					}
					

				}

				repaint();
			}
		});

	}

	// paint bubbles
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0; i < arrBubbles.size(); i++) {

			arrBubbles.get(i).breathe();
			arrBubbles.get(i).paint(g);
		}

	}

	// runner thread
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(25);
			}

			catch (Exception e) {
			}

			repaint();
		}

	}
	
	// update score for EmotionLabel of player text
	public String updateScore(String str, int amount) {
		
		String name=str.substring(0, str.indexOf('-'));
		System.out.println("name: "+name);
		String seperate="- ";
		String score=str.substring(str.indexOf('-')+2, str.length());
		
		// if score is 0 -> no change
		if(Integer.parseInt(score) + amount < 0)
			return str;
		
		System.out.println("score: "+score);
		
		int newScore=Integer.parseInt(score)+amount;
		
		System.out.println("new score: "+newScore);
		
		return name+seperate+newScore;
		
	}

}
