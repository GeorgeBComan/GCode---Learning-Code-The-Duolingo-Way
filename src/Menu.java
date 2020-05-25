import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Menu extends JFrame implements ActionListener {
    JButton adaptiveM, allEasy, allMedi, allHard, help;
    JTextArea qBetweenDif;
    JLabel text;
    Menu(){
        super("Menu");
        text = new JLabel("<html>" + "Set how many questions need to be solved between difficulties." +
                "<br/> The default value is 2."
                + "</html>", SwingConstants.CENTER);
        text.setFont(new Font("Courier", Font.BOLD,17));
        text.setForeground(Color.white);
        text.setBounds(10, 10, 270, 100);
        add(text);

        qBetweenDif = new JTextArea();
        qBetweenDif.setForeground(Color.BLACK);
        qBetweenDif.setBackground(Color.WHITE);
        qBetweenDif.setBounds(290, 45, 40, 40);
        qBetweenDif.setEditable(true);
        qBetweenDif.setText("2");
        qBetweenDif.setFont(new Font("Courier", Font.BOLD,30));
        add(qBetweenDif);

        adaptiveM = new JButton("Adaptive mode");
        adaptiveM.setBounds(50, 120, 300, 40);
        add(adaptiveM);
        adaptiveM.addActionListener(this);
        allEasy = new JButton("Only Easy Questions");
        allEasy.setBounds(50, 190, 300, 40);
        add(allEasy);
        allEasy.addActionListener(this);
        allMedi = new JButton("Only Medium Questions");
        allMedi.setBounds(50, 260, 300, 40);
        add(allMedi);
        allMedi.addActionListener(this);
        allHard = new JButton("Only Hard Questions");
        allHard.setBounds(50, 330, 300, 40);
        add(allHard);
        allHard.addActionListener(this);
        help = new JButton("Help");
        help.setBounds(50, 400, 300, 40);
        add(help);
        help.addActionListener(this);
        setLayout(null);
        Color c = new Color(46,83,158);
        c = new Color(60,81,101);
        getContentPane().setBackground(c);
        setLocationRelativeTo(null);
        setSize(400, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == adaptiveM){
            String numberOfQuestionPerDiff = qBetweenDif.getText();
            if(numberOfQuestionPerDiff.equals("") || Float.valueOf(numberOfQuestionPerDiff) <= 0){
                numberOfQuestionPerDiff = "2";
            }
            new Runner(-1f, Float.valueOf(numberOfQuestionPerDiff));
        }
        if(e.getSource() == allEasy){
            new Runner(1f, 0);
        }
        if(e.getSource() == allMedi){
            new Runner(2f, 0);
        }
        if(e.getSource() == allHard){
            new Runner(3f, 0);
        }
        if(e.getSource() == help){
            setSize(400, 830);
            JLabel label0 = new JLabel("<html>" + "Name of the app: GCode<br/><br/>" +
                    "This is an explanation for the app and " +
                    "a guide on how to use it<br/>"  +
                    "What each of the buttons do:<br/><br/>" +
                    "Adaptive mode start from 0 difficulty and tailors the challenges for your experience level<br/>" +
                    "This is the mode the app was designed to be used in<br/><br/>" +
                    "Only ***** questions makes the difficulty level set to easy, medium or hard <br/><br/>" +
                    "This way, you can always go to the difficulty you desire and skip the others<br/><br/>" +
                    "</html>", SwingConstants.CENTER);
            label0.setFont(new Font("Courier", Font.BOLD,15));
            label0.setForeground(Color.white);
            label0.setBounds(10, 380, 380, 500);
            add(label0);
        }
    }
}
