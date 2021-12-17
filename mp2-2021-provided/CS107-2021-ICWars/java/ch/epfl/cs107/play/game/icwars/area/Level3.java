package ch.epfl.cs107.play.game.icwars.area;


import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


/**
 * Specific area
 */


public class Level3 extends ICwarsArea {

    @Override
    public String getTitle() {
        return "icwars/Level3";
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(0, 0);
    }

    @Override
    public DiscreteCoordinates getEnnemySpawnPosition() {
        return new DiscreteCoordinates(5, 3);
    }

    @Override
    public int getNumberOfTank() {
        return 1;
    }

    @Override
    public int getNumberOfGeek() {
        return 1;
    }

    @Override
    public int getNumberOfSoldier() {
        return 0;
    }


    protected void createArea() {
        registerActor(new Background(this));
    }

    @Override
    public DiscreteCoordinates getAllyUnitSpawn() {
        return new DiscreteCoordinates(2, 1);
    }

    @Override
    public DiscreteCoordinates getEnnemyUnitSpawn() {
        return new DiscreteCoordinates(8, 3);
    }

}

