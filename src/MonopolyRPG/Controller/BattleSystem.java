package MonopolyRPG.Controller;

import MonopolyRPG.*;

import java.util.LinkedList;
import java.util.Scanner;

public class BattleSystem {
    private static LoopOnceMusicList musicList;
    private static int trials = 0;

    public static boolean selectActionPlayerBattle(Player attacker, Player defender){

        System.out.println(attacker + "'s turn, enter your command");
        System.out.println(Command.ATTACK + " for attack\n"+Command.ITEM+" for using item\n");
        Scanner input = new Scanner(System.in);
        int command = -1;
        if(attacker instanceof BotPlayer){  //bot select action on battle tile
            command = ((BotPlayer) attacker).selectBattleCommand(true);
        }else{
            command = input.nextInt();
        }
        switch(command){
            case Command.ATTACK:
                attack(attacker, defender);
                return true;
            case Command.ITEM:
                //display and choose item to be used
                ItemBag bag = attacker.getItemBag();
                bag.display();
                return bag.promptUser(attacker, true);
        }
        return true;
    }

    // bot player vs monster / player vs monster
    public static boolean selectActionMonsterBattle(Player player, LinkedList<Monster> monsters, LinkedList<Monster> deadMonsters){
        System.out.println("\n" + player + "'s turn, enter your command");
        System.out.println(Command.ATTACK + " ATTACK\n" + Command.ITEM + " USE ITEM\n" + Command.FLEE + " FLEE");
        System.out.println("4 Check status");
        Scanner input = new Scanner(System.in);

        int command = -1;
        if(player instanceof BotPlayer){
            command = ((BotPlayer) player).selectBattleCommand(false);
        }else{
            System.out.print("Command: ");  //player input cmd
            command = input.nextInt();
        }

        switch(command){
            case 4:
                if (player != null) {
                    player.displayStats();
                    break;
                }
            case Command.ATTACK:    //1
                int monster_index = 0;
                if(player instanceof BotPlayer){
                    monster_index = ((BotPlayer) player).selectMonsterToAttack(monsters);
                }

                //if number of monsters more than 1, ask which to be attacked
                if(monsters.size() > 1) {
                    if(player instanceof BotPlayer){

                    }else {
                        System.out.println("Which monster to be attacked?");
                        for (int i = 0; i < monsters.size(); i++) {
                            System.out.println((i + 1) + " for " + monsters.get(i));
                        }
                        System.out.print("Attack: ");
                        monster_index = input.nextInt() - 1;
                    }
                    attack(player , monsters.get(monster_index ));

                    //check if the monster is dead
                    if(monsters.get(monster_index).isDead()){
                        deadMonsters.add(monsters.remove(monster_index));
                    }
                }else{
                    attack(player, monsters.get(monster_index));

                    //check if the monster is dead
                    if(monsters.get(monster_index).isDead()){
                        deadMonsters.add(monsters.remove(monster_index));
                    }
                }
                return true;
            case Command.ITEM:

                //displays items in the bag
                ItemBag bag = player.getItemBag();
                bag.display();

                //asks for which item to be used
                return bag.promptUser(player, true);
            case Command.FLEE:

                trials ++;
                int highest_agility = monsters.get(0).getStatusWithEquipment().getAgility();
                //I will compare with the highest agility among the monsters
                for(Monster monster : monsters){
                    if(highest_agility < monster.getStatusWithEquipment().getAgility()){
                        highest_agility = monster.getStatusWithEquipment().getAgility();
                    }
                }

                //check if successfully escaped
                if(Probability.escaped(player.getStatusWithEquipment().getAgility(),highest_agility, trials)){
                    run(player);
                    return false;
                }
                return true;
        }
        return true;
    }


    // damage of attacker on defender
    private static int calculateDamage(Role attacker, Role defender){
        int damage = attacker.getStatusWithEquipment().getStrength() - (int)(0.5 * defender.getStatusWithEquipment().getDefence());
        if(damage <= 0 ){
            damage = 1;
        }
        return damage;
    }

    // player / bot player vs monster battle
    public static void startMonsterBattle(Player player, LinkedList<Monster> monsters){
        musicList = new LoopOnceMusicList();

        trials = 0;
        LinkedList <Monster> deadMonsters = new LinkedList<>();
        for(Monster monster : monsters){
            System.out.println(player + " encounters " + monster);
        }

        musicList.play(4);

        while(!player.isDead() && monsters.size() > 0){ //playerHP <=0 = isDead
            //if flee successfully will return false
            if(!selectActionMonsterBattle(player, monsters, deadMonsters)){
                endBattle();
                return;
            }

            musicList.play(4);

            //monsters' turn to attack player
            for(Monster monster : monsters){
                attack(monster, player);    // miss/attacked?, cal dmg, is defender dead?, defender's HP
                //if player is dead then can end
                if (player.isDead()) {
                    endBattle();
                    return;
                }
            }
        }

        //if player wins the battle, monsters drop items
        for(Monster monster : deadMonsters){
            drop(player,monster);
        }

        endBattle();
    }

    // PvP battle
    public static void startPlayerBattle(Player player1, Player player2){
        System.out.println(player1 + " encounters " + player2);

        musicList = new LoopOnceMusicList();
        musicList.play(4);

        while(!player1.isDead() && !player2.isDead()){

            //player 1's turn, if flee away then stop the battle
            //but got no drops
            if(!selectActionPlayerBattle(player1,player2)){
                endBattle();
                break;
            }

            //if player1 kills the player2, ends the game also
            if(player2.isDead()){
                drop(player1,player2);
                endBattle();
                break;
            }

            //same goes to player 2's turn
            if(!selectActionPlayerBattle(player2,player1)){
                endBattle();
                break;
            }

            if(player1.isDead()) {
                drop(player2, player1);
                endBattle();
                break;
            }
        }
    }

    private static void attack(Role attacker, Role defender){
        //check if the attack miss
        if(!Probability.hit(attacker.getStatusWithEquipment().getAccuracy(), defender.getStatusWithEquipment().getEvasion())){
            System.out.println(attacker + " missed!");
            return;
        }

        //calculate the damage
        int damage = calculateDamage(attacker, defender);
        //decrease the hp
        defender.getStatus().setCurrentHP(defender.getStatusWithEquipment().getCurrentHP() - damage);

        //display it
        System.out.println(attacker + " deals " + damage + " damage to " + defender);

        //check if the defender is dead
        if(defender.isDead()){
            System.out.println(defender + " is dead");
        }else {
            //if the defender is not dead, displays hp left
            System.out.println(defender + " left " + defender.getStatusWithEquipment().getCurrentHP() + "HP");
        }
    }

    //the loser drops exp, gold and items (if it is monster)
    // for PvP battle, drop EXP & gold only
    public static void drop(Role winner, Role loser){
        //only if the winner is player
        //monster will be despawned although they win
        if( winner instanceof Player){

            System.out.println(winner + " obtains " + loser.getExp() + " EXP");
            ((Player) winner).earnExp(loser.getExp());

            System.out.println(winner + " obtains " + loser.getGold() + " GOLD");
            ((Player) winner).earnGold(loser.getGold());

            //only monster will drop items
            if(loser instanceof Monster){
                LinkedList<Item> itemsDropped = ((Monster) loser).dropItem();   //potion / hi-potion
                for(Item item : itemsDropped){
                    System.out.println(loser + " drops " + item);
                    ((Player) winner).obtainItem(item, (int) (Math.random() * 2) + 1 ); // get 1/2 item
                }
            }
        }
    }

    //run away from the battle
    public static void run(Player player){
        musicList = new LoopOnceMusicList();
        musicList.play(7);
        System.out.println(player + " runs away..");
    }

    //end the battle
    public static void endBattle(){
        System.out.println("Battle ends!");
    }
}