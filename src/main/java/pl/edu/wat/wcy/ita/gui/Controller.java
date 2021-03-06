package pl.edu.wat.wcy.ita.gui;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.edu.wat.wcy.ita.Tree.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Controller {
    private static double scale = 1;
    private static double delta = 0.1;
    private Stage stage;
    private static Scale scaleTransform = new Scale(scale, scale, 0, 0);
    private SettingsSingleton settings = SettingsSingleton.getSingleton();
    public TextField findProbability;
    public TextField removeProbability;
    public TextField addProbability;
    public TableColumn depthColumn;
    public TableColumn BSTDepthColumn;
    public TableColumn AVLDepthColumn;
    public TableColumn RBDepthColumn;
    public TableColumn splayDepthColumn;
    public TableColumn heightColumn;
    public TableColumn BSTHeightColumn;
    public TableColumn AVLHeightColumn;
    public TableColumn RBHeightColumn;
    public TableColumn SplayHeightColumn;
    public TableColumn leafsColumn;
    public TableColumn BSTLeafsColumn;
    public TableColumn AVLLeafsColumn;
    public TableColumn RBLeafsColumn;
    public TableColumn SplayLeafsColumn;
    public TableColumn rotateColumn;
    public TableColumn AVLRotateColumn;
    public TableColumn RBRotateColumn;
    public TableColumn SplayRotateColumn;
    public Tab BSTTab;
    public Tab AVLTab;
    public Tab RBTab;
    public Tab SplayTab;
    public TextArea numberArea;
    public TableView<Entry> table;
    public ScrollPane bstPane;
    public ScrollPane rbPane;
    public ScrollPane avlPane;
    public ScrollPane splayPane;
    public Label operationSplayLabel;
    public Label operationBstLabel;
    public Label operationAvlLabel;
    public Label operationRbLabel;
    public TextField randomSize;

    public void addNodesButton() { TreeOperation(OperationType.ADD); }
    public void removeNodesButton() { TreeOperation(OperationType.REMOVE); }
    public void findNodesButton() { TreeOperation(OperationType.FIND); }
    public void randomNodesButton() { TreeOperation(OperationType.RANDOM); }
    public void prevTree() { setTree(TreeContainer.getPrev()); }
    public void nextTree() { setTree(TreeContainer.getNext()); }

    public void addNumbers() {
        ArrayList<Integer> list = new ArrayList<>();
        Random random = new Random();
        StringBuilder text = new StringBuilder(numberArea.getText());
        if (!tryParseInt(randomSize.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(randomSize.getText());
            alert.setContentText("Zły format liczby");
            alert.showAndWait();
            return;
        }
        int i = Integer.parseInt(randomSize.getText());
        while (list.size() < i) {
            int rnd = random.nextInt(10000);
            if(!list.contains(rnd)) {
                list.add(rnd);
                text.append(rnd).append("\n");
            }
        }
        numberArea.setText(text.toString());
    }

    private void setTree(TreeContainer treeContainer) {
        if (treeContainer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ostrzeżenie");
            alert.setHeaderText(null);
            alert.setContentText("Nie ma kolejnego drzewa");
            alert.showAndWait();
            return;
        }
        if (treeContainer.countNodes() < 300) {
            String label = treeContainer.getComment();
            if (settings.isBST()) {
                bstPane.setContent(getTree(treeContainer.getTree(TreeType.BST)));
                operationBstLabel.setText(label);
            }
            if (settings.isRB()) {
                rbPane.setContent(getTree(treeContainer.getTree(TreeType.RB)));
                operationRbLabel.setText(label);
            }
            if (settings.isAVL()) {
                avlPane.setContent(getTree(treeContainer.getTree(TreeType.AVL)));
                operationAvlLabel.setText(label);
            }
            if (settings.isSPLAY()) {
                splayPane.setContent(getTree(treeContainer.getTree(TreeType.SPLAY)));
                operationSplayLabel.setText(label);
            }
        }
    }

    private Canvas getTree (Tree tree) {
        double windowWidth = bstPane.getWidth();
        double treeWidth = (tree.getTreeHeight()*300)^(tree.getTreeHeight());
        double width = Math.max(treeWidth, windowWidth);
        Canvas canvas = new Canvas(width,tree.getTreeHeight()*40);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFont(new Font(12));

        canvas.getTransforms().add(scaleTransform);
        canvas.setOnScroll((event -> {
            if (event.getDeltaY() < 0) scale -= delta;
            else scale += delta;
            scaleTransform.setX(scale);
            scaleTransform.setY(scale);
        }));
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);

        if (tree.isNull(tree.getRoot())) drawNode(gc,tree, tree.getRoot(), width/2.0, 10, width/2.0 -10);

        return canvas;
    }

    private void drawNode(GraphicsContext gc, Tree tree, Node node, double x, double y, double sw) {
        gc.setFill(Color.GREEN);
        gc.fillOval(x, y, 20, 20);
        gc.setFill(Color.BLACK);
        gc.fillText(node.getValue().toString(),x,y);
        sw/=2.0;
        if (tree.isNull(node.getLeft())){
            gc.strokeLine(x+10,y+20, x+10-sw, y+40);
            drawNode(gc,tree,node.getLeft(),x-sw,y+40,sw);
        }
        if (tree.isNull(node.getRight())){
            gc.strokeLine(x+10,y+20, x+10+sw, y+40);
            drawNode(gc,tree,node.getRight(),x+sw,y+40,sw);
        }
    }

    private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void TreeOperation(OperationType operationType) {
        if (operationType == OperationType.RANDOM) randomTreeOperation();
        else nonRandomOperation(operationType);
        numberArea.setText("");
    }

    private void nonRandomOperation(OperationType operationType) {
        String[] numbers = numberArea.getText().split("\n");
        ObservableList<Entry> data = table.getItems();


        final TreeContainer[] last = {null};
        Task<Void> yourTaskName = new Task<Void>() {
            @Override
            public Void call() {
                int i = 0;
                for (String number : numbers) {
                    if (!tryParseInt(number)) {
                        wrongNumberAlert(number);
                        continue;
                    }
                    int n = Integer.parseInt(number);

                    TreeContainer treeContainer;
                    treeContainer = new TreeContainer(operationType, n);
                    data.addAll(new Entry(treeContainer));
                    if (treeContainer.countNodes() < 300)
                        last[0] = treeContainer;

                    updateProgress(i++, numbers.length);
                    updateMessage(treeContainer.getComment());
                }
                return null;
            }
        };
        createProgressBar(yourTaskName);
    }

    private void wrongNumberAlert(String number) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(number);
        if (!number.equals("")) alert.setContentText("Nie można operować na takim elemencie");
        else alert.setContentText("Nie wprowadzono liczb do operacji");
        alert.showAndWait();
    }

    private void randomTreeOperation() {
        String[] numbers = numberArea.getText().split("\n");
        ObservableList<Entry> data = table.getItems();
        Random random = new Random();
        double findProbability = Double.parseDouble(this.findProbability.getText());
        double removeProbability = Double.parseDouble(this.removeProbability.getText());
        double addProbability = Double.parseDouble(this.addProbability.getText());
        if (findProbability + removeProbability + addProbability != 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Suma prawdopodobieńst nie jest równa 1");
            alert.showAndWait();
            return;
        }
        Task<Void> yourTaskName = new Task<Void>() {
            @Override
            public Void call() {
                double randomValue;
                int i = 0;
                for (String number : numbers) {
                    if (!tryParseInt(number)) {
                        wrongNumberAlert(number);
                        continue;
                    }
                    int n = Integer.parseInt(number);

                    TreeContainer treeContainer;
                    randomValue = random.nextDouble();
                    if (randomValue < addProbability) treeContainer = new TreeContainer(OperationType.ADD, n);
                    else if (randomValue < addProbability + removeProbability) treeContainer = new TreeContainer(OperationType.REMOVE, n);
                    else treeContainer = new TreeContainer(OperationType.FIND, n);

                    data.addAll(new Entry(treeContainer));
                    updateProgress(i++, numbers.length);
                    updateMessage(treeContainer.getComment());
                }
                return null;
            }
        };
        createProgressBar(yourTaskName);
    }

    private void createProgressBar(Task<Void> yourTaskName) {
        ProgressBar pBar = new ProgressBar();
        pBar.progressProperty().bind(yourTaskName.progressProperty());
        Label statusLabel = new Label("Proszę czekać...");
        yourTaskName.messageProperty().addListener((observable, oldValue, newValue) -> statusLabel.setText(newValue));

        VBox root = new VBox(statusLabel, pBar);
        root.setFillWidth(true);
        root.setAlignment(Pos.CENTER);
        Scene secondScene = new Scene(root, 230, 100);
        Stage newWindow = new Stage();
        newWindow.setTitle("ładowanie");
        newWindow.setScene(secondScene);
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.show();
        yourTaskName.setOnSucceeded(event -> {
            newWindow.close();
            setTree(TreeContainer.getLast());
        });
        Thread loadingThread = new Thread(yourTaskName);
        loadingThread.start();
    }

    public void saveCsvButton() throws IOException {
        StringBuilder stringBuilder = new StringBuilder(Entry.getNames());
        stringBuilder.append("\n");
        for (Entry e: table.getItems())
            stringBuilder.append(e).append("\n");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz plik csv");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(stringBuilder.toString());
            }
        }
    }

    public void setStageAndSetupListeners(Stage stage) {
        this.stage = stage;
        SettingsSingleton settings = SettingsSingleton.getSingleton();
        depthColumn.setVisible(settings.isDepth());
        BSTDepthColumn.setVisible(settings.isBST() && settings.isDepth());
        AVLDepthColumn.setVisible(settings.isAVL() && settings.isDepth());
        RBDepthColumn.setVisible(settings.isRB() && settings.isDepth());
        splayDepthColumn.setVisible(settings.isSPLAY() && settings.isDepth());
        heightColumn.setVisible(settings.isHeight());
        BSTHeightColumn.setVisible(settings.isBST() && settings.isHeight());
        AVLHeightColumn.setVisible(settings.isAVL() && settings.isHeight());
        RBHeightColumn.setVisible(settings.isRB() && settings.isHeight());
        SplayHeightColumn.setVisible(settings.isSPLAY() && settings.isHeight());
        leafsColumn.setVisible(settings.isLeafs());
        BSTLeafsColumn.setVisible(settings.isBST() && settings.isLeafs());
        AVLLeafsColumn.setVisible(settings.isAVL() && settings.isLeafs());
        RBLeafsColumn.setVisible(settings.isRB() && settings.isLeafs());
        SplayLeafsColumn.setVisible(settings.isSPLAY() && settings.isLeafs());
        rotateColumn.setVisible(settings.isRotates());
        AVLRotateColumn.setVisible(settings.isAVL() && settings.isRotates());
        RBRotateColumn.setVisible(settings.isRB() && settings.isRotates());
        SplayRotateColumn.setVisible(settings.isSPLAY() && settings.isRotates());
        BSTTab.setDisable(!settings.isBST());
        AVLTab.setDisable(!settings.isAVL());
        RBTab.setDisable(!settings.isRB());
        SplayTab.setDisable(!settings.isSPLAY());
    }

}
