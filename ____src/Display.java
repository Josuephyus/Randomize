package src;

import java.util.ArrayList;
import java.util.Collections;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Display extends JPanel {

    private float y = 0;
    private float momentum = 0;
    public boolean moving = false;

    private ArrayList<Entry> Entries;
    private ArrayList<Entry> RemovedEntries;
    public Display() {
        super();
        this.openList();
        this.setSize(1);
    }


    private static final int STATE_1_HEIGHT = 50;
    private static final int STATE_2_HEIGHT = 80;
    private static final int STATE_3_HEIGHT = 110;
    private static final int STATE_4_HEIGHT = 140;
    private int size;
    private int height;
    private Font font_title;
    private Font font_additional;
    public void setSize(int in) {
        this.size = in;
        switch (in) {
            case 1:
                height = STATE_1_HEIGHT;
                font_title = Schemes.font_title1;
                break;
            case 2:
                height = STATE_2_HEIGHT;
                font_title = Schemes.font_title1;
                font_additional = Schemes.font_add1;
                break;
            case 3:
                height = STATE_3_HEIGHT;
                font_title = Schemes.font_title2;
                font_additional = Schemes.font_add2;
                break;
            case 4:
                height = STATE_4_HEIGHT;
                font_title = Schemes.font_title3;
                font_additional = Schemes.font_add3;
                break;
        }
    }

    
    @Override public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Entries == null) {
            paintEmpty(g);
            return;
        }
        if (Entries.size() == 0) {
            paintEmpty(g);
            return;
        }

        // Semi Static Values
        int w = this.getWidth();

        // Get amount to display
        int h = this.getHeight();
        int amount = (h / height) + 4;

        // Then draw them (in a disgusting way)
        int half = (amount / 2);
        int yRound = (int)Math.round(y - 0.5f);
        float yValue = y - (float)yRound;
        if (Float.isNaN(yValue)) {
            yRound = (int)Math.round(y - 0.5f);
            yValue = y - (float)yRound;
            y = 0;
        }
        Graphics2D g2 = (Graphics2D)g;

        for (int i = 0; i < amount; i++) {
            int index = yRound + i - half;

            while (index < 0) { index += Entries.size(); }
            index %= Entries.size();
            Entry entry = Entries.get(index);

            int start_y = (int)((-half + i - yValue) * height) + (h / 2);
            // System.out.print(i + ", " + index + ", " + half);
            // System.out.println(", " + yValue + ", " + (h / 2) + ", " + start_y);
            g.setColor(Schemes.color_background);
            g.fillRect(0, start_y, w, height);

            g.drawImage(ImageLoader.get(entry.image), 0, start_y, height, height, null);
            if (height == STATE_1_HEIGHT) {
                g2.setColor(Schemes.color_title);
                g2.setFont(font_title);
                g2.drawString(entry.title, height + 5, start_y + (height - 10));
            } else {
                if (entry.additional == null) {
                    g2.setColor(Schemes.color_title);
                    g2.setFont(font_title);
                    g2.drawString(entry.title, height + 5, start_y + (height * 11 / 16));
                } else {
                    g2.setColor(Schemes.color_title);
                    g2.setFont(font_title);
                    g2.drawString(entry.title, height + 5, start_y + (height / 2));
                    g2.setColor(Schemes.color_additional);
                    g2.setFont(font_additional);
                    g2.drawString(entry.additional, height + 5, start_y + (height - 14));
                }
                
            }

            if (i == half) {
                if (moving) g2.setColor(Schemes.color_movingBorder);
                else g2.setColor(Schemes.color_selectedBorder);
                g2.setStroke(new BasicStroke(2));
                g2.drawRect(2, start_y + 2, this.getWidth() - 2, height - 4);
            }
        }
        
    }
    private void paintEmpty(Graphics g) {
        g.setColor(Schemes.color_background);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        Graphics2D g2 = (Graphics2D)g;
        g2.setFont(Schemes.font_err);
        g2.setColor(Schemes.color_title);
        g2.drawString("Empty List", 10, this.getHeight() / 2);
    }

    


    // Utils
    public void reduceMomentum(float delta) {
        y += momentum * 2 / height;
        while (y < 0) { y += Entries.size(); }
        y %= Entries.size();

        float reduce = delta * 5;
        momentum -= reduce;

        if (momentum < 0) momentum = 0;
        if (momentum == 0) { moving = false; }
        else { moving = true; }
    }
    public void addMomentum(float in) { momentum += in; }
    public void openList() {
        String[] entries = FileHandler.FileToStringArray("texts/open.txt");
        Entries = Entry.ReadFromArray(entries);
        RemovedEntries = new ArrayList<Entry>();
    }
    public void saveList(String in) {
        String[] lines = new String[Entries.size()];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = Entries.get(i).toString();
        }
        FileHandler.WriteToFile(in, lines);
    }
    private byte lastSearchByte = 0;
    private int searchCounterTotal = 0;
    public void search(byte in) {
        int searchCounter = 0;
        int first = Integer.MAX_VALUE;
        if (in == lastSearchByte) {
            searchCounterTotal++;
        } else {
            searchCounterTotal = 0;
            lastSearchByte = in;
        }
        for (int i = 0; i < Entries.size(); i++) {
            Entry entry = Entries.get(i);
            if (entry.title.getBytes()[0] == in) {
                if (i < first) {
                    first = i;
                }
                if (searchCounter == searchCounterTotal) {
                    y = i;
                    return;
                }
                searchCounter++;
            }
        }
        if (first != Integer.MAX_VALUE) {
            y = first;
            searchCounterTotal = 0;
        }
    }
    public void move(int in) { y += (float)in; }
    public void undo() {
        if (RemovedEntries.size() == 0) return;

        Entry entry = RemovedEntries.remove(RemovedEntries.size() - 1);
        int index = entry.prevIndex;
        if (index > Entries.size()) index = Entries.size();
        Entries.add(index, entry);
    }
    public void removeSelected() {
        if (Entries.size() == 0) return;

        int yRound = (int)y;
        Entry entry = Entries.remove(yRound);
        entry.prevIndex = yRound;
        RemovedEntries.add(entry);
    }
    public void shuffle() {
		Collections.shuffle(Entries);
    }
    public void randomize() {
        int count = (this.getHeight() / height) + 4;
        count += getRandom(0, Entries.size());

        addMomentum(count);
    }
    public int getRandom(int min, int max) {
        return (int)(Math.random() * (max - min + 1)) + min;
    }
}