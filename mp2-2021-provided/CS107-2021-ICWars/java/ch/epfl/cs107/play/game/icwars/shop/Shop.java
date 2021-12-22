package ch.epfl.cs107.play.game.icwars.shop;


import ch.epfl.cs107.play.game.actor.players.ICwarsPlayer;
import ch.epfl.cs107.play.game.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.shop.shopitems.Potion;
import ch.epfl.cs107.play.game.icwars.shop.shopitems.ReviveCharm;
import ch.epfl.cs107.play.game.icwars.shop.shopitems.ShopItem;
import ch.epfl.cs107.play.game.icwars.shop.shopitems.bierePG;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;

public final class Shop {
    private final ArrayList<ShopItem> shopItems = new ArrayList<>();
    private final ICwarsPlayer player;

    public Shop(ICwarsPlayer player) {
        //player that uses the shop
        this.player = player;
        //add items here :
        shopItems.add(new bierePG());
        shopItems.add(new Potion());
        shopItems.add(new ReviveCharm());
    }

    /**
     * listen for keyboard entry if a key is pressed that corresponds to the key of an item and that the
     * player has enough money to buy that item the item will be used on the currently selected unit
     */
    public void shopping(Keyboard keyboard) {
        //iterate through shop items
        for (ShopItem item : shopItems) {
            //check for key press on items
            if (keyboard.get(item.getKey()).isPressed()) {
                //check if player has enough money
                if (item.getPrice() <= player.getMoney()) {
                    //buy the item and use it on the player cast to
                    // realplayer because it's the only one that can shop may need to change if add other type of players than ai
                    ((RealPlayer) player).playerBought(item);
                    //deduce the price of the item on the player mooney
                }
            }

        }
    }

}
