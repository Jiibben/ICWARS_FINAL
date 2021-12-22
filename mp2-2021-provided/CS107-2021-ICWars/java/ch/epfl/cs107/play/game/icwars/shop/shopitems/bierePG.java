package ch.epfl.cs107.play.game.icwars.shop.shopitems;

import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.window.Keyboard;

public final class bierePG extends ShopItem {

    public final static String NAME = "(B)IERE PG";
    public final static int PRICE = 5;
    public static final int KEY = Keyboard.B;


    public bierePG() {
        super(NAME, PRICE, KEY);
    }

    @Override
    public void effect(Unit unit) {
        unit.increaseAttack(1);
    }

}
