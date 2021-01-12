package MonopolyRPG;

import MonopolyRPG.Controller.ItemBag;
import MonopolyRPG.Controller.Shop;
import MonopolyRPG.Controller.WeaponBag;

import java.util.LinkedList;

public class BotPlayer extends Player{
    public BotPlayer(String name) {
        super(name);
    }

    public boolean isHpLow(){
        return this.getStatusWithEquipment().getCurrentHP() <= 10;
    }

    public int selectActionCommand(){
        return Command.ROLL;
    }

    // BotPlayer commands: if low HP -> healing / flee
    // if not low HP -> attack
    // if low HP, no item, PvP -> attack
    // if low HP, no item, PvM -> flee
    // if not battling player -> flee
    public int selectBattleCommand(boolean battlingPlayer){
        if(isHpLow()){
            ItemBag itemBag = this.getItemBag();
            if(itemBag.searchItemBySpecialEffect(Item.SpecialEffect.HEALING) != null){
                return Command.ITEM;    //return 2
            }else if(itemBag.searchItemBySpecialEffect(Item.SpecialEffect.ONE_HUNDRED_PERCENT_RUN) != null){
                return Command.ITEM;    // return 2
            }else{
                if(battlingPlayer){
                    return Command.ATTACK;  // return 1
                }else{
                    return Command.FLEE;    // return 3
                }
            }
        }else{
            return Command.ATTACK;
        }
    }

    public int selectMonsterToAttack(LinkedList<Monster> monsters){
        int monsterIndex = 0;
        int count = 0;
        int lowestHP = monsters.getFirst().getStatusWithEquipment().getCurrentHP(); //current HP of first mon in list
        int highestAttack = monsters.getFirst().getStatusWithEquipment().getStrength(); //str of first mon in list
        for(Monster monster : monsters){
            //attack the highest str
            if(monster.getStatusWithEquipment().getStrength() > highestAttack){
                monsterIndex = count;
                lowestHP = monster.getStatusWithEquipment().getCurrentHP();
                highestAttack = monster.getStatusWithEquipment().getStrength();
            }else if(monster.getStatusWithEquipment().getStrength() == highestAttack){// if same str
                //attack the one who has lower hp
                if(monster.getStatusWithEquipment().getCurrentHP() < lowestHP){
                    monsterIndex = count;
                    lowestHP = monster.getStatusWithEquipment().getCurrentHP();
                    highestAttack = monster.getStatusWithEquipment().getStrength();
                }
            }
            count ++;
        }
        return monsterIndex;
    }

    public int selectShopCommand(){
        if(this.getGold() >= Shop.POTION_PRICE){
            return Command.BUY;
        }else{
            return Command.EXIT;
        }
    }

    // Action of bot on empty tile
    // if low HP -> Healing
    // if not low HP -> equip weapon if has AXE & not using AXE
    // else quit, game continue
    public int selectEmptyTileCommand(){
        if(isHpLow()){
            ItemBag itemBag = this.getItemBag();
            Item item = itemBag.searchItemBySpecialEffect(Item.SpecialEffect.HEALING);
            if(item != null){
                return Command.TILE_ITEM;   // return 1
            }
        }

        WeaponBag weaponBag = this.getWeaponBag();
        if(weaponBag.contains(Weapon.AXE) != -1 && this.getWeapon() != Weapon.AXE){
            return Command.TILE_EQUIPMENT;  // return 2
        }
        return Command.TILE_QUIT;   //return 3
    }

    public int selectWeaponToUse(){
        WeaponBag weaponBag = this.getWeaponBag();
        if(this.getWeapon() != Weapon.AXE){
            return weaponBag.contains(Weapon.AXE);
        }
        return -1;
    }

    //bot uses item
    // if low HP on empty tile, then healing
    // if not on empty tile, then 100% run
    public Item selectItemToUse(boolean isEmptyTile){
        if(isHpLow()){

            ItemBag itemBag = this.getItemBag();
            Item item = itemBag.searchItemBySpecialEffect(Item.SpecialEffect.HEALING);
            if(item != null){
                return item;
            }

            if(!isEmptyTile) {
                item = itemBag.searchItemBySpecialEffect(Item.SpecialEffect.ONE_HUNDRED_PERCENT_RUN);
                return item;
            }
        }
        return null;
    }

    public Item selectItemToBuy(){
            return Item.POTION;
    }

    // select weapon to buy based on level, if owned
    public Weapon selectWeaponToBuy() {
        if (this.level < 5) {
            if (getWeaponBag().ownWeapon(Weapon.AXE)) {
                return Weapon.SPEAR;
            } else if (getWeaponBag().ownWeapon(Weapon.SPEAR)) {
                return Weapon.HAMMER;
            } return Weapon.AXE;
        } else if (this.level < 8) {
            if (getWeaponBag().ownWeapon(Weapon.HAMMER)) {
                return Weapon.SPEAR;
            }
            return Weapon.HAMMER;
        } else {
            return Weapon.SPEAR;
            }
        }


    public int selectAmountToBuy(Item item){
        return (int)Math.floor(this.getGold()/Shop.getPrice(item));
    }
}
