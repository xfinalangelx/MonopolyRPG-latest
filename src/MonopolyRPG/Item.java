package MonopolyRPG;

public enum Item {
    //consumable item dictionary
    POTION (1,"Potion", new Status(20), SpecialEffect.HEALING),
    HIPOTION (2,"Hi-potion", new Status(50), SpecialEffect.HEALING),
    SMOKEBOMB(3,"Smoke bomb", SpecialEffect.ONE_HUNDRED_PERCENT_RUN),
    FULLPOTION (4,"Full-potion", new Status(1000), SpecialEffect.HEALING);

    public enum SpecialEffect{
        ONE_HUNDRED_PERCENT_RUN,
        HEALING
    }
    private int itemID;
    private String description;
    private Status status;
    private SpecialEffect specialEffect;

    // POTION, HIPOTION
    Item(int itemID, String description, Status status, SpecialEffect effect) {
        this.itemID = itemID;
        this.description = description;
        this.specialEffect = null;
        this.status = status;
        this.specialEffect = effect;
    }

    // 100% RUN
    Item(int itemID, String description, SpecialEffect effect) {
        this.itemID = itemID;
        this.description = description;
        this.specialEffect = effect;
        this.status = null;
    }

    @Override
    public String toString() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public SpecialEffect getSpecialEffect() {
        return specialEffect;
    }
}
