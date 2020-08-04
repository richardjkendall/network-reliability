import java.util.*;

public class Connectivity implements GraphAlgorithm {
    ArrayList iEdgeValues = new ArrayList();
    ArrayList iVertexValues = new ArrayList();
    Graph iGraph;

    public Connectivity() {
	
    }

    public void setParameters(Hashtable pParams) {
	
    }
    
    public void setGraph(Graph pGraph) {
	iGraph = pGraph;
    }

    public void run() {
	String[] tNodes = iGraph.vertexList();
	iGraph.setAllWeights(1);
	for(int i = 0;i < tNodes.length;i++) {
	    String tSource = tNodes[i];
	    for(int j = 0;j < tNodes.length;j++) {
		String tSink = tNodes[j];
		if (!tSource.equals(tSink)) {
		    MaximumFlow3 tMax = new MaximumFlow3(tSource, tSink, iGraph);
		    tMax.run();
		    AlgorithmResult tResult = tMax.answer();
		    iEdgeValues.add(new Integer(tResult.getIntValue()));
		}
	    }
	}
	Graph eGraph = iGraph.prepareForKCMaxFlow();
	tNodes = eGraph.vertexList();
	for(int i = 0;i < tNodes.length;i++) {
	    String tSource = tNodes[i];
	    if (isTransmitter(tSource)) {
		for(int j = 0;j < tNodes.length;j++) {
		    String tSink = tNodes[j];
		    if (!tSource.equals(tSink)) {
			if (isReceiver(tSink)) {
			    MaximumFlow3 tMax = new MaximumFlow3(tSource, tSink, eGraph);
			    tMax.run();
			    AlgorithmResult tResult = tMax.answer();
			    iVertexValues.add(new Integer(tResult.getIntValue()));
			}
		    }
		}
	    }
	}
    }

    public AlgorithmResult answer() {
	String tRes = new String("Kappa: " + minValue(iVertexValues) + "; Lambda: " + minValue(iEdgeValues));
	return new AlgorithmResult(tRes);
    }

    private int minValue(ArrayList tList) {
	int min = 1000;
	Iterator i = tList.iterator();
	while(i.hasNext()) {
	    int tValue = ((Integer)i.next()).intValue();
	    if (tValue < min) {
		min = tValue;
	    }
	}
	return min;
    }

    public int getEdgeConnectivity() {
	return minValue(iEdgeValues);
    }

    public int getVertexConnectivity() {
	return minValue(iVertexValues);
    }

    private boolean isTransmitter(String tVertex) {
	int tPlace = tVertex.lastIndexOf("t");
	return tPlace == (tVertex.length() - 1);
    }

    private boolean isReceiver(String tVertex) {
	int tPlace = tVertex.lastIndexOf("r");
	return tPlace == (tVertex.length() - 1);
    }
}
