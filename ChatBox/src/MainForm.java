import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    public JTable list2;
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
    private int theme=0;

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
    public MainForm(DatabaseManager DbIn) throws IOException {
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
        try{
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        //myForm.getRootPane()

    }

    public JButton getChangerPseudoButt() {
        return ChangerPseudoButt;
    }

    public void setChangerPseudoButt(JButton changerPseudoButt) {
        ChangerPseudoButt = changerPseudoButt;
    }

    private void createUIComponents() throws IOException {
        //DatabaseManager Db = new DatabaseManager();
        //getDb().dbinit();
        //setDb(Db);
        ActivityPseudo activityPseudo =new ActivityPseudo(this,getDb());
        ActivityLogin activityLogin = new ActivityLogin();
        activityLogin.setVisible(false);
        ConversationManager cm = new ConversationManager();
        AccountManager am = new AccountManager();
        System.out.println("Db est " + getDb().getAnnuaireList());
        HashMap<String, ConversationManager> mapCM = new HashMap<String, ConversationManager>();
        setMapCM(mapCM);
        MainForm mf= this;
        annuOuv= new ArrayList<String>();
        //contactSelector.visible(false);
        BufferedImage buttonIcon = ImageIO.read(getClass().getResource("/icons/send301.png"));
        buttonEnvoyer = new JButton(new ImageIcon(buttonIcon));
        buttonEnvoyer.setBorder(BorderFactory.createEmptyBorder());
        buttonEnvoyer.setContentAreaFilled(false);

        boolean convOuverte = false;
        buttonEnvoyer.setVisible(false);
        textArea1 = new JTextArea();

        textArea1.setVisible(false);
        ArrayList<String> asAnnu = getDb().getAnnuaireList();
        setSize(800, 600);
        //
        //
        //convoModel.removeAllElements();
        nameLabel = new JLabel();
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


        if (buttonEnvoyer.getModel().isPressed()){
            BufferedImage buttonIcon2 = ImageIO.read(getClass().getResource("/icons/send303.png"));
            buttonEnvoyer = new JButton(new ImageIcon(buttonIcon2));
        }




        buttonEnvoyer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list2.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("selected");
                list2.getTableHeader().repaint();
                if (!Db.id_login_exists(Db.getIdbyLoginString(selected),selected)) {
                	JOptionPane.showMessageDialog(null,
                            "User changed login or disconnected",
                            "Alert",
                            JOptionPane.WARNING_MESSAGE);
                }
                else {
                String messageAEnv = textArea1.getText();
                if(messageAEnv.matches("[\n]+")){
                    textArea1.setText("");
                }
                if (!messageAEnv.isEmpty() && !messageAEnv.matches("[\n]+")) {
                    System.out.println(selected);
                    System.out.println(getMapCM());


                    try{if (getMapCM().get(Db.getIdbyLoginString(selected)).isClosed()) {
                        JOptionPane.showMessageDialog(null,
                                "User disconnected",
                                "Alert",
                                JOptionPane.WARNING_MESSAGE);
                    }

                    else {
                        BufferedImage buttonIcon4 = ImageIO.read(getClass().getResource("/icons/send302.png"));
                        buttonEnvoyer = new JButton(new ImageIcon(buttonIcon4));
                        getMapCM().get(Db.getIdbyLoginString(selected)).sendmessage(messageAEnv);
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

                        list2.setModel(messageModel);}}
                    catch (Exception e1){}




                    //list2.setDefaultRenderer(String.class,new MessageTableRenderer(selected,ahwx));
                    ;;}
            }
            }
        });

        DeconnexionButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThreadEnvoiAnnuaire envoiAnnuaire = new ThreadEnvoiAnnuaire("");
                envoiAnnuaire.start();
                System.out.println("envoi annuaire lancé");
                for (Map.Entry<String,ConversationManager> element : getMapCM().entrySet()){
                    try{ element.getValue().sendmessage("**ExitClavardage**");}
                    catch(Exception excep){
                        System.out.println("Error closing connection " + element.getKey() + "with " + excep);
                    }
                    element.getValue().closeconnection();
                }
                cm.closeconnection();
                System.exit(0); 

            }
        });

        ChangerPseudoButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                activityPseudo.setVisible(true);
                activityPseudo.usernameField.setText("Nouveau pseudo: ");



            }
        });
        AffAnnButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ContactSelector contactSelector = new ContactSelector(mf,getDb(),Db.getAnnuaireList());
                contactSelector.setVisible(true);
               contactSelector.setContactChoisi("");
               System.out.println("Annu ds CS"+ getDb().getAnnuaireList());
               //contactSelector.getListModel().removeAllElements();
               for(String element: getDb().getAnnuaireList()){
                    if ((!contactSelector.getListModel().contains(element)) && (!element.equals(Db.getPseudo()))){
                        contactSelector.getListModel().addElement(element);
                        System.out.println("on ajoute un élément : " + element);
                        //contactSelector.getContactList().add(element);
                    }
               }

                System.out.println("+"+contactSelector.getContactChoisi());
                System.out.println("**"+Db.getAnnuaireList());


            }
        });/*
        nameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                theme++;
                if (theme%2==1){
                try{
                    UIManager.setLookAndFeel(new FlatMacDarkLaf());

                } catch (UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }}
                else {
                    try{
                        UIManager.setLookAndFeel(new FlatMacLightLaf());
                    } catch (UnsupportedLookAndFeelException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });*/

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                //todo fermer threads
                ThreadEnvoiAnnuaire envoiAnnuaire = new ThreadEnvoiAnnuaire("");
                envoiAnnuaire.start();
                for (Map.Entry<String,ConversationManager> element : getMapCM().entrySet()){
                    try{ element.getValue().sendmessage("**ExitClavardage**");}
                        catch(Exception excep){
                        System.out.println("Error closing connection " + element.getKey() + "with " + excep);
                        }
                    element.getValue().closeconnection();
                }
                cm.closeconnection();
                System.exit(0);
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
                list2.getColumnModel().getColumn(0).setCellRenderer(new MessageTableRenderer(selected,Db,theme));
                list2.getColumnModel().getColumn(1).setCellRenderer(new MessageTableRenderer(getSelected(),Db,theme));

                list2.getColumnModel().getColumn(2).setCellRenderer(new MessageTableRenderer(getSelected(),Db,theme));
                list2.getTableHeader().repaint();
                list2.repaint();
                list2.scrollRectToVisible(list2.getCellRect(list2.getRowCount()-1,0,true));
                for (Object conv:convoModel.toArray()){
                    Contact cont = (Contact) conv;
                    if (!Db.getAnnuaireList().contains(cont.login)) {
                        convoModel.removeElement(cont);                    	
                    }
                    if (getDb().ArrayHistorywithX(getDb().getownIP(),getDb().getIdbyLoginString(cont.login)).isEmpty() && getMapCM().get(Db.getIdbyLoginString(selected)).isClosed()){
                        convoModel.removeElement(cont);
                    }
                }
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

        list1.setCellRenderer(new AnnuaireRenderer(getDb(),theme));
        
        System.out.println("HEEEEY "+selected+getSelected());
        list2.getColumnModel().getColumn(0).setCellRenderer(new MessageTableRenderer(selected,Db,theme));
        list2.getColumnModel().getColumn(1).setCellRenderer(new MessageTableRenderer(getSelected(),Db,theme));

        list2.getColumnModel().getColumn(2).setCellRenderer(new MessageTableRenderer(getSelected(),Db,theme));
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
    private void $$$setupUI$$$() throws IOException {
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
        DatabaseManager.Message nvMsg = new DatabaseManager.Message(message,Db.getIdbyLoginString(idSender),getDb().getownIP(),ts);

        int idM = getDb().getMaxIdmessage()+1;
        getDb().insertmessage(idM,addr.getHostAddress(),getDb().getownIP(),message,ts);
        System.out.println("message***- "+ message);
        System.out.println("ideSender --- "+ idSender);
        System.out.println("getselected --- "+ getDb().getIdbyLoginString(getSelected()));


        if (idSender.equals(getSelected())){
            System.out.println("Id "+ getDb().getIdbyLoginString(getSelected()));
            System.out.println("Login "+ getSelected());
            messageModel.addRow(new Object[]{ nvMsg,emptyMsg,emptyMsg }) ;
        }
        if(!annuOuv.contains(idSender)){
            annuOuv.add(contactC.login);
            getConvoModel().addElement(contactC);
            
        }
    }
    /**
     * @noinspection ALL
     */
    public void changerTheme(){

        theme++;
        System.out.println(theme);
        if (theme%2==1){
            try{
                UIManager.setLookAndFeel(new FlatMacDarkLaf());
                SwingUtilities.updateComponentTreeUI(this);
                pack();
                list2.repaint();
                list2.getColumnModel().getColumn(0).setCellRenderer(new MessageTableRenderer(selected,Db,theme));
                list2.getColumnModel().getColumn(1).setCellRenderer(new MessageTableRenderer(getSelected(),Db,theme));

                list2.getColumnModel().getColumn(2).setCellRenderer(new MessageTableRenderer(getSelected(),Db,theme));
            } catch (UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }}
        else {
            try{
                UIManager.setLookAndFeel(new FlatMacLightLaf());
                SwingUtilities.updateComponentTreeUI(this);
                pack();
                list2.repaint();
                list2.getColumnModel().getColumn(0).setCellRenderer(new MessageTableRenderer(selected,Db,theme));
                list2.getColumnModel().getColumn(1).setCellRenderer(new MessageTableRenderer(getSelected(),Db,theme));

                list2.getColumnModel().getColumn(2).setCellRenderer(new MessageTableRenderer(getSelected(),Db,theme));

            } catch (UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }

        }

    }

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
    public int theme;
  public AnnuaireRenderer(DatabaseManager DbR,int theme){
      this.DbR=DbR;
      this.theme=theme;
  }
    public Component getListCellRendererComponent(JList list, Object value,int index,boolean isSelected, boolean cellHasFocus) {

        if (value instanceof MainForm.Contact){
            if (theme%2==0){
            setBackground(Color.white);
            setForeground(Color.black);}
            else{
                setBackground(Color.decode("#1E1E1E"));
                setForeground(Color.WHITE);
            }
            MainForm.Contact contactC = (MainForm.Contact) value;
            String contact = contactC.login;
            ArrayList<DatabaseManager.Message> msgArr = DbR.ArrayHistorywithX(DbR.getownIP(),DbR.getIdbyLoginString(contact) );
            setText(contact);
            if (contactC.unread){
                setBackground(Color.green.darker());
                setForeground(Color.WHITE);
            }else{
                if (theme%2==0){
                    setBackground(Color.white);
                    setForeground(Color.black);}
                else{
                    setBackground(Color.decode("#1E1E1E"));
                    setForeground(Color.WHITE);
                }
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
    public int theme;

    public MessageTableRenderer(String selected,DatabaseManager dbM,int theme){
        setOpaque(true);
        this.theme=theme;
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
                if(theme%2==0){
                    if (!msg.contenu.equals("")){
                    //DatabaseManager.Message msg = (DatabaseManager.Message) messAt;
                        if (col==2) {
                            list.getTableHeader().getColumnModel().getColumn(col).setHeaderValue(dbM.getPseudo());

                            //c.setHorizontalAlignment(SwingConstants.LEFT);
                            setHorizontalAlignment(SwingConstants.RIGHT);
                            setBackground(Color.BLUE.brighter());
                            // setPreferredSize((new Dimension(10,30)));
                            setForeground(Color.WHITE);

                        }
                        else if(col==0) {
                            //list.getTableHeader().getColumnModel().getColumn(col).setHeaderValue(selected);

                            setBackground(Color.GRAY);
                            setForeground(Color.WHITE);
                            setHorizontalAlignment(SwingConstants.LEFT);}

                        }
                    else if (col!=1){

                        setBackground(Color.WHITE);
                        //dark Color.decode(#1e1e1e)
                        setForeground(Color.WHITE);
                    }
                    else{
                        setBackground(Color.WHITE);
                        setForeground(Color.black);
                        setText(msg.date.toString().substring(0, 16));
                    }}

                else{
                    if (!msg.contenu.equals("")){
                        if (col==2) {
                            list.getTableHeader().getColumnModel().getColumn(col).setHeaderValue(dbM.getPseudo());

                            //c.setHorizontalAlignment(SwingConstants.LEFT);
                            setHorizontalAlignment(SwingConstants.RIGHT);
                            setBackground(Color.BLUE.brighter());
                            // setPreferredSize((new Dimension(10,30)));
                            setForeground(Color.WHITE);

                        }
                        else if(col==0) {
                            //list.getTableHeader().getColumnModel().getColumn(col).setHeaderValue(selected);

                            setBackground(Color.GRAY);
                            setForeground(Color.WHITE);
                            setHorizontalAlignment(SwingConstants.LEFT);}

                    } else if (col!=1){

                        setBackground(Color.decode("#1E1E1E"));
                        //dark Color.decode(#1e1e1e)
                        setForeground(Color.decode("#1E1E1E"));
                    }
                    else{
                        setBackground(Color.decode("#1E1E1E"));
                        setForeground(Color.WHITE);
                        setText(msg.date.toString().substring(0, 16));
                    }}
                    }


                if (isSelected) {
                    setBackground(getBackground().darker());/*
                    if (msg.contenu.equals("") && col!=1 && col!=2){
                        setText(msg.date.toString());}

*/
                }


            return this;
}}