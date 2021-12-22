package ch.epfl.cs107.play.game.actor.players;

import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Patch;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.game.icwars.area.ICwarsArea;
import ch.epfl.cs107.play.game.icwars.area.ICwarsBehavior;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.List;

import static ch.epfl.cs107.play.game.actor.players.ICwarsPlayer.PlayerState.*;

public class AIPlayer extends ICwarsPlayer {
    //hp threshold to know when the player should heal it's unit
    private final int PATCH_THRESHOLD = 2;
    //used in wait for
    private static boolean counting = true;
    private static float counter = 0.f;


    public AIPlayer(Faction faction, ICwarsArea owner, DiscreteCoordinates coordinates, int numberOfTank, int numberOfSoldier, int numberOfGeek, int numberOfBoat, DiscreteCoordinates unitSpawn) {
        super(faction, owner, coordinates, numberOfTank, numberOfSoldier, numberOfGeek, numberOfBoat, unitSpawn);
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
    public boolean isRealPlayer() {
        //return false cause it's a bot
        return false;
    }

    @Override
    public void update(float deltaTime) {
        //automate used to handle the different state
        automate();
        super.update(deltaTime);
    }

    /**
     * gère les différents états du joueur
     */
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
                if (waitFor(10f, 1f)) {
                    handleMoveUnit();
                }
                break;
            case ACTION_SELECTION:
                //handling action selection state
                if (waitFor(10f, 1f)) {
                    handleActionSelection();
                }
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
        DiscreteCoordinates closestEnnemyPosition = area.closestEnnemyUnitFromPosition(unitToMovePosition, getFaction());
        //check if the closestEnnemyPosition is not null (if there are no ennemy  by exemple should not happen but just in case)
        if (closestEnnemyPosition != null) {
            //move selected unit to the nearest position computed earlier
            DiscreteCoordinates positiontoMove = computeValidCell(closestEnnemyPosition, unitToMove, unitToMove.getMovingRange());
            unitToMove.changePosition(positiontoMove);
            this.changePosition(getSelectedUnit().getCurrentMainCellCoordinates());

        }
        //set the unit so that she can't move anymore
        unitToMove.disableMove();
        //change to actionselection state
        setState(ACTION_SELECTION);
    }

    /**
     * compute the closest position of a given ennemy position taking count of the moving range of the given unit
     *
     * @param unit     unit from which the coordinates will be calculated and the range used
     * @param enemyPos position of the enemy unit
     * @return closest position to the enemy in the range of the unit
     */
    private DiscreteCoordinates computeValidCell(DiscreteCoordinates enemyPos, Unit unit, int range) {
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
        DiscreteCoordinates finalCoordinates = new DiscreteCoordinates(finalX, finalY);
        //decreese range if it's not a valid position and try
        if (((ICwarsArea) getOwnerArea()).canMoveUnitOnPosition(unit, finalCoordinates)||range==0) {
            return finalCoordinates;
        } else {

            return computeValidCell(enemyPos, unit, range - 1);
        }

    }


    /**
     * handle action selection select the suited attack for the selected unit and executes it
     */
    private void handleActionSelection() {
        //unit that attacks
        Unit unitThatAttack = getSelectedUnit();
        //list of all the
        ArrayList<Integer> potentialTarget = unitThatAttack.getAttackableUnits();

        //change the aiplayer cursor to the selected unit
        this.changePosition(getSelectedUnit().getCurrentMainCellCoordinates());

        //check which attack the ai player should execute
        if (unitThatAttack.getHp() <= PATCH_THRESHOLD && unitThatAttack.hasAction(Patch.class)) {
            unitThatAttack.autoAction(this, Patch.KEY);
        } else if (potentialTarget.size() != 0 && unitThatAttack.hasAction(Attack.class)) {
            unitThatAttack.autoAction(this, Attack.KEY);
        } else {
            unitThatAttack.autoAction(this, Wait.KEY);
        }
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

    /**
     * add waiting time between actions
     */
    private boolean waitFor(float value, float dt) {
        if (counting) {
            counter += dt;
            if (counter > value) {
                counting = false;
                return true;
            }
        } else {
            counter = 0.f;
            counting = true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        //if action happening draw it
        if (this.getAct() != null) {
            this.getAct().draw(canvas);
        }
        //draw sprite of the character
        super.draw(canvas);

    }
}

