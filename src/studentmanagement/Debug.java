//This project is Developed by Huang Ching
package studentmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Huang
 */
public class Debug {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void Log(String str) {
        System.out.println(str);
    }

    public static void LogError(String str) {
        System.out.println(ANSI_RED + "[ERROR]: " + ANSI_RESET + str);
    }

    public static <T> T getFromList(List<T> list, String msg) {
        List<String> sl = new ArrayList<String>();
        int index = 0;
        for (T o : list) {
            sl.add(ANSI_GREEN + "[" + index + "]" + ANSI_RESET + " - " + o.toString());
            index++;
        }
        Log(msg);
        Log("Please choose an object from the list below(Enter the " + ANSI_GREEN + "[Index]" + ANSI_RESET + ")");
        Log(generateBoxString(sl));
        Log("");
        int i = getInt("Please enter the Index of the object you wish to select or type '-1' to return to main menu");
        if (i < 0) {
            StudentManagement.openMainMenu();
            return null;
        }
        try {
            return list.get(i);
        } catch (Exception e) {
            Debug.Log("Failed to select object, please try again : " + e.getMessage());
            return getFromList(list, msg);
        }
    }

    public static String generateBoxString(List<String> list) {
        int length = getLongest(list);
        String topString = "+-";
        for (int i = 1; i <= length; i++) {
            topString += "-";
        }
        topString += "-+";

        String str = topString + "\n";
        for (String s : list) {
            str += "| " + s + getRepeating(length - getNoColor(s).length(), "-") + " |\n";
        }
        str += topString + "\n";
        return str;
    }

    private static String getRepeating(int amount, String str) {
        String s = "";
        for (int i = 1; i <= amount; i++) {
            s += str;
        }
        return s;
    }

    private static int getLongest(List<?> list) {
        int i = 0;
        for (Object o : list) {
            int length = getNoColor(o.toString()).length();
            if (length > i) {
                i = length;
            }
        }
        return i;
    }

    private static String getNoColor(String str) {
        return str.replaceAll("\u001B\\[[;\\d]*m", "");
    }

    public static int getInt(String msg) {
        Scanner sc = new Scanner(System.in);
        System.out.println(msg);
        try {
            return sc.nextInt();
        } catch (Exception e) {
            Debug.Log("Failed to get Int, please try again : " + e.getMessage());
            return getInt(msg);
        }
    }

    static String getString(String msg) {
        Scanner sc = new Scanner(System.in);
        System.out.println(msg);
        try {
            return sc.next();
        } catch (Exception e) {
            Debug.Log("Failed to get Int, please try again : " + e.getMessage());
            return getString(msg);
        }
    }
}
