import java.util.*;

public class MinimumSpanningTree {
    Graph iGraph;
    ArrayList iEdges = new ArrayList();
    
    public MinimumSpanningTree(Graph pGraph) {
	iGraph = pGraph;
    }

    public void run() {
	ArrayList tForest = new ArrayList();
	String[] tEdges = iGraph.allEdges();
	String[] tVerts = iGraph.vertexList();
	FlowEdge[] tFEdges = new FlowEdge[tEdges.length];
	Hashtable tDone = new Hashtable();
	for(int i = 0;i < tEdges.length;i++) {
	    String tFrom = tEdges[i].substring(0, tEdges[i].indexOf(","));
	    String tTo = tEdges[i].substring(tEdges[i].indexOf(",") + 1);
	    tFEdges[i] = new FlowEdge(tEdges[i], iGraph.getEdgeWeight(tFrom, tTo));
	}
	Arrays.sort(tFEdges);
	for(int j = 0;j < tVerts.length;j++) {
	    Hashtable tH = new Hashtable();
	    tH.put(tVerts[j], new Integer(0));
	    tForest.add(tH);
	}
	int edge_counter = 0;
	while (!finished(tForest)) {
	    String tE = tFEdges[edge_counter].getEdge();
	    String tFrom = tE.substring(0, tE.indexOf(","));
	    String tTo = tE.substring(tE.indexOf(",") + 1);
	    int f = 0;
	    int s = 0;
	    Iterator k = tForest.iterator();
	    while(k.hasNext()) {
		Hashtable tH = (Hashtable)k.next();
		if (tH.containsKey(tFrom)) {
		    break;
		}
		f++;
	    }
	    Iterator l = tForest.iterator();
	    while(l.hasNext()) {
		Hashtable tH = (Hashtable)l.next();
		if (tH.containsKey(tTo)) {
		    break;
		}
		s++;
	    }
	    if (s != f) {
		// System.out.println(tFrom + " in forest #" + f + "; " + tTo + " in forest #" + s);
		System.out.println("Edge: " + tE);
		Hashtable tF = (Hashtable)tForest.get(f);
		Hashtable tS = (Hashtable)tForest.get(s);
		Enumeration tEnum = tS.keys();
		while(tEnum.hasMoreElements()) {
		    String tK = (String)tEnum.nextElement();
		    tF.put(tK, new Integer(0));
		}
		tForest.set(f, tF);
		tForest.set(s, new Hashtable());
		// System.out.println("Forest: " + f + " " + tF);
		tDone.put(tFrom + "," + tTo, new Integer(0));
		iEdges.add(tE);
	    }
	    edge_counter++;
	}
	System.out.println("done!");
    }

    public boolean finished(ArrayList pForest) {
	Iterator i = pForest.iterator();
	boolean found = false;
	while(i.hasNext()) {
	    Hashtable tTree = (Hashtable)i.next();
	    if(tTree.size() == iGraph.getNumberOfVertices()) {
		found = true;
	    }
	}
	return found;
    }

    public ArrayList getEdges() {
	return iEdges;
    }
    
    public Graph newGraph() {
	Graph tGraph = new Graph();
	String[] tVerts = iGraph.vertexList();
	for(int i = 0;i < tVerts.length;i++) {
	    tGraph.addVertex(tVerts[i]);
	}
	Iterator i = iEdges.iterator();
	while(i.hasNext()) {
	    String tEdge = (String)i.next();
	    String tFrom = tEdge.substring(0, tEdge.indexOf(","));
	    String tTo = tEdge.substring(tEdge.indexOf(",") + 1);
	    tGraph.addEdge(tFrom, tTo, iGraph.getEdgeWeight(tFrom, tTo));
	    //tGraph.addEdge(tTo, tFrom, iGraph.getEdgeWeight(tTo, tFrom));
	}
	return tGraph;
    }

    class FlowEdge implements Comparable {
	String iEdge;
	int iCap;

	public FlowEdge(String pEdge, int pCap) {
	    iEdge = pEdge;
	    iCap = pCap;
	}

	public String getEdge() {
	    return iEdge;
	}

	public int getCap() {
	    return iCap;
	}

	public int compareTo(Object o) {
	    FlowEdge tEdge = (FlowEdge)o;
	    if(tEdge.getCap() > iCap) {
		return -1;
	    } else {
		if(tEdge.getCap() == iCap) {
		    return 0;
		} else {
		    return 1;
		}
	    }
	}
	
	public String toString() {
	    return new String(iEdge + " - " + iCap);
	}
    }
}
