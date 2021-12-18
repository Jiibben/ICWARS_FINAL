
<h1>déroulements :</h1>
<ol>
	<li>Chaque unité peut être jouée 1 fois par tour comme ceci :
	<ol> 
		<p><p>
		<li>séléction de l' unité
		<li>déplacement de l'unité sélectionnée
		<li>action de l'unité déplacée
	</ol>
	<p><p>
	<li>une fois déplacée et ces étapes finies l'unité n'est plus déplacable et ne peut plus effectué d'action
	<br><br>
	<li>une fois le tour du joueur fini la main est passée au joueur suivant si il n'a plus d'unité le joueur restant est gagant
	<br><br>
	<li>à tout moment le joueur peut recommencer la partie à 0 en appuyant sur la touche <strong>R</strong>
	<br><br>
	<li>à tout moment le joueur peut se tenter au niveau suivant en appuyant sur la touche <strong>N</strong> si il n'y a plus de niveau rien ne se passe de visible.
	<br><br>
	<li> les unités sont marquées mortes lorsque leur nombre de point de vie atteint 0, on peut les différencier des autres par leur allure détruites ou des tombes
	<br><br>
	<li> lors de son tour le joueur peut passer son tour en appuyant sur <strong>TAB</strong>
	<br><br>
	<li>lors de la phase d'attaque seuls les attaques qui sont utilisables s'affichent (exemple : si les unités ennemies ne sont pas dans la range d'attaque de l'unité attaque ne sera pas affichée dans la liste d'attaque)
	<br><br>
	<li>le sol à un impacte sur la défense(réduit les dégats pris) et sur la portée de tir de l'unité
</ol>

</table>
<h1>ACtions</h1>
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
</table>

<h1>unités</h1>
<table>
<tr>
<th>NOMS
<th>ATTACK 
<th>MOVING RANGE
<th>ATTACK RANGE
<th>HP
</tr>
<tr>
<td>Tank
<td>Patch / Attack / Wait
<td>4
<td>5
<td>10
</tr>
<tr>
<td>Soldier
<td>Attack / Wait
<td>5
<td>3
<td>6
</tr>
<tr>
<td>Geek
<td>Attack / Wait / Hack
<td>6
<td>8
<td>7
</tr>
</table>
<h1>Controles :</h1>
<table>
  <tr>
    <th>CONTROLES
    <th>KEY
  </tr>
  <tr>
    <td>select unit / confirm move / confirm attack </td>
    <td>ENTER</td>
  </tr>
  <tr>
    <td>cancel moving / cancel select unit / end turn  </td>
    <td>TAB</td>
  </tr>
  <tr>
  <td> move cursor( left, right, down, up) / selecting in action (left,right)
  <td>ARROW KEYS
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