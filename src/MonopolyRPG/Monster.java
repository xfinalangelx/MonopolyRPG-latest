package MonopolyRPG;

import java.util.LinkedList;
import java.util.Random;

// 3 types of monster
enum mons {
    mon1(15, 4, 3, 3, 10, 20, 0.1, 0.8),    //high str
    mon2(18, 2, 3, 3, 15, 22, 0.1, 0.5),    //high hp
    mon3(15, 2, 3, 5, 20, 24, 0.1, 0.3);    //high agi

    private int hp, str, def, agi, gold, exp;
    private double eva;
    private double accu;

    mons(int hp, int str, int def, int agi, int gold, int exp, double eva, double accu) {
        this.hp = hp;
        this.str = str;
        this.def = def;
        this.agi = agi;
        this.gold = gold;
        this.exp = exp;
        this.eva = eva;
        this.accu = accu;
    }

    public int getHp() {
        return hp;
    }

    public int getStr() {
        return str;
    }

    public int getDef() {
        return def;
    }

    public int getAgi() {
        return agi;
    }

    public int getGold() {
        return gold;
    }

    public int getExp() {
        return exp;
    }

    public double getEva() {
        return eva;
    }

    public double getAccu() {
        return accu;
    }
}
public class Monster extends Role{


    public Monster(String name, int level) {
        Random rnd = new Random();
        int monType = rnd.nextInt();
        int coefficient, hp, strength, defence, agility;
        double accuracy, evasion;

        if (monType == 0) {
            this.exp = mons.mon1.getExp();
            this.level = level;
            this.name = name + " Level " + this.level;
            this.gold = mons.mon1.getGold();
            this.weapon = null;
            coefficient = this.level -1;
            hp = mons.mon1.getHp() + coefficient * rnd.nextInt(3);
            strength = mons.mon1.getStr() + (int)(coefficient * 0.5);
            defence = mons.mon1.getDef() + (int)(coefficient * 0.5);
            agility = mons.mon1.getAgi() + (int)(coefficient * 0.5);
            accuracy = mons.mon1.getAccu()+ (int)(coefficient * 0.5)/100.0;
            evasion = mons.mon1.getEva() + (int)(coefficient * 0.5)/100.0;

        } else if (monType == 1) {
            this.exp = mons.mon2.getExp();
            this.level = level;
            this.name = name + " Level " + this.level;
            this.gold = mons.mon2.getGold();
            this.weapon = null;
            coefficient = this.level - 1;
            hp = mons.mon2.getHp() + coefficient * rnd.nextInt(3);
            strength = mons.mon2.getStr() + (int) (coefficient * 1.5);
            defence = mons.mon2.getDef() + (int) (coefficient * 1.5);
            agility = mons.mon2.getAgi() + (int) (coefficient * 1.5);
            accuracy = mons.mon2.getAccu() + (int) (coefficient * 1.5) / 100.0;
            evasion = mons.mon2.getEva() + (int) (coefficient * 1.5) / 100.0;

        } else {
            this.exp = mons.mon3.getExp();
            this.level = level;
            this.name = name + " Level " + this.level;
            this.gold = mons.mon3.getGold();
            this.weapon = null;
            coefficient = this.level -1;
            hp = mons.mon3.getHp() + coefficient * rnd.nextInt(3);
            strength = mons.mon3.getStr() + (int)(coefficient * 1.5);
            defence = mons.mon3.getDef() + (int)(coefficient * 1.5);
            agility = mons.mon3.getAgi() + (int)(coefficient * 1.5);
            accuracy = mons.mon3.getAccu()+ (int)(coefficient * 1.5)/100.0;
            evasion = mons.mon3.getEva() + (int)(coefficient * 1.5)/100.0;
        }
        this.status = new Status(hp,hp,strength,defence,agility,accuracy,evasion);
    }

    // drop POTION / HI-POTION based on rand probability
    public LinkedList<Item> dropItem(){
        LinkedList<Item> itemsDropped = new LinkedList<>();
        if(Probability.dropped(0.4)) {
            itemsDropped.add(Item.POTION);
        } else if (Probability.dropped(0.3)) {
            itemsDropped.add(Item.HIPOTION);
        }

        return itemsDropped;
    }

}
