import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActivityMain {


        public static void main(String[] args) {
            // Create a new JFrame with the title "Login Page"
            JFrame frame = new JFrame("Login Page");

            // Set the size of the frame
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            // Create a panel to hold the components
            JPanel panel = new JPanel();

            // Add the panel to the frame
            frame.add(panel);

            // Set the layout of the panel to GridLayout
            panel.setLayout(new GridLayout(2, 2));

            // Create labels for the username and password fields
            JLabel usernameLabel = new JLabel("Username:");


            // Create text fields for the username and password
            JTextField usernameField = new JTextField();

            // Create a login button
            JButton loginButton = new JButton("Login");

            // Add the labels, fields, and button to the panel
            panel.add(usernameLabel);
            panel.add(usernameField);
            panel.add(loginButton);

            // Show the frame
            frame.setVisible(true);

            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                    MainForm myForm = new MainForm();
                    //ajout user dans DB

                }
            });
        }
    }


