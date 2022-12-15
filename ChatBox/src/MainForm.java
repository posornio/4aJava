import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

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

    public JList<Object> getList1() {
        return list1;
    }

    public void setList1(JList<Object> list1) {
        this.list1 = list1;
    }

    private JScrollPane paneContact;
    private JScrollPane paneMessages;
    private JList list2;
    private JButton buttonEnvoyer;
    private JTextArea textArea1;
    private DefaultListModel messageModel = new DefaultListModel();

    private String selected;
    private String selectAnnu;

    public String getSelectAnnu() {
        return selectAnnu;
    }

    public void setSelectAnnu(String selectAnnu) {
        this.selectAnnu = selectAnnu;
    }
// private ArrayList<String> arrMsgStr = new ArrayList<>();


    private JTable table1;


    public MainForm() {

        $$$setupUI$$$();
        setContentPane(mainPanel);
        setTitle("ChatApp");
        DatabaseManager Db = new DatabaseManager();

        //Connection connection = Db.conn;
        Db.dbinit();

        ArrayList<String> asAnnu = Db.getAnnuaireList();
        setSize(800, 600);
        DefaultListModel messageModel = new DefaultListModel();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        list1 = new JList<>(asAnnu.toArray());
        paneContact.add(list1);
        list1.setFixedCellHeight(50);
        list2 = new JList(messageModel);
        list2.setFixedCellWidth(1);
        list2.setFixedCellWidth(1);
        paneMessages.add(list2);
        list1.setEnabled(true);
        list2.setEnabled(true);

        paneContact.setEnabled(true);
        paneMessages.setEnabled(false);
        setVisible(true);

    }

    public static void main(String[] args) {
        MainForm myForm = new MainForm();

    }

    public JButton getChangerPseudoButt() {
        return ChangerPseudoButt;
    }

    public void setChangerPseudoButt(JButton changerPseudoButt) {
        ChangerPseudoButt = changerPseudoButt;
    }

    private void createUIComponents() {
        DatabaseManager Db = new DatabaseManager();
        Db.dbinit();
        ContactSelector contactSelector = new ContactSelector(this,Db);
        ActivityPseudo activityPseudo =new ActivityPseudo(this,Db);
        ActivityLogin activityLogin = new ActivityLogin(Db);

        //contactSelector.visible(false);
        ConversationManager cm = new ConversationManager();
        buttonEnvoyer = new JButton("Envoyer");

        ArrayList<String> asAnnu = Db.getAnnuaireList();
        setSize(800, 600);
        list1 = new JList<Object>(asAnnu.toArray());
        DeconnexionButt = new JButton();
        ChangerPseudoButt = new JButton(Db.getPseudo());
        ChangerPseudoButt.setText(Db.getPseudo());
        AffAnnButt = new JButton();



        buttonEnvoyer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String messageAEnv = textArea1.getText();
                System.out.println(selected);
                int idM =Db.getMaxIdmessage()+1;
                System.out.println(idM);
                Db.insertmessage(idM,Db.getPseudo(),selected,messageAEnv,new Timestamp(System.currentTimeMillis()));
                ArrayList<DatabaseManager.Message> ahwx =Db.ArrayHistorywithX(Db.getPseudo(),selected);
                messageModel.addElement(ahwx.get(ahwx.size()-1).toString());
                textArea1.setText("");
            }
        });

        DeconnexionButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                activityLogin.setVisible(true);

            }
        });

        ChangerPseudoButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                activityPseudo.setVisible(true);


            }
        });
        AffAnnButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contactSelector.setVisible(true);
               contactSelector.setContactChoisi("");


                System.out.println("+"+contactSelector.getContactChoisi());

            }
        });
        list2 = new JList(messageModel);


        list1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                selected = asAnnu.get(((ListSelectionModel) e.getSource()).getSelectedIndices()[0]);
                ArrayList<DatabaseManager.Message> histWX = Db.ArrayHistorywithX(Db.getPseudo(), selected);
                //messageModel = initLM(histWX);
                //messageModel = new DefaultListModel();
                messageModel.removeAllElements();

                for ( int i = 0; i < histWX.toArray().length; i++ ){
                    DatabaseManager.Message message =histWX.get(i);
                    messageModel.addElement(message);
                }
                list2 = new JList(messageModel);

            }
        });

/*
        list2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                int sel = ((ListSelectionModel) e.getSource()).getSelectedIndices()[0];
                System.out.println(sel);
                ArrayList<DatabaseManager.Message> histWX = Db.ArrayHistorywithX("        Db.getPseudo()
", selected);
                messageModel.set(sel,histWX.get(sel).contenu+histWX.get(sel).date.toString());

            }
        });
*/
        list2.setCellRenderer(new DefaultListCellRenderer(){
            // @Override
            public Component getListCellRendererComponent(JList liste, Object value,int index,boolean isSelected, boolean cellHasFocus){
                Component c = super.getListCellRendererComponent(liste,value,index,isSelected,cellHasFocus);
                if (value instanceof DatabaseManager.Message){
                    DatabaseManager.Message msg = (DatabaseManager.Message) value;
                    setText(msg.contenu);
                    if (msg.idSender.equals(selected)){
                        setHorizontalAlignment(SwingConstants.LEFT);
                        setBackground(Color.gray);
                        setPreferredSize((new Dimension(10,30)));
                        setForeground(Color.WHITE);

                    }
                    else{
                        setHorizontalAlignment(SwingConstants.RIGHT);
                        setPreferredSize((new Dimension(10,30)));

                        setBackground(Color.BLUE.brighter());
                        setForeground(Color.WHITE);

                    }
                    if (isSelected) {
                        setBackground(getBackground().darker());
                        if (msg.idSender.equals(selected)){
                            setText(msg.contenu + " " + msg.date.toString());
                        }else{
                            setText(msg.date.toString() + " " +msg.contenu );

                        }

                        }
                }
                return c;
            }
        });




        //paneContact.add(list1);

        // TODO: place custom component creation code here
    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setEnabled(true);
        PaneCenter = new JPanel();
        PaneCenter.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(PaneCenter, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        paneContact = new JScrollPane();
        PaneCenter.add(paneContact, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        paneContact.setViewportView(list1);
        paneMessages = new JScrollPane();
        PaneCenter.add(paneMessages, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        //list2.setModel(defaultListModel1);
        paneMessages.setViewportView(list2);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        PaneCenter.add(panel1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        panel1.add(buttonEnvoyer, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textArea1 = new JTextArea();
        panel1.add(textArea1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        PaneHead = new JPanel();
        PaneHead.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(PaneHead, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, 1, null, null, null, 0, false));
        nameLabel = new JLabel();
        nameLabel.setText("ChatApp");
        PaneHead.add(nameLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        AffAnnButt.setText("Afficher Annuaire");
        PaneHead.add(AffAnnButt, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PaneHead.add(ChangerPseudoButt, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        DeconnexionButt.setText("Deconnexion");
        PaneHead.add(DeconnexionButt, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }
    public DefaultListModel initLM(ArrayList a){
        DefaultListModel model = new DefaultListModel<>();
        for ( int i = 0; i < a.toArray().length; i++ ){
            model.addElement(a.get(i).toString());
        }
        return model;
    }
    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
    public void handlerAnnu(ArrayList<String> asAnnu){
        JFrame frame = new JFrame("Annuaire");
        frame.setLayout(new BorderLayout());
        DatabaseManager Db = new DatabaseManager();
        Db.dbinit();
        frame.setSize(800, 600);
        DefaultListModel messageModel = new DefaultListModel();


        // Create the list model and the contact list
        JList<Object> contactList = new JList<>(asAnnu.toArray());
        // Add some sample contacts to the list mode

        // Add the contact list to the panel
        frame.add(new JScrollPane(contactList), BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new ContactSelector());
        frame.setLocationRelativeTo(null);


        frame.pack();
        frame.setVisible(true);
    }
}