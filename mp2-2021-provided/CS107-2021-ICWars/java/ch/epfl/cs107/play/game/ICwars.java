package ch.epfl.cs107.play.game;

import ch.epfl.cs107.play.audio.AudioPlayer;
import ch.epfl.cs107.play.game.actor.players.AIPlayer;
import ch.epfl.cs107.play.game.actor.players.ICwarsPlayer;
import ch.epfl.cs107.play.game.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icwars.actor.ICwarsActor;
import ch.epfl.cs107.play.game.icwars.area.*;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Image;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.security.Key;
import java.util.ArrayDeque;
import java.util.ArrayList;


public class ICwars extends AreaGame {

    private gameState currentGameState = gameState.INIT;
    public final static float CAMERA_SCALE_FACTOR = 13.f;
    private int areaIndex;
    private ArrayList<ICwarsPlayer> players;
    private ICwarsPlayer activePlayer;
    private final String[] areas = {"icwars/Level0", "icwars/Level1", "icwars/Level2", "icwars/Level3", "icwars/Level4", "icwars/Level5", "icwars/epflMap"};

    private final AudioPlayer gameSound = new AudioPlayer("gameSound3", true);

    //queue

    private final ArrayDeque<ICwarsPlayer> playersQueue = new ArrayDeque<>();
    private final ArrayDeque<ICwarsPlayer> waitingPlayersQueue = new ArrayDeque<>();

    /**
     * enum used to keep track of the game state
     */
    public enum gameState {
        TITLE,
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
        addArea(new Menu());
        addArea(new Level1());
        addArea(new Level0());
        addArea(new Level2());
        addArea(new Level3());
        addArea(new Level4());
        addArea(new Level5());
        addArea(new EpflMap());
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
        //ally player
        players.add(new RealPlayer(ICwarsActor.Faction.ALLY, area, area.getPlayerSpawnPosition(), area.getNumberOfTank(), area.getNumberOfSoldier(), area.getNumberOfGeek(), area.getNumberOfBoat(), area.getAllyUnitSpawn()));
        //ennemy player
//        players.add(new RealPlayer(ICwarsActor.Faction.ENEMY, area, area.getEnnemySpawnPosition(), area.getNumberOfTank(), area.getNumberOfSoldier(), area.getNumberOfGeek(), area.getNumberOfBoat(), area.getEnnemyUnitSpawn()));

        //AI PLAYER comment if you want to disable the ai player and uncomment the other realplayer
        players.add(new AIPlayer(ICwarsActor.Faction.ENEMY, area, area.getEnnemySpawnPosition(), area.getNumberOfTank(), area.getNumberOfSoldier(), area.getNumberOfGeek(), area.getNumberOfBoat(), area.getEnnemyUnitSpawn()));
        if (area.hasThreePlayer()) {
            players.add(new AIPlayer(ICwarsActor.Faction.OUTLAW, area, area.getThirdPlayerSpawnPosition(), area.getNumberOfTank(), area.getNumberOfSoldier(), area.getNumberOfGeek(), area.getNumberOfBoat(), area.getThirdPlayerUnitSpawn()));
        }
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
            } else if (player.getFaction() == ICwarsActor.Faction.ALLY) {
                player.enterArea(area, area.getPlayerSpawnPosition());
            } else if (player.getFaction() == ICwarsActor.Faction.OUTLAW) {
                if (area.getThirdPlayerSpawnPosition() != null) {
                    player.enterArea(area, area.getThirdPlayerSpawnPosition());
                }
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
     * handle title
     */
    private void handleTitle() {
        Keyboard keyboard = getWindow().getKeyboard();
        if (keyboard.get(Keyboard.ENTER).isPressed()) {
            areaIndex = 0;
            initArea(areas[areaIndex]);
        }
    }

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
        //start player turn
        this.activePlayer.startTurn();
        //pay the player for the cities he has
        this.activePlayer.payForCities();
        //set the game state to player turn
        this.currentGameState = (gameState.PLAYER_TURN);
    }

    /**
     * handle player turn by checking if he is in IDLE mode if he is end player turn
     */
    private void handlePlayerTurn() {
        //check if player goes back to idle(skip turn)
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
            if (waitingPlayersQueue.getFirst().isRealPlayer()) {
                ((RealPlayer) waitingPlayersQueue.getFirst()).hasWon();
            } else {
                reload();
            }
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
            case TITLE:
                handleTitle();
                break;
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
            gameSound.playSound();
            createAreas();
            setCurrentArea("icwars/Menu", true);
            currentGameState = gameState.TITLE;
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
        area.createCities();
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
        ((ICwarsArea) getCurrentArea()).destroyCities();

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
            ((ICwarsArea) getCurrentArea()).destroyCities();
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

    autres m??thodes

    <_------------------------------*/

    public void update(float deltaTime) {
        KeyboardHandler();
        automate();
        super.update(deltaTime);

    }

    @Override
    public void end() {

        super.end();
    }

    @Override
    public String getTitle() {
        return "ICwars";
    }

}

