import java.util.*;

public interface GraphAlgorithm {
    public void setGraph(Graph pGraph);
    public void setParameters(Hashtable pParams);
    public void run();
    public AlgorithmResult answer();
}
