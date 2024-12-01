import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Emotion implements Serializable {

	private static final long serialVersionUID = 6670722710687178709L;
	private String name;
	private String definition;

	private ArrayList<String> questionArraylist;

	public Emotion(String name) {
		super();
		this.name = name;
		questionArraylist = new ArrayList<String>();
	}

	public Emotion(String name, String definition) {
		super();
		this.name = name;
		this.definition = definition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public ArrayList<String> getQuestionArraylist() {
		return questionArraylist;
	}

	public void setQuestionArraylist(ArrayList<String> questionArraylist) {
		this.questionArraylist = questionArraylist;
	}

	public void addQuestion(String question) {
		questionArraylist.add(question);
	}

	@Override
	public String toString() {
		return "Emotion [name=" + name + ", definition=" + definition + ", questionArraylist=" + questionArraylist
				+ "]\n";
	}

}