import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EmotionChooseColorsPanel extends JPanel {

	private JPanel pnlMiddle, pnlleft;
	private EmojiLabel playerEmotionLabel;
	private JLabel lblSpinning, lblEmotionArr[];
	
	private JPanel mainClassPnlMiddle;

	public EmotionChooseColorsPanel(EmojiLabel playerEmotionLabel, JPanel mainClassPnlMiddle) {
		this.mainClassPnlMiddle = mainClassPnlMiddle;
		this.playerEmotionLabel = playerEmotionLabel;
		this.setOpaque(true);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);

		// middle panel
		pnlMiddle = new JPanel(new BorderLayout());
		lblSpinning = new JLabel(new ImageIcon(EmojiBubblesApp.imagesPath + "spinning" + ".gif"));
		pnlMiddle.add(lblSpinning, BorderLayout.CENTER);
		
		
		int emotionsCounter = EmojiBubblesApp.emotionData.getEmotionResultArraylist().size();

		
		lblSpinning.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				pnlMiddle.removeAll();
				
				int randomEmotionIndex=(int)(Math.random()*emotionsCounter);
				
				pnlMiddle.add(new EmotionConversationPanel(playerEmotionLabel,  // sent player label
						EmojiBubblesApp.emotionData.getEmotionResultArraylist().get(randomEmotionIndex).getName(), mainClassPnlMiddle, randomEmotionIndex)); // and emotion name
			}
		});
		

		// left panel
		pnlleft = new JPanel(new GridLayout(0, 1));
		System.out.println("emotionsCounter: " + emotionsCounter);
		lblEmotionArr = new JLabel[emotionsCounter];

		for (int i = 0; i < emotionsCounter; i++) {
			// create emotion jlabel with the right image (by emotion name)
			lblEmotionArr[i] = new JLabel(new ImageIcon(EmojiBubblesApp.imagesPath
					+ EmojiBubblesApp.emotionData.getEmotionResultArraylist().get(i).getName() + ".png"));
			lblEmotionArr[i].setToolTipText(EmojiBubblesApp.emotionData.getEmotionResultArraylist().get(i).getName());
			System.out.println(EmojiBubblesApp.imagesPath
					+ EmojiBubblesApp.emotionData.getEmotionResultArraylist().get(i).getName() + ".png");
			
			pnlleft.add(lblEmotionArr[i]);
			
			// press the emotion image and move to conversation section
			lblEmotionArr[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					
					
					// get the index in the array of the clicked label
					int clickedIndex = -1;
					JLabel clickedlabel = (JLabel) e.getSource();
					int j;
					for (j = 0; j < lblEmotionArr.length; j++) {
						if (clickedlabel == lblEmotionArr[j]) {
							clickedIndex = j;
							break;
						}
					}
					
					//EmotionTalkApp.getMainObject().getStatisticData().
					
					//change cursor color
					setCursor(new EmojiCursor(clickedIndex).getCursor());
					
					pnlMiddle.removeAll();
					validate();
					repaint();
					pnlMiddle.add(new EmotionConversationPanel(playerEmotionLabel,  // sent player label
							((JLabel)e.getSource()).getToolTipText(), mainClassPnlMiddle, clickedIndex), BorderLayout.CENTER); // and emotion name
					System.out.println("emotion send: "+((JLabel)e.getSource()).getToolTipText());
					
					System.out.println("clicked index: " + clickedIndex);
					
					validate();
				}
			});

		}

		this.add(pnlMiddle, BorderLayout.CENTER);
		this.add(pnlleft, BorderLayout.WEST);

		pnlMiddle.setBackground(Color.white);
		pnlleft.setBackground(Color.white);

	}

}
