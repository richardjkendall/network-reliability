import java.awt.*;
import java.util.*;
import javax.swing.*;

public class GraphEditor extends JPanel {
    JButton iAddV = new JButton("Add Vertex...");
    JButton iAddE = new JButton("Add Edge...");
    JComboBox iVerts = new JComboBox();
    JComboBox iEdges = new JComboBox();
    JButton iRemV = new JButton("Remove Vertex");
    JButton iRemE = new JButton("Remove Edge");
    
    Graph iGraph;

    String[] iVertexList;

    public GraphEditor() {
	// what here?
	setPreferredSize(new Dimension(700, 30));
	iAddV.addActionListener(new AddVertexListener(this));
	iAddE.addActionListener(new AddEdgeListener(this));
	iRemV.addActionListener(new RemoveVertexListener(this));
	iRemE.addActionListener(new RemoveEdgeListener(this));
	add(iAddV);
	add(iAddE);
	add(new JLabel("|"));
	add(iVerts);
	add(iRemV);
	add(new JLabel("|"));
	add(iEdges);
	add(iRemE);
    }

    public void setGraph(Graph pGraph) {
	iGraph = pGraph;
	iVerts.removeAllItems();
	iEdges.removeAllItems();
	String[] tVerts = iGraph.vertexList();
	iVertexList = tVerts;
	for(int i = tVerts.length - 1;i >= 0;i--) {
	    iVerts.addItem(tVerts[i]);
	    Vertex tVertex = iGraph.getVertex(tVerts[i]);
	    Edge[] tEdges = tVertex.getOutEdges();
	    for(int j = 0;j < tEdges.length;j++) {
		Edge tEdge = tEdges[j];
		String tE = new String(tVerts[i] + "," + tEdge.getEdgeTo());
		iEdges.addItem(tE);
	    }
	}
    }

    public void addVertex() {
	String tName = JOptionPane.showInputDialog(GraphGUI.iMainWindow, "Enter vertex name:","");
	if (iGraph.addVertex(tName)) {
	    setGraph(iGraph);
	    GraphGUI.updateGraph(iGraph);
	} else {
	    JOptionPane.showMessageDialog(null, "The vertex was not added, perhaps a vertex with the same name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
	}
    }

    public void addEdge() {
	AddEdgeDialog tDialog = new AddEdgeDialog(iVertexList);;
    }

    public void addEdgeData(final String pFrom, final String pTo, final int pWeight) {
	iGraph.addEdge(pFrom, pTo, pWeight);
	setGraph(iGraph);
	GraphGUI.updateGraph(iGraph);
    }

    public void removeVertex() {
	String tVertex = (String)iVerts.getSelectedItem();
	System.out.println(tVertex);
	if (iGraph.removeVertex(tVertex)) {
	    setGraph(iGraph);
	    GraphGUI.updateGraph(iGraph);
	} else {
	    JOptionPane.showMessageDialog(null, "The vertex was not removed, perhaps no vertex with this name exists.", "Error", JOptionPane.ERROR_MESSAGE);
	}
    }

    public void removeEdge() {
	String tEdge = (String)iEdges.getSelectedItem();
	String tFrom = tEdge.substring(0, tEdge.indexOf(","));
	String tTo = tEdge.substring(tEdge.indexOf(",") + 1);
	iGraph.removeEdge(tFrom, tTo);
	setGraph(iGraph);
	GraphGUI.updateGraph(iGraph);
    }
}
