Modification et apport au projet de base :

ceci est un résumé des plus grandes modifications notamment les extensions mais les légères modifications de "gestion et petites fonctionalitées" ont été commentées et expliquée à la volée dans le code 


comme extension nous avons (leur fonctionnement est détaillé en dessou):

	- rajouté un type de cellule avec un nouveau sprite : sable (dispo au Level4)
	- rajouté des niveaux (3 pour l'intsant)
	- rajouté 2 actions : patch et hack
	- rajouté 1 unité  : le geek
	- modification sur : l'affichage, voir affichage en bas
	- passe au niveau supérieur uniquement si le realplayer gagne contre l'ia si il perd reload le niveau au debut, et quand plus de niveua fin du jeu
	- ajout d'un nombre d'unité variable selon le niveau en question
	- lègère amélioration de l'ia peut soigner son tank
		
	
	

ICWarsPlayer:
	- initialisation des unités: nous avons préféré gérer la création des unités 
	par un nombrepour chaque unité plûtot qu'une elipsise
	
	-coordonnées de spawn des unités nous avons décidé de les alloués de manière
	 automatique en donnant une première
	coordonnée et après en augmantant ca coordonnée x 1 par une ce qui forme une liste d'unité.
	
	-méthode computeSpriteName simple méthode qui ajoute friendly ou enemy selon la faction 
	au nom du sprite pour en faire une path du sprite.


AIPlayer :
	- selection des attaques nous avons ajouté un check si le joueur possède l'action en 
	question pour l'executé
	
	- nous avons aussi ajouté l'automatisation de l'action Patch cf (actions et tank) l'IA 
	utilisera cette action
	si l'unité à accès a cette action et si sont nombre de hp est <=2 (peut simplement être 
	modifié (attribut 
	PATCH THRESHOLD)
	



ICWarsArea:

	-deux méthodes permettant de trouver respectivement la liste 
	d'allié ou d'ennemy dans la zone d'attaque 
	d'une unité donnée
	
	-nous avons ajouté un nouveau type de cellule avec un nouveau sprite
	 (le sable présent au niveau 4)
	
	



ICWarsAction:
	nous avons ajouté deux attaques l'attaque hack utilisé par l'unité "geek" et l'attaque Patch.
	
		-Patch : "soigne" l'unité qui l'utilise d'un nombre fixe seul les tanks y ont accès dans notre jeu
		
		-hack :  est une attaque passive elle permet d'augmenter d'un nombre fixe les points d'attaque d'une unité 
		alliée sélectionnée et en même temps elle même. (Petit plus nous l'avons appelé hack pour représenter le 
		fait que le "geek" avait trouvé une faille de sécurité 
		chez les ennemis et la communique a une unité alliée)
		

Unités:
	nous avons ajouté une unité qui est le geek et une fonctionalité des unités 
	
		-geek : a l'attaque hack expliqué ci dessu (cf icwarsaction) et peu de vie 
		une grande portée et une faible attack
		
	fonctionalité :
	
		-distinction entre rayon de déplacement et rayon d'attaque
		- variation du rayon d'attaque selon le type de case sur lequel l'unité ce trouvem
	
	implementation:

	- pour les attaques nous avons décidé d'utiliser une hashmap avec comme clefs le button du clavier
	 associer à l'attaque comme valeur et
		
	
	
	
		
levels :

	-nous avons ajouté une méthode par type d'unité qui retourne le nombre d'unité de ce type dans la zone pour 
	créer le bon nombre d'unité associer a la zone
	
	- ajout de  niveaux supplémentaires
	
	-le level 0 a les unités de base soldat et tank
	
	-le level1 a 2 tank au lieu des 1 prevus initiallement 1 soldat et 1 geek 
	
	-le level2 a 1
	
	
	
	
affichage : 

	-seuls les attaques exécutables sont montrées donc attaques etc seulement affichée dans le actionpanel si dans la condition déterminée dans canBeUsed de l'action est vraie 
	
	-nous avons rajouté la range d'attaque dans l'affichage des informations de l'unité car cela nous 
	paraissait important
	
	-rajout du sprite sable dans le panel info
	
	- rajout de l'affichage du curseur lorsque l'ia attaque car permet plus de clareté
	
		
création de map :

	-créer des petits tools en python pour facilement créer des maps, présent au fichier racine du projet "mapcreation" (il y'a un requirements.txt pour installer la seule dépendance qui est numpy)
	
	- resise_asset : permet de redimensionner un élément donné 
	- getcolor.py : permet de trouver une couleur rgb depuis un code hexadécimal(comme dans le projet)
	- rotateImage.py : permet de tourner une image donnée
	
	
	-create_map.py : permet de créer un background de carte 
	(map.png par défaut) et sa behavior map associée (rgbMap.png par défaut)
	
		rapide guide sur l'utilisation : 
		
			1. créer un fichier map.txt dans le même dossier que create_map.py
			2. faire des lignes de lettre minuscule qui représente chacune un sprite d'un carré de map par exemple o pour rivière et r pour road et w pour wood etc ... (voir meaning dans la méthode) /!\ a ne pas laisser de ligne vide en bas du fichier
			
			3. runner le script (pour l'instant le programme n'accepte que des lignes de même taille )
			
			4. 
Key mapping :
	
      action :
	
	- attack : A
	- hack : H
	- patch : P
	
      other : 
	- moving : arrow key
	- next level : N
	- reload game : R
	 select unit : ENTER
	- validate attack : ENTER	
		
		
		
