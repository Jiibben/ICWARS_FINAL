package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.players.ICwarsPlayer;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.*;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

public class GameGui implements Graphics {
    private final ICwarsPlayer player;
    private final int height = 4;
    private final int width = 4;
    private final Shape rect = new Polygon(0, 0, 0, height, width, height, width, 0);
    private final ShapeGraphics bg = new ShapeGraphics(this.rect, Color.LIGHT_GRAY, Color.BLACK, 0f, 0.7f, 3000f);
    private TextGraphics display;
    private TextGraphics info1;
    private TextGraphics info2;

    public GameGui(ICwarsPlayer player) {

        this.player = player;
    }

    public void won() {
        this.display = new TextGraphics("YOU WON !", 1.2f, Color.red, Color.black, 0.02f,
                false, false, new Vector(0.3f, height - 0.4f),
                TextAlign.Horizontal.LEFT, TextAlign.Vertical.MIDDLE, 1.0f, 3001f);
        this.info1 = new TextGraphics("PRESS N FOR NEXT LEVEL", 0.5f, Color.red, Color.black, 0.02f,
                false, false, new Vector(0.45f, height - 1.8f),
                TextAlign.Horizontal.LEFT, TextAlign.Vertical.MIDDLE, 1.0f, 3001f);
        this.info2 = new TextGraphics("PRESS R FOR RELOAD", 0.4f, Color.red, Color.black, 0.02f,
                false, false, new Vector(0.6f, height - 2.5f),
                TextAlign.Horizontal.LEFT, TextAlign.Vertical.MIDDLE, 1.0f, 3001f);
    }


    @Override
    public void draw(Canvas canvas) {
        if (display != null) {
            final Transform transform = Transform.I.translated(player.getPosition().sub(2f, 2f));
            bg.setRelativeTransform(transform);
            display.setRelativeTransform(transform);
            info1.setRelativeTransform(transform);
            info2.setRelativeTransform(transform);
            bg.draw(canvas);
            display.draw(canvas);
            info1.draw(canvas);
            info2.draw(canvas);
        }


    }
}

