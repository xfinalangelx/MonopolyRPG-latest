package MonopolyRPG;

import java.util.Currency;

public class Status {
    private int currentHP, maxHP, strength, defence, agility;
    private double accuracy, evasion;

    public Status(int currentHP, int maxHP, int strength, int defence, int agility, double accuracy, double evasion) {
        this.currentHP = currentHP;
        this.maxHP = maxHP;
        this.strength = strength;
        this.defence = defence;
        this.agility = agility;
        this.accuracy = accuracy;
        this.evasion = evasion;
    }

    public Status (int currentHP){
        this.currentHP = currentHP;
        this.maxHP = 0;
        this.strength = 0;
        this.defence = 0;
        this.agility = 0;
        this.accuracy = 0;
        this.evasion = 0;
    }

    public Status addStatus(Status anotherStat){
        //check if the new current hp is higher than the maximum hp, if yes then set it to the maximum hp.
        int newCurrentHp = currentHP + anotherStat.getCurrentHP() > maxHP + anotherStat.getMaxHP() ?
                maxHP + anotherStat.getMaxHP() : currentHP + anotherStat.getCurrentHP();

        return new Status( newCurrentHp,
                maxHP + anotherStat.getMaxHP(),
                strength + anotherStat.getStrength(),
                defence + anotherStat.getDefence(),
                agility + anotherStat.getAgility(),
                accuracy + anotherStat.getAgility(),
                evasion + anotherStat.getEvasion());
    }

    public Status upgradeWeapon(Status basicStats, int level) {

        return new Status(basicStats.getCurrentHP(),
                maxHP + basicStats.getMaxHP() * level / 3,
                strength + basicStats.getStrength() * level/3,
                defence + basicStats.getDefence() * level/3,
                agility + basicStats.getAgility() * level/3,
                accuracy + basicStats.getAccuracy() * level/3,
                evasion + basicStats.getEvasion() * level / 3);
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getStrength() {
        return strength;
    }

    public int getDefence() {
        return defence;
    }

    public int getAgility() {
        return agility;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getEvasion() {
        return evasion;
    }

    public void setCurrentHP(int currenthp) {
        this.currentHP = currenthp;
    }

    public void setMaxHP(int maxhp) {
        this.maxHP = maxhp;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public void setEvasion(double evasion) {
        this.evasion = evasion;
    }

    @Override
    public String toString() {
        if (currentHP < 0) {
            currentHP = 0;
        }
        return String.format("%-11s %11s %11s %11s %11s %11s %11s\n%6d %14d %10d %11d %11d %12.2f %11.2f\n",
                "Current Hp", "Max HP", "Strength", "Defence", "Agility", "Accuracy", "Evasion",
                currentHP, maxHP, strength, defence, agility, accuracy, evasion);
    }
}