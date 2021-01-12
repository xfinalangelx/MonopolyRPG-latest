package MonopolyRPG.Controller;

import MonopolyRPG.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Shop {
    //items and their price
    public final static int POTION_PRICE = 30, SMOKEBOMB_PRICE = 30, HIPOTION_PRICE = 100;
    public final static int SWORD_PRICE = 10, AXE_PRICE = 20, SPEAR_PRICE = 40, HAMMER_PRICE = 35;
    private static HashMap<Item, Integer> items;
    private static HashMap<Weapon, Integer> weapons;
    private static LoopOnceMusicList musicList;

    public Shop() {
        items = new HashMap<>();
        items.put(Item.POTION,POTION_PRICE);
        items.put(Item.SMOKEBOMB,SMOKEBOMB_PRICE);
        items.put(Item.HIPOTION,HIPOTION_PRICE);

        weapons = new HashMap<>();
        weapons.put(Weapon.AXE, AXE_PRICE);
        weapons.put(Weapon.SPEAR, SPEAR_PRICE);
        weapons.put(Weapon.HAMMER, HAMMER_PRICE);

    }

    // Player enter shop tile
    public void enter(Player player){

        Scanner input = new Scanner(System.in);
        int shop;
        int command = -1 ;
        int count = 0;

        while(command != Command.EXIT){
            System.out.println("Your gold: " + player.getGold());
            System.out.println(Command.BUY + ": Buy");
            System.out.println(Command.SELL + ": Sell");
            System.out.println(Command.EXIT + ": Exit");
            System.out.print("Enter your command: ");

            if(player instanceof BotPlayer){
                command = ((BotPlayer) player).selectShopCommand();
                count ++;
                System.out.println(command + "\n");// Buy:1, Exit:3

                // bot maximum choose command for 3 times
                if (count > 3) {
                    System.out.println("Nothing to buy");
                    return;
                }

            }else {
                command = input.nextInt();  //player input cmd
                System.out.println();
            }

            switch (command) {
                case Command.BUY:   //1
                    this.display();
                    System.out.println("You want to buy \n1 Item \n2 Weapon");

                    if (player instanceof BotPlayer) {
                        if (((BotPlayer) player).isHpLow()) {
                            shop = 1;
                        } else shop = 2;

                        System.out.println(shop);

                    } else {
                        shop = input.nextInt();
                    }

                    if(shop == 1){
                        this.promptBuy(player, shop);  // 1: items
                        break;
                    }else {
                        this.promptBuy(player, shop);  // 2: weapons
                        break;
                    }

                        case Command.SELL:  //2
                            player.displayItemBag();
                            this.promptSell(player);
                            break;

                        case Command.EXIT:  //3
                            break;
            }
        }
        this.leave();
    }

    public void promptBuy(Player player, int shop){
        Scanner input = new Scanner(System.in);
        Item item;
        Weapon weapon;
        int amount = 0;

        if(shop == 1){
            if (player instanceof BotPlayer) {

                item = ((BotPlayer) player).selectItemToBuy();  //POTION: 1
                amount = ((BotPlayer) player).selectAmountToBuy(item);  // (int)(gold/price)
                System.out.println("Which item you want to buy: " + item);
                System.out.println("How many you want to buy (0 for none): " + amount);

            } else {
                //Player chooses item & quantity
                // if regret & don't want buy, just enter amount as 0

                System.out.print("Which items you want to buy: ");
                int itemID = input.nextInt();

                System.out.print("How many you want to buy (0 for none): ");
                amount = input.nextInt();
                item = getItemByID(itemID);
            }

            int price = items.get(item) * amount;
            if (player.getGold() < price) {
                System.out.println("You don't have enough gold");
            } else {
                buyItem(player, item, amount, price);
            }

        } else{
            if (player instanceof BotPlayer) {
                weapon = ((BotPlayer) player).selectWeaponToBuy();
                System.out.println(weapon);
                if (player.getWeaponBag().ownWeapon(weapon)) {
                    return;
                }
            } else {

                System.out.print("Which weapon do you want to buy: ");
                int weaponID = input.nextInt();
                weapon = getWeaponByID(weaponID);

                if (player.getWeaponBag().ownWeapon(weapon)) {
                    System.out.println("You owned this weapon.");
                    return;
                }
            }
                int price = weapons.get(weapon);

                if (player.getGold() < price) {
                    System.out.println("You don't have enough gold");
                } else {
                    buyWeapon(player, weapon, price);
                }
        }
    }

    public void buyItem(Player player, Item item, int amount, int price){
        musicList = new LoopOnceMusicList();

        System.out.println("Purchased successful");

        musicList.play(1);

        player.obtainItem(item,amount);
        player.spendGold(price);
        System.out.println("It costs " + price + "\nRemaining gold: " + player.getGold() + "\n");
    }

    public void buyWeapon(Player player, Weapon weapon, int price) {
        musicList = new LoopOnceMusicList();
        System.out.println("Purchased successful");
        musicList.play(1);
        player.obtainWeapon(weapon);
        player.spendGold(price);
        System.out.println("It costs " + price + "\nRemaining gold: " + player.getGold() + "\n");
    }

    public void promptSell(Player player){

        Scanner input = new Scanner(System.in);
        System.out.print("Which items you want to sell: ");
        int itemID = input.nextInt();
        System.out.print("How many you want to sell: ");
        int amount = input.nextInt();
        ItemBag bag = player.getItemBag();  //getItemBag return ItemBag object
        Item item = bag.get(itemID);    // 1: Potion 2: SmokeBomb

        if(bag.getAmount(item) < amount){
            System.out.println("You don't have enough amount of items");
        }else{
            sellItem(player,item,amount);
        }
    }

    public void sellItem(Player player, Item item, int amount){

        //selling price = buying price / 2
        int price = (items.get(item) * amount) /2;

        musicList = new LoopOnceMusicList();
        System.out.println("Successful deal");
        musicList.play(1);
        player.sellItem(item,amount, price);
        System.out.println("You earned " + price + "\tYour remaining gold: " + player.getGold() + "\n");
    }

    public void leave(){
        System.out.println("You left the shop");
    }

    public void display(){
        int count = 1;
        System.out.println("+++Items++++");
        for (Map.Entry item : items.entrySet()) {
            System.out.printf("Item %d: %s \tPrice: %d\n", count, item.getKey(), (int)item.getValue());
            count ++;
        }

        count = 1;
        System.out.println("\n+++Weapons+++");
        for (Map.Entry weapons : weapons.entrySet()) {
            System.out.printf("Weapon %d: %s \tPrice: %d\n\n", count, weapons.getKey(), (int) weapons.getValue());
            count++;
        }
    }

    //actually is by counts
    public Item getItemByID(int itemID){
        int count = 1 ;
        Item item = null;
        for (Map.Entry itemInMap : items.entrySet()) {
            if(itemID == count){
                item = (Item) itemInMap.getKey();
                break;
            }
            count ++;
        }
        return item;
    }

    public Weapon getWeaponByID(int weaponID){
        int count = 1 ;
        Weapon weapon = null;
        for (Map.Entry weaponInMap : weapons.entrySet()) {
            if(weaponID == count){
                weapon = (Weapon) weaponInMap.getKey();
                break;
            }
            count ++;
        }
        return weapon;
    }


    public static int getPrice(Item item){
        return items.get(item);
    }
}