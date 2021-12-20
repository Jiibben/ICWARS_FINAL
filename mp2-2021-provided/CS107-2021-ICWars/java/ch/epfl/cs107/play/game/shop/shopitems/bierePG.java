package ch.epfl.cs107.play.game.shop.shopitems;

import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.window.Keyboard;

public class bierePG extends ShopItem {

    public final static String name = "(B)IERE PG";
    public final static int price = 5;
    public static final int KEY = Keyboard.B;


    public bierePG() {
        super(name, price, KEY);
    }

    @Override
    public void effect(Unit unit) {
        unit.increaseAttack(1);
    }

}
