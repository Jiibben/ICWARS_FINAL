package ch.epfl.cs107.play.game.icwars.actor;

//import ch.epfl.cs107.play.game.actor.players.AIPlayer;

import ch.epfl.cs107.play.game.actor.players.AIPlayer;
import ch.epfl.cs107.play.game.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.ICwarsAction;
import ch.epfl.cs107.play.game.icwars.area.ICwarsArea;
import ch.epfl.cs107.play.game.icwars.area.ICwarsBehavior;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractorVisitor;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.*;

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


    public Unit(Faction faction, Area area, DiscreteCoordinates position, String name, int movingRay, int maxHp, int damagePerAttack, String spriteName, int attackRay) {
        super(faction, area, position);
        this.name = name;
        this.movingRay = movingRay;
        this.attackRay = attackRay;
        this.initialAttackRay = attackRay;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.damagePerAttack = damagePerAttack;
        //sprite initialisation
        this.sprite = new Sprite(spriteName, 1.5f, 1.5f, this, null, new Vector(-0.25f, -0.25f));
    }

    /*
     * ------------------------------------------------
     *       fonctionality
     * --------------------------------------------
     * */

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

    //todo document this
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
     * @param unit unit that will inflict the damage to this one
     */
    public void takeDamage(Unit unit) {
        if (!(defense_stars > unit.damagePerAttack)) {
            this.hp = (this.hp - unit.damagePerAttack) + defense_stars;
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

    public ArrayList<Integer> getAttackableUnits() {
        return ((ICwarsArea) getOwnerArea()).getEnemyUnitsInAttackRange(this);
    }

    public ArrayList<Integer> getInteractableAllyUnits() {
        return ((ICwarsArea) getOwnerArea()).getAllyUnitsInAttackRange(this);
    }

    /**
     * increase the attack by a given amount
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
        //keyboard
        Keyboard keyboard = getOwnerArea().getKeyboard();
        for (int keyCode : actions.keySet()) {
            //iterate through possible action and listen for the associated key then if
            //a key associated to an action is pressed execute the action
            if (keyboard.get(keyCode).isPressed()) {
                ICwarsAction act = actions.get(keyCode);
                if (act.canBeUsed()) {
                    player.setAct(act);
                    player.startAction();
                }

            }
        }
    }

    //for ai player
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

    @Override
    public void draw(Canvas canvas) {
        this.sprite.draw(canvas);
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


    private class unitInteractionHandler implements ICWarsInteractorVisitor {

        public void interactWith(ICwarsBehavior.ICwarsCell cell) {
            //add defense point corresponding to the floor type
            defense_stars = cell.getType().getDefenseStar();
            //reduce the attack ray depending on the cell the unit is sitting on
            attackRay = initialAttackRay - cell.getType().getObstaclesStar();
        }

    }

}
