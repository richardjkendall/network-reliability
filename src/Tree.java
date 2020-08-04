import java.util.*;

public class Tree {
    private String iData = new String();
    private Hashtable iChildren = new Hashtable();
    
    public Tree() {
	// empty constructor for algo
    }

    public Tree(final String pData) {
	// nothing
	iData = pData;
    }

    public Tree getChild(final String pNode) {
	return (Tree)iChildren.get(pNode);
    }
    
    public void updateChild(final String pNode, final Tree pTree) {
	iChildren.put(pNode, pTree);
    }
    
    public String getData() {
	return iData;
    }

    public boolean noChildren() {
	return iChildren.size() == 0;
    }
    
    public Hashtable getChildren() {
	return iChildren;
    }
    
    public void printTree(Tree pTree) {
	Queue tPrint = new Queue();
	Queue tLevel = new Queue();
	tLevel.push(new Integer(0));
	tPrint.push(pTree);
	while (!tPrint.empty()) {
	    try {
		Tree tTree = (Tree)tPrint.pop();
		int level = ((Integer)tLevel.pop()).intValue();
		System.out.println("l: " + level + "; " + tTree.getData());
		if (!tTree.noChildren()) {
		    Enumeration tOut = tTree.getChildren().elements();
		    while(tOut.hasMoreElements()) {
		      Tree tSubTree = (Tree)tOut.nextElement();
		      tPrint.push(tSubTree);
		      tLevel.push(new Integer((level + 1)));
		    }
		} 
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }
}
