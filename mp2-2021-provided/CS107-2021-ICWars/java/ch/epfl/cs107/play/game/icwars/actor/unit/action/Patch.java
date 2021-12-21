package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.audio.AudioPlayer;
import ch.epfl.cs107.play.game.actor.players.AIPlayer;
import ch.epfl.cs107.play.game.actor.players.ICwarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICwarsArea;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Patch extends ICwarsAction {
    private static final String NAME = "(P)patch";
    public static final int KEY = Keyboard.P;
    private static final int patchAmount = 3;
    private final AudioPlayer healSound = new AudioPlayer("healSound");

    public Patch(Unit unit, ICwarsArea area) {
        super(unit, area, Patch.NAME);
    }

    @Override
    public void doAction(float dt, ICwarsPlayer player, Keyboard keyboard) {
        //real player
        this.patch(player);
    }

    @Override
    public boolean canBeUsed() {
        //can be used only if the
        return getActionUnit().getMaxHp() > getActionUnit().getHp();
    }

    @Override
    public void doAutoAction(float dt, AIPlayer player) {
        //heal the currently selected unit
        this.patch(player);
    }

    /**
     * patch method used to perform the action
     */
    private void patch(ICwarsPlayer player) {
        //check if the unit is already at max hp or if heal is
        if (!canBeUsed()) {
            //playre didn't do the action cuz already full life
            player.hasNotActed();
        } else {
            healSound.playSound();
            getActionUnit().repair(Patch.patchAmount);
            //player did the action
            player.hasActed();
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}

