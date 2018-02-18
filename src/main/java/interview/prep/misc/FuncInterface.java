package interview.prep.misc;

@FunctionalInterface
public interface FuncInterface {
	public abstract int doCalc();

	default int myDef() {
		return 1;
	}
}
