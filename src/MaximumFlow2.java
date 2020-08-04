import java.util.*;

public class MaximumFlow2 implements BFS_Algorithm {
    private String iSink;
    private Stack iPath = new Stack();
    private ArrayList iPaths = new ArrayList();
    private Graph iGraph;
    private ArrayList iFlows = new ArrayList();
    private Hashtable usedCap = new Hashtable();
    private boolean foundandquit = false;
    private Hashtable tAlready = new Hashtable();
    private String iSource;
    private int iMaxFlow = 0;

    public MaximumFlow2(final String pSink, final Graph pGraph) {
	//System.out.println("Going to " + pSink);
	iSink = pSink;
	iGraph = pGraph;
    }
    
    public void run(final Tree pInput) {
	int cSize = 0;
	boolean c = true;
	iSource = pInput.getData();
	while(c) {
	    //System.out.println("Going around control loop...");
	    foundandquit = false;
	    iPath = new Stack();
	    findFlowAugmentingPaths(pInput);
	    //break;
	    //System.out.println("BACK IN CONTROL FUNCTION");
	    if (iPaths.size() > cSize) {
		c = true;
		cSize = iPaths.size();
	    } else {
		c = false;
	    }
	}
	Iterator j = iFlows.iterator();
	int f = 0;
	while(j.hasNext()) {
	    int v = ((Integer)j.next()).intValue();
	    f += v;
	}
	iMaxFlow = f;
    }

    public AlgorithmResult answer() {
	return new AlgorithmResult(new Integer(iMaxFlow));
    }
    
    public ArrayList getPaths() {
	return iPaths;
    }

    private void findFlowAugmentingPaths(Tree pInput) {
	String tFrom = pInput.getData();
	iPath.push(tFrom);
	System.out.println("Entered findFlowAugmentingPaths() tree rooted at " + tFrom);
	if (!pInput.noChildren()) {
	    System.out.println("Sorting children in order of max available flow...");
	    Hashtable tTable = pInput.getChildren();
	    EdgeCell[] tChildren = new EdgeCell[tTable.size()];
	    Enumeration tEnum = tTable.elements();
	    int tC = 0;
	    while(tEnum.hasMoreElements()) {
		Tree tTree = (Tree)tEnum.nextElement();
		String tGoingTo = tTree.getData();
		int tResidualCap = 0;
		if (usedCap.containsKey(tFrom + tGoingTo)) {
		    int tUsedCap = ((Integer)usedCap.get(tFrom + tGoingTo)).intValue();
		    tResidualCap = iGraph.getEdgeWeight(tFrom, tGoingTo) - tUsedCap;
		} else {
		    tResidualCap = iGraph.getEdgeWeight(tFrom, tGoingTo);
		}
		System.out.println("\tEdge " + tFrom + "->" + tGoingTo + " resid cap:" + tResidualCap);
		tChildren[tC] = new EdgeCell(tResidualCap, tTree);
		tC++;
	    }
	    Arrays.sort(tChildren, new EdgeCellComparator());
	    for(int i = 0;i < tChildren.length;i++) {
		System.out.print(tChildren[i].getTree().getData() + " ");
	    }
	    System.out.println();
	    int k = 0;
	    while (k < tChildren.length && !foundandquit) {
		Tree tTree = tChildren[k].getTree();
		String tGoingTo = tTree.getData();
		int tResidualCap = tChildren[k].getCap();
		boolean f = true;
		k++;
		if (tResidualCap == 0 || tGoingTo.equals(iSource)) {
		    f = false;
		} 
		if (f) {
		    if (tGoingTo.equals(iSink)) {
			System.out.println("We have ourselves a flow augmenting path!");
			// calculate max flow through the path
			iPath.push(iSink);
			Path tPath = new Path(iPath);
			// update used edges table
			String[] tEdges = tPath.getEdges();
			for(int i = 0;i < tEdges.length;i++) {
			    if (!usedCap.containsKey(tEdges[i])) {
				usedCap.put(tEdges[i], new Integer(0));
				usedCap.put(strRev(tEdges[i]), new Integer(0));
			    }
			}
			System.out.println("\tFlow augmenting path found: " + tPath);
			iPaths.add(tPath);
			// find min available flow
			String[] tVerts = tPath.getVertices();
			int min = 0;
			for(int j = 0;j < (tVerts.length - 1);j++) {
			    int ew = iGraph.getEdgeWeight(tVerts[j], tVerts[j+1]);
			    int sf = ((Integer)usedCap.get(tVerts[j] + tVerts[j+1])).intValue();
			    int al = ew - sf;
			    if (j == 0) {
				min = al;
			    } else {
				if (al < min) {
				    min = al;
				}
			    }
			}
			for(int i = 0;i < tEdges.length;i++) {
			    int soFar = ((Integer)usedCap.get(tEdges[i])).intValue();
			    soFar += min;
			    usedCap.put(tEdges[i], new Integer(soFar));
			    usedCap.put(strRev(tEdges[i]), new Integer(soFar));
			}
			System.out.println("\tFlow level: " + min);
			iFlows.add(new Integer(min));
			foundandquit = true;
			break;
		    } else {
			// check out this node, any paths?
			findFlowAugmentingPaths(tTree);
			iPath.pop();
		    }
		}
	    }
	} else {
	    System.out.println("\tIt has no children.");
	}
    }

    public static String strRev(final String input) {
	char data[] = {input.charAt(1), input.charAt(0)};
	return new String(data);
    }
}
