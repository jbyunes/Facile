package fr.upd;
import java.io.*;


/**
 * The <code>MyTokenizer</code> class takes an input stream and
 * parses it into "tokens", allowing the tokens to be
 * read one at a time.
 * A token is a word made of characters except "blanks".
 *
 * @author  Jean-Baptiste Yunès
 * @version 1, 15/08/03
 */

public class MyTokenizer {

    private Reader reader = null;

    private char buf[] = new char[80];

    private int lastChar = NEED_CHAR;

    private static final int NEED_CHAR = Integer.MAX_VALUE;
    private static final int ALPHA = 1;
    private static final int BLANK = 2;

    /** The line number of the last token read */
    private int ligne = 1;

    public static final int EOF = -1;
    public static final int TOKEN = 0;

    private String theToken;

    /**
     * Create a tokenizer that parses the given character stream.
     *
     * @param reader a Reader object providing the input stream.
     */
    public MyTokenizer(Reader reader) {
        if (reader == null) {
            throw new NullPointerException();
        }
	this.reader = reader;
    }

    /**
     * Read a single character.
     */
    public int read() throws IOException {
	return reader.read();
    }

    public int nextToken() {
	try {
	    theToken = null;

	    int c = lastChar;
	    if (c < 0) c = NEED_CHAR;
	    if (c == NEED_CHAR) {
		c = read();
		if (c < 0) return EOF;
	    }
	    
	    lastChar = NEED_CHAR;
	    
	    int ctype = c <= ' ' ? BLANK : ALPHA;
	    while ((ctype & BLANK) != 0) {
		if (c == '\r') {
		    ligne++;
		    c = read();
		    if (c == '\n') c = read();
		} else {
		    if (c == '\n') ligne++;
		    c = read();
		}
		if (c < 0) return EOF;
		ctype = c <= ' ' ? BLANK : ALPHA;
	    }

	    int i = 0;
	    do {
		if (i >= buf.length) {
		    char nb[] = new char[buf.length + 80];
		    System.arraycopy(buf, 0, nb, 0, buf.length);
		    buf = nb;
		}
		buf[i++] = (char) c;
		c = read();
		ctype = c <= ' ' ? BLANK : ALPHA;
	    } while ((ctype & ALPHA) != 0);
	    lastChar = c;
	    theToken = String.copyValueOf(buf, 0, i);
	    return TOKEN;
	} catch (IOException ioe) {
	    return EOF;
	}
    }

    public String currentToken() {
	return theToken;
    }

    public int lineno() {
	return ligne;
    }

    public String toString() {
	return "Le token est " + theToken + " en ligne " + ligne;
    }

}
