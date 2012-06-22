package to.uk.orangedog.reginald;


import java.io.IOException;

import to.uk.orangedog.reginald.lexer.LexerException;
import to.uk.orangedog.reginald.parser.ParserException;
import junit.framework.TestCase;


public class ExpressorTest extends TestCase {

	public Expressor parseExpressor() throws ParserException, LexerException, IOException {
		final ExpressorLoader loader = new ExpressorLoader();

		final Expressor expressor = loader.parseResource(getClass());
		return expressor;
	}

	public void testStart0() throws Exception {
		final Expressor expressor = parseExpressor();

		assertTrue(expressor.start.contains("A"));
	}

	public void testStart1() throws Exception {
		final Expressor expressor = parseExpressor();

		assertTrue(expressor.start.contains("B"));
	}

	public void testStart2() throws Exception {
		final Expressor expressor = parseExpressor();

		assertTrue(expressor.start.contains("CB"));
	}

	public void testTransform0_0() throws Exception {
		final Expressor expressor = parseExpressor();

		assertTrue(expressor.patternReplacements.containsKey("/A/"));
	}

	public void testTransform0_1() throws Exception {
		final Expressor expressor = parseExpressor();

		assertEquals("{AB}", expressor.patternReplacements.get("/A/").replacement);
	}


	public void testAlgae0() throws Exception {
		final ExpressorValue start = parseExpressor().getValue(0);

		assertEquals("A", start.applyExpressions(0).value);
	}

	public void testAlgae1() throws Exception {
		final ExpressorValue start = parseExpressor().getValue(0);

		assertEquals("AB", start.applyExpressions(1).value);
	}


	public void testAlgae2() throws Exception {
		final ExpressorValue start = parseExpressor().getValue(0);

		assertEquals("ABA", start.applyExpressions(2).value);
	}

	public void testAlgae3() throws Exception {
		final ExpressorValue start = parseExpressor().getValue(0);

		assertEquals("ABAAB", start.applyExpressions(3).value);
	}

	public void testAlgae4() throws Exception {
		final ExpressorValue start = parseExpressor().getValue(0);

		assertEquals("ABAABABA", start.applyExpressions(4).value);
	}

	public void testAlgae5() throws Exception {
		final ExpressorValue start = parseExpressor().getValue(0);

		assertEquals("ABAABABAABAAB", start.applyExpressions(5).value);
	}

	public void testAlgae6() throws Exception {
		final ExpressorValue start = parseExpressor().getValue(0);

		assertEquals("ABAABABAABAABABAABABA", start.applyExpressions(6).value);
	}

	public void testAlgae7() throws Exception {
		final ExpressorValue start = parseExpressor().getValue(0);

		assertEquals("ABAABABAABAABABAABABAABAABABAABAAB", start.applyExpressions(7).value);
	}


	public void testTransform1_0() throws Exception {
		final Expressor expressor = parseExpressor();

		assertTrue(expressor.patternReplacements.containsKey("/B/"));
	}

	public void testTransform1_1() throws Exception {
		final Expressor expressor = parseExpressor();

		assertEquals("{A}", expressor.patternReplacements.get("/B/").replacement);
	}

	public void testTransform2_0() throws Exception {
		final Expressor expressor = parseExpressor();

		assertTrue(expressor.patternReplacements.containsKey("/(C)B/"));
	}

	public void testTransform2_1() throws Exception {
		final Expressor expressor = parseExpressor();

		assertEquals("{$1A}", expressor.patternReplacements.get("/(C)B/").replacement);
	}

	public void testTransform3_0() throws Exception {
		final Expressor expressor = parseExpressor();

		assertTrue(expressor.patternReplacements.containsKey("/(C)A/"));
	}

	public void testTransform3_1() throws Exception {
		final Expressor expressor = parseExpressor();


		assertEquals("{$1BA}", expressor.patternReplacements.get("/(C)A/").replacement);
	}

}
