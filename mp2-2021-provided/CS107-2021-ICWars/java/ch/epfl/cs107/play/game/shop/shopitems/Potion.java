package ch.epfl.cs107.play.game.shop.shopitems;


import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.window.Keyboard;

public class Potion extends ShopItem {

    public final static String name = "(P)OTION";
    public final static int price = 4;
    public static final int KEY = Keyboard.P;
    private final int HEALINGAMOUNT = 3;


    public Potion() {
        super(name, price, KEY);
    }

    @Override
    public void effect(Unit unit) {
        unit.repair(HEALINGAMOUNT);
    }

}
