package ch.epfl.cs107.play.game.icwars.handler;

import ch.epfl.cs107.play.game.actor.players.ICwarsPlayer;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICwarsBehavior;

public interface ICWarsInteractorVisitor extends AreaInteractionVisitor {


    default void interactWith(Unit unit) {
    }

    default void interactWith(ICwarsPlayer player) {
    }

    default void interactWith(ICwarsBehavior.ICwarsCell cell) {
    }

}
