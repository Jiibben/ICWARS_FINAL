package ch.epfl.cs107.play.game.icwars.area;


import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


/**
 * Specific area
 */


public final class Level5 extends ICwarsArea {

    @Override
    public String getTitle() {
        return "icwars/Level5";
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(11, 4);
    }

    @Override
    public DiscreteCoordinates getEnnemySpawnPosition() {
        return new DiscreteCoordinates(10, 4);
    }

    @Override
    public int getNumberOfTank() {
        return 0;
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
        return 3;
    }

    @Override
    public boolean hasThreePlayer() {
        return false;
    }
    public DiscreteCoordinates getThirdPlayerSpawnPosition(){
        return null;
    }
    public DiscreteCoordinates getThirdPlayerUnitSpawn(){
        return null;
    }


    protected void createArea() {
        registerActor(new Background(this));
    }

    @Override
    public DiscreteCoordinates getAllyUnitSpawn() {
        return new DiscreteCoordinates(4, 5);
    }

    @Override
    public DiscreteCoordinates getEnnemyUnitSpawn() {
        return new DiscreteCoordinates(17, 5);
    }

}

