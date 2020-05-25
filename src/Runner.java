import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;

/**
 *
 * This is the Runner class
 * It is the class that builds the challange interface
 *
 * sets the layout for all the features
 * It contains all the methods that perform the functionalities of the application
 *
 */
public class Runner extends JFrame implements ActionListener {

    JButton submitButton, skipButton, goButton;
    JTextArea textArea, lineNo;
    JLabel quesText, suggBox;

    int score = 0, questionDifficulty = 0, numberOfFails = 0;

    float currDifficulty = -1, increasePoints = 0, penaltyPoints = 0;

    String question = "", solution = "", answer = "", usrOutput = "", desiredOutput = "";
    String doneQ = "", error = "", givenCode = "", lastCategory = "", currCategory = "";

    boolean addedOnce = true;
    boolean noCatLeft = false;
    /**
     *
     * This methods is used to write strings to files
     * It writes the files in the source folder
     *
     */
    public void writeToFile(String fileName, String toPrint) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("src/" + fileName);
        } catch (Exception ex) {
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(toPrint);
        printWriter.close();
    }
    /**
     *
     * This method writes the code given for the challange in the text area
     * It is used to the other methods that are supposed to do the same thing don't get too long
     *
     */
    public void writeMain(){
        textArea.setText(givenCode);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(textArea.getFont().deriveFont(15f));
    }
    /**
     *
     * This method implements the mechanism of selecting a new question
     * It searches through the allQuestions text fie
     * and selections the questions apropraitely
     *
     * It also implements the difficulty features, the category features and the numbering
     *
     */
    public void selectQuestion() {
        question = "";
        desiredOutput = "";
        solution = "";
        givenCode = "";
        String noCatLEft = "";
        String displayDifficulty = "";
        writeToFile("usrOutput.txt", "");
        try {
            String st, questionNumber = "";
            File file = new File("src/allQuestions.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            boolean selected = false;
            while ((st = br.readLine()) != null){
                if(st.contains("--------------------new--------------------") && !selected){
                    st = br.readLine();
                    questionNumber = st.substring(st.indexOf("Number-") + 7);
                    st = br.readLine();
                    questionDifficulty = Integer.parseInt(st.substring(st.indexOf("Difficulty-") + 11));
                    st = br.readLine();
                    currCategory = st.substring(st.indexOf("Category-") + 9);
                    st = br.readLine();
                    st = br.readLine();
                    System.out.println("AAAAA" + questionNumber);
                    System.out.println("AAAAA" + questionDifficulty);
                    System.out.println("AAAAA" + currCategory);
                }
                if(!doneQ.contains(questionNumber) && !selected &&
                        questionDifficulty == (int) currDifficulty && currCategory.equals(lastCategory)
                && !noCatLeft) {
                    System.out.println("BBB" + questionNumber);
                    System.out.println("BBB" + questionDifficulty);
                    System.out.println("BBB" + currCategory);
                    while(!st.contains("----------Question")){
                        givenCode += st + "\n";
                        st = br.readLine();
                    }
                    st = br.readLine();
                    while(!st.contains("----------Solution")){
                        question += st + "<br/>";
                        st = br.readLine();
                    }
                    st = br.readLine();
                    while(!st.contains("----------desiredOutput")){
                        solution += st;
                        st = br.readLine();
                    }
                    st = br.readLine();
                    while(!st.contains("--------------------end--------------------")){
                        desiredOutput += st;
                        st = br.readLine();
                    }
                    doneQ += questionNumber;
                    selected = true;
                }else if(!doneQ.contains(questionNumber) && !selected && questionDifficulty == (int) currDifficulty
                        && noCatLeft) {
                    System.out.println("CCC" + questionNumber);
                    System.out.println("CCC" + questionDifficulty);
                    System.out.println("CCC" + currCategory);
                    if(!lastCategory.equals("")){
                        noCatLEft = "<br/>For DEV: We couldn't find any more questions in the last category. Please, add more.";
                    }else{
                        noCatLEft = "";
                    }
                    while(!st.contains("----------Question")){
                        givenCode += st + "\n";
                        st = br.readLine();
                    }
                    st = br.readLine();
                    while(!st.contains("----------Solution")){
                        question += st + "<br/>";
                        st = br.readLine();
                    }
                    st = br.readLine();
                    while(!st.contains("----------desiredOutput")){
                        solution += st;
                        st = br.readLine();
                    }
                    st = br.readLine();
                    while(!st.contains("--------------------end--------------------")){
                        desiredOutput += st;
                        st = br.readLine();
                    }
                    doneQ += questionNumber;
                    selected = true;
                }
                else{
                    while(!st.contains("--------------------end--------------------")){
                        st = br.readLine();
                    }
                }
            }
        }
        catch(Exception e){}
        if((int) currDifficulty == 1){
            displayDifficulty = "Question Difficulty: EASY";
            if(question.equals("")){
                displayDifficulty += "<br/>There are no questions left at this difficulty , please add more";
            }
        }
        if((int) currDifficulty == 2) {
            displayDifficulty = "Question Difficulty: MEDIUM";
            if(question.equals("")){
                displayDifficulty += "<br/>There are no questions left at this difficulty , please add more";
            }
        }
        if((int) currDifficulty == 3) {
            displayDifficulty = "Question Difficulty: HARD";
            if(question.equals("")){
                displayDifficulty += "<br/>There are no questions left at this difficulty , please add more";
            }
        }
        displayDifficulty += noCatLEft;
        writeMain();
        writeToFile("curQuestion.txt", question);
        writeToFile("curSolution.txt", solution);
        writeToFile("desiredOutput.txt", desiredOutput);
        quesText.setText("<html>" + displayDifficulty + "<br/><br/>" + question + "</html>");
    }
    /**
     *
     * This methods is used to set the layout of the challange interface
     * It adds a textarea for the numbering of the rows as well
     *
     * It adds the main textarea where the student writes the code
     *
     * It takes all the actions for when buttons are pressed
     * It sets the position of where a question is displayed
     *
     */
    public void Layout() {
        writeToFile("usrOutput.txt", "");
        quesText = new JLabel("<html>" + question + "</html>", SwingConstants.CENTER);
        quesText.setFont(new Font("Courier", Font.BOLD,17));
        quesText.setForeground(Color.white);
        quesText.setBounds(10, 0, 840, 490);
        add(quesText);

        textArea = new JTextArea();
        textArea.setForeground(Color.BLACK);
        textArea.setBackground(Color.WHITE);
        textArea.setBounds(890, 20, 840, 880);
        textArea.setEditable(true);
        add(textArea);

        lineNo = new JTextArea("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\n16\n17\n18\n19" +
                "\n20\n21\n22\n23\n24\n25\n26\n27\n28\n29\n30\n31\n" +
                "32\n33\n34\n35\n36\n37\n38\n39\n40\n41\n42\n43\n44\n45\n46\n47\n48");
        lineNo.setForeground(Color.BLACK);
        lineNo.setBackground(Color.lightGray);
        lineNo.setBounds(860, 20, 30, 880);
        lineNo.setEditable(false);
        lineNo.setFont(textArea.getFont().deriveFont(15f));
        add(lineNo);

        suggBox = new JLabel("Go on and see if the wizard is going to recommend you something.");
        suggBox.setFont(new Font("Courier", Font.BOLD,17));
        suggBox.setForeground(Color.white);
        suggBox.setBounds(10, 490, 850, 490);
        add(suggBox);
        textArea.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
//                System.out.println("test");
//                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    System.out.println("up is pressed");
//                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
//                answer = textArea1.getText();
////                System.out.println(answer);
//                String[] lines = answer.split("\n");
//                String lastLine = lines[lines.length - 3];
                String lastLine = getLineOfTextArea(-3);
                String suggestion = Suggest(lastLine, 1);
                suggBox.setText("<html>" + suggestion + "</html>");
                System.out.println(usrOutput);
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter("src/curAnswer.txt");
                }
                catch(Exception ex){}
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.print(answer);
                printWriter.close();
            }
        });
        selectQuestion();
        if(noCatLeft == false && question.length() < 10){
            noCatLeft = true;
            selectQuestion();
        }
    }

    /**
     *
     * This method returns the row of the given line from the user's solution
     *
     */
    public String getLineOfTextArea(int lineNo){
        answer = textArea.getText();
        String[] lines = answer.split("\n");
        String line = "";
        if(lineNo < 0)
            line = lines[lines.length + lineNo];
        else
            line = lines[lineNo + 1];
        return line;
    }
    /**
     *
     * This method displays the errors in the suggestion box
     * It is also the method that obtains the errors from the
     * err.txt file
     *
     */
    public void displayError(){
        String error1 = readTxtFile("err.txt");
        String temp = "";
        if(!error1.equals("") && error1.contains("stderr"))
            temp = error1.substring(error1.indexOf("src\\Answer.java:") + 16, error1.indexOf(": error:"));
        String line = getLineOfTextArea(Integer.valueOf(temp) - 2);
        String suggestion = Suggest(line, 2);
        if(suggestion.equals("")){
            suggestion = error1.substring(0, 150);
        }
        suggBox.setText("<html>" + "Woah, there! <br/> We found an error on line " + temp +
                "<br/>" + "Here is how you can rewrite that piece of code:<br/>" +
                suggestion + "</html>");
    }
    /**
     *
     * This method implements the functionality of the suggestion box
     * It displays the tutorial found in the suggestion database
     *
     */
    public String Suggest(String line, int caller) {
        System.out.println(line);
        String lookingFor = "";
        if(caller == 1)
            lookingFor = "sugg---";
        if(caller == 2)
            lookingFor = "errr---";
        int optionNo = 0;
        String[] options = {"emptySTR", "if", "for", "while", "import",
                "System", "int ", "String", "float"};
        for(int i = 0; i < options.length; i++){
            if(line.contains(options[i])){
                optionNo = i;
            }
        }
        String result = "";
        try {
            File file = new File("src/suggestionsAndErrors.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            String str1;
            while ((str = br.readLine()) != null) {
                if (str.contains(lookingFor + options[optionNo])) {
                    result = str.substring(str.indexOf(lookingFor + options[optionNo]) + 14);
                    while ((str1 = br.readLine()) != null && !str1.contains("sugg---") &&
                            !str1.contains("errr---")){
                        result += "<br/>" + str1;
                    }
                }
//                else{
//                    result = "";
//                }
                System.out.println("THIS IS THE" + result);
            }
        }
        catch(Exception e){}
        System.out.println("THIS IS THE RESULTfdsddf" + result);
        return result;
    }
    /**
     *
     * This is the constructor of the Runner class
     *  It sets the resolution of the challange interface
     *
     */
    Runner(float dif, float qPerDiff){
        super("GCode");
        if(dif == (float)-1){
            currDifficulty = 1f;
            increasePoints = 1f / qPerDiff;
            penaltyPoints = 1f / 5;
            System.out.println(increasePoints);
        }
        else{
            currDifficulty = dif;
            increasePoints = 0;
        }

        System.out.println(currDifficulty);
        submitButton = new JButton("Submit");
        submitButton.setBounds(500, 910, 300, 40);
        add(submitButton);
        submitButton.addActionListener(this);
        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {submitButton.setBackground(Color.BLACK);}
            public void mouseExited(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(UIManager.getColor("control"));}
        });
        skipButton = new JButton("Skip this question");
        skipButton.setBounds(810, 910, 300, 40);
        add(skipButton);
        skipButton.addActionListener(this);
        skipButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                skipButton.setBackground(Color.BLACK);}
            public void mouseExited(java.awt.event.MouseEvent evt) {
                skipButton.setBackground(UIManager.getColor("control"));}
        });
        goButton = new JButton("GO!");
        goButton.setBounds(2010, 910, 300, 40);
        add(goButton);
        goButton.addActionListener(this);
        goButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {goButton.setBackground(Color.BLACK);}
            public void mouseExited(java.awt.event.MouseEvent evt) {
                goButton.setBackground(UIManager.getColor("control"));}
        });
        setLayout(null);
        Color c = new Color(36,48,64);
        c = new Color(60,81,101);
        getContentPane().setBackground(c);
        setLocationRelativeTo(null);
        setSize(1760, 990);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Layout();
    }
    public String readTxtFile(String fileName){
        String temp = "";
        File file = new File("src/" + fileName);
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null){
                temp += st;
            }
        }
        catch(Exception e1){}
        return temp;
    }
    /**
     *
     * This methods monitors the buttons that the user presses
     * It triggers the appropriate methods when those buttons are pressed
     *
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == submitButton){
            answer = textArea.getText();
            usrOutput = "";
            error = "";
            writeToFile("usrOutput.txt", "");
            System.out.println(stringSimilarity(solution, answer));
            compileAndExecute(solution, answer);
            usrOutput = readTxtFile("usrOutput.txt");
            desiredOutput = readTxtFile("desiredOutput.txt");
            score = stringSimilarity(desiredOutput, usrOutput);
            System.out.println("This is the score " + score);
            if(readTxtFile("err.txt").contains("stderr")){
                score = 0;
                displayError();
                goButton.setBounds(2010, 910, 300, 40);
            }
            else if(score == 100){
                noCatLeft = false;
                suggBox.setText("<html><div>That was great!<br/>You sco" +
                        "red 100 points in that one!<br/> Are you ready for the next question?<br/>" +
                        " Click on GO!</div></html>");
                if(addedOnce){
                    currDifficulty += increasePoints;
                    addedOnce = false;
                }
                if(currDifficulty >= 4f){
                    currDifficulty = 3f;
                }
                goButton.setBounds(1120, 910, 300, 40);
            }
            else if(readTxtFile("err.txt").contains("stdout")){
                suggBox.setText("<html><div>That wasn't perfect yet; Your output matched the desired" +
                        " output only by " + score + "%<br/><br/>" +
                        "This is because it printed: <br/>" + "\" " + usrOutput + " \"" + "<br/>" +
                        "<br/>instead of the desired output:<br/>" + "\" " + desiredOutput + " \"" + "<br/><br/>"
                        + "(this print doesn't include new lines)<br/>Go ahead and try again!</div></html>");
                currDifficulty -= penaltyPoints;
                if(currDifficulty <= 1f){
                    currDifficulty = 1f;
                }
                numberOfFails++;
                goButton.setBounds(2010, 910, 300, 40);
            }
            else if(readTxtFile("err.txt").equals("")){
                suggBox.setText("<html><div>Your program did not output anything." +
                        "<br/> Try printing something." +
                        "<br/> Also, follow the instructions in the question" +
                        "<br/> The only way to progrss to the next question is by printing the desired output</div></html>");
                goButton.setBounds(2010, 910, 300, 40);
            }
            writeToFile("Answer.java", "");
            System.out.println((int) currDifficulty);
            System.out.println(Math.round(currDifficulty));
            System.out.println(currDifficulty);
        }
        if(e.getSource() == skipButton){
            selectQuestion();
            if(noCatLeft == false && question.length() < 10){
                noCatLeft = true;
                selectQuestion();
            }
        }
        if(e.getSource() == goButton){
            if(numberOfFails > 0){
                lastCategory = currCategory;
            }else {
                lastCategory = "";
            }
            numberOfFails = 0;
            selectQuestion();
            if(noCatLeft == false && question.length() < 10){
                noCatLeft = true;
                selectQuestion();
            }
            goButton.setBounds(2010, 910, 300, 40);
            suggBox.setText("");
            addedOnce = true;
        }
    }
    /**
     *
     * This methods prints the lines that the java compiler outputs
     * It is a part of the execution process
     *
     */
    private void printLines(String cmd, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(cmd + " " + line);
            error += cmd + " " + line + "\n";
        }
        writeToFile("err.txt", error);
    }
    /**
     *
     * This is the method that sends the commands to the
     * virtual terminal that java creates
     * It is the main command for execution
     *
     */
    public void runProcess(String command) throws Exception {
        String tempS = "", str;
        Process pro = Runtime.getRuntime().exec(command);
        InputStreamReader isReader = new InputStreamReader(pro.getInputStream());
        BufferedReader reader = new BufferedReader(isReader);
        while((str = reader.readLine())!= null){
            tempS += str;
        }
        Process pro1 = Runtime.getRuntime().exec(command);
        printLines(command + " stdout:", pro1.getInputStream());
        printLines(command + " stderr:", pro1.getErrorStream());
        writeToFile("usrOutput.txt", tempS);
    }
    /**
     *
     * This method writes the needed commands to compile the java class
     * It writes them to the terminal using the runProcess method
     *
     */
    public void compileAndExecute(String s, String t) {
        writeToFile("Answer.java", t);
        try {
            runProcess("javac -cp src src/Answer.java");
            System.out.println("**********");
            runProcess("java -cp src Answer");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * This methods returns the string similarity between two strings
     * It is the method used for the grading mechanism
     *
     */
    public static int stringSimilarity(String string1, String string2) {
        int length1 = string1.length(), length2 = string2.length();
        if (length1 == 0) return 0;
        else if (length2 == 0) return 0;
        if (length1 > length2) {
            String temp = string1;
            string1 = string2;
            string2 = temp;
            length1 = length2;
            length2 = string2.length();
        }
        int arr1[] = new int[length1 + 1], arr2[] = new int[length1 + 1], arr3[], i, j, difference;
        char char1;
        for (i = 0; i <= length1; i++)
            arr1[i] = i;
        for (j = 1; j <= length2; j++) {
            char1 = string2.charAt(j - 1);
            arr2[0] = j;
            for (i = 1; i <= length1; i++) {
                difference = string1.charAt(i - 1) == char1 ? 0 : 1;
                arr2[i] = Math.min(Math.min(arr2[i - 1] + 1, arr1[i] + 1), arr1[i - 1] + difference);
            }
            arr3 = arr1;
            arr1 = arr2;
            arr2 = arr3;
        }
        float length = string2.length();
        float score = ((length - (float) arr1[length1]) / length) * (float)100;
        return (int)score;
    }
}