import java.util.*;

public class Vertex {
    private String iVertexName;
    private int iVertexID;
    private ArrayList iIncidentOutEdges = new ArrayList();
    private ArrayList iIncidentInEdges = new ArrayList();

    public Vertex(final String pVertexName, final int pVertexID) {
	iVertexName = pVertexName;
	iVertexID = pVertexID;
    }
    
    public void addInEdge(final Edge pEdge) {
	iIncidentInEdges.add(pEdge);
    }

    public void addOutEdge(final Edge pEdge) {
	iIncidentOutEdges.add(pEdge);
    }

    public String getVertexName() {
	return iVertexName;
    }
    
    public int getVertexID() {
	return iVertexID;
    }
    
    public String toString() {
	/* String tRet = new String("");
	Iterator i = iIncidentInEdges.iterator();
	tRet = "Vertex: " + iVertexName + " (o-d: " + iIncidentOutEdges.size() + ", i-d: " + iIncidentInEdges.size() + ")\n";
	tRet = tRet + "  - in edges\n";
	while (i.hasNext()) {
	    Edge tEdge = (Edge)i.next();
	    tRet = tRet + "     |_ " + tEdge.toString() + "\n";
	}
	i = iIncidentOutEdges.iterator();
	tRet = tRet + "  - out edges\n";
	while (i.hasNext()) {
	    Edge tEdge = (Edge)i.next();
	    tRet = tRet + "     |_ " + tEdge.toString() + "\n";
	}
	return tRet; */
	return iVertexName;
    }

    public Edge[] getOutEdges() {
	Object[] tObj = iIncidentOutEdges.toArray();
	Edge[] tEdges = new Edge[tObj.length];
	for(int i = 0;i < tObj.length;i++) {
	    tEdges[i] = (Edge)tObj[i];
	}
	return tEdges;
	//return iIncidentOutEdges.toArray(new Edge[1]);
    }
    
    public void replaceOutEdges(Edge[] pEdges) {
	iIncidentOutEdges.clear();
	for (int i = 0;i < pEdges.length;i++) {
	    iIncidentOutEdges.add(pEdges[i]);
	}
    }
    
    public void removeOutEdge(final String pTo) {
	Iterator i = iIncidentOutEdges.iterator();
	int c = 0;
	while(i.hasNext()) {
	    Edge tEdge = (Edge)i.next();
	    if (tEdge.getEdgeTo().equals(pTo)) {
		iIncidentOutEdges.remove(c);
		break;
	    }
	    c++;
	}
    }

    public int degree() {
	return iIncidentOutEdges.size();
    }
}
