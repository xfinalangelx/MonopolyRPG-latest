package MonopolyRPG.Controller;

import MonopolyRPG.Item;
import MonopolyRPG.Player;
import MonopolyRPG.Weapon;
import MonopolyRPG.LoopOnceMusicList;

import java.util.LinkedList;
import java.util.Random;

public class Chest {
    LinkedList<Item> items ;
    LinkedList<Weapon> weapons;
    public Chest() {
        LoopOnceMusicList musicList = new LoopOnceMusicList();
        musicList.play(0);
        items = new LinkedList<>();
        weapons = new LinkedList<>();
        this.generateWeapon();
        this.generateItems();
    }

    public void generateWeapon(){
        Random rnd = new Random();
        if(rnd.nextInt(5) < 2){
            weapons.add(Weapon.AXE);
        }else if(rnd.nextInt(5) > 2){
            weapons.add(Weapon.BOW);
        }else if(rnd.nextInt(10000) == 450){
            weapons.add(Weapon.ULTIMATECANON);
        }
    }

    public void generateItems(){
        Random rnd = new Random();
        if(rnd.nextInt(5) < 3){
            items.add(Item.HIPOTION);
        }else if (rnd.nextInt(1000) > 900){
            items.add(Item.FULLPOTION);
        }else{
            items.add(Item.POTION);}

    }

    public void open(Player player){
        //can put the animation of opening chest here
        System.out.println(player + " opened a chest");
        System.out.print("The chest contains ");
        for(Weapon weapon : weapons){
            System.out.println(weapon);
            player.obtainWeapon(weapon);
        }
        for(Item item: items){
            System.out.println(item);
            player.obtainItem(item, 1);
        }
    }

    public LinkedList<Item> getItems() {
        return items;
    }

    public LinkedList<Weapon> getWeapons() {
        return weapons;
    }
}