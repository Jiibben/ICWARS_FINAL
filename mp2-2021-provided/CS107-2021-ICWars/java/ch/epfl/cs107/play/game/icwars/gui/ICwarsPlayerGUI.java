package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.players.ICwarsPlayer;


import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.ICwarsAction;
import ch.epfl.cs107.play.game.icwars.area.ICwarsBehavior;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;

public class ICwarsPlayerGUI implements Graphics {
    //associated player
    private final ICwarsPlayer player;
    //player state of the associated player
    private ICwarsPlayer.PlayerState playerState;
    //selected unit
    private Unit selectedUnit = null;
    public static final float FONT_SIZE = 20;
    private final ICWarsInfoPanel infoPanel;
    private final ICWarsActionsPanel actionPanel;
    private final GameGui gameGui;
    private final shopGui shopGui;


    public ICwarsPlayerGUI(float cameraScaleFactor, ICwarsPlayer player) {
        this.player = player;
        //initiate different gui for the player
        //gui of the winning title
        gameGui = new GameGui(player);
        //info gui for the cells and units
        infoPanel = new ICWarsInfoPanel(cameraScaleFactor);
        //panel to display the actions
        actionPanel = new ICWarsActionsPanel(cameraScaleFactor);
        //display the shop
        shopGui = new shopGui(player, cameraScaleFactor);

    }

    public void won() {
        gameGui.won();
    }

    @Override
    public void draw(Canvas canvas) {
        gameGui.draw(canvas);

        if (selectedUnit != null) {
            if (playerState == ICwarsPlayer.PlayerState.MOVE_UNIT) {
                rangeAndShortestPathForSelectedUnit(canvas);
            } else if (playerState == ICwarsPlayer.PlayerState.SHOPPING) {
                shopGui.draw(canvas);
            }
        } else {

            if (playerState == ICwarsPlayer.PlayerState.NORMAL || playerState == ICwarsPlayer.PlayerState.SELECT_CELL) {
                infoPanel.draw(canvas);
            } else if (playerState == ICwarsPlayer.PlayerState.ACTION_SELECTION) {
                actionPanel.draw(canvas);
            }
        }


    }

    /**
     * set the actions for the action panelto display
     *
     * @param actions list of the action to display
     */
    public void setActions(ArrayList<ICwarsAction> actions) {
        actionPanel.setActions(actions);
    }

    /**
     * set the unit for the info panel
     *
     * @param unit unit to select to display the info
     */
    public void setCurrentUnit(Unit unit) {
        //set the currently selected  selected unit in the infopanel
        infoPanel.setUnit(unit);

    }

    /**
     * set the cell type that the player is on so that the info panel can display it
     *
     * @param cellType type of the cellule
     */
    public void setCurrentCell(ICwarsBehavior.ICwarsCellType cellType) {
        infoPanel.setCurrentCell(cellType);
    }

    /**
     * state of the player (used to know what panel must be displayed)
     */
    public void setPlayerState(ICwarsPlayer.PlayerState playerState) {
        this.playerState = playerState;
    }

    /**
     * set the money that the player has so the shopgui can display it
     *
     * @param amount number of money
     */
    public void setPlayerMoneyAmount(int amount) {
        shopGui.setPlayerMoneyAmount(amount);
    }

    /**
     * used to display the shortest path
     *
     * @param canvas canvs to draw on
     */

    private void rangeAndShortestPathForSelectedUnit(Canvas canvas) {
        //show the shortest range and path
        selectedUnit.drawRangeAndPathTo(player.getCurrentMainCellCoordinates(), canvas);
    }

    /**
     * which unit is selected used to display things related to that unit
     *
     * @param unit unit to select
     */
    public void setSelectedUnit(Unit unit) {
        this.selectedUnit = unit;
    }
    /**unselect the selected unit (set selectedunit to null)*/
    public void unselectUnit() {
        this.selectedUnit = null;
    }

}
