import javax.swing.*;
import java.sql.Connection;

public class MainForm extends JFrame {
    private JScrollPane ContactScroll;
    private JScrollPane MessageScroll;
    private JButton AffAnnButt;
    private JButton ChangerPseudoButt;
    private JButton DeconnexionButt;
    private JPanel PaneHead;
    private JPanel PaneCenter;
    private JPanel mainPanel;
    private JLabel nameLabel;
    DatabaseManager Db = new DatabaseManager();
    Connection connection = Db.conn;

    public MainForm(){
        setContentPane(mainPanel);
        setTitle("ChatApp");

        setSize(450,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        MainForm myForm = new MainForm();
    }
}
