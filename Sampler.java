// Sampler.java
// interface for samplers
// COMP90056 2018s2
// Assignment B
// William Holland

public interface Sampler<T> {

    public void add(T item, int value);
    public T output();
}
