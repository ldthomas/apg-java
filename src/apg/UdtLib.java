package apg;
/**
 * This is the parent class for a library of User-Defined Terminals (UDTs).
 * UDTs are provided for some simple and repetitive phrases that are common
 * to most languages designed to be in human-readable, ASCII text.
 */
public class UdtLib {
	private final static char SP = (char)32;
	private final static char HTAB = (char)9;
	private final static char LF = '\n';
	private final static char CR = '\r';
	private final static char PRINTING_BEG = (char)32;
	private final static char PRINTING_END= (char)126;
	private final static char SEMI = ';';
	private final static char SLASH = '/';
	private final static char STAR = '*';
	private final static char DQUOTE = '"';
	private final static char SQUOTE = '\'';
	private UdtLib(){}
	
	// public API - the UDTs
	/**
	 *  UDT for recognizing alpha-numeric phrases.
	 *  Alpha-numeric phrases begin with an alphabetic character
	 *  <code>a-z</code> or <code>A-Z</code>. Characters after the first
	 *  may be alphabetic or numeric, <code>0-9</code>.
	 */
	public static class Alphanum extends Parser.UdtCallback{
		private final char otherChar;
		private final boolean hasOtherChar;
		/**
		 * Default constructor.
		 * @param p the Parser object the UDT is attached to.
		 */
		public Alphanum(Parser p){
			super(p);
			this.otherChar = (char)0;
			this.hasOtherChar = false;
		}
		/**
		 * Constructor defining one other character which is allowed 
		 * in addition to alpha-numeric after the first.
		 * @param p the Parser object the UDT is attached to.
		 * @param otherChar the other character to allow.
		 */
		public Alphanum(Parser p, char otherChar){
			super(p);
			this.otherChar = otherChar;
			this.hasOtherChar = true;
		}
		@Override public int callback(int offset){
			int len = -1;
			int i = offset;
			char c;
			if(i < callbackData.inputString.length){
				c = callbackData.inputString[i];
				if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')){
					i++;
					len = 1;
					for(; i < callbackData.inputString.length; i++){
						c = callbackData.inputString[i];
						if((c >= 'a' && c <= 'z') ||
								(c >= 'A' && c <= 'Z') ||
								(c >= '0' && c <= '9') ||
								(hasOtherChar && c == otherChar)){
							len++;
						} else{break;}
					}
				}
			}
			return len;
		}
	}
	/** UDT for recognizing a string of any printing characters.
	 * That is, ASCII characters <code>32-126</code>.
	 */
	public static class Any extends Parser.UdtCallback{
		private final char begin;
		private final char end;
		private final boolean spaceAllowed;
		/**
		 * The default constructor.
		 * @param p the Parser object the UDT is attached to.
		 */
		public Any(Parser p){
			super(p);
			this.begin = (char)32;
			this.end = (char)126;
			this.spaceAllowed = true;
		}
		/**
		 * Constructor to include/exclude spaces (<code>x20</code>) from the set of allowed characters.
		 * @param p the Parser object the UDT is attached to.
		 * @param spaceAllowed if true, spaces are included, if false, spaces are excluded.
		 */
		public Any(Parser p, boolean spaceAllowed){
			super(p);
			if(spaceAllowed){
				this.begin = (char)32;
				this.end = (char)126;
				this.spaceAllowed = true;
			} else{
				this.begin = (char)33;
				this.end = (char)126;
				this.spaceAllowed = false;
			}
		}
		@Override public int callback(int offset){
			int len = 0;
			if(spaceAllowed){
				for(int i = offset; i < callbackData.inputString.length; i++){
					char c = callbackData.inputString[i];
					if((c >= begin && c <= end) || c == HTAB){len++;}
					else{break;}
				}
			} else{
				for(int i = offset; i < callbackData.inputString.length; i++){
					char c = callbackData.inputString[i];
					if((c >= begin && c <= end)){len++;}
					else{break;}
				}
			}
			return len;
		}
	}
	/**
	 * UDT for recognizing the C-language comments.
	 * These are strings of all UTF-8 characters which begin with the phrase "<code>/*</code>"
	 * and end with "<code>*&#47;</code>". Nesting of beginning and ending phrases not allowed.   
	 */
	public static class CComment extends Parser.UdtCallback{
		/**
		 * The default constructor.
		 * @param parser the Parser object the UDT is attached to.
		 */
		public CComment(Parser parser){super(parser);}
		@Override public int callback(int offset){
			int len = 0;
			int i = offset;
			int inlen = callbackData.inputString.length;
			int ii = i + 1;;
			char c = callbackData.inputString[i];
			if((i+1) < inlen && c == SLASH && callbackData.inputString[ii] == STAR){
				len += 2;
				i += 2;
				ii = i + 1;
				for(; ii < inlen; i++, ii++){
					c = callbackData.inputString[i];
					if(c == STAR && callbackData.inputString[ii] == SLASH){len += 2; break;}
					if((c >= PRINTING_BEG && c <= PRINTING_END) ||
							c == HTAB ||
							c == LF ||
							c == CR){len++;}
					else{len = -1; break;}
				}
			}
			return (len > 0) ? len : -1;
		}
	}
	/**
	 * UDT for many common comment formats.
	 * Comments are defined by one or two leading characters and the comment
	 * will include all following characters up to but not including 
	 * the next line end character (<code>x0A</code> or <code>x0D</code>)
	 */
	public static class Comment extends Parser.UdtCallback{
		protected final char begin1;
		protected final char begin2;
		protected boolean isDoubleChar;
		/**
		 * Constructor for a comment beginning with a single character.
		 * @param parser the Parser object this UDT is attached to.
		 * @param begin the first character which defines the beginning of the comment.
		 */
		public Comment(Parser parser, char begin){
			super(parser);
			this.begin1 = begin;
			this.begin2 = 0;
			this.isDoubleChar = false;
		}
		/**
		 * Constructor for a comment beginning with a pair of characters
		 * @param parser the Parser object this UDT is attached to.
		 * @param begin1 the first character of the beginning pair.
		 * @param begin2 the second character of the beginning pair.
		 */
		public Comment(Parser parser, char begin1, char begin2){
			super(parser);
			this.begin1 = begin1;
			this.begin2 = begin2;
			this.isDoubleChar = true;
		}
		@Override public int callback(int offset){
			int len = 0;
			int i = offset;
			int inlen = callbackData.inputString.length;
			char c;
			if(!isDoubleChar){
				if(i < inlen && callbackData.inputString[i] == begin1){
					len++;
					i++;
					for(; i < inlen; i++){
						c = callbackData.inputString[i];
						if((c >= PRINTING_BEG && c <= PRINTING_END) || c == HTAB){len++;}
						else{break;}
					}
				}
			} else{
				if((i+1) < inlen && callbackData.inputString[i] == begin1 && 
						callbackData.inputString[i+1] == begin2){
					len += 2;
					i += 2;
					for(; i < inlen; i++){
						c = callbackData.inputString[i];
						if((c >= PRINTING_BEG && c <= PRINTING_END) || c == HTAB){len++;}
						else{break;}
					}
				}
			}
			return (len > 0) ? len : -1;
		}
	}
	/**
	 * UDT for the C++ language comment.
	 * This is an extension of the Comment class which defines
	 * the beginning pair of characters to be "//".
	 */
	public static class CppComment extends Comment{
		/**
		 * The default constructor.
		 * @param parser the Parser object this UDT is attached to.
		 */
		public CppComment(Parser parser){
			super(parser, SLASH, SLASH);
		}
	}

	/**
	 * UDT for the ABNF comment.
	 * This is an extension of the Comment class which defines
	 * the leading comment character to be the semi-colon, "<code>;</code>".
	 */
	public static class SemiComment extends Comment{
		/**
		 * The default constructor.
		 * @param parser the Parser object this UDT is attached to.
		 */
		public SemiComment(Parser parser){
			super(parser, SEMI);
		}
	}

	/**
	 * UDT for a decimal number.
	 * <p>
	 * This is a string of at least <code>min</code> and 
	 * not more than <code>max</code> decimal digits,
	 * where <code>0 &lt;= min &lt;= max</code>.
	 * A decimal digit is <code>0-9</code>.
	 */
	public static class DecNum extends Parser.UdtCallback{
		private final int minDigits, maxDigits;
		private final boolean noLeadingZeros;
		/**
		 * The default constructor.
		 * <p>
		 * The default values are 
		 * <code>min = 0, max = infinite</code>.
		 * Note that <code>min = 0</code>, 
		 * indicates that the decimal number phrase is optional.
		 * Leading zeros are not allowed. 
		 * @param p the Parser object this UDT is attached to.
		 */
		public DecNum(Parser p){
			super(p);
			this.minDigits = 0;
			this.maxDigits = Integer.MAX_VALUE;
			this.noLeadingZeros = false;
		}
		/**
		 * Constructor with choice for whether the number is optional or not.
		 * Leading zeros are not allowed. 
		 * @param p the Parser object this UDT is attached to.
		 * @param optional if true, the number is optional and  <code>min = 0</code>, 
		 * if false <code>min = 1</code>.
		 * For both, <code>max = infinite</code> and leading zeros are not allowed.
		 */
		public DecNum(Parser p, boolean optional){
			super(p);
			this.minDigits = optional ? 0 : 1;
			this.maxDigits = Integer.MAX_VALUE;
			this.noLeadingZeros = false;
		}
		/**
		 * Constructor with explicit choices for all options.
		 * @param p the Parser object this UDT is attached to.
		 * @param minDigits minimum number of digits allowed (must be <code>&gt;= 0</code>)
		 * @param maxDigits maximum number of digits allowed (must be <code>&gt;= minDigits</code>)
		 * @param leadingZeros if true, leading zeros <i>are</i> allowed,
		 * if false, leading zeros are <i>not</i> allowed.
		 */
		public DecNum(Parser p, int minDigits, int maxDigits, boolean leadingZeros){
			super(p);
			this.minDigits = minDigits < 0 ? 0 : minDigits;
			this.maxDigits = maxDigits < minDigits ? minDigits : maxDigits;
			this.noLeadingZeros = !leadingZeros;
		}
		@Override public int callback(int offset){
			int len = -1;
			int i = offset;
			int inlen = callbackData.inputString.length;
			int digits = 0;
			if(noLeadingZeros){if(i < inlen && callbackData.inputString[i] == '0'){return len;}}
			for(; digits < maxDigits && i < inlen; digits++, i++){
				char c = callbackData.inputString[i];
				if(!(c >= '0' && c <= '9')){break;}
			}
			if(digits >= minDigits && digits <= maxDigits){len = digits;}
			return len;
		}
	}
	/**
	 * UDT for the quoted-string.
	 * <p>
	 * A quoted string is a string of any printing ASCII characters
	 * (<code>32-126</code>)
	 * between beginning and ending quote characters, not including the quote character.
	 * "Escaped" character pairs, such as <code>\n</code> or <code>\"</code> not allowed.
	 */
	public static class QuotedString extends Parser.UdtCallback{
		private final char quote;
		/**
		 * Constructor.
		 * @param p the Parser object this UDT is attached to.
		 * @param quote the beginning and ending quote character.
		 */
		public QuotedString(Parser p, char quote){
			super(p);
			this.quote = quote;
		}
		@Override public int callback(int offset){
			int len = 0;
			int i = offset;
			if(i < callbackData.inputString.length && callbackData.inputString[i] == quote){
				len++;
				i++;
				for(; i < callbackData.inputString.length; i++){
					char c = callbackData.inputString[i];
					if(((c >= PRINTING_BEG && c <= PRINTING_END) || c == HTAB) && c != quote){len++;}
					else {break;}
				}
				if(i < callbackData.inputString.length && callbackData.inputString[i] == quote){len++;}
				else{len = -1;} // final quote not found
			}
			return len;
		}
	}
	/**
	 * UDT for the single-quoted string.
	 * The string begins and ends with the single quote <code>' (x27)</code>.
	 */
	public static class SingleQuotedString extends QuotedString{
		/**
		 * Default constructor.
		 * @param p the Parser object this UDT is attached to.
		 */
		public SingleQuotedString(Parser p){super(p, SQUOTE);}
	}

	/**
	 * UDT for the double-quoted string.
	 * The string begins and ends with the double quote <code>" (x22)</code>.
	 */
	public static class DoubleQuotedString extends QuotedString{
		/**
		 * Default constructor.
		 * @param p the Parser object this UDT is attached to.
		 */
		public DoubleQuotedString(Parser p){super(p, DQUOTE);}
	}

	/**
	 * UDT for a hexidecimal number.
	 * <p>
	 * This is a string of at least <code>min</code> and 
	 * not more than <code>max</code> hexidecimal digits,
	 * where <code>0 &lt;= min &lt;= max</code>.
	 * A hexidecimal digit is <code>0-9</code> or <code>A-F</code> (case insensitive).
	 */
	public static class HexNum extends Parser.UdtCallback{
		private final int minDigits, maxDigits;
		/**
		 * The default constructor.
		 * <p>
		 * The default values are 
		 * <code>min = 0, max = infinite</code>.
		 * Note that <code>min = 0</code>, 
		 * indicates that the hexidecimal number phrase is optional. 
		 * @param p the Parser object this UDT is attached to.
		 */
		public HexNum(Parser p){
			super(p);
			this.minDigits = 0;
			this.maxDigits = Integer.MAX_VALUE;
		}
		/**
		 * Constructor with choice for whether the number is optional or not.
		 * @param p the Parser object this UDT is attached to.
		 * @param optional if true, the number is optional and  <code>min = 0</code>, 
		 * if false <code>min = 1</code>.
		 * In either case <code>max = infinite</code>.
		 */
		public HexNum(Parser p, boolean optional){
			super(p);
			this.minDigits = optional ? 0 : 1;
			this.maxDigits = Integer.MAX_VALUE;
		}
		/**
		 * Constructor with explicit choices for all options.
		 * @param p the Parser object this UDT is attached to.
		 * @param minDigits minimum number of digits allowed (must be <code>&gt;= 0</code>)
		 * @param maxDigits maximum number of digits allowed (must be <code>&gt;= minDigits</code>)
		 */
		public HexNum(Parser p, int minDigits, int maxDigits){
			super(p);
			this.minDigits = minDigits < 0 ? 0 : minDigits;
			this.maxDigits = maxDigits < minDigits ? minDigits : maxDigits;
		}
		@Override public int callback(int offset){
			int len = -1;
			int i = offset;
			int inlen = callbackData.inputString.length;
			int digits = 0;
			for(; digits < maxDigits && i < inlen; digits++, i++){
				char c = callbackData.inputString[i];
				if(!((c >= 'a' && c <= 'f') ||
						(c >= 'A' && c <= 'F') ||
						(c >= '0' && c <= '9'))){break;}
				}
			if(digits >= minDigits && digits <= maxDigits){len = digits;}
			return len;
		}
	}
	/**
	 * UDT for a "forgiving" line end. 
	 * Forgiving means that anything resembling either of the two most 
	 * frequent standards will be accepted.
	 * Any of LF (<code>x0A</code>), CRLF (<code>x0Dx0A</code>)or CR (<code>x0D</code>)
	 * are accepted as line ends.
	 */
	public static class ForgivingLineEnd extends LineEnd{
		/**
		 * Default constructor.
		 * @param parser the Parser object this UDT is attached to.
		 */
		public ForgivingLineEnd(Parser parser){
			super(parser);
		}
	}
	/**
	 * UDT for the LF or newline line end.<br>
	 * LF = line feed, newline, <code>\n</code> or <code>x0A</code>
	 */
	public static class LFLineEnd extends LineEnd{
		/**
		 * Default constructor.
		 * The line end is the single newline character, x0A.
		 * @param parser the Parser object this UDT is attached to.
		 */
		public LFLineEnd(Parser parser){
			super(parser, '\n');
		}
	}

	/**
	 * UDT for the standard CRLF line end.<br>
	 * CR = carriage return, <code>\r</code> or <code>x0D</code><br>
	 * LF = line feed, newline, <code>\n</code> or <code>x0A</code>
	 */
	public static class CRLFLineEnd extends LineEnd{
		/**
		 * Default constructor.
		 * The line end is the double character, <code>x0Dx0A</code>.
		 * @param parser the Parser object this UDT is attached to.
		 */
		public CRLFLineEnd(Parser parser){
			super(parser, '\r', '\n');
		}
	}
	/**
	 * UDT for line end characters.
	 * Several conventions are commonly used for line end characters in different common environments.
	 * Flexible options are available.
	 */
	public static class LineEnd extends Parser.UdtCallback{
		private final char end1;
		private final char end2;
		private final int charCount;
		/**
		 * Default constructor. The "forgiving" line end.
		 * Forgiving means that anything resembling either of the two most 
		 * frequent standards will be accepted.
		 * Any of LF (<code>x0A</code>), CRLF (<code>x0Dx0A</code>)or CR (<code>x0D</code>)
		 * are accepted as line ends.
		 * @param p the Parser object this UDT is attached to.
		 */
		public LineEnd(Parser p){
			super(p);
			this.end1 = LF;
			this.end2 = CR;
			this.charCount = 3;
		}
		/**
		 * Constructor for the single-character line end.
		 * @param p the Parser object this UDT is attached to.
		 * @param end the line end character
		 */
		public LineEnd(Parser p, char end){
			super(p);
			this.end1 = end;
			this.end2 = (char)0;
			this.charCount = 1;
		}
		/**
		 * Constructor for the double-character line end.
		 * @param p the Parser object this UDT is attached to.
		 * @param end1 the first of the line end character pair
		 * @param end2 the second of the line end character pair
		 */
		public LineEnd(Parser p, char end1, char end2){
			super(p);
			this.end1 = end1;
			this.end2 = end2;
			this.charCount = 2;
		}
		@Override public int callback(int offset){
			int len = -1;
			int i = offset;
			int inlen = callbackData.inputString.length;
			if(i < inlen){
				if(charCount == 1){
					if(callbackData.inputString[i] == end1){len = 1;}
				} else if(charCount == 2){
					int ii = i + 1;
					if(ii < inlen){
						if(callbackData.inputString[i] == end1 &&
								callbackData.inputString[ii] == end2){len = 2;}
					}
				} else{
					if(callbackData.inputString[i] == end1){len = 1;}
					else if(callbackData.inputString[i] == end2){
						len = 1;
						i++;
						if(i < inlen && callbackData.inputString[i] == end1){len++;}
					}
				}
			}
			return len;
		}
	}
	/**
	 * UDT for any of several choices for white space characters.
	 */
	public static class WhiteSpace extends Parser.UdtCallback{
		private final boolean optional;
		private final Comment comment;
		private final LineEnd lineEnd;
		/**
		 * Default constructor.
		 * White space is an optional string of space (<code>x20</code>) 
		 * or horizontal tab (<code>x09</code>) characters.
		 * @param parser the Parser object this UDT is attached to.
		 */
		public WhiteSpace(Parser parser){
			super(parser);
			this.optional = true;
			this.lineEnd = null;
			this.comment = null;
		}
		/**
		 * General constructor.
		 * White space always includes the standard white space characters 
		 * space(<code>x20</code>) or horizontal tab(<code>x09</code>).
		 * Optionally white space additionally may include line comments and/or 
		 * folding white space.
		 * Folding white space, found in many Internet standards, is a line end followed by
		 * one standard white space character.
		 * @param parser the Parser object this UDT is attached to.
		 * @param optional if true, the white space is optional,
		 * if false at least one white space character or entity is required.
		 * @param comment if null, comments are <i>not</i> considered white space,
		 * otherwise comment is a Comment class object or any Comment extension object.
		 * @param foldingLineEnd if null, folding white space is not allowed.
		 * Otherwise folding white space is allowed and 
		 * foldingLineEnd is a LineEnd class object or any LineEnd extension object.
		 */
		public WhiteSpace(Parser parser, boolean optional, Comment comment, LineEnd foldingLineEnd){
			super(parser);
			this.optional = optional;
			this.lineEnd = foldingLineEnd;
			this.comment = comment;
		}
		@Override public int callback(int offset){
			int inlen = callbackData.inputString.length;
			char[] in = callbackData.inputString;
			int len = 0;
			int i = offset;
			while(i < inlen){
				char c = in[i];
				int matched = 0;
				if(c == SP || c == HTAB){matched = 1;} // check for white space characters
				if(matched == 0 && comment != null){
					int commentLen = comment.callback(i); // check for comment
					if(commentLen > 0){matched = commentLen;}
				}
				if(matched == 0 && lineEnd != null){
					int lineEndLen = lineEnd.callback(i); // check for folding white space
					if(lineEndLen > 0){
						int tempOffset = i + lineEndLen;
						if(tempOffset < inlen){
							c = in[tempOffset];
							if(c == SP || c == HTAB){matched = lineEndLen + 1;}
						}
					}
				}
				if(matched > 0){
					len += matched;
					i += matched;
				} else{break;} // end of white space
			}
			if(!optional){return (len > 0) ? len : -1;}
			return len;
		}
	}
}
//public static class FoldingWhiteSpace extends Parser.UdtCallback{
//private final Comment comment;
//private final FWSP fwsp;
//private final boolean optional;
//private final boolean blankContinuationLinesAllowed;
///*
// * Default Constructor
// * 
// * fwsp-nb = (1*WSP [CRLF 1*WSP]) / (CRLF 1*WSP)
// * at least one WSP or (CRLF WSP) required, may not be empty
// * CRLF = forgiving line end
// * no comments allowed
// * no blank continuation lines allowed
// */
//public FoldingWhiteSpace(Parser parser){
//	super(parser);
//	this.comment = null;
//	this.fwsp = new FWSP(parser); // forgiving line end
//	this.optional = false;
//	this.blankContinuationLinesAllowed = false;
//}
///**
// * General Constructor
// * 
// * @param comment if null, comments not allowed
// *                 otherwise, defines the type of comment allowed
// * @param fwsp may not be null, defines the type of line end allowed
// * @param optional if true, fwsp may be empty
// *                  if false, at least one WSP, comment or (CRLF WSP) required
// * @param blanksAllowed if true, blank continuations lines are allowed
// *                       if false, not allowed
// */
//
//public FoldingWhiteSpace(Parser parser, Comment comment, FWSP fwsp, boolean optional, boolean blanksAllowed){
//	super(parser);
//	this.comment = comment;
//	this.fwsp = fwsp;
//	this.optional = optional;
//	this.blankContinuationLinesAllowed = blanksAllowed;
//}
//@Override public int callback(int offset){
////	fwsp-oc    = *WSP [comment] *(CRLF 1*WSP [comment]) 
//	int len = 0;
//	int i = offset;
//	int inlen = callbackData.inputString.length;
//	char c;
//	int temp;
//	
//	// leading WSP
//	for(; i < inlen; i++){
//		c = callbackData.inputString[i];
//		if(c == SP || c == HTAB){len++;}
//		else{break;}
//	}
//	
//	// optional comment (if allowed)
//	if(comment != null){
//		// leading comment
//		offset = i;
//		temp = comment.callback(offset);
//		if(temp > 0){
//			len += temp;
//			i += temp;
//		}
//	}
//	
//	// line continuations
//	int reps = 0;
//	while(true){
//		offset = i;
//		temp = fwsp.callback(offset);
//		if(temp <= 0){break;} // no line end found
//		
//		// trailing WSP
//		i += temp;
//		len += temp;
//		temp = 0;
//		for(; i < inlen; i++){
//			c = callbackData.inputString[i];
//			if(c == SP || c == HTAB){temp++;}
//			else{break;}
//		}
//		len += temp;
//		
//		// optional comment (if allowed)
//		if(comment != null){
//			// leading comment
//			offset = i;
//			temp = comment.callback(offset);
//			if(temp > 0){
//				len += temp;
//				i += temp;
//			}
//		}
//
//		reps++;
//		if(reps == 1 && !blankContinuationLinesAllowed){break;} //
//	}
//	return (len == 0 && !optional) ? -1 : len;
//}
//}
//
//public static class FWSP extends Parser.UdtCallback{
//private final char SP = (char)32;
//private final char end1;
//private final char end2;
//private final int charCount;
///**
// * Default LineEnd
// * 
// * may be LR or CR LF or CR (\n or \r\n or \r)
// */
//public FWSP(Parser p){
//	super(p);
//	this.end1 = LF;
//	this.end2 = CR;
//	this.charCount = 0;  // forgiving; \n or \r\n or \r
//}
///**
// * Single-Character LineEnd
// * @param end line end character
// */
//public FWSP(Parser p, char end){
//	super(p);
//	this.end1 = end;
//	this.end2 = (char)0;
//	this.charCount = 1;
//}
///**
// * Double-Character LineEnd
// * @param end1 first line end character of the pair
// * @param end2 second line end character of the pair
// */
//public FWSP(Parser p, char end1, char end2){
//	super(p);
//	this.end1 = end1;
//	this.end2 = end2;
//	this.charCount = 2;
//}
//@Override public int callback(int offset){
//	int len = -1;
//	int i = offset;
//	int ii = i + 1;
//	int inlen = callbackData.inputString.length;
//	if(ii < inlen){
//		switch(charCount){
//		case 0: // forgiving line end
//			if(callbackData.inputString[i] == end1 && callbackData.inputString[ii] == SP){len = 2;}
//			else if(callbackData.inputString[i] == end2){
//				if(callbackData.inputString[ii] == SP){len = 2;}
//				else if(ii+1 < inlen && 
//						callbackData.inputString[ii] == end1 && 
//						callbackData.inputString[ii+1] == SP){len = 3;}
//			}
//			break;
//		case 1: // single-character line end
//			if(callbackData.inputString[i] == end1 && callbackData.inputString[ii] == SP){len = 2;}
//			break;
//		case 2: // double-character line end
//			if((callbackData.inputString[i] == end1) && (callbackData.inputString[ii] == end2) &&
//					(callbackData.inputString[ii+1] == SP)){len = 3;}
//			break;
//		default:
//			break;
//		}
//	}
//	return len;
//}
//}
