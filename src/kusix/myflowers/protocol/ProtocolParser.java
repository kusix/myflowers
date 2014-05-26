package kusix.myflowers.protocol;

public interface ProtocolParser<T> {
	
	public T parse(String string);
}
