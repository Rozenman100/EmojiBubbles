import java.awt.Color;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StatisticData implements Serializable {
	
	private int playerCounter;
	private Map<Color, Integer> colorUsageCounter;
	private int durationOfPlay;
	private Date date;
	
	public StatisticData() {
		
		this.colorUsageCounter = new HashMap<Color, Integer>();
		
	}

	public int getPlayerCouner() {
		return playerCounter;
	}

	public void setPlayerCouner(int playerCouner) {
		this.playerCounter = playerCouner;
	}
	
	
	
	public Map<Color, Integer> getColorUsageCounter() {
		return colorUsageCounter;
	}

	public void setColorUsageCounter(Map<Color, Integer> colorUsageCounter) {
		this.colorUsageCounter = colorUsageCounter;
	}

	public void addToColorCounter(Color c) {
		
		if (colorUsageCounter.containsKey(c))
			this.colorUsageCounter.put(c, colorUsageCounter.get(c) + 1);
		else
			this.colorUsageCounter.put(c, 1);
	}

	public int getPlayerCounter() {
		return playerCounter;
	}

	public void setPlayerCounter(int playerCounter) {
		this.playerCounter = playerCounter;
	}

	public int getDurationOfPlay() {
		return durationOfPlay;
	}

	public void setDurationOfPlay(int durationOfPlay) {
		this.durationOfPlay = durationOfPlay;
	}
	
	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "StatisticData [playerCounter=" + playerCounter + ", colorUsageCounter=" + colorUsageCounter
				+ ", durationOfPlay=" + durationOfPlay + "]";
	}
	
	
	
	

}
