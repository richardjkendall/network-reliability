import java.util.*;

public class ModifyGraph {
    private Graph iGraph;
    private int iK;
    private Hashtable iSEdges = new Hashtable();
    private Hashtable iNEdges = new Hashtable();
    private String iLog = new String("");

    public ModifyGraph(final Graph pGraph, final int k) {
	iGraph = pGraph;
	iK = k;
	run();
    }

    public void addToLog(String pLog) {
	iLog = iLog + pLog + "\n";
    }

    public String getLog() {
	return iLog;
    }

    public void run() {
	ArrayList tSubPartitions = sub_partitions();
	String[] tVerts = iGraph.vertexList();
	more_edges(tSubPartitions, iK);
	for(int i = 0;i< tVerts.length;i++) {
	    System.out.print("Vertex " + tVerts[i] + ": ");
	    if (iSEdges.containsKey(tVerts[i])) {
		int v = ((Integer)iSEdges.get(tVerts[i])).intValue();
		System.out.println("" + v + " new edges.");
	    } else {
		System.out.println("no new edges.");
	    }
	}
	System.out.println("Applying the splitting lemma...");
	apply_splitting_lemma();
    }

    private void apply_splitting_lemma() {
	Hashtable tDone = new Hashtable();
	Hashtable tNC = new Hashtable();
	String[] tVerts = iGraph.vertexList();
	boolean tDesperate = false;
	while(iSEdges.size() > 0) {
	    // select first vertex
	    String tFirst = new String("");
	    String tSecond = new String("");
	    for(int i = 0;i < tVerts.length;i++) {
		if (iSEdges.containsKey(tVerts[i])) {
		    tFirst = tVerts[i];
		    break;
		}
	    }
	    // select second vertex
	    for(int j = 0;j < tVerts.length;j++) {
		if (!tDesperate) {
		    if(!adjacent(tFirst, tVerts[j]) && !tFirst.equals(tVerts[j]) && !tDone.containsKey(tFirst + "->" + tVerts[j]) && iSEdges.containsKey(tVerts[j])) {
			tSecond = tVerts[j];
			break;
		    }
		} else {
		    if(!tFirst.equals(tVerts[j]) && iSEdges.containsKey(tVerts[j])) {
			tSecond = tVerts[j];
			break;
		    }
		}
	    }
	    if (tSecond.equals("")) {
		System.out.println("No second match, first: " + tFirst + "; 's' edges size: " + iSEdges.size());
		if (iSEdges.size() == 1) {
		    break;
		} else {
		    tDesperate = true;
		}
	    } else {
		System.out.println("Replacing " + tFirst + "->s and " + tSecond + "->s with " + tFirst + "->" + tSecond);
		addToLog("\t [*] (" + tFirst + "," + tSecond + ")");
		int tFEC = ((Integer)iSEdges.get(tFirst)).intValue();
		int tSEC = ((Integer)iSEdges.get(tSecond)).intValue();
		if (tFEC - 1 == 0) {
		    iSEdges.remove(tFirst);
		} else {
		    iSEdges.put(tFirst, new Integer(tFEC - 1));
		}
		if (tSEC - 1 == 0) {
		    iSEdges.remove(tSecond);
		} else {
		    iSEdges.put(tSecond, new Integer(tSEC - 1));
		}
		tDone.put(tFirst + "->" + tSecond, new Integer(0));
		iGraph.addEdge(tFirst, tSecond, 1);
		if (tNC.containsKey(tFirst)) {
		    int z = ((Integer)tNC.get(tFirst)).intValue();
		    tNC.put(tFirst, new Integer(z));
		} else {
		    tNC.put(tFirst, new Integer(1));
		}
		if (tNC.containsKey(tSecond)) {
		    int z = ((Integer)tNC.get(tFirst)).intValue();
		    tNC.put(tSecond, new Integer(z));
		} else {
		    tNC.put(tSecond, new Integer(1));
		}
	    }
	}
	if (iSEdges.size() == 1) {
	    System.out.println("There is one edge to 's' left over.");
	    int min = 1000;
	    String small = new String("");
	    String first = new String("");
	    for(int k = 0;k < tVerts.length;k++) {
		if (tNC.containsKey(tVerts[k])) {
		    int x = ((Integer)tNC.get(tVerts[k])).intValue();
		    if (x < min) {
			min = x;
			small = tVerts[k];
		    }
		}
		if (iSEdges.containsKey(tVerts[k])) {
		    first = tVerts[k];
		}
	    }
	    iGraph.addEdge(first, small, 1);
	}
    }

    private boolean adjacent(String tSource, String tSink) {
	boolean tRet = false;
	Vertex tVSrc = iGraph.getVertex(tSource);
	Edge[] tEdges = tVSrc.getOutEdges();
	for(int i = 0;i < tEdges.length;i++) {
	    if (tEdges[i].getEdgeTo().equals(tSink)) {
		tRet = true;
		break;
	    }
	}
	return tRet;
    }

    private boolean more_edges(ArrayList tList, int k) {
	boolean tRet = false;
	Iterator i = tList.iterator();
	while(i.hasNext()) {
	    Path tPath = (Path)i.next();
	    int oe = sub_partition_out_edges(tPath, iSEdges);
	    if ((oe - k) < 0) {
		tRet = true;
		System.out.println(tPath + " requires " + (k - oe) + " more connection(s)");
		int req = k - oe;
		// add enough edges from this sub-partition to s
		String[] tVerts = tPath.getVertices();
		String tMost = new String("");
		int tE = 0;
		for(int j = 0;j < tVerts.length;j++) {
		    if (iGraph.getVertex(tVerts[j]).getOutEdges().length > tE) {
			tMost = tVerts[j];
		    }
		}
		System.out.println("Adding " + req + " edges from " + tMost + " to 's'");
		if (iSEdges.containsKey(tMost)) {
		    int sofar = ((Integer)iSEdges.get(tMost)).intValue();
		    sofar += req;
		    iSEdges.put(tMost, new Integer(sofar));
		} else {
		    iSEdges.put(tMost, new Integer(req));
		}
	    }
	}
	return tRet;
    }

    private ArrayList sub_partitions() {
	ArrayList tSList = new ArrayList();
	try {
	    ArrayList tVList = iGraph.vertexArrayList();
	    Iterator i = tVList.iterator();
	    int tSize = iGraph.getNumberOfVertices();
	    while(i.hasNext()) {
		Vertex tVertex = (Vertex)i.next();
		// follow the edges out of the vertex to build up a sub-partition by DFS
		ArrayList tPaths = dfs(tVertex, tSize);
		Iterator j = tPaths.iterator();
		while(j.hasNext()) {
		    Path tP = (Path)j.next();
		    tSList.add(tP);
		}
	    }
	} catch (Exception e) {
	    System.out.println("Queue error");
	    e.printStackTrace();
	}
	return tSList;
    }

    private ArrayList dfs(Vertex pRoot, int pDepth) {
	// System.out.println(" #=# DFS called rooted at " + pRoot);
	Stack tWork = new Stack();
	Stack tParent = new Stack();
	Hashtable tPred = new Hashtable();
	ArrayList tPaths = new ArrayList();
	Stack tPPath = new Stack();
	tPPath.push(pRoot.getVertexName());
	tPaths.add(new Path(tPPath));
	tWork.push(pRoot);
	while(!tWork.empty()) {
	    try {
		Vertex tCurrent = (Vertex)tWork.pop();
		Edge[] tEdges = tCurrent.getOutEdges();
		String tFrom = tCurrent.getVertexName();
		String tOrigin = new String("");
		if (!tParent.empty()) {
		    tOrigin = (String)tParent.pop();
		}
		// System.out.println("Vertex " + tCurrent + " with parent " + tOrigin);
		for(int i = 0;i < tEdges.length;i++) {
		    String tGoingTo = tEdges[i].getEdgeTo();
		    if (!tGoingTo.equals(tOrigin)) {
			tWork.push(iGraph.getVertex(tGoingTo));
			tParent.push(tFrom);
			// System.out.println("\tEdge to " + tGoingTo);
			tPred.put(tGoingTo, tFrom);
			String tP = tGoingTo;
			Stack tPath = new Stack();
			tPath.push(tGoingTo);
			while(true) {
			    tP = (String)tPred.get(tP);
			    tPath.push(tP);
			    // System.out.println("\t\t - " +tP);
			    if(tP.equals(pRoot.getVertexName())) {
				break;
			    }
			}
			Path tAP = new Path(reverseStack(tPath));
			tPaths.add(tAP);
		    }
		    // what to do here?
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	return tPaths;
    }

    private int sub_partition_out_edges(Path tPath, Hashtable pSEdges) {
	String[] tVerts = tPath.getVertices();
	Hashtable tP = new Hashtable();
	int total_out_edges = 0;
	for(int j = 0;j < tVerts.length;j++) {
	    tP.put(tVerts[j], new Integer(0));
	}
	for(int i = 0;i < tVerts.length;i++) {
	    Vertex tVert = iGraph.getVertex(tVerts[i]);
	    Edge[] tEdges = tVert.getOutEdges();
	    for(int k = 0;k < tEdges.length;k++) {
		if (!tP.containsKey(tEdges[k].getEdgeTo())) {
		    total_out_edges++;
		}
	    }
	    if(pSEdges.containsKey(tVert.getVertexName())) {
		int v = ((Integer)pSEdges.get(tVert.getVertexName())).intValue();
		total_out_edges += v;
	    }
	}
	return total_out_edges;
    }

    public Stack reverseStack(Stack pStack) {
	Stack tStack = new Stack();
	while(!pStack.empty()) {
	    tStack.push(pStack.pop());
	}
	return tStack;
    }
}
