import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class SwingAssignment extends JFrame implements ActionListener {

    JTextField namee = new JTextField(3);
    JComboBox cb = new JComboBox(new String[]{"Male", "Female"});
    JTextField fathernamee = new JTextField(3);
    JPasswordField  password = new JPasswordField(3);
    JPasswordField  cpassword = new JPasswordField(3);
    JTextField cityy = new JTextField(3);
    JTextField emaill = new JTextField(3);
    JFrame p = new JFrame("SwingAssignment");
    JButton b = new JButton("REGISTER");
    JButton b1 = new JButton("RESET");

    SwingAssignment() {
        super("SwingAssignment");
        p.getContentPane().setBackground(Color.YELLOW);
        cb.setBounds(200, 60, 200, 30);
        p.add(cb);
        JLabel[] labels = {new JLabel("NAME"), new JLabel("GENDER"), new JLabel("FATHERNAME"),
                new JLabel("PASSWORD"), new JLabel("CONFIRM PASSWORD"),
                new JLabel("CITY"), new JLabel("Email")};
        JComponent[] fields = {namee, cb, fathernamee, password, cpassword, cityy, emaill};
        for (int i = 0; i < labels.length; i++) {
            labels[i].setBounds(10, 10 + 50 * i, 150, 30);
            fields[i].setBounds(200, 10 + 50 * i, 200, 30);
            p.add(labels[i]);
            p.add(fields[i]);
        }
        b.setBounds(100, 400,100,30);
        b1.setBounds(300, 400,100,30);
        p.add(b);
        p.add(b1);
        b.addActionListener(this);
        b1.addActionListener(this);

        p.setLayout(null);
        p.setVisible(true);
        p.setSize(600, 500);
        p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b) {
            // register button action
        }
        else if (e.getSource() == b1) {
            // reset button action
        }
    }

    public static void main(String[] args) {
        SwingAssignment sa = new SwingAssignment();
    }
}
