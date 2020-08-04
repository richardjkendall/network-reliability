import java.util.*;

public class EdgeDisjointPaths implements BFS_Algorithm {
    private String iSink;
    //private Hashtable iUsedEdges = new Hashtable();
    private Stack iPath = new Stack();
    private ArrayList iPaths = new ArrayList();
    private ArrayList iDisjointPaths = new ArrayList();
    private int iPathNumber = 0;

    public EdgeDisjointPaths(final String pSink) {
	//System.out.println("Going to " + pSink);
	iSink = pSink;
    }
    
    public void run(final Tree pInput) {
	//iUsedEdges = new Hashtable();
	findPaths(pInput);
	sortPaths();
	iPathNumber = uniquePaths();
    }

    public AlgorithmResult answer() {
	return new AlgorithmResult(new Integer(iPathNumber));
    }

    private void sortPaths() {
	Path[] tPaths = new Path[iPaths.size()];
	ArrayList tList = new ArrayList();
	int c = 0;
	Iterator i = iPaths.iterator();
	while(i.hasNext()) {
	    tPaths[c] = (Path)i.next();
	    c++;
	}
	Arrays.sort(tPaths);
	for(int j = 0;j < tPaths.length;j++) {
	    tList.add(tPaths[j]);
	}
	iPaths = tList;
    }
    
    public ArrayList getPaths() {
	return iDisjointPaths;
    }
    
    private int uniquePaths() {
	Hashtable tHash = new Hashtable();
	Iterator i = iPaths.iterator();
	int c = 0;
	while(i.hasNext()) {
	    Path tPath = (Path)i.next();
	    //System.out.print(tPath);
	    String[] tEdges = tPath.getEdges();
	    boolean f = true;
	    for(int j = 0;j < tEdges.length;j++) {
		if (tHash.containsKey(tEdges[j])) {
		    f = false;
		}
	    }
	    if (f) {
		for (int k = 0;k < tEdges.length;k++) {
		    tHash.put(tEdges[k], new Integer(0));
		}
		c++;
		iDisjointPaths.add(tPath);
		//System.out.println(" (*)");
	    } else {
		//System.out.println("");
	    }
	}
	return c;
    }

    private void findPaths(Tree pInput) {
	String tFrom = pInput.getData();
	//System.out.println("Start from: " + tFrom);
	iPath.push(tFrom);
	if (!pInput.noChildren()) {
	    Enumeration tEnum = pInput.getChildren().elements();
	    while (tEnum.hasMoreElements()) {
		// get child tree
		Tree tTree = (Tree)tEnum.nextElement();
		//System.out.println("Child vertex: " + tTree.getData());
		//if (!(iUsedEdges.containsKey(tFrom + tTree.getData()))) {
		    //iUsedEdges.put(tFrom + tTree.getData(), new Integer(0));
		    if (tTree.getData().equals(iSink)) {
			//System.out.println(" found");
			iPath.push(iSink);
			//printPath();
			iPaths.add(new Path(iPath));
			iPath.pop();
			//break;
		    } else {
			findPaths(tTree);
			iPath.pop();
			//System.out.println("back to " + tFrom);
		    }
		    //}
	    }
	} else {
	    // no children
	}
    }
}
