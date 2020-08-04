import java.awt.event.*;
import javax.swing.JOptionPane;

public class AddEdgeListener implements ActionListener {
    GraphEditor iParent;

    public AddEdgeListener(GraphEditor pParent) {
	iParent = pParent;
    }

    public void actionPerformed(ActionEvent e) {
	iParent.addEdge();
    }
}
