package al.niidhogg.snake.snake;

import al.niidhogg.snake.map.Map;

/**
 * Class pour stocker donnée
 * @author Niidhogg
 *
 */
public class Data
{
	public static short CASESIZE = 10;
	public static short SNAKESIZE = 3;
	
	public static short NBRCASEX = 20;
	public static short NBRCASEY = 20;
	public static int SNAKESPEED = 50;
	
	public static Map MAP = new Map(Data.CASESIZE*Data.NBRCASEX, Data.CASESIZE*Data.NBRCASEY);
	
	public static void reborn()
	{
		MAP = new Map(Data.CASESIZE*Data.NBRCASEX, Data.CASESIZE*Data.NBRCASEY);
	}
}
