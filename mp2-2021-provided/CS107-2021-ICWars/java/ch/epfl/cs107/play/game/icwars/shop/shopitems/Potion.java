package ch.epfl.cs107.play.game.icwars.shop.shopitems;


import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.window.Keyboard;

public final class Potion extends ShopItem {

    public final static String NAME = "(P)OTION";
    public final static int PRICE = 4;
    public static final int KEY = Keyboard.P;
    private final int HEALINGAMOUNT = 3;


    public Potion() {
        super(NAME, PRICE, KEY);
    }

    @Override
    public void effect(Unit unit) {
        unit.repair(HEALINGAMOUNT);
    }

}
