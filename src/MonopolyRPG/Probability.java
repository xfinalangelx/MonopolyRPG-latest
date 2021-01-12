package MonopolyRPG;

import java.util.Random;

public class Probability {
    public static boolean escaped(int escaper_agility, int enemy_agility, int trials){
        //probability = agility1 * 32 /agility2 + (30 * number of tries)
        if (enemy_agility <= 0 ){
            return true;
        }
        int threshold = escaper_agility * 32 / enemy_agility + (30 * trials);
        //System.out.println(threshold);
        if(threshold >= 256){
            return true;
        }
        Random rnd = new Random();
        return rnd.nextInt(256) < threshold;
        // if threshold more than rand, cant flee
        // else return false,flee, end battle
    }

    // player / bot player / monster attacks
    public static boolean hit(double attacker_accuracy, double defender_evasion){
        //miss rate
        int miss = (int)Math.round(( (1 - attacker_accuracy) * defender_evasion) * 100);
        Random rnd = new Random();
        if(miss > rnd.nextInt(100)){
            return false;
        }else{
            return true;
        }

    }

    // item dropped if return true
    // Invoked in Monster: dropItem()
    public static boolean dropped(double dropRate){
        Random rnd = new Random();
        return rnd.nextInt(100) <= (int)Math.round(dropRate*100);
    }
}
