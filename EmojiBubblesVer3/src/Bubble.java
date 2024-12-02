import java.awt.*;

class Bubble {

	private int x, y, diameter, startDiameter, endDiameter;
	private boolean growing = true;
	private Graphics g;
	private Color color;
	private int colorIndex;

	public Bubble() {

		startDiameter = 20 + (int) (Math.random() * 30);

		endDiameter = 70 + (int) (Math.random() * 40);

		diameter = startDiameter;

		colorIndex=(int) (Math.random() * BubbleGamePanel.colorsArr.length);
		color = BubbleGamePanel.colorsArr[colorIndex];

		this.x = (int) (Math.random() * 900);
		this.y = (int) (Math.random() * 650);
	}

	public Bubble(Color c) {
		startDiameter = 20 + (int) (Math.random() * 30);
		endDiameter = 70 + (int) (Math.random() * 40);
		diameter = startDiameter;
		color = c;
		
		this.x = (int) (Math.random() * 1000);
		this.y = (int) (Math.random() * 650);
	}

	public Bubble(int start, int end, Color c) {
		startDiameter = start;
		endDiameter = end;
		diameter = startDiameter;
		color = c;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void drawBubble() {
		g.setColor(color);
		g.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter);
	}

	public void breathe() {
		// grow till end diameter is reached
		if (growing) {
			if (diameter < endDiameter)
				diameter++;
			else
				growing = false;

		}

		// shrink till start diameter is reached
		else {
			if (diameter > startDiameter)
				diameter--;
			else
				growing = true;
		}
	}

	public void paint(Graphics gr) {
		g = gr;
		drawBubble();
	}

	public int getDiameter() {
		return diameter;
	}

	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}

	public boolean insideBubble(int x, int y) {

		return (int) Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2)) < this.diameter / 2;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getColorName() {
		return GameColors.colorsStringArr[colorIndex];
	}

}
