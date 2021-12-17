package ch.epfl.cs107.play.game;

import ch.epfl.cs107.play.game.actor.players.AIPlayer;
import ch.epfl.cs107.play.game.actor.players.ICwarsPlayer;
import ch.epfl.cs107.play.game.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icwars.actor.ICwarsActor;
import ch.epfl.cs107.play.game.icwars.area.ICwarsArea;
import ch.epfl.cs107.play.game.icwars.area.Level0;
import ch.epfl.cs107.play.game.icwars.area.Level1;
import ch.epfl.cs107.play.game.icwars.area.Level2;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayDeque;
import java.util.ArrayList;


public class ICwars extends AreaGame {

    private gameState currentGameState = gameState.INIT;
    public final static float CAMERA_SCALE_FACTOR = 13.f;
    private int areaIndex;
    private ArrayList<ICwarsPlayer> players;
    private ICwarsPlayer activePlayer;
    private final String[] areas = {"icwars/Level0", "icwars/Level1", "icwars/Level2"};

    //queue

    private final ArrayDeque<ICwarsPlayer> playersQueue = new ArrayDeque<>();
    private final ArrayDeque<ICwarsPlayer> waitingPlayersQueue = new ArrayDeque<>();

    /**
     * enum used to keep track of the game state
     */
    public enum gameState {
        INIT,
        CHOOSE_PLAYER,
        START_PLAYER_TURN,
        PLAYER_TURN,
        END_PLAYER_TURN,
        END_TURN,
        END
    }

    /**
     * create the area add one if you wan't to add a level and add his key to the areas key
     */
    private void createAreas() {
        addArea(new Level1());
        addArea(new Level0());
        addArea(new Level2());
    }

    /*------------------->

        queue handling

     <------------------------*/

    /**
     * init the players queue by adding all the players
     */
    private void initPlayersQueue() {
        for (ICwarsPlayer player : this.players) {
            this.playersQueue.addLast(player);
        }
    }

    /**
     * transfer the player from the waiting queue to the current queue
     */
    private void transferQueue() {
        while (this.waitingPlayersQueue.peekFirst() != null) {
            this.playersQueue.addLast(this.waitingPlayersQueue.removeFirst());
        }

    }

    /**
     * create all players
     *
     * @param area area to enter
     */

    private void initPlayers(ICwarsArea area) {
        createPlayers(area);
        playersEnterArea(area);

    }

    /**
     * create all players in the game
     */
    private void createPlayers(ICwarsArea area) {
        players = new ArrayList<ICwarsPlayer>();

        players.add(new RealPlayer(ICwarsActor.Faction.ALLY, area, area.getPlayerSpawnPosition(), area.getNumberOfTank(), area.getNumberOfSoldier(), area.getNumberOfGeek(), area.getAllyUnitSpawn()));
//        players.add(new RealPlayer(ICwarsActor.Faction.ENEMY, area, area.getEnnemySpawnPosition(), 1, 1, area.getEnnemyUnitSpawn()));


        //AI PLAYER comment if you want to disable the ai player
        players.add(new AIPlayer(ICwarsActor.Faction.ENEMY, area, area.getEnnemySpawnPosition(), area.getNumberOfTank(), area.getNumberOfSoldier(), area.getNumberOfGeek(), area.getEnnemyUnitSpawn()));

    }


    /**
     * Make enter all the current game players in the given area
     *
     * @param area area in which the player enter
     */
    private void playersEnterArea(ICwarsArea area) {
        for (ICwarsPlayer player : this.players) {
            if (player.getFaction() == ICwarsActor.Faction.ENEMY) {
                player.enterArea(area, area.getEnnemySpawnPosition());
            } else {
                player.enterArea(area, area.getPlayerSpawnPosition());
            }
        }
    }

    /**
     * Make all the players leave their area
     */
    private void playersLeaveArea() {
        for (ICwarsPlayer player : this.players) {
            player.leaveArea();
        }
    }

    /*---------------------->

    Automate handling

    <---------------------*/

    /**
     * init part fill the queue and change state to choose player
     */
    private void handleInit() {
        initPlayersQueue();
        this.currentGameState = gameState.CHOOSE_PLAYER;
    }

    /**
     * choose player part take the first player in queue
     * if none in the queue end turn
     * otherwise put the game in start player turn state
     */

    private void handleChoosePlayer() {
        this.activePlayer = this.playersQueue.pollFirst();
        if (activePlayer == null) {
            this.currentGameState = gameState.END_TURN;
        } else {
            this.currentGameState = gameState.START_PLAYER_TURN;
        }
    }

    /**
     * start turn handling
     * get the currently playing player and start his turn
     */

    private void handleStartPlayerTurn() {
        this.activePlayer.startTurn();
        this.currentGameState = (gameState.PLAYER_TURN);
    }

    /**
     * handle player turn by checking if he is in IDLE mode if he is end player turn
     */
    private void handlePlayerTurn() {
        if (this.activePlayer.getState() == ICwarsPlayer.PlayerState.IDLE) {
            this.currentGameState = gameState.END_PLAYER_TURN;
        }
    }

    /**
     * handle end player turn check if one player is defeated if this is the case remove them from the players
     */

    private void handleEndPlayerTurn() {
        if (this.activePlayer.isDefeated()) {
            this.activePlayer.leaveArea();
        } else {
            for (ICwarsPlayer player : players) {
                if (player.isDefeated()) {
                    player.leaveArea();
                    playersQueue.remove(player);
                    waitingPlayersQueue.remove(player);
                }
            }
            this.activePlayer.enableALlUnits();
            this.waitingPlayersQueue.addLast(this.activePlayer);
        }
        this.currentGameState = gameState.CHOOSE_PLAYER;
    }

    /**
     * handle end turn check if there is only 1 player in queue if this is the case end the
     * game else start the new player turn
     */
    private void handleEndTurn() {
        if (this.waitingPlayersQueue.size() == 1) {
            this.currentGameState = (gameState.END);
        } else {
            this.transferQueue();
            currentGameState = gameState.CHOOSE_PLAYER;
        }
    }

    /**
     * automate handle things depending on the game state
     */
    private void automate() {
        switch (this.currentGameState) {
            case INIT:
                handleInit();
            case CHOOSE_PLAYER:
                handleChoosePlayer();
                break;
            case START_PLAYER_TURN:
                handleStartPlayerTurn();
                break;
            case PLAYER_TURN:
                handlePlayerTurn();
                break;
            case END_PLAYER_TURN:
                handleEndPlayerTurn();
                break;
            case END_TURN:
                handleEndTurn();
                break;
            case END:
                System.out.println("fin");
        }

    }


    @Override
    /** create areas and initialize them*/
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            createAreas();
            areaIndex = 0;
            initArea(areas[areaIndex]);
            return true;
        }
        return false;
    }

    /**
     * init area by given index
     *
     * @param areaKey index of the area in areas
     */
    private void initArea(String areaKey) {

        setCurrentArea(areaKey, true);
        ICwarsArea area = (ICwarsArea) getCurrentArea();
        initPlayers(area);
        this.currentGameState = gameState.INIT;
    }


    /*
     *
     * ------------------------------------------------------
     *
     *       keyboard handling and functionality
     * --------------------------------------------------
     *  */

    /**
     * reload is used when the reload key is used
     * it justs set everything back to normal
     */

    private void reload() {
        this.areaIndex = 0;
        this.playersQueue.clear();
        this.waitingPlayersQueue.clear();
        playersLeaveArea();

        initArea(areas[areaIndex]);

        this.currentGameState = gameState.INIT;


    }

    /**
     * next level is used when the next level key is pressed
     * it justs unregister every player from current area and initialize a new area by increasing the index of area by 1 if no next area end the game
     */
    private void nextLevel() {
        if (this.areaIndex + 1 < this.areas.length) {
            playersLeaveArea();
            this.playersQueue.clear();
            this.waitingPlayersQueue.clear();
            this.areaIndex += 1;
            this.initArea(areas[areaIndex]);


        } else {
            this.end();
        }
    }

    /**
     * keyboard handler that listens to the different keyboard key and activate the associated actions
     */
    private void KeyboardHandler() {
        Keyboard keyboard = getCurrentArea().getKeyboard();
        //keyboard mappings
        Button reload = keyboard.get(Keyboard.R);
        Button nextLevel = keyboard.get(Keyboard.N);

        if (reload.isPressed()) {
            this.reload();
        } else if (nextLevel.isPressed()) {
            this.nextLevel();
        }
    }



    /*
    --------------------------------->

    autres méthodes

    <_------------------------------*/

    public void update(float deltaTime) {
        KeyboardHandler();
        automate();
        super.update(deltaTime);

    }

    @Override
    public void end() {
        // TODO: trouver comment on quitte le jeu et le faire
        System.out.println("Game Over");
    }

    @Override
    public String getTitle() {
        return "ICwars";
    }

}

