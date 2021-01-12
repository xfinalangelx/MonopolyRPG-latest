package MonopolyRPG.Controller;

import MonopolyRPG.*;

import java.util.LinkedList;
import java.util.Scanner;

public class WeaponBag extends Bag {

    private LinkedList<Weapon> weapons ;

    // invoked in Player constructor
    // create an empty LinkedList to store weapon
    // weapon is added using weaponBag.put()
    public WeaponBag(){

        this.weapons = new LinkedList<>();
    }

    //index starts from 0
    public Weapon get(int index){
        return weapons.get(index);
    }

    public boolean put(Weapon weapon){
        if(this.isFull()){
            return false;
        }
            weapons.add(weapon);
        return true;
    }

    public LinkedList<Weapon> upgrade(int level) {

        for (Weapon weaponInBag : weapons) {
            Status status = weaponInBag.getStatus();
            weaponInBag.getStatus().upgradeWeapon(status, level);
        }
        System.out.println("Weapons are upgraded to level " + level);
        return weapons;
    }

    @Override
    public void display() {
        //display the weapon bag
        int count = 1;
        for(Weapon weapon : weapons){
            System.out.println("\nWeapon " + count + ": " + weapon);
            weapon.printStats();
            count ++;
        }
    }

    public boolean promptUser(Player player, Boolean active){
        int weaponID = -1;
        if(player instanceof BotPlayer){
            weaponID = ((BotPlayer) player).selectWeaponToUse();
        }else {
            Scanner input = new Scanner(System.in);
            System.out.print("Which equipment you want to equip(-1 to go back): ");
            weaponID = input.nextInt() - 1;
            System.out.println();
            if(weaponID == -2){return false;}
        }

        Weapon weapon = this.get(weaponID);
        if(weapon != null){
            System.out.println("Player unequipped " + player.getWeapon());
            player.unequip();
            System.out.println("Player equip " + weapon);
            player.equip(weapon);
            return false;
        }
        return true;
    }

    public int contains(Weapon weaponToSearch){
        int index =0 ;
        for(Weapon weapon : weapons){
            if(weapon == weaponToSearch){
                return index;
            }
            index ++;
        }
        return -1;
    }

    public boolean ownWeapon(Weapon weaponToSearch){
        for(Weapon weapon : weapons){
            if(weapon == weaponToSearch){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isFull() {
        return this.weapons.size() >= this.capacity;
    }
}