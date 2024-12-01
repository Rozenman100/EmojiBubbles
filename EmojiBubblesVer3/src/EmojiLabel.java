import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class EmojiLabel extends JLabel {

	private static int playerCounter = 1;
	public static Font font = new Font("Arial", Font.PLAIN, 20);

	private int points;
	private int increment;
	private int decrement;

	// c'tor for game type option
	public EmojiLabel(String imageName, int increment, int decrement) {

		super(imageName);
		setIcon(new ImageIcon(EmojiBubblesApp.imagesPath + imageName + ".png"));
		this.points = 0;
		this.increment = increment;
		this.decrement = decrement;

		setVerticalTextPosition(JLabel.BOTTOM);
		setHorizontalTextPosition(JLabel.CENTER);

		setFont(font);
		setOpaque(true);
		// setBorder(blackline);

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {

				setBackground(Color.green);
			}

			public void mouseExited(MouseEvent e) {

				setBackground(Color.white);

			}
		});

	}
	
	public EmojiLabel(String imageName) {

		super(imageName);
		setIcon(new ImageIcon(EmojiBubblesApp.imagesPath + imageName + ".png"));
		this.points = 0;

		setVerticalTextPosition(JLabel.BOTTOM);
		setHorizontalTextPosition(JLabel.CENTER);

		setFont(font);
		setOpaque(true);
		// setBorder(blackline);

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {

				setBackground(Color.green);
			}

			public void mouseExited(MouseEvent e) {

				setBackground(Color.white);

			}
		});

	}


	// used for players labels
	public EmojiLabel(String imageName, String textBelowImage) {

		super(textBelowImage);
		setIcon(new ImageIcon(EmojiBubblesApp.imagesPath + imageName + ".png"));
		setVerticalTextPosition(JLabel.BOTTOM);
		setHorizontalTextPosition(JLabel.CENTER);

		setFont(font);
		setText(this.getText() + " - 0");
		// setBorder(blackline);
	}

	public String getPlayerName() {
		return this.getText();
	}
	
	

	public int getIncrement() {
		return increment;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}

	public int getDecrement() {
		return decrement;
	}

	public void setDecrement(int decrement) {
		this.decrement = decrement;
	}

	public void addOnePoint() {
		this.points++;
	}

	public void removeOnePoint() {
		if (this.points > 0)
			this.points--;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

}
