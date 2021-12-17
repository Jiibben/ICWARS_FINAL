package ch.epfl.cs107.play.game.actor.players;

import ch.epfl.cs107.play.game.ICwars;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICwarsArea;
import ch.epfl.cs107.play.game.icwars.area.ICwarsBehavior;
import ch.epfl.cs107.play.game.icwars.gui.ICwarsPlayerGUI;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractorVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.security.Key;
import java.util.List;

import static ch.epfl.cs107.play.game.actor.players.ICwarsPlayer.PlayerState.*;


public class RealPlayer extends ICwarsPlayer {

    // move duration
    private final static int MOVE_DURATION = 8;

    //gui handler
    private final ICwarsPlayerGUI playerGUI = new ICwarsPlayerGUI(ICwars.CAMERA_SCALE_FACTOR, this);

    //action handler
    private final ICwarsPlayerInteractionHandler interactionHandler = new ICwarsPlayerInteractionHandler(this);

    public RealPlayer(Faction faction, ICwarsArea owner, DiscreteCoordinates coordinates, int numberOfTank, int numberOfSoldier, int numberOfGeek, DiscreteCoordinates unitSpawn) {
        super(faction, owner, coordinates, numberOfTank, numberOfSoldier, numberOfGeek, unitSpawn);

        //create player at idle state
        this.setState(IDLE);
    }

    /*--------------------------------------------------
     *
     *           STATE HANDLING
     *
     * ---------------------------------------------------
     *  */

    /**
     * set the player state
     */
    @Override
    protected void setState(PlayerState state) {
        super.setState(state);
        // let the gui know that the state of the player changed
        playerGUI.setPlayerState(state);
    }

    @Override
    public boolean isRealPlayer() {
        //return true cause it's not a bot
        return true;
    }

    /**
     * change state to given state on given key press
     *
     * @param state playerstate to set when button pressed
     * @param b     key to listen to in order to change state
     */
    public void handleKeyState(PlayerState state, Button b) {
        if (b.isPressed()) {
            this.setState(state);
        }
    }

    // handle each state:

    /**
     * normal handling
     */
    private void handleNormal(Keyboard keyboard) {
        setState(NORMAL);
        //show player at 100% opacity (enable)
        getSprite().setAlpha(1.f);
        //on enter key change to select cell
        handleKeyState(SELECT_CELL, keyboard.get(Keyboard.ENTER));
        //go to idle on tab press
        handleKeyState(IDLE, keyboard.get(Keyboard.TAB));
        //unselect unit in playerGui
        playerGUI.unselectUnit();
        //allows player to move
        moveHandling(keyboard);

    }

    /**
     * handle cell selection
     */
    private void handleSelectCell(Keyboard keyboard) {
        //go back to normal state on tab press
        handleKeyState(NORMAL, keyboard.get(Keyboard.TAB));
        //allows player to move
        moveHandling(keyboard);

    }

    /**
     * handle moving units
     */
    private void handleMoveUnit(Keyboard keyboard) {
        //in moveUnit player cursor can move, and move unit on enter also cna go back to normal state on tab press
        //on tab press you go back to normal
        handleKeyState(NORMAL, keyboard.get(Keyboard.TAB));
        //move selected unit to position on enter press
        moveSelectedUnit(keyboard.get(Keyboard.ENTER));
        //movement
        moveHandling(keyboard);
    }

    /**
     * handle action selection
     */
    private void handleActionSelection(Keyboard keyboard) {
        //in moveUnit player cursor can move, and move unit on enter also cna go back to normal state on tab press
        //on tab press you go back to normal
        playerGUI.setActions(getSelectedUnit().getActions());
        handleKeyState(NORMAL, keyboard.get(Keyboard.TAB));
        //move selected unit to position on enter press
        moveSelectedUnit(keyboard.get(Keyboard.ENTER));
        //movement
    }

    /**
     * handle action
     */
    private void handleAction(Keyboard keyboard) {
        try {
            getAct().doAction(4, this, keyboard);
        } catch (NullPointerException ignored) {
            //should never appear
            System.out.println("no action selected");
        }
    }

    // AUTOMAte that is handling the different state of the player
    private void automate(Keyboard keyboard) {
        switch (this.getState()) {
            case IDLE:
                //don't show player cursor cause player not active
                getSprite().setAlpha(0.f);
                break;
            case NORMAL:
                //handle normal state
                handleNormal(keyboard);
                break;
            case SELECT_CELL:
                //handle select cell
                handleSelectCell(keyboard);
                break;
            case MOVE_UNIT:
                //handle move unit
                handleMoveUnit(keyboard);
                break;
            case ACTION_SELECTION:
                //handle action selection
                handleActionSelection(keyboard);
                break;
            case ACTION:
                //handle action selected
                handleAction(keyboard);
                break;
        }
    }


    /**
     * select unit and also selects it in the gui handler of the player
     *
     * @param i index of the unit to select
     */
    @Override
    public void selectUnit(int i) {
        super.selectUnit(i);
        //let the player gui know that the selected unit changed
        playerGUI.setSelectedUnit(super.getSelectedUnit());
    }



    /* _-------------------------------------


                MOUVEMENT HANDLING

        ----------------------------------------
    */

    /**
     * change selected unit position to the current position of the player
     *
     * @param b button to listen to to initiate movement
     */
    private void moveSelectedUnit(Button b) {
        if (b.isPressed()) {
            //current position of the player
            DiscreteCoordinates pos = getCurrentMainCellCoordinates();
            //selected unit
            Unit selectedUnit = getSelectedUnit();
            //check if the selected point is in the range of the unit
            if (selectedUnit.isInRange(pos) && getSelectedUnit().canMove()) {
                if (!isDisplacementOccurs()) {
                    //change the position of selected unit to the pos of the cursor
                    getSelectedUnit().changePosition(pos);
                    //in case it's not the already the case change the cursor position to the selected unit
                    this.changePosition(getSelectedUnit().getCurrentMainCellCoordinates());
                    //because movement occured unit can't move anymore
                    getSelectedUnit().disableMove();
                    //center camer on cursor (if it's not already the case)
                    this.centerCamera();
                    //tell the gui that the unit isn't selected anymore for the gui
                    playerGUI.unselectUnit();
                    //tell the gui what actions are avaible on the selected unit
                    Unit unit = getSelectedUnit();
                    playerGUI.setActions(unit.getActions());
                    //change state to action selection
                    this.setState(ACTION_SELECTION);

                }

            }
        }
    }

    private void moveHandling(Keyboard keyboard) {
        //allows movement up right left down associated to the arrow key
        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

    }


    private void moveIfPressed(Orientation orientation, Button b) {
        if (b.isDown()) {
            // listen to the button and executes movement on button press used in movehandling
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);
            }
        }
    }


    @Override
    public void update(float deltaTime) {
        //update method

        Keyboard keyboard = getOwnerArea().getKeyboard();
        automate(keyboard);
        super.update(deltaTime);
    }


    public void draw(Canvas canvas) {
        //if action hapenning draw it
        if (getState() == ACTION) {
            //if state is in action (player is executing the selected action on an unit) draw the action
            getAct().draw(canvas);
        }
        //draw sprite of the character
        //draw playerGUI
        playerGUI.draw(canvas);
        super.draw(canvas);

    }


    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    public void startAction() {
        this.setState(ACTION);
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(interactionHandler);

    }


    // interaction handler

    private class ICwarsPlayerInteractionHandler implements ICWarsInteractorVisitor {
        private final RealPlayer player;

        public ICwarsPlayerInteractionHandler(RealPlayer player) {
            //player associated to the handler
            this.player = player;

        }

        public void interactWith(Unit unit) {
            switch (getState()) {
                case NORMAL:
                    playerGUI.setCurrentUnit(unit);
                    break;

                case SELECT_CELL:
                    playerGUI.setCurrentUnit(unit);
                    /*allows to only select ally unit*/
                    if (getFaction() == unit.getFaction()) {
                        //allows to select only unit that can move (not disabled
                        if (unit.canMove()) {
                            //select unit we currently are on when selecting cell
                            selectUnit(findUnitIndex(unit));
                            setState(MOVE_UNIT);
                        }
                    }
                    break;
                case ACTION_SELECTION:
                    //make sure that the unit can act
                    if (unit.canAct()) {
                        //start listening for action
                        unit.action(player);
                    }

            }

        }

        public void interactWith(ICwarsBehavior.ICwarsCell cell) {
            playerGUI.setCurrentUnit(null);
            //tell the gui the type of the current cell the cursor is on
            playerGUI.setCurrentCell(cell.getType());
        }
    }
}