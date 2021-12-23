package ch.epfl.cs107.play.game.icwars.actor.city;

import ch.epfl.cs107.play.game.actor.players.ICwarsPlayer;
import ch.epfl.cs107.play.game.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.icwars.actor.ICwarsActor;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICwarsBehavior;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractorVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import static ch.epfl.cs107.play.game.actor.players.ICwarsPlayer.PlayerState.*;

public class City extends ICwarsActor implements Interactable {

    ICwarsPlayer owner = null;
    //Sprites
    private Sprite currentSprite;
    private final Sprite neutralSprite = new Sprite("icwars/neutralBuilding", 1.0f, 1.0f, this, null);
    private final Sprite enemySprite = new Sprite("icwars/enemyBuilding", 1.0f, 1.0f, this, null);
    private final Sprite allySprite = new Sprite("icwars/friendlyBuilding", 1.0f, 1.0f, this, null);
    private final Sprite outlawSprite = new Sprite("icwars/outlawBuilding", 1.0f, 1.0f, this, null);

    public City(Faction faction, Area area, DiscreteCoordinates position) {
        super(faction, area, position);
        currentSprite=neutralSprite;
    }

    /**
     * set the owner of the city and changes the sprite to match the player faction
     * used in the attack capture.
     */
    public void setPlayerOwner(ICwarsPlayer owner) {
        if (this.owner != null) {
            this.owner.removeCity(this);
        }
        this.owner = owner;
        owner.addCity(this);
        setFaction(owner.getFaction());
        if (owner != null) {
            if (owner.getFaction() == Faction.ALLY) {
                currentSprite=allySprite;
            } else if (owner.getFaction() == Faction.ENEMY) {
                currentSprite=enemySprite;
            } else if (owner.getFaction() == Faction.OUTLAW) {
                currentSprite=outlawSprite;
            }
        } else {
            currentSprite= neutralSprite;
        }


    }


    @Override
    public void draw(Canvas canvas) {

        currentSprite.draw(canvas);
    }


    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ICWarsInteractorVisitor) v).interactWith(this);
    }


}
