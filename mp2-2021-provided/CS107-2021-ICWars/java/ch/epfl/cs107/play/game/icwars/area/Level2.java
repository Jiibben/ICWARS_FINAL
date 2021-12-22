package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


/**
 * Specific area
 */


public final class Level2 extends ICwarsArea {

    @Override
    public String getTitle() {
        return "icwars/Level2";
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(2, 5);
    }

    @Override
    public DiscreteCoordinates getEnnemySpawnPosition() {
        return new DiscreteCoordinates(7, 1);
    }

    @Override
    public int getNumberOfTank() {
        return 1;
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

    @Override
    public void createCities() {
        //cities position
        addNewCityCoordinates(new DiscreteCoordinates(5, 8));
        addNewCityCoordinates(new DiscreteCoordinates(5, 2));
        addNewCityCoordinates(new DiscreteCoordinates(16, 8));
        addNewCityCoordinates(new DiscreteCoordinates(16, 2));
        super.createCities();
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
        return new DiscreteCoordinates(17, 5);
    }

}
