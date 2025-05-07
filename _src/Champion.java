import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.util.ArrayList;

public class Champion extends JPanel {

    public static int width = 850;
    public static int height = 80;
    public int x = 0;
    public int y = -200;
    public boolean selected = false;
    public boolean affected = false;
    public static boolean moving = false;

    JLabel LABEL_Name;
    JLabel LABEL_Title;
    BufferedImage IMAGE;

    public String name;
    public String title;


    public Champion(String name, String title){
        super();
        this.name = name;
        this.title = title;

        this.setLayout(null);
        this.setBackground(Schemes.grey3);
        this.setForeground(Schemes.gold);
        this.setBounds(x, y, width, height);

        LABEL_Name = new JLabel(name.toUpperCase());
        LABEL_Name.setBounds(90, 5, width, 50);
        LABEL_Name.setFont(Schemes.titleFont_1);
        LABEL_Name.setForeground(Schemes.gold);
        this.add(LABEL_Name);

        LABEL_Title = new JLabel(title);
        LABEL_Title.setBounds(90, 50, width, 30);
        LABEL_Title.setFont(Schemes.descFont_1);
        LABEL_Title.setForeground(Schemes.gold2);
        this.add(LABEL_Title);

        try {
            IMAGE = Champion.LoadImage("Icons/" + FixNameForIcon(name));
        } catch (IOException e) {
            System.out.println("Failed: " + FixNameForIcon(name));

            try {
                IMAGE = Champion.LoadImage("Icons/None.png");
            } catch (IOException e2) { System.out.println("  Failed: None.png");}
        }
    }

    public void Rescale() {
        this.remove(LABEL_Title);
        this.add(LABEL_Title);

        int x = height + 5;
        if (height == 60) {
            LABEL_Name.setBounds(x, 0, width, 60);
            LABEL_Name.setFont(Schemes.titleFont_1);
            this.remove(LABEL_Title);
        } else if (height == 90) {
            LABEL_Name.setBounds(x, 0, width, 60);
            LABEL_Name.setFont(Schemes.titleFont_1);
            LABEL_Title.setBounds(x, 60, width, 30);
            LABEL_Title.setFont(Schemes.descFont_1);
        } else if (height == 120) {
            LABEL_Name.setBounds(x, 0, width, 80);
            LABEL_Name.setFont(Schemes.titleFont_2);
            LABEL_Title.setBounds(x, 80, width, 40);
            LABEL_Title.setFont(Schemes.descFont_2);
        } else {
            LABEL_Name.setBounds(x, 0, width, 100);
            LABEL_Name.setFont(Schemes.titleFont_3);
            LABEL_Title.setBounds(x, 100, width, 50);
            LABEL_Title.setFont(Schemes.descFont_3);
        }

        try {
            IMAGE = Champion.LoadImage("Icons/" + FixNameForIcon(name));
        } catch (IOException e) {
            System.out.println("Failed: " + FixNameForIcon(name));

            try {
                IMAGE = Champion.LoadImage("Icons/None.png");
            } catch (IOException e2) { System.out.println("  Failed: None.png");}
        }
    }


    public static ArrayList<Champion> ConvertStringArray(String[] in) {
		ArrayList<Champion> Champions = new ArrayList<Champion>();
        
		for (int i = 0; i < in.length; i++) {
            Champions.add(ConvertString(in[i]));
		}

        return Champions;
    }

    public static Champion ConvertString(String in) {
        String name = in;
        String title = "";

        if (in.split(", ").length > 1) {
            name = in.split(", ")[0];
            title = in.split(", ")[1];
        }        
        return new Champion(name, title);
    }


    public void paintComponent(Graphics g) {
        this.update();
        super.paintComponent(g);

        ((Graphics2D)g).drawImage(IMAGE, 0, 0, height, height, null);

        if (selected) {
            if (moving)
                g.setColor(Schemes.blue2);
            else
                g.setColor(Schemes.gold);

            ((Graphics2D)g).setStroke(new BasicStroke(2));
            g.drawRect(2, 2, this.getWidth() - 18, height - 4);
        }
    }


    public void update() {
        this.setBounds(x, y, width, height);
    }




    public static String FixNameForIcon(String in) {
        String name = in;
        // Dr.Mundo
        name = name.replace(".","");
        
        // Asol
        name = name.replace(" ", "");
        
        // BelVeth, ChoGath, KSante, KaiSa, KhaZix, KogMaw,
        // RekSai, VelKoz
        name = name.replace("'", "");
        
        return name + ".png";
    }


    public static BufferedImage LoadImage(String in) throws IOException {
        File imageFile = new File(in);
        BufferedImage imageBuff = ImageIO.read(imageFile);
        
        return imageBuff;
    }
}
