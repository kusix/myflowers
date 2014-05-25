package kusix.myflowers.protocol;

public interface ProtocolParser<T> {
	
	public T parse(char[] data);
	
	public int getEOF();

}
