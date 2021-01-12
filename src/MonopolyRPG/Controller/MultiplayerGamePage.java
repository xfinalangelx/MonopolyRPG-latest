package MonopolyRPG.Controller;

import MonopolyRPG.*;

import java.util.Scanner;

public class MultiplayerGamePage extends Game{

    public MultiplayerGamePage() {
        this.board = new Board(true);
        this.map = new Map(this.board);
        this.dice = new Dice();
    }

    //start the game
    @Override
    public void start(){
        this.inputPlayerNumber();
        this.inputPlayerName();
        this.arrangePlayer();
        System.out.println("<<< Game Starts >>>");

        //map.renderMap();
        Scanner input = new Scanner(System.in);

        /*
        2 conditions for game to end
        1) Only 1 player remaining in the game
        2) All player agree to end the game
         */

        while(remaining_player > 1){
            //each player's turn
            for(int i = 0 ; i < player_num ; i ++){
                //if the player is dead, skip his/her turn
                if (players[i].isDead()){
                    continue;
                }

                //the player's round ends only when he/she rolls the dice
                boolean rolled = false;
                while(!rolled){
                    map.renderMap();
                    System.out.println("Now is " + players[i] + "'s turn, enter your command");
                    this.displayCommands(); //cmd options

                    System.out.print("Command: ");
                    int command = input.nextInt();  //input cmd

                    switch (command) {
                        case Command.ROLL:
                            rolled = true;
                            this.roll(players[i]);
                            calculateRemainingPlayer();

                            if(remaining_player <= 1){
                                this.end();
                                System.out.println("Do you want to Restart the game? [Y/N]");
                                char choice = input.next().charAt(0);
                                if (choice == 'Y'){
                                    GameModePage gameModePage = new GameModePage();
                                    gameModePage.start();
                                }else {
                                    System.exit(0);
                                }
                                return;
                            }
                            break;

                        case Command.CHECKSTATS:
                            players[i].displayStats();
                            break;
                        case Command.CHECKITEM:
                            players[i].displayItemBag();
                            break;
                        case Command.CHECKEQUIPMENT:
                            players[i].displayWeaponBag();
                            break;
                        case Command.QUIT:
                            boolean all_agree =true;
                            for(int j = 0 ; j < i ; j++){
                                System.out.print(players[j] + " agrees to end the game?[Y/N]: ");
                                char agree = input.next().charAt(0);
                                if(agree != 'Y'){
                                    all_agree = false;
                                    break;
                                }
                            }

                            // ask each player's opinion to quit the game
                            // if not all agree, then continue game
                            for(int j = i + 1 ; j < players.length ; j ++){
                                System.out.print(players[j] + " agrees to end the game?[Y/N]: ");
                                char agree = input.next().charAt(0);
                                if(agree != 'Y'){
                                    all_agree = false;
                                    break;
                                }
                            }

                            // if all players agree to quit, then end game.
                            // can choose restart or exit game
                            if(all_agree) {
                                this.end();
                                System.out.println("Do you want to Restart the game? [Y/N]");
                                char choice = input.next().charAt(0);
                                if (choice == 'Y'){
                                    GameModePage gameModePage = new GameModePage();
                                    gameModePage.start();
                                }else {
                                    System.exit(0);
                                }
                                return;
                            }
                    }
                }
            }
        }
    }

    @Override
    public void inputPlayerName() {

        Scanner input =new Scanner(System.in);
        for(int i = 0 ; i < player_num ; i ++){
            System.out.print("Player " + (i+1) + ", please enter your name in one line: ");
            players[i] = new Player(input.nextLine());
        }
    }
}