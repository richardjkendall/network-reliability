public class RandomColour {
    int iPointer = 0;
    
    int[] iRed =   {255,0,  0,  255,31, 207,255,0};
    int[] iGreen = {0,  0,  255,159,159,79, 0,  191};
    int[] iBlue =  {0,  255,0,  0,  159,159,255,255};

    public RandomColour() {
	// nothing as yet
    }

    public void increment() {
	iPointer++;
    }

    public int red() {
	return iRed[iPointer % iRed.length];
    }

    public int green() {
	return iGreen[iPointer % iRed.length];
    }

    public int blue() {
	return iBlue[iPointer % iRed.length];
    }
}
