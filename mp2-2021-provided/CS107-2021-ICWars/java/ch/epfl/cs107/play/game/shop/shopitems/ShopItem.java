package ch.epfl.cs107.play.game.shop.shopitems;

import ch.epfl.cs107.play.game.icwars.actor.Unit;

public abstract class ShopItem {
    private final String name;
    private final int price;
    private final int key;

    public ShopItem(String name, int price, int key) {
        this.price = price;
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public abstract void effect(Unit unit);

    public int getPrice() {
        return price;
    }

    public int getKey() {
        return key;
    }
}
