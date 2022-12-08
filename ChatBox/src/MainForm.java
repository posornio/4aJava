import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;

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


    public MainForm(){
        DatabaseManager Db = new DatabaseManager();
        //Connection connection = Db.conn;
        Db.dbinit();
        setContentPane(mainPanel);
        setTitle("ChatApp");
        ArrayList<String> asAnnu = Db.getAnnuaireList();
        JList<String> annuaireJL = new JList<>(asAnnu.toArray(new String[0]));
        ContactScroll = new JScrollPane(annuaireJL);
        setSize(450,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        MainForm myForm = new MainForm();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
