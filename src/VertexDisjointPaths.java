import java.util.*;

public class VertexDisjointPaths implements BFS_Algorithm {
    private String iSink;
    private Stack iPath = new Stack();
    private ArrayList iPaths = new ArrayList();
    private int iPathNumber = 0;

    public VertexDisjointPaths(final String pSink) {
	iSink = pSink;
    }

    public void run(Tree pInput) {
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

    private int uniquePaths() {
	Hashtable tHash = new Hashtable();
	Iterator i = iPaths.iterator();
	int c = 0;
	//System.out.println("Trying to get to: " + iSink);
	while(i.hasNext()) {
	    Path tPath = (Path)i.next();
	    //System.out.print(tPath.toString());
	    if (tPath.length() > 1) {
		String[] tVerts = tPath.getVertices();
		boolean f = true;
		for(int j = 1;j < tVerts.length - 1;j++) {
		    if (tHash.containsKey(tVerts[j])) {
			f = false;
		    }
		}
		if (f) {
		    for (int k = 1;k < tVerts.length - 1;k++) {
			tHash.put(tVerts[k], new Integer(0));
		    }
		    //System.out.println(" (*) ");
		    c++;
		} else {
		    //System.out.print("\n");
		}
	    }
	}
	//System.out.println(c);
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
			Path tPath = new Path(iPath);
			//System.out.println(tPath.toString());
			iPaths.add(tPath);
			iPath.pop();
			//break;
		    } else {
			// recurse on child tree
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
