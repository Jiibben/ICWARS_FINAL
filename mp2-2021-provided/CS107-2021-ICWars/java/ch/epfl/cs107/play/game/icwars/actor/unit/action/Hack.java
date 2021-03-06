package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.audio.AudioPlayer;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.players.AIPlayer;
import ch.epfl.cs107.play.game.actor.players.ICwarsPlayer;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICwarsArea;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;


public final class Hack extends ICwarsAction {
    public static final String NAME = "(H)hack";
    public static final int KEY = Keyboard.H;
    //amout of attack increase the Hack provides
    private final int INCREASE_AMOUNT = 1;
    //sprite associated to the action
    private final ImageGraphics cursor = new ImageGraphics(ResourcePath.getSprite("icwars/UIpackSheet"), 1f, 1f, new RegionOfInterest(4 * 18, 26 * 18, 16, 16));
    //list of indexes of the concerned units (in the area)
    ArrayList<Integer> indexes = new ArrayList<>();
    //sound for the hack
    private final AudioPlayer hackSound = new AudioPlayer("hackSound");
    //index of the currently selected unit
    private int unitSelectedIndex = 0;

    public Hack(Unit unit, ICwarsArea area) {
        super(unit, area, Hack.NAME);
    }

    @Override
    public void doAction(float dt, ICwarsPlayer player, Keyboard keyboard) {
        //key mapping corresponding to the action bellow
        Button next = keyboard.get(Keyboard.RIGHT);
        Button back = keyboard.get(Keyboard.LEFT);
        Button attack = keyboard.get(Keyboard.ENTER);
        Button tab = keyboard.get(Keyboard.TAB);

        // get all the ally units in range that can be "attacked" in our case increase their damage by 1
        this.indexes = getActionUnit().getInteractableAllyUnits();
        try {
            unitSelectedIndex %= indexes.size();
            // cycle through ally unit
            Unit currentTarget = getActionArea().getSelectedUnit(indexes.get(Math.abs(unitSelectedIndex)));
            getActionArea().setViewCandidate(currentTarget);
            if (next.isPressed()) {
                unitSelectedIndex += 1;
            } else if (back.isPressed()) {
                unitSelectedIndex -= 1;
            } else if (attack.isPressed()) {
                //attack confirmed execute hack
                hack(currentTarget, player);
            } else if (tab.isPressed()) {
                //tab is pressed cancel the action
                player.hasNotActed();
                player.centerCamera();
            }
        } catch (Exception e) {
            //exception player couldn't hack
            player.hasNotActed();
            player.centerCamera();
        }
    }


    @Override
    public void doAutoAction(float dt, AIPlayer player) {
        //fonction is defined but not used in the ai maybe for a future update
        //get potentital targets
        ArrayList<Integer> indexesOfpotentialTarget = getActionUnit().getInteractableAllyUnits();
        //find a random index
        int randomIndex = (int) Math.round(Math.random() * indexesOfpotentialTarget.size());
        //get the random target and hack it
        Unit randomTarget = getActionArea().getSelectedUnit(indexesOfpotentialTarget.get(randomIndex));
        hack(randomTarget, player);
    }

    @Override
    public boolean canBeUsed() {
        //can be used if there is at least one ally unit in rnge of the unit
        if (getActionUnit().getInteractableAllyUnits().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * general hack mechanism increase damage
     * of the target unit by a fixed amount and to the unit that performs the action too
     *
     * @param player player that asked for the attack
     * @param target target unit to attack
     */
    private void hack(Unit target, ICwarsPlayer player) {
        //increase the attack of the unit that performed the action
        target.increaseAttack(INCREASE_AMOUNT);
        //increase attack of the unit that acted
        getActionUnit().increaseAttack(INCREASE_AMOUNT);
        //player has acted
        player.hasActed();
        //play the sound of the action
        hackSound.playSound();
        //center back camera on player
        player.centerCamera();
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.indexes.size() > 0) {
            cursor.setAnchor(canvas.getPosition().add(1, 0));
            cursor.draw(canvas);
        }
    }

}
