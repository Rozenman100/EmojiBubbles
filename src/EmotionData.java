import java.io.Serializable;
import java.util.ArrayList;

public class EmotionData implements Serializable{
	
	
	private static final long serialVersionUID = 6796116846866299804L;
	private ArrayList<EmotionResult> emotionResultArraylist;

	public EmotionData() {
		emotionResultArraylist=new ArrayList<EmotionResult>();
	}

	public ArrayList<EmotionResult> getEmotionResultArraylist() {
		return emotionResultArraylist;
	}

	public void setEmotionResultArraylist(ArrayList<EmotionResult> emotionResultArraylist) {
		this.emotionResultArraylist = emotionResultArraylist;
	}
	
	public void addEmotionResult(EmotionResult emotionResult) {
		emotionResultArraylist.add(emotionResult);
	}

	@Override
	public String toString() {
		return "EmotionData [emotionResultArraylist=" + emotionResultArraylist + "]";
	}
	
	

}
