import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

public class FeedbackFrame extends JFrame implements ActionListener {

	private JPanel mainPanel, agePanel, enjoyGamePanel, pnlSuggestions, form;
	private Border enjoyBorder, ageBorder, suggestionsBorder;

	private int[] ages;
	private ArrayList<JComboBox<String>> agesArr;

	private JRadioButton yesButton, noButton;
	private ButtonGroup enjoyButtonGroup;
	private JTextArea suggestions;
	private JButton submitButton;

	private int numberofPlayers;
	private FeedbackData feedBackData;
	private Client client;

	public FeedbackFrame(int numberofPlayers, Client client) {
		super("FeedBack Frame");
		this.numberofPlayers = numberofPlayers;
		this.client = client;

		mainPanel = new JPanel(new BorderLayout());

		form = new JPanel(new BorderLayout());

		// 1. Did you enjoy the game? (Yes/No)
		enjoyGamePanel = new JPanel();
		enjoyBorder = BorderFactory.createTitledBorder("Enjoy?");
		enjoyGamePanel.setBorder(enjoyBorder);
		enjoyGamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		enjoyGamePanel.add(new JLabel("Did you enjoy the game?"));

		yesButton = new JRadioButton("Yes");
		noButton = new JRadioButton("No");

		// Grouping radio buttons
		enjoyButtonGroup = new ButtonGroup();
		enjoyButtonGroup.add(yesButton);
		enjoyButtonGroup.add(noButton);

		enjoyGamePanel.add(yesButton);
		enjoyGamePanel.add(noButton);
		form.add(enjoyGamePanel, BorderLayout.NORTH);

		// 2. ages of players

		agePanel = new JPanel(new FlowLayout());
		ageBorder = BorderFactory.createTitledBorder("Age of Players:");
		agePanel.setBorder(ageBorder);
		agesArr = new ArrayList<JComboBox<String>>();

		// create ages for comboBoxs
		String ages[] = new String[100];
		for (int i = 0; i < ages.length; i++)
			ages[i] = "" + i;

		// add ages to comboboxs
		for (int i = 0; i < numberofPlayers; i++) {
			agesArr.add(new JComboBox<String>(ages));
			agePanel.add(agesArr.get(i));
		}

		form.add(agePanel, BorderLayout.CENTER);

		// suggestions
		pnlSuggestions = new JPanel(new BorderLayout());
		suggestionsBorder = BorderFactory.createTitledBorder("Suggestions:");
		pnlSuggestions.setBorder(suggestionsBorder);
		suggestions = new JTextArea(5, 50);
		pnlSuggestions.add(suggestions, BorderLayout.CENTER);

		form.add(pnlSuggestions, BorderLayout.SOUTH);

		mainPanel.add(form, BorderLayout.NORTH);

		// submit button
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		mainPanel.add(submitButton, BorderLayout.SOUTH);

		setContentPane(mainPanel);
		setLocationRelativeTo(null);
		pack();
		setVisible(true);

	}

	private ArrayList<String> convertCombo2Ages() {
		ArrayList<String> strArr = new ArrayList<String>(agesArr.size());

		for (int i = 0; i < agesArr.size(); i++)
			strArr.add(agesArr.get(i).getSelectedIndex() + "");

		return strArr;
	}

	public static void main(String args[]) {

		// new FeedBackFrame(7);
	}

	private float averageAges() {
		float average = 0.0f;
		int sum = 0;

		for (int i = 0; i < agesArr.size(); i++)
			sum += agesArr.get(i).getSelectedIndex();

		return sum / (float) agesArr.size();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		feedBackData = new FeedbackData(yesButton.isSelected() ? true : false, agesArr.size(), averageAges(),
				suggestions.getText());
		System.out.println(feedBackData);

		// send feedback to server
		client.writeFeedbackDataServer(feedBackData);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.exit(0);

	}

}
