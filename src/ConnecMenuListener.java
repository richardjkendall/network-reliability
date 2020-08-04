import java.awt.event.*;
import javax.swing.JOptionPane;

public class ConnecMenuListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
	String tCommand = e.getActionCommand();
	if (tCommand.equals("Run k-(edge-)connectivity algorithm")) {
	    GraphGUI.runConnectivity();
	}
	if (tCommand.equals("Highlight v,w edge disjoint paths")) {
	    String tSource = JOptionPane.showInputDialog(GraphGUI.iMainWindow, "Enter source vertex:", "");
	    String tSink = JOptionPane.showInputDialog(GraphGUI.iMainWindow, "Enter sink vertex:", "");
	    GraphGUI.highlightEdgeDisPaths(tSource, tSink);
	}
	if (tCommand.equals("Maximum flow between v,w")) {
	    String tSource = JOptionPane.showInputDialog(GraphGUI.iMainWindow, "Enter source vertex:", "");
	    String tSink = JOptionPane.showInputDialog(GraphGUI.iMainWindow, "Enter sink vertex:", "");
	    GraphGUI.findMaxFlow(tSource, tSink);
	}
	if (tCommand.equals("Augment to k-edge-connected...")) {
	    String tK = JOptionPane.showInputDialog(GraphGUI.iMainWindow, "Enter k:", "");
	    GraphGUI.augment(Integer.parseInt(tK));
	}
	if (tCommand.equals("Highlight v,w cut-sets")) {
	    String tSource = JOptionPane.showInputDialog(GraphGUI.iMainWindow, "Enter source vertex:", "");
	    String tSink = JOptionPane.showInputDialog(GraphGUI.iMainWindow, "Enter sink vertex:", "");
	    GraphGUI.findCutSet(tSource, tSink);
	}
	if (tCommand.equals("Highlight v,w minimal sep-sets")) {
	    String tSource = JOptionPane.showInputDialog(GraphGUI.iMainWindow, "Enter source vertex:", "");
	    String tSink = JOptionPane.showInputDialog(GraphGUI.iMainWindow, "Enter sink vertex:", "");
	    GraphGUI.findMinSepSet(tSource, tSink);
	}
	if (tCommand.equals("Show minimum spanning tree")) {
	    GraphGUI.showMST();
	}
	if (tCommand.equals("New")) {
	    GraphGUI.newGraph();
	}
	if (tCommand.equals("Save...")) {
	    JOptionPane.showMessageDialog(null, "Function not available.", "Message", JOptionPane.ERROR_MESSAGE);
	}
	if (tCommand.equals("Properties...")) {
	    JOptionPane.showMessageDialog(null, "Function not available.", "Message", JOptionPane.ERROR_MESSAGE);
	}
    }
}
