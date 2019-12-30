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

    final String first_row_names[] = { "CLAP", "SNAP", "RIM", "SNARE", "SNARE 2", "KICK", "KICK 2", "808 1", "808 2",
            "808 3", "808 4", "808 5" };
    final String second_row_names[] = { "HAT", "HAT", "CLAVES", "TRIANGLE", "SHAKER", "CRASH", "GUN", "CONGA",
            "CONGA 2", "STICK", "TOM", "TOM 2" };
    final String third_row_names[] = { "KEYS 1", "KEYS 2", "KEYS 3", "KEYS 4", "KEYS 5", "KEYS 6", "GUITAR 1",
            "GUITAR 2", "GUITAR 3", "GUITAR 4", "GUITAR 5" };
    final String fourth_row_names[] = { "JYEA", "UGH", "HEY", "YEAUH", "UGH", "AWEYEAH", "HA", "HOLDUP", "HUH",
            "KHALED" };

    JFrame root = new JFrame("Music Producer");
    
    ArrayList<JButton> btns = new ArrayList<JButton>();
    final JButton record = new JButton("Record");
    final JButton stop  = new JButton("Stop");

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

        JPanel record_section = new JPanel(new GridLayout(1, 2));

        record.setBackground(Color.WHITE);
        record.setForeground(Color.BLACK);
        record.setFocusPainted(false);
        record.addActionListener(this);

        stop.setBackground(Color.WHITE);
        stop.setForeground(Color.RED);
        stop.setFocusPainted(false);
        stop.addActionListener(this);

        record_section.add(record);
        record_section.add(stop);

        wrapper.add(buttons_section);
        wrapper.add(record_section);

        root.add(pane);

        root.setVisible(true);
    }

    public void createButtons(String[] row_names){
        for(String temp: row_names){
            btns.add(new JButton(temp));
        }
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == record) {
            // System.out.println("Record");
            try {
                new AudioPlayer("test.wav");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } 
        else if (e.getSource() == stop){
            System.out.println("Stop");
        }
        else{
            for(JButton button: btns){
                if (e.getSource() == button){
                    System.out.println(button.getText());
                }
            }
        }
    }

    public static void main(String[] args) {
        new MusicApp();
    }
}

class AudioPlayer {
    Clip clip;
    
    AudioInputStream audioInputStream;
    String file_path = "test.wav";

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

        try { 
            play();
            System.out.println("Playing");
            // while(status){}
        }
        catch (Exception ex) { 
            System.out.println("Error with playing sound."); 
            ex.printStackTrace();
        }
        finally {
            stop();
            status = false;
        }
    }

    // Method to play the audio 
    public void play() {
        //start the clip 
        clip.start();
        status = true;
    }

    // Method to stop the audio
    public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        clip.stop();
        clip.close(); 
    }
}