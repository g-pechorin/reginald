package to.uk.orangedog.reginald.util;

public class SubStringer {
	private final String	substring;
	public int				index	= 0;

	@Override
	public String toString() {
		return substring.substring(index);
	}

	public SubStringer(final String substring) {
		this.substring = substring;
	}

	public boolean isEmpty() {
		return toString().equals("");
	}

	public String consume(final int i) {
		final String consumed = toString().substring(0, i);

		index += i;

		return consumed;
	}
}