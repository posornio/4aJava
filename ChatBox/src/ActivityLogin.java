import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;

public class ActivityLogin extends JFrame {





    public ActivityLogin(){
        //UIManager.setLookAndFeel(com.intellij.uiDesigner.core.);
        DatabaseManager Db =new DatabaseManager();
        // Create a new JFrame with the title "Login Page"

        ConversationManager cm = new ConversationManager();
        Db.dbinit();
        Db.dropTable();
        AccountManager accountManager = new AccountManager();

        JFrame frame = new JFrame("Login Page");

        setSize(400, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();


        add(panel);

        setLayout(new FlowLayout());

        JLabel usernameLabel = new JLabel("Username:");


        JTextField usernameField = new JTextField("Username");

        JButton loginButton = new JButton("Login");

        Box box = Box.createVerticalBox();

        usernameLabel.setSize(new Dimension(100,10));
        box.add(usernameField);
        usernameField.setSize(new Dimension(100,2));
        box.add(loginButton);
        panel.add(box);
        loginButton.setSize(new Dimension(100,50));
        loginButton.setMaximumSize(new Dimension(100,1));
        getRootPane().setDefaultButton(loginButton);
        setVisible(true);




        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //setVisible(false);
                if(!accountManager.seconnecter(Db.getIdbyLoginString(usernameField.getText()),usernameField.getText()))
                {
                    JOptionPane.showMessageDialog(null,
                            "Le pseudo est deja pris",
                            "Alert",
                            JOptionPane.WARNING_MESSAGE);
                }
                else{

                //Db.insertuser("",usernameField.getText());

                Db.setPseudo(usernameField.getText()); 
                    MainForm myForm = null;
                    try {
                        myForm = new MainForm(Db);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    ThreadEcouteConnexionsTCP threadEcouteConnexionsTCP = new ThreadEcouteConnexionsTCP(myForm);
                threadEcouteConnexionsTCP.start();
                ThreadEcouteConnexionsUDP ecouteConnexionsUDP = new ThreadEcouteConnexionsUDP();
                ecouteConnexionsUDP.start();
                ThreadEnvoiAnnuaire envoiAnnuaire = new ThreadEnvoiAnnuaire(usernameField.getText());
                envoiAnnuaire.start();
                setVisible(false);
                }

                //ajout user dans DB


            }
        });


    }

        public static void main(String[] args) {
            ActivityLogin activityLogin = new ActivityLogin();
            try{
                UIManager.setLookAndFeel(new FlatMacLightLaf());
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
            //activityLogin.setVisible(true);
        }
    }


