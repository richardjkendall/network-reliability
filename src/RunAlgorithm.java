import java.util.*;

public class RunAlgorithm extends Thread {
    GraphAlgorithm iAlgo;
    Hashtable iParams;
    Graph iGraph;

    public RunAlgorithm(GraphAlgorithm pAlgo, Hashtable pParams, Graph pGraph) {
	iAlgo = pAlgo;
	iParams = pParams;
	iGraph = pGraph;
    }

    public void run() {
	GraphGUI.setAlgoRunning(true);
	iAlgo.setParameters(iParams);
	iAlgo.setGraph(iGraph);
	long tStart = System.currentTimeMillis();
	iAlgo.run();
	long tTotal = (System.currentTimeMillis() - tStart);
	GraphGUI.setAlgoRunning(false);
	AlgorithmResult tResult = iAlgo.answer();
	GraphGUI.displayResult(tResult, tTotal);
    }
}
