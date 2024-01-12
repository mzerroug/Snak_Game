package al.niidhogg.snake.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Class Block, un morceau du serpent
 * @author Niidhogg
 *
 */
public class Block extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	protected int posX;
	protected int posY;
	private Block ancre;
	private Color color = Color.WHITE;
	
	/**
	 * Cr�� un Block du serpent qui suivra le Block ancre
	 * @param ancre Block a suivre
	 */
	public Block(Block ancre)
	{
		this.ancre = ancre;
		this.posX = ancre.posX+1;
		this.posY = ancre.posY;
		
		this.setSize(new Dimension(Data.CASESIZE, Data.CASESIZE));
		this.setLocation(posX*Data.CASESIZE, posY*Data.CASESIZE);
	}
	
	/**
	 * Block a la position (posX, posY)
	 * @param posX position X du Block
	 * @param posY position Y du serpent
	 */
	public Block(int posX, int posY)
	{
		this.posX = posX;
		this.posY = posY;
		
		this.setSize(new Dimension(Data.CASESIZE, Data.CASESIZE));
		this.setLocation(posX*Data.CASESIZE, posY*Data.CASESIZE);
	}
	
	/**
	 * Faire avancer le Block vers le Block qu'il suit
	 */
	protected void avance()
	{
		this.posX = ancre.posX;
		this.posY = ancre.posY;
		
		this.setLocation(posX*Data.CASESIZE, posY*Data.CASESIZE);
	}
	
	/**
	 * Block.WEST: Vers la gauche
	 * Block.EST: Vers la droite
	 * Block.NORTH: Vers le haut
	 * Block.SOUTH: Vers le bas
	 * Faire avancer le serpent dans la direction dir
	 * @param dir direction a prendre
	 */
	protected void avance(short dir)
	{
		if(dir == Snake.EST)
			posX++;
		else if(dir == Snake.WEST)
			posX--;
		else if(dir == Snake.NORTH)
			posY--;
		else //if(dir == Snake.SOUTH)
			posY++;
		
		//S'il sort du cadre
		
		if(posX<0 && dir == Snake.WEST)posX=Data.NBRCASEX-1;
		if(posX>=Data.NBRCASEX && dir == Snake.EST)posX=0;
		
		if(posY<0 && dir == Snake.NORTH)posY=Data.NBRCASEY-1;
		if(posY>=Data.NBRCASEY && dir == Snake.SOUTH)posY=0;
		
		this.setLocation(posX*Data.CASESIZE, posY*Data.CASESIZE);
	}
	
	/**
	 * Faire bouger le Block a la position (posX, posY)
	 * @param posX position X du Block
	 * @param posY position Y du serpent
	 */
	public void moveIt(int posX, int posY)
	{
		this.posX = posX;
		this.posY = posY;
		
		this.setSize(new Dimension(Data.CASESIZE, Data.CASESIZE));
		this.setLocation(posX*Data.CASESIZE, posY*Data.CASESIZE);
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.drawOval(0, 0, Data.CASESIZE-1, Data.CASESIZE-1);
		
		g.setColor(color);
		g.fillOval(1, 1, Data.CASESIZE-3, Data.CASESIZE-3);
	}

	/**
	 * Donner la couleur color au Block
	 * @param color couleur a donner
	 */
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	/**
	 * 
	 * @return la couleur du Block
	 */
	public Color getColor()
	{
		return color;
	}
}
