package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.players.AIPlayer;
import ch.epfl.cs107.play.game.actor.players.ICwarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICwarsArea;
import ch.epfl.cs107.play.window.Keyboard;

public abstract class ICwarsAction implements Graphics {
    //unit associated to the action
    private final Unit unit;
    //area associated to the action
    private final ICwarsArea area;
    //name of the action
    private final String name;

    private static boolean counting = true;
    private static float counter = 0.f;

    public ICwarsAction(Unit unit, ICwarsArea area, String name) {
        this.unit = unit;
        this.area = area;
        this.name = name;
    }


    /**
     * return the name of the action
     */
    public String getName() {
        return this.name;
    }

    /**
     * return the action that is associated to the action
     */
    protected Unit getActionUnit() {
        return this.unit;

    }

    public abstract boolean canBeUsed();

    /**
     * return the area associated to the action
     */
    protected ICwarsArea getActionArea() {
        return this.area;
    }

    /**
     * used to perform the action
     *
     * @param player   player that performs the action (unit belongs to this palyer)
     * @param keyboard used to interact in the action
     * @param dt       ?
     */
    abstract public void doAction(float dt, ICwarsPlayer player, Keyboard keyboard);

    /**
     * automatic action used by the ai
     *
     * @param dt     ?
     * @param player player that performs the action (unit belongs to this player)
     */
    abstract public void doAutoAction(float dt, AIPlayer player);

    protected boolean waitFor(float value, float dt) {

        if (counting) {
            counter += dt;
            if (counter > value) {
                counting = false;
                return true;
            }
        } else {
            counter = 0.f;
            counting = true;
        }
        return false;
    }

}
