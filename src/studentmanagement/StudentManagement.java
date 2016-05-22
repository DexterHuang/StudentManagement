package studentmanagement;

import java.util.ArrayList;
import java.util.List;

public class StudentManagement {

    public static School school = new School("Bogus");
    public static InterfaceOption addStudentOption;
    public static InterfaceOption removeStudentOption;
    public static InterfaceOption addModuleOption;
    public static InterfaceOption removeModuleOption;
    public static InterfaceOption displayModuleListOption;
    public static InterfaceOption displayStudentOption;
    public static InterfaceOption registerModuleOption;
    public static InterfaceOption displayModulesTakenByStudent;
    public static InterfaceOption openMainMenu;

    public static void main(String[] args) {
        init();
        openMainMenu();
    }

    public static void init() {
        addStudentOption = new InterfaceOption("Add a student", new Runnable() {
            @Override
            public void run() {
                addStudent();
            }
        });
        removeStudentOption = new InterfaceOption("Remove a student", new Runnable() {
            @Override
            public void run() {
                removeStudent();
            }
        });
        addModuleOption = new InterfaceOption("Add a module", new Runnable() {
            @Override
            public void run() {
                addModule();
            }
        });
        removeModuleOption = new InterfaceOption("Remove a module", new Runnable() {
            @Override
            public void run() {
                removeModule();
            }
        });
        displayModuleListOption = new InterfaceOption("Display module list", new Runnable() {
            @Override
            public void run() {
                displayModuleList();
            }
        });
        displayStudentOption = new InterfaceOption("Display student list", new Runnable() {
            @Override
            public void run() {
                displayStudentList();
            }
        });
        registerModuleOption = new InterfaceOption("Register Module", new Runnable() {
            @Override
            public void run() {
                registerModuleForStudent();
            }
        });
        displayModulesTakenByStudent = new InterfaceOption("Display module taken by student", new Runnable() {
            @Override
            public void run() {
                displayStudentModules();
            }
        });

        openMainMenu = new InterfaceOption("go to main menu", new Runnable() {
            @Override
            public void run() {
                openMainMenu();
            }
        });
    }

    public static void openMainMenu() {
        Interface inter = new Interface();

        inter.addOption(addStudentOption);
        inter.addOption(removeStudentOption);
        inter.addOption(addModuleOption);
        inter.addOption(removeModuleOption);
        inter.addOption(displayModuleListOption);
        inter.addOption(displayStudentOption);
        inter.addOption(registerModuleOption);
        inter.addOption(displayModulesTakenByStudent);
        inter.showAndGetOption().run();
    }

    public static void addStudent() {
        Student student = new Student(Debug.getInt("Please enter student ID."), Debug.getString("Please enter student name."));
        for (Student s : school.students) {
            if (s.studentID == student.studentID) {
                Debug.LogError("Duplicate student ID.");
                retryOrReturnMainMenu(addStudentOption);
                return;
            }
        }
        school.addStudent(student);
        Debug.Log(student.toString() + " Has been added.");
        openMainMenu();
    }

    public static void removeStudent() {
        Student student = Debug.getFromList(school.students, "Please select student");
        school.removeStudent(student.studentID);
        Debug.Log(student.toString() + " has been successfully removed.");
        pauseAndGoMainMenu();
    }

    public static void addModule() {
        Module module = new Module(Debug.getInt("Please enter module ID"), Debug.getString("Please eneter module name"), Debug.getInt("Please eneter max population limit"), Debug.getInt("Please eneter semestrer number."));
        for (Module m : school.modules) {
            if (m.ModuleID == module.ModuleID) {
                Debug.LogError("Duplicate module ID");
                retryOrReturnMainMenu(addModuleOption);
                return;
            }
        }
        school.addModule(module);
        Debug.Log("Added " + module.toString() + " sucessfully.");
        pauseAndGoMainMenu();
    }

    public static void removeModule() {
        Module module = Debug.getFromList(school.modules, "Please select module");
        for (Student s : school.students) {
            if (s.tookModule(module)) {
                Debug.LogError(s.toString() + " is still taking this module - remove failed");
                retryOrReturnMainMenu(removeModuleOption);
                return;
            }
        }
        Debug.Log("Remove" + module.toString() + " sucessfully.");
        pauseAndGoMainMenu();
    }

    public static void displayModuleList() {
        List<String> l = new ArrayList<String>();
        if (school.modules.isEmpty()) {
            Debug.LogError("Module list is empty.");
            pauseAndGoMainMenu();
            return;
        }
        for (Module m : school.modules) {
            l.add(m.toString());
        }
        Debug.Log("Module list:");
        Debug.Log(Debug.generateBoxString(l));
        pauseAndGoMainMenu();
    }

    public static void displayStudentList() {
        List<String> l = new ArrayList<String>();
        if (school.students.isEmpty()) {
            Debug.LogError("Student list is empty.");
            pauseAndGoMainMenu();
            return;
        }
        for (Student s : school.students) {
            l.add(s.toString());
        }
        Debug.Log("Student list:");
        Debug.Log(Debug.generateBoxString(l));
        pauseAndGoMainMenu();
    }

    public static void displayStudentModules() {
        List<String> l = new ArrayList<String>();
        Student student = Debug.getFromList(school.students, "Please select studnet");
        if (student.modules.isEmpty()) {
            Debug.LogError("module list is empty.");
            pauseAndGoMainMenu();
            return;
        }
        for (Module m : student.modules) {
            l.add(m.toString());
        }
        Debug.Log("Module taken by " + student.toString() + ":");
        Debug.Log(Debug.generateBoxString(l));
        pauseAndGoMainMenu();
    }

    public static void registerModuleForStudent() {
        Student student = Debug.getFromList(school.students, "Please select studnet");
        Module module = Debug.getFromList(school.modules, "Please select module");
        if (module.isFull() == false) {
            if (student.modules.contains(module) == false) {
                student.takeModule(module);
                Debug.Log(student.toString() + " registered for module " + module + " successfully.");
                pauseAndGoMainMenu();
                return;
            } else {
                Debug.LogError(student.toString() + " is already taking this module.");
            }
        } else {
            Debug.LogError("Module is full, ");
        }
        retryOrReturnMainMenu(registerModuleOption);
    }

    public static void retryOrReturnMainMenu(InterfaceOption option) {
        Interface inter = new Interface();
        inter.addOption(option.clone().setName("Try again"));
        inter.addOption(openMainMenu);
        inter.showAndGetOption().run();
    }

    public static void pauseAndGoMainMenu() {
        Debug.getString("Enter any key to go to main menu.");
        openMainMenu();
    }
}
