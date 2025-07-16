package src;

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    public static String[] FileToStringArray(String in) {
        // Convert to Char[] Array
        char[] charArr = null;
        File file = new File(in);
        try {
			charArr = new char[(int)file.length()];
            FileReader reader = new FileReader(file);
            reader.read(charArr);
            reader.close();
        } catch (Exception e) {  }


        // Convert to ArrayList<String>
        String line = "";
        char newline = (char)10;
		ArrayList<String> strArr = new ArrayList<String>();
		for (int i = 0; i < charArr.length; i++) {
			if (charArr[i] == newline) {
				strArr.add(line);
				line = new String();
			} else {
				line += charArr[i];
			}
		}


        // Convert to String[]
        String[] result = new String[strArr.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = strArr.get(i);
        }
        return result;
    }


    public static void WriteToFile(String in, String[] lines) {
        File file = new File(in);

        try {
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < lines.length; i++) {
                writer.write(lines + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}