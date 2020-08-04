import java.util.*;
import java.io.*;
import java.awt.Color;

public class GraphRenderer {
    private Graph iGraph;
    private Hashtable iColouredVertices = new Hashtable();
    private Hashtable iColouredEdges = new Hashtable();
    private String iNeatoPath = new String("");
    
    public GraphRenderer(final Graph pGraph, final String pNeatoPath) {
	iGraph = pGraph;
	iNeatoPath = pNeatoPath;
    }

    public void setColouredVertex(final String pVertex, final int red, final int green, final int blue) {
	iColouredVertices.put(pVertex, new Color(red, green, blue));
    }
    
    public void unsetColouredVertex(final String pVertex) {

    }

    public void setColouredEdge(final String pEdge, final int red, final int green, final int blue) {
	iColouredEdges.put(pEdge, new Color(red, green, blue));
    }

    public String hexRep(final int in) {
	int d = in;
	String tStr = new String("");
	while(d != 0) {
	    int r = d % 16;
	    if (r < 10) {
		tStr = tStr + r;
	    } else {
		char tc = 55;
		tc += r;
		tStr = tStr + new Character(tc).toString();
	    }
	    d /= 16;
	    //System.out.println(d + " r: " + r);
	}
	if (tStr.length() == 0) {
	    tStr = "00";
	} else {
	    if (tStr.length() == 1) {
		tStr = "0" + tStr;
	    }
	}
	return tStr;
    }

    public void render(final String pFilename) {
	String tRet = new String("graph grp {\n");
	ArrayList tList = iGraph.vertexArrayList();
	Hashtable tUsed = new Hashtable();
	Iterator i = tList.iterator();
	//tRet = tRet + "\tsize=\"12,10\";\n";
	tRet = tRet + "\t[node shape=circle];\n";
	while(i.hasNext()) {
	    Vertex tVertex = (Vertex)i.next();
	    Edge[] tEdges = tVertex.getOutEdges();
	    for(int j = 0;j < tEdges.length;j++) {
		if (!tUsed.containsKey(tVertex.getVertexName() + tEdges[j].getEdgeTo()) && !tUsed.containsKey(tEdges[j].getEdgeTo() + tVertex.getVertexName())) {
		    tUsed.put(tVertex.getVertexName() + tEdges[j].getEdgeTo(), new Integer(0));
		    tUsed.put(tEdges[j].getEdgeTo() + tVertex.getVertexName(), new Integer(0));
		    tRet = tRet + "\t" + tVertex.getVertexName() + " -- " + tEdges[j].getEdgeTo() + " [ width=2, label = \"" + tEdges[j].getWeight() + "\", fontsize=8";
		    //tRet = tRet + "\t" + tVertex.getVertexName() + " -- " + tEdges[j].getEdgeTo() + " [ width=2, fontsize=8";
		    if (iColouredEdges.containsKey(tVertex.getVertexName() + "," + tEdges[j].getEdgeTo())) {
			Color tCol = (Color)iColouredEdges.get(tVertex.getVertexName() + "," + tEdges[j].getEdgeTo());
			int red = tCol.getRed();
			int green = tCol.getGreen();
			int blue = tCol.getBlue();
			tRet = tRet + ", color=\"#" + hexRep(red) + hexRep(green) + hexRep(blue) + "\"];\n";
		    } else {
			tRet = tRet + "];\n";
		    }
		}
	    }
	}
	char eof = 26;
	Enumeration tEnum = iColouredVertices.elements();
	Enumeration tVerts = iColouredVertices.keys();
	while (tEnum.hasMoreElements()) {
	    Color tCol = (Color)tEnum.nextElement();
	    int red = tCol.getRed();
	    int green = tCol.getGreen();
	    int blue = tCol.getBlue();
	    
	    String tStr = (String)tVerts.nextElement();
	    tRet = tRet + tStr + " [color=\"#" + hexRep(red) + hexRep(green) + hexRep(blue) + "\"];\n";
	}
	tRet = tRet + "}\n";
	
	try {
	    PrintWriter tOut = new PrintWriter(new BufferedWriter(new FileWriter("temp.tmp")));
	    tOut.print(tRet);
	    tOut.close();
	} catch (IOException e) {
	    System.out.println(" ** Could not write graphviz data to file");
	}

	//System.out.print(tRet);
	try {
	    ArrayList tProcOutput = new ArrayList();
	    Process tProc = Runtime.getRuntime().exec(iNeatoPath + " -Tgif temp.tmp");
	    InputStream tDataIn = tProc.getInputStream();
	    //OutputStream tDataOut = tProc.getOutputStream();
	    //PrintWriter tPrint = new PrintWriter(tDataOut);
	    //tPrint.print(tRet);
	    byte[] tByte = new byte[1024];
	    FileOutputStream tFOut = new FileOutputStream(pFilename);
	    while (tDataIn.read(tByte, 0, 1024) != -1) {
		tFOut.write(tByte);
	    }
	    tFOut.close();
	    tProc.destroy();
	} catch (IOException e) {
	    System.out.println(" ** Could not output data to image file");
	}
	
    }
}
