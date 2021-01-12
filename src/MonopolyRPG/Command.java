package MonopolyRPG;

// the commands for the game

public class Command {
    //action commands
    public final static int ROLL = 1, CHECKSTATS = 2, CHECKITEM = 3, CHECKEQUIPMENT = 4, QUIT = 5;

    //battle commands
    public final static int ATTACK = 1, ITEM = 2, FLEE =3;

    //shop commands
    public final static int BUY=1, SELL =2, EXIT = 3;

    //empty tile commands
    public final static int TILE_ITEM = 1, TILE_EQUIPMENT =2, TILE_QUIT = 3;
}
