package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


/**
 * Specific area
 */


public class Level2 extends ICwarsArea {

    @Override
    public String getTitle() {
        return "icwars/Level2";
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(1, 6);
    }

    @Override
    public DiscreteCoordinates getEnnemySpawnPosition() {
        return new DiscreteCoordinates(7, 1);
    }

    @Override
    public int getNumberOfTank() {
        return 0;
    }

    @Override
    public int getNumberOfGeek() {
        return 2;
    }

    @Override
    public int getNumberOfSoldier() {
        return 1;
    }
    @Override
    public int getNumberOfBoat() {
        return 0;
    }


    protected void createArea() {
        registerActor(new Background(this));
    }

    @Override
    public DiscreteCoordinates getAllyUnitSpawn() {
        return new DiscreteCoordinates(1, 5);
    }

    @Override
    public DiscreteCoordinates getEnnemyUnitSpawn() {
        return new DiscreteCoordinates(8, 1);
    }

}
