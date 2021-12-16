package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.Collections;
import java.util.List;


public abstract class ICwarsActor extends MovableAreaEntity {
    //enum used to define
    public enum Faction {
        ENEMY,
        ALLY
    }


    private final Faction faction;

    public ICwarsActor(Faction faction, Area area, DiscreteCoordinates position) {
        super(area, Orientation.UP, position);
        this.faction = faction;
    }

    /**
     * @return player faction
     */
    public Faction getFaction() {
        return this.faction;
    }

    @Override
    public DiscreteCoordinates getCurrentMainCellCoordinates() {
        return super.getCurrentMainCellCoordinates();
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    /**
     * register actor in area and set the position of the actor in the area
     *
     * @param area     area in which to register the actor
     * @param position position at which the unit enter
     */
    public void enterArea(Area area, DiscreteCoordinates position) {
        area.registerActor(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
    }

    /**
     * unregister actor from area
     */
    public void leaveArea() {
        getOwnerArea().unregisterActor(this);
    }


    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }


}
