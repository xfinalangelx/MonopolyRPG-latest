package MonopolyRPG;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class LoopOnceMusic {
    private String musicTitle, fileName;
    private Media hit ;
    private MediaPlayer mediaPlayer ;


    public LoopOnceMusic(String musicTitle, String fileName) {
        this.musicTitle = musicTitle;
        this.fileName = fileName;
        hit = new Media(new File(fileName).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);

    }

    public void play(){
        mediaPlayer.play();;
    }

    public void stop(){
        mediaPlayer.stop();
    }

    @Override
    public String toString(){
        return "Music : " + musicTitle ;
    }
}
