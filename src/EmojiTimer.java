import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class EmojiTimer {
	
	private int delay; // in milliseconds
	private int startTime; // in seconds
	private int currentTime;
	private JPanel controlledPanel;
	
	private ActionListener taskPerformer;
	  
	Timer timer;
	
	
	// c'tor for limiting time of each round of play  
	public EmojiTimer(JPanel controlledPanel) {
		
		this.delay = 1000;
		this.startTime = 11;
		this.currentTime = startTime;
		this.controlledPanel = controlledPanel;
		
		this.taskPerformer = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		    	  currentTime--;
		          System.out.println("remaining time: " + currentTime);
		          
		          // update display time label
		          String newText = "Time: " + currentTime;
		          EmojiBubblesApp.getMainObject().getLblDisplayTime().setText(newText);
		          
		          EmojiBubblesApp.getMainObject().getLblClock().setIcon(new ImageIcon("images/clock.png"));
		          EmojiBubblesApp.getMainObject().validate();
		          
		          
		          if (currentTime <= 0) {
		        	  System.out.println("no time left");
		        	  newText = "";
			          EmojiBubblesApp.getMainObject().getLblDisplayTime().setText(newText);
			          EmojiBubblesApp.getMainObject().getLblClock().setIcon(null);
		        	  
		        	  controlledPanel.removeAll();
		        	  controlledPanel.add(new JLabel(new ImageIcon("images/red.png")), BorderLayout.CENTER);
		        	  controlledPanel.validate();
		        	  
		        	 ((Timer) evt.getSource()).stop();
		          }
		          
		      }
		  };
		
		this.timer = new Timer(this.delay, this.taskPerformer);
		this.timer.setInitialDelay(0);
		
	}
	
	
	// timer for statistic data - duration of play
	public EmojiTimer() {
		
		this.delay = 1000;
		this.startTime = 0;
		this.currentTime = startTime;
		
		this.taskPerformer = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				
				currentTime++;
				//System.out.println("time since launch: " + currentTime);
				
				
			}
		};
		
		this.timer = new Timer(this.delay, this.taskPerformer);
		this.timer.setInitialDelay(0);
	}



	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}


	public int getCurrentTime() {
		return currentTime;
	}


	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}
	
	

}






