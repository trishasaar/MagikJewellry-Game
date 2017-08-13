import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;


public class ROUGH {

	/*public void drawSecondBlock(int number)
	{
		int width = block.getWidth();
		int height = block.getHeight();
		if(number == RED){
			RoundRectangle2D block1 = new RoundRectangle2D.Double(375, 0 + height  , width - 2 , height - 2,35,35) ;
			graphics.setColor(Color.RED);
			graphics.draw(block1);
			graphics.fill(block1);
		}
		
		else if(number == BLUE)
		{
			Ellipse2D block2 = new Ellipse2D.Double(375 , 0 + height  , width - 2 ,  height -2);
			graphics.setColor(Color.BLUE);
			graphics.draw(block2);
			graphics.fill(block2);
		}
		
		else if(number == GREEN)
		{
			Rectangle2D block3 = new Rectangle2D.Double(375 , 0 + height , width -2, height-2);
			graphics.setColor(Color.GREEN);
			graphics.draw(block3);
			graphics.fill(block3);
			
		}
		
	}
	
	public void drawThirdBlock(int number){
		int width = block.getWidth();
		int height = block.getHeight();
		if(number == RED){
			RoundRectangle2D block1 = new RoundRectangle2D.Double(375 , 0 + 2*height, width, height,35,35) ;
			graphics.setColor(Color.RED);
			graphics.draw(block1);
			graphics.fill(block1);
		}
		
		else if(number == BLUE)
		{
			Ellipse2D block2 = new Ellipse2D.Double(375 , 0 + 2*height, width, height);
			graphics.setColor(Color.BLUE);
			graphics.draw(block2);
			graphics.fill(block2);
		}
		
		else if(number == GREEN)
		{
			Rectangle2D block3 = new Rectangle2D.Double(375 , 0 + 2*height, width, height);
			graphics.setColor(Color.GREEN);
			graphics.draw(block3);
			graphics.fill(block3);
			
		}
	}*/
	
	/*
	public void drawThreeBlocks(int x, int y)
	{		
		int width = block1.getWidth();
		int height = block1.getHeight();

		Block block1 = new Block(x,y,Color.MAGENTA);
		RoundRectangle2D firstBlock = new RoundRectangle2D.Double(block1.getX(), block1.getY() + 1, width, height,35,35 );
		Block block2 = new Block(x, block1.getY() + 40, Color.blue);
		Ellipse2D secBlock = new Ellipse2D.Double(block2.getX(), block2.getY()  , width - 2 ,  height - 2);
		Block block3 = new Block(x, block2.getY() + 40, Color.ORANGE);
		Rectangle2D thirdBlock = new Rectangle2D.Double(block3.getX() ,  block3.getY(), width, height);
		graphics.setColor(block1.getColour());
		graphics.draw(firstBlock);
		graphics.fill(firstBlock);
		graphics.setColor(block2.getColour());
		graphics.draw(secBlock); 
		graphics.fill(secBlock); 
		graphics.setColor(block3.getColour());
		graphics.draw(thirdBlock);
		graphics.fill(thirdBlock);
	
	}
	}
	 */
	
	/*int xPos = block1.getX() / 75;
	int yPos = block1.getY() / 40;
	Block tempBlock = new Block(block1.getX(), block1.getY(), block1.getColour());
	gameArray[yPos][xPos] = tempBlock;
	block1.setX(375);
	block2.setY(0);
	drawGameGrid(tempBlock);*/
	
	//BLOCK ARRAY
	/*
	 public void updateGameGrid() {
		int x = 0, y = 0;
		for (int i = 0; i < HEIGHT/40; i++){
			for (int j = 0; j < 10; j++){
				if (reachTheBottom == true ){
					if (block1.getX() == x && block1.getY() == y)
						gameArray[i][j] = block1;
					else if (block2.getX() == x && block2.getY() == y)
						gameArray[i][j] = block2;
					else if (block3.getX() == x && block3.getY() == y)
						gameArray[i][j] = block3;
				}
				x += 40;
			}
			y += 75;
		}
	}
	 */
	
	//LATEST!!!!!!!
	
	/*
	 if(block1.getY() < 686-120) //to check if blocks have reached the bottom,
			//height of three blocks: 40 * 3 = 120
		{
			reachTheBottom = false;
			block1.setY(block1.getY() + 1); //to keep it moving even if the user doesn't press a key
			block2.setY(block2.getY() + 1);
			block3.setY(block3.getY() + 1);
			if(k.keysPressed[k.getDOWN()]){
				block1.setY(block1.getY() + vy); //if the user presses DOWN key, velocity increases
				block2.setY(block2.getY() + vy);
				block3.setY(block3.getY() + vy);
				k.keysPressed[k.getDOWN()] = false;
			}
		}
			
		else
		{
			reachTheBottom = true;
			block1.setY(686 - 120);
			block2.setY(686 - 80);
			block3.setY(686 - 40);
			k.keysPressed[k.getDOWN()] = false;
			blockSet[0] = block1;
			blockSet[1] = block2;
			blockSet[2] = block3;
			

		}
		
		if(k.keysPressed[k.getLEFT()])
		{
			block1.setX(block1.getX() - vx);
			block2.setX(block2.getX() - vx);
			block3.setX(block3.getX() - vx);
			k.keysPressed[k.getLEFT()] = false;
		}
		
		if(k.keysPressed[k.getRIGHT()]){
			block1.setX(block1.getX() + vx);
			block2.setX(block2.getX() + vx);
			block3.setX(block3.getX() + vx);
			k.keysPressed[k.getRIGHT()] = false;
		}
		
		if(block1.getX() > 752-75){//scorePanel starts at 752, checking for right bounds
			block1.setX(block1.getX() - vx );
			block2.setX(block2.getX() - vx );
			block3.setX(block3.getX() - vx );
		}
		if(block1.getX() < 0){ //checking for left bounds
			block1.setX(block1.getX() + vx);
			block2.setX(block2.getX() + vx);
			block3.setX(block3.getX() + vx);
		}
		
		if(reachTheBottom) {
			updateGameGrid();
			tempX = block1.getX();
			tempY = block1.getY();
			block1.setX(375);
			block2.setX(375);
			block3.setX(375);
			block1.setY(0);
			block2.setY(0);
			block3.setY(0);
			
		}
		
		updateGameGrid();
	 */
	
	//UPDATE GAME GRID:
	
	/*
	 int count = 0;
		for (int i = 0; i < 50; i++){
			if (count == 0){
				int x1 = block1.getX();
				int y1 = block1.getY();
				if (xCoordinates[i] == 0){
					xCoordinates[i] = x1;
					System.out.println("x coor block1 WORKS");
				}
				if (yCoordinates[i] == 0)
					yCoordinates[i] = y1;
				if (colours[i] == 0){
					if (block1.getColour().equals(Color.red))
						colours[i] = 1;
					else if (block1.getColour().equals(Color.green))
						colours[i] = 2;
					else if (block1.getColour().equals(Color.blue))
						colours[i] = 3;
					else 
						colours[i] = 4;
				
				
				}
				count++;
			}
			else if (count == 1){
				int x2 = block2.getX();
				int y2 = block2.getY();
				
				if (xCoordinates[i] == 0)
					xCoordinates[i] = x2;
				if (yCoordinates[i] == 0)
					yCoordinates[i] = y2;
				if (colours[i] == 0){
					if (block2.getColour().equals(Color.red)){
						colours[i] = 1;
						System.out.println("WORKS1");
					}
					
					else if (block2.getColour().equals(Color.green))
						colours[i] = 2;
					else if (block2.getColour().equals(Color.blue))
						colours[i] = 3;
					else 
						colours[i] = 4;
					break;
				}
				count++;
			}
			else if (count == 3){
				int x3 = block1.getX();
				int y3 = block1.getY();
				
				if (xCoordinates[i] == 0)
					xCoordinates[i] = x3;
				if (yCoordinates[i] == 0)
					yCoordinates[i] = y3;
				if (colours[i] == 0){
					if (block3.getColour().equals(Color.red))
						colours[i] = 1;
					else if (block3.getColour().equals(Color.green))
						colours[i] = 2;
					else if (block3.getColour().equals(Color.blue))
						colours[i] = 3;
					else 
						colours[i] = 4;
					break;
				}
				break;
			}
		
		}
		
		public void drawGameBlock(Block tempBlock){
		Rectangle2D block = new Rectangle2D.Double(tempBlock.getX(), tempBlock.getY(),tempBlock.getWidth(), tempBlock.getHeight());
		graphics.setColor(tempBlock.getColour());
		graphics.draw(block);
		graphics.fill(block);
		
		
	}
	
	/*public void drawThreeBlocks()
	{		
		int width = block1.getWidth();
		int height = block1.getHeight();
		int x = block1.getX();
		int y = block1.getY();
		Block block1 = new Block(x,y,Color.red);
		RoundRectangle2D firstBlock = new RoundRectangle2D.Double(block1.getX(), block1.getY() + 1, width, height,35,35 );
		Block block2 = new Block(x, block1.getY() + 40, Color.blue);
		Ellipse2D secBlock = new Ellipse2D.Double(block2.getX(), block2.getY()  , width - 2 ,  height - 2);
		Block block3 = new Block(x, block2.getY() + 40, Color.green);
		Rectangle2D thirdBlock = new Rectangle2D.Double(block3.getX() ,  block3.getY(), width, height);
		graphics.setColor(block1.getColour());
		graphics.draw(firstBlock);
		graphics.fill(firstBlock);
		graphics.setColor(block2.getColour());
		graphics.draw(secBlock); 
		graphics.fill(secBlock); 
		graphics.setColor(block3.getColour());
		graphics.draw(thirdBlock);
		graphics.fill(thirdBlock);
	
	}*/
	//SECOND UPDATE METHOD:
	
	/*int blockCurrentY = 0, blockCurrentX = 375;
	for (int i = 0; i < gameGrid.length; i++){
		for (int j = 2; j < gameGrid[1].length; j++){
			if (j < gameGrid[1].length - 2){
				if(blockSet[BLOCK3].getY() < HEIGHT && (gameGrid[i][j + 1] == 0)) { //to check if blocks have reached the bottom, //height of three blocks: 40 * 3 = 120
					moveBlocks();
			
					if(k.keysPressed[k.getLEFT()] && gameGrid[i - 1][j]  == 0){
						goLeft();
						blockCurrentX -= 75;
					}
					else if(k.keysPressed[k.getRIGHT()]&& gameGrid[i + 1][j]  == 0){
						goRight();
						blockCurrentX += 75;
					}
					int[] newXandY = updateBlockPositionInGrid(blockCurrentX, blockCurrentY);
					
					reachTheBottom = false;
				}
				else{
					reachTheBottom = true;
					gameGrid[i][j] = blockSet[BLOCK1].getShape();
					gameGrid[i][j - 1] = blockSet[BLOCK2].getShape(); 
					gameGrid[i][j - 2] = blockSet[BLOCK3].getShape(); 
					updateBlocks();
				}
			}
			else {
				reachTheBottom = true;
				gameGrid[i][j] = blockSet[BLOCK1].getShape();
				gameGrid[i][j - 1] = blockSet[BLOCK2].getShape(); 
				gameGrid[i][j - 2] = blockSet[BLOCK3].getShape(); 
				updateBlocks();
			}
			
		}
	}
	
	(blockSet[BLOCK3].getY() >= 40 || blockSet[BLOCK3].getY() >= 80 || blockSet[BLOCK3].getY() >= 120 || blockSet[BLOCK3].getY() >= 160 || blockSet[BLOCK3].getY() >= 40
				|| blockSet[BLOCK3].getY() >= 200 || blockSet[BLOCK3].getY() >= 240 || blockSet[BLOCK3].getY() >= 280 || blockSet[BLOCK3].getY() >= 320 ||
				blockSet[BLOCK3].getY() >= 360 || blockSet[BLOCK3].getY() >= 400 || blockSet[BLOCK3].getY() >= 440 || blockSet[BLOCK3].getY() >= 480 || blockSet[BLOCK3].getY() >= 520
				|| blockSet[BLOCK3].getY() >= 560 || blockSet[BLOCK3].getY() >= 600 || blockSet[BLOCK3].getY() >= 640 || blockSet[BLOCK3].getY() >= 680 ||
				blockSet[BLOCK3].getY() >= 40)
	*/
	
	/*
	 DRAW GAME GRID::
	 
	 for (int i = 0; i < gameGrid.length; i++){
				for (int j = 0; j < gameGrid[1].length; j++)
					System.out.print(gameGrid[i][j]);
				System.out.print("\n");
			}
	 */
	
	/*
	 //HORIZONTAL CHECK
	  if ((j > 1 && i < gameGrid[1].length - 2) && gameGrid[i][j-1] == currentShape && gameGrid[i][j-2] == currentShape && gameGrid[i][j+2] == currentShape && gameGrid[i][j+1] == currentShape){//middle horizontal
			gameGrid[i][j-1] = 0;
			gameGrid[i][j-2] = 0;
			gameGrid[i][j+1] = 0;
			gameGrid[i][j+2] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
		else if ((j > 1 && i < gameGrid[1].length - 1) && gameGrid[i][j-1] == currentShape && gameGrid[i][j-2] == currentShape && gameGrid[i][j+1] == currentShape){//middle horizontal
			gameGrid[i][j-1] = 0;
			gameGrid[i][j-2] = 0;
			gameGrid[i][j+1] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
		else if ((j > 0 && i < gameGrid[1].length - 2) && gameGrid[i][j-1] == currentShape && gameGrid[i][j+2] == currentShape && gameGrid[i][j+1] == currentShape){//middle horizontal
			gameGrid[i][j-1] = 0;
			gameGrid[i][j+1] = 0;
			gameGrid[i][j+2] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
	
	
		//VERTICAL CHECK
		 if ((i > 1 && i < gameGrid.length - 2) && gameGrid[i-1][j] == currentShape && gameGrid[i-2][j] == currentShape && gameGrid[i+2][j] == currentShape && gameGrid[i+1][j] == currentShape){//middle vertical
			gameGrid[i-1][j] = 0;
			gameGrid[i-2][j] = 0;
			gameGrid[i+1][j] = 0;
			gameGrid[i+2][j] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
		else if ((i > 1 && i < gameGrid.length - 1) && gameGrid[i-1][j] == currentShape && gameGrid[i-2][j] == currentShape && gameGrid[i+1][j] == currentShape){//middle vertical
			gameGrid[i-1][j] = 0;
			gameGrid[i-2][j] = 0;
			gameGrid[i+1][j] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
		else if ((i > 0 && i < gameGrid.length - 2) && gameGrid[i-1][j] == currentShape && gameGrid[i+2][j] == currentShape && gameGrid[i+1][j] == currentShape){//middle vertical
			gameGrid[i-1][j] = 0;
			gameGrid[i+1][j] = 0;
			gameGrid[i+2][j] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
		
			if ((i > 1 && i < gameGrid.length - 2 && j > 1 && j < gameGrid[1].length - 2) && gameGrid[i+1][j-1] == currentShape && gameGrid[i+2][j-2] == currentShape && gameGrid[i-2][j-2] == currentShape && gameGrid[i-1][j+1] == currentShape){//right diagonal 
			gameGrid[i+1][j-1] = 0;
			gameGrid[i+2][j-2] = 0;
			gameGrid[i-2][j+2] = 0;
			gameGrid[i-1][j+1] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
		else if ((i > 0 && i < gameGrid.length - 2 && j > 1 && j < gameGrid[1].length - 1) && gameGrid[i+1][j-1] == currentShape && gameGrid[i+2][j-2] == currentShape && gameGrid[i-1][j+1] == currentShape){//right diagonal 
			gameGrid[i+1][j-1] = 0;
			gameGrid[i+2][j-2] = 0;
			gameGrid[i-1][j+1] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
		else if ((i > 1 && i < gameGrid.length - 1 && j > 0 && j < gameGrid[1].length - 2) && gameGrid[i+1][j-1] == currentShape && gameGrid[i-2][j+2] == currentShape && gameGrid[i-1][j+1] == currentShape){//right diagonal 
			gameGrid[i+1][j-1] = 0;
			gameGrid[i-2][j+2] = 0;
			gameGrid[i-1][j+1] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
		 
		if ((i > 1 && i < gameGrid.length - 2 && j > 1 && j < gameGrid[1].length - 2) && gameGrid[i-1][j-1] == currentShape && gameGrid[i-2][j-2] == currentShape && gameGrid[i+2][j+2] == currentShape && gameGrid[i+1][j+1] == currentShape){//left diagonal 
			gameGrid[i-1][j-1] = 0;
			gameGrid[i-2][j-2] = 0;
			gameGrid[i+2][j+2] = 0;
			gameGrid[i+1][j+1] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
		else if ((i > 1 && i < gameGrid.length - 1 && j > 1 && j < gameGrid[1].length - 1) && gameGrid[i-1][j-1] == currentShape && gameGrid[i-2][j-2] == currentShape && gameGrid[i+1][j+1] == currentShape){//left diagonal 
			gameGrid[i-1][j-1] = 0;
			gameGrid[i-2][j-2] = 0;
			gameGrid[i+1][j+1] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
		else if ((i > 0 && i < gameGrid.length - 2 && j > 0 && j < gameGrid[1].length - 2) && gameGrid[i-1][j-1] == currentShape && gameGrid[i+2][j+2] == currentShape && gameGrid[i+1][j+1] == currentShape){//left diagonal 
			gameGrid[i-1][j-1] = 0;
			gameGrid[i+2][j+2] = 0;
			gameGrid[i+1][j+1] = 0;
			gameGrid[i][j] = 0;
			score += 50;
			threeInARow = true;
		}
		 
	 */
	
	
	 
	
	
}
