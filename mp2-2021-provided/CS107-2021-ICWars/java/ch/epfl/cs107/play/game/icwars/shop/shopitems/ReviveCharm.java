package ch.epfl.cs107.play.game.icwars.shop.shopitems;


import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.window.Keyboard;

public final class ReviveCharm extends ShopItem {

    public final static String NAME = "(F)Revive";
    public final static int PRICE = 0;
    public static final int KEY = Keyboard.F;


    public ReviveCharm() {
        super(NAME, PRICE, KEY);
    }

    @Override
    public void effect(Unit unit) {
        unit.revive();

    }

    @Override
    public boolean canBeUsed(Unit unit) {
        return (unit.isDead());
    }


}
