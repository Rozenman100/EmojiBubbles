import java.io.Serializable;
import java.util.ArrayList;

public class FeedbackData implements Serializable{
    
    private boolean enjoyment;
    private int playersNumber;
    private float averageAge;
    private String notes;
    
   
 public FeedbackData(boolean enjoyment, int playersNumber, float averageAge, String notes) {
		super();
		this.enjoyment = enjoyment;
		this.playersNumber = playersNumber;
		this.averageAge = averageAge;
		this.notes = notes;
	}


public boolean isEnjoyment() {
	return enjoyment;
}


public void setEnjoyment(boolean enjoyment) {
	this.enjoyment = enjoyment;
}


public int getPlayersNumber() {
	return playersNumber;
}


public void setPlayersNumber(int playersNumber) {
	this.playersNumber = playersNumber;
}


public float getAverageAge() {
	return averageAge;
}


public void setAverageAge(float averageAge) {
	this.averageAge = averageAge;
}


public String getNotes() {
	return notes;
}


public void setNotes(String notes) {
	this.notes = notes;
}


@Override
public String toString() {
	return "FeedbackData [enjoyment=" + enjoyment + ", playersNumber=" + playersNumber + ", averageAge=" + averageAge
			+ ", notes=" + notes + "]";
}

	
   

   

   
    
}