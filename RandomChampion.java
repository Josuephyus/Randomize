import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Color;

public class RandomChampion{

	static Champion[] championValues;

	public static void main(String[] args){
		String[] champions = loadChampions();
		try {

			String web = "https://ddragon.leagueoflegends.com/cdn/14.13.1/img/champion/";
			String form = ".png";

			for (int i = 0; i < champions.length; i++){

				String actualName = champions[i]
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
					.replace("Wukong","MonkeyKing"); //"wu Kong"

				File file = new File("icon\\" + actualName + form);
				file.createNewFile();
				System.out.println("Writing " + actualName + ".png");
				if (ImageIO.write(ImageIO.read(new URI(web + actualName + form).toURL()), form, file)){
					System.out.println("Completed " + actualName);
				}
			}
		} catch (IOException e){System.out.println("IOEX"); e.printStackTrace();}
		catch (URISyntaxException e){System.out.println("URIS"); e.printStackTrace();}
		

		/*
		JFrame win = new JFrame();
		win.setUndecorated(true);
		win.setDefaultCloseOperation(3);
		win.setLayout(null);
		JPanel scr = new JPanel();
		scr.setBounds(0,0,600,400);
		scr.setBackground(Schemes.grey3);
		scr.setLayout(null);
		win.add(scr);

		championValues = new Champion[champions.length];
		for (int i = 0; i < champions.length; i++){
			System.out.print(i + ", ");
			championValues[i] = new Champion(champions[i]);
			if (i < 4){
				championValues[i].display.setBounds(0, 20 + i * 120, Champion.width, Champion.height);
			} else if (i == champions.length - 1){
				championValues[i].display.setBounds(0, -100, Champion.width, Champion.height);
			}else {
				championValues[i].display.setBounds(-999, -999, Champion.width, Champion.height);
			}
			scr.add(championValues[i].display);
		}

		JButton start = new JButton("Start");
		start.setBounds(425,125,125,35);
		start.setBackground(Schemes.grey1);
		start.setFont(Schemes.descFont);
		start.setForeground(Schemes.grey2);
		start.setFocusable(false);
		scr.add(start);

		win.pack();
		win.setSize(600, 400);
		win.setLocationRelativeTo(null);
		win.setVisible(true);*/
	}

	public static String[] loadChampions(){
		File ChampionListTxt = new File("ChampionList.txt");
		try {
			FileReader ChampionListReader = new FileReader(ChampionListTxt);
			char[] characters = new char[2000];
			ChampionListReader.read(characters);
			ChampionListReader.close();


			String bigString = "";
			for (int i = 0; i < characters.length; i++){
				if (characters[i] != 0){
					bigString += ("" + characters[i]);
				} else {
					i = characters.length;
				}
			}

			String[] smallStrings = bigString.split(((char)13 + "" + (char)10));

			return smallStrings;

		} catch (IOException e){}
		return new String[]{};
	}
}