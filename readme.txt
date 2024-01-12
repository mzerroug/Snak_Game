Un simple snake en java-----------------------
Url     : http://codes-sources.commentcamarche.net/source/54045-un-simple-snake-en-javaAuteur  : NiidhoggDate    : 05/08/2013
Licence :
=========

Ce document intitulé « Un simple snake en java » issu de CommentCaMarche
(codes-sources.commentcamarche.net) est mis à disposition sous les termes de
la licence Creative Commons. Vous pouvez copier, modifier des copies de cette
source, dans les conditions fixées par la licence, tant que cette note
apparaît clairement.

Description :
=============

Un petit snake en Java sans image sans fichier de score ou de sauvegarde. Les co
uleurs du snake et des pommes sont au hasard.
<br /><a name='source-exemple'></
a><h2> Source / Exemple : </h2>
<br /><pre class='code' data-mode='basic'>
Le
 reste du code est dans l'archive !

/**

<ul>	 <li> Faire avancer le serpen
t d'une case
</li>	 <li>/</li></ul>
	public void avance()
	{
		for(int k=que
u.size()-1;k&gt;=0;k--)//Remplacer le Block n-1 par celui n
			queu.get(k).avan
ce();
		
		if(dir+dirAv != 0)//Ne peut pas ce diriger dans le sens contraire

		{
			tete.avance(dir);//On fait avancer la tete dans la direction dir
			dir
Av=dir;
		}
		else
			tete.avance((short) (dirAv));//On fait avancer la tete 
dans la direction dirAv
		
		for(int k=0;k&lt;queu.size();k++)
			if(queu.get
(k).getLocation().equals(tete.getLocation()))//Si la tete touche un autre Block 
du serpent
			{
				statue = DEAD;//Bloquer le serpent
				Dialog.danger(null
, &quot;Perdu !&quot;);
			}
		
		if(tete.posX == objectif.getPosX() &amp;&am
p; tete.posY == objectif.getPosY())//Si il a atteind un objectif
		{
			object
if.moveIt((int)(Math.random()*Data.NBRCASEX), (int)(Math.random()*Data.NBRCASEY)
);
			this.addOne();
			score++;
			((JLabel)pc.getComponent(1)).setText(Stri
ng.valueOf(score));
			pc.repaint();
		}
	}
</pre>
