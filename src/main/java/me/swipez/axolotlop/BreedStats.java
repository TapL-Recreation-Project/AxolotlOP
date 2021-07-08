package me.swipez.axolotlop;

public class BreedStats {

    private int level = 0;
    private int bredCount = 0;

    private final int[] levelRequirements;

    public BreedStats(int[] levelRequirements) {
        this.levelRequirements = levelRequirements;
    }

    public void addBredCount(int count){
        bredCount+=count;
        for (int testLevel : levelRequirements){
            if (testLevel == bredCount){
                level++;
            }
        }
    }

    public void setBredCount(int count){
        bredCount = count;
    }

    public void setLevel(int count){
        level = count;
    }

    public int getBredCount() {
        return bredCount;
    }

    public int getLevel() {
        return level;
    }
}
