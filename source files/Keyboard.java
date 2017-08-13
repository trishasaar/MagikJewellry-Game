//Name: Yashvi Shah, Trisha Saar
//Date: June 4, 2013
//Purpose: Handle keyboard user input and fire actions accordingly

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	
	private final int UP = 0;
	private final int DOWN = 1;
	private final int LEFT = 2;
	private final int RIGHT = 3;
	private final int ENTER = 4;
	private final int SPACE = 5;
	private final int cKEY = 6;
	private final int bKEY = 7;
	private final int pKEY = 8;
	private final int oneKEY = 9;
	private final int twoKEY = 10;
	private final int threeKEY = 11;
	private final int fourKEY = 12;
	private final int fiveKEY = 13;
	private boolean[] keysPressed = new boolean[14];
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			getKeysPressed()[LEFT] = true;
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			getKeysPressed()[RIGHT] = true;
		else if(e.getKeyCode() == KeyEvent.VK_UP)
			getKeysPressed()[UP] = true;
		else if(e.getKeyCode() == KeyEvent.VK_DOWN)
			getKeysPressed()[DOWN] = true;
		else if(e.getKeyCode() == KeyEvent.VK_SPACE)
			getKeysPressed()[SPACE] = true;
		else if(e.getKeyCode() == KeyEvent.VK_ENTER)
			getKeysPressed()[ENTER] = true;
		else if(e.getKeyCode() == KeyEvent.VK_C)
			getKeysPressed()[cKEY] = true;
		else if(e.getKeyCode() == KeyEvent.VK_B)
			getKeysPressed()[bKEY] = true;
		else if(e.getKeyCode() == KeyEvent.VK_P)
			getKeysPressed()[pKEY] = true;
		else if(e.getKeyCode() == KeyEvent.VK_1)
			getKeysPressed()[oneKEY] = true;
		else if(e.getKeyCode() == KeyEvent.VK_2)
			getKeysPressed()[twoKEY] = true;
		else if(e.getKeyCode() == KeyEvent.VK_3)
			getKeysPressed()[threeKEY] = true;
		else if(e.getKeyCode() == KeyEvent.VK_4)
			getKeysPressed()[fourKEY] = true;
		else if(e.getKeyCode() == KeyEvent.VK_5)
			getKeysPressed()[fiveKEY] = true;
	}
	
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			getKeysPressed()[LEFT] = false;
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			getKeysPressed()[RIGHT] = false;
		else if(e.getKeyCode() == KeyEvent.VK_UP)
			getKeysPressed()[UP] = false;
		else if(e.getKeyCode() == KeyEvent.VK_DOWN)
			getKeysPressed()[DOWN] = false;
		else if(e.getKeyCode() == KeyEvent.VK_SPACE)
			getKeysPressed()[SPACE] = false;
		else if(e.getKeyCode() == KeyEvent.VK_ENTER)
			getKeysPressed()[ENTER] = false;
		else if (e.getKeyCode() == KeyEvent.VK_C)
			getKeysPressed()[cKEY] = false;
		else if(e.getKeyCode() == KeyEvent.VK_B)
			getKeysPressed()[bKEY] = false;
		else if(e.getKeyCode() == KeyEvent.VK_P)
			getKeysPressed()[pKEY] = false;
		else if(e.getKeyCode() == KeyEvent.VK_1)
			getKeysPressed()[oneKEY] = false;
		else if(e.getKeyCode() == KeyEvent.VK_2)
			getKeysPressed()[twoKEY] = false;
		else if(e.getKeyCode() == KeyEvent.VK_3)
			getKeysPressed()[threeKEY] = false;
		else if(e.getKeyCode() == KeyEvent.VK_4)
			getKeysPressed()[fourKEY] = false;
		else if(e.getKeyCode() == KeyEvent.VK_5)
			getKeysPressed()[fiveKEY] = false;
	}
	
	public void keyTyped(KeyEvent e) {	}
	
	
	//getters 
	public int getUP()
	{
		return UP;
	}
	public int getDOWN()
	{
		return DOWN;
	}
	public int getLEFT()
	{
		return LEFT;
	}
	public int getRIGHT()
	{
		return RIGHT;
	}
	public int getENTER()
	{
		return ENTER;
	}
	public int getSPACE()
	{
		return SPACE;
	}	
	public int getcKEY(){
		return cKEY;
	}
	public int getbKEY(){
		return bKEY;
	}
	public int getOneKEY(){
		return oneKEY;
	}
	public int getTwoKEY(){
		return twoKEY;
	}
	public int getThreeKEY(){
		return threeKEY;
	}
	public int getFourKEY(){
		return fourKEY;
	}
	public int getFiveKEY(){
		return fiveKEY;
	}
	public int getpKEY(){
		return pKEY;
	}

	public boolean[] getKeysPressed() {
		return keysPressed;
	}


	public void setKeysPressed(boolean[] keysPressed) {
		this.keysPressed = keysPressed;
	}
}
