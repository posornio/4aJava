import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
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
    private DefaultListModel messageModel = new DefaultListModel();

   // private ArrayList<String> arrMsgStr = new ArrayList<>();





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
        list1 = new JList<>(asAnnu.toArray());
        paneContact.add(list1);
        list2 = new JList(messageModel);
        paneMessages.add(list2);
        list1.setEnabled(true);
        list2.setEnabled(true);
        paneContact.setEnabled(true);
        paneMessages.setEnabled(false);

        setVisible(true);
        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //super.mouseClicked(e);
                if (e.getClickCount()==2){
                    System.out.println("CLICK");

                }

            }
        });
        list1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    System.out.println("e.getSource()");
                }
            }
        });
    }

    public static void main(String[] args) {
        MainForm myForm = new MainForm();

    }

    private void createUIComponents() {
        DatabaseManager Db = new DatabaseManager();

        Db.dbinit();
        ArrayList<String> asAnnu = Db.getAnnuaireList();
        setSize(800,600);
        list1 = new JList<Object>(asAnnu.toArray());

        System.out.println("asAnnu!");
        if(messageModel == null)
        {
            messageModel = new DefaultListModel();
        }

        list2 = new JList(messageModel);

        messageModel.addElement("HETY");



        //paneContact.add(list1);

        // TODO: place custom component creation code here
    }
}
