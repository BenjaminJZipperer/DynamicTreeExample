package webstartComponentArch;
import javax.swing.JFrame;
/**
 * How many Chances can anyone have
 * @author CC-Student
 *
 */
public class DynamicTreeApplication extends JFrame {
	 public static void main(String [] args) {
	        DynamicTreeApplication app = new DynamicTreeApplication();
	        app.createGUI();
	    }

	    private void createGUI() {
	        //Create and set up the content pane.
	        DynamicTreePanel newContentPane = new DynamicTreePanel();
	        newContentPane.setOpaque(true); 
	        setContentPane(newContentPane);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        pack();
	        setVisible(true);
	    }    
}
