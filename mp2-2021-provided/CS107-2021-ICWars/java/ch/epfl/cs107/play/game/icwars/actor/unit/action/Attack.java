package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.audio.AudioPlayer;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.players.AIPlayer;
import ch.epfl.cs107.play.game.actor.players.ICwarsPlayer;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICwarsArea;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;


public class Attack extends ICwarsAction {
    //name of the action
    public static final String NAME = "(A)attack";
    //selected unit index
    private int unitSelectedIndex = 0;
    //key associated to the action
    public static final int KEY = Keyboard.A;
    //sprite associated to the action
    private final ImageGraphics cursor = new ImageGraphics(ResourcePath.getSprite("icwars/UIpackSheet"), 1f, 1f, new RegionOfInterest(4 * 18, 26 * 18, 16, 16));

    //audio handler for the attacks
    private final AudioPlayer tankAttack = new AudioPlayer("tankAttack");
    private final AudioPlayer soldierAttack = new AudioPlayer("soldierAttack");

    //indexes of the unit (in the area units list)
    ArrayList<Integer> indexes = new ArrayList<>();

    public Attack(Unit unit, ICwarsArea area) {
        super(unit, area, Attack.NAME);
    }

    @Override
    public void doAction(float dt, ICwarsPlayer player, Keyboard keyboard) {
        //key mapping for the action
        Button next = keyboard.get(Keyboard.RIGHT), back = keyboard.get(Keyboard.LEFT), attack = keyboard.get(Keyboard.ENTER), tab = keyboard.get(Keyboard.TAB);
        this.indexes = getActionUnit().getAttackableUnits();
        //used to cycle through the ennemy units
        try {
            unitSelectedIndex = Math.abs(unitSelectedIndex) % indexes.size();
            Unit currentTarget = getActionArea().getSelectedUnit(indexes.get(unitSelectedIndex));
            getActionArea().setViewCandidate(currentTarget);
            if (next.isPressed()) {
                unitSelectedIndex += 1;
            } else if (back.isPressed()) {
                unitSelectedIndex -= 1;
            } else if (attack.isPressed()) {
                //if attack button is pressed attack the selected unit
                attack(currentTarget, player);
            } else if (tab.isPressed()) {
                //if tab is pressed just cancel the action
                player.hasNotActed();
                player.centerCamera();
            }
        } catch (Error ignored) {
            //error maybe in case if there is no player
            player.hasNotActed();
            player.centerCamera();
        }
    }


    // used for ai
    @Override
    public void doAutoAction(float dt, AIPlayer player) {
        this.indexes = getActionUnit().getAttackableUnits();
        //search for unit that has the lowest life
        Unit currentTarget = lowestLife(indexes, this.getActionArea());
        getActionArea().setViewCandidate(currentTarget);
        if (waitFor(3, 1)) {
            attack(currentTarget, player);
        }


    }

    /**
     * return the unit with the less hp in list if equal just return the first one found
     */
    private Unit lowestLife(ArrayList<Integer> units, ICwarsArea area) {
        Unit unitToReturn = area.getSelectedUnit(units.get(0));
        int lowestLife = unitToReturn.getHp();
        for (int i : units) {
            if (area.getSelectedUnit(i).getHp() < lowestLife) {
                unitToReturn = area.getSelectedUnit(i);
            }
        }
        return unitToReturn;
    }

    /**
     * general attack mechanism inflict damage
     * to the target unit
     *
     * @param player player that asked for the attack
     * @param target target unit to attack
     */
    private void attack(Unit target, ICwarsPlayer player) {
        target.takeDamage(getActionUnit());
        getActionUnit().disableAct();
        //player made an action
        player.hasActed();
        //center back camera on the player
        player.centerCamera();
        if (getActionUnit().getName() == "Tank") {
            tankAttack.playSound();
        }else{
            soldierAttack.playSound();
        }
    }

    @Override
    public boolean canBeUsed() {
        return getActionUnit().getAttackableUnits().size() > 0;
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.indexes.size() > 0) {
            //draw the cursor at the right side of the selected unit to attack position
            cursor.setAnchor(canvas.getPosition().add(1, 0));
            cursor.draw(canvas);
        }
    }


}
