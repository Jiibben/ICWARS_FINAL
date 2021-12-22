package ch.epfl.cs107.play.game.icwars.actor.unit;

//import ch.epfl.cs107.play.game.actor.players.AIPlayer;

import ch.epfl.cs107.play.game.actor.players.AIPlayer;
import ch.epfl.cs107.play.game.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.ICwarsActor;
import ch.epfl.cs107.play.game.icwars.actor.city.City;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.ICwarsAction;
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.game.icwars.area.ICwarsArea;
import ch.epfl.cs107.play.game.icwars.area.ICwarsBehavior;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractorVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

public abstract class Unit extends ICwarsActor implements Interactor {
    //stats of the unit
    //name of the unit (added)
    private final String name;
    //maximum hp that the unit can have (used as hp at creation)
    private final int maxHp;
    //current health point of the unit
    private int hp;
    //damage that the unit does
    private int damagePerAttack;
    //moving range of the unit(max deplacement range)
    private final int movingRay;
    //attack range of the unit(max attack range)
    private int attackRay;
    //used for changing the attack ray
    private final int initialAttackRay;
    //sprite of the unit(image)
    private final Sprite sprite;
    private final Sprite deadSprite;
    //used to handle movement of the unit
    private ICWarsRange range;
    //determine if the player can move this unit
    private boolean canMove = true;
    //determine if the player can use an action of the unit
    private boolean canAct = true;
    // handler of the action for the given unit
    private final unitInteractionHandler handler = new unitInteractionHandler();
    // action hashmap Integer is a keyboard key see @Keyboard associated to an action
    protected final HashMap<Integer, ICwarsAction> actions = new HashMap<>();
    //defense_stars of the cell that the unit is sitting on
    private int defense_stars;
    //check if unit is on city
    private boolean isOnCity = false;
    //city that the uni is on
    private City selectedCity = null;


    public Unit(Faction faction, Area area, DiscreteCoordinates position, String name, int movingRay, int maxHp, int damagePerAttack, String spriteName, int attackRay) {
        super(faction, area, position);
        //set all the value
        this.name = name;
        this.movingRay = movingRay;
        this.attackRay = attackRay;
        this.initialAttackRay = attackRay;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.damagePerAttack = damagePerAttack;

        //sprite initialisation
        this.deadSprite = new Sprite(deadComputeSprite(name, faction), 1.5f, 1.5f, this, null, new Vector(-0.25f, -0.25f));
        this.sprite = new Sprite(spriteName, 1.5f, 1.5f, this, null, new Vector(-0.25f, -0.25f));
    }

    /*
     * ------------------------------------------------
     *       fonctionality
     * --------------------------------------------
     * */

    /**
     * used to compute the correct spritename
     *
     * @param faction faction of the unit
     * @param name    name of the unit
     */
    public String deadComputeSprite(String name, ICwarsActor.Faction faction) {
        String finalName = "icwars/broken";

        if (name.equals("Tank") || name.equals("Boat")) {
            if (faction == ICwarsActor.Faction.ALLY) {
                finalName = finalName + "Friendly" + name;
            } else if (faction == ICwarsActor.Faction.ENEMY) {
                finalName = finalName + "Enemy" + name;
            }
        } else {
            // exception security -> default sprite
            finalName = "icwars/soldierGrave";
        }
        return finalName;
    }

    /**
     * getter for isoncity return true if the player is on a city
     */
    public boolean isOnCity() {
        return isOnCity;
    }

    /**
     * set the city that the unit is on
     */
    private void setOnCity(boolean a) {
        isOnCity = a;
    }

    /**
     * transforme la hashmap en une liste des actions disponible dans cette hashmap
     * action (seulement la value) de la hashmap
     */
    public ArrayList<ICwarsAction> getActions() {
        ArrayList<ICwarsAction> returnActions = new ArrayList<>();
        for (int key : actions.keySet()) {
            ICwarsAction act = actions.get(key);
            if (act.canBeUsed()) {
                returnActions.add(act);
            }
        }
        return returnActions;
    }

    /**
     * return the selected city
     */
    public City getSelectedCity() {
        return selectedCity;
    }

    /**
     * return the maximum hp that the unit can have
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * usefull to get the correct spritename (faction) for the unit given the faction and the
     *
     * @param faction    of the unit
     * @param spriteName spritename of the unit
     */
    protected static String computeSpriteName(Faction faction, String spriteName) {
        String path = "icwars/";
        if (faction == Faction.ENEMY) {
            path += "enemy";
        } else {
            path += "friendly";
        }
        path += spriteName;
        return path;

    }

    /**
     * return true if the unit has this action
     *
     * @param checkAction action to check for
     */
    public boolean hasAction(Class checkAction) {
        for (ICwarsAction action : getActions()) {
            if (checkAction.isInstance(action)) {
                return true;
            }
        }
        return false;
    }

    /**
     * return moving range usefull for ai
     *
     * @return movingray
     */
    public int getMovingRange() {
        return movingRay;
    }

    public int getAttackRange() {
        return attackRay;
    }

    /**
     * @return name of the unit
     */
    public String getName() {
        return this.name;
    }

    /**
     * check if the unit must be considered dead
     *
     * @return true if the unit has 0 hp or lower otherwise false
     */
    public boolean isDead() {
        return this.hp <= 0;
    }

    /**
     * getter of hp
     *
     * @return an int representing the health point of the unit
     */
    public int getHp() {
        return hp;
    }

    /**
     * get the damage that the unit does
     */
    public int getDamage() {
        return damagePerAttack;
    }


    /**
     * take damage from another unit
     * damage are just a sub to the health point and we take the defesne star as"health point"
     *
     * @param damage damage to take "this" unit
     */
    public void takeDamage(int damage) {
        if (!(defense_stars > damage)) {
            this.hp = (this.hp - damage) + defense_stars;
        }

        if (this.hp < 0) {
            this.hp = 0;
        }
    }

    /**
     * repair the unit by a given amount
     *
     * @param amount amount to repair the unit
     */
    public void repair(int amount) {
        if (this.hp + amount > maxHp) {
            this.hp = this.maxHp;
        } else {
            this.hp += amount;
        }
    }

    /**
     * get all units that this unit can attack in his area
     *
     * @return a list of integer representing indexes of the attackable unit in the area
     */
    public ArrayList<Integer> getAttackableUnits() {
        return ((ICwarsArea) getOwnerArea()).getUnitsInRangeFromPosition(this.getCurrentMainCellCoordinates(), this.attackRay, this.getFaction(), false);
    }

    /**
     * get all ally units that this unit can interact with in his area
     *
     * @return a list of integer representing indexes of the interactable ally unit in the area
     */
    public ArrayList<Integer> getInteractableAllyUnits() {
        return ((ICwarsArea) getOwnerArea()).getUnitsInRangeFromPosition(this.getCurrentMainCellCoordinates(), this.attackRay, this.getFaction(), true);
    }

    /**
     * increase the attack by a given amount
     *
     * @param amount amount to increase the attack with
     */
    public void increaseAttack(int amount) {
        this.damagePerAttack += amount;
    }

    /*
     * --------------------------------------
     *
     *  action and moving handling (change if the unit can move or act)
     *
     * -----------------------------------
     * */
    public void disableMove() {
        this.canMove = false;
    }

    public void enableMove() {
        this.canMove = true;
    }

    public boolean canMove() {
        return canMove;
    }

    public void disableAct() {
        this.canAct = false;
    }

    public void enableAct() {
        this.canAct = true;
    }

    public boolean canAct() {
        return this.canAct;
    }

    /**
     * listen to the player keyboard if key associated to an action
     * is pressed set the action the the player and put the playre in action state
     *
     * @param player player associated to that unit
     */
    public void action(RealPlayer player) {
        //unit start action mode and listen for the keyboard on her given actions
        //keyboard
        Keyboard keyboard = getOwnerArea().getKeyboard();
        for (int keyCode : actions.keySet()) {
            //iterate through possible action and listen for the associated key then if
            //a key associated to an action is pressed execute the action
            if (keyboard.get(keyCode).isPressed()) {
                ICwarsAction act = actions.get(keyCode);
                if (act.canBeUsed()) {
                    //if the action can be used set the player action to the given action
                    player.setAct(act);
                    //player can now start acting on this action
                    player.startAction();
                }

            }
        }
    }

    /**
     * used in ai player so the unit can do actions
     */
    public void autoAction(AIPlayer player, int key) {
        player.setAct(actions.get(key));
        actions.get(key).doAutoAction(20f, player);
    }


    /*----------------------->
     *
     * range
     *
     *  <---------------------------*/


    /**
     * adding to the range the valid cell in the given range
     */
    private void addEdges() {
        range = new ICWarsRange();
        int maxHeight = this.getOwnerArea().getHeight() - 1;
        int maxWidth = this.getOwnerArea().getWidth() - 1;
        DiscreteCoordinates currentPosition = getCurrentMainCellCoordinates();

        int maxX = Math.min(maxWidth, currentPosition.x + movingRay);
        int minX = Math.max(0, currentPosition.x - movingRay);
        int maxY = Math.min(maxHeight, currentPosition.y + movingRay);
        int minY = Math.max(0, currentPosition.y - movingRay);
        boolean hasLeftEdge;
        boolean hasRightEdge;
        boolean hasDownEdge;
        boolean hasUpEdge;
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                hasRightEdge = x != maxX;
                hasLeftEdge = x != minX;
                hasUpEdge = y != maxY;
                hasDownEdge = y != minY;
                range.addNode(new DiscreteCoordinates(x, y), hasLeftEdge, hasUpEdge, hasRightEdge, hasDownEdge);
            }

        }
    }


    /**
     * used to draw the range to the destination
     *
     * @param destination destination
     * @param canvas
     */
    public void drawRangeAndPathTo(DiscreteCoordinates destination,
                                   Canvas canvas) {
        range.draw(canvas);
        Queue<Orientation> path =
                range.shortestPath(getCurrentMainCellCoordinates(),
                        destination);
        // Draw path only if it exists ( destination inside the range )
        if (path != null) {
            new Path(getCurrentMainCellCoordinates().toVector(),
                    path).draw(canvas);

        }
    }


    /**
     * returns true if a coordinates is in the valid range of diplacement of the unit
     *
     * @param coordinates coordinates of the cell you want to check
     * @return return true if the cell is in range otherwise no
     */
    public boolean isInRange(DiscreteCoordinates coordinates) {
        return range.nodeExists(coordinates);
    }


    @Override
    public void update(float deltaTime) {
        if (!this.canMove) {
            this.sprite.setAlpha(0.7f);
        } else if (!this.canAct) {
            this.sprite.setAlpha(0.5f);
        } else {
            this.sprite.setAlpha(1.f);
        }


        this.addEdges();
        super.update(deltaTime);
    }

    public void revive(){
        this.hp = this.maxHp;
    }

    @Override
    public void draw(Canvas canvas) {
        if (isDead()) {
            this.deadSprite.draw(canvas);
        } else {
            this.sprite.draw(canvas);
        }
    }


    @Override
    public boolean takeCellSpace() {
        return true;
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
        ((ICWarsInteractorVisitor) v).interactWith(this);
    }


    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler);

    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    //used for interaction with cells so
    private class unitInteractionHandler implements ICWarsInteractorVisitor {

        public void interactWith(ICwarsBehavior.ICwarsCell cell) {
            //add defense point corresponding to the floor type
            defense_stars = cell.getType().getDefenseStar();
            //reduce the attack ray depending on the cell the unit is sitting on
            attackRay = initialAttackRay - cell.getType().getObstaclesStar();
            //check if player is on a city or not and change the attribute that determines it
            setOnCity(cell.getType() == ICwarsBehavior.ICwarsCellType.CITY);

        }

        @Override
        public void interactWith(City city) {
            //select the city that the unit is on used for interaction
            selectedCity = city;

        }
    }

}
