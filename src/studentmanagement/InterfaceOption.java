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

    public InterfaceOption clone() {
        return new InterfaceOption(name, runnable);
    }

    public InterfaceOption setName(String name) {
        this.name = name;
        return this;
    }

    public void run() {
        runnable.run();
    }
}
