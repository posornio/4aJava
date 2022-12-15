import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActivityPseudo extends JFrame {
    private String pseudo;
    private MainForm mf;

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

        // Create a new JFrame with the title "Login Page"

        ConversationManager cm = new ConversationManager();
        //Connection connection = Db.conn;
        this.pseudo = Db.getPseudo();
        JFrame frame = new JFrame("Changer Pseudo");
        setVisible(false);

        // Set the size of the frame
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        // Create a panel to hold the components
        JPanel panel = new JPanel();

        // Add the panel to the frame
        add(panel);

        // Set the layout of the panel to GridLayout
        setLayout(new GridLayout(3, 1));

        // Create labels for the username and password fields
        JLabel currentPseudo = new JLabel("Pseudo actuel :"+ Db.getPseudo());

        JLabel usernameLabel = new JLabel("Nouveau psuedo: ");


        // Create text fields for the username and password
        JTextField usernameField = new JTextField();

        // Create a login button
        JButton loginButton = new JButton("Changer de pseudonyme");

        // Add the labels, fields, and button to the panel
        add(currentPseudo);
        currentPseudo.setHorizontalAlignment(SwingConstants.CENTER);

        add(usernameLabel);
        usernameLabel.setSize(new Dimension(100,10));
        add(usernameField);
        usernameField.setSize(new Dimension(100,2));
        add(loginButton);
        loginButton.setSize(new Dimension(100,50));
        loginButton.setMaximumSize(new Dimension(100,1));

        // Show the frame

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                //Db.changerPseudo(usernameField.getText());
                Db.setPseudo(usernameField.getText());
                System.out.println(usernameField.getText());
                System.out.println(Db.getPseudo());
                //ajout user dans DB
                mf.setVisible(true);
                currentPseudo.setText ("Pseudo actuel :"+ Db.getPseudo());

                mf.getChangerPseudoButt().setText(Db.getPseudo());





            }
        });
    }

    public static void main(String[] args) {
        ActivityPseudo activityPseudo = new ActivityPseudo();
    }
}


