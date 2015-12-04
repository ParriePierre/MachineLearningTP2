package texte.processing;

public interface Vector<T> {
	public T get(int i);

	public double prodScal(Vector<T> v);

	public void set(int i, T val);
}
