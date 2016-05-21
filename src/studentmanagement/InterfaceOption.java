//This project is Developed by Huang Ching
package studentmanagement;

public class InterfaceOption {

    String name;

    Runnable runnable;

    public InterfaceOption(String name, Runnable runnable) {
        this.name = name;
        this.runnable = runnable;
    }

    public String toString() {
        return name;
    }
}
