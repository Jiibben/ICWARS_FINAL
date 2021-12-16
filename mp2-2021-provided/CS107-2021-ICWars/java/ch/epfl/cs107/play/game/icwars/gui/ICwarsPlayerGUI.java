package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.players.ICwarsPlayer;


import ch.epfl.cs107.play.game.icwars.actor.Unit;
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

    public ICwarsPlayerGUI(float cameraScaleFactor, ICwarsPlayer player) {
        this.player = player;
        //initiate different panel
        infoPanel = new ICWarsInfoPanel(cameraScaleFactor);
        actionPanel = new ICWarsActionsPanel(cameraScaleFactor);
    }

    @Override
    public void draw(Canvas canvas) {
        if (selectedUnit != null) {
            if (playerState == ICwarsPlayer.PlayerState.MOVE_UNIT) {
                rangeAndShortestPathForSelectedUnit(canvas);
            }
        } else {

            if (playerState == ICwarsPlayer.PlayerState.NORMAL || playerState == ICwarsPlayer.PlayerState.SELECT_CELL) {
                infoPanel.draw(canvas);
            } else if (playerState == ICwarsPlayer.PlayerState.ACTION_SELECTION) {
                actionPanel.draw(canvas);
            }
        }


    }

    public void setActions(ArrayList<ICwarsAction> actions) {
        actionPanel.setActions(actions);
    }

    public void setCurrentUnit(Unit unit) {
        //set the currently selected  selected unit in the infopanel
        infoPanel.setUnit(unit);

    }

    public void setCurrentCell(ICwarsBehavior.ICwarsCellType cellType) {
        infoPanel.setCurrentCell(cellType);
    }

    public void setPlayerState(ICwarsPlayer.PlayerState playerState) {
        this.playerState = playerState;
    }


    private void rangeAndShortestPathForSelectedUnit(Canvas canvas) {
        //show the shortest range and path
        selectedUnit.drawRangeAndPathTo(player.getCurrentMainCellCoordinates(), canvas);
    }

    public void setSelectedUnit(Unit unit) {
        this.selectedUnit = unit;
    }

    public void unselectUnit() {
        this.selectedUnit = null;
    }

}
