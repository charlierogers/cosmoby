import image.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BabyTky {
    
    private boolean visible;
    private int x;
    private int y;
    private Image babyTky;
    private String tkyImageFile = "images/Noseless_Mober.png";
    
    public BabyTky (int x, int y) {
        this.x = x;
        this.y = y;
        babyTky = new ImageIcon(tkyImageFile).getImage();
        visible = true;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public Image getImage() {
        
        return babyTky;
    }
    
    public Rectangle makeRectangle() {
        return new Rectangle(x, y, babyTky.getWidth(null), babyTky.getHeight(null));
    }
    
    public void setVisible(boolean vState) {
        visible = vState;
    }
    
    public boolean isVisible() {
        return visible;
    }
}