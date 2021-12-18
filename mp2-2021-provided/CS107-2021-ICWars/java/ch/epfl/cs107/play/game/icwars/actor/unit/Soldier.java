package ch.epfl.cs107.play.game.icwars.actor.unit;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.game.icwars.area.ICwarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


public class Soldier extends Unit {
    private final static int MAXHP = 6;
    private final static int DAMAGEPERATTACK = 2;

    //rayon de deplacement
    private final static int MOVINGRAY = 5;
    private final static String spriteName = "Soldier";
    //rayon d'attack
    private final static int ATTACKRANGE = 3;


    public Soldier(Faction faction, Area area, DiscreteCoordinates position, String name) {
        super(faction, area, position, name, Soldier.MOVINGRAY, Soldier.MAXHP, Soldier.DAMAGEPERATTACK, computeSpriteName(faction, spriteName), Soldier.ATTACKRANGE);
        //actions that the soldier have
        actions.put(Wait.KEY, new Wait(this, (ICwarsArea) (getOwnerArea())));
        actions.put(Attack.KEY, new Attack(this, (ICwarsArea) getOwnerArea()));

    }


}
