import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EmojiCursor {
	
	private String startPath = "images/thecat_";
	private String endPath = ".png";
	
	private int colorIndex;
	private Cursor cursor;

	public EmojiCursor(int colorIndex) {
		super();
		this.colorIndex = colorIndex;
		
		BufferedImage originalImg = null;
		try {
			originalImg = ImageIO.read(new File(startPath + this.colorIndex + endPath));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        // Alpha
        BufferedImage cursorImg = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        
        // Graphics2D                   
        Graphics2D g2d = cursorImg.createGraphics();
        
        // hints                 
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        //                            
        g2d.drawImage(originalImg, 0, 0, 32, 32, null);
        g2d.dispose();
        
        // 
        int hotspotX = originalImg.getWidth() / 2;
        int hotspotY = originalImg.getHeight() / 2;
        Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(
            cursorImg, new Point(hotspotX, hotspotY), "Custom Cursor");
        
        this.cursor = customCursor;
	}

	public Cursor getCursor() {
		return cursor;
	}

	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}
	
	
	
	

}
