package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractorVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

import java.util.List;

public class ICwarsBehavior extends AreaBehavior {
    public enum ICwarsCellType {
        //https://stackoverflow.com/questions/25761438/understanding-bufferedimage-getrgb-output-values
        NONE(0, 0, 0),
        ROAD(-16777216, 0, 0),
        PLAIN(-14112955, 1, 0),
        WOOD(-65536, 3, 2),
        RIVER(-16776961, 0, 1),
        MOUNTAIN(-256, 4, 3),
        CITY(-1, 2, 1),
        SAND(-124534, 1, 1),
        PIPE(-234, 0, 2);


        final int type;
        //defense associated to the typee
        final int defense;
        //obstacles is used to change the attackray
        final int obstacles;

        public int getDefenseStar() {


            return defense;
        }


        public int getObstaclesStar() {
            return obstacles;
        }

        public String typeToString() {
            return this.toString();
        }

        ICwarsCellType(int type, int defense, int obstacles) {
            this.type = type;
            this.defense = defense;
            this.obstacles = obstacles;
        }

        public static ICwarsCellType toType(int type) {
            for (ICwarsCellType ict : ICwarsCellType.values()) {
                if (ict.type == type)
                    return ict;
            }
            // When you add a new color, you can print the int value here before assign it to a type
            System.out.println(type);
            return NONE;
        }
    }

    /**
     * Default  Constructor
     *
     * @param window (Window), not null
     * @param name   (String): Name of the Behavior, not null
     */
    public ICwarsBehavior(Window window, String name) {
        super(window, name);
        int height = getHeight();
        int width = getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ICwarsCellType color = ICwarsCellType.toType(getRGB(height - 1 - y, x));
                setCell(x, y, new ICwarsCell(x, y, color));
            }
        }
    }
    

    /**
     * Cell adapted to the  game
     */
    public class ICwarsCell extends AreaBehavior.Cell {
        /// Type of the cell following the enum
        private final ICwarsCellType type;

        /**
         * Default Tuto2Cell Constructor
         *
         * @param x    (int): x coordinate of the cell
         * @param y    (int): y coordinate of the cell
         * @param type (EnigmeCellType), not null
         */
        public ICwarsCell(int x, int y, ICwarsCellType type) {
            super(x, y);
            this.type = type;
        }

        public ICwarsCellType getType() {
            return type;
        }

        @Override
        protected boolean canLeave(Interactable entity) {
            return true;
        }

        @Override
        protected boolean canEnter(Interactable entity) {
            for (Interactable i : this.entities) {
                if (i.takeCellSpace() && entity.takeCellSpace()) {
                    return false;
                }
            }
            return true;
        }


        @Override
        public boolean isCellInteractable() {
            return true;
        }

        @Override
        public boolean isViewInteractable() {

            return false;
        }

        @Override
        public void acceptInteraction(AreaInteractionVisitor v) {
            ((ICWarsInteractorVisitor) v).interactWith(this);
        }

    }
}

