import javax.swing.*;

/**
 * @author WeiYi Yu
 * @date 2019-08-26
 */
public class Display {

    public static void main(String[] args){
        JFrame f = new JFrame();

        JButton b = new JButton();

        b.setBounds(130, 100, 100, 40);

        f.add(b);

        f.setSize(400, 500);
        f.setLayout(null);
        f.setVisible(true);
    }
}
