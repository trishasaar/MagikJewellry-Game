//Name: Trisha Saar, Yashvi Shah
//Date: June 4, 2013
//Purpose: To create the game Magik Jewelry which requires three blocks of the same colour to be arranged in a row
import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class Block {

	private final int height = 40;
	private final int width = 75;	
	private int x, y;
	private int shape;
	
	public Block(int x, int y,int shape)
	{
		setX(x);
		setY(y);
		setShape(shape);
	}
	
		public int getX()
		{
			return this.x;
		}
		public void setX(int x)
		{
			this.x = x;
		}
		
		public int getY()
		{
			return this.y;
		}
		public void setY(int y)
		{
			this.y = y;
		}
		
		public int getHeight()
		{
			return this.height;
		}
		
		public int getWidth()
		{
			return this.width;
		}
		
		public int getShape(){
			return shape;
		}
		
		public void setShape(int shape){
			this.shape = shape;
		}

}
