import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.io.IOException;

import java.net.URL;
import java.net.URI;

public class Champion {

    JPanel display;

    public static final Integer width = 400, height = 120;

    public Champion(String name){
        this(name, "");
    }
    public Champion(String name, String title){
        display = new JPanel();
        display.setLayout(null);
        display.setBackground(Schemes.grey3);
        display.add(new JLabel(name.toUpperCase()){{
            setBounds(120,5,280,40);
            setFont(Schemes.titleFont);
            setForeground(Schemes.gold);
        }});
        display.add(new JLabel(){{
            try {
                setIcon(new ImageIcon(ImageIO.read(new URI("https://ddragon.leagueoflegends.com/cdn/14.13.1/img/champion/" + name + ".png").toURL())));
            } catch (IOException e){}
            catch (Exception e){}
            setBounds(0,0,120,120);
            }});
    }
}
