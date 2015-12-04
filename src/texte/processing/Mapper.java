package texte.processing;

public interface Mapper<From, To> {
	public To map(From f);
}
