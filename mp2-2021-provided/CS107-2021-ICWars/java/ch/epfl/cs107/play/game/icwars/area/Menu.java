package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Menu extends ICwarsArea{
    @Override
    public String getTitle() {
        return "icwars/Menu";
    }

    @Override
    protected void createArea() {
        registerActor(new Background(this));
    }

    @Override
    public DiscreteCoordinates getAllyUnitSpawn() {
        return null;
    }

    @Override
    public DiscreteCoordinates getEnnemyUnitSpawn() {
        return null;
    }

    @Override
    public DiscreteCoordinates getThirdPlayerUnitSpawn() {
        return null;
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return null;
    }

    @Override
    public DiscreteCoordinates getEnnemySpawnPosition() {
        return null;
    }

    @Override
    public DiscreteCoordinates getThirdPlayerSpawnPosition() {
        return null;
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
        return 0;
    }

    @Override
    public boolean hasThreePlayer() {
        return false;
    }
}
