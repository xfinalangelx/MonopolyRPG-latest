package MonopolyRPG;

import java.util.ArrayList;

public class LoopOnceMusicList {
    private ArrayList<LoopOnceMusic> loopOnceMusicArrayList;
    //uses this variable to record the index of music is currently playing
    private int currentMusic;

    public LoopOnceMusicList(){
        this.loopOnceMusicArrayList = new ArrayList<>();
        currentMusic = 0;

        //adds the predefined music list
        addMusic(new LoopOnceMusic("Chest","Chest.mp3"));
        addMusic(new LoopOnceMusic("Coin","Coin.mp3"));
        addMusic(new LoopOnceMusic("LevelUp","Level-up.mp3"));
        addMusic(new LoopOnceMusic("Win","Win.mp3"));
        addMusic(new LoopOnceMusic("Battle","Battle.mp3"));
        addMusic(new LoopOnceMusic("Potion","Healing.mp3"));
        addMusic(new LoopOnceMusic("SmokeBomb","SmokeBomb.mp3"));
        addMusic(new LoopOnceMusic("FleeSuccessful","Flee.mp3"));

    }


    public void addMusic(LoopOnceMusic music){
        loopOnceMusicArrayList.add(music);
    }

    //this plays the music of currentMusic index
    public void play(){
        loopOnceMusicArrayList.get(currentMusic).play();
    }

    //this can selectively play music of any index
    public void play(int index){
        //stop the music playing first
        stop();

        //then only plays the music
        currentMusic = index;
        loopOnceMusicArrayList.get(index).play();
    }

    public void stop(){
        loopOnceMusicArrayList.get(currentMusic).stop();
    }
    public void stop(int index){
        loopOnceMusicArrayList.get(index).stop();
    }

    public int getCurrentMusicIndex() {
        return currentMusic;
    }
    public LoopOnceMusic getMusicPlaying(){
        return loopOnceMusicArrayList.get(currentMusic);
    }

    @Override
    public String toString() {
        String str = "";
        int id = 0 ;
        for(LoopOnceMusic music : loopOnceMusicArrayList){
            id ++;
            str += id + music.toString() + "\n";
        }
        return str;
    }
}
