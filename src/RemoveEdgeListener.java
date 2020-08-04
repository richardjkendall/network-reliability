import java.awt.event.*;
import javax.swing.JOptionPane;

public class RemoveEdgeListener implements ActionListener {
    GraphEditor iParent;

    public RemoveEdgeListener(GraphEditor pParent) {
	iParent = pParent;
    }

    public void actionPerformed(ActionEvent e) {
	iParent.removeEdge();
    }
}
