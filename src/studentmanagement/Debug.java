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

    public static void Log(String str) {
        System.out.println(str);
    }

    public static <T> T getFromList(List<T> list) {
        List<String> sl = new ArrayList<String>();
        int index = 0;
        for (T o : list) {
            sl.add(index + " - " + o.toString());
            index++;
        }

        Log("Please choose an object from the list below(Enter the Index)");
        Log(generateBoxString(sl));
        Log("");
        int i = getInt("Please enter the Index of the object you which to select.");
        try {
            return list.get(i);
        } catch (Exception e) {
            Debug.Log("Failed to select object, please try again : " + e.getMessage());
            return getFromList(list);
        }
    }

    public static String generateBoxString(List<String> list) {
        int length = getLongest(list);
        String topString = "+";
        for (int i = 1; i <= length; i++) {
            topString += "-";
        }

        String str = topString + "\n";
        for (String s : list) {
            str += "|" + s + "|\n";
        }
        str += topString + "\n";
        return str;
    }

    private static int getLongest(List<?> list) {
        int i = 0;
        for (Object o : list) {
            if (o.toString().length() > i) {
                i = o.toString().length();
            }
        }
        return i;
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
}
