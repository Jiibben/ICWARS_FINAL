package ch.epfl.cs107.play.game.actor.players;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.ICwarsActor;
import ch.epfl.cs107.play.game.icwars.actor.city.City;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.Boat;
import ch.epfl.cs107.play.game.icwars.actor.unit.Geek;
import ch.epfl.cs107.play.game.icwars.actor.unit.Soldier;
import ch.epfl.cs107.play.game.icwars.actor.unit.Tank;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.ICwarsAction;
import ch.epfl.cs107.play.game.icwars.area.ICwarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.List;

import static ch.epfl.cs107.play.game.actor.players.ICwarsPlayer.PlayerState.*;

public abstract class ICwarsPlayer extends ICwarsActor implements Interactor {
    //units that the player has
    private final ArrayList<Unit> units = new ArrayList<Unit>();
    //unit spawn (first point)
    private final DiscreteCoordinates unitSpawn;
    //currently selected unit
    private Integer selectedUnit;

    //Current state of the player
    private PlayerState state = PlayerState.IDLE;
    //sprite of the player
    private final Sprite sprite;
    //currently selected action of the player
    private ICwarsAction act;
    //deadUnits of the player
    private final ArrayList<Unit> deadUnits = new ArrayList<Unit>();
    //constant of money that the player earns per kill
    private final int MONEYPERKILL = 5;
    //money that a city gives each round
    private final int MONEYPERCITY = 4;
    //money that the player has (porte monnaie)
    private int money = 0;

    //city that the player captured
    private final ArrayList<City> capturedCity = new ArrayList<>();

    /**
     * used to remove a city from the list of city
     *
     * @param city city to remove from capturedCity list
     */
    public void removeCity(City city) {
        capturedCity.remove(city);
    }

    /**
     * add a city in the list of city that the player captured
     *
     * @param city city to add
     */
    public void addCity(City city) {
        capturedCity.add(city);
    }

    /**
     * enum used to keep track of the state of the players
     */
    public enum PlayerState {
        IDLE,
        NORMAL,
        SELECT_CELL,
        MOVE_UNIT,
        ACTION_SELECTION,
        ACTION,
        SHOPPING_SELECT,
        SHOPPING;
    }


    /**
     * remove money from player account
     */
    public void removeMoney(int amount) {
        if (!(amount > this.money)) {
            money -= amount;
        }

    }

    /**
     * get money that the player has
     */
    public int getMoney() {
        return this.money;
    }


    /**
     * know if the player is a real player or not must be redefined
     */
    public abstract boolean isRealPlayer();

    /**
     * used to compute the spritename associated to this player  by faction
     *
     * @param faction faction {@link PlayerState}
     */
    protected static String computeSpriteName(Faction faction) {
        String path = "icwars/";
        if (faction == Faction.ENEMY) {
            path += "enemy";
        } else {
            path += "ally";
        }
        path += "Cursor";
        return path;
    }


    /**
     * used to know if the player is still defeated or not which is determined by if he still has some units that are alive
     *
     * @return true if he is defeated false otherwise
     */

    public boolean isDefeated() {
        return units.size() <= 0;
    }

    /**
     * constructor of player
     *
     * @param faction         faction in which the player his {@link ch.epfl.cs107.play.game.icwars.actor.ICwarsActor.Faction}
     * @param area            in which area the player belongs
     * @param position        position to attribute the player
     * @param numberOfTank    number of tank to attribute the player at his creation
     * @param numberOfSoldier number of soldier to attribute the playrer at creation
     * @param unitSpawn       position in which units spawn
     */
    public ICwarsPlayer(Faction faction, ICwarsArea area, DiscreteCoordinates position, int numberOfTank, int numberOfSoldier, int numberOfGeek, int numberOfBoat, DiscreteCoordinates unitSpawn) {
        super(faction, area, position);
        this.unitSpawn = unitSpawn;
        this.sprite = new Sprite(computeSpriteName(faction), 1.f, 1.f, this);

        //création du nombre d'unité passé dans le constructeur (dépend de l'area en question)
        for (int tankN = 0; tankN < numberOfTank; tankN++) {
            DiscreteCoordinates newPosition = new DiscreteCoordinates(this.unitSpawn.x + units.size(), this.unitSpawn.y);
            Unit unit = new Tank(faction, getOwnerArea(), newPosition, "Tank");
            units.add(unit);

        }
        for (int soldierN = 0; soldierN < numberOfSoldier; soldierN++) {
            DiscreteCoordinates newPosition = new DiscreteCoordinates(this.unitSpawn.x + units.size(), this.unitSpawn.y);
            Unit unit = new Soldier(faction, getOwnerArea(), newPosition, "Soldier");
            units.add(unit);
        }
        for (int geekN = 0; geekN < numberOfGeek; geekN++) {
            DiscreteCoordinates newPosition = new DiscreteCoordinates(this.unitSpawn.x + units.size(), this.unitSpawn.y);
            Unit unit = new Geek(faction, getOwnerArea(), newPosition, "Geek");
            units.add(unit);

        }
        for (int boatN = 0; boatN < numberOfBoat; boatN++) {
            DiscreteCoordinates newPosition = new DiscreteCoordinates(this.unitSpawn.x + units.size(), this.unitSpawn.y);
            Unit unit = new Boat(faction, getOwnerArea(), newPosition, "Boat");
            units.add(unit);

        }
    }


    /*--------------------------->

            STATE HANDLING

    <----------------------------*/

    /**
     * returns the game state mainly used in ICwars for turn handling
     */
    public PlayerState getState() {
        return this.state;
    }

    /**
     * used to manualy set the state
     *
     * @param state player state to be set
     */
    protected void setState(PlayerState state) {
        this.state = state;

    }


    /**
     * if player made an action with one of his unit it sets back the player back to the normal state
     * used in {@link ch.epfl.cs107.play.game.icwars.actor.unit.action.ICwarsAction}
     */
    public void hasActed() {
        //permet de changer l'état courant du joueur à normal si il a fini une action
        this.setAct(null);
        this.setState(NORMAL);
    }

    /**
     * keep the player in action selection in case the action couldn't be completed used in {@link ch.epfl.cs107.play.game.icwars.actor.unit.action.ICwarsAction}
     */
    public void hasNotActed() {
        //permet de changer l'état du joueur à action_selection utilisé dans le cas ou une action n'abouti pas
        this.setState(ACTION_SELECTION);
    }

    /**
     * used to determine that it's the beginning of this player turn
     * also centers the camera on this player
     */
    public void startTurn() {
        //met le jouer en état normal au début de son tour et centre la camera sur ce dernier
        this.state = PlayerState.NORMAL;
        //centre la camera sur ce joueur
        centerCamera();
    }


    /* ---------------->

    units handling

    <------------- */

    /**
     * returns the list of units that the player has set as protected because only subclass and other players needs it
     */
    protected ArrayList<Unit> getUnits() {
        //unité que le joueur possède
        return this.units;
    }

    /**
     * get the currently selected unit and returns it
     */
    protected Unit getSelectedUnit() {
        return units.get(this.selectedUnit);
    }

    /**
     * make all player units movable and actable again
     */
    public void enableALlUnits() {
        // used at the end of a player turn and beginning of next player turn
        for (Unit unit : this.units) {
            //make every units that the player owns movable and actable
            unit.enableMove();
            unit.enableAct();
        }

    }

    /**
     * find the index of the given unit in player units
     *
     * @return return an index if found otherwise -1
     * @unit unit to return the index
     */
    protected int findUnitIndex(Unit unit) {
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i) == unit) {
                return i;
            }
        }
        return -1;
    }


    /**
     * set the current selected unit index
     *
     * @param i index in the list of units
     */
    protected void selectUnit(int i) {
        //change currently selected unit
        this.selectedUnit = i;
    }


    /**
     * unregister all units from the current area actors and removes them from the list of them kept in the owner area
     */
    private void unregisterUnits() {
        //désenregistre les unités que le joueur possède une par une
        for (Unit unit : this.units) {
            unregisterUnit(unit);
        }
        for (Unit unit : this.deadUnits) {
            unregisterUnit(unit);
        }

    }

    /**
     * deletes from list of unit in area and remove the unit of actors in area
     */
    private void unregisterUnit(Unit unit) {
        //méthode de désanregistrement d'une unité donnée
        unit.leaveArea();

        //enlève l'unité de la liste d'unité de l'aire dans laquelle il est
        ((ICwarsArea) getOwnerArea()).removeUnit(unit);
    }

    /**
     * registers the player units in the given area and adds them to the list of units in this area
     *
     * @param area area in which to register the units
     */
    private void registerUnits(Area area) {
        //spawn the units in a line by increasing the x coordinates by 1 every time a new unit is registered in the area
        int increase = 0;
        for (Unit unit : this.units) {
            DiscreteCoordinates coords = new DiscreteCoordinates(this.unitSpawn.x + increase, this.unitSpawn.y);
            registerUnit(unit, (ICwarsArea) area, coords);
            // augmente la coordonnée x de 1 à chaque unité enregistrée
            increase++;

        }
    }

    /**
     * register one unit in the area list of unit and has an actor in this area
     *
     * @param unit   unit to register
     * @param area   area in which to register unit
     * @param coords coordinates to attribute the unit at registration
     */
    private void registerUnit(Unit unit, ICwarsArea area, DiscreteCoordinates coords) {
        //enregistrement de l'unité
        unit.enterArea(area, coords);
        //ajout de l'unité de la liste de l'unité de  l'air d'appartenance du joueur
        area.addUnit(unit);
    }


    /**
     * deletes it from the player list of units and the area list of units but doesn't unregister it
     *
     * @param unit unit to delete and unregister
     */
    private void deleteUnit(Unit unit) {
        //désenregistre l'unité
        ((ICwarsArea) getOwnerArea()).removeUnit(unit);
        //enleve l'unité de la liste d'unité de l'air d'apartenance du joueur
        //enleve l'unité de la liste d'unité de l'air d'apartenance du joueur
        if (unit.isOnCity()) {
            //unregister the unit if on city so it doesn't take the place to recapture
            unit.leaveArea();
        }
        this.deadUnits.add(unit);
        this.units.remove(unit);
    }

    /**
     * add money to the players monney
     * so he can buy things in the shop
     */
    public void killEarnsMoney() {
        this.money += MONEYPERKILL;
    }

    /**
     * used to pay the user for the number of cities he owns
     */
    public void payForCities() {
        this.money += (this.MONEYPERCITY * capturedCity.size());
    }

    /**
     * remove dead units from the current list of unit
     */
    private void checkForDeadUnits() {
        ArrayList<Unit> deadUnits = new ArrayList<>();
        //itère sur toutes les unités qu'il possède si une unité est morte (hp <=0) elle est ajoutée à une liste de désenregitrement
        for (Unit unit : this.units) {
            if (unit.isDead()) {
                deadUnits.add(unit);
            }
        }
        //désenregistre les unités précédemment marquée comme morte
        for (Unit unit : deadUnits) {
            deleteUnit(unit);
        }

    }


    //--------------------------->

    //      Action handling
    //
    // <----------------------------

    /**
     * set the currently selected action
     *
     * @param action action to select
     */
    public void setAct(ICwarsAction action) {
        //permet de définir l'action sélectionnée
        act = action;
    }

    protected ICwarsAction getAct() {
        //retourn el'action sélectionnée par le joueur
        return act;
    }
    /* -------------------->

    player handling( most of methods are implementation or heritage )

     <-------------------------------*/

    protected Sprite getSprite() {
        return this.sprite;
    }

    /**
     * puts the player back in normal mode when he leaves a cell in SELECT MODE
     * {@link PlayerState}
     *
     * @param coordinates coordinates list
     */
    @Override
    public void onLeaving(List<DiscreteCoordinates> coordinates) {
        if (this.state == PlayerState.SELECT_CELL) {
            this.state = PlayerState.NORMAL;
        } else if (this.state == SHOPPING_SELECT) {
            this.state = PlayerState.NORMAL;
        }
    }

    /**
     * unregister player from area and it's units
     */
    @Override
    public void leaveArea() {
        unregisterUnits();
        super.leaveArea();
    }

    /**
     * center camera on the player
     */
    public void centerCamera() {
        getOwnerArea().setViewCandidate(this);
    }

    /**
     * register the player and it's units in the area
     *
     * @param area     area to register
     * @param position position to register
     */
    @Override
    public void enterArea(Area area, DiscreteCoordinates position) {
        registerUnits(area);
        super.enterArea(area, position);

    }


    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public void update(float deltaTime) {
        //check for dead units a each update
        checkForDeadUnits();
        checkForRevivedUnits();
        super.update(deltaTime);
    }

    /**
     * check if a unit was revived
     */
    public void checkForRevivedUnits() {
        ArrayList<Unit> unitToRemove = new ArrayList<>();
        for (Unit unit : deadUnits) {
            if (!unit.isDead()) {
                units.add(unit);
                ((ICwarsArea) getOwnerArea()).addUnit(unit);
                unitToRemove.add(unit);
            }
        }
        for (Unit i : unitToRemove) {
            deadUnits.remove(i);
        }
    }


    @Override

    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {

    }

    @Override
    public void interactWith(Interactable other) {

    }


    @Override
    public DiscreteCoordinates getCurrentMainCellCoordinates() {
        return super.getCurrentMainCellCoordinates();
    }


}