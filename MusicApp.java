import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.util.ArrayList;

// For playing the audio
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 

class MusicApp implements ActionListener {

    int dimensionx = 940;
    int dimensiony = 540;

    int r = 150, g = 100, b = 255;

    final String first_row_names[] = { "CLAP", "SNAP", "SNARE", "SNARE2", "KICK", "KICK2", "8082", "8083", "8084", "8085", "8086" };
    final String second_row_names[] = { "HAT", "HAT", "CLAVES", "TRIANGLE", "SHAKER", "CONGA", "TOM", "TOM2" };
    final String third_row_names[] = { "KEYS3", "KEYS4", "KEYS5","GUITAR1", "GUITAR2", "GUITAR3", "GUITAR4" };
    final String fourth_row_names[] = { "JYEA", "UGH", "HEY", "YEAUH", "UGH", "HA", "HOLDUP" };

    JFrame root = new JFrame("Music Producer"); 
    
    ArrayList<JButton> btns = new ArrayList<JButton>();

    MusicApp() {
        root.setSize(dimensionx, dimensiony);
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

        JScrollPane pane = new JScrollPane(wrapper);

        JPanel buttons_section = new JPanel();
        
        buttons_section.setLayout(new GridLayout(5, 10));
        buttons_section.setBackground(new Color(200, 200, 200));

        createButtons(first_row_names);
        createButtons(second_row_names);
        createButtons(third_row_names);
        createButtons(fourth_row_names);

        for(JButton button: btns){
            button.setPreferredSize(new Dimension(90, 90));
            button.setBackground(new Color(r, g, b));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.addActionListener(this);
            buttons_section.add(button);
            r -= 2;
            g -= 2;
        }

        wrapper.add(buttons_section);

        root.add(pane);

        root.setVisible(true);
    }

    public void createButtons(String[] row_names){
        for(String temp: row_names){
            btns.add(new JButton(temp));
        }
    }

    public void actionPerformed(ActionEvent e){
        for(JButton button: btns){
            if (e.getSource() == button){
                try {
                    String sound_path = "./sounds/" + button.getText().toLowerCase() + ".wav";
                    AudioPlayer player = new AudioPlayer(sound_path);
                    player.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new MusicApp();
    }
}

// When each sound is to be played a new thread is created to play the partivular sound
class AudioPlayer extends Thread {
    Clip clip;

    AudioInputStream audioInputStream;
    String file_path;

    Boolean status;

    // constructor to initialize streams and clip
    public AudioPlayer(String f_path) throws UnsupportedAudioFileException, IOException, LineUnavailableException { 
        file_path = f_path;
        
        // create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(new File(file_path).getAbsoluteFile()); 

        // create clip reference
        clip = AudioSystem.getClip();
        
        // open audioInputStream to the clip
        clip.open(audioInputStream);
    }

    // Method to play the audio 
    public void play() throws IOException {
        //start the clip 
        clip.start();
        status = true;
    }

    // Method to stop the audio
    public void stopAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        clip.stop();
        clip.close();
    }

    public void run() {
        try {
            // play the sound
            play();
            while(status){
                // sleep for 5 seconds in order for the audio to be completely played
                // because the audio will play only as long as the thread is alive and 
                // the thread will be alive only as long as the obljects are created and destroyed
                // which is very much less than 5 seconds
                Thread.sleep(5_000);
                status = false;
            }
        }
        catch (Exception ex) { 
            System.out.println("Error with playing sound."); 
            ex.printStackTrace();
        }
        finally {
            try {
                stopAudio();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
            status = false;
        }
    }
}