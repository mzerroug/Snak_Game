package al.niidhogg.snake.snake;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import al.niidhogg.graph.Dialog;
import al.niidhogg.snake.map.Food;

/**
 * Le serpent
 * @author Niidhogg
 *
 */
public class Snake extends JPanel implements Runnable, KeyListener
{
	private static final long serialVersionUID = 1L;
	
	public final static short WEST = -1;
	public final static short EST = 1;
	public final static short NORTH = -2;
	public final static short SOUTH = 2;

	public final static short PLAY = 0;
	public final static short DEAD = 1;
	public final static short PAUSE = 2;
	
	private Block tete;
	private ArrayList<Block> queu = new ArrayList<Block>();
	
	private short dir = WEST;
	private short dirAv = WEST;
	
	private Color color;
	private short statue = PLAY;
	
	private Food objectif = new Food((int)(Math.random()*Data.NBRCASEX), (int)(Math.random()*Data.NBRCASEY));
	private int score = 0;
	private JPanel pc = new JPanel();
	
	/**
	 * Créé un nouveau serpent
	 * @param tete le Block représentant sa tête
	 * @param nbrQueu le nombre de Block en plus de sa tete
	 */
	public Snake(Block tete, int nbrQueu)
	{
		this.tete = tete;
		color = new Color(tete.getColor().getRed()+127, tete.getColor().getGreen()+127, tete.getColor().getBlue()+127);
		
		for(int i=0;i<nbrQueu;i++)//Ajoute le corp
		{
			if(i==0)
				queu.add(new Block(tete));
			else queu.add(new Block(queu.get(i-1)));
			
			queu.get(i).setColor(color);
		}
		
		//Ajouter a la map
		
		Data.MAP.add(tete);
		Data.MAP.add(queu.toArray(new Block[queu.size()]));
		Data.MAP.add(objectif);
		
		//Ajouter le panel du score a l'ecran
		
		pc.add(new JLabel("Score: "));
		pc.add(new JLabel(String.valueOf(score)));
	}
	
	/**
	 * Résussite le serpent
	 * @param tete le Block représentant sa tete
	 * @param nbrQueu le nombre de Block en plus de sa tete
	 */
	public void reborn(Block tete, int nbrQueu)
	{
		this.tete = tete;
		color = new Color(tete.getColor().getRed()+127, tete.getColor().getGreen()+127, tete.getColor().getBlue()+127);
		score = 0;
		
		pc.removeAll();
		pc.add(new JLabel("Score: "));
		pc.add(new JLabel(String.valueOf(score)));
		
		queu.removeAll(queu);
		
		for(int i=0;i<nbrQueu;i++)//Ajouter le corp du serpent
		{
			if(i==0)
				queu.add(new Block(tete));
			else queu.add(new Block(queu.get(i-1)));
			
			queu.get(i).setColor(color);
		}
		
		objectif = new Food((int)(Math.random()*Data.NBRCASEX), (int)(Math.random()*Data.NBRCASEY));
		
		Data.MAP.add(tete);
		Data.MAP.add(queu.toArray(new Block[queu.size()]));
		Data.MAP.add(objectif);
		
		dir = WEST;
		dirAv = WEST;
		statue = PLAY;
	}
	
	/**
	 * Ajouter un Block en plus au serpent
	 */
	public void addOne()
	{
		Block b = new Block(queu.get(queu.size()-1));
		b.setColor(color);
		
		queu.add(b);
		Data.MAP.add(b);
	}
	
	/**
	 * Faire avancer le serpent d'une case
	 */
	public void avance()
	{
		for(int k=queu.size()-1;k>=0;k--)//Remplacer le Block n-1 par celui n
			queu.get(k).avance();
		
		if(dir+dirAv != 0)//Ne peut pas ce diriger dans le sens contraire
		{
			tete.avance(dir);//On fait avancer la tete toujours dans la même direction
			dirAv=dir;
		}
		else
			tete.avance((short) (dirAv));//On fait avancer la tete dans la direction dir
		
		for(int k=0;k<queu.size();k++)
			if(queu.get(k).getLocation().equals(tete.getLocation()))//Si la tete touche un autre Block du serpent
			{
				statue = DEAD;//Bloquer le serpent
				Dialog.danger(null, "Perdu !");
			}
		
		if(tete.posX == objectif.getPosX() && tete.posY == objectif.getPosY())//Si il a atteind un objectif
		{
			objectif.moveIt((int)(Math.random()*Data.NBRCASEX), (int)(Math.random()*Data.NBRCASEY));
			this.addOne();
			score++;
			((JLabel)pc.getComponent(1)).setText(String.valueOf(score));
			pc.repaint();
		}
	}
	
	/**
	 * Thread run
	 */
	public void run()
	{
		while(true)
		{
			if(statue == PLAY)//Faire avancer le serpent s'il est vivant
			{
				this.avance();
				
				try
				{
					Thread.sleep(1040-10*Data.SNAKESPEED);
				}
				catch (InterruptedException e){e.printStackTrace();}
			}
		}
	}
	
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_UP)
			dir=NORTH;
		else if(e.getKeyCode()==KeyEvent.VK_DOWN)
			dir=SOUTH;
		else if(e.getKeyCode()==KeyEvent.VK_LEFT)
			dir=WEST;
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			dir=EST;
	}
	
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	
	/**
	 * 
	 * @return les Block formant la queu du serpent
	 */
	public ArrayList<Block> getQueu()
	{
		return queu;
	}
	
	/**
	 * Snake.DEAD: mort
	 * Snake.PLAY: vivant
	 * Snake.PAUSE: en pause
	 * @return statu du serpent
	 */
	public short getStatue()
	{
		return statue;
	}
	
	/**
	 * Snake.DEAD: mort
	 * Snake.PLAY: vivant
	 * Snake.PAUSE: en pause
	 * @param statue statue à donner au serpent
	 */
	public void setStatue(short statue)
	{
		this.statue = statue;
	}
	
	/**
	 * 
	 * @return Panel du score
	 */
	public JPanel getPanel()
	{
		return this.pc;
	}
}
