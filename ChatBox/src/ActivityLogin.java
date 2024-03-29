import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;

public class ActivityLogin extends JFrame {

	private boolean first_time = true;



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
        usernameField.setForeground(Color.GRAY);
        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Username")) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setForeground(Color.GRAY);
                    usernameField.setText("Username");
                }
            }
        });


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
        getContentPane().requestFocusInWindow();



        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //setVisible(false);     
            	if (first_time) {
            		ThreadEcouteConnexionsUDP ecouteConnexionsUDP = new ThreadEcouteConnexionsUDP();
                    ecouteConnexionsUDP.start();
                    first_time=false;
            	}
                ThreadEnvoiAnnuaire envoiAnnuaire = new ThreadEnvoiAnnuaire("**pseudodelamortquituequepesonnen'aledroitdeprendreaunquelconquemoment**");
                envoiAnnuaire.start();
                try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                System.out.println("\n\n\n\nL'adresse ip de patrick est censée etre : ");
                Db.getIdByLogin("Username");
                if(!accountManager.seconnecter(Db.getownIP(),usernameField.getText()))
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
                
                ThreadEnvoiAnnuaire envoiAnnuaire2 = new ThreadEnvoiAnnuaire(usernameField.getText());
                envoiAnnuaire2.start();
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


