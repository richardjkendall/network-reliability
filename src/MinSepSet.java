import java.util.*;

public class MinSepSet {
    Graph iGraph;
    String iSource = new String("");
    String iSink = new String("");
    int iMaxFlow = 0;
    ArrayList iPaths;
    ArrayList iVertices = new ArrayList();
    String iLog = new String("");

    public MinSepSet(Graph pGraph, String pSource, String pSink) {
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
	    String[] tVerts = tPath.getVertices();
	    int[] tDeg = new int[tVerts.length];
	    for(int j = 1;j < tVerts.length - 1;j++) {
		tDeg[j] = iGraph.vertexDegree(tVerts[j]);
		System.out.println("Vertex: " + tVerts[j] + "; degree: " + tDeg[j]);
	    }
	    int smallest = 0;
	    String small_vert = new String("");
	    for(int k = 1;k < tVerts.length - 1;k++) {
		if(tDeg[k] > smallest) {
		    small_vert = tVerts[k];
		    smallest = tDeg[k];
		}
	    }
	    System.out.println(tPath + "\nVertex to remove: " + small_vert);
	    iVertices.add(small_vert);
	    iLog = iLog + "\t[*] " + small_vert + "\n";
	}
    }

    public String getLog() {
	return iLog;
    }

    public ArrayList getVertices() {
	return iVertices;
    }
}
