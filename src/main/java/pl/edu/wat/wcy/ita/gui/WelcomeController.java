package pl.edu.wat.wcy.ita.gui;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import pl.edu.wat.wcy.ita.Main;

public class WelcomeController {
    public CheckBox bst;
    public CheckBox avl;
    public CheckBox rb;
    public CheckBox splay;
    public CheckBox depth;
    public CheckBox height;
    public CheckBox leafs;
    public CheckBox rotates;
    private Main main;

    public void button() {
        SettingsSingleton settingsSingleton = SettingsSingleton.getSingleton();
        settingsSingleton.setBST(bst.isSelected());
        settingsSingleton.setAVL(avl.isSelected());
        settingsSingleton.setRB(rb.isSelected());
        settingsSingleton.setSPLAY(splay.isSelected());
        settingsSingleton.setDepth(depth.isSelected());
        settingsSingleton.setHeight(height.isSelected());
        settingsSingleton.setLeafs(leafs.isSelected());
        settingsSingleton.setRotates(rotates.isSelected());

        main.newScene();
    }

    public void bstSelect() {
        if(bst.isSelected()) {
            rotates.setSelected(false);
            rotates.setDisable(true);
        }
        else rotates.setDisable(false);
    }

    public void setMain (Main main) {
        this.main = main;
    }
}
