package ch.epfl.cs107.play.game.icwars.area;


import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.icwars.actor.ICwarsActor;
import ch.epfl.cs107.play.game.icwars.actor.city.City;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;

/**
 * Specific area
 */
public final class Level0 extends ICwarsArea {
    @Override
    public String getTitle() {
        return "icwars/Level0";
    }


    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(0, 0);
    }

    @Override
    public DiscreteCoordinates getEnnemySpawnPosition() {
        return new DiscreteCoordinates(7, 4);
    }

    @Override
    public int getNumberOfTank() {
        return 1;
    }

    @Override
    public int getNumberOfGeek() {
        return 0;
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
        return new DiscreteCoordinates(
                2, 5);
    }

    @Override
    public DiscreteCoordinates getEnnemyUnitSpawn() {
        return new DiscreteCoordinates(8, 5);
    }

}

