package util;

public interface DoubleHashFunction<T> extends HashFunction<T> {
	
	int secondHash(T element);

}
