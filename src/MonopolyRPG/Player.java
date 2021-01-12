package MonopolyRPG;

import MonopolyRPG.Controller.ItemBag;
import MonopolyRPG.Controller.WeaponBag;

import java.util.Random;

public class Player extends Role implements Comparable<Player>{
    //initial status
    private final int HP=30, STRENGTH = 10, DEFENCE = 8, AGILITY = 7;
    private final int LEVEL = 1, GOLD = 200, EXP = 0;
    private final double ACCURACY = 0.7 , EVASION = 0.1;
    private final Weapon WEAPON = Weapon.SWORD;

    private static LoopOnceMusicList musicList;

    private int location;   //player on which tile
    private int currentEXP;
    private ItemBag itemBag;
    private WeaponBag weaponBag;
    private int player_index;

    // New player: accept input name only
    public Player(String name){
        this.respawn(); //local  method
        this.name = name;   //player name is set
        this.equip(WEAPON); //weapon is equipped
        this.itemBag = new ItemBag();   //create linkedList with 5 Potion, 3 smoke bomb
        this.weaponBag = new WeaponBag();//create empty linkedList
        weaponBag.put(this.weapon); //default weapon: Sword (set in line 24)
        player_index = 0;
    }

    // invoked in Player constructor
    // invoked in WeaponBag: promptUser() if player ask to equip
    public void equip(Weapon weapon){
        this.weapon = weapon;
        this.weapon.setEquipped(true);
        this.weapon.printStats();
    }

    // invoked in WeaponBag: promptUser() if player ask to equip
    // unequip before equip
    public void unequip(){
        this.weapon.setEquipped(false);
        this.weapon.printStats();
        this.weapon = null;
    }

    // Invoked in Player: earnEXP()
    // level up if player's currentEXP >= EXP needed for level up
    // Invoked in Game(): movePlayer()
    // get EXP for level up after passing a round of the board ***
    public void levelUp(){
        Random rnd = new Random();

        // Status(currentHP, maxHP, str, def, agi, acc, eva)
        // set level up values to be added when available to level up
        Status levelUpStatus = new Status(0,
                rnd.nextInt(5), //maxHP: rand up 0-4
                rnd.nextInt(3), //str: rand up 0-2
                rnd.nextInt(3), //def: rand up 0-2
                rnd.nextInt(3), //agi: rand up 0-2
                rnd.nextInt(3)/100.0,   //acc: rand up 0-2 /100
                rnd.nextInt(3)/100.0    //eva: rand up 0-2 /100
        );

        // status: object of Status
        // old stats are added with new level up stats
        this.status = this.status.addStatus(levelUpStatus);
        this.level ++;  // +1 level
        weaponBag.upgrade(level);
    }

    // display items in player's item bag
    // Invoked in BotGamePage: executeCommand() 
    // Invoked in MultiplayerGamePage: start()
    // if Command = CHECKITEM(3)
    // Invoked in Shop: enter() if Command = SELL (2)
    public void displayItemBag(){
        // itemBag: object of ItemBag
        itemBag.display();
    }

    // display weapons in player's weapon bag
    // Invoked in BotGamePage: executeCommand
    // Invoked in MultiplayerGamePage
    // if Command=CHECKEQUIPMENT (4)
    public void displayWeaponBag(){
        //weaponBag: object of WeaponBag
        weaponBag.display();
    }

     /*Invoked in BotPlayer : selectBattleCommand() -> create ItemBag obj
      *     to check for Healing / 100% run
      *                     : selectEmptyTile() ->
      *     to check for Healing
      *                    : selectItemToUse() ->
      *     to check for Healing / 100% run
      * Invoked in BattleSystem : selectActionPlayerBattle() ->
      *     to display item in attacker's bag
      *                         : selectActionMonsterBattle()
      *     to display item in player's bag
      * Invoked in EmptyTile : enter() to display item in player's bag
      * Invoked in Shop : promptSell() to get item for sell from bag
     */
    public ItemBag getItemBag() {
        return itemBag;
    }

    // Invoked in BotPlayer : selectEmptyTileCommand() to check for AXE
    //                      : selectWeaponToUse() to return index for selected weapon
    // Invoked in EmptyTile : enter() to display player's weapon in bag
    public WeaponBag getWeaponBag() {
        return weaponBag;
    }

    // display stats when requested / game ends
    public void displayStats(){
        System.out.println("<<< " + this.name + " >>>");
        System.out.printf("%-9s %13s %21s %19s %23s\n%5d %13d %17d %20d %21d\n",
                "||LEVEL||", "Total EXP", "Current level EXP", "EXP to level up", "More EXP to level up",
                this.level, this.exp, this.currentEXP, this.getEXP_nextLevel(), this.getEXP_required());
        System.out.println("\n< Status without Equipment >");
        System.out.println(status);
        System.out.println("\n***|Weapon|***\t--->" + this.weapon);
        System.out.println("\n< Status with Equipment >");
        System.out.println(this.getStatusWithEquipment());
    }

    // Total EXP for level up
    public int getEXP_nextLevel(){
        //25*X*(1+X)
        int expToLevelUp = 25*this.level * (1 + this.level);
        return expToLevelUp;
    }

    // EXP threshold for level up
    private int getEXP_required(){
        int expRequired = getEXP_nextLevel() - this.currentEXP;
        return expRequired;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    // invoked in BattleSystem : drop(), winner in battle earn gold
    public void earnGold(int amount){
        this.gold += amount;
    }

    public void spendGold(int amount){
        this.gold -= amount;
    }

    public void obtainItem(Item item, int amount){
        this.itemBag.put(item,amount);
    }

    public void obtainWeapon(Weapon weapon){
        this.weaponBag.put(weapon);
    }

    public void earnExp(int amount){
        this.exp += amount;
        this.currentEXP += amount;

        if(this.currentEXP >= getEXP_nextLevel()){
            musicList= new LoopOnceMusicList();
            musicList.play(2);
            this.currentEXP -= getEXP_nextLevel();
            this.levelUp();
        }
    }

    public void useItem(Item item){
        musicList= new LoopOnceMusicList();

        itemBag.use(item);
        if (item.getSpecialEffect() == Item.SpecialEffect.ONE_HUNDRED_PERCENT_RUN) {
            musicList.play(6);
        }else if (item.getSpecialEffect() == Item.SpecialEffect.HEALING){
            musicList.play(5);
            this.status = this.status.addStatus(item.getStatus());
        }
    }

    public void sellItem(Item item, int amount, int money){
        itemBag.sell(item,amount);
        this.gold += money;
    }

    // Create a player with default initial stats
    public void respawn(){
        this.status = new Status(HP,HP,STRENGTH,DEFENCE,AGILITY,ACCURACY,EVASION);
        this.gold = GOLD;
        this.exp = EXP;
        this.level = LEVEL;
        this.location = 0;
        this.currentEXP = EXP;
    }

    @Override
    public int compareTo(Player o) {
        if(this.getExp() == o.getExp()){
            return this.getGold() - o.getGold();
        }
        return this.getExp() - o.getExp();
    }

    public int getPlayer_index() {
        return player_index;
    }

    public void setPlayer_index(int player_index) {
        this.player_index = player_index;
    }
}