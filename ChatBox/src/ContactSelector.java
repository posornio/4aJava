import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import javax.swing.event.ListSelectionListener;

public class ContactSelector extends JFrame {
    private JList<Object> contactList;
    private String contactChoisi = "" ;

    public String getContactChoisi() {
        return contactChoisi;
    }

    public void setContactChoisi(String contactChoisi) {
        this.contactChoisi = contactChoisi;
    }

    public DefaultListModel<String> getListModel() {
        return listModel;
    }

    public MainForm getMf() {
        return mf;
    }

    public void setMf(MainForm mf) {
        this.mf = mf;
    }

    public ConversationManager getCm() {
        return cm;
    }

    public void setCm(ConversationManager cm) {
        this.cm = cm;
    }

    private MainForm mf;
    private ConversationManager cm;

    public void setListModel(DefaultListModel<String> listModel) {
        this.listModel = listModel;
    }

    private DefaultListModel<String> listModel;

    public ContactSelector(){}
    public ContactSelector(MainForm mainForm,DatabaseManager Db) {
        mf=mainForm;

        setVisible(false);
        setTitle("Annuaire");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLayout(new GridLayout(1, 1));

        ArrayList<String> asAnnu = Db.getAnnuaireList();
        System.out.println(asAnnu);
        setSize(800, 600);
        DefaultListModel messageModel = new DefaultListModel();


        contactList = new JList<>(asAnnu.toArray());

        add(new JScrollPane(contactList), BorderLayout.CENTER);
        //JButton loginButton = new JButton("Selectionner Contact");
        //add(loginButton);
        setSize(800, 600);

        setLocationRelativeTo(null);



        contactList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                contactChoisi = asAnnu.get(((ListSelectionModel) e.getSource()).getSelectedIndices()[0]);
                setContactChoisi(contactChoisi);
                setVisible(false);
                //notifyAll();
                mf.setSelectAnnu(contactChoisi);
                if(!mf.getConvoModel().contains(contactChoisi)){
                    mf.getConvoModel().addElement(contactChoisi);
                    try {
                        ThreadInitConnexionsTCP tI = new ThreadInitConnexionsTCP(InetAddress.getByName(contactChoisi));
                        tI.start();
                        tI.join();
                        setCm(tI.getcm());
                        mf.getMapCM().put(contactChoisi,tI.getcm());/*
                    if(!mf.getConvoModel().contains(contactChoisi)){
                        mf.getMapCM().put(contactChoisi,tI.getcm());
                        mf.getConvoModel().addElement(contactChoisi);
                    }*/

                    } catch (UnknownHostException ex) {
                        throw new RuntimeException(ex);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                }


                //System.out.println(contactChoisi);
                //messageModel = initLM(histWX);
                //messageModel = new DefaultListModel();
            }
        });
    }

    public static void main(String[] args) {

        ContactSelector contactSelector = new ContactSelector() ;

    }


}
