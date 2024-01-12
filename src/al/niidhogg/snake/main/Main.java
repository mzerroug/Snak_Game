package al.niidhogg.snake.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import al.niidhogg.graph.Dialog;
import al.niidhogg.snake.snake.*;

/**
 * Class Main du Snake
 * Fait le 4 et 5 février 2012
 * @author Niidhogg
 *
 */
public class Main extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private final Main before = this;
	private static final String version = "0.1";
	
	private Block tete;
	private Snake serpent;
	
	private JPanel mapPanel = new JPanel();
	
	public static void main(String[] args)
	{
		new Main();
	}
	
	/**
	 * Constructeur pour construire la fenetre du jeu
	 */
	public Main()
	{
		this.setTitle("Snake !");
		this.setLayout(new BorderLayout());
		
		//MENU !
		
			JMenuBar mb = new JMenuBar();
				JMenu jeu = new JMenu("Jeu");
					JMenuItem start = new JMenuItem("Start");
					JMenuItem pause = new JMenuItem("Pause");
				JMenu info = new JMenu("?");
					JMenuItem aPropos = new JMenuItem("A propos");
			
			jeu.setMnemonic('J');
			pause.setMnemonic('P');
			aPropos.setMnemonic('H');
			
			start.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
			start.addActionListener(new Option(this));
			
			jeu.add(start);
			
			pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
			pause.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0)
				{
					serpent.setStatue(Snake.PAUSE);
					Dialog.info(before, "Jeu en pause !");
					serpent.setStatue(Snake.PLAY);
				}
			});
			
			jeu.add(pause);
			
			aPropos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
			aPropos.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0)
				{
					Dialog.info(before, "<html>Créé par: Niidhogg<br />Version: "+version+"<br />Copyright: <img src='http://i.creativecommons.org/l/by-nc-sa/3.0/80x15.png' /><br/>http://creativecommons.org/licenses/by-nc-sa/3.0/</html>");
				}
			});
			
			info.add(aPropos);
			
			mb.add(jeu);
			mb.add(info);
			
			this.getContentPane().add(mb, BorderLayout.NORTH);
		
		//SNAKE !
			
			mapPanel.setLayout(new FlowLayout());
			mapPanel.add(Data.MAP, BorderLayout.CENTER);
			this.getContentPane().add(mapPanel);
		
			tete = new Block((Data.NBRCASEX-1)/2, (Data.NBRCASEY-1)/2);
			tete.setColor(new Color((int)(Math.random()*127), (int)(Math.random()*127), (int)(Math.random()*127)));
			serpent = new Snake(tete, Data.SNAKESIZE);
			
			new Thread(serpent).start();
			this.addKeyListener(serpent);
			
		//COMPTEUR
			
			this.add(serpent.getPanel(), BorderLayout.SOUTH);
			
		this.pack();
		this.setLocationRelativeTo(null);
		
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	/**
	 * Rejouer et rétablir la fenetre
	 */
	public void replay()
	{
		Data.MAP.removeAll();
		Data.MAP.repaint();
		
		mapPanel.removeAll();
		mapPanel.repaint();
		
		this.remove(mapPanel);
		mapPanel.remove(Data.MAP);
		Data.reborn();
		
		mapPanel.add(Data.MAP, BorderLayout.CENTER);
		this.getContentPane().add(mapPanel);
		
		tete.moveIt((Data.NBRCASEX-1)/2, (Data.NBRCASEY-1)/2);
		tete.setColor(new Color((int)(Math.random()*127), (int)(Math.random()*127), (int)(Math.random()*127)));
		serpent.reborn(tete, Data.SNAKESIZE);
		
		this.pack();
		this.setLocationRelativeTo(null);
	}
}