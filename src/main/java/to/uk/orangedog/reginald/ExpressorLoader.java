package to.uk.orangedog.reginald;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;

import to.uk.orangedog.reginald.analysis.DepthFirstAdapter;
import to.uk.orangedog.reginald.lexer.Lexer;
import to.uk.orangedog.reginald.lexer.LexerException;
import to.uk.orangedog.reginald.node.AStart;
import to.uk.orangedog.reginald.node.ATransform;
import to.uk.orangedog.reginald.parser.Parser;
import to.uk.orangedog.reginald.parser.ParserException;

public class ExpressorLoader {
	public Expressor parseResource(Class<?> host, final String name) throws ParserException, LexerException, IOException {
		final Expressor expression = new Expressor();


		final InputStream resourceAsStream = host.getResourceAsStream(name + ".expressor");
		assert resourceAsStream != null;
		new Parser(new Lexer(new PushbackReader(new InputStreamReader(resourceAsStream), 1024))).parse().apply(new DepthFirstAdapter() {

			@Override
			public void caseAStart(AStart node) {
				final String text = node.getText().toString().trim();
				assert text.startsWith("{") && text.endsWith("}") : "`" + text + "` is not a valid starting expression";

				expression.addStart(text);
			}

			@Override
			public void caseATransform(ATransform node) {

				String pattern = node.getPattern().toString().trim();
				String replacement = node.getReplacement().toString().trim();

				assert pattern.startsWith("/") && pattern.endsWith("/") : "`" + pattern + "` is not a valid pattern";
				assert replacement.startsWith("{") && replacement.endsWith("}") : "`" + replacement + "` is not a valid replacement";

				expression.addTransformation(pattern, replacement);
			}
		});

		return expression;
	}

	public Expressor parseResource(final Class<?> host) throws ParserException, LexerException, IOException {
		return parseResource(host, host.getSimpleName());
	}
}