package ch.epfl.cs107.play.game.actor;


import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.ICwarsActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class DeadUnit extends AreaEntity {
    private final Sprite sprite;

    /**
     * Default AreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity in the Area. Not null
     * @param position    (DiscreteCoordinate): Initial position of the entity in the Area. Not null
     */
    //todo Resolve bug in area canEnter method (NullPointerException)
    public DeadUnit(Area area, Orientation orientation, DiscreteCoordinates position, String name, ICwarsActor.Faction faction) {
        super(area, orientation, position);
        sprite = new Sprite(computeSpriteName(name, faction), 1.0f, 1.0f, this);
    }

    /**
     * compute sprite choose the correct dead unit sprite
     */
    public String computeSpriteName(String name, ICwarsActor.Faction faction) {
        String finalName = "icwars/broken";

        if (name == "Tank") {
            if (faction == ICwarsActor.Faction.ALLY) {
                finalName = finalName + "Friendly" + name;
            } else if (faction == ICwarsActor.Faction.ENEMY) {
                finalName = finalName + "Enemy" + name;
            }
        } else {
            // exception security -> default sprite
            finalName = "icwars/soldierGrave";
        }
        return finalName;
    }

    public void enterArea(Area area, DiscreteCoordinates position) {
        area.registerActor(this);

        setOwnerArea(area);
        setCurrentPosition(position.toVector());

    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());

    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {

    }
}
