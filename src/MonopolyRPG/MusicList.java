package MonopolyRPG;

import MonopolyRPG.Music;

import java.util.ArrayList;

public class MusicList {
    private ArrayList<Music> musicArrayList;
    //uses this variable to record the index of music is currently playing
    private int currentMusic;

    public MusicList(){
        this.musicArrayList = new ArrayList<>();
        currentMusic = 0;

        //adds the predefined music list
        addMusic(new Music("retro1","FinalRetro1.mp3"));
        addMusic(new Music("retro2","retro2.mp3"));

    }

    public void addMusic(Music music){
        musicArrayList.add(music);
    }

    //this plays the music of currentMusic index
    public void play(){
        musicArrayList.get(currentMusic).play();
    }

    //this can selectively play music of any index
    public void play(int index){
        //stop the music playing first
        stop();

        //then only plays the music
        currentMusic = index;
        musicArrayList.get(index).play();
    }

    public void stop(){
        musicArrayList.get(currentMusic).stop();
    }



    public int getCurrentMusicIndex() {
        return currentMusic;
    }
    public Music getMusicPlaying(){
        return musicArrayList.get(currentMusic);
    }

    @Override
    public String toString() {
        String str = "";
        int id = 0 ;
        for(Music music : musicArrayList){
            id ++;
            str += id + music.toString() + "\n";
        }
        return str;
    }
}