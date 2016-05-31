/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagement;

import java.util.ArrayList;
import java.util.List;

public class Interface {

    List<InterfaceOption> options = new ArrayList<InterfaceOption>();
    String title;

    public Interface(String title) {
        this.title = title;
    }

    public void addOption(InterfaceOption option) {
        options.add(option);
    }

    public InterfaceOption showAndGetOption() {
        return Debug.getFromList(options, "Please choose what function you wish to use.", title);
    }
}
