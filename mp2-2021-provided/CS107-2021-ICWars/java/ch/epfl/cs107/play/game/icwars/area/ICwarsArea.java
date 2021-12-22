package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.ICwars;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.ICwarsActor;
import ch.epfl.cs107.play.game.icwars.actor.city.City;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;

import static ch.epfl.cs107.play.math.DiscreteCoordinates.distanceBetween;

public abstract class ICwarsArea extends Area {
    private final ArrayList<Unit> units = new ArrayList<Unit>();
    private ArrayList<DiscreteCoordinates> cityCoordinates = new ArrayList<>();
    ICwarsBehavior behavior;

    /**
     * Create the area by adding it all actors
     * called by begin method
     * Note it set the Behavior as needed !
     */
    protected abstract void createArea();

    /**
     * used to check if a unit can move on the position
     */
    public boolean canMoveUnitOnPosition(Unit unit, DiscreteCoordinates position) {
        ArrayList<DiscreteCoordinates> positions = new ArrayList<>();
        positions.add(position);
        return behavior.canEnter(unit, positions);

    }

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
            behavior = new ICwarsBehavior(window, getTitle());
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
    public DiscreteCoordinates closestEnnemyUnitFromPosition(DiscreteCoordinates position, ICwarsActor.Faction faction) {
        ArrayList<Unit> differentFactionUnit = getDifferentFactionUnit(faction);
        //return null if no units where found
        if (differentFactionUnit.size() <= 0) {
            return null;
        }
        //set the closest to the first in olist
        DiscreteCoordinates closestPosition = differentFactionUnit.get(0).getCurrentMainCellCoordinates();
        //compute distance
        float closest = distanceBetween(closestPosition, position);
        //iterate through all the ennemy units to see if there is a unit closer that the one set above
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
     * @param range       range in which to check for the units
     * @param faction     faction used in order to determine if unit is the same faction or other faction
     * @param sameFaction true if you wan't ally units index false otherwise
     * @return an arrayList of indexes of the closest ennemy units from a given units return an empty list if none found
     */
    public ArrayList<Integer> getUnitsInRangeFromPosition(DiscreteCoordinates coordinates, int range, ICwarsActor.Faction faction, boolean sameFaction) {
        //determine the maximums and minimum of the range
        int maxX = coordinates.x + range + 1, maxY = coordinates.y + range + 1, minX = coordinates.x - range - 1, minY = coordinates.y - range - 1;

        ArrayList<Integer> sameFactionUnit = new ArrayList<>();
        ArrayList<Integer> otherFactionUnit = new ArrayList<>();

        for (int i = 0; i < this.units.size(); i++) {
            Unit unit = this.units.get(i);
            //x,y position of the unit
            int x = unit.getCurrentMainCellCoordinates().x;
            int y = unit.getCurrentMainCellCoordinates().y;
            //check if it's in the range
            if ((x < maxX && x > minX) && ((y < maxY) && (y > minY))) {
                if (faction == unit.getFaction()) {
                    sameFactionUnit.add(i);
                } else {
                    otherFactionUnit.add(i);

                }
            }
        }
        if (sameFaction) {
            return sameFactionUnit;
        } else {
            return otherFactionUnit;
        }
    }


}





