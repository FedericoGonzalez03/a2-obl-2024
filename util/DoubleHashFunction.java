package util;

public interface DoubleHashFunction<T> extends HashFunction<T> {
	
	Integer secondHash();

}
