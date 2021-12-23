<h1>Conceptions</h1>

<h2>Modifications et détails d'implémentation + ajouts (non extension)</h2>
<ul>
<li>Unités : 
<ul>
<li>Modification du nombre de hp maximum
<li> Ajout de la méthode increaseAttack permettant d'augmenter les dégats d'attaque de l'unité
<li>Pour représenter les actions d'une unité nous avons décidé d'utiliser une hashmap car son modèle de key value pair permet de bien représenter l'association de la touche de clavier associé à cette action. De plus ses actions peuvent rapidement être présentée sous forme d'array list sans la key pour différents usages notamment les fournir à la GUI pour les afficher.
<li>Nous avons aussi modifié le nombre d'unités demandées dans la deuxième zone, augmentant le nombre et la variété des unités présentes. 
</ul>
<li>ICWarsPlayer : 
<ul>
<li>Pour enregistrer les unités que le player possède nous avons décidé de simplement donner un nombre paramétrable dans le constructeur pour chaque type d'unité. Nous avons fais ce choix car chaque unité était créée de la même manière et que seul le nombre d'unités de chaque type que possède le joueur est important. Nous trouvons que c'est une meilleure implémentation qu'une elipse.
<li>Nous avons aussi automatisé le placement des unités du joueur. Elles sont placées en ligne droite horizontale à partir d'un point donné formant ainsi un "convoi". Nous avons choisi ceci car si le joueur possède un grand nombre d'unités, cela compliquede manière conséquente la disposition ordonnées d'unités.
<li> Nous avons créé une méthode <i>computeSpriteName</i> qui permet de déterminer le <i>path</i> du sprite du curseur en fonction de la faction (Il peut être <i>Ally</i> ou <i>Enemy</i>). Nous trouvions cela plus pratique que de contrôler la faction dans le constructeur pour attribuer le bon sprite.
<lu>
</ul>
<li>ICWars
<ul>
<li>Pour l'enchainement des joueurs nous avons décidé d'utiliser une  <i>arrayList</i> de tous les joueurs présent sur le jeu une <i>Deque</i> pour les joueurs attendant le prochain tour et une <i>Deque</i> pour les joueurs qui attendent de jouer. Cette liste permet de facilement intéragir sur tous les joueurs. Et les <i>Deque</i> permettent de bien représenter le concept de fil d'attente (First in first Out) et permettent d'aisémant effectuer certaines action demandées comme enlever et ajouter des unités au début et à la fin sans trop d'effort et sans ce soucier des indexes.  	
</ul>
<li>AiPlayer
<ul>
<li>Dans l'automatisation des attaques avons ajouté un contrôle; l'unité ne peut effectuer qu'une action par tour.
<li>Nous avons ajouté l'affichage du curseur d'attaque pour l'AIPlayer afin de rendre plus clair quelle unité est ciblée par l'IA.

</ul>
<li>ICWarsArea: ajout de <i>getAllySpawnUnit</i> et <i>getEnnemySpawnUnit</i> comme expliqué précédemment nous avons changé comment les unités spawn en leur donnant simplement un point de départ d'où une ligne est créée. Ces méthodes permettent de définir ce point propre à chaque <i>area</i> du jeu. Elles sont abstraites car doivent être redéfinies dans chaque extension de cette classe (Le point d'aparition dépend de la taille de la carte et de la volonté de position des unités du dévelopeur).
<li>Nous avons aussi ajouté une méthode abstraite par unité permettant de définir un nombre de chaque type d'unité. Ceci permet à nouveau de rendre plus facile la création de plusieurs niveau en traitant ce processus toujours de la même manière.
</ul>
<ul>
<li>Changement de zone et <i>reload</i>: Lorsque le joueur perd, il recommence automatiquement au début du premier niveau. il peut aussi choisir de passer au niveau suivant en appuyant sur "N".
</ul>
<li>Play
<ul>
<li>changement de la hauteur et largeur de la fenêtre (s'adapte à la taille de l'écran)
</ul>
<br></br>

<h1>Extensions</h1>
<p>Information : les détails non techniques des implémentations sur comment les utilisers sont disponibles dans le README.md</p>

<ol>
<li>Amélioration du déplacement des unités :
	<ul>
		<li>Cette extension empêche le realplayer de ce déplacer sur des unités adverse /alliée /morte ce qui avait pour effet d'annuler son déplacement (le laisser sur place)
		<li>Le realPlayer contrôle si il est en intéraction avec une unité lorsqu'il est en déplacement si c'est le cas une valeur canMoveUnit est mise à faux ce qui dans les contrôles pour initier le déplacement bloque le déplacement de l'unité. La valeure canMoveUnit est settée à vrai lorsqu'il est en contact avec une cellule libre.
	</ul>
<li>Mort de l'unité
	<ul>
	<li>Cette extension au lieu de faire disparaître les unités cela rajoute des sprites de mort à ces dernières qui ne disparaîssent donc pas (apparence détruite) et on ne peut pas intéragir avec ni ce déplacer dessu.
	<li>Pour ce faire nous utilisons simplement une méthode isDead() qui regarde si les hp de l'unité sont plus petits ou égal à 0 ce qui affiche un autre sprite dans draw.
	</ul>
<li>Ajout d'unité
<ul>
<li>Cette extension rajoute 2 unités les bateaux et les geeks
<li>Création analogue à une unité comme soldier + rajout d'action pérsonalisée
</ul>
<li>Ajout d'action:
<ul>
<li>Cette extension rajoute 3 actions : capture, hack et patch (voir readme pour effet)
<li>création analogues aux autres unitées comme tank etc...
</ul>

<li>ajout d'effet sonore:
<ul>
<li>rajoute un bruit de fond de guerre et des bruits pour certaines actions spécfique
<li>Nous avons créer notre propre classe audioHandler qui gère l'audio car nous n'avons pas trouvé la documentation de votre implémentation
<li>contenu de audioHandler
<ul>
<li>le constructeur prend en constructeur le fichier audio qu'il va jouer
<li>méthode playSound permet de player le clip lier à la path donnée dans le constructeur
</ul>
</ul>
<li> ajout d'un shop 
<ul>
<li>Cette extension rajoute un shop avec des objets achetable qui ont diverse effets sur les unités
<li>pour implémenter le shop nous avons créer
<ul> 
<li>une nouvelle classe gui dans le package gui gérant l'affichage (mis comme atribut dans icWarsPlayerGUi)
<li>Une classe abstraite représentant un objet du shop avec un prix un nom un effet et une fonction qui définit quand il est utilisable. Puis avons fais hériter de cette classe et définit concrètement les objets pour plusieurs objets du shop. Le tout dans un package dédié shop.
<li>une classe shop qu'on a attribué au ICWarsPlayer cette classe permet de gérer les intéractions clavier dans le shop.
<li>puis nous avons du implémenter un système de monnaie qui permet de gagner de l'argent avec les cités et les kills des unités.
</ul>
</ul>
<li>création de nouvelle carte
<ul>
<li> Cette extension rajoute un set de carte différentes qui ont chacunes un set d'unités spécifique et des backgrounds différents
<li>Pour créer les niveaux nous avons procédé de manière analogue aux autres
<li> création d'un tool python pour créer les behavior et le background (car on ne savait pas qu'il y'avait un outil de création de map déjà existant).
<li> ajout de nouvelles cellules à ces cartes
</ul>
<li> différenciation du rayon d'attaque et du rayon de déplacement
<ul>
<li>Cette extension différencie le rayon de déplacement de celui d'attaque les différents cellules affecte le rayon d'attaque.
<li>pour ce faire nous avons du rajouter :
<ul>
<li>un attribut pour le rayon d'attaque et une valeure qui garde celui initial pour le faire varier.
<li>un nouveau attribut aux types de cellules (obstacle) dans ICWarsBehavior
</ul>
</ul>
<li>Amélioration de l'IA:
<ul>
<li>cette extension ajoute une ia un peu plus intelligente elle peut utiliser l'action patch et ces déplacements sont moins "stupide" que celle initiallement prévu
<li>implémentation : simplement revu la méthode de déplacement
</ul>
<li>amélioration de l'affichage / utilisation des actions 
<ul>
<li>seuls les actions utilisables sont affichées dans l'écran d'action, et seuls celles utilisable peuvent être utilisée.
<li>implémentation ajout d'une méthode canBeUsed dans chaque action (au niveau d'abstraction des actions afin de les rédéfinir pour toutes
actions).

</ul>
<li>ajout de capture de cité
<ul>
<li>nous avons ajouté des cités qui peuvent être capturée par les joueurs et font gagner de l'argent au joueur par tour
<li>attribut capturedCity dans realPlayer qui est une liste des cités que le joueur a capturé
<li>nouvelles intéractions avec les cités qui permet de setter la cité sur laquelle l'unité est à l'attribut selectedCity.
<li>nouvelle action capture avec un canBeUsed adapté (seulement si l'unité est placé sur une cellule de type cité)
<li>nouvelle méthode dans ICWarsPlayer qui permet de payer le joueur selon le nombre de cité qu'il a capturé.
<li>méthode qui permet de changer le joueur qui possède la cité dans la classe City.
</ul>
<li> affichage en plus de range dans l'info panel
<ul>
<li>cette extension rajoute le rayon d'attaque de l'unité dans l'info panel
<li>simple modification dans le info panel GUI
</ul>
<li>Ajout d'une nouvelle faction
<ul>
<li>cette extension rajoute une troisième faction dans certaines map uniquement.
<li>pour implémenter ceci nous avons rajouter /créé:
<ul>
<li>une faction dans enum
<li>des sprites correspondant
<li>et des méthodes dans les areas qui permettent de savoir si il y'a un troisième joueur et une méthode donnant le point de spawn de ces unités et sont points de spawn.
</ul>
</ul>
</ol>

