package MonopolyRPG;

import MonopolyRPG.Controller.WeaponBag;

import java.util.LinkedList;

public enum Weapon {
    //weapon dictionary
    SWORD (1,"Sword",new Status(0,0,3,0,1,0,0), 1),
    AXE (2, "Axe", new Status(0,10,5,0,-3,-0.05,-0.05), 1),
    SPEAR (3, "Spear", new Status(0, 5, 5, 8, 10, 0.2, -0.02), 1),
    HAMMER(4, "Hammer", new Status(0, 5, 10, 10, 2, -0.1, 0.5), 1),
    BOW (3,"Bow", new Status(0,5,3,0,3,5,0), 1),
    ULTIMATECANON (4,"Ultimate Cannon", new Status(0,100,100,100,100,100,100), 1);

    private int weaponID;
    private String description;
    private int level;
    private boolean isEquipped;
    private Status status;

    Weapon(int weaponID, String description, Status status, int level) {
        this.weaponID = weaponID;
        this.status = status;
        this.description = description;
        this.level = level;
        this.isEquipped = false;
    }

    public boolean isEquipped(){
        return isEquipped;
    }

    public void setEquipped(boolean equip){
        this.isEquipped = equip;
    }

    public Status getStatus() {
        return status;
    }

    /*@Override
    public String toString() {
        return  "\t" + description +
                "\nAttributes:\n" + status +
                "\nEquipped status: " + isEquipped + "\n";
    }*/

    public void printStats(){
        System.out.println("\t" + description +
                "\nAttributes:\n" + status +
                "\nEquipped status: " + isEquipped + "\n");
    }
}