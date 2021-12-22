package ch.epfl.cs107.play.game.icwars.actor.unit.action;


import ch.epfl.cs107.play.game.actor.players.AIPlayer;
import ch.epfl.cs107.play.game.actor.players.ICwarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.city.City;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICwarsArea;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public final class Capture extends ICwarsAction {
    private static final String NAME = "(C)apture";
    public static final int KEY = Keyboard.C;


    public Capture(Unit unit, ICwarsArea area) {
        super(unit, area, NAME);
    }

    @Override
    public void doAction(float dt, ICwarsPlayer player, Keyboard keyboard) {
        //execute the action
        this.capture(player);
    }

    @Override
    public void doAutoAction(float dt, AIPlayer player) {
        //execute the action
        this.capture(player);
    }

    @Override
    public boolean canBeUsed() {
        return (getActionUnit().isOnCity() && (getActionUnit().getSelectedCity().getFaction() != getActionUnit().getFaction()));
    }

    /**
     *
     */
    private void capture(ICwarsPlayer player) {
        //disable action on unit(it acted)
        getActionUnit().disableAct();
        //check the city that the unit is on and set the player that acted as the new owner of the city
        City city = getActionUnit().getSelectedCity();
        city.setPlayerOwner(player);
        //player has acted
        player.hasActed();

    }

    @Override
    public void draw(Canvas canvas) {
    }
}
