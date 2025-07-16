package src;

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Util {


    public static String[] GenerateEmptyStringArray(int in) {
        String[] result = new String[in];
        for (int i = 0; i < in; i++) {
            result[i] = "N/A";
        }
        return result;
    }
    public static ArrayList<Champion> DupToMinSize(ArrayList<Champion> in, int min) {
        ArrayList<Champion> result = new ArrayList<Champion>();
        for (int i = 0; i < in.size(); i++) {
            result.add(in.get(i));
        }
        while (result.size() < min) {
            for (int i = 0; i < in.size(); i++) {
                result.add(in.get(i).Clone());
            } 
        }
        return result;
    }
    public static ArrayList<String> DupStrToMinSize(ArrayList<String> in, int min) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < in.size(); i++) {
            result.add(in.get(i));
        }
        while (result.size() < min) {
            for (int i = 0; i < in.size(); i++) {
                result.add(in.get(i));
            } 
        }
        return result;
    }
    public static ArrayList<String> PurgeDupStr(ArrayList<String> in) {
        ArrayList<String> unique = new ArrayList<String>();
        for (int i = 0; i < in.size(); i++) {
            boolean isUnique = true;
            for (int j = 0; j < unique.size(); j++) {
                if (in.get(i).compareTo(unique.get(j)) == 0) {
                    isUnique = false;
                }
            }

            if (isUnique) {
                unique.add(in.get(i));
            }
        }
        return unique;
    }
    public static ArrayList<Champion> PurgeDupChamp(ArrayList<Champion> in) {
        ArrayList<Champion> unique = new ArrayList<Champion>();
        for (int i = 0; i < in.size(); i++) {
            boolean isUnique = true;
            for (int j = 0; j < unique.size(); j++) {
                if (in.get(i).name.compareTo(unique.get(i).name) == 0) {
                    isUnique = false;
                }
            }

            if (isUnique) {
                unique.add(in.get(i));
            }
        }
        return unique;
    }


    public static char[] ReadFile(String in) {
        try {
			char[] output;
			File ListFile = new File(in);

			output = new char[(int)ListFile.length()];
			FileReader ListReader = new FileReader(ListFile);
			ListReader.read(output);
			ListReader.close();
            return output;
		} catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static ArrayList<String> CharArr_to_StrList(char[] in) {
		String line = "";
        char newline = (char)10;
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < in.length; i++) {
			if (in[i] == newline) {
				result.add(line);
				line = new String();
			} else {
				line += in[i];
			}
		}
        return result;
    }


    public static String[] StrList_to_StrArr(ArrayList<String> in) {
        String[] arr = new String[in.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = in.get(i);
        }
        return arr;
    }
}