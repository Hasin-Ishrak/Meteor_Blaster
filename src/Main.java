import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import components.panel;


public class Main extends JFrame {

    public Main(){
        init();
    }
    private void init(){
        
        setTitle("Meteor_Blaster");
        setSize(1000,720);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        panel panel=new panel();
        add(panel);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                panel.start();
            }
        });

    }
   public static void main(String[] args) {

       Main main =new Main();
       main.setVisible(true);

   }
}
