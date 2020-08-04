import java.awt.event.*;
import javax.swing.JOptionPane;

public class AddVertexListener implements ActionListener {
    GraphEditor iParent;

    public AddVertexListener(GraphEditor pParent) {
	iParent = pParent;
    }

    public void actionPerformed(ActionEvent e) {
	iParent.addVertex();
    }
}
