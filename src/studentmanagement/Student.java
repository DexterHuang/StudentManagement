package studentmanagement;

import java.util.ArrayList;
import java.util.List;

public class Student {

    int studentID;

    String name;

    List<Module> modules = new ArrayList<Module>();

    public Student(int studentID, String name) {
        this.studentID = studentID;
        this.name = name;
    }

    @Override
    public String toString() {
        return studentID + " " + name;
    }

    public boolean tookModule(Module module) {
        return modules.contains(module);
    }

    void takeModule(Module module) {
        modules.add(module);
    }

}
