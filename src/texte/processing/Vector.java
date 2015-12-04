package texte.processing;

public interface Vector<T> {
	public T get(int i);

	public void set(int i, T val);

	public double prodScal(Vector<T> v);
}
