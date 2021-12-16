package ch.epfl.cs107.play.game.icwars.actor.unit;


import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Hack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.game.icwars.area.ICwarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


public class Geek extends Unit {

    //life
    private final static int MAXHP = 6;
    //damage of the unit
    private final static int DAMAGEPERATTACK = 1;

    //rayon de deplacement
    private final static int MOVINGRAY = 6;
    //sprite of the unit
    private final static String spriteName = "Rocket";
    //rayon d'attack
    private final static int ATTACKRANGE = 8;


    public Geek(Faction faction, Area area, DiscreteCoordinates position, String name) {
        super(faction, area, position, name, Geek.MOVINGRAY, Geek.MAXHP, Geek.DAMAGEPERATTACK, computeSpriteName(faction, spriteName), Geek.ATTACKRANGE);
        actions.put(Wait.KEY, new Wait(this, (ICwarsArea) (getOwnerArea())));
        actions.put(Attack.KEY, new Attack(this, (ICwarsArea) getOwnerArea()));
        actions.put(Hack.KEY, new Hack(this, (ICwarsArea) getOwnerArea()));

    }


}
//can hack the enemy and reveal the security breach he found in the enemy's defense to one of his ally it increases the ally damage he does by one