import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class Champion {

    JPanel display;

    public static final Integer width = 850, height = 120;

    public Champion(String name){
        this(name, "temp");
    }
    public Champion(String name, String title){
        display = new JPanel();
        display.setLayout(null);
        display.setBackground(Schemes.grey3);
        display.setForeground(Schemes.gold2);
        display.add(new JLabel(name.toUpperCase()){{
            setBounds(130,5,850, 80);
            setFont(Schemes.titleFont);
            setForeground(Schemes.gold);
        }});
        display.add(new JLabel(){{
            try {
                setIcon(new ImageIcon(ImageIO.read(new File("icons\\" + name
					.replace(". ","") //Dr.Mundo
					.replace(" ","") //Asol
					.replace("'V","v") //Belveth
					.replace("'G","g") //Chogath
					.replace("K'S","KS") //Ksante
					.replace("i'S", "is") //Kaisa
					.replace("'Z", "z") //Khazix
					.replace("'M","M") //Kogmaw
					.replace("eB", "eb") //Leblanc
					.replace("&Willump","") //Nunu
					.replace("'S","S") //RekSai
					.replace("Glasc","") //Renata
					.replace("l'K","lk") //Velkoz
					.replace("Wukong","MonkeyKing") //"wu Kong"
                    + ".png"
                ))));
            } catch (IOException e){}
            catch (Exception e){}
            setBounds(0,0,120,120);
        }});
        display.add(new JLabel(title){{
            setBounds(130, 85, 600, 30);
            setFont(Schemes.descFont);
        }});
    }
}
