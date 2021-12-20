<h1>Conceptions</h1>

<h2>Modifications et détails d'implémentation + ajouts (non extension)</h2>
<ul>
<li>Unités : 
<ul>
<li>modification du nombre de hp maximum
<li> ajout de increaseAttack permet d'augmenter les dégats d'attaque de l'unité
<li>pour représenter les actions d'une unité nous avons décidé d'utiliser une hashmap car son modèle de key value pair permet de bien représenter l'association de la touche de clavier associé à cette action de plus ses actions peuvent rapidement être présentée sous forme d'array list sans la key pour différent usage notamment les fournir à la GUI pour les afficher.
<li>nous avons aussi modifier le nombre d'unité demander dans la deuxième zone ajout d'un tank et d'un geek car la carte est grande donc pourquoi pas utilisé la place à disposition.
</ul>
<li>ICWarsPlayer : 
<ul>
<li>Pour enregistrer les unités que le player possède nous avons décidé de simplement donné un nombre paramétrable dans le constructeur pour chaque type d'unité. Nous avons fais ce choix car chaque unité était crée de la même manière et que seul le nombre d'unité de chaque type que possède le joueuer est important nous trouvons que c'est une meilleure implémentation qu'une elipse.
<li>Nous avons aussi automatisé le placement des unités du joeueur elles sont placées en ligne droite horizontale à partir d'un point donné cela forme un "convoi". Nous avons choisi ceci car si le joueuer possède un grand nombre d'unité comme notre dernière map avec 23 tank cela rend le placement plus facile.
<li> nous avons créer une méthode computeSpriteName qui permet de déterminer la path du sprite du curseur (sois enemy sois ally) même si elle n'était pas nécessaire nous trouvions ça plus pratique que de contrôler la faction dans le constructeur pour attribuer le bon sprite.
<lu>
</ul>
<li>ICWars
<ul>
<li>pour l'enchainement des joueurs nous avons décidé d'utiliser une  arrayListe de tous les joueurs présent sur le jeu une deque pour les joueurs attendant le prochain tour et une deque pour les joueurs qui attendent de jouer. Nous avons décidé d'implémenter ceci: car la liste permet de facilement intéragisr sur tous les joueurs. Et les deque permettent assez bien de représenter ce qui nous est demandé d'enlever et d'ajouter des unités au début et à la fin sans trop d'effort sans ce soucier des indexes. Cela représente aussi assez bien la file d'attente (first in first out).	
</ul>
<li>AiPlayer
<ul>
<li>Dans l'automatisation des attaques avons ajouté un contrôle de si l'unité possède l'action ou pas avant de l'exécuter.
<li>Nous avons ajouté l'affichage du curseur d'attaque pour l'AIPlayer comme ca cela rend plus clair quel unité a été ciblée par l'IA.

</ul>
<li>ICWarsArea: ajout de getAllySpawnUnit et getEnnemySpawnUnit comme expliqué précédemment nous avons changé comment les unités spawn en leur donnant simplement un point de départ ou une ligne est crée. Ces méthodes permettent de définir ce point propre à chaque area du jeu. Elles sont abstraites car doivent être redéfinies dans chaque extension de cette classe (point de spawn depend de la taille de la map et de la volonté de position des unités du dévelopeur )
<li>Nous avons aussi ajouté une méthode abstraites par unité permettant de définir un nombre de chaque type d'unité. Ceci permet à nouveau de rendre plus facile la création de plusieurs niveau car c'est toujours fais de la même manière.
</ul>
<ul>
<li>Changement de zone et reload: nous avons ajouté que lorsque le joueur perd il recommence automatiquement au début du premier niveau.
</ul>
<br></br>


<h1>Extensions</h1>
<p>information : les détails non technique des implémentations sont disponibles dans le readme</p>

<ol>
<li>Amélioration des déplacements d'unité : nous avons ajouté le fait que le joueur ne puisse pas se déplacer sur les unités alliées / ennemies / mortes car si coder comme dans le polycopié les unités ne se déplacaient simplement pas mais le déplacement était comptabilisé si on choisissait une case occupée par une autre unitée.(l'unité peut toujours être déplacée sur elle même pour ne pas bouger pendant son tour mais tout de même attaquer). Nous avons décidé d'implementer ceci car cela nous paraissait un comportement plus logique que celui initiallement prévu. Pour ce faire nous avons simplement ajouté un attribut boolean "canMoveUnit" dans la classe RealPlayer qui prend la valeur fausse si lorsque le joueur est en move unit et est sur une unit et est vrai si il est sur une cellue sans unitée. La valeure de canMoveUnit est donc controlée avant l'initialisation du mouvement de l'unité.
<br></br>
<li>Mort de l'unité : nous avons modifié la mort des unités au lieu de les faire disparaître leur sprite prend une apparence détruite et elles gisent sur l'emplacement de leur mort tout le long de la partie. /!\ étant des carcasses elles ne sont pas traversable donc le joueur allié ou adverse ne peut pas se déplacer dessu. (la case est occupée pour toute la durée de la partie). Pour ce faire nous avons simplement changé le sprite de l'unité et l'effaçons de la liste d'unité du joueur et de la liste d'unité de la liste mais ne la désenregistrons pas. Elle reste donc ici comme unitée "inerte" et non "intéractable"
<br></br>
<li>ajout d'unité le "geek" (uniquement disponible au niveau 2) nous avons ajouté l'unité geek qui est un soldier modifié avec moins d'attaque plus de mobilité et une attaque supplémentaire l'attaque "hack" discutée plus bas (voir readme). Pour ce faire nous avons simplement rajouté une classe Geek construite de manière analogue à la classe tank et soldier et dans le même package que les autres unités.
<br></br>
<li>Ajout d'une autre unité "boat"(visible uniquement au niveau 5) simple unité qui ne fait rien de spécial appart avoir un sprite différent des autres et un rayon d'attaque, nombre de dégat, point de vie propre à elle même (voir readme)
<br></br>
<li>Ajout d'une action hack : nous avons ajouté une action qui est uniquement utilisée par le geek elle permet d'augmenter les points de dégats d'une valeure donnée d'une unitée alliée dans la zone d'attaque de l'unité et augmente par la même occasion les dégats de l'unité qui utilise cette action. Nous avons décidé d'ajouter cette extension car le jeu portant le nom ICwars nous étions obligé d'inclure une référence à l'informatique. Pour implémenter ceci nous avons simplement rajouter une classe héritant de ICWarsAction analogue à attack dans le même dossier actions que cette dernière. Il a aussi fallu créer une méthode dans unités permettant d'augmenter les points de dégats de cette dernière. De plus il a fallu créer une méthode dans ICWarsArea permettant de trouver les alliés dans la range d'attaque de l'unité pour pouvoir intéragir avec eux et augmenter leurs dégats.
<br></br>
<li> Ajout d'une action patch. Cette action est une action uniquement utilisable par les tanks et elle permet de soigner l'unité sur laquelle elle est utilisée. Pour implémenter ceci nous avons simplement rajouté une classe héritant de ICWarsAction analogue à attack dans le même dossier actions que cette dernière.L'action utilise la méthode repair déjà présente du tank.
<br></br>
<li>ajout de son : Nous avons ajouté des effets sonore pour l'attaque du tank, l'attaque du soldat, et un soundtrack à volume très faible dans le jeu. Pour ce faire nous avons créer notre propre classe AudioHandler dans le package audio  qui prend simplement un fichier son à son constructeur et à une méthode playSound qui lance le song en question. Nous n'avons pas implémenté ceci avec le matériel fourni car nous n'avons trouvé aucune documentation (même dnas la javadoc) sur les fichiers sonores. Le but étant assez simple nous avons donc préféré construire notre propre classe sans créer d'interface ou de classe abstraite car elle a un unique but qui dans notre cas n'a pas besoin d'être étendu.
<br></br>
<li>ajout d'un shop : pour ajouter un peu d'intéraction dans notre jeu nous avons décidé d'ajouter un shop. (Voir readme sur utilisation et touche). Pour implémenter le shop nous avons procédé de manière analogue au déplacement d'unité c'est à dire rajouter deux états au joueur un premier état qui est SELECT_SHOPPING qui lorsqu'on est sur une unité la sélectionne passe en état shopping (comme move unit qui est dabord en select cell et puis après en move unit). La deuxième implémentation nécessaire était les objets du shop car notre but était que le shop vende différents objets nous avons donc créer un package dédié à ceci pour garder les choses en rapport au shop directement dans un package shop. Dans ce package nous avons mis une classe shop (nous y reviendrons plus tard) et un sous package shopitems dans ce sous package nous avons créer une classe abstraite ShopItem qui donne une idée générale de ce qu'un item vendu dans le shop aura besoin (prix, nom, et touche d'achat) et une méthode effet qui décrit l'action que l'objet acheté effectuera. Les objets du shop pourront donc en hériter. C'est le cas de la classe bierePG. Dans la classe Shop (élement qui gère l'achat des objets et l'exécution de l'effet) c'est ici ou on ajoute chaque objet dans le shop.Le joueur possède un attribut Shop.Pour gérer les éléments graphique du shop nous avons créer une classe ShopGUI dans le package GUI. Et avons créer un attribut ShopGui dans la classe ICWarsPlayerGUi qui se charge de l'afficher au moment nécessaire. Dernièrement nous avons ajouté un "porte monnaie " au player  à l'aide d'un attribut monnaie et chaque fois qu'une unité alliée fais un kill son joueur gagne de l'argent ce contrôle est fait directement dans l'attaque.
<br></br>
<li>ajout de niveaux (cartes/area) nous avons ajouté plusieurs niveaux de taille différentes et de nouveaux types de cellules. Notamment une carte totalement aquatique pour mettre nos batteaux et la dernière carte EPFL. Pour créer chaque carte il a dabord fallu créer les backgrounds des cartes et les behaviors correspondant. Pour ce faire nous avons créer un tool en python fourni en annexe (dossier mapcreation (voir readme). Le tool assemble différentes images de cellule ensemble et en fait une imagine finale et pour chaque type de cellule crée un pixel de couleur correspondante (voir enum dnas ICwarsBehavior) à la même position dans une autre image. Puis il a fallu créer les classes correspondant à chaque niveau en spécifiant les points de spawn des unités et joueur et le nombre d'unités plus à quel background/behavior l'associé.
<br></br>
<li>ajout de nouveux types de cellules nous avons ajouté plusieurs types de cellules notamment les pipes(tuyaux) et le sable. Nous avons donc du modifié la GUI du joueur pour ajouter l'affichage des sprites lorsque le joueur est sur la cellule. Pour implémenter les nouvelles cellules il a fallu les ajouter à des cartes (voir point 8) et l'ajouté dans l'enum de ICWarsBehavior avec un code rgb hexadécimal correspondant à celui qu'on a utilisé dans la behavior map pour la cellule donnée. 
<br></br>
<li>simple amélioration de l'ia : l'ia soigne son tank lorsqu'il atteint un nombre de point de vie fixé PATCH_THRESHOLD. Et elle contrôle si l'unité possède une action donnée avant de l'utilisé. 
<br></br>
<li>ajout d'affichage de victoire lorsque le joueur réussi un niveau pour ceci nous avons du créer une classe dans le package gui. Et avons créer un attribut de cette classe dans la playerGUI, lorsque le joueur gagne (déterminé dans ICwars)  une méthode hasWon est appelée sur le joueur ce qui signale à la PlayerGui que le joueur a gagné et qu'il faut afficher le carré rose et le texte de victoire avec les informations pour recommencer ou passer au niveau supérieur.	 (Pour pouvoir tout de même expérimenter toutes les cartes sans forcément réussir les précédentes nous avons laissé la possibilité de passer les niveaux avec la touche N sans avoir réussi le niveau)
<br></br>
<li>différentier le rayon d'attaque du rayon de déplacement : nous avons décidé de faire une distinction entre les deux car cela paraissait plus "réaliste et logique". Pour ce faire nous avons créer un nouvel attribut dans les units qui est attackRay et movingRay et avons adapté les méthodes qui avait besoin d'un des deux en conséquent.
<br></br>
<li>cellules impacte le rayon d'attaque : comme nous avons différentier les deux rayons (cf 12) nous avons décidé que les cellules aient un impact sur le rayon d'attaque indiqué par "obstacles" dans l'enum du type de cellule. L'unité vera son rayon d'attaque réduit sur certains types de cellules. Pour ce faire nous avons procédé de la même manière que pour les defenses stars mais pour les obstacles.
<br></br>
<li> affichage du rayon d'attaque dans l'info panel: nous avons décidé d'ajouter l'affichage du rayon d'attaque dans les informations quand le joueur ce positionne sur l'unité. Nous avons simplement rajouté une indication analogue a celle de l'affichage des points de vies dans le panel d'info mais pour le rayon d'attaque des unités.
<br></br>
<li>affichage des attaques uniquement lorsque <strong>utilisable</strong> : pour rnedre plus claire l'utilisation du jeu. Seuls les actions utilisable au moment de la sélection d'attaque sont présentés au joueueur (attaque si unités ennemies dnas le rayon d'attaque de l'unité ou patch seulement si les points de vies ne sont pas maximum).Pour ce faire nous avons  ajouté une méthode abstraite dans ICwarsAction qui permet de déterminer si l'action peut être utilisée ou non(propre implémentaiton à chaque attaque), si elle ne peut pas être utilisée elle n'est simplement pas donnée à la gui qui ne l'affiche donc pas.
<br></br>
</ol>