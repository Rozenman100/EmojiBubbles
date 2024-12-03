import java.io.Serializable;
import java.util.ArrayList;

public class EmotionResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3534316621432656864L;

	private String name;
	
	private ArrayList<Emotion> emotionsArraylist;

	public EmotionResult(String name) {
		super();
		this.name = name;
		emotionsArraylist=new ArrayList<Emotion>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Emotion> getEmotionsArraylist() {
		return emotionsArraylist;
	}

	public void setEmotionsArraylist(ArrayList<Emotion> emotionsArraylist) {
		this.emotionsArraylist = emotionsArraylist;
	}

	@Override
	public String toString() {
		return "EmotionResult [name=" + name + ", emotionsArraylist=" + emotionsArraylist + "]";
	}
	
	

}
