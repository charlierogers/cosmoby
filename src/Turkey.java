import screen.Dim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Turkey {

    private double x;
    private double y;
    private double xChange;
    private double yChange;
    private double turkeyFPS;
    private boolean facingEast;
    private boolean visible;
    private boolean hasFood;
    private boolean isDead;
    private boolean goingFast;
    private boolean invincible;
    private String turkeyEFile = "images/turkey7east.GIF";
    private String turkeyWFile = "images/turkey7west.GIF";
    private String cookedTkyFile = "images/turkey5.GIF";
    private String turkeyEinvFile = "images/turkey7eastshield.GIF";
    private String turkeyWinvFile = "images/turkey7westshield.GIF";
    private String turkeyEBigFile = "images/turkey7eastBig.GIF";
    private String turkeyWBigFile = "images/turkey7westBig.GIF";
    private String turkeyEInvBigFile = "images/turkey7eastshieldBig.GIF";
    private String turkeyWInvBigFile = "images/turkey7westshieldBig.GIF";
    private Image turkeyE;
    private Image turkeyW;
    private Image turkeyWInv;
    private Image turkeyEInv;
    private Image turkeyEReg;
    private Image turkeyWReg;
    private Image turkeyWInvReg;
    private Image turkeyEInvReg;
    private Image turkeyEBig;
    private Image turkeyWBig;
    private Image turkeyWInvBig;
    private Image turkeyEInvBig;
    private Image cookedTky;
    private static final double HAS_FOOD_SCALE_FACTOR = 1.3;
    private Timer speedTimer;
    private SpeedTimerTask speedTimerTask;
    private Timer invincibilityTimer;
    private InvincibilityTimerTask invincibilityTimerTask;
    Dim dim;
    //controls
    private boolean rightButton;
    private boolean leftButton;
    private boolean upButton;
    private boolean downButton;

    public Turkey(int width, int height) {
        dim = new Dim(width, height, 1000, 700);
        //get turkey image in all orientations and both scales
        turkeyE = turkeyEReg = new ImageIcon(turkeyEFile).getImage();
        turkeyW = turkeyWReg = new ImageIcon(turkeyWFile).getImage();
        turkeyEInv = turkeyEInvReg = new ImageIcon(turkeyEinvFile).getImage();
        turkeyWInv = turkeyWInvReg = new ImageIcon(turkeyWinvFile).getImage();
        turkeyEBig = new ImageIcon(turkeyEBigFile).getImage();
        turkeyWBig = new ImageIcon(turkeyWBigFile).getImage();
        turkeyEInvBig = new ImageIcon(turkeyEInvBigFile).getImage();
        turkeyWInvBig = new ImageIcon(turkeyWInvBigFile).getImage();
        //get cooked turkey image
        cookedTky = new ImageIcon(cookedTkyFile).getImage();
        putInStartPos();

        turkeyFPS = dim.adjWDouble(2);    
        
        visible = true;

        goingFast = false;
        invincible = false;
    }

    public void putInStartPos() {
        x = dim.adjW(5);
        y = dim.adjH(30);
        facingEast = true;
        setHasFood(false);
        isDead = false;
    }
    public void move(int winWidth, int winHeight) {

        //can't move when dead
        if (isDead())
            return;
        
        //see if turkey is done going fast
        if (speedTimerTask != null) {
            if (speedTimerTask.goingFast() == false) {
                goingFast = false;
                speedTimerTask.setGoingFast(true);
                speedTimer.purge();
                speedTimer.cancel();
            }
        }
        
        //see if turkey is done being invincible
        if (invincibilityTimerTask != null) {
            if (invincibilityTimerTask.isInvincible() == false) {
                invincible = false;
                invincibilityTimerTask.setInvincible(true);
                invincibilityTimer.purge();
                invincibilityTimer.cancel();
            }
        }
        
        
        if (rightButton && leftButton) {
            xChange = 0;
        }
        if (upButton && downButton) {
            yChange = 0;
        }
        
        if (x <= 0) {
            x = 1;
        }
        if (y <= 25) {
            y = 26;
        }
        if (x >= winWidth - getWidth() - 5) {
            x = (winWidth - 1) - getWidth() - 5;
        }
        if (y >= (winHeight) - getHeight()/2) {
            y = (winHeight) - getHeight()/2;
        }

        if (goingFast) {
            x += xChange * 2;
            y += yChange * 2;
        } else {
            x += xChange;
            y += yChange;
        }
    }
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return getImage().getWidth(null);
    }

    public double getHeight() {
        return getImage().getHeight(null);
    }
    
    public Image getImage() {

        if (isDead)
            return cookedTky;

        if (facingEast) {
            if (invincible) {
                return turkeyEInv;
            } else { 
                return turkeyE;
            }
        } else {
            if (invincible) {
                return turkeyWInv;
            } else {
                return turkeyW;
            }
        }
    }
    
    public Rectangle makeRectangle() {
        if (facingEast) {
            return new Rectangle((int) Math.round(x + .25 * getWidth()), (int) Math.round(y + .35 * getHeight()),
                    (int) Math.round(.7 * getWidth()), (int) Math.round(.45 * getHeight()));
        } else {
            return new Rectangle((int) Math.round(x + .05 * getWidth()), (int) Math.round(y + .35 * getHeight()),
                    (int) Math.round(.7 * getWidth()), (int) Math.round(.45 * getHeight()));
        }
    }
    
    public void setVisible(boolean vState) {
        visible = vState;
    }
    
    public void setGoingFast(boolean state) {
        goingFast = state;
        if (goingFast) {
            speedTimer = new Timer();
            speedTimerTask = new SpeedTimerTask();
            speedTimer.schedule(speedTimerTask, 5000);
        }
    }
    
    public void setInvincible(boolean state) {
        invincible = state;
        if (invincible) {
            invincibilityTimer = new Timer();
            invincibilityTimerTask = new InvincibilityTimerTask();
            invincibilityTimer.schedule(invincibilityTimerTask, 5000);
        }
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setHasFood(boolean state) {
        hasFood = state;
        if (state) {
            turkeyE = turkeyEBig;
            turkeyW = turkeyWBig;
            turkeyEInv = turkeyEInvBig;
            turkeyWInv = turkeyWInvBig;
        } else {
            turkeyE = turkeyEReg;
            turkeyW = turkeyWReg;
            turkeyEInv = turkeyEInvReg;
            turkeyWInv = turkeyWInvReg;
        }
    }

    public boolean hasFood() {
        return hasFood;
    }

    public void die() {
        isDead = true;
        setHasFood(false);
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isVisible() {
        return visible;
    }
    
    private class SpeedTimerTask extends TimerTask {
        
        private boolean goingFast;
        
        public SpeedTimerTask() {
            goingFast = true;
        }
        
        @Override
        public void run() {
            goingFast = false;
        }
        
        public boolean goingFast() {
            return goingFast;
        }
        
        public void setGoingFast(boolean state) {
            goingFast = state;
        }
    }
    
    public class InvincibilityTimerTask extends TimerTask {
        private boolean invincible;
        
        public InvincibilityTimerTask() {
            invincible = true;
        }
        
        @Override
        public void run() {
            invincible = false;
        }
        
        public void setInvincible(boolean state) {
            invincible = state;
        }
        
        public boolean isInvincible() {
            return invincible;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        char kChar = e.getKeyChar();

        if ((key == KeyEvent.VK_RIGHT) || (kChar == 'd')) {
            xChange = turkeyFPS;
            rightButton = true;
            facingEast = true;
        }
        if ((key == KeyEvent.VK_LEFT) || (kChar == 'a')) {
            xChange = -turkeyFPS;
            leftButton = true;
            facingEast = false;
        }
        if ((key == KeyEvent.VK_DOWN) || (kChar == 's')) {
            yChange = turkeyFPS;
            downButton = true;
        }
        if ((key == KeyEvent.VK_UP) || (kChar == 'w')) {
            yChange = -turkeyFPS;
            upButton = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        char kChar = e.getKeyChar();

        if ((key == KeyEvent.VK_DOWN) || (kChar == 's')) {
            if (upButton) {
                yChange = -turkeyFPS;
            } else {
                yChange = 0;
            }
            downButton = false;
        }
        
        if ((key == KeyEvent.VK_UP) || (kChar == 'w')) {
            if (downButton) {
                yChange = turkeyFPS;
            } else {
                yChange = 0;
            }
            upButton = false;
        }

        if ((key == KeyEvent.VK_LEFT) || (kChar == 'a')) {
            if (rightButton) {
                xChange = turkeyFPS;
                facingEast = true;
            } else {
                xChange = 0;
            }
            leftButton = false;
        }

        if ((key == KeyEvent.VK_RIGHT) || (kChar == 'd')) {
            if (leftButton) {
                xChange = -turkeyFPS;
                facingEast = false;
            } else {
                xChange = 0;
            }
            rightButton = false;
        }
    }
}