package MonopolyRPG.Controller;

import MonopolyRPG.*;

import java.util.LinkedList;
import java.util.Scanner;

public abstract class Game {
    private static LoopOnceMusicList musicList;

    protected int player_num;
    protected Player[] players;;
    protected int remaining_player;
    protected Dice dice;
    protected Map map ;
    protected Board board;

    //display all the commands that user should input
    public void displayCommands(){
        System.out.println(Command.ROLL + ": Roll dice");
        System.out.println(Command.CHECKSTATS + ": Check your status");
        System.out.println(Command.CHECKITEM + ": Check your items");
        System.out.println(Command.CHECKEQUIPMENT + ": Check your equipments");
        System.out.println(Command.QUIT + ": Quit the game");
    }


    public void roll(Player player){
        int dice_rolled = dice.roll();
        System.out.println(player + " rolls " + dice_rolled);

        this.movePlayer(player, dice_rolled, board.getTiles());
    }


    public abstract void start();


    // determine winner when game ends
    public void end(){
        musicList = new LoopOnceMusicList();
        musicList.play(3);

        Player winner = null;
        //simply put one player that's not dead as the winner
        for(int i = 0 ; i < players.length ; i ++ ){
            if(players[i].isDead()){
                continue;
            }else{
                winner = players[i];
                break;
            }
        }

        //if more than 1 player survived then compare them and find who is the winner
        if(remaining_player > 1) {
            for (int i = 0; i < players.length - 1; i++) {
                if(players[i].isDead()){
                    continue;
                }
                if (players[i].compareTo(players[i + 1]) < 0) {
                    winner = players[i + 1];
                }
            }
        }
        System.out.println("Remaining player: " + remaining_player);
        System.out.println(winner + " wins the game.");

        for(int i = 0 ; i < players.length; i++){
            players[i].displayStats();
            System.out.println(players[i].getName() + "'s GOLD: " + players[i].getGold() + "\n");
            System.out.println("|.*.|".repeat(30) + "\n");
        }
        System.out.println("Winner's Gold: " + winner.getGold());
        System.out.println("<<< Game Ends >>>");
    }

    // Roll dice for determining player's turn
    public void arrangePlayer(){
        Dice dice = new Dice();
        System.out.println("Determining who first");
        int [] priority = new int[player_num];
        for(int i = 0 ; i < player_num ; i++){
            int value = dice.roll();
            System.out.println(players[i] + " rolls " + value);
            priority[i] = value;
            board.getTiles()[0].playerEnter(players[i]);    //each player is added to the first tile (START) (LinkedList)
        }

        //bubble sort
        // Player is arranged from the highest to the lowest dice value
        for(int i = 0 ; i < priority.length ; i ++){
            for(int j = 0 ; j < priority.length - 1 ; j ++){
                if(priority[j] < priority[j + 1]){
                    int temp = priority[j];
                    priority[j] = priority[j + 1];
                    priority[j + 1] = temp;

                    Player tempPlayer = players[j];
                    players[j] = players[j + 1];
                    players[j + 1] = tempPlayer;
                }
            }
        }
        for(int i = 0; i  < players.length ; i ++){
            players[i].setPlayer_index(i+1);    //each player is assigned a number
            System.out.println("Player " + players[i].getPlayer_index() + ": " + players[i]);
        }

    }

    public abstract void inputPlayerName();

    public void inputPlayerNumber() {
        System.out.print("Enter number of players [2-4]: ");
        Scanner input = new Scanner(System.in);
        this.player_num = input.nextInt();
        this.players = new Player[player_num];
        this.remaining_player = player_num;
    }

    // move player to the tile after rolling dice
    public void movePlayer(Player player , int steps, Tile[] tiles){
        //move the location
        int previousLocation = player.getLocation();
        // 31 + 5 % 32 = 4 (index start from 0)
        player.setLocation((player.getLocation() + steps) % Board.getNum_of_tiles());
        int currentLocation = player.getLocation();

        //record in the tiles
        tiles[previousLocation].playerLeave(player);    //player is removed from the onTile list
        tiles[currentLocation].playerEnter(player);     //and moved to the new tile
        map.renderMap();    //print map with player on new position


        //passes a round
        // 4 < 31: a new round on the board

        if(currentLocation < previousLocation){
            player.earnExp(player.getEXP_nextLevel());
            //player's total EXP & current EXP is added with the total EXP to get next level
            //current EXP remain as the EXP from last level
            //level up
            player.levelUp();   //level up again?
        }

        //check if battle with player triggered
        if(tiles[currentLocation].isBattleDeclared()){

            LinkedList<Player> playerOnTile = tiles[currentLocation].getPlayerOnTile();

            BattleSystem.startPlayerBattle(playerOnTile.get(1), playerOnTile.get(0));
        }else{
            //trigger what happens on the tiles
            tiles[currentLocation].trigger(player);
        }
    }


    public void calculateRemainingPlayer(){
        //calculate the remaining player survives
        //quite redundant, but I cant get a better way to do yet
        //would think on it later
        remaining_player = 0;
        for(int i = 0 ; i < players.length ; i++){
            if(!players[i].isDead()){
                remaining_player ++;
            }
        }
    }
}