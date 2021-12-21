<h1>ICWARS2021</h1>

<h1>INFO</h1>
<p>ICWars est un jeu développé dans le cadre du cours d'introduction à la programmation orienté objet en java à l'epfl</p>
<p>Le jeu tel quel peut encore grandement être amélioré et nous avons été limité par le temps à disposition</p>
<h1>Liens importants</h1>
<p>voilà quelques liens importants :
démo vidéo (utile pour voir rapidement toutes les fonctionalités) :  https://www.youtube.com/watch?v=N2ph4_Epx0Q </p>
<p>
site de présentation du jeu : https://compassionate-feynman-4aef09.netlify.app/
</p>


<h1>Déroulements :</h1>
Déroulement d'une partie typique. Voir bas du document pour caractéristiques des unités, des cartes, des cases et le mapping des touches.

<ol>
	<li>Chaque unité peut être jouée 1 fois par tour comme ceci :
	<ol> 
		<p><p>
		<li>séléction de l' unité
		<li>déplacement de l'unité sélectionnée
		<li>action de l'unité déplacée
	</ol>
	<p><p>
	<li>Une fois déplacée et ces étapes finies, l'unité n'est plus déplacable et ne peut plus effectuer d'action
	<br><br>
	<li>Une fois le tour du joueur fini, la main est passée au joueur suivant. Si il n'a plus d'unité le joueur restant est gagant
	<br><br>
	<li>A tout moment le joueur peut recommencer la partie à 0 en appuyant sur la touche <strong>R</strong>
	<br><br>
	<li>A tout moment le joueur peut se tenter au niveau suivant en appuyant sur la touche <strong>N</strong>. Si il n'y a plus de niveau, rien ne se passe de visible.
	<br><br>
	<li> Les unités sont marquées mortes lorsque leur nombre de point de vie atteint 0, on peut les différencier des autres par leur allure détruites ou des tombes. Si l'unité meurt sur une citée elle disparaîtra totalement.
	<br><br>
	<li> Lors de son tour le joueur peut passer son tour en appuyant sur <strong>TAB</strong>.
	<br><br>
	<li>Lors de la phase d'attaque, seules les attaques qui sont utilisables s'affichent (exemple : si les unités ennemies ne sont pas dans la range d'attaque de l'unité,l'option d' attaque ne sera pas affichée dans la liste d'attaque)
	<br><br>
	<li>Chaque case a un impact sur la défense (réduit les dégats pris) et sur la portée de tir de l'unité.
	<br><br>
	<li>En état normal le joueur peut appuyer sur la touche <strong>S</strong> si il est sur une unité et le shop s'ouvrira,  permettant d'acheter un objet pour cette unité si le joueur a assez d'argent.
	<br></br>
	<li>Chaque unité tuée rapporte 5 d'argent au joueur (l'argent est remis à zéro lors du changement de carte).
	<br></br>
	<li>Il est possible de capturer les cités (disponibles sur certaines cartes uniquement) en placant une unité dessus et utilisant l'action "catpurer" (touche <strong>C</strong>). Les cités capturées ramènent un nombre fixe de 4 pièces par tour(au début du tour). 
	
</ol>

</table>
<h1>ACtions</h1>
key : touche de clavier associée à l'attaque
<table>
  <tr>
  <th>ACTIONS (not all units have access to the same actions)
  <th>KEY
  <th>EFFET
  <tr>
  <tr>
  <td>attack 
  <td><strong>A</strong>
  <td>attaque l'unité sélectionnée
  <tr>
   <tr>
  <td>patch (healing) 
  <td><strong>P</strong>
  <td>soigne l'unité exécutant l'action
  <tr>
  <tr>
  <td>wait
  <td><strong>W</strong>
  <td>ne fait rien 
  <tr>
  <tr>
  <td>hack 
  <td><strong>H</strong>
  <td>augmente les points d'attaque de l'unité exécutant l'action et de l'unité  alliée ciblée
  <tr>
  <tr>
  <td>capture
  <td><strong>C</strong>
  <td>capture sur laquelle est l'unité
  <tr>
</table>

<h1>unités</h1>
<table>
<tr>
<th>NOMS
<th>ATTACK 
<th>MOVING RANGE
<th>ATTACK RANGE
<th>HP
<th>DAMAGE
</tr>
<tr>
<td>Tank
<td>Patch / Attack / Wait
<td>3
<td>5
<td>10
<td>7
</tr>
<tr>
<td>Soldier
<td>Attack / Wait
<td>2
<td>3
<td>6
<td>2
</tr>
<tr>
<td>Geek
<td>Attack / Wait / Hack
<td>6
<td>8
<td>7
<td>1
</tr>
<tr>
<td>Boat
<td>Attack / Wait
<td>7
<td>3
<td>7
<td>3
</tr>
</table>
<h1>Controles :</h1>
<table>
  <tr>
    <th>CONTROLES
    <th>KEY
  </tr>
  <tr>
    <td>select unit / confirm move / confirm attack
    <td>ENTER</td>
  </tr>
  <tr>
    <td>cancel moving / cancel select unit / end turn / exit shop
    <td>TAB
  </tr>
  <tr>
  <td> move cursor( left, right, down, up) / selecting in action (left,right)
  <td>ARROW KEYS
  </tr>
  <tr>
  <td>open shop 
  <td>S
  </tr>
  <tr>
  <td>move to next level 
  <td>N
  </tr>
  <tr>
  <td>restart game at first level 
  <td>R
  </tr>
  
</table>

<h1>TILES</h1>
<table>
  <tr>
  <th>TILE TITLE
  <th>RGB HEXCODE
  <th>defense augm
  <th>range malus
  </tr>
  <tr>
  <td>road
  <td>-116777216
  <td>0
  <td>0
  </tr>
  <tr>
  <td>plain
  <td>-14112955
  <td>1
  <td>0
  </tr>
  <tr>
  <td>wood
  <td>-65536
  <td>3
  <td>2
  </tr>
  <tr>
  <td>river
  <td>-16776961
  <td>0
  <td>1
  </tr>	
 <tr>
  <td>mountain
  <td>-256
  <td>4
  <td>3
  </tr>	
 <tr>
  <td>city
  <td>-1
  <td>2	
  <td>1
  </tr>
 <tr>
  <td>sand
  <td>-124534
  <td>1
  <td>1
  </tr>
 <tr>
  <td>pipe
  <td>-234
  <td>0
  <td>2
  </tr>			
</table>
<h1>Levels</h1>
<table>
<tr>
<th>Name
<th>tank number (par faction)
<th>soldier number (par faction)
<th>geek number(par faction)
<th>boat number (par faction)
<th>cities number
</tr>
<tr>
<tr>
<td>Level0
<td>1
<td>1
<td>0
<td>0
<td>0
</tr>
<tr>
<td>Level1
<td>2
<td>1
<td>1
<td>0
<td>2
</tr>
<tr>
<td>Level2
<td>1
<td>1
<td>2
<td>0
<td>4
</tr>
<tr>
<td>Level3
<td>1
<td>0
<td>1
<td>0
<td>1
</tr>
<tr>
<td>Level4
<td>2
<td>0
<td>0
<td>0
<td>6
</tr>
<tr>
<td>Level5
<td>0
<td>0
<td>0
<td>3
<td>0
</tr>
<tr>
<td>EPFLMAP (FOR FUN)
<td>23
<td>0
<td>0
<td>0
<td>0
<tr>
</table>
<h1>SHOP</h1>
<table>
<tr>
<th>OBJET
<th>PRIX
<th>EFFET
</tr>
<tr>
<td>Bière PG
<td>5
<td>rajoute 1 de dégat à l'unité sur laquelle l'objet est utilisé
</tr>
<tr>
<td>Potion
<td>4
<td>soigne de 3 hp l'unité sur laquelle l'objet est utilisé
</tr>
</table>
<h1>Map creation</h1>
<p>(INCLUS CAR A ETE UTILISE DANS LA CREATION DU JEU ACTUEL MAIS SUPPLEMENTAIRE)</p>
<p>script python permettant de créer semi-automatiquement les behaviors map et les background des cartes pour ICWars présent (dossier présent dans le fichier racine du projet)</p>
<h2>utilisation:</h2>
<ul>
<li>create_map.py
<ul>
<li>installer les dépendances requirements.txt disponible
<li>modifier le fichier map.txt avec les abréviations de chaque tile (voir exemple de map.txt)
<li>lancer le script et cela va créer deux images une map.png et une rgbMap.png qui sont respectivement la carte et la behavior associée a placé dans les ressources.
</ul>
<li>getcolor.py
<ul>
<li>permet de trouver la valeur rgb d'une couleur rgb en hexadécimal 
</ul>
<li>resize_assets.py
<ul>
<li>permet de redimensionner une image notemment les tiles
<ul>
<l
</ul>


