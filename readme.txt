Modification et apport au projet de base :

ceci est un résumé des plus grandes modifications mais les légères modifications de "gestion et petites fonctionalitées" ont été commentées et expliquée à la volée dans le code 

ICWarsPlayer:
	- initialisation des unités: nous avons préféré gérer la création des unités 
	par un nombrepour chaque unité plûtot qu'une elipsise
	
	-coordonnées de spawn des unités nous avons décidé de les alloués de manière
	 automatique en donnant une première
	coordonnée et après en augmantant ca coordonnée x 1 par une ce qui forme une liste d'unité.
	
	-méthode computeSpriteName simple méthode qui ajoute friendly ou enemy selon la faction 
	au nom du sprite pour en faire une path du sprite.

AIPlayer :
	- selection des attaques nous avons ajouté un check si le joueur possède l'action en question pour l'executé
	
	- nous avons aussi ajouté l'automatisation de l'action Patch cf (actions et tank) l'IA utilisera cette action
	si l'unité à accès a cette action et si sont nombre de hp est <=2 (peut simplement être modifié (attribut 
	PATCH THRESHOLD)
	

ICWarsArea:

	-deux méthodes permettant de trouver respectivement la liste d'allié ou d'ennemy dans la zone d'attaque 
	d'une unité donnée



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

	pour les attaques nous avons décidé d'utiliser une hashmap avec comme clefs le button du clavier
	 associer à l'attaque comme valeur et
		
		
levels :

	-nous avons ajouté une méthode par type d'unité qui retourne le nombre d'unité de ce type dans la zone
	
	-le level 0 a les unités de base soldat et tank
	
	-le level1 a 2 tank au lieu des 1 prevus initiallement 1 soldat et 1 geek 
	
	
affichage : 

	-seuls les attaques exécutables sont montrées donc attaques etc seulement affichée dans le actionpanel si dans la condition déterminée dans canBeUsed de l'action est vraie 
	
	-nous avons rajouté la range d'attaque dans l'affichage des informations de l'unité
	
		
		


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
		
		
		
