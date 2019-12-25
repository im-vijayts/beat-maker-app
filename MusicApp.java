// import sun.audio.*;

import java.util.List;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class MusicApp{

    int dimensionx = 940;
    int dimensiony = 540;

    final String first_row_names[] = {"CLAP", "SNAP", "RIM", "SNARE", "SNARE 2", "KICK", "KICK 2", "808 1", "808 2", "808 3", "808 4", "808 5"};
    final String second_row_names[] = {"HAT", "HAT", "CLAVES", "TRIANGLE", "SHAKER", "CRASH", "GUN", "CONGA", "CONGA 2", "STICK", "TOM", "TOM 2"};
    final String third_row_names[] = {"KEYS 1", "KEYS 2", "KEYS 3", "KEYS 4", "KEYS 5", "KEYS 6", "GUITAR 1", "GUITAR 2", "GUITAR 3", "GUITAR 4", "GUITAR 5"};
    final String fourth_row_names[] = {"JYEA", "UGH", "HEY", "YEAUH", "UGH", "AWEYEAH", "HA", "HOLDUP", "HUH", "KHALED"};


    static ArrayList<JButton> first_row_buttons = new ArrayList<JButton>();
    static ArrayList<JButton> second_row_buttons = new ArrayList<JButton>();
    static ArrayList<JButton> third_row_buttons = new ArrayList<JButton>();
    static ArrayList<JButton> fourth_row_buttons = new ArrayList<JButton>();
    private static row_no row_name;

    MusicApp() {
        JFrame root = new JFrame("Music Producer");
        root.setSize(dimensionx, dimensiony);
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel first_row = new JPanel();
        JPanel second_row = new JPanel();
        JPanel third_row = new JPanel();
        JPanel fourth_row = new JPanel();

        for(int i=1; i<=4; i++){
            createRows(i);
            first_row.setLayout(new GridLayout(0, 12, 0, 0));
            second_row.setLayout(new GridLayout(0, 12, 0, 0));
            third_row.setLayout(new GridLayout(0, 12, 0, 0));
            fourth_row.setLayout(new GridLayout(0, 12, 0, 0));
        }

        root.setVisible(true);
    }

    public void createRows(int row_no) {
        
        String temp[] = new String();
        ArrayList<JButton> temp_btns = new JButton();

        switch (row_no){
            case 1:
                temp = first_row_names;
                temp_btns = first_row_buttons;
            case 2:
                temp = second_row_names;
                temp_btns = second_row_buttons;
            case 3:
                temp = third_row_names;
                temp_btns = third_row_buttons;
            case 4:
                temp = fourth_row_names;
                temp_btns = fourth_row_buttons;
        }
        for (String btn_name: temp){
            temp_btns.add(new JButton(btn_name));
        }
    }

    public void playSound(int i, String btn_name) {
        
    }

    public static void main(String[] args) {
        new MusicApp();
    }
}