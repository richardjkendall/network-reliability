public class Edge {
    public static int OUT = 1;
    public static int IN = 2;

    private String iVertexName;
    private int iWeight;
    private int iDirection;
    private int iUsedCapacity = 0;

    public Edge(final String pVertexName, final int pWeight, final int pDirection) {
	iVertexName = pVertexName;
	iWeight = pWeight;
	iDirection = pDirection;
    }

    public String getEdgeTo() {
	return iVertexName;
    }

    public int getWeight() {
	return iWeight;
    }
    
    public void setWeight(int pWeight) {
	iWeight = pWeight;
    }

    public int iDirection() {
	return iDirection;
    }
    
    public String toString() {
	if (iDirection == OUT) {
	    return "edge to " + iVertexName + " weight: " + iWeight + "; used cap: " + iUsedCapacity;
	} else {
	    return "edge from " + iVertexName + " weight: " + iWeight + "; used cap: " + iUsedCapacity;
	}
    }
}
