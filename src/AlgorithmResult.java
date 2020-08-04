public class AlgorithmResult {
    private Object iData = null;

    public AlgorithmResult(Object pResult) {
	iData = pResult;
    }

    public int getIntValue() {
	return ((Integer)iData).intValue();
    }

    public String getStringValue() {
	return (String)iData;
    }
}
