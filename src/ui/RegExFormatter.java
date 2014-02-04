package ui;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.text.DefaultFormatter;

/**
 * Formatter class used to generate Format objects to instantiate JFormattedTextFields
 * 
 * NOTE: This was never successfully implemented. I got things connected properly however,
 * I couldn't fix the issue I was having with the regular expression being displayed in
 * the JFormattedTextFields.
 * 
 * @author Slaiy
 */
public class RegExFormatter extends DefaultFormatter {
		private static final long serialVersionUID = 1L;
		private Pattern pattern;
	    private Matcher matcher;
	    
	    /**
	     * Default constructor
	     */
	    public RegExFormatter() {
	        super();
	    }

	    /**
	     * Constructor setting the String pattern for the formatter
	     * @param pattern to be set for formatter
	     * @throws PatternSyntaxException
	     */
	    public RegExFormatter(String pattern) throws PatternSyntaxException {
	        this();
	        setPattern(Pattern.compile(pattern));
	    }

	    /**
	     * Constructor setting the Pattern for the formatter
	     * @param pattern to be set for formatter
	     */
	    public RegExFormatter(Pattern pattern) {
	        this();
	        setPattern(pattern);
	    }

	    /**
	     * Mutator used to set the Pattern
	     * @param pattern to set
	     */
	    public void setPattern(Pattern pattern) {
	        this.pattern = pattern;
	    }

	    /**
	     * Accessor used to access the Pattern
	     * @return a reference to the pattern
	     */
	    public Pattern getPattern() {
	        return pattern;
	    }

	    /**
	     * Mutator used to set the Matcher
	     * @param matcher to set
	     */
	    protected void setMatcher(Matcher matcher) {
	        this.matcher = matcher;
	    }

	    /**
	     * Accessor used to access the Matcher
	     * @return a reference to the matcher
	     */
	    protected Matcher getMatcher() {
	        return matcher;
	    }
	    
	    /**
	     * Checks if the pattern and text match.
	     */
	    @Override
	    public Object stringToValue(String text) throws ParseException {
	        Pattern pattern = getPattern();
	        if (pattern != null) {
	            Matcher matcher = pattern.matcher(text);
	            if (matcher.matches()) {
	                setMatcher(matcher);
	                return super.stringToValue(text);
	            }
	            throw new ParseException("Pattern doesn't match!", 0);
	        }
	        return text;
	    }
}