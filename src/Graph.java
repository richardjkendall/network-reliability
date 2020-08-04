import java.util.*;
import java.io.*;

public class Graph {
    private Hashtable iVertices = new Hashtable();
    private int iEdgeNumber = 0;

    public Graph() {
	// emtpy graph constructor
    }
    
    public Graph(final Hashtable pVertices) {
	// to make a copy of the graph
	iVertices = pVertices;
    }

    public Graph(final String pFileName) {
	// create a graph from the graph file format
	try {
	    loadFromFile(pFileName);
	} catch (IOException e) {
	    System.out.println("Error - could not read graph data file: " + pFileName);
	}
    }

    public void loadFromFile(final String pFileName) throws IOException {
	BufferedReader tReader = new BufferedReader(new FileReader(pFileName));
	int tVID = 0;
	iVertices = new Hashtable();
	while (true) {
	    String tLine = tReader.readLine();
	    if (tLine == null) {
		break;
	    }
	    StringTokenizer tTokens = new StringTokenizer(tLine);
	    if (tTokens.hasMoreTokens()) {
		String tType = tTokens.nextToken();
		if (tType.equals("vertex")) {
		    String tVertexName = tTokens.nextToken();
		    iVertices.put(tVertexName, new Vertex(tVertexName, tVID));
		    tVID++;
		    //System.out.println("PARSE: added vertex " + tVertexName + " to graph");
		}
		if (tType.equals("edge")) {
		    iEdgeNumber++;
		    String tEdgeFrom = tTokens.nextToken();
		    String tEdgeTo = tTokens.nextToken();
		    int tEdgeWeight = Integer.parseInt(tTokens.nextToken());
		    Vertex tVertex = (Vertex)iVertices.get(tEdgeFrom);
		    tVertex.addOutEdge(new Edge(tEdgeTo, tEdgeWeight, Edge.OUT));
		    
		    iVertices.put(tEdgeFrom, tVertex);
		    tVertex = (Vertex)iVertices.get(tEdgeTo);
		    tVertex.addInEdge(new Edge(tEdgeFrom, tEdgeWeight, Edge.IN));
		    iVertices.put(tEdgeTo, tVertex);
		    //System.out.println("PARSE: added edge from " + tEdgeFrom + " to " + tEdgeTo + " of weight: " + tEdgeWeight);
		}
	    } else {
		//System.out.println("PARSE: blank line");
	    }
	}
    }

    public boolean addVertex(final String pVertexName) {
	if (iVertices.containsKey(pVertexName)) {
	    return false;
	} else {
	    iVertices.put(pVertexName, new Vertex(pVertexName, 0));
	    return true;
	}
    }

    public void addEdge(final String pFrom, final String pTo, final int pWeight) {
	System.out.println("addEdge called; " + pFrom + " to " + pTo);
	Vertex tVertex = (Vertex)iVertices.get(pFrom);
	tVertex.addOutEdge(new Edge(pTo, pWeight, Edge.OUT));
	iVertices.put(pFrom, tVertex);
	tVertex = (Vertex)iVertices.get(pTo);
	tVertex.addOutEdge(new Edge(pFrom, pWeight, Edge.IN));
	iVertices.put(pTo, tVertex);
    }

    public boolean removeVertex(final String pVertexName) {
	if (iVertices.containsKey(pVertexName)) {
	    System.out.println("removed");
	    iVertices.remove(pVertexName);
	    System.out.println(iVertices.containsKey(pVertexName));
	    return true;
	} else {
	    return false;
	}
    }

    public boolean removeEdge(final String pFrom, final String pTo) {
	Vertex tVFrom = (Vertex)iVertices.get(pFrom);
	Vertex tVTo = (Vertex)iVertices.get(pTo);
	tVFrom.removeOutEdge(pTo);
	tVTo.removeOutEdge(pFrom);
	return true;
    }

    public Graph getCopy() {
	return new Graph((Hashtable)iVertices.clone());
    }

    public String toString() {
	String tRet = new String ("");
	Enumeration tOut = iVertices.elements();
	while(tOut.hasMoreElements()) {
	    Vertex tVertex = (Vertex)tOut.nextElement();
	    tRet = tRet + tVertex.toString() + "\n";
	}
	return tRet;
    }
    
    public Vertex getVertex(final String pID) {
	return (Vertex)iVertices.get(pID);
    }

    public int getNumberOfVertices() {
	return iVertices.size();
    }

    public int getNumberOfEdges() {
	return iEdgeNumber;
    }

    public String[] vertexList() {
	String[] tRet = new String[iVertices.size()];
	Enumeration tEnum = iVertices.elements();
	int c = 0;
	while(tEnum.hasMoreElements()) {
	    tRet[c] = ((Vertex)tEnum.nextElement()).getVertexName();
	    c++;
	}
	return tRet;
    }
    
    public int getEdgeWeight(final String pFromVertex, final String pToVertex) {
	Vertex tVertex = (Vertex)iVertices.get(pFromVertex);
	Edge[] tEdges = tVertex.getOutEdges();
	int tWeight = 0;
	for(int i = 0;i < tEdges.length;i++) {
	    if (tEdges[i].getEdgeTo().equals(pToVertex)) {
		tWeight = tEdges[i].getWeight();
	    }
	}
	return tWeight;
    }

    public ArrayList vertexArrayList() {
	ArrayList tList = new ArrayList();
	Enumeration tEnum = iVertices.elements();
	while(tEnum.hasMoreElements()) {
	    tList.add((Vertex)tEnum.nextElement());
	}
	return tList;
    }

    public String[] adjacentVertices(final String pVertex) {
	Vertex tV = (Vertex)iVertices.get(pVertex);
	Edge[] tEdges = tV.getOutEdges();
	String[] tStr = new String[tEdges.length];
	for(int i = 0;i < tEdges.length;i++) {
	    tStr[i] = tEdges[i].getEdgeTo();
	}
	return tStr;
    }

    public int vertexDegree(final String pVertex) {
	Vertex tV = getVertex(pVertex);
	return tV.degree();
    }

    public String[] allEdges() {
	ArrayList tEdges = new ArrayList();
	String[] tVerts = vertexList();
	for(int i = 0;i < tVerts.length;i++) {
	    Vertex tV = getVertex(tVerts[i]);
	    Edge[] tE = tV.getOutEdges();
	    for(int k = 0;k < tE.length;k++) {
		tEdges.add(new String(tV.getVertexName() + "," + tE[k].getEdgeTo()));
	    }
	}
	String[] tRet = new String[tEdges.size()];
	Iterator j = tEdges.iterator();
	int c = 0;
	while(j.hasNext()) {
	    tRet[c] = (String)j.next();
	    c++;
	}
	return tRet;
    }

    public boolean edgeExists(String pSource, String pSink) {
	Vertex tVert = getVertex(pSource);
	Edge[] tEdges = tVert.getOutEdges();
	boolean found = false;
	for(int i = 0;i < tEdges.length;i++) {
	    if(tEdges[i].getEdgeTo().equals(pSink)) {
		found = true;
	    }
	}
	return found;
    }

    public Tree BFS_Tree(final String pSource) {
	Queue tWorkList = new Queue(); // vertices to visit
	Queue tOriginVertex = new Queue(); // vertex just visited
	Queue tTreeParent = new Queue(); // parent node in BFS tree
	Hashtable tBeenTo = new Hashtable();
	Tree tTree = new Tree(pSource);
	int max_times = 200;
	int c_level = 0;
	int m_level = 30;
	tWorkList.push(getVertex(pSource));
	tTreeParent.push(tTree);
	while (!(tWorkList.empty()) && (c_level < m_level)) {
	    try {
		Vertex tVertex = (Vertex)tWorkList.pop();
		Tree tSubTree = (Tree)tTreeParent.pop();
		String tFrom = new String("");
		if (!tOriginVertex.empty()) {
		    tFrom = (String)tOriginVertex.pop();
		} else {
		    tFrom = pSource;
		}
		int tVID = tVertex.getVertexID();
		Edge[] tEdges = tVertex.getOutEdges();
		//System.out.println(" => Vertex " + tVertex.getVertexName() + " has been popped off the WorkList.");
		if (!tFrom.equals("")) {
		    if (!tBeenTo.containsKey(tFrom + tVertex.getVertexName())) {
			tBeenTo.put(tFrom + tVertex.getVertexName(), new Integer(1));
		    }
		    //tBeenTo.put(tVertex.getVertexName() + tFrom, new Integer(0));
		}
		for(int i = 0;i < tEdges.length;i++) {
		    int tTimes = 0;
		    // check to see if this edge has been followed already??
		    //System.out.print("Edge to " + tEdges[i].getEdgeTo() + " ... ");
		    if (tEdges[i].getEdgeTo().equals(tFrom)) {
			//System.out.println(" is from the origin vertex - No action.");
		    } else {
			boolean tOk = false;;
			if (!(tBeenTo.containsKey(tVertex.getVertexName() + tEdges[i].getEdgeTo()))) {
			    tOk = true;
			} else {
			    Integer tInt = (Integer)tBeenTo.get(tVertex.getVertexName() + tEdges[i].getEdgeTo());
			    tTimes = tInt.intValue();
			    if (tTimes <= max_times) {
				tTimes++;
				tBeenTo.put(tVertex.getVertexName() + tEdges[i].getEdgeTo(), new Integer(tTimes));
				tOk = true;
			    } else {
				tOk = false;
			    }
			}
			//tOk = true;
			if (tOk) {
			    // add children to sub tree
			    tSubTree.updateChild(tEdges[i].getEdgeTo(), new Tree(tEdges[i].getEdgeTo()));
			    tTreeParent.push(tSubTree.getChild(tEdges[i].getEdgeTo()));
			    //System.out.println(" has been explored " + tTimes);
			    //tBeenTo.put(tVertex.getVertexName() + tEdges[i].getEdgeTo(), new Integer(0));
			    tWorkList.push(getVertex(tEdges[i].getEdgeTo()));
			    tOriginVertex.push(tVertex.getVertexName());
			    
			    //System.out.println("   Target Vertex (" + tEdges[i].getEdgeTo() + ") pushed onto the WorkList.");
			} else {
			    //System.out.println(" has been explored max times.  No action.");
			}
		    }
		}
		
		//System.out.println("WorkList: " + tWorkList.toString());
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    //System.out.println("");
	    //c_level++;
	}
	//tWorkList.push(getVertex(pSource));
	return tTree;
    }

    public void setAllWeights(int pValue) {
	String[] tVerts = vertexList();
	for (int i = 0;i < tVerts.length;i++) {
	    Vertex tVertex = (Vertex)iVertices.get(tVerts[i]);
	    Edge[] tEdges = tVertex.getOutEdges();
	    //System.out.println("setting edge weights from " + tVerts[i] + " to one");
	    for (int j = 0;j < tEdges.length;j++) {
		tEdges[j].setWeight(1);
	    }
	    //System.out.println("...done");
	    tVertex.replaceOutEdges(tEdges);
	    iVertices.put(tVerts[i], tVertex);
	}
    }

    public boolean on3Cycle(String pEdge) {
	String tFrom = pEdge.substring(0, pEdge.indexOf(","));
	String tTo = pEdge.substring(pEdge.indexOf(",") + 1);
	Vertex tX = getVertex(tTo);
	Edge[] tEdges = tX.getOutEdges();
	boolean found = false;
	//System.out.println("Edge to: " + tTo);
	for(int i = 0;i < tEdges.length;i++) {
	    Vertex tY = getVertex(tEdges[i].getEdgeTo());
	    Edge[] tEdgesA = tY.getOutEdges();
	    for(int j = 0;j < tEdgesA.length;j++) {
		if(tFrom.equals(tEdgesA[j].getEdgeTo())) {
		    found = true;
		    break;
		}
	    }
	}
	return found;
    }

    public Graph prepareForKCMaxFlow() {
	String tG = new String("");
	String tG2 = new String("");
	String[] tVerts = vertexList();
	for (int i = 0;i < tVerts.length;i++) {
	    tG = tG + "vertex " + tVerts[i] + "t\nvertex " + tVerts[i] + "r\n";
	    tG2 = tG2 + "edge " + tVerts[i] + "r " + tVerts[i] + "t 1\n";
	}
	for (int i = 0;i < tVerts.length;i++) {
	    Vertex tVertex = (Vertex)iVertices.get(tVerts[i]);
	    Edge[] tEdges = tVertex.getOutEdges();
	    for (int j = 0;j < tEdges.length;j++) {
		String tEdgeFrom = tVerts[i];
		String tEdgeTo = tEdges[j].getEdgeTo();
		tG2 = tG2 + "edge " + tEdgeFrom + "t " + tEdgeTo + "r 1\n";
		tG2 = tG2 + "edge " + tEdgeTo + "t " + tEdgeFrom + "r 1\n";
	    }
	}
	tG = tG + tG2;
	//System.out.println(tG);
	try {
	    PrintWriter tOut = new PrintWriter(new BufferedWriter(new FileWriter("t.tmp")));
	    tOut.print(tG);
	    tOut.close();
	} catch (IOException e) {
	    System.out.println(" ** Unable to save temp g data");
	}
	return new Graph("t.tmp");
    }
}
