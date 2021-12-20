package ch.epfl.cs107.play.game.shop;


import ch.epfl.cs107.play.game.actor.players.ICwarsPlayer;
import ch.epfl.cs107.play.game.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.shop.shopitems.ShopItem;
import ch.epfl.cs107.play.game.shop.shopitems.bierePG;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;

public class Shop {
    private ArrayList<ShopItem> shopItems = new ArrayList<>();
    private ICwarsPlayer player;

    public Shop(ICwarsPlayer player) {
        this.player = player;
        shopItems.add(new bierePG());
    }

    public void shopping(Keyboard keyboard) {
        for (ShopItem item : shopItems) {
            if (keyboard.get(item.getKey()).isPressed()) {
                if (item.getPrice() <= player.getMoney()) {
                    ((RealPlayer) player).playerBought(item);
                    player.removeMoney(item.getPrice());
                }
            }

        }
    }

}
