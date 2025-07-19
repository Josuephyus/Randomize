package src;

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    public static String[] FileToStringArray(String in) {
        // Read Lines as ArrayList<String>
		ArrayList<String> strArr = new ArrayList<String>();
        File file = new File(in);
        try {
            FileReader reader = new FileReader(file);
            BufferedReader buffr = new BufferedReader(reader);
            for (String line = buffr.readLine(); line != null; line = buffr.readLine()){
                if (line.trim().length() > 0) {
                    strArr.add(line);
                }
            }
            reader.close();
        } catch (Exception e) {  }

        // Convert to String[]
        String[] result = new String[strArr.size()];
        strArr.toArray(result);
        
        return result;
    }


    public static void WriteToFile(String in, String[] lines) {
        File file = new File(in);

        try {
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < lines.length; i++) {
                writer.write(lines[i] + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}