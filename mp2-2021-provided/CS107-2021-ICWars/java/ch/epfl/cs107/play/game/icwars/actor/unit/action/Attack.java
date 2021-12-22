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


public final class Attack extends ICwarsAction {
    //name of the action
    public static final String NAME = "(A)attack";
    //selected unit index
    private int unitSelectedIndex = 0;
    //key associated to the action
    public static final int KEY = Keyboard.A;
    //sprite associated to the action
    private final ImageGraphics cursor = new ImageGraphics(ResourcePath.getSprite("icwars/UIpackSheet"), 1f, 1f, new RegionOfInterest(4 * 18, 26 * 18, 16, 16));

    //audio handler for the different unit attacks
    private final AudioPlayer tankAttack = new AudioPlayer("tankAttack");
    private final AudioPlayer soldierAttack = new AudioPlayer("soldierAttack");
    //indexes of the unit (in the area units list)
    ArrayList<Integer> indexes = new ArrayList<>();

    /**
     * constructor
     */
    public Attack(Unit unit, ICwarsArea area) {
        super(unit, area, Attack.NAME);
    }

    /**
     * handle action for the unit
     *
     * @param player   player that started the action
     * @param keyboard used to switch between units
     */
    @Override
    public void doAction(float dt, ICwarsPlayer player, Keyboard keyboard) {
        //key mapping for the action
        Button next = keyboard.get(Keyboard.RIGHT), back = keyboard.get(Keyboard.LEFT), attack = keyboard.get(Keyboard.ENTER), tab = keyboard.get(Keyboard.TAB);
        //units that are in the range of the unit that is doing the action
        this.indexes = getActionUnit().getAttackableUnits();
        //used to cycle through the ennemy units
        try {
            unitSelectedIndex %= indexes.size();
            Unit currentTarget = getActionArea().getSelectedUnit(indexes.get(Math.abs(unitSelectedIndex)));
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
        } catch (Exception ignored) {
            //error just in case if there is no player
            player.hasNotActed();
            player.centerCamera();
        }
    }


    /**
     * used to handle action for the ai
     */
    @Override
    public void doAutoAction(float dt, AIPlayer player) {
        //get units that are attackable in the range of the unit
        this.indexes = getActionUnit().getAttackableUnits();
        //search for unit that has the lowest life
        Unit currentTarget = lowestLife(indexes, this.getActionArea());
        //change the view to the target
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
        //take the damage
        target.takeDamage(getActionUnit().getDamage());
        //player made an action and unit too so disable act
        player.hasActed();
        getActionUnit().disableAct();
        //center back the camera on the player
        player.centerCamera();

        //check if the player killed the target in case he did the player earns money.
        if (target.isDead()) {
            player.killEarnsMoney();
        }
        //check for the unit name to play the sound
        if (getActionUnit().getName() == "Tank") {
            tankAttack.playSound();
        } else {
            soldierAttack.playSound();
        }
    }

    @Override
    public boolean canBeUsed() {
        //can be used if there is at least one attackable units
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
