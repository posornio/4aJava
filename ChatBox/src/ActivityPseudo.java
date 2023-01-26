import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

public class ActivityPseudo extends JFrame {
    private String pseudo;
    private MainForm mf;

    JTextField usernameField;

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public MainForm getMf() {
        return mf;
    }

    public void setMf(MainForm mf) {
        this.mf = mf;
    }
    public ActivityPseudo(){}
    public ActivityPseudo(MainForm mf,DatabaseManager Db){


        ConversationManager cm = new ConversationManager();
        //Connection connection = Db.conn;
        this.pseudo = Db.getPseudo();
        JFrame frame = new JFrame("Changer Pseudo");
        setVisible(false);
        AccountManager accountManager = new AccountManager();
        setSize(400, 200);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();

        add(panel);

        setLayout(new FlowLayout());
        Box box = Box.createVerticalBox();

        JLabel currentPseudo = new JLabel("Pseudo actuel :"+ Db.getPseudo());

        JLabel usernameLabel = new JLabel("Nouveau psuedo: ");



        usernameField = new JTextField("Nouveau psuedo: ");

        JButton loginButton = new JButton("Changer de pseudonyme");
        JButton themeButton = new JButton("Changer de theme");
        box.add(currentPseudo);
        currentPseudo.setHorizontalAlignment(SwingConstants.CENTER);

        //add(usernameLabel);
        usernameLabel.setSize(new Dimension(100,10));
        box.add(usernameField);
        usernameField.setSize(new Dimension(900,2));
        box.add(loginButton);
        box.add(themeButton);
        box.setAlignmentX(5);
        box.setAlignmentY(5);

        add(box);
        loginButton.setSize(new Dimension(100,50));
        themeButton.setSize(new Dimension(100,50));
        getRootPane().setDefaultButton(loginButton);



        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!accountManager.seconnecter(Db.getIdbyLoginString(usernameField.getText()),usernameField.getText())){
                    JOptionPane.showMessageDialog(null,
                            "Le pseudo est deja pris",
                            "Alert",
                            JOptionPane.WARNING_MESSAGE);
                }else{

                setVisible(false);
                //Db.changerPseudo(usernameField.getText());
                Db.setPseudo(usernameField.getText());
                System.out.println(usernameField.getText());
                System.out.println(Db.getPseudo());
                //ajout user dans DB
                mf.setVisible(true);
                currentPseudo.setText ("Pseudo actuel :"+ Db.getPseudo());

                mf.getChangerPseudoButt().setText(Db.getPseudo());
                mf.getList2().getTableHeader().getColumnModel().getColumn(2).setHeaderValue(Db.getPseudo());
                mf.getList2().getTableHeader().repaint();
                ThreadEnvoiAnnuaire envoiAnnuaire = new ThreadEnvoiAnnuaire(usernameField.getText());
                envoiAnnuaire.start();}
                for (Map.Entry<String,ConversationManager> element : mf.getMapCM().entrySet()){
                    try{ element.getValue().sendmessage("**ExitClavardage**");}
                    catch(Exception excep){
                        System.out.println("Error closing connection " + element.getKey() + "with " + excep);
                    }
                    element.getValue().closeconnection();
                }





            }
        });
        themeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mf.changerTheme();
                mf.setVisible(true);
                setVisible(false);
            }
        });

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
               mf.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        ActivityPseudo activityPseudo = new ActivityPseudo();

        try{
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        //myForm.getRootPane()



    }
}


