import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Deleter {
    private int tries = 0;
    private final JList<String> text = new JList<>(new String[]{"Deleter starting..."});

    private Deleter() {}

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InterruptedException, InstantiationException, IllegalAccessException, IOException {
        Deleter deleter = new Deleter();
        deleter.run(args);
    }

    private void run(String[] files) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException, IOException {
        append("--- Files to delete :");
        for (String arg : files) {
            append("    " + arg);
        }
        append("---------------------");

        JFrame frame = new JFrame("Deleter");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JScrollPane scroll = new JScrollPane(text);
        scroll.setBounds(0, 0, 500, 500);
        frame.add(scroll);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        for (String file : files) {
            tries = 0;
            delete(new File(file));
        }
        append("Done! You can now close this window.");
        Thread.sleep(50000);
        System.exit(0);
    }

    private void delete(File file) throws InterruptedException {
        append("Checking if \"" + file.getAbsolutePath() + "\" exists");
        if (!file.exists()) {
            append("\"" + file.getAbsolutePath() + "\" does not exist!");
            return;
        }

        append("Deleting file \"" + file.getAbsolutePath() + "\"");

        if (!file.delete()) {
            tries += 1;
            if (tries >= 4) {
                append("\nToo many tries for \"" + file.getAbsolutePath() + "\", cancelling.");
                return;
            }
            append("\n\"" + file.getAbsolutePath() + "\" failed to delete, trying again in " + tries + " seconds...");
            Thread.sleep(1000L * tries);
            delete(file);
        } else {
            append("Successfully deleted \"" + file.getAbsolutePath() + "\"!");
        }
    }

    private void append(String string) {
        System.out.println(string);
        ArrayList<String> list = getList();
        list.add(string);
        text.setModel(new JListList(list));
    }

    private ArrayList<String> getList() {
        ArrayList<String> list = new ArrayList<>();
        int i = 0;
        while (i < text.getModel().getSize()) {
            i++;
            list.add(text.getModel().getElementAt(i - 1));
        }
        return list;
    }
}

