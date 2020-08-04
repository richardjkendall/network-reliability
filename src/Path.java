import java.util.*;

public class Path implements Comparable {
    ArrayList iEdges = new ArrayList();
    ArrayList iVertices = new ArrayList();

    public Path(final Stack pStack) {
	Object[] tVerts = pStack.toArray();
	//iVertices = pStack;
	for(int i = 0; i < (tVerts.length - 1);i++) {
	    String tFrom = (String)tVerts[i];
	    String tTo = (String)tVerts[i + 1];
	    iEdges.add(tFrom + "," + tTo);
	    iVertices.add(tFrom);
	    //System.out.println(tFrom + tTo);
	}
	iVertices.add((String)tVerts[tVerts.length - 1]);
	//System.out.println("Number of vertices in path: " + iVertices.size());
    }

    public String[] getEdges() {
	String[] tStr = new String[iEdges.size()];
	Iterator i = iEdges.iterator();
	int c = 0;
	while(i.hasNext()) {
	    tStr[c] = (String)i.next();
	    c++;
	}
	return tStr;
    }

    public String[] getVertices() {
	String[] tStr = new String[iVertices.size()];
	Iterator i = iVertices.iterator();
	int c = 0;
	while(i.hasNext()) {
	    tStr[c] = (String)i.next();
	    c++;
	}
	return tStr;
    }

    public int length() {
	//System.out.println("NV's now: " + iVertices.size());
	return iVertices.size();
    }

    public String toString() {
	String tRet = new String("");
	Iterator i = iVertices.iterator();
	int c = 0;
	tRet = "[";
	while(i.hasNext()) {
	    if (c == (iVertices.size() - 1)) {
		tRet = tRet + (String)i.next();
	    } else {
		tRet = tRet + (String)i.next() + ", ";
	    }
	    c++;
	}
	tRet = tRet + "]";
	/*i = iEdges.iterator();
	c = 0;
	while(i.hasNext()) {
	    if (c == (iEdges.size() - 1)) {
		tRet = tRet + (String)i.next();
	    } else {
		tRet = tRet + (String)i.next() + ", ";
	    }
	    c++;
	}
	tRet = tRet + "]";*/
	return tRet;
    }

    public int compareTo(Object o) {
	Path tPath = (Path)o;
	if (tPath.length() > length()) {
	    return -1;
	} else {
	    if (tPath.length() == length()) {
		return 0;
	    } else {
		return 1;
	    }
	}
    }

    /*public int compareTo(Object o) {
	Path tPath = (Path)o;
	String[] tRPathVerts = tPath.getVertices();
	String [] tPathVerts = getVertices();
	int c = 0;
	for(int i = 1;i < tPathVerts.length;i++) {
	    for(int j = 1;j < tRPathVerts.length;j++) {
		if (tPathVerts[i].equals(tRPathVerts[j])) {
		    c++;
		}
	    }
	}
	if (((c / (tPath.length() + length()) / 2) * 100) > 50) {
	    return 1;
	} else {
	    return -1;
	}
	}*/
}
