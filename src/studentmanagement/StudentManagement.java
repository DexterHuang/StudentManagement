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
    public static InterfaceOption openMainMenuOption;
    public static InterfaceOption moduleMatterOption;
    public static InterfaceOption studentMatterOption;
    public static InterfaceOption quitProgramOption;
    public static InterfaceOption loadSaveFileOption;

    public static Interface mainMenuInterface;
    public static Interface moduleMatterInterface;
    public static Interface studentMatterInterface;

    //SA
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
        registerModuleOption = new InterfaceOption("Register Module for Student", new Runnable() {
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

        openMainMenuOption = new InterfaceOption("go to main menu", new Runnable() {
            @Override
            public void run() {
                openMainMenu();
            }
        });

        studentMatterOption = new InterfaceOption("Student Matters", new Runnable() {
            @Override
            public void run() {
                studentMatterInterface.showAndGetOption().run();
            }
        });

        moduleMatterOption = new InterfaceOption("Module Matters", new Runnable() {
            @Override
            public void run() {
                moduleMatterInterface.showAndGetOption().run();
            }
        });

        quitProgramOption = new InterfaceOption("Exit Student Management", new Runnable() {
            @Override
            public void run() {
                Debug.Log("Bye bye~~~~");
                SerializationHandler.saveClass(school, "SchoolSave");
                System.exit(0);
            }
        });

        loadSaveFileOption = new InterfaceOption("Load save file", new Runnable() {
            @Override
            public void run() {
                School s = SerializationHandler.loadClass("SchoolSave");
                if (s != null) {
                    school = s;
                    Debug.LogInfo("Save file has been loaded.");
                } else {
                    Debug.LogError("Save file loading failed! its most likely because you didnt exit the app normally or its the first launch of this app");
                }
                openMainMenu();
            }

        });

        studentMatterInterface = new Interface("Student Matters");
        studentMatterInterface.addOption(addStudentOption);
        studentMatterInterface.addOption(removeStudentOption);
        studentMatterInterface.addOption(displayStudentOption);
        studentMatterInterface.addOption(registerModuleOption);
        studentMatterInterface.addOption(displayModulesTakenByStudent);
        studentMatterInterface.addOption(openMainMenuOption);

        moduleMatterInterface = new Interface("Module Matters");
        moduleMatterInterface.addOption(addModuleOption);
        moduleMatterInterface.addOption(removeModuleOption);
        moduleMatterInterface.addOption(displayModuleListOption);
        moduleMatterInterface.addOption(openMainMenuOption);

        mainMenuInterface = new Interface("Main Menu");
        mainMenuInterface.addHeader(Debug.ANSI_YELLOW + "Welcom to SchoolApp made for IP assignment by Huang Ching" + Debug.ANSI_RESET);
        mainMenuInterface.addHeader(Debug.ANSI_YELLOW + "If you have used this app before you can select Load save file to load previous progresses" + Debug.ANSI_RESET);
        mainMenuInterface.addHeader(Debug.ANSI_YELLOW + "I did not make the save load on startup because it is not part of the requirment of the assigment," + Debug.ANSI_RESET);
        mainMenuInterface.addHeader(Debug.ANSI_YELLOW + "however it can be easily implimented" + Debug.ANSI_RESET);
        mainMenuInterface.addOption(studentMatterOption);
        mainMenuInterface.addOption(moduleMatterOption);
        mainMenuInterface.addOption(loadSaveFileOption);
        mainMenuInterface.addOption(quitProgramOption);

    }

    public static void openMainMenu() {
        mainMenuInterface.showAndGetOption().run();
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
        Student student = Debug.getFromListWithID(school.students, "Please select student");
        school.removeStudent(student.studentID);
        Debug.Log(student.toString() + " has been successfully removed.");
        pauseAndGoMainMenu();
    }

    public static void addModule() {

        int id = Debug.getInt("Please enter module ID");
        for (Module m : school.modules) {
            if (m.ModuleID == id) {
                Debug.LogError("Duplicate module ID!");
                retryOrReturnMainMenu(addModuleOption);
                return;
            }
        }
        String name = Debug.getString("Please eneter module name");
        int limit = Debug.getInt("Please eneter max population limit");
        int se = Debug.getInt("Please eneter semestrer number.", 1, 2);
        Module module = new Module(id, name, limit, se);

        if (Debug.getBoolean("Please confirm to add " + module.toString() + ".")) {
            school.addModule(module);
        }
        Debug.Log("Added " + module.toString() + " sucessfully.");
        pauseAndGoMainMenu();
    }

    public static void removeModule() {
        Module module = Debug.getFromListWithID(school.modules, "Please select module");
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
        Debug.Log(Debug.generateBoxString(l, "Module list:"));
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
        Debug.Log(Debug.generateBoxString(l, "Student list:"));
        pauseAndGoMainMenu();
    }

    public static void displayStudentModules() {
        List<String> l = new ArrayList<String>();
        Student student = Debug.getFromListWithID(school.students, "Please select studnet");
        if (student.modules.isEmpty()) {
            Debug.LogError("module list is empty.");
            pauseAndGoMainMenu();
            return;
        }
        for (Module m : student.modules) {
            l.add(m.toString());
        }
        Debug.Log(Debug.generateBoxString(l, "Module taken by " + student.toString() + ":", "This list shows moudles that is \n taken by " + student.toString()));
        pauseAndGoMainMenu();
    }

    public static void registerModuleForStudent() {
        Student student = Debug.getFromListWithID(school.students, "Please select studnet");
        Module module = Debug.getFromListWithID(school.modules, "Please select module");
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
        Interface inter = new Interface("Do you want to try again?");
        inter.addOption(option.clone().setName("Try again"));
        inter.addOption(openMainMenuOption);
        inter.showAndGetOption().run();
    }

    public static void pauseAndGoMainMenu() {
        Debug.getString("Enter any key to go to main menu.");
        openMainMenu();
    }
}
