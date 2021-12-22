package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.ICwars;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.ICwarsActor;
import ch.epfl.cs107.play.game.icwars.actor.city.City;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;

import static ch.epfl.cs107.play.math.DiscreteCoordinates.distanceBetween;

public abstract class ICwarsArea extends Area {
    private final ArrayList<Unit> units = new ArrayList<Unit>();
    private ArrayList<DiscreteCoordinates> cityCoordinates = new ArrayList<>();

    /**
     * Create the area by adding it all actors
     * called by begin method
     * Note it set the Behavior as needed !
     */
    protected abstract void createArea();


    @Override
    public final float getCameraScaleFactor() {
        return ICwars.CAMERA_SCALE_FACTOR;
    }

    /**
     * return the fisrt spawn point of the unit of the ally faction
     */
    public abstract DiscreteCoordinates getAllyUnitSpawn();

    /**
     * return the fisrt spawn point of the unit of the enemy faction
     */

    public abstract DiscreteCoordinates getEnnemyUnitSpawn();

    /**
     * return the  spawn point of the player of the ally faction
     */

    public abstract DiscreteCoordinates getPlayerSpawnPosition();

    /**
     * return the  spawn point of the player of the enem  faction
     */

    public abstract DiscreteCoordinates getEnnemySpawnPosition();

    public void addUnit(Unit unit) {
        units.add(unit);

    }

    /**
     * used to add cities to create
     */
    protected void addNewCityCoordinates(DiscreteCoordinates coordinates) {
        cityCoordinates.add(coordinates);

    }

    public void createCities() {
        for (DiscreteCoordinates i : cityCoordinates) {
            City a = new City(ICwarsActor.Faction.NEUTRAL, this, i);
            a.enterArea(this, i);
        }
    }

    public abstract int getNumberOfTank();

    public abstract int getNumberOfGeek();

    public abstract int getNumberOfSoldier();

    public abstract int getNumberOfBoat();


    /**
     * remove the whole list of units in area
     */

    //not used but may be useful
    public void removeUnits() {
        units.clear();
    }

    public void removeUnit(Unit unit) {
        this.units.remove(unit);
    }


    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            // Set the behavior map associated to this background
            ICwarsBehavior behavior = new ICwarsBehavior(window, getTitle());
            setBehavior(behavior);
            //create this area
            createArea();
            //if creation went well return true
            return true;
        }
        //return false if window didn't begin
        return false;
    }

    /**
     * @return unit by given index
     */
    public Unit getSelectedUnit(int index) {
        return this.units.get(index);
    }


    /**
     * return a list of all the units that are not in the same faction of the given faction
     *
     * @param faction faction of the unit you wan't to get the enemy
     */

    private ArrayList<Unit> getDifferentFactionUnit(ICwarsActor.Faction faction) {
        ArrayList<Unit> returnList = new ArrayList<>();
        for (Unit unit : units) {
            if (unit.getFaction() != faction) {
                returnList.add(unit);
            }
        }
        return returnList;
    }



    /**
     * compute the closest ennemy position from a given position and faction
     *
     * @param position position you wan't to compute from where you wan't to compute the nearest ennemy(origin)
     * @param faction  faction to get the enemy of this faction
     */
    public DiscreteCoordinates computePosition(DiscreteCoordinates position, ICwarsActor.Faction faction) {
        ArrayList<Unit> differentFactionUnit = getDifferentFactionUnit(faction);
        if (differentFactionUnit.size() <= 0) {
            return null;
        }
        DiscreteCoordinates closestPosition = differentFactionUnit.get(0).getCurrentMainCellCoordinates();
        float closest = distanceBetween(closestPosition, position);

        for (Unit unit : differentFactionUnit) {
            DiscreteCoordinates testPosition = unit.getCurrentMainCellCoordinates();
            float distance = distanceBetween(testPosition, position);
            if (distance < closest) {
                closest = distance;
                closestPosition = testPosition;
            }
        }
        return closestPosition;
    }


    /**
     * used to compute what is the closest enemy in order to attack in a given range
     *
     * @return an arrayList of indexes of the closest ennemy units from a given units return an empty list if none found
     * @unit ally unit that you want to find ennemy units around
     * @range range to check for ennemies
     */
    public ArrayList<Integer> getEnemyUnitsInAttackRange(Unit unit) {
        int range = unit.getAttackRange();
        DiscreteCoordinates coordinates = unit.getCurrentMainCellCoordinates();
        int maxX = coordinates.x + range + 1;
        int maxY = coordinates.y + range + 1;
        int minX = coordinates.x - range - 1;
        int minY = coordinates.y - range - 1;

        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < this.units.size(); i++) {
            Unit unity = this.units.get(i);
            if (unity.getFaction() != unit.getFaction()) {
                int x = unity.getCurrentMainCellCoordinates().x;
                int y = unity.getCurrentMainCellCoordinates().y;
                if ((x < maxX && x > minX) && ((y < maxY) && (y > minY))) {
                    indexes.add(i);
                }
            }
        }
        return indexes;

    }

    /**
     * get all the ally units that are in range of the given unit
     */

    public ArrayList<Integer> getAllyUnitsInAttackRange(Unit unit) {
        int range = unit.getAttackRange();

        DiscreteCoordinates coordinates = unit.getCurrentMainCellCoordinates();
        int maxX = coordinates.x + range + 1;
        int maxY = coordinates.y + range + 1;
        int minX = coordinates.x - range - 1;
        int minY = coordinates.y - range - 1;

        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < this.units.size(); i++) {
            Unit unity = this.units.get(i);
            if (unity.getFaction() == unit.getFaction() && unity != unit) {
                int x = unity.getCurrentMainCellCoordinates().x;
                int y = unity.getCurrentMainCellCoordinates().y;
                if ((x < maxX && x > minX) && ((y < maxY) && (y > minY))) {
                    indexes.add(i);
                }
            }
        }
        return indexes;

    }


}





