//Name: Trisha Saar, Yashvi Shah
//Date: June 4, 2013
//Purpose: To create blocks of various colours and shapes at different positions
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextAttribute;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.AttributedString;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.Vector;
import javazoom.jl.player.*;
import java.io.*;
import java.lang.reflect.Array;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import javazoom.jl.player.Player;

public class Game extends Canvas implements Runnable{
	
	//screen dimensions and variables
	static final int WIDTH = 1024;
	static final int HEIGHT = WIDTH / 3 * 2; //4:3 aspect ratio
	private JFrame frame;


	//game updates per second
	static final int UPS = 60;

	//variables for the thread
	private Thread thread;
	private boolean running;

	//used for drawing items to the screen
	private Graphics2D graphics;

	//keyboard variable
	private Keyboard k;
	
	//game states
	private int gameState;
	private final int GAME_MAIN_MENU = 0;  
	private final int GAME_PLAY = 1;    
	private final int GAME_HELP = 2;
	private final int GAME_STARTSCREEN = 3;
	private final int GAME_QUIT = 4;
	private final int GAME_PAUSE = 5;
	private final int GAME_QUITSCREEN = 6;
	private final int GAME_RATE = 7;

	//startscreen variables
	private long colourCount; //to change the colour of "MAGIK JEWELRY" in startScreen
	private Block square;
	private int squareY; //y position on the falling blocks
	
	//button variables
	private final int PLAY_BUTTON = 0;
	private final int HELP_BUTTON = 2;
	private final int QUIT_BUTTON = 1;
	private final int YES_BUTTON = 4;
	private final int NO_BUTTON = 5;
	private final int RESUME_BUTTON = 6;
	private final int PAUSE_QUIT_BUTTON = 7;
	private final int GAME_LOST = 8;
	private int currentPauseButton; //current button in the pause state
	private int currentButton;
	private int currentQuitButton; //current button in the quit screen
	
	//block velocity variables
	private int velX;
	private int velY;
	private int velocityIncrease; // natural increase, without the user pressing a key
	
	//BLOCK ARRAY
	private int[] nextBlockSet;
	private Block[] blockSet;
	private final int BLOCK1 = 0, BLOCK2 = 1, BLOCK3 = 2;
	
	//to draw new blocks
	private boolean reachTheBottom;
	
	//Game Array
	private int[][] gameGrid;
	private int gridY, gridX;
	boolean firstPass;//to allow two sets of blocks to generate the first time game is run; one set will be on the game grid, while the other is on the side.  
	private int updateCounter; //WHAT IS THIS? TRISHAAAA
	private int yCounter; //To make sure the blocks have passed a certain element before gridX is incremented.
	private boolean threeInARow;
	
	//SCORE and LEVELLING
	private int score;
	private int level;
	private boolean levelUp; //to check whether the score has passed a certain number
	
	//generating random blocks
	private Random random;
	private final int BLUE = 1;
	private final int GREEN = 2;
	private final int YELLOW = 3;
	private final int ORANGE = 4;
	private final int RED = 5;
	
	//initialize game objects, load media(pics, music, etc)
	private static BufferedImage picture;
	private Player player;
	private FileInputStream file;
	
	//GAMESHOW PERKS
	private int gamesPlayed = 0;
	private int[] highscores = new int[10];
	private Scanner fileInput;
	
	public void init() {
		gameState = GAME_STARTSCREEN;		
		initializeButtonStates();
		initializeStartscreenVariables();
		initializeBlockVelocityVariables();
		initializeBlockSetArrays();
		initializeGameGridVaribles();
		initializeScoreAndLevels();
		playMusic();
	}
	
	public void initializeButtonStates(){
		
		currentButton = PLAY_BUTTON;
		currentQuitButton = NO_BUTTON;
		currentPauseButton = RESUME_BUTTON;
	}
	
	public void initializeStartscreenVariables(){
		colourCount = 1;
		squareY = 0;
		square = new Block(20, squareY ,5);
	}
	public void initializeBlockVelocityVariables(){
		velocityIncrease = 1;
		velX = 75;
		velY = 8;
	}
	public void initializeBlockSetArrays(){
		blockSet = new Block[3];
		blockSet[BLOCK1] = new Block(375,0,0);
		blockSet[BLOCK2] = new Block(375,40,0);
		blockSet[BLOCK3] = new Block(375,80,0);
		nextBlockSet = new int[3];
	}
	
	public void initializeGameGridVaribles(){
		reachTheBottom = true;
		random = new Random();
		gameGrid = new int[17][10];   
		gridY = 0;
		gridX = 0;
		updateCounter = 0;
		firstPass = true;
		yCounter = 80;
		threeInARow = false;
	}
	public void initializeScoreAndLevels(){
		score = 0;
		level = 1;
		levelUp = false;
	}
	
	public void playMusic(){
		try {
		    file =  new FileInputStream("BackgroundMusic.mp3");
		    player = new Player(file);
		}
		catch(Exception e) {}
		    
		new Thread() {
		    public void run() {
		        try {
		            player.play();
		        } catch (Exception e) { System.out.println(e); }
		    }
		}.start();
	}
		
	public Game() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		System.out.println(HEIGHT);
		frame = new JFrame();
		k = new Keyboard();
		addKeyListener(k);
	}
	
	//update game objects
	public void update() {
		if(gameState == GAME_STARTSCREEN){
			updateStartscreenColourCount();
			updateStartscreen();
		}
		else if(gameState == GAME_MAIN_MENU)
			updateMenuButtons();
		//when the user tries to go back by pressing the 'b' key
		else if((gameState == GAME_HELP ) && k.getKeysPressed()[k.getbKEY()] )
			gameState = GAME_MAIN_MENU;
		else if(gameState == GAME_PLAY)
			updateGame();
		else if(gameState == GAME_QUITSCREEN)
			updateQuitButtons();
		else if(gameState == GAME_PAUSE)
			updatePauseButtons();
		else if(gameState == GAME_LOST)
			updateLoseScreen();
		else if(gameState == GAME_RATE)
			updateRateScreen();
	}	
	public void updateStartscreen(){
		square.setY(square.getY() + 2); //to keep the blocks moving
		if(square.getY() > HEIGHT)
			square.setY(0);				//to keep them falling from the top once they reach the bottom
		if(k.getKeysPressed()[k.getcKEY()])
			gameState = GAME_MAIN_MENU;
	}
	public void updateStartscreenColourCount(){
		//to assign different colours quickly, according to loop iterations 
		for(long i = 0; i <= 1000000; i++){	
			if( i == 1000000){
				if(colourCount == 4)
					colourCount = 1;
				else
					colourCount++;
			}
		}
	}
	
	public void updateMenuButtons(){
		if(currentButton == PLAY_BUTTON)
			updatePlayButton();
		else if(currentButton == QUIT_BUTTON)
			updateQuitButton();
		else if(currentButton == HELP_BUTTON)
			updateHelpButton();
	}
	
	public void updatePlayButton(){
		if((k.getKeysPressed()[k.getRIGHT()])){
			currentButton = HELP_BUTTON;
			k.getKeysPressed()[k.getRIGHT()] = false; //to avoid repetition of the action
		}
		else if(k.getKeysPressed()[k.getENTER()]){
			gamesPlayed++;
			gameState = GAME_PLAY;
			k.getKeysPressed()[k.getENTER()] = false;
		}
		else if(k.getKeysPressed()[k.getDOWN()]){
			currentButton = QUIT_BUTTON;
			k.getKeysPressed()[k.getDOWN()] = false;
		}
	}
	
	public void updateHelpButton(){
		if(k.getKeysPressed()[k.getDOWN()])
			currentButton = QUIT_BUTTON;
		else if((k.getKeysPressed()[k.getLEFT()]))
			currentButton = PLAY_BUTTON;
		else if (k.getKeysPressed()[k.getENTER()])
			gameState = GAME_HELP;
	}
	
	public void updateQuitButton(){
		if(k.getKeysPressed()[k.getLEFT()])
			currentButton = PLAY_BUTTON;
		else if(k.getKeysPressed()[k.getUP()])
			currentButton = PLAY_BUTTON;
		else if(k.getKeysPressed()[k.getENTER()]){
			gameState = GAME_QUITSCREEN;
			k.getKeysPressed()[k.getENTER()] = false;
		}
		else if(k.getKeysPressed()[k.getRIGHT()]){
			currentButton = HELP_BUTTON;
			k.getKeysPressed()[k.getENTER()] = false;
		}
	}
	public void updateGame(){ //updates game components
		updateBlocks();
		if (reachTheBottom == true)
			generateNextBlockSet(); 
		updateGameGrid();
		updateLevel();
		checkForGamePause();
	}
	public void updateBlocks() { //generates new blocks
		if (firstPass == true)
			generateNextBlockSet();
		if (reachTheBottom || updateCounter <= 1){
			int yCoordinate = 0;
			for (int i = 0; i < 3; i++){
				if (nextBlockSet[i] == RED)
					setRed(i, yCoordinate);
				else if (nextBlockSet[i] == BLUE)
					setBlue(i, yCoordinate);
				else if (nextBlockSet[i] == GREEN)
					setGreen(i, yCoordinate);
				else if (nextBlockSet[i] == YELLOW)
					setYellow(i, yCoordinate);
				else if (nextBlockSet[i] == ORANGE)
					setOrange(i, yCoordinate);
				updateCounter++;
				yCoordinate += 40;
			}
		}
		checkThreeInARow();
	}
	
	public void generateNextBlockSet(){
		for (int i = 0; i < 3; i++)
			nextBlockSet[i] = getRandomNumber();
	}
	//Methods initialize new blocks depending on shape
	public void setRed(int i, int yCoordinate){
		blockSet[i].setX(375);
		blockSet[i].setY(yCoordinate);
		blockSet[i].setShape(RED);
	}
	
	public void setBlue(int i, int yCoordinate){
		blockSet[i].setX(375);
		blockSet[i].setY(yCoordinate);
		blockSet[i].setShape(BLUE);
	}
	
	public void setGreen(int i, int yCoordinate){
		blockSet[i].setX(375);
		blockSet[i].setY(yCoordinate);
		blockSet[i].setShape(GREEN);
	}
	
	public void setYellow(int i, int yCoordinate){
		blockSet[i].setX(375);
		blockSet[i].setY(yCoordinate);
		blockSet[i].setShape(YELLOW);
	}
	
	public void setOrange(int i, int yCoordinate){
		blockSet[i].setX(375);
		blockSet[i].setY(yCoordinate);
		blockSet[i].setShape(ORANGE);
	}
	
	public int getRandomNumber(){
		return random.nextInt(5) + 1;
	}

	public void updateGameGrid() {//shapes are stored as ints into the game grid when the current blockSet hits the ground
		firstPass = false;
		if (reachTheBottom)
			resetGameGridVariables();
		boolean bottomElement = checkBottomElement(gridY, gridX), rightElement = checkRightElement(gridY, gridX), leftElement = checkLeftElement(gridY, gridX);
		if (bottomElement)
			if (gridY < 16)
				moveBlocks();
		if (reachTheBottom == false && k.getKeysPressed()[k.getRIGHT()] && rightElement)
			goRight(gridX, gridY);
		if (reachTheBottom == false && k.getKeysPressed()[k.getLEFT()] && leftElement)
			goLeft(gridY, gridX);
		if (k.getKeysPressed()[k.getSPACE()])
			reorderShapes();
		if (gridY >= 16 || bottomElement == false)
			addBlocksToGrid();
		resetGameGrid();
		boolean filledGrid = checkForFilledGrid();
		if(filledGrid){
			gameState = GAME_LOST;
		}
	}
	
	public void resetGameGridVariables(){
		gridX = 5;
		gridY = 2;
		reachTheBottom = false;
		yCounter = 120;
	}
	
	public boolean checkBottomElement(int x, int y){
		if (x < 16 && gameGrid[x+1][y] == 0 && blockSet[BLOCK3].getY() + velY <= (HEIGHT))
			return true;
		else
			return false;
	}
	
	public boolean checkRightElement(int x, int y){
		if (y < 9 && gameGrid[x][y+1] == 0)
			return true;
		else
			return false;
	}
	
	public boolean checkLeftElement(int x, int y){
		if (y >= 1 && gameGrid[x][y-1] == 0)
			return true;
		else
			return false;
	}
	
	public void moveBlocks(){//moves the blocks down constantly
		blockSet[BLOCK1].setY(blockSet[BLOCK1].getY() + velocityIncrease); //to keep it moving even if the user doesn't press a key
		blockSet[BLOCK2].setY(blockSet[BLOCK2].getY() + velocityIncrease);
		blockSet[BLOCK3].setY(blockSet[BLOCK3].getY() + velocityIncrease);
		if(k.getKeysPressed()[k.getDOWN()]){
			blockSet[BLOCK1].setY(blockSet[BLOCK1].getY() + velY); 
			blockSet[BLOCK2].setY(blockSet[BLOCK2].getY() + velY);
			blockSet[BLOCK3].setY(blockSet[BLOCK3].getY() + velY);
			k.getKeysPressed()[k.getDOWN()] = false;
		}
		if (blockSet[BLOCK3].getY() >= yCounter && gridY < 16){
			gridY++;
			yCounter += 40;
		}
	}
	
	public void goLeft(int x, int y){
		gridX--;
		blockSet[BLOCK1].setX(blockSet[BLOCK1].getX() - velX); 
		blockSet[BLOCK2].setX(blockSet[BLOCK2].getX() - velX);
		blockSet[BLOCK3].setX(blockSet[BLOCK3].getX() - velX);
		k.getKeysPressed()[k.getLEFT()] = false;
	}
	
	public void goRight(int x, int y){
		gridX++;
		blockSet[BLOCK1].setX(blockSet[BLOCK1].getX() + velX); 
		blockSet[BLOCK2].setX(blockSet[BLOCK2].getX() + velX);
		blockSet[BLOCK3].setX(blockSet[BLOCK3].getX() + velX);
		k.getKeysPressed()[k.getRIGHT()] = false;
	}
	
	public void reorderShapes(){
		int shape1, shape2, shape3;
		shape1 = blockSet[BLOCK1].getShape();
		shape2 = blockSet[BLOCK2].getShape();
		shape3 = blockSet[BLOCK3].getShape();
		blockSet[BLOCK1].setShape(shape3);
		blockSet[BLOCK2].setShape(shape1);
		blockSet[BLOCK3].setShape(shape2);
		k.getKeysPressed()[k.getSPACE()] = false;
	}
	
	public void addBlocksToGrid(){
		reachTheBottom = true;
		gameGrid[gridY - 2][gridX] = blockSet[BLOCK1].getShape();
		gameGrid[gridY - 1][gridX] = blockSet[BLOCK2].getShape();
		gameGrid[gridY][gridX] = blockSet[BLOCK3].getShape();
	}
	
	public void checkThreeInARow(){
		for (int i = 0; i < gameGrid.length; i++){
			for (int j = 0; j < gameGrid[1].length; j++){
				int currentShape = gameGrid[i][j];
				if (currentShape != 0){
					checkHorizontal(currentShape, i, j);
					checkVertical(currentShape, i, j);
					checkTopDiagonal(currentShape, i, j);
					checkBottomDiagonal(currentShape, i, j);
					checkMiddleDiagonal(currentShape, i, j);
				}
				if (threeInARow)
					resetGameGrid();
			}
		}
	}
	//Following methods cancel out blocks if three are present in a row
	public void checkHorizontal(int currentShape, int i, int j){
		checkRightHorizontal(currentShape, i, j);
		checkLeftHorizontal(currentShape, i, j);
		checkMiddleHorizontal(currentShape, i, j);
	}
	public void checkRightHorizontal(int currentShape, int i, int j){
		if (j < gameGrid[1].length - 2 && gameGrid[i][j+1] == currentShape && gameGrid[i][j+2] == currentShape){//right horizontal
			gameGrid[i][j+1] = 0;
			gameGrid[i][j+2] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
	}
	public void checkLeftHorizontal(int currentShape, int i, int j){
		if ((j > 1 && gameGrid[i][j-1] == currentShape && gameGrid[i][j-2] == currentShape)){
			gameGrid[i][j-1] = 0;
			gameGrid[i][j-2] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
	}
	public void checkMiddleHorizontal(int currentShape, int i, int j){
		if ((j > 0 && i < gameGrid[1].length - 1) && gameGrid[i][j-1] == currentShape && gameGrid[i][j+1] == currentShape){//middle horizontal
			gameGrid[i][j-1] = 0;
			gameGrid[i][j+1] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
	}

	public void checkVertical(int currentShape, int i, int j){
		checkAboveVerticle(currentShape, i ,j);
		checkBottomVerticle(currentShape, i ,j);
		checkMiddleVerticle(currentShape, i ,j);
	}
	
	public void checkAboveVerticle(int currentShape, int i, int j){
		if (i > 1 && gameGrid[i-1][j] == currentShape && gameGrid[i-2][j] == currentShape){
			gameGrid[i-1][j] = 0;
			gameGrid[i-2][j] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
	}
	public void checkBottomVerticle(int currentShape, int i, int j){
		if (i < gameGrid.length - 2 && gameGrid[i+1][j] == currentShape && gameGrid[i+2][j] == currentShape){
			gameGrid[i+1][j] = 0;
			gameGrid[i+2][j] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
	}
	public void checkMiddleVerticle(int currentShape, int i, int j){
		if ((i > 0 && i < gameGrid.length - 1) && gameGrid[i-1][j] == currentShape && gameGrid[i+1][j] == currentShape){
			gameGrid[i-1][j] = 0;
			gameGrid[i+1][j] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
	}
	

	public void checkTopDiagonal(int currentShape, int i, int j){
		if ((i > 1 && j > 1) && gameGrid[i-1][j-1] == currentShape && gameGrid[i-2][j-2] == currentShape){ //top left diagonal
			gameGrid[i-1][j-1] = 0;
			gameGrid[i-2][j-2] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
		if ((i > 1 && j < gameGrid[1].length - 2) && gameGrid[i-1][j+1] == currentShape && gameGrid[i-2][j+2] == currentShape){//top right diagonal 
			gameGrid[i-1][j+1] = 0;
			gameGrid[i-2][j+2] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
	}
	
	public void checkBottomDiagonal(int currentShape, int i, int j){
		if ((i < gameGrid.length - 2 && j > 1) && gameGrid[i+1][j-1] == currentShape && gameGrid[i+2][j-2] == currentShape){//bottom left diagonal 
			gameGrid[i+1][j-1] = 0;
			gameGrid[i+2][j-2] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
		if ((i < gameGrid.length - 2 && j < gameGrid[1].length - 2) && gameGrid[i+1][j+1] == currentShape && gameGrid[i+2][j+2] == currentShape){//bottom right diagonal 
			gameGrid[i+1][j+1] = 0;
			gameGrid[i+2][j+2] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
	}
	
	public void checkMiddleDiagonal(int currentShape, int i, int j){
		if ((i > 0 && i < gameGrid.length - 1 && j > 0 && j < gameGrid[1].length - 1) && gameGrid[i-1][j-1] == currentShape && gameGrid[i+1][j+1] == currentShape){//left diagonal 
			gameGrid[i-1][j-1] = 0;
			gameGrid[i+1][j+1] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
		if ((i > 0 && i < gameGrid.length - 1 && j > 0 && j < gameGrid[1].length - 1) && gameGrid[i+1][j-1] == currentShape && gameGrid[i-1][j+1] == currentShape){//right diagonal 
			gameGrid[i+1][j-1] = 0;
			gameGrid[i-1][j+1] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
	}
	
	//new grid after rows of three have been eliminated from the grid
	public void resetGameGrid(){
		int currentNum;
		for (int i = gameGrid[1].length - 1; i >= 0; i--){
			for (int j = gameGrid.length - 1; j >= 1; j--){
				if (gameGrid[j][i] == 0 && gameGrid[j-1][i] != 0){
					currentNum = gameGrid[j-1][i];
					gameGrid[j][i] = currentNum;
					gameGrid[j-1][i] = 0;
				}
			}
		}
		threeInARow = false;
	}
	
	public boolean checkForFilledGrid(){
		for (int i = 0; i < gameGrid[1].length; i++){
			if (gameGrid[0][i] != 0)
				return true;
		}
		if (gameGrid[2][5] != 0)
			return true;
		else 
			return false;
	}
	
	public void checkForGamePause(){	//when the user presses 'p' during the game
		if(k.getKeysPressed()[k.getpKEY()])
			gameState = GAME_PAUSE;
	}
	
	public void updateLevel(){
		if(score >= (level*1000)){		//everytime score increments by 1000, level is updated
			levelUp = true;
			level++;
			velocityIncrease ++;
		}
	}
	public void updateQuitButtons(){	//when the user selects QUIT from the menu
		if(k.getKeysPressed()[k.getbKEY()])
			gameState = GAME_MAIN_MENU;
		if(currentQuitButton == YES_BUTTON)	
			updateYesButton();
		else if(currentQuitButton == NO_BUTTON)
			updateNoButton();
	
	}
	
	public void updateYesButton(){
		if(k.getKeysPressed()[k.getDOWN()])
			currentQuitButton = NO_BUTTON;
		else if(k.getKeysPressed()[k.getENTER()])
			gameState = GAME_RATE;
	}
	public void updateNoButton(){
		if( k.getKeysPressed()[k.getUP()])
			currentQuitButton = YES_BUTTON;
		else if(k.getKeysPressed()[k.getENTER()]){
			gameState = GAME_MAIN_MENU;
			k.getKeysPressed()[k.getENTER()] = false;
		}
	}
	
	public void updatePauseButtons(){ // when the game is paused, RESUME OR QUIT?
		if(currentPauseButton == RESUME_BUTTON)
			updateResumeButton();
		else if(currentPauseButton == PAUSE_QUIT_BUTTON)
			updatePauseQuitButton();
	}
	
	public void updateResumeButton(){
		if(k.getKeysPressed()[k.getDOWN()])
			currentPauseButton = PAUSE_QUIT_BUTTON;
		else if(k.getKeysPressed()[k.getENTER()])
			gameState = GAME_PLAY;
	}
	
	public void updatePauseQuitButton(){
		if(k.getKeysPressed()[k.getUP()])
			currentPauseButton = RESUME_BUTTON;
		else if(k.getKeysPressed()[k.getENTER()]){
			gameState = GAME_MAIN_MENU;
			resetGame();		 
			k.getKeysPressed()[k.getENTER()] = false;
		} 
	}
	
	public void updateLoseScreen(){	//when the blocks reach the top
		int index = 0;
		if(k.getKeysPressed()[k.getcKEY()]){
			//updateHighscore(index);
			k.getKeysPressed()[k.getcKEY()] = false;
			gameState = GAME_MAIN_MENU;
			resetGame();
			
		}
	}
	//HIGHSCOREs
	/*public void updateHighscore(int index){
		fileInput = null;
    	try {
    		fileInput = new Scanner(new File("Highscores.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("No such file exists! Add the file to the project OR check to see that the file name is correct.");
		}
    	
    	while(index < 10){
    		
    		String currLine = fileInput.nextLine();
    		String currScore;
    		if(index < 9)
    			currScore = currLine.substring(2);
    		else
    			currScore = currLine.substring(3);
    		int currScoreInt = Integer.parseInt(currScore);
    		highscores[index] = currScoreInt;
    		index++;
    	}
		for(int i = 0; i < 10; i++){
				if(score > highscores[i]){
					highscores[i] = score;
					break;
			}
		}
		Arrays.sort(highscores);
		FileOutputStream writer;
		try {
			writer = new FileOutputStream("Highscores.txt");
			writer.write(0);
			writer.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		addToFile();
	}
	
	public void addToFile() {
	    try {
    		String filename= "Highscores.txt";
    	    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
    	    for(int i = 9, num = 1; i >= 0  ; i--, num++)
    	    {
    	    	String score = num + "." + highscores[i];
    	    	fw.write(score + "\n");//appends the string to the file
    	    }
    	    
    	    fw.close();
		} catch (IOException ioe) {
			System.out.println("No such file exists!");
		}
	}*/
	
	public void resetGame(){ //called when game is lost or restarted
		initializeButtonStates();
		initializeStartscreenVariables();
		initializeBlockVelocityVariables();
		initializeBlockSetArrays();
		initializeGameGridVaribles();
		initializeScoreAndLevels();
	}
	
	public void updateRateScreen(){
		String rate = updateRate();
		if(rate != " "){
		 try {
			 	gameState = GAME_QUIT; //to exit as soon as the user rates the game
	    		String filename= "Ratings.txt";
	    	    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
	    	    fw.write(rate + "\n");//appends the string to the file
	    	    fw.close();
			} catch (IOException ioe) {
				System.out.println("No such file exists!");
			}
		}
	}
	
	public String updateRate(){
		String rate = " ";
		if(k.getKeysPressed()[k.getOneKEY()]){
			rate = "1";
			k.getKeysPressed()[k.getOneKEY()] = false;
		}
		else if(k.getKeysPressed()[k.getTwoKEY()]){
			rate = "2";
			k.getKeysPressed()[k.getTwoKEY()] = false;
		}
		else if(k.getKeysPressed()[k.getThreeKEY()]){
			rate = "3";
			k.getKeysPressed()[k.getThreeKEY()] = false;
		}
		else if(k.getKeysPressed()[k.getFourKEY()]){
			rate = "4";
			k.getKeysPressed()[k.getFourKEY()] = false;
		}
		else if(k.getKeysPressed()[k.getFiveKEY()]){
			rate = "5";
			k.getKeysPressed()[k.getFiveKEY()] = false;
		}
		return rate;
	}
	

	//draw things to the screen
	public void draw() {
		drawBackground();
		if(gameState == GAME_STARTSCREEN)
			drawStartScreen();
		else if(gameState == GAME_MAIN_MENU)
			drawMenu();
		else if(gameState == GAME_HELP)
			drawHelpScreen();
		else if(gameState == GAME_PLAY)
			drawGame();
		else if(gameState == GAME_QUITSCREEN) 
			drawConfirmationScreen(); //"Are you sure you want to quit?"
		else if (gameState == GAME_QUIT)
			System.exit(0);
		else if(gameState == GAME_PAUSE)
			drawPauseScreen();
		else if(gameState == GAME_LOST)
			drawLoseScreen();
		else if(gameState == GAME_RATE)
			drawRateScreen();
	}
		
	public void drawBackground() {
		Rectangle2D background = new Rectangle2D.Double(0,0,WIDTH, HEIGHT);
		graphics.setColor(Color.black);
		graphics.fill(background);
	}		
	
	public void drawStartScreen(){
		loadBackgroundImage();
		drawBackgroundImage();
		drawWelcomeTo();
		drawMagikJewelry();
		drawContinueMessage();
		drawNamesAndCourseCode();
		//three falling blocks
		drawStartScreenBlockOne();
		drawStartScreenBlockTwo();
		drawStartScreenBlockThree();
	}
	
	public static void loadBackgroundImage() {
		try {
			picture = ImageIO.read(new File("MagikJewelryBackground.jpg"));
		} catch (Exception e) {};
	}

	public void drawBackgroundImage(){
		graphics.drawImage(picture,0,0,WIDTH,HEIGHT,null);
	}
	public void drawWelcomeTo(){
		String text = "Welcome to " ;
		Font times = new Font("Times New Roman", Font.BOLD, 70);//creates a font object
		AttributedString attStr = new AttributedString(text); //used to add attributes to any part of a given string
		attStr.addAttribute(TextAttribute.FONT, times); //sets the font of the entire string to courier
		Color textColour = new Color(102,0,102);
		attStr.addAttribute(TextAttribute.FOREGROUND,textColour,0, text.length()); //sets the colour of characters from position 3 to 4 to red
		graphics.drawString(attStr.getIterator(), 335, 200);
	}
	
	public void drawMagikJewelry(){
		String title = "MAGIK JEWELRY";
		AttributedString as = new AttributedString(title);
		Font system = new Font("System", Font.BOLD, 100);
		as.addAttribute(TextAttribute.FONT, system); 
		Color titleColour = getTitleColour(); //swtich colours of the title according to loop
		graphics.setColor(titleColour);
		graphics.drawString(as.getIterator(), 100, 350);
	}
	
	public Color getTitleColour(){
		if(colourCount == 1)
			return Color.blue;
		else if(colourCount == 2)
			return Color.green;
		else if(colourCount == 3)
			return Color.red;
		else
			return Color.magenta;
	}
	
	public void drawContinueMessage() {
		String continueMessage = "Press 'c' to continue";
		Font times = new Font("Times New Roman", Font.BOLD, 70);
		AttributedString as = new AttributedString(continueMessage);
		as.addAttribute(TextAttribute.FONT, times);
		Color textColour = new Color(255,102,102);
		as.addAttribute(TextAttribute.FOREGROUND, textColour,0, continueMessage.length());
		graphics.drawString(as.getIterator(), 250, 600);
	}
	
	public void drawNamesAndCourseCode(){
		String nameTrisha = "Trisha Saar";
		String nameYashvi = "Yashvi Shah";
		String byMessage = "By:";
		String course = "ICS3U";
		Color colour = new Color(255,0,127);
		graphics.setColor(colour);
		Font MONOSPACED = new Font(Font.MONOSPACED, Font.BOLD, 40);
		graphics.setFont(MONOSPACED);
		graphics.drawString(byMessage, 300, 430);
		graphics.drawString(nameTrisha, 380, 430);
		graphics.drawString(nameYashvi, 380, 510);
		colour = new Color(0,51,102);
		graphics.setColor(colour);
		graphics.drawString(course, 440, 70);
	}
	
	public void drawStartScreenBlockOne(){
		Rectangle2D block1 = new Rectangle2D.Double(square.getX(), square.getY(), 75,40);
		graphics.setColor(Color.red);
		graphics.fill(block1);
	}
	
	public void drawStartScreenBlockTwo(){
		RoundRectangle2D block2 = new RoundRectangle2D.Double(450,square.getY(), 75,40,35,35 );
		graphics.setColor(Color.blue);
		graphics.fill(block2);
	}
	public void drawStartScreenBlockThree(){
		Ellipse2D block3 = new Ellipse2D.Double(935, square.getY(), 75, 40);
		graphics.setColor(Color.green);
		graphics.fill(block3);	
	}
	
	public void drawMenu(){
		drawBackground();
		drawTitle();
		drawKeyboardMessage();
		loadMenuImage();
		drawMenuImage();
		drawButtons();
		if(currentButton == PLAY_BUTTON) 
			highlightPlayButton();	//to show which button is selected
		else if (currentButton == QUIT_BUTTON)
			highlightMenuQuitButton();
		else if(currentButton == HELP_BUTTON)
			highlightHelpButton();
	}
	
	
	public void drawTitle(){
		String firstWord = "MAGIK";
		String secondWord = "JEWELRY";
		Font sansSerif = new Font(Font.SANS_SERIF, Font.BOLD, 100);
		Color colour = new Color(0,128,255);
		graphics.setFont(sansSerif);
		graphics.setColor(colour);
		graphics.drawString(firstWord, 100, 385);
		colour = new Color(204, 0 ,102);
		graphics.setColor(colour);
		graphics.drawString(secondWord, 495, 385);
	}
	
	public void loadMenuImage(){
		try {
			picture = ImageIO.read(new File("MJ.jpg"));
		} catch (Exception e) {};
	}
	
	public void drawMenuImage(){
		graphics.drawImage(picture,0,1,1024,300,null);
	}
	
	public void drawButtons(){
		Color colour = new Color(102,255,102);
		graphics.setColor(colour);
		Font sansSerif = new Font(Font.SANS_SERIF, Font.BOLD, 60);
		graphics.setFont(sansSerif);
		drawPlayButton();
		graphics.setColor(colour);
		drawHelpButton();
		graphics.setColor(colour);
		drawMenuQuitButton();
		
	}
	public void drawPlayButton(){
		RoundRectangle2D playButton = new RoundRectangle2D.Double(140, 450, 300,100, 40,40);
		graphics.fill(playButton);
		String play = "Play";
		graphics.setColor(Color.RED);
		graphics.drawString(play, 230, 520);
	}
	
	public void drawHelpButton(){
		RoundRectangle2D helpButton = new RoundRectangle2D.Double(520, 450, 300,100, 40,40);
		graphics.fill(helpButton);
		String help = "Help";
		graphics.setColor(Color.RED);
		graphics.drawString(help, 610, 520);
	}
	
	public void drawMenuQuitButton(){
		RoundRectangle2D quitButton = new RoundRectangle2D.Double(330, 580, 300,100, 40,40);
		graphics.fill(quitButton);
		String quit = "Quit";
		graphics.setColor(Color.RED);
		graphics.drawString(quit, 415, 650);
	}
	
	public void drawKeyboardMessage(){
		String message = "PLEASE USE KEYBOARD CONTROLS ONLY";
		Color colour = new Color(0, 153 ,0);
		graphics.setColor(colour);
		Font sansSerif = new Font(Font.SANS_SERIF, Font.BOLD, 30);
		graphics.setFont(sansSerif);
		graphics.drawString(message, 170, 425);
	}

	public void highlightPlayButton() {
		Rectangle2D playBorder = new Rectangle2D.Double(138,448,302,104);
		graphics.setColor(Color.white);
		graphics.draw(playBorder);
	}
	
	public void highlightHelpButton(){
		Rectangle2D helpBorder = new Rectangle2D.Double(518,448,302,104);
		graphics.setColor(Color.white);
		graphics.draw(helpBorder);
		
	}
	public void highlightMenuQuitButton() {
		Rectangle2D quitBorder = new Rectangle2D.Double(328,578,302,104);
		graphics.setColor(Color.white);
		graphics.draw(quitBorder);
	}
	
	public void drawHelpScreen(){
		loadGameScreenshot();
		drawGameScreenshot();
		initializeInstructions();
	}
	
	public void loadGameScreenshot(){
		try {
			picture = ImageIO.read(new File("GameScreenshot.jpg"));
		} catch (Exception e) {};
	}
	public void drawGameScreenshot(){
		graphics.drawImage(picture,150,1,WIDTH - 300,HEIGHT - 320,null);
	}
	
	public void initializeInstructions(){
		String[] instructions = new String[12];
		instructions[0] = "Three coloured blocks will fall.";
		instructions[1] = "-Press the 'down' arrow key to increase the speed of the falling blocks.";
		instructions[2] = "-Press the'left' arrow key to move the blocks left.";
		instructions[3] = "-Press the 'right' arrow key to move the blocks right.";
		instructions[4] = "-Press the 'spacebar' to change the order of the falling blocks.";
		instructions[5] = "-Press the 'p' key to pause the game.";
		instructions[6] = "<------";
		instructions[7] = "------>";
		instructions[8] = "-Press the 'b' key to go back to the main menu.";
		instructions[9] = "-Get three blocks of the same colour in a row horizontally, vertically or diagonally to destroy them.";
		instructions[10] = "-Don't let the blocks pile up and touch the top!";
		instructions[11] = "-50 points are earned everytime three blocks are destroyed!!";
		drawInstructions(instructions);
	}
	
	public void drawInstructions(String[] instructions){
		Font sansSerif = new Font(Font.SANS_SERIF, Font.BOLD, 22);
		graphics.setFont(sansSerif);
		graphics.setColor(Color.BLUE);
		graphics.drawString(instructions[1], 25,520);
		graphics.drawString(instructions[2], 25,550);
		graphics.drawString(instructions[3], 25,580);
		graphics.drawString(instructions[4], 25,610);
		graphics.drawString(instructions[5], 25,430);
		graphics.drawString(instructions[6], 350,110);
		graphics.drawString(instructions[7], 480,110);
		graphics.drawString(instructions[8], 25,400);
		graphics.drawString(instructions[9], 25,460);
		graphics.drawString(instructions[10], 25,490);
		graphics.drawString(instructions[0], 350,35);
		graphics.drawString(instructions[11], 25, 640);
		drawDownArrow();
	}
	
	public void drawDownArrow(){
		String firstLine = "|";
		int yLine = 200;
		for(int i = 0; i < 4 ; i++){
			graphics.drawString(firstLine, 438,yLine );
			yLine += 10;
		}
		graphics.drawString("V", 434, 233);
	}
	public void drawGame(){
		drawGameGrid();
		drawScorePanel();
		drawScore();
		drawNextBlockSet();
		drawLevelUps();
	}
	public void drawGameGrid(){		
		drawLines();
		drawCurrentBlocks();
		drawStoredBlocks();
		drawSideImage();
	}
	
	public void drawLines(){
		for(int i = 1; i < 750 ; )	{
			for(int j = 0; j < HEIGHT ; ){
				Rectangle2D gridBox = new Rectangle2D.Double(i, j , 75, HEIGHT);
				Color colour = new Color(255,69,0);
				graphics.setColor(colour);
				graphics.draw(gridBox);
				j += 40;
			}
			i += 75;
		}
	}
	
	public void drawCurrentBlocks(){
		for (int i = 0; i < 3; i++){
			if (blockSet[i].getShape() == BLUE)
				drawOvals(blockSet[i].getX(), blockSet[i].getY());
			else if (blockSet[i].getShape() == GREEN)
				drawRoundRectangles(blockSet[i].getX(), blockSet[i].getY());
			else if (blockSet[i].getShape() == ORANGE)
				drawDiamonds(blockSet[i].getX(), blockSet[i].getY());
			else if (blockSet[i].getShape() == YELLOW)
				drawRectangles(blockSet[i].getX(), blockSet[i].getY());
			else if (blockSet[i].getShape() == RED)
				drawTriangles(blockSet[i].getX(), blockSet[i].getY());
		}
	}
	public void drawStoredBlocks(){
		int x, y;
		for (int i = 0; i < gameGrid.length; i++){
			y = i*40;
			for (int j = 0; j < gameGrid[1].length; j++){
				x = j*75;
				if (gameGrid[i][j] != 0){
					if (gameGrid[i][j] == BLUE)
						drawOvals(x, y);
					else if (gameGrid[i][j] == GREEN)
						drawRoundRectangles(x, y);
					else if (gameGrid[i][j] == ORANGE)
						drawDiamonds(x, y);
					else if (gameGrid[i][j] == YELLOW)
						drawRectangles(x, y);
					else if (gameGrid[i][j] == RED)
						drawTriangles(x, y);
				}
			}
		}
	}
	
	public void drawSideImage(){
		loadImage();
		drawImage();
	}
	public static void loadImage() {
		try {
			picture = ImageIO.read(new File("StatueLiberty.jpg"));
		} catch (Exception e) {};
	}

	public void drawImage(){
		graphics.drawImage(picture,754,382,274,300,null);
	}
	
	public void drawScorePanel()
	{
		Rectangle2D scorePanel = new Rectangle2D.Double(752, 0, 1024-750, HEIGHT);
		graphics.setColor(Color.green);
		graphics.draw(scorePanel);
	}
		
	public void drawScore()	{
		String text = "Score: " + score;
		Font font = new Font("Font.MONO_SPACED", Font.BOLD, 35);	
		graphics.setFont(font);
		Color colour = new Color(255,128,0);
		graphics.setColor(colour);
		graphics.drawString(text, 755, 40);
	}
	
	public void drawNextBlockSet(){ 
		drawNextBlockSetString();
		int yCoordinate = 120;
		for (int i = 0; i < 3; i++){
			if (nextBlockSet[i] == BLUE)
				drawOvals(835, yCoordinate);
			else if (nextBlockSet[i] == GREEN)
				drawRoundRectangles(835, yCoordinate);
			else if (nextBlockSet[i] == ORANGE)
				drawDiamonds(835, yCoordinate);
			else if (nextBlockSet[i] == YELLOW)
				drawRectangles(835, yCoordinate);
			else if (nextBlockSet[i] == RED)
				drawTriangles(835, yCoordinate);
			yCoordinate += 40;
		}
	}
	
	public void drawNextBlockSetString(){
		String text = "Next Blocks:";
		Font font = new Font("Font.MONO_SPACED", Font.BOLD, 35);	
		graphics.setFont(font);
		Color colour = new Color(255,200,200);
		graphics.setColor(colour);
		graphics.drawString(text, 755, 100);
	}
	
	public void drawLevelUps(){
		if(levelUp || level == 1){
				String levelUpMessage = "Level " + level ;
				Font dialog = new Font(Font.DIALOG, Font.BOLD, 50);
				graphics.setFont(dialog);
				Color colour = new Color(51,51,255);
				graphics.setColor(colour);
				graphics.drawString(levelUpMessage, 800, HEIGHT/2 - 20);			
		}
	}
	
	public void drawOvals(int x, int y){
		graphics.setColor(Color.blue);
		graphics.fillOval(x, y+1, 75, 40);
		graphics.setColor(Color.white);
		graphics.drawOval(x - 1, y, 76, 41);
	}
	public void drawRoundRectangles(int x, int y){
		graphics.setColor(Color.green);
		RoundRectangle2D currentBlock = new RoundRectangle2D.Double(x, y+1, 75, 40, 40, 35);
		graphics.fill(currentBlock);
		graphics.setColor(Color.white);
		RoundRectangle2D blockBorder = new RoundRectangle2D.Double(x - 1, y  , 76, 41, 40, 35);
		graphics.draw(blockBorder);
	}
	public void drawRectangles(int x, int y){
		graphics.setColor(Color.yellow);
		Rectangle2D currentBlock = new Rectangle2D.Double(x, y+1, 75, 40);
		graphics.fill(currentBlock);
		graphics.setColor(Color.white);
		Rectangle2D blockBorder = new Rectangle2D.Double(x - 1, y, 76, 41);
		graphics.draw(blockBorder);
	}
	public void drawDiamonds(int x, int y){
		graphics.setColor(Color.orange);
		int[] xPoints = {x + 37, x + 75, x +37, x};
		int[] yPoints = {y, y + 20, y + 40, y + 20};
		graphics.fillPolygon(xPoints, yPoints, 4);
		drawDiamondBlockBorder(x, y);
	}
	
	public void drawDiamondBlockBorder(int x, int y){
		graphics.setColor(Color.white);
		int[] xPointsBorder = {x + 37, x + 76, x + 37,x - 1};
		int[] yPointsBorder = {y - 1, y +20, y + 41, y +20};
		graphics.drawPolygon(xPointsBorder, yPointsBorder, 4);
		
	}
	
	public void drawTriangles(int x, int y){
		graphics.setColor(Color.red);
		int[] xPoints = {x + 37,x,x + 75};
		int[] yPoints = {y,y+40,y+40};
		graphics.drawPolygon(xPoints, yPoints, 3);
		graphics.fillPolygon(xPoints, yPoints, 3);
		drawTriangleBorder(x,y);
	}
	
	public void drawTriangleBorder(int x, int y){
		graphics.setColor(Color.white);
		int[] xPointsBorder = {x +37, x -2, x + 77};
		int[] yPointsBorder = {y -2, y + 42, y + 42};
		graphics.drawPolygon(xPointsBorder, yPointsBorder, 3);
		
	}
	public void drawConfirmationScreen(){ //to confirm whether the player wants to quit or not
		drawBackground();
		drawQuestion();
		drawYesButton();
		drawNoButton();
		if(currentQuitButton == YES_BUTTON)
			highlightYesButton();
		else if(currentQuitButton == NO_BUTTON)
			highlightNoButton();
		drawBackMessage();		
	}
	
	public void drawQuestion(){
		String quitMessage1 = "Are you sure you want";
		String quitMessage2 = "to quit?";
		graphics.setColor(Color.cyan);
		Font sansSerif = new Font(Font.SANS_SERIF, Font.BOLD, 80);
		graphics.setFont(sansSerif);
		graphics.drawString(quitMessage1, 70, 70);
		graphics.drawString(quitMessage2, 335, 160);
	}
	public void drawYesButton(){
		Rectangle2D yesButton = new Rectangle2D.Double(362, 300, 300,150);
		Color colour = new Color(0,153,0);
		graphics.setColor(colour);
		graphics.fill(yesButton);
		String yes = "YES";
		graphics.setColor(Color.CYAN);
		graphics.drawString(yes, 430, 405);
	}
	public void drawNoButton(){
		Rectangle2D noButton = new Rectangle2D.Double(362, 500, 300, 150);
		Color colour = new Color(0,153,0);
		graphics.setColor(colour);
		graphics.fill(noButton);
		String no = "NO";
		graphics.setColor(Color.CYAN);
		graphics.drawString(no, 450, 605);
		
	}
	
	public void highlightYesButton(){
		Rectangle2D yesBorder = new Rectangle2D.Double(360,298,302, 152);
		graphics.setColor(Color.white);
		graphics.draw(yesBorder);
	}
	public void highlightNoButton(){
		Rectangle2D noBorder = new Rectangle2D.Double(360,498,302, 152);
		graphics.setColor(Color.white);
		graphics.draw(noBorder);
	}
	
	public void drawBackMessage(){
		String backMessage = "Press the 'b' key to go back to the main menu.";
		Font sansSerif = new Font(Font.SANS_SERIF, Font.BOLD, 40);
		graphics.setFont(sansSerif);
		graphics.drawString(backMessage, 70, 250);
	}
	
	public void drawPauseScreen(){
		drawBackground();
		drawMessage();		
		drawResumeButton();
		drawQuitButton();
		if(currentPauseButton == RESUME_BUTTON)
			highlightResumeButton();
		else if(currentPauseButton == PAUSE_QUIT_BUTTON)
			highlightPauseQuitButton();
	}
	public void drawMessage(){
		graphics.setColor(Color.gray);
		String message = "What do you want to do?";
		Font sansSerif = new Font(Font.SANS_SERIF, Font.BOLD, 70);
		graphics.setFont(sansSerif);
		graphics.drawString(message, 120, 100);
	}
	public void drawResumeButton(){
		RoundRectangle2D resumeButton = new RoundRectangle2D.Double(312, 200, 300, 150, 40,35);
		graphics.setColor(Color.MAGENTA);
		graphics.fill(resumeButton);
		String resume = "RESUME";
		graphics.setColor(Color.GRAY);
		Font sansSerif = new Font(Font.SANS_SERIF, Font.BOLD, 60);
		graphics.setFont(sansSerif);
		graphics.drawString(resume ,335, 297);
	}
	public void drawQuitButton(){
		RoundRectangle2D quitButton = new RoundRectangle2D.Double(312, 400, 300, 150, 40,35);
		graphics.setColor(Color.MAGENTA);
		graphics.fill(quitButton);
		String quit = "QUIT";	
		graphics.setColor(Color.GRAY);
		Font sansSerif = new Font(Font.SANS_SERIF, Font.BOLD, 60);
		graphics.setFont(sansSerif);
		graphics.drawString(quit, 390 , 497);
	}
	public void highlightResumeButton(){
		RoundRectangle2D resumeButtonBorder = new RoundRectangle2D.Double(310, 198, 302, 152, 40,35);
		graphics.setColor(Color.white);
		graphics.draw(resumeButtonBorder);
	}
	public void highlightPauseQuitButton(){
		RoundRectangle2D pauseQuitButtonBorder = new RoundRectangle2D.Double(310, 398, 302, 152, 40,35);
		graphics.setColor(Color.white);
		graphics.draw(pauseQuitButtonBorder);
	}
	
	public void drawLoseScreen(){
		String loseMessage = "You lost! Your score is: " + score ;
		String continueMessage = "Press 'c' to continue";
		Color colour= new Color(204,255,229);
		graphics.setColor(colour);
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 50);
		graphics.setFont(font);
		graphics.drawString(loseMessage, 110, 300);
		graphics.drawString(continueMessage, 150, 450);
	}
		
	public void drawRateScreen(){
		drawBackground();
		drawRateMessages();
		
	}
	
	public void drawRateMessages(){
		String rateMessageOne = "Please rate this game on a scale";
		String rateMessageTwo = "of 1(worst) to 5(best).";
		Color colour = new Color(240,132,89);
		graphics.setColor(colour);
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 50);
		graphics.setFont(font);
		graphics.drawString(rateMessageOne, 20, 150);
		graphics.drawString(rateMessageTwo, 150, 200);
		String helpMessage = "Press 1, 2, 3, 4 or 5 on the keyboard.";
		font = new Font(Font.MONOSPACED, Font.BOLD, 45);
		graphics.setFont(font);
		graphics.drawString(helpMessage, 4, 400);
	}
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.add(game); //game is a component because it extends Canvas
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);            
		game.start();

	}

	//starts a new thread for the game
	public synchronized void start() {
		thread = new Thread(this, "Game");
		running = true;
		thread.start();
	}

	//main game loop
	public void run() {
		init();
		long startTime = System.nanoTime();
		double ns = 1000000000.0 / UPS;
		double delta = 0;
		int frames = 0;
		int updates = 0;

		long secondTimer = System.nanoTime();
		while(running) {
			long now = System.nanoTime();
			delta += (now - startTime) / ns;
			startTime = now;
			while(delta >= 1) {
				update();
				delta--;
				updates++;
			}
			render();
			frames++;

			if(System.nanoTime() - secondTimer > 1000000000) {
				this.frame.setTitle(updates + " ups  ||  " + frames + " fps");
				secondTimer += 1000000000;
				frames = 0;
				updates = 0;
			}
		}
		stop();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy(); //method from Canvas class

		if(bs == null) {
			createBufferStrategy(3); //creates it only for the first time the loop runs (trip buff)
			return;
		}

		graphics = (Graphics2D)bs.getDrawGraphics();
		draw();
		graphics.dispose();
		bs.show();
	}

	//stops the game thread and quits
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
