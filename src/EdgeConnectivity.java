import java.util.*;

public class EdgeConnectivity {
    Tree[] iForest;
    ArrayList iValues = new ArrayList();

    public EdgeConnectivity(final Graph tGraph) {
	String[] tNodes = tGraph.vertexList();
	iForest = new Tree[tNodes.length];
	for(int i = 0;i < tNodes.length;i++) {
	    iForest[i] = tGraph.BFS_Tree(tNodes[i]);
	    //System.out.println("BFS Tree for " + tNodes[i] + " built.");
	    for(int j = 0; j < tNodes.length;j++) {
		if (!tNodes[j].equals(tNodes[i])) {
		    BFS_Algorithm tAlgo = new EdgeDisjointPaths(tNodes[j]);
		    tAlgo.run(iForest[i]);
		    AlgorithmResult tRes = tAlgo.answer();
		    iValues.add(new Integer(tRes.getIntValue()));
		    //System.out.println(tRes.getIntValue() + " edge-disjoint path(s) to " + tNodes[j]);
		}
	    }
	}
	
    }

    private int minValue() {
	int min = 1000;
	Iterator i = iValues.iterator();
	while(i.hasNext()) {
	    int tValue = ((Integer)i.next()).intValue();
	    if (tValue < min) {
		min = tValue;
	    }
	}
	return min;
    }

    public int answer() {
	return minValue();
    }
}
