package ch.epfl.cs107.play.game.icwars.shop.shopitems;

import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;

public abstract class ShopItem {
    private final String name;
    private final int price;
    private final int key;

    public ShopItem(String name, int price, int key) {
        //price of the item
        this.price = price;
        //name of the item
        this.name = name;
        //key associated to the keyboard
        this.key = key;
    }

    /**
     * get the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * effect on the unit that the item has
     */
    public abstract void effect(Unit unit);

    /**
     * getter for the price of the item
     */
    public int getPrice() {
        return price;
    }

    public int getKey() {
        return key;
    }
}
