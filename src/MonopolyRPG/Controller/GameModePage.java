package MonopolyRPG.Controller;

import java.util.Scanner;

public class GameModePage {
    private final int MULTIPLAYER = 1, BOT = 2;

    public void start(){
        System.out.println("██████╗ ██████╗  ██████╗     ██╗  ██╗    ███╗   ███╗ ██████╗ ███╗   ██╗ ██████╗ \n" +
                "██╔══██╗██╔══██╗██╔════╝     ╚██╗██╔╝    ████╗ ████║██╔═══██╗████╗  ██║██╔═══██╗\n" +
                "██████╔╝██████╔╝██║  ███╗     ╚███╔╝     ██╔████╔██║██║   ██║██╔██╗ ██║██║   ██║\n" +
                "██╔══██╗██╔═══╝ ██║   ██║     ██╔██╗     ██║╚██╔╝██║██║   ██║██║╚██╗██║██║   ██║\n" +
                "██║  ██║██║     ╚██████╔╝    ██╔╝ ██╗    ██║ ╚═╝ ██║╚██████╔╝██║ ╚████║╚██████╔╝\n" +
                "╚═╝  ╚═╝╚═╝      ╚═════╝     ╚═╝  ╚═╝    ╚═╝     ╚═╝ ╚═════╝ ╚═╝  ╚═══╝ ╚═════╝ \n" +
                "                                                                                ");
        System.out.println("Select the game mode");
        System.out.println(MULTIPLAYER + " for vs player ");
        System.out.println(BOT +" for vs bots\n");
        Scanner input = new Scanner(System.in);
        System.out.print("Your choice: ");
        int game_mode = input.nextInt();
        switch (game_mode){
            case MULTIPLAYER:
                MultiplayerGamePage game = new MultiplayerGamePage();
                game.start();
                break;
            case BOT:
                BotGamePage botGame = new BotGamePage();
                botGame.start();
                break;
        }
    }
}