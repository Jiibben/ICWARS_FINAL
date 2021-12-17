package ch.epfl.cs107.play.game.icwars.area;


import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


/**
 * Specific area
 */


public class Level5 extends ICwarsArea {

    @Override
    public String getTitle() {
        return "icwars/Level5";
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(25, 3);
    }

    @Override
    public DiscreteCoordinates getEnnemySpawnPosition() {
        return new DiscreteCoordinates(40, 3);
    }

    @Override
    public int getNumberOfTank() {
        return 2;
    }

    @Override
    public int getNumberOfGeek() {
        return 2;
    }

    @Override
    public int getNumberOfSoldier() {
        return 2;
    }


    protected void createArea() {
        registerActor(new Background(this));
    }

    @Override
    public DiscreteCoordinates getAllyUnitSpawn() {
        return new DiscreteCoordinates(25, 6);
    }

    @Override
    public DiscreteCoordinates getEnnemyUnitSpawn() {
        return new DiscreteCoordinates(40, 6);
    }

}

