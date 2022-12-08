import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

public class MainForm extends JFrame {
    private JScrollPane ContactScroll;
    private JButton AffAnnButt;
    private JButton ChangerPseudoButt;
    private JButton DeconnexionButt;
    private JPanel PaneHead;
    private JPanel PaneCenter;
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JList<Object> list1;
    private JScrollPane paneContact;
    private JScrollPane paneMessages;
    private JList list2;
    private JTable table1;


    public MainForm(){

        setContentPane(mainPanel);
        setTitle("ChatApp");
        DatabaseManager Db = new DatabaseManager();

        //Connection connection = Db.conn;
        Db.dbinit();
        ArrayList<String> asAnnu = Db.getAnnuaireList();
        setSize(800,600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        list1 = new JList<Object>(asAnnu.toArray());
        paneContact.add(list1);
        list1.setEnabled(true);
        paneContact.setEnabled(true);
        System.out.println(asAnnu.toString());
        System.out.println("asAnnu2");

        setVisible(true);
    }

    public static void main(String[] args) {
        MainForm myForm = new MainForm();

    }
    MouseListener mouseListener = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                DatabaseManager Db = new DatabaseManager();

                Db.dbinit();
                String selectedItem = (String) list1.getSelectedValue();
                int myId =Db.getIdbyLoginInt("xxOsornioxx");

                int theirId =Db.getIdbyLoginInt(selectedItem);
                ArrayList<String> arrMsgStr = new ArrayList<String>();
                ArrayList<DatabaseManager.Message> arrayMessages = Db.ArrayHistorywithX(myId,theirId);
                //arrayMessages.

               // list2 = new JList<Object>(asAnnu.toArray());
                paneContact.add(list1);
                list1.setEnabled(true);
                // add selectedItem to your second list.


            }
        }
    };
    private void createUIComponents() {
        DatabaseManager Db = new DatabaseManager();

        Db.dbinit();
        ArrayList<String> asAnnu = Db.getAnnuaireList();
        setSize(800,600);
        list1 = new JList<Object>(asAnnu.toArray());
        System.out.println("asAnnu!");
        //paneContact.add(list1);

        // TODO: place custom component creation code here
    }
}
