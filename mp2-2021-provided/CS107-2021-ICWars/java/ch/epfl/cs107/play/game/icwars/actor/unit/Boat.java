package ch.epfl.cs107.play.game.icwars.actor.unit;


import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Capture;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.game.icwars.area.ICwarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


public final class Boat extends Unit {

    //life
    public final static int MAXHP = 7;
    //damage of the unit
    public final static int DAMAGEPERATTACK = 3;

    //rayon de deplacement
    public final static int MOVINGRAY = 7;
    //sprite of the unit
    public final static String spriteName = "Boat";
    //rayon d'attack
    public final static int ATTACKRANGE = 3;


    public Boat(Faction faction, Area area, DiscreteCoordinates position, String name) {
        super(faction, area, position, name, MOVINGRAY, MAXHP, DAMAGEPERATTACK, computeSpriteName(faction, spriteName), ATTACKRANGE);
        //added actions
        actions.put(Wait.KEY, new Wait(this, (ICwarsArea) (getOwnerArea())));
        actions.put(Attack.KEY, new Attack(this, (ICwarsArea) getOwnerArea()));
        actions.put(Capture.KEY, new Capture(this, (ICwarsArea) getOwnerArea()));

    }

}
