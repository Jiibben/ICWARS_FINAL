package ch.epfl.cs107.play.game.icwars.actor.unit;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Patch;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.game.icwars.area.ICwarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


public class Tank extends Unit {
    private final static int MAXHP = 10;
    private final static int DAMAGEPERATTACK = 7;
    private final static int MOVINGRAY = 3;
    private final static int ATTACKRANGE = 5;
    private final static String SPRITENAME = "Tank";

    //added name so you can change the unit name but no usage for this at the moment
    //todo change name
    public Tank(Faction faction, Area area, DiscreteCoordinates position, String name) {
        super(faction, area, position, name, Tank.MOVINGRAY, Tank.MAXHP, Tank.DAMAGEPERATTACK, computeSpriteName(faction, SPRITENAME), Tank.ATTACKRANGE);
        //action that the
        actions.put(Wait.KEY, new Wait(this, (ICwarsArea) (getOwnerArea())));
        actions.put(Attack.KEY, new Attack(this, (ICwarsArea) getOwnerArea()));
        actions.put(Patch.KEY, new Patch(this, (ICwarsArea) getOwnerArea()));
    }


}
