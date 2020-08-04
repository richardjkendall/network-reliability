import java.util.*;

public class EdgeCellComparator implements Comparator {

    public int compare(Object a, Object b) {
	EdgeCell tA = (EdgeCell)a;
	EdgeCell tB = (EdgeCell)b;
	if (tA.getCap() > tB.getCap()) {
	    return -1;
	} else {
	    if (tA.getCap() == tB.getCap()) {
		return 0;
	    } else {
		return 1;
	    }
	}
    }
}
