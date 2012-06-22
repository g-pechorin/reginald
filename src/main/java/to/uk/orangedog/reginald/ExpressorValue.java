package to.uk.orangedog.reginald;


import java.util.regex.Matcher;

import to.uk.orangedog.reginald.Expressor.PatternReplacement;
import to.uk.orangedog.reginald.util.SubStringer;


public class ExpressorValue {
	private final Expressor	expressor;
	public final String		value;

	@Override
	public String toString() {
		return value;
	}

	public ExpressorValue applyExpressions() {

		final StringBuilder result = new StringBuilder();

		final SubStringer subStringer = new SubStringer(value);

		moreGreen: while (!subStringer.isEmpty()) {

			nextReplacer: for (final String key : this.expressor.patternReplacements.keySet()) {
				final PatternReplacement patternReplacement = this.expressor.patternReplacements.get(key);

				final Matcher matcher = patternReplacement.pattern.matcher(subStringer.toString());

				if (!matcher.find()) {
					continue nextReplacer;
				}

				if (matcher.start() != 0) {
					continue nextReplacer;
				}

				final String pattern = patternReplacement.pattern.pattern();
				final int size = matcher.end();
				final String consumed = subStringer.consume(size);
				final String replacementTrimmed = patternReplacement.replacementTrimmed;
				final String replaceAll = consumed.replaceAll(pattern, replacementTrimmed);
				result.append(replaceAll);


				continue moreGreen;
			}

			throw new IllegalArgumentException("No more room for green :(");
		}

		return new ExpressorValue(this.expressor, result.toString());
	}

	ExpressorValue(Expressor expressor, final String value) {
		this.expressor = expressor;
		this.value = value;
	}


	public ExpressorValue applyExpressions(int i) {
		return i == 0 ? this : applyExpressions(i - 1).applyExpressions();
	}
}
