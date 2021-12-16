package ch.epfl.cs107.play.game.actor.players;

import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Patch;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.game.icwars.area.ICwarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;

import static ch.epfl.cs107.play.game.actor.players.ICwarsPlayer.PlayerState.*;

public class AIPlayer extends ICwarsPlayer {
    private final float dt = 0.1f;
    private final int PATCH_THRESHOLD = 2;


    public AIPlayer(Faction faction, ICwarsArea owner, DiscreteCoordinates coordinates, int numberOfTank, int numberOfSoldier, int numberOfGeek, DiscreteCoordinates unitSpawn) {
        super(faction, owner, coordinates, numberOfTank, numberOfSoldier, numberOfGeek, unitSpawn);
        //create at idle state
        this.setState(IDLE);
    }


    /**
     * get the index of the avaible unit that can be moved
     *
     * @return the index of the first unit that is playable return -1 otherwise.
     */
    private int playableUnits() {
        for (int i = 0; i < getUnits().size(); i++) {
            if (getUnits().get(i).canMove()) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public void update(float deltaTime) {
        //automate used to handle the different state
        automate();
        super.update(deltaTime);
    }

    private void automate() {
        switch (this.getState()) {
            case IDLE:
                //handling idle state
                //idle set the player sprite to minimum opacity (invisible)
                getSprite().setAlpha(0f);
                break;
            case NORMAL:
                //handling normal state

                //idle set the player sprite(cursor) to maximum opacity (visible)
                getSprite().setAlpha(1f);
                handleNormal();

                break;
            case MOVE_UNIT:
                //handling move unit state
                handleMoveUnit();
                break;
            case ACTION_SELECTION:
                //handling action selection state
                handleActionSelection();
                break;
        }
    }


    /**
     * handle normal state check if there is a playable unit if that's the case select it and
     * change state to move unit otherwise change to idle
     */
    private void handleNormal() {
        //get the index of the playable units
        int unitIndex = playableUnits();
        //check if it found a playable unit
        if (unitIndex != -1) {
            //select the found unit
            selectUnit(unitIndex);
            //put the player in move unit
            setState(MOVE_UNIT);

        } else {
            setState(IDLE);
        }
    }

    /**
     * handle move unit move the currently selected unit to a computed position
     */

    private void handleMoveUnit() {
        //current area that the player is in
        ICwarsArea area = (ICwarsArea) getOwnerArea();
        //currently selected unit
        Unit unitToMove = getSelectedUnit();
        //current position of the selected unit
        DiscreteCoordinates unitToMovePosition = unitToMove.getCurrentMainCellCoordinates();
        //closest position from the nearest ennemy unit (position in movingrange of the unit)
        DiscreteCoordinates closestEnnemyPosition = area.computePosition(unitToMovePosition, getFaction());
        //check if the closestEnnemyPosition is not null (if there are no ennemy  by exemple)
        if (closestEnnemyPosition != null) {
            //move selected unit to the nearest position computed earlier
            DiscreteCoordinates positiontoMove = computeValidCell(closestEnnemyPosition, unitToMove);
            this.changePosition(positiontoMove);
            unitToMove.changePosition(positiontoMove);

        }
        //set the unit so that she can't move anymore
        unitToMove.disableMove();
        //change to actionselection state
        setState(ACTION_SELECTION);
    }

    /**
     * handle action selection select the suited attack for the selected unit and executes it
     */
    private void handleActionSelection() {
        //todo add auto hack
        ICwarsArea area = (ICwarsArea) getOwnerArea();
        Unit unitThatAttack = getSelectedUnit();
        ArrayList<Integer> potentialTarget = area.getEnemyUnitsInAttackRange(unitThatAttack);
        if (unitThatAttack.getHp() <= PATCH_THRESHOLD && unitThatAttack.hasAction(Patch.class)) {
            unitThatAttack.autoAction(this, Patch.KEY);
        } else if (potentialTarget.size() != 0 && unitThatAttack.hasAction(Attack.class)) {
            unitThatAttack.autoAction(this, Attack.KEY);
        } else {
            unitThatAttack.autoAction(this, Wait.KEY);
        }
    }

    /**
     * compute the closest position of a given ennemy position taking count of the moving range of the given unit
     *
     * @param unit     unit from which the coordinates will be calculated and the range used
     * @param enemyPos position of the enemy unit
     * @return closest position to the enemy in the range of the unit
     */
    private DiscreteCoordinates computeValidCell(DiscreteCoordinates enemyPos, Unit unit) {
        //set initial variable needed
        int range = unit.getMovingRange();
        //x and y of the enemy
        int enemyX = enemyPos.x;
        int enemyY = enemyPos.y;
        //current x and y of the unit
        int unitX = unit.getCurrentMainCellCoordinates().x;
        int unitY = unit.getCurrentMainCellCoordinates().y;
        //finalx and finaly are the x,y coords that will be returned after being computed
        int finalX = unitX;
        int finalY = unitY;
        //compute position
        if (unitX > enemyX) {
            while (enemyX + 1 != finalX && finalX > (unitX - range)) {
                finalX--;
            }
        } else if (unitX < enemyX) {
            while (enemyX - 1 != finalX && finalX <= (unitX + range)) {
                finalX++;
            }
        }
        if (unitY < enemyY) {
            while (enemyY - 1 != finalY && finalY < (unitY + range)) {
                finalY++;
            }
        } else if (unitY > enemyY) {
            while (enemyY + 1 != finalY && finalX > (unitY - range)) {
                finalY--;
            }
        }
        return new DiscreteCoordinates(finalX, finalY);

    }


    @Override

    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    @Override
    public boolean wantsCellInteraction() {
        return false;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }
}

