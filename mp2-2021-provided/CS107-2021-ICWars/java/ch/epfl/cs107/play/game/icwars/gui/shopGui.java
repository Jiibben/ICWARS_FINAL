package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
//import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
//import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
//import ch.epfl.cs107.play.game.icwars.area.ICWarsBehavior;
import ch.epfl.cs107.play.game.actor.players.ICwarsPlayer;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.icwars.shop.shopitems.Potion;
import ch.epfl.cs107.play.game.icwars.shop.shopitems.bierePG;
import ch.epfl.cs107.play.math.*;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;


public final class shopGui implements Graphics {

    private final float fontSize;


    /// Sprite and text graphics line
    private final ShapeGraphics background;
    private TextGraphics[] items;

    private final ImageGraphics beerImage, potionImage;
    private final TextGraphics beerText, shopText, moneyText, potionText;
    private int playerMoneyAmount;

    /**
     * used to set the balance of the player in the gui to display it
     *
     * @param playerMoneyAmount
     */
    public void setPlayerMoneyAmount(int playerMoneyAmount) {

        this.playerMoneyAmount = playerMoneyAmount;
        this.moneyText.setText("balance : " + this.playerMoneyAmount);
    }

    /**
     * Default Dialog Constructor
     */


    public shopGui(ICwarsPlayer player, float cameraScaleFactor) {
        playerMoneyAmount = player.getMoney();
        final float height = (cameraScaleFactor / 4) + 1f;
        final float width = (cameraScaleFactor / 4);
        Vector anchor = new Vector(.1f, -.2f);

        fontSize = cameraScaleFactor / ICwarsPlayerGUI.FONT_SIZE;

        Shape rect = new Polygon(0, 0, 0, height, width, height, width, 0);
        background = new ShapeGraphics(rect, Color.DARK_GRAY, Color.BLACK, 0f, 0.7f, 3000f);


        shopText = new TextGraphics("  SHOP          PRICE", fontSize, Color.WHITE, null, 0.0f,
                false, false, new Vector(0, 1 * 1.25f * fontSize - 0.35f),
                TextAlign.Horizontal.LEFT, TextAlign.Vertical.MIDDLE, 1.0f, 3001f);

        shopText.setFontName("Kenney Pixel");

        beerImage = new ImageGraphics(ResourcePath.getSprite("icwars/bierePG"), 1f, 1f, null, anchor, 1f, 3001f);
        beerText = new TextGraphics(bierePG.NAME + " | " + bierePG.PRICE, fontSize, Color.WHITE, null, 0.0f,
                false, false, new Vector(0, 1 * 1.25f * fontSize - 0.35f),
                TextAlign.Horizontal.LEFT, TextAlign.Vertical.MIDDLE, 1.0f, 3001f);
        beerText.setFontName("Kenney Pixel");

        potionImage = new ImageGraphics(ResourcePath.getSprite("icwars/potion"), 1f, 1f, null, anchor, 1f, 3001f);
        potionText = new TextGraphics(Potion.NAME + "  |  " + Potion.PRICE, fontSize, Color.WHITE, null, 0.0f,
                false, false, new Vector(0, 1 * 1.25f * fontSize - 0.35f),
                TextAlign.Horizontal.LEFT, TextAlign.Vertical.MIDDLE, 1.0f, 3001f);
        potionText.setFontName("Kenney Pixel");
        moneyText = new TextGraphics("balance : " + playerMoneyAmount, fontSize, Color.WHITE, null, 0.0f,
                false, false, new Vector(0, 1 * 1.25f * fontSize - 0.35f),
                TextAlign.Horizontal.LEFT, TextAlign.Vertical.MIDDLE, 1.0f, 3001f);
        moneyText.setFontName("Kenney Pixel");

    }


    @Override
    public void draw(Canvas canvas) {
        // Compute width, height and anchor

        float width = canvas.getXScale();
        float height = canvas.getYScale();
        //draw the background
        final Transform transform = Transform.I.translated(canvas.getPosition().add(-width / 2f, (height / 4) - 1f));
        background.setRelativeTransform(transform);
        background.draw(canvas);

        //draw the shop text
        final Transform textTransform = Transform.I.translated(canvas.getPosition().add(-width / 2f, (height / 3f) + 1.2f));

        shopText.setRelativeTransform(textTransform);
        shopText.draw(canvas);

        //draw the image of the beer
        final Transform beerTransform = Transform.I.translated(canvas.getPosition().add((-width / 2f) - 0.3f, (height / 3f) + 0.4f));
        beerImage.setRelativeTransform(beerTransform);
        beerImage.draw(canvas);

        //draw the potion image
        final Transform potionTransform = Transform.I.translated(canvas.getPosition().add((-width / 2f) - 0.3f, (height / 3f) - 0.6f));
        potionImage.setRelativeTransform(potionTransform);
        potionImage.draw(canvas);
        //draw the text next to the beer
        final Transform beerTextTransform = Transform.I.translated(canvas.getPosition().add((-width / 2f) + 0.6f, (height / 3f) + 0.4f));
        beerText.setRelativeTransform(beerTextTransform);
        beerText.draw(canvas);
        //draw the text next to the potion
        final Transform potionTextTranform = Transform.I.translated(canvas.getPosition().add((-width / 2f) + 0.6f, (height / 3f) + -0.6f));
        potionText.setRelativeTransform(potionTextTranform);
        potionText.draw(canvas);
        //draw player balance at the bottom
        final Transform moneyAmountTextTransform = Transform.I.translated(canvas.getPosition().add((-width / 2f) + 0.6f, (height / 3f) - 2f));
        moneyText.setRelativeTransform(moneyAmountTextTransform);
        moneyText.draw(canvas);


//    }

    }
}
