import java.awt.event.*;
import javax.swing.JOptionPane;

public class RemoveVertexListener implements ActionListener {
    GraphEditor iParent;

    public RemoveVertexListener(GraphEditor pParent) {
	iParent = pParent;
    }

    public void actionPerformed(ActionEvent e) {
	iParent.removeVertex();
    }
}
