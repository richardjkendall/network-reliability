/* 
   Idea to use a LinkedList to implement a queue came from
   http://www.developer.com/java/data/article.php/3296821 (4th November 2004)
*/

import java.util.LinkedList;

public class Queue {
    
    private LinkedList iItems = new LinkedList();
    
    public Queue() {
	// does nothing
    }

    public void push (final Object pNewElement) {
	iItems.add(pNewElement);
    }

    public Object pop() throws Exception {
	//System.out.println("POP called " + iItems.size() + " elements in queue");
	if (iItems.size() == 0) {
	    throw new Exception();
	} else {
	    return iItems.removeFirst();
	}
    }

    public boolean empty() {
	return (iItems.size() == 0);
    }

    public String toString() {
	String tRet = new String("[");
	Object[] tItems = iItems.toArray();
	for(int i = 0;i < tItems.length;i++) {
	    if (i == (tItems.length - 1)) {
		tRet = tRet + tItems[i].toString() + "]";
	    } else {
		tRet = tRet + tItems[i].toString() + ", ";
	    }
	}
	return tRet;
    }
}
