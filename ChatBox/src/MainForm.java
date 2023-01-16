import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.*;

import static java.lang.Thread.sleep;
import static javax.swing.text.StyleConstants.setBackground;
import static javax.swing.text.StyleConstants.setForeground;

public class MainForm extends JFrame {
    private JScrollPane ContactScroll;
    private JButton AffAnnButt;
    private JButton ChangerPseudoButt;
    private JButton DeconnexionButt;
    private JPanel PaneHead;

    public JTable getList2() {
        return list2;
    }

    public void setList2(JTable list2) {
        this.list2 = list2;
    }

    private JPanel PaneCenter;
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JList list1;

    private HashMap<String,ConversationManager> mapCM;

    public HashMap<String, ConversationManager> getMapCM() {
        return mapCM;
    }

    public void setMapCM(HashMap<String, ConversationManager> mapCM) {
        this.mapCM = mapCM;
    }

    private JScrollPane paneContact;
    private JScrollPane paneMessages;
    private JTable list2;
    private JButton buttonEnvoyer;
    private JTextArea textArea1;

    private DefaultTableModel messageModel = new DefaultTableModel(0,3){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultListModel convoModel = new DefaultListModel();
    //private DefaultListModel messListM = new DefaultListModel();

    private TableModel messageTable= new DefaultTableModel();

    public DefaultListModel getConvoModel() {
        return convoModel;
    }

    public void setConvoModel(DefaultListModel convoModel) {
        this.convoModel = convoModel;
    }

    private String selected="";

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    private String selectAnnu;

    public String getSelectAnnu() {
        return selectAnnu;
    }

    public void setSelectAnnu(String selectAnnu) {
        this.selectAnnu = selectAnnu;
    }
// private ArrayList<String> arrMsgStr = new ArrayList<>();


    private JTable table1;

    private DatabaseManager Db;

    public DatabaseManager getDb() {
        return Db;
    }

    public void setDb(DatabaseManager db) {
        Db = db;
    }
    public ArrayList<String> annuOuv;

    public DefaultTableModel getMessageModel() {
        return messageModel;
    }


    public void setMessageModel(DefaultTableModel messageModel) {
        this.messageModel = messageModel;
    }
    public MainForm() {}
    public MainForm(DatabaseManager DbIn) {
        this.Db=DbIn;
        $$$setupUI$$$();
        setContentPane(mainPanel);
        setTitle("ChatApp");

        //DatabaseManager Db = new DatabaseManager();
        String header[]= new String[]{selected,"",getDb().getPseudo()};
        //Connection connection = getDb().conn;
        getDb().dbinit();
        //setDb(Db);
        ArrayList<String> asAnnu = getDb().getAnnuaireList();
        ArrayList<String> ConvOpen = getDb().getConvOuvertes();
        System.out.println(ConvOpen);
        setSize(800, 600);
        DefaultTableModel messageModel = getMessageModel();
        DefaultListModel convoModel = getConvoModel();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        /*
        for ( int i = 0; i < ConvOpen.toArray().length; i++ ){
            convoModel.addElement(ConvOpen.get(i));
        }*/
        //list1 = new JList(convoModel);
        list1 = new JList();
        //messageModel.setColumnIdentifiers(header);
        list2= new JTable();
        //buttonEnvoyer = new JButton("Envoyer");
        getRootPane().setDefaultButton(buttonEnvoyer);
        //buttonEnvoyer.setMnemonic(KeyEvent.VK_ENTER);

        list1.setModel(convoModel);
        paneContact.add(list1);
        list2.setModel(messageModel);
        //messageModel.setColumnIdentifiers(header);

        //list2.setFixedCellWidth(1);
        //list2.setFixedCellWidth(1);
        paneMessages.add(list2);
        list1.setEnabled(true);
        list2.setEnabled(true);
        //JRootPane rootPane = SwingUtilities.getRootPane(buttonEnvoyer);


        paneContact.setEnabled(true);
        paneMessages.setEnabled(false);
        setVisible(true);

    }

    public static void main(String[] args) {
        MainForm myForm = new MainForm();
        //myForm.getRootPane()

    }

    public JButton getChangerPseudoButt() {
        return ChangerPseudoButt;
    }

    public void setChangerPseudoButt(JButton changerPseudoButt) {
        ChangerPseudoButt = changerPseudoButt;
    }

    private void createUIComponents() {
        //DatabaseManager Db = new DatabaseManager();
        //getDb().dbinit();
        //setDb(Db);
        ContactSelector contactSelector = new ContactSelector(this,getDb(),Db.getAnnuaireList());
        ActivityPseudo activityPseudo =new ActivityPseudo(this,getDb());
        ActivityLogin activityLogin = new ActivityLogin();
        activityLogin.setVisible(false);
        ConversationManager cm = new ConversationManager();
        System.out.println("Db est " + getDb().getAnnuaireList());
        HashMap<String, ConversationManager> mapCM = new HashMap<String, ConversationManager>();
        setMapCM(mapCM);
        annuOuv= new ArrayList<String>();
        //contactSelector.visible(false);
        buttonEnvoyer = new JButton("Envoyer");
        boolean convOuverte = false;
        buttonEnvoyer.setVisible(false);
        textArea1 = new JTextArea();

        textArea1.setVisible(false);
        ArrayList<String> asAnnu = getDb().getAnnuaireList();
        setSize(800, 600);
        //
        //
        //convoModel.removeAllElements();

        list1 = new JList(convoModel);
        list2 = new JTable(messageModel);
        list2.setShowGrid(false);
        list2.setIntercellSpacing(new Dimension(1, 1));
        list2.setDragEnabled(false);
        DeconnexionButt = new JButton();
        ChangerPseudoButt = new JButton(getDb().getPseudo());
        ChangerPseudoButt.setText(getDb().getPseudo());
        AffAnnButt = new JButton();
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        DatabaseManager.Message emptyMsg = new DatabaseManager.Message("","","",ts);







        buttonEnvoyer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list2.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("selected");
                list2.getTableHeader().repaint();
                String messageAEnv = textArea1.getText();
                if(messageAEnv.matches("[\n]+")){
                    textArea1.setText("");
                }
                if (!messageAEnv.isEmpty() && !messageAEnv.matches("[\n]+")) {
                    System.out.println(selected);
                    System.out.println(getMapCM());


                    try{ getMapCM().get(selected).sendmessage(messageAEnv);
                        int idM = getDb().getMaxIdmessage()+1;
                        System.out.println(idM);
                        getDb().insertmessage(idM,getDb().getownIP(),getDb().getIdbyLoginString(selected),messageAEnv,new Timestamp(System.currentTimeMillis()));
                        ArrayList<DatabaseManager.Message> ahwx = getDb().ArrayHistorywithX(getDb().getownIP(),getDb().getIdbyLoginString(selected));
                        emptyMsg.date=ahwx.get(ahwx.size()-1).date;
                        System.out.println(ahwx.get(ahwx.size()-1));
                        Timestamp ts = new Timestamp(System.currentTimeMillis());
                        DatabaseManager.Message emptyMsg = new DatabaseManager.Message("","","",ts);
                        messageModel.addRow(new Object[]{ emptyMsg,emptyMsg,ahwx.get(ahwx.size()-1)}) ;
                        //MessageTableRenderer mtr = new MessageTableRenderer(selected,ahwx);
                        //messListM.addElement(ahwx.get(ahwx.size()-1));
                        textArea1.setText("");

                        list2.setModel(messageModel);}
                    catch (Exception e1){}




                    //list2.setDefaultRenderer(String.class,new MessageTableRenderer(selected,ahwx));
                    ;;}
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
                activityPseudo.usernameField.setText("Nouveau psuedo: ");



            }
        });
        AffAnnButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contactSelector.setVisible(true);
               contactSelector.setContactChoisi("");

               for(String element: getDb().getAnnuaireList()){
                    if (!contactSelector.getListModel().contains(element) && !element.equals(Db.getPseudo())){
                        contactSelector.getListModel().addElement(element);
                        //contactSelector.getContactList().add(element);
                    }
               }

                System.out.println("+"+contactSelector.getContactChoisi());
                System.out.println("**"+Db.getAnnuaireList());


            }
        });

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                //todo fermer threads
                System.exit(0);//cierra aplicacion
            }
        });

        list2.setModel(messageModel);


        list1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                boolean convOuverte = true;
                buttonEnvoyer.setVisible(true);
                textArea1.setVisible(true);
                //selected = asAnnu.get(((ListSelectionModel) e.getSource()).getSelectedIndices()[0]);
                Contact selectedC = (Contact) convoModel.get(((ListSelectionModel) e.getSource()).getSelectedIndices()[0]);
                selectedC.unread=false;
                selected = selectedC.login;
                setSelected(selected);

                System.out.println(getSelected());
                //list2.getColumnModel().getColumn(0).setHeaderValue(selected);
                list2.getTableHeader().getColumnModel().getColumn(0).setHeaderValue(selected);
                list2.getTableHeader().repaint();

                ArrayList<DatabaseManager.Message> histWX = getDb().ArrayHistorywithX(getDb().getownIP(),getDb().getIdbyLoginString(selected));
                //messageModel = initLM(histWX);
                //messageModel = new DefaultListModel();
                messageModel.setRowCount(0);

                for ( int i = 0; i < histWX.toArray().length; i++ ){
                    DatabaseManager.Message message =histWX.get(i);
                    //messageModel.addElement(message);
                    DatabaseManager.Message emptyMsg = new DatabaseManager.Message("","","",message.date);

                    if (message.idSender.equals(getDb().getownIP())){

                        messageModel.addRow(new Object[]{emptyMsg,emptyMsg,message});
                        //messListM.addElement(message);
                    }
                    else {
                        emptyMsg.date=message.date;

                        messageModel.addRow(new Object[]{message,emptyMsg,emptyMsg});
                       // messListM.addElement(message);
                    }
                }
                list2.setModel(messageModel);




            }

        });

        list1.setCellRenderer(new AnnuaireRenderer(getDb()));
        list2.getColumnModel().getColumn(0).setCellRenderer(new MessageTableRenderer(selected,Db));
        System.out.println("HEEEEY "+selected+getSelected());
        list2.getColumnModel().getColumn(1).setCellRenderer(new MessageTableRenderer(getSelected(),Db));

        list2.getColumnModel().getColumn(2).setCellRenderer(new MessageTableRenderer(getSelected(),Db));
        //messageModel.setColumnIdentifiers(new Object[] { selected, " Date ", getDb().getPseudo() });
        //list2.getColumnModel().getColumn(0).setHeaderValue(getSelected());
        list2.getColumnModel().getColumn(1).setHeaderValue("Date");
        list2.getColumnModel().getColumn(2).setHeaderValue(getDb().getPseudo());




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

    public void handlerMR(String message,InetAddress addr){
        String idSender = getDb().getLoginbyIDString(addr.getHostAddress());
        Contact contactC = new Contact(idSender,true);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        DatabaseManager.Message emptyMsg = new DatabaseManager.Message("","","",ts);
        int idM = getDb().getMaxIdmessage()+1;
        getDb().insertmessage(idM,addr.getHostAddress(),getDb().getownIP(),message,ts);
        System.out.println("ideSender --- "+ idSender);

        if (idSender.equals(getDb().getIdbyLoginString(getSelected()))){
            System.out.println("Id "+ getDb().getIdbyLoginString(getSelected()));
            System.out.println("Login "+ getSelected());
            messageModel.addRow(new Object[]{ message,emptyMsg,emptyMsg }) ;
        }
        if(!annuOuv.contains(idSender)){
            annuOuv.add(contactC.login);
            getConvoModel().addElement(contactC);
            
        }
    }
    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }


static class Contact{
        public String login;
        public boolean unread;
        //pubic String id; ??
    public Contact(String login, boolean unread) {
        this.login = login;
        this.unread = unread;
    }
}
}
class AnnuaireRenderer extends DefaultListCellRenderer {

    public DatabaseManager DbR;
  public AnnuaireRenderer(DatabaseManager DbR){
      this.DbR=DbR;
  }
    public Component getListCellRendererComponent(JList list, Object value,int index,boolean isSelected, boolean cellHasFocus) {
        if (value instanceof MainForm.Contact){
            setBackground(Color.white);
            setForeground(Color.black);
            MainForm.Contact contactC = (MainForm.Contact) value;
            String contact = contactC.login;
            ArrayList<DatabaseManager.Message> msgArr = DbR.ArrayHistorywithX(DbR.getownIP(),DbR.getIdbyLoginString(contact) );
            setText(contact);
            if (contactC.unread){
                setBackground(Color.green);
                setForeground(Color.WHITE);
            }else{
                setBackground(Color.white);
                setForeground(Color.black);
            }
            if (msgArr.size()>0){
                DatabaseManager.Message dernierMsg = msgArr.get(msgArr.size()-1);
                String msgCont = dernierMsg.contenu;
                String fleche="<-";
                if (dernierMsg.idSender.equals(DbR.getPseudo())){
                    fleche="->";
                }
                fleche = contact + fleche +msgCont;
                setText(fleche);}

            if (isSelected){
                setBackground(Color.BLUE.brighter());
                setForeground(Color.WHITE);
            }

        }
        return this;
    }
}



class MessageTableRenderer extends JLabel implements TableCellRenderer {
    public String selected;
    DatabaseManager dbM;

    public MessageTableRenderer(String selected,DatabaseManager dbM){
        setOpaque(true);

        this.selected=selected;
        this.dbM=dbM;
    }
        public Component getTableCellRendererComponent(JTable list, Object value,boolean isSelected, boolean cellHasFocus,int row, int col) {
            //Component c = super.getTableCellRendererComponent(list,value,isSelected,cellHasFocus,row,col);

            if (value instanceof DatabaseManager.Message ) {
                //ArrayList<DatabaseManager.Message> ahwx = dbM.ArrayHistorywithX(dbM.getPseudo(), "xxRaveauxx");
                //Object messAt = list.getModel().getValueAt(row, col);
                //DatabaseManager.Message msg = (DatabaseManager.Message) messAt;

                //this.setOpaque(true);

                DatabaseManager.Message msg = (DatabaseManager.Message) value;
                setText(msg.contenu);

                if (!msg.contenu.equals("")){
                //DatabaseManager.Message msg = (DatabaseManager.Message) messAt;
                    if (col==2) {

                        //c.setHorizontalAlignment(SwingConstants.LEFT);
                        setHorizontalAlignment(SwingConstants.RIGHT);
                        setBackground(Color.BLUE.brighter());
                        // setPreferredSize((new Dimension(10,30)));
                        setForeground(Color.WHITE);

                    }
                    else if(col==0) {
                        setBackground(Color.GRAY);
                        setForeground(Color.WHITE);
                        setHorizontalAlignment(SwingConstants.LEFT);}

                    }
                else if (col!=1){
                    setBackground(Color.WHITE);
                    setForeground(Color.WHITE);
                }
                else{
                    setBackground(Color.WHITE);
                    setForeground(Color.black);
                    setText(msg.date.toString().substring(0, 16));
                }

                if (isSelected) {
                    setBackground(getBackground().darker());/*
                    if (msg.contenu.equals("") && col!=1 && col!=2){
                        setText(msg.date.toString());}

*/
                }

            }
            return this;
}}