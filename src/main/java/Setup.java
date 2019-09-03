
import javax.swing.JFrame;

public class Setup
        extends JFrame {
//
//    // private static JFrame frame = new JFrame("Setup");
//
//    private JTextField L1TopTxtFld;
//    private JTextField L1BotTxtFld;
//    private JTextField L2TopTxtFld;
//    private JTextField L2BotTxtFld;
//    private JTextField L3TopTxtFld;
//    private JTextField L3BotTxtFld;
//    private JTextField L4TopTxtFld;
//    private JTextField L4BotTxtFld;
//    private JTextField L5TopTxtFld;
//    private JTextField L5BotTxtFld;
//    private JTextField S1HeadTxtFld;
//    private JTextField S1TailTxtFld;
//    private JTextField S2HeadTxtFld;
//    private JTextField S2TailTxtFld;
//    private JTextField S3HeadTxtFld;
//    private JTextField S3TailTxtFld;
//    private JTextField S4HeadTxtFld;
//    private JTextField S4TailTxtFld;
//    private JTextField S5HeadTxtFld;
//    private JTextField S5TailTxtFld;
//
//    private boolean start = false;
//    private JTextArea errorTxtA;
//
//    public Setup(SLGame slg) {
//        // public Setup(Board bd) {
//
//        // public Setup() {
//        // myPanel.setLayout(null);
//
//        JPanel myPanel = new JPanel();
//        myPanel.setLayout(null);
//
//        JTextArea rulesTxtA = new JTextArea();
//        rulesTxtA.setLineWrap(true);
//        rulesTxtA.setWrapStyleWord(true);
//        rulesTxtA.setFont(new Font("Arial", Font.PLAIN, 14));
//        rulesTxtA.setBounds(10, 11, 230, 578);
//        rulesTxtA.setText("Rules:\n"
//                                  +
//                                  "-The difference between snakes and ladder end-points (Sanke head and tail or " +
//                                  "ladder top and base) cannot be more than 30.\n"
//                                  +
//                                  "-The ladder top or base cannot be placed on a snake’s head location and a ladder " +
//                                  "base cannot be placed on top of another ladder’s head.\n"
//                                  +
//                                  "-A snake’s head cannot be placed on top of an existing ladder head, ladder base or" +
//                                  " next to another snake’s head.\n"
//                                  + "-Only one snake head can be in locations 81 to 100 at any one time.\n"
//                                  +
//                                  "-There should be no ladder base at location 1 and no ladder top at location 100.\n");
//        myPanel.add(rulesTxtA);
//
//        JLabel lblLadder = new JLabel("com.snakehunter.model.Ladder");
//        lblLadder.setBounds(285, 0, 46, 14);
//        myPanel.add(lblLadder);
//
//        JLabel lblTop = new JLabel("Top");
//        lblTop.setBounds(341, 0, 37, 14);
//        myPanel.add(lblTop);
//
//        JLabel lblBottom = new JLabel("Bottom");
//        lblBottom.setBounds(402, 0, 50, 14);
//        myPanel.add(lblBottom);
//
//        JLabel lblSnake = new JLabel("Snake");
//        lblSnake.setBounds(514, 0, 46, 14);
//        myPanel.add(lblSnake);
//
//        JLabel lblHead = new JLabel("Head");
//        lblHead.setBounds(570, 0, 53, 14);
//        myPanel.add(lblHead);
//
//        JLabel lblTail = new JLabel("Tail");
//        lblTail.setBounds(633, 0, 44, 14);
//        myPanel.add(lblTail);
//
//        JLabel lblNewLabel = new JLabel("1");
//        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
//        lblNewLabel.setBounds(250, 28, 46, 14);
//        myPanel.add(lblNewLabel);
//
//        L1TopTxtFld = new JTextField();
//        L1TopTxtFld.setText("10");
//        L1TopTxtFld.setBounds(328, 25, 50, 20);
//        myPanel.add(L1TopTxtFld);
//        L1TopTxtFld.setColumns(10);
//
//        L1BotTxtFld = new JTextField();
//        L1BotTxtFld.setText("1");
//        L1BotTxtFld.setBounds(402, 25, 50, 20);
//        myPanel.add(L1BotTxtFld);
//        L1BotTxtFld.setColumns(10);
//
//        JLabel label = new JLabel("2");
//        label.setHorizontalAlignment(SwingConstants.RIGHT);
//        label.setBounds(250, 65, 46, 14);
//        myPanel.add(label);
//
//        L2TopTxtFld = new JTextField();
//        L2TopTxtFld.setText("100");
//        L2TopTxtFld.setColumns(10);
//        L2TopTxtFld.setBounds(328, 65, 50, 20);
//        myPanel.add(L2TopTxtFld);
//
//        L2BotTxtFld = new JTextField();
//        L2BotTxtFld.setText("10");
//        L2BotTxtFld.setColumns(10);
//        L2BotTxtFld.setBounds(402, 65, 50, 20);
//        myPanel.add(L2BotTxtFld);
//
//        JLabel label_1 = new JLabel("3");
//        label_1.setHorizontalAlignment(SwingConstants.RIGHT);
//        label_1.setBounds(250, 96, 46, 14);
//        myPanel.add(label_1);
//
//        L3TopTxtFld = new JTextField();
//        L3TopTxtFld.setColumns(10);
//        L3TopTxtFld.setBounds(328, 96, 50, 20);
//        myPanel.add(L3TopTxtFld);
//
//        L3BotTxtFld = new JTextField();
//        L3BotTxtFld.setText("48");
//        L3BotTxtFld.setColumns(10);
//        L3BotTxtFld.setBounds(402, 96, 50, 20);
//        myPanel.add(L3BotTxtFld);
//
//        JLabel label_2 = new JLabel("4");
//        label_2.setHorizontalAlignment(SwingConstants.RIGHT);
//        label_2.setBounds(250, 141, 46, 14);
//        myPanel.add(label_2);
//
//        L4TopTxtFld = new JTextField();
//        L4TopTxtFld.setText("59");
//        L4TopTxtFld.setColumns(10);
//        L4TopTxtFld.setBounds(328, 141, 50, 20);
//        myPanel.add(L4TopTxtFld);
//
//        L4BotTxtFld = new JTextField();
//        L4BotTxtFld.setText("82");
//        L4BotTxtFld.setColumns(10);
//        L4BotTxtFld.setBounds(402, 141, 50, 20);
//        myPanel.add(L4BotTxtFld);
//
//        JLabel label_3 = new JLabel("5");
//        label_3.setHorizontalAlignment(SwingConstants.RIGHT);
//        label_3.setBounds(250, 197, 46, 14);
//        myPanel.add(label_3);
//
//        L5TopTxtFld = new JTextField();
//        L5TopTxtFld.setText("60");
//        L5TopTxtFld.setColumns(10);
//        L5TopTxtFld.setBounds(328, 197, 50, 20);
//        myPanel.add(L5TopTxtFld);
//
//        L5BotTxtFld = new JTextField();
//        L5BotTxtFld.setText("35");
//        L5BotTxtFld.setColumns(10);
//        L5BotTxtFld.setBounds(402, 197, 50, 20);
//        myPanel.add(L5BotTxtFld);
//
//        JLabel label_4 = new JLabel("1");
//        label_4.setHorizontalAlignment(SwingConstants.RIGHT);
//        label_4.setBounds(475, 25, 46, 14);
//        myPanel.add(label_4);
//
//        S1HeadTxtFld = new JTextField();
//        S1HeadTxtFld.setText("19");
//        S1HeadTxtFld.setColumns(10);
//        S1HeadTxtFld.setBounds(553, 25, 50, 20);
//        myPanel.add(S1HeadTxtFld);
//
//        S1TailTxtFld = new JTextField();
//        S1TailTxtFld.setText("9");
//        S1TailTxtFld.setColumns(10);
//        S1TailTxtFld.setBounds(627, 25, 50, 20);
//        myPanel.add(S1TailTxtFld);
//
//        JLabel label_5 = new JLabel("2");
//        label_5.setHorizontalAlignment(SwingConstants.RIGHT);
//        label_5.setBounds(475, 65, 46, 14);
//        myPanel.add(label_5);
//
//        S2HeadTxtFld = new JTextField();
//        S2HeadTxtFld.setText("82");
//        S2HeadTxtFld.setColumns(10);
//        S2HeadTxtFld.setBounds(553, 65, 50, 20);
//        myPanel.add(S2HeadTxtFld);
//
//        S2TailTxtFld = new JTextField();
//        S2TailTxtFld.setText("50");
//        S2TailTxtFld.setColumns(10);
//        S2TailTxtFld.setBounds(627, 65, 50, 20);
//        myPanel.add(S2TailTxtFld);
//
//        JLabel label_6 = new JLabel("3");
//        label_6.setHorizontalAlignment(SwingConstants.RIGHT);
//        label_6.setBounds(475, 96, 46, 14);
//        myPanel.add(label_6);
//
//        S3HeadTxtFld = new JTextField();
//        S3HeadTxtFld.setText("85");
//        S3HeadTxtFld.setColumns(10);
//        S3HeadTxtFld.setBounds(553, 96, 50, 20);
//        myPanel.add(S3HeadTxtFld);
//
//        S3TailTxtFld = new JTextField();
//        S3TailTxtFld.setText("44");
//        S3TailTxtFld.setColumns(10);
//        S3TailTxtFld.setBounds(627, 96, 50, 20);
//        myPanel.add(S3TailTxtFld);
//
//        JLabel label_7 = new JLabel("4");
//        label_7.setHorizontalAlignment(SwingConstants.RIGHT);
//        label_7.setBounds(475, 141, 46, 14);
//        myPanel.add(label_7);
//
//        S4HeadTxtFld = new JTextField();
//        S4HeadTxtFld.setText("22");
//        S4HeadTxtFld.setColumns(10);
//        S4HeadTxtFld.setBounds(553, 141, 50, 20);
//        myPanel.add(S4HeadTxtFld);
//
//        S4TailTxtFld = new JTextField();
//        S4TailTxtFld.setText("54");
//        S4TailTxtFld.setColumns(10);
//        S4TailTxtFld.setBounds(627, 141, 50, 20);
//        myPanel.add(S4TailTxtFld);
//
//        JLabel label_8 = new JLabel("5");
//        label_8.setHorizontalAlignment(SwingConstants.RIGHT);
//        label_8.setBounds(475, 197, 46, 14);
//        myPanel.add(label_8);
//
//        S5HeadTxtFld = new JTextField();
//        S5HeadTxtFld.setText("27");
//        S5HeadTxtFld.setColumns(10);
//        S5HeadTxtFld.setBounds(553, 197, 50, 20);
//        myPanel.add(S5HeadTxtFld);
//
//        S5TailTxtFld = new JTextField();
//        S5TailTxtFld.setColumns(10);
//        S5TailTxtFld.setBounds(627, 197, 50, 20);
//        myPanel.add(S5TailTxtFld);
//
//        JButton btnStart = new JButton("Check");
//        btnStart.addActionListener(e -> {
//            slg.setUpValidation(getSetup());
//            slg.control();
//            //bd.repaint();
//            start = true;
//            System.out.println(start);
//        });
//        btnStart.setBounds(432, 228, 89, 23);
//        myPanel.add(btnStart);
//
//		/*frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(520, 360);
//		frame.setVisible(true);
//		*/
//
//        errorTxtA = new JTextArea();
//        errorTxtA.setFont(new Font("Arial", Font.PLAIN, 14));
//        errorTxtA.setLineWrap(true);
//        errorTxtA.setWrapStyleWord(true);
//        errorTxtA.setBounds(687, 11, 224, 578);
//        myPanel.add(errorTxtA);
//
//        UIManager.put("OptionPane.minimumSize", new Dimension(940, 550));
//        Object[] options = {"Start", "Exit"};
//        int mainMenu = JOptionPane.showOptionDialog(null, myPanel, "Setup", JOptionPane.YES_NO_OPTION,
//                                                    JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
//
//        if (mainMenu == JOptionPane.YES_OPTION) {
//
//            boolean test = false;
//            // if (slg.setUpValidation())
//
//            // if (!(test))
//
//            // start = true;
//        }
//
//        if (mainMenu == JOptionPane.NO_OPTION || mainMenu == JOptionPane.CLOSED_OPTION) {
//            System.exit(0);
//        }
//
//    }
//
//    public Setup getSetup() {
//        return this;
//    }
//
//    public void setErrorText(String errorText) {
//        errorTxtA.setText(errorText);
//    }
//
//    public JTextField getL1TopTxtFld() {
//        return L1TopTxtFld;
//    }
//
//    public JTextField getL1BotTxtFld() {
//        return L1BotTxtFld;
//    }
//
//    public JTextField getL2TopTxtFld() {
//        return L2TopTxtFld;
//    }
//
//    public JTextField getL2BotTxtFld() {
//        return L2BotTxtFld;
//    }
//
//    public JTextField getL3TopTxtFld() {
//        return L3TopTxtFld;
//    }
//
//    public JTextField getL3BotTxtFld() {
//        return L3BotTxtFld;
//    }
//
//    public JTextField getL4TopTxtFld() {
//        return L4TopTxtFld;
//    }
//
//    public JTextField getL4BotTxtFld() {
//        return L4BotTxtFld;
//    }
//
//    public JTextField getL5TopTxtFld() {
//        return L5TopTxtFld;
//    }
//
//    public JTextField getL5BotTxtFld() {
//        return L5BotTxtFld;
//    }
//
//    public JTextField getS1HeadTxtFld() {
//        return S1HeadTxtFld;
//    }
//
//    public JTextField getS1TailTxtFld() {
//        return S1TailTxtFld;
//    }
//
//    public JTextField getS2HeadTxtFld() {
//        return S2HeadTxtFld;
//    }
//
//    public JTextField getS2TailTxtFld() {
//        return S2TailTxtFld;
//    }
//
//    public JTextField getS3HeadTxtFld() {
//        return S3HeadTxtFld;
//    }
//
//    public JTextField getS3TailTxtFld() {
//        return S3TailTxtFld;
//    }
//
//    public JTextField getS4HeadTxtFld() {
//        return S4HeadTxtFld;
//    }
//
//    public JTextField getS4TailTxtFld() {
//        return S4TailTxtFld;
//    }
//
//    public JTextField getS5HeadTxtFld() {
//        return S5HeadTxtFld;
//    }
//
//    public JTextField getS5TailTxtFld() {
//        return S5TailTxtFld;
//    }
//
//    public boolean getStart() {
//        return start;
//    }
}
