package pl.edu.wat.wcy.ita.gui;

import lombok.Data;

@Data
public class SettingsSingleton {
    private boolean BST = true;
    private boolean AVL = true;
    private boolean RB = true;
    private boolean SPLAY = true;
    private boolean depth = true;
    private boolean height = true;
    private boolean leafs = true;
    private boolean rotates = false;
    private  static SettingsSingleton singleton = new SettingsSingleton();

    private SettingsSingleton () { }

    public static SettingsSingleton getSingleton() {
        return singleton;
    }
}
