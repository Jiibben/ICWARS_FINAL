package ch.epfl.cs107.play.game.icwars.area;


import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * Specific area
 */


public final class Level1 extends ICwarsArea {

    @Override
    public String getTitle() {
        return "icwars/Level1";
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(2, 5);
    }

    @Override
    public DiscreteCoordinates getEnnemySpawnPosition() {
        return new DiscreteCoordinates(17, 5);
    }

    @Override
    public int getNumberOfTank() {
        return 2;
    }

    @Override
    public int getNumberOfGeek() {
        return 1;
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
    public void createCities() {
        addNewCityCoordinates(new DiscreteCoordinates(5, 2));
        addNewCityCoordinates(new DiscreteCoordinates(15, 6));

        super.createCities();
    }

    @Override
    public DiscreteCoordinates getAllyUnitSpawn() {
        return new DiscreteCoordinates(2, 5);
    }

    @Override
    public DiscreteCoordinates getEnnemyUnitSpawn() {
        return new DiscreteCoordinates(12, 5);
    }

}
