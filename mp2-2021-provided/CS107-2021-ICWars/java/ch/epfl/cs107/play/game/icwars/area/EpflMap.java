package ch.epfl.cs107.play.game.icwars.area;


import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * Specific area
 */
public class EpflMap extends ICwarsArea {

    @Override
    public String getTitle() {
        return "icwars/epflMap";
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(11, 5);
    }

    @Override
    public DiscreteCoordinates getEnnemySpawnPosition() {
        return new DiscreteCoordinates(0, 0);
    }

    @Override
    public int getNumberOfTank() {
        return 23;
    }

    @Override
    public int getNumberOfGeek() {
        return 0;
    }

    @Override
    public int getNumberOfSoldier() {
        return 0;
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
        return new DiscreteCoordinates(
                0, 10);
    }

    @Override
    public DiscreteCoordinates getEnnemyUnitSpawn() {
        return new DiscreteCoordinates(0, 1);
    }

}

