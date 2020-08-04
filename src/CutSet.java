import java.util.*;

public class CutSet {
    Graph iGraph;
    String iSource = new String("");
    String iSink = new String("");
    int iMaxFlow = 0;
    ArrayList iPaths;
    ArrayList iEdges = new ArrayList();
    String iLog = new String("");

    public CutSet(Graph pGraph, String pSource, String pSink) {
	iGraph = pGraph;
	iSource = pSource;
	iSink = pSink;
	MaximumFlow3 tMax = new MaximumFlow3(iSource, iSink, iGraph);
	tMax.run();
	AlgorithmResult tResult = tMax.answer();
	iMaxFlow = tResult.getIntValue();
	iPaths = tMax.getPaths();
    }

    public int min(int x, int y) {
	return x < y ? x : y;
    }

    public void run() {
	Iterator i = iPaths.iterator();
	while(i.hasNext()) {
	    Path tPath = (Path)i.next();
	    String[] tEdges = tPath.getEdges();
	    int[] tCaps = new int[tEdges.length];
	    int[] tF = new int[tEdges.length];
	    int[] tT = new int[tEdges.length];
	    for(int j = 0;j < tEdges.length;j++) {
		String tFrom = tEdges[j].substring(0, tEdges[j].indexOf(","));
		String tTo = tEdges[j].substring(tEdges[j].indexOf(",") + 1);
		System.out.println("From: " + tFrom + "; To: " + tTo);
		tCaps[j] = iGraph.getEdgeWeight(tFrom, tTo);
		tF[j] = iGraph.vertexDegree(tFrom);
		tT[j] = iGraph.vertexDegree(tTo);
	    }
	    // need to find the largest edge
	    int biggest = 0;
	    int smallest = 1000;
	    String large_edge = new String("");
	    for(int k = 0;k < tEdges.length;k++) {
		System.out.println(tEdges[k] + " " + tCaps[k] + " " + tT[k] + " " + tF[k]);
		if(true) {
		    if(tT[k] < smallest || tF[k] < smallest) {
			biggest = tCaps[k];
			smallest = min(tT[k], tF[k]);
			large_edge = tEdges[k];
		    }
		}
	    }
	    System.out.println(tPath + "\nEdge to remove: " + large_edge + " (" + biggest + ") [" + smallest + "]");
	    iEdges.add(large_edge);
	    iLog = iLog + "\t[*] (" + large_edge + ")\n";
	}
    }

    public String getLog() {
	return iLog;
    }

    public ArrayList getEdges() {
	return iEdges;
    }
}
