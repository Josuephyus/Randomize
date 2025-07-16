package src;

import java.awt.Graphics;
import javax.swing.JPanel;

public class Display extends JPanel {

    private ArrayList<Entry> Entries = new ArrayList<>();
    private ArrayList<Entry> RemovedEntries = new ArrayList<>();
    
    public Display() {
        super();

        String[] entries = Reader.FileToStringArray("open.txt");
        Entries = Entry.EntryListFromStrings(entries);
    }  

    
    @Override public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}