import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class GraphGUI {
    public static JFrame iMainWindow = new JFrame("3hproj - v1 - r.j.kendall@durham.ac.uk");
    public static Container iContentPane = iMainWindow.getContentPane();
    public static JPanel iControlPanel = new JPanel();
    public static JLabel iImageDisplay = new JLabel("", JLabel.CENTER);
    public static JLabel iGraphFile = new JLabel("No file open.");
    public static JLabel iAlgoStatus = new JLabel("No algorithm running.", JLabel.CENTER);
    public static JProgressBar iProgBar = new JProgressBar();
    public static JTextArea iTextLog = new JTextArea(30, 30);
    public static JMenuBar iMenu = new JMenuBar();
    public static GraphEditor iEditor = new GraphEditor();

    // menu bar items
    public static JMenu iFileMenu = new JMenu("File");
    public static JMenuItem iOpenOpt = new JMenuItem("Open...");
    public static JMenuItem iSaveOpt = new JMenuItem("Save...");
    public static JMenuItem iNewOpt = new JMenuItem("New");
    public static JMenuItem iProps = new JMenuItem("Properties...");
    public static JMenuItem iExit = new JMenuItem("Exit");
    public static JMenu iConnecMenu = new JMenu("Connectivity");
    public static JMenuItem iRunKAlgo = new JMenuItem("Run k-(edge-)connectivity algorithm");
    public static JMenuItem iRunVWPath = new JMenuItem("Highlight v,w edge disjoint paths");
    public static JMenuItem iRunVWPath2 = new JMenuItem("Highlight v,w vertex disjoint paths");
    public static JMenuItem iCutSet = new JMenuItem("Highlight v,w cut-sets");
    public static JMenuItem iCutSetV = new JMenuItem("Highlight v,w minimal sep-sets");
    public static JMenu iNetworkMenu = new JMenu("Network");
    public static JMenuItem iMaxFlow = new JMenuItem("Maximum flow between v,w");
    public static JMenuItem iMST = new JMenuItem("Show minimum spanning tree");
    public static JMenu iAugMenu = new JMenu("Augmentation");
    public static JMenuItem iEdgeAug = new JMenuItem("Augment to k-edge-connected...");
    public static JMenu iHelpMenu = new JMenu("Help");

    public static ConnecMenuListener iCML = new ConnecMenuListener();
    
    // graph stuff
    public static Graph iGraph = new Graph();
    public static Connectivity iConnec = null;

    // file stuff
    public static String iFile = new String("");

    // operating env stuff;
    public static String iNeatoPath = new String("");

    // sync
    public static boolean iRunFlag = false;
    public static String iAlgoName = new String("");

    public static void main(String[] args) {
	iImageDisplay.setBackground(new Color(255,255,255));
	iMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	iControlPanel = new JPanel(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	c.fill = GridBagConstraints.HORIZONTAL;
	
	//c.weightx = 0.5;
	c.gridx = 0;
	c.gridy = 0;
	iControlPanel.add(iGraphFile, c);

	JScrollPane tTextS = new JScrollPane(iTextLog);
	tTextS.setPreferredSize(new Dimension(310, 544));
	c.gridx = 0;
	c.gridy = 1;
	//c.gridheight = 2;
	iControlPanel.add(tTextS, c);
	
	JScrollPane tImageS = new JScrollPane(iImageDisplay);
	tImageS.setPreferredSize(new Dimension(700, 560));
	c.gridx = 1;
	c.gridy = 0;
	c.gridheight = 2;
	iControlPanel.add(tImageS, c);

	c.gridx = 1;
	c.gridy = 2;
	c.gridheight = 1;
	iControlPanel.add(iEditor, c);

	c.gridx = 0;
	iControlPanel.add(iProgBar, c);

	// set-up menu
	iOpenOpt.addActionListener(new OpenButtonListener());
	iExit.addActionListener(new ExitButtonListener());
	iFileMenu.add(iNewOpt);
	iNewOpt.addActionListener(iCML);
	iFileMenu.add(iSaveOpt);
	iSaveOpt.addActionListener(iCML);
	iFileMenu.add(iOpenOpt);
	iFileMenu.add(iProps);
	iProps.addActionListener(iCML);
	iFileMenu.add(iExit);
	
	iConnecMenu.add(iRunKAlgo);
	iRunKAlgo.addActionListener(iCML);
	iConnecMenu.add(iRunVWPath);
	iRunVWPath.addActionListener(iCML);
	iConnecMenu.add(iRunVWPath2);
	iConnecMenu.add(iCutSet);
	iCutSet.addActionListener(iCML);
	iConnecMenu.add(iCutSetV);
	iCutSetV.addActionListener(iCML);
	
	// iNetworkMenu.add(iShortPathD);
	iNetworkMenu.add(iMaxFlow);
	iMaxFlow.addActionListener(iCML);
	iNetworkMenu.add(iMST);
	iMST.addActionListener(iCML);

	iAugMenu.add(iEdgeAug);
	iEdgeAug.addActionListener(iCML);

	iMenu.add(iFileMenu);
	iMenu.add(iConnecMenu);
	iMenu.add(iNetworkMenu);
	iMenu.add(iAugMenu);
	//iMenu.add(iHelpMenu);
	
	iProgBar.setStringPainted(true);
	iProgBar.setString("No algorithm running.");

	// main window stuff
	Image img = Toolkit.getDefaultToolkit().getImage(java.net.URLClassLoader.getSystemResource("icon.gif"));
	iContentPane.add(iControlPanel, BorderLayout.CENTER);
	iMainWindow.setJMenuBar(iMenu);
	iMainWindow.setSize(1100, 650);
	iMainWindow.setIconImage(img);
	iMainWindow.setVisible(true);
	//iMainWindow.pack();

	String tNeato = System.getProperty("neato");
	
	if (tNeato != null) {
	    if(!tNeato.equals("")) {
		iNeatoPath = tNeato;
		System.out.println("GraphViz tool neato found: " + iNeatoPath);
		iImageDisplay.setIcon(new ImageIcon("logo.gif"));
	    } else {
		iImageDisplay.setIcon(new ImageIcon("error.gif"));
		iImageDisplay.setText("NEATO environment varible not set!  Graph drawing will not be available.");
	    }
	} else {
	    iImageDisplay.setIcon(new ImageIcon("error.gif"));
	    iImageDisplay.setText("NEATO environment varible not set!  Graph drawing will not be available.");
	}
    }

    public static void newGraph() {
	iGraph = new Graph();
	iGraphFile.setText("Not saved yet");
	GraphRenderer tGraph = new GraphRenderer(iGraph, iNeatoPath);
	updateGraphImage(tGraph);
	iEditor.setGraph(iGraph);
    }

    public static void openGraphFile() {
	JFileChooser tChooser = new JFileChooser();
	int tRet = tChooser.showOpenDialog(iMainWindow);
	if (tRet == JFileChooser.APPROVE_OPTION) {
	    String tFile = tChooser.getSelectedFile().getAbsolutePath();
	    addToLog("Opening " + tChooser.getSelectedFile().getName());
	    iGraph = new Graph(tFile);
	    iGraphFile.setText(tFile);
	    iFile = tFile;
	    addToLog("\tVertices: " + iGraph.getNumberOfVertices() + "\n\tEdges: " + iGraph.getNumberOfEdges() + "\n");
	    GraphRenderer tGraph = new GraphRenderer(iGraph, iNeatoPath);
	    updateGraphImage(tGraph);
	    iEditor.setGraph(iGraph);
	}
    }

    public static void updateGraph(Graph pGraph) {
	iGraph = pGraph;
	GraphRenderer tGraph = new GraphRenderer(pGraph, iNeatoPath);
	updateGraphImage(tGraph);
    }

    public static void setAlgoRunning(boolean running) {
	iRunFlag = running;
	if(running) {
	    iProgBar.setIndeterminate(true);
	    iProgBar.setString("Algorithm running...");
	} else {
	    iProgBar.setIndeterminate(false);
	    iProgBar.setString("No algorithm running.");
	}
    }

    public static void displayResult(AlgorithmResult pResult, long pMs) {
	addToLog("...complete; time: " + pMs + "ms.\n\nResults:\n");
	if (iAlgoName.equals("CONNEC")) {
	    addToLog("\t" + pResult.getStringValue());
	}
	if (iAlgoName.equals("MAXFLOW")) {
	    addToLog("\tMaxflow: " + pResult.getIntValue());
	}
	addToLog("");
    }

    public static void runConnectivity() {
	addToLog("Running k-(edge-)connectivity algorithm...");
	iAlgoName = "CONNEC";
	RunAlgorithm tRun = new RunAlgorithm(new Connectivity(), new Hashtable(), iGraph);
	tRun.start();
    }

    public static void findMaxFlow(final String pSource, final String pSink) {
	addToLog("Running Ford-Fulkerson maximum flow algorithm...");
	Hashtable tParams = new Hashtable();
	tParams.put("SOURCE", pSource);
	tParams.put("SINK", pSink);
	iAlgoName = "MAXFLOW";
	RunAlgorithm tRun = new RunAlgorithm(new MaximumFlow3(), tParams, iGraph);
	tRun.start();
    }

    public static void highlightEdgeDisPaths(final String pSource, final String pSink) {
	addToLog("Finding edge disjoint paths from " + pSource + " to " + pSink + "...");
	long tStart = System.currentTimeMillis();
	MaximumFlow3 tMax = new MaximumFlow3(pSource, pSink, iGraph);
	tMax.run();
	long tTotal = (System.currentTimeMillis() - tStart);
	addToLog("...complete; time: " + (tTotal / 1000) + " seconds.\n");
	ArrayList tPaths = tMax.getPaths();
	addToLog("Results (" + tPaths.size() + " edge disjoint paths found):");
	Iterator i = tPaths.iterator();
	GraphRenderer tGraph = new GraphRenderer(iGraph, iNeatoPath);
	RandomColour tRand = new RandomColour();
	tGraph.setColouredVertex(pSource, 255, 0, 0);
	tGraph.setColouredVertex(pSink, 0, 0, 255);
	while(i.hasNext()) {
	    Path tPath = (Path)i.next();
	    addToLog("\t[*] " + tPath.toString());
	    String[] tEdges = tPath.getEdges();
	    for(int j = 0;j < tEdges.length;j++) {
		tGraph.setColouredEdge(tEdges[j], tRand.red(), tRand.green(), tRand.blue());
		tGraph.setColouredEdge(revEdge(tEdges[j]), tRand.red(), tRand.green(), tRand.blue());
	    }
	    tRand.increment();
	}
	addToLog("");
	updateGraphImage(tGraph);
    }
    
    public static void highlightVertexDisjointPaths(final String pSource, final String pSink) {
	// still to do
    }

    public static void augment(int k) {
	//System.out.println("Augment called...");
	addToLog("Running edge-augmentation algorithm...");
	long tStart = System.currentTimeMillis();
	MinimumSpanningTree tMST = new MinimumSpanningTree(iGraph);
	tMST.run();
	Graph tGraph = tMST.newGraph();
	ModifyGraph tModify = new ModifyGraph(tGraph, k);
	long tTotal = (System.currentTimeMillis() - tStart);
	addToLog("...complete; time: " + (tTotal / 1000) + " seconds. \n\nAdding edges:\n" + tModify.getLog());
	updateGraph(tGraph);
	iEditor.setGraph(tGraph);
    }

    public static void findCutSet(String pSource, String pSink) {
	addToLog("Running v,w cut-set finding algorithm...");
	long tStart = System.currentTimeMillis();
	CutSet tCut = new CutSet(iGraph, pSource, pSink);
	tCut.run();
	long tTotal = (System.currentTimeMillis() - tStart);
	addToLog("...complete; time: " + (tTotal / 1000) + " seconds. \n\nEdges:\n" + tCut.getLog());
	GraphRenderer tGraph = new GraphRenderer(iGraph, iNeatoPath);
	ArrayList tList = tCut.getEdges();
	Iterator i = tList.iterator();
	while(i.hasNext()) {
	    String tEdge = (String)i.next();
	    tGraph.setColouredEdge(tEdge, 255, 0, 0);
	    tGraph.setColouredEdge(revEdge(tEdge), 255, 0, 0);
	}
	updateGraphImage(tGraph);
    }

    public static void findMinSepSet(String pSource, String pSink) {
	addToLog("Running v,w min sep set finding algorithm...");
	long tStart = System.currentTimeMillis();
	MinSepSet tMin = new MinSepSet(iGraph, pSource, pSink);
	tMin.run();
	long tTotal = (System.currentTimeMillis() - tStart);
	addToLog("...complete; time: " + (tTotal / 1000) + " seconds.\n\nVertices:\n" + tMin.getLog());
	GraphRenderer tGraph = new GraphRenderer(iGraph, iNeatoPath);
	ArrayList tList = tMin.getVertices();
	Iterator i = tList.iterator();
	while(i.hasNext()) {
	    String tVert = (String)i.next();
	    tGraph.setColouredVertex(tVert, 255, 0, 0);
	}
	updateGraphImage(tGraph);
    }

    public static void showMST() {
	addToLog("Running MST finding algorithm...");
	long tStart = System.currentTimeMillis();
	MinimumSpanningTree tMST = new MinimumSpanningTree(iGraph);
	tMST.run();
	long tTotal = (System.currentTimeMillis() - tStart);
	addToLog("... complete; time: " + (tTotal / 1000) + " seconds.\n\nEdges:\n");
	GraphRenderer tGraph = new GraphRenderer(iGraph, iNeatoPath);
	ArrayList tList = tMST.getEdges();
	Iterator i = tList.iterator();
	while(i.hasNext()) {
	    String tEdge = (String)i.next();
	    addToLog("\t[*] (" + tEdge + ")");
	    tGraph.setColouredEdge(tEdge, 255, 0, 0);
	    tGraph.setColouredEdge(revEdge(tEdge), 255, 0, 0);
	}
	addToLog("");
	updateGraphImage(tGraph);
    }

    public static void updateGraphImage(final GraphRenderer tGraph) {
	if (!iNeatoPath.equals("")) {
	    try {
		File tFile = File.createTempFile("3hproj", ".gif");
		iImageDisplay.setText("Image is loading...");
		tGraph.render(tFile.getAbsolutePath());
		iImageDisplay.setText("");
		iImageDisplay.setIcon(null);
		iImageDisplay.repaint();
		iImageDisplay.setIcon(new ImageIcon(tFile.getAbsolutePath()));
		iImageDisplay.repaint();
	    } catch (IOException e) {
		iImageDisplay.setText("Unable to load the image, is GraphViz available?");
		System.out.println("Could not create temp file for the graph image.");
	    }
	}
    }

    public static String strRev(final String input) {
	char data[] = {input.charAt(1), input.charAt(0)};
	return new String(data);
    }

    public static String revEdge(final String input) {
	String tFrom = input.substring(0, input.indexOf(","));
	String tTo = input.substring(input.indexOf(",") + 1);
	return new String(tTo + "," + tFrom);
    }

    public static void addToLog(final String append) {
	iTextLog.setText(iTextLog.getText() + append + "\n");
    }
}
