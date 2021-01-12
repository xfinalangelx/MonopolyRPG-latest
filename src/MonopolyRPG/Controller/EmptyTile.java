package MonopolyRPG.Controller;

import MonopolyRPG.BotPlayer;
import MonopolyRPG.Command;
import MonopolyRPG.Player;

import java.util.Scanner;

public class EmptyTile {

    public void enter(Player player){
        Scanner input = new Scanner(System.in);
        int command = -1;
        while(command != Command.TILE_QUIT) {
            System.out.println("Do you want to use item or equip equipment?");
            System.out.print(Command.TILE_ITEM + " for item\n"+Command.TILE_EQUIPMENT+" for equipment\n"+Command.TILE_QUIT+" for quit\n");
            System.out.print("Enter your command: ");
            if(player instanceof BotPlayer){
                command = ((BotPlayer) player).selectEmptyTileCommand();
                System.out.println(command);
            }else {
                command = input.nextInt();
                System.out.println();
            }
            if (command == Command.TILE_ITEM) { //1
                ItemBag bag = player.getItemBag();
                bag.display();
                bag.promptUser(player, false);
                System.out.println();

            } else if (command == Command.TILE_EQUIPMENT) { //2
                WeaponBag bag = player.getWeaponBag();
                bag.display();
                bag.promptUser(player,true);
                System.out.println();
            }
        }

        //quit
        //close scene here
    }
}