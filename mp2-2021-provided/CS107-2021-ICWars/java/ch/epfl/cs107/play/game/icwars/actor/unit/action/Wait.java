package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.actor.players.AIPlayer;
import ch.epfl.cs107.play.game.actor.players.ICwarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICwarsArea;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Wait extends ICwarsAction {
    private static final String NAME = "(W)ait";
    public static final int KEY = Keyboard.W;

    public Wait(Unit unit, ICwarsArea area) {

        super(unit, area, Wait.NAME);

    }

    @Override
    public void doAction(float dt, ICwarsPlayer player, Keyboard keyboard) {
        //execute the action
        this.wait(player);
    }

    //ai
    public void doAutoAction(float dt, AIPlayer player) {
        //execute the action
        this.wait(player);
    }

    @Override
    public boolean canBeUsed() {
        return true;
    }

    /**
     * wait just puts the unit like it has already acted but does nothing else
     */
    private void wait(ICwarsPlayer player) {
        //disable action on unit(it acted)
        getActionUnit().disableAct();
        //player has acted
        player.hasActed();

    }

    @Override
    public void draw(Canvas canvas) {
    }
}
