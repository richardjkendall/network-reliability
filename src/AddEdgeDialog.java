import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class AddEdgeDialog extends JDialog {
    String[] iVertexSet;
    
    JComboBox iFrom = new JComboBox();
    JComboBox iTo = new JComboBox();
    JTextField iCap = new JTextField(5);

    String from = new String("");
    String to = new String("");
    int weight = 0;
    
    public AddEdgeDialog(final String[] pVertexSet) {
	JLabel tFrom = new JLabel("From: ");
	JLabel tTo = new JLabel("To: ");
	JLabel tWeight = new JLabel("Capacity: ");
	JButton tOk = new JButton("Add");
	JButton tCancel = new JButton("Cancel");
	Container tContainer = getContentPane();
	tContainer.setLayout(new FlowLayout());
	iVertexSet = pVertexSet;
	tOk.addActionListener(new OkListener(this));
	tCancel.addActionListener(new CancelListener(this));
	for(int i = 0;i < iVertexSet.length;i++) {
	    iFrom.addItem(iVertexSet[i]);
	    iTo.addItem(iVertexSet[i]);
	}
	tContainer.add(tFrom);
	tContainer.add(iFrom);
	tContainer.add(tTo);
	tContainer.add(iTo);
	tContainer.add(tWeight);
	tContainer.add(iCap);
	tContainer.add(tOk);
	tContainer.add(tCancel);
	setTitle("Add edge...");
	pack();
	setVisible(true);
    }

    public void addEdge() {
	String tFrom = (String)iFrom.getSelectedItem();
	String tTo = (String)iTo.getSelectedItem();
	System.out.println("Edge from " + tFrom + " to " + tTo);
	if (tFrom.equals(tTo)) {
	    // no loops
	    System.out.println("vertices not distinct");
	} else {
	    if (iCap.getText().equals("") || iCap.getText().equals("0")) {
		// edge weight cannot be zero
		System.out.println("edge weight empty or zero");
	    } else {
		// everything looks good add the edge
		from = tFrom;
		to = tTo;
		weight = Integer.parseInt(iCap.getText());
		GraphGUI.iEditor.addEdgeData(from, to, weight);
	    }
	}
    }

    public String getFrom() {
	return from;
    }

    public String getTo() {
	return to;
    }

    public int getWeight() {
	return weight;
    }
}

class OkListener implements ActionListener {
    public AddEdgeDialog iDialog;

    public OkListener(final AddEdgeDialog tDialog) {
	iDialog = tDialog;
    }

    public void actionPerformed(ActionEvent e) {
	iDialog.addEdge();
	iDialog.dispose();
    }
}

class CancelListener implements ActionListener {
    public AddEdgeDialog iDialog;

    public CancelListener(final AddEdgeDialog tDialog) {
	iDialog = tDialog;
    }

    public void actionPerformed(ActionEvent e) {
	iDialog.dispose();
    }
}
