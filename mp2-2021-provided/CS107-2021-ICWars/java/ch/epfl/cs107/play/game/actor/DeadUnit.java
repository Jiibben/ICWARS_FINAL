package ch.epfl.cs107.play.game.actor;


import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.ICwarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICwarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class DeadUnit extends ICwarsActor {
    private final Sprite sprite;
    private boolean posee = false;


    public DeadUnit(DiscreteCoordinates position, String name, ICwarsActor.Faction faction, Area area) {
        super(faction, area, position);
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

    public boolean isPosee() {
        return posee;
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }


    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public void enterArea(Area area, DiscreteCoordinates position) {
        posee = true;
        super.enterArea(area, position);
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

