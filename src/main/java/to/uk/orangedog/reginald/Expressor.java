package to.uk.orangedog.reginald;


import java.util.LinkedHashMap;
import java.util.TreeSet;
import java.util.regex.Pattern;



public class Expressor {

	public ExpressorValue getValue(final int i) {
		return new ExpressorValue(this, Expressor.this.start.toArray()[i % Expressor.this.start.size()].toString());
	}

	public ExpressorValue getValue() {

		if (Expressor.this.start.size() != 1) {
			throw new IllegalArgumentException("Index must be defined");
		}

		return getValue(0);
	}

	public TreeSet<String>								start				= new TreeSet<String>();

	public LinkedHashMap<String, PatternReplacement>	patternReplacements	= new LinkedHashMap<String, PatternReplacement>();

	public class PatternReplacement {
		public final Pattern	pattern;
		public final String		replacement;
		public final String		replacementTrimmed;

		public PatternReplacement(final String pattern, final String replacement) {
			this.pattern = Pattern.compile(pattern);
			this.replacementTrimmed = (this.replacement = replacement).replaceAll("^\\{(.*)\\}$", "$1");
		}
	}

	public void addTransformation(final String pattern, final String replacement) {
		if (patternReplacements.containsKey(pattern)) {
			throw new IllegalArgumentException("pattern `" + pattern + "` already has a replacement");
		}
		patternReplacements.put(pattern, new PatternReplacement(pattern.replaceAll("^\\/(.*)\\/$", "$1"), replacement));
	}

	public void addStart(final String start) {
		if (!this.start.add(start.replaceAll("^\\{(.*)\\}$", "$1"))) {
			throw new IllegalArgumentException("start `" + start + "` already exists");
		}
	}
}
