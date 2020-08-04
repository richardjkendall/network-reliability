public class EdgeCell {
    int iCap = 0;
    Tree iTree = null;

    public EdgeCell(int pCap, Tree pTree) {
	iCap = pCap;
	iTree = pTree;
    }
    
    public int getCap() {
	return iCap;
    }

    public Tree getTree() {
	return iTree;
    }
}
