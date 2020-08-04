import java.util.*;

public class MaximumFlow3 implements GraphAlgorithm {
    private String iSink;
    private ArrayList iPaths = new ArrayList();
    private Graph iGraph;
    private ArrayList iFlows = new ArrayList();
    private Hashtable iFlow = new Hashtable();
    private boolean foundandquit = false;
    private Hashtable tAlready = new Hashtable();
    private String iSource;
    private int iMaxFlow = 0;
    private Hashtable iPred = new Hashtable();

    public MaximumFlow3() {
	// nothing
    }

    public MaximumFlow3(final String pSource, final String pSink, final Graph pGraph) {
	iSource = pSource;
	iSink = pSink;
	iGraph = pGraph;
    }
    
    public void setGraph(Graph pGraph) {
	iGraph = pGraph;
    }

    public void setParameters(Hashtable pParams) {
	iSource = (String)pParams.get("SOURCE");
	iSink = (String)pParams.get("SINK");
    }

    public void run() {
	iMaxFlow = max_flow(iSource, iSink);
    }

    public AlgorithmResult answer() {
	return new AlgorithmResult(new Integer(iMaxFlow));
    }
    
    public ArrayList getPaths() {
	return iPaths;
    }

    public int flow(String pFrom, String pTo) {
	if (iFlow.containsKey(pFrom + "," + pTo)) {
	    return ((Integer)iFlow.get(pFrom + "," + pTo)).intValue();
	} else {
	    return 0;
	}
    }

    public void set_flow(String pFrom, String pTo, int value) {
	iFlow.put(pFrom + "," + pTo,  new Integer(value));
	iFlow.put(pTo + "," + pFrom, new Integer(value));
    }

    public int min(int x, int y) {
	return x < y ? x : y;
    }

    public boolean bfs(String pSource, String pSink) {
	//System.out.println("Entered bfs");
	Queue tWorkList = new Queue();
	Queue tOrigin = new Queue();
	Hashtable tBeenDown = new Hashtable();
	boolean tFound = false;
	//Stack tPath = new Stack();
	tWorkList.push(iGraph.getVertex(pSource));
	while (!tWorkList.empty() && !tFound) {
	    try {
		Vertex tC = (Vertex)tWorkList.pop();
		//System.out.println(" => Vertex " + tC.getVertexName() + " has been popped off the worklist.");
		Edge[] tEdges = tC.getOutEdges();
		String tFrom = tC.getVertexName();
		String tParent = new String("");
		if (!tOrigin.empty()) {
		    tParent = (String)tOrigin.pop();
		}
		//tPath.push(tFrom);
		for (int i = 0;i < tEdges.length;i++) {
		    String tGoingTo = tEdges[i].getEdgeTo();
		    String tEdge = tFrom + "," + tGoingTo;
		    if ((iGraph.getEdgeWeight(tFrom, tGoingTo) - flow(tFrom, tGoingTo) > 0) && !tBeenDown.containsKey(tEdge)) {
			//System.out.println("\tEdge to: " + tGoingTo + "; with resid cap: " + (iGraph.getEdgeWeight(tFrom, tGoingTo) - flow(tFrom, tGoingTo)));
			//System.out.println("\tParent: " + tParent);
			tBeenDown.put(tEdge, new Integer(0));
			//residual flow is +ve add to worklist
			// is it the target?
			// it is from the parent? - if so do nothing
			if (tGoingTo.equals(tParent)) {
			    //System.out.println("parent vertex - do nothing, say nothing");
			} else {
			    if (tGoingTo.equals(pSink)) {
				iPred.put(tGoingTo, tFrom);
				//System.out.println("\tSetting the predcessor of " + tGoingTo + " to " + tFrom);
				//tPath.push(tFrom);
				//Path rPath = new Path(tPath);
				//System.out.println("found a path, should now exit");
				//return rPath;
				tFound = true;
				break;
			    } else {
				//go round again
				//tPath.push(tGoingTo);
				//System.out.println("\tSetting the predcessor of " + tGoingTo + " to " + tFrom);
				if (!iPred.containsKey(tGoingTo)) {
				    iPred.put(tGoingTo, tFrom);
				}
				tWorkList.push(iGraph.getVertex(tEdges[i].getEdgeTo()));
				tOrigin.push(tFrom);
				//tPath.pop();
			    }
			}
		    }
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	//System.out.println("Got out of both loops");
	return iPred.containsKey(pSink);
    }

    public int max_flow(String pSource, String pSink) {
	int flow = 0;
	while (bfs(pSource, pSink)) {
	    int increment = 1000000;
	    String tPred = pSink;
	    String tFrom = tPred;
	    Stack tPath = new Stack();
	    tPath.push(tPred);
	    while (true) {
		tPred = (String)iPred.get(tPred);
		tPath.push(tPred);
		//System.out.println(tFrom + " -> " + tPred + ": " + (iGraph.getEdgeWeight(tPred, tFrom) - flow(tPred, tFrom)));
		increment = min(increment, iGraph.getEdgeWeight(tPred, tFrom) - flow(tPred, tFrom));
		tFrom = tPred;
		if (tPred.equals(pSource)) {
		    break;
		}
	    }
	    iPaths.add(new Path(reverseStack(tPath)));
	    //System.out.println("\tFlow on the path: " + increment);
	    tPred = pSink;
	    tFrom = tPred;
	    while(true) {
		tPred = (String)iPred.get(tPred);
		set_flow(tFrom, tPred, (flow(tFrom, tPred) + increment));
		tFrom = tPred;
		if (tPred.equals(pSource)) {
		    break;
		}
	    }
	    flow += increment;
	    iPred = new Hashtable();
	}
	return flow;
    }

    public Stack reverseStack(Stack pStack) {
	Stack tStack = new Stack();
	while(!pStack.empty()) {
	    tStack.push(pStack.pop());
	}
	return tStack;
    }

    public static String strRev(final String input) {
	char data[] = {input.charAt(1), input.charAt(0)};
	return new String(data);
    }
}
