/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.
    Java APG Version 1.1 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package apg;
// <code></code>

import java.io.*;
import java.util.Vector;
import java.util.ListIterator;
/**
 * The Utilities class defines several helper classes and static functions.
 * These are stand-alone classes and functions that have general utility beyond
 * the needs of any single class.
 */
public class Utilities {
	private Utilities(){}
	
	// public API
	/**
	 * Generates a string of spaces (<code>0x20</code>) which is often used to provide an indent to a line of text.
	 * @param length the number of spaces in the indent string.
	 * @return a String of "length" spaces
	 */
	public static String indent(int length){
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < length; i++){
			buf.append(" ");
		}
		return buf.toString();
	}
	/**
	 * Converts the parser state to a String representation.
	 * Used in the formatting of trace and AST displays.
	 * @param down if <code>true</code>, the parser direction is "down" the syntax tree -
	 * <i>before</i> to parsing the branch below.
	 * If <code>false</code>, the direction is "up" <i>after</i> visiting the branch below.
	 * @param match if <code>true</code>, the phrase was matched (may be empty)
	 * @param length if match is <code>true</code>, the length of the matched phrase.
	 * Zero (0) length indicates an empty phrase matched. 
	 * @return the String representation of the state.
	 */
	public static String parserStateToString(boolean down, boolean match, int length){
		String out = "";
		if(down){out += "~";}
		else if(!match){out = "N";}
		else{
			if(length == 0){out = "E";}
			else{out = "M";}
		}
		return out;
	}
	
	// ASCII chars to ASCII, non-ASCII chars to hex
	/**
	 * Converts a subset of a character array to a String representation.
	 * Non-printing characters (not in the range <code>32-126</code>) are displayed as hex values 
	 * (eg. <code>x1F</code>)
	 * @param input the character array to convert
	 * @param offset offset of the first character in the array subset to convert.
	 * @param length the number of characters in the array subset.
	 * @param maxChars imposes a maximum character length limit on the subset.
	 * If length is greater than maxChars, the subset will be truncated.
	 * @return the String representation of the character array subset.
	 */
	public static String charArrayToString(char[] input, int offset, int length, int maxChars){
		StringBuffer buf = new StringBuffer();
		boolean inHex = false;
		if(length < 0){length = input.length;}
		else{length = offset + length;}
		if(length > input.length){length = input.length;}
		for(int i = offset; i < length; i++){
			char c = input[i];
			if(c < 32 || c > 126){
				if(!inHex){
					buf.append(String.format("x%02X", (int)c));
					inHex = true;
				}
				else{
					buf.append(String.format(".%02X", (int)c));
				}
			}
			else{
				inHex = false;
				buf.append(c);
			}
			if(buf.length() > maxChars){break;}
		}
		return buf.toString();
	}
	
	// UTF-8 XML, all non-printing, 7-bit ASCII characters are output as hexidecimal numerical entities
	/**
	 * Converts a subset of a character array to XML string format.
	 * @param input the character array to convert
	 * @param offset offset of the first character in the array subset to convert.
	 * @param length the number of characters in the array subset.
	 * @return the XML representation of the character array subset.
	 */
	public static String charArrayToXml(char[] input, int offset, int length){
		StringBuffer buf = new StringBuffer();
		if(length < 0){length = input.length;}
		else{length = offset + length;}
		if(length > input.length){length = input.length;}
		for(int i = offset; i < length; i++){
			char c = input[i];
			if(c < 32 || c > 126){
				buf.append(String.format("&#x%02X;", (int)c));
			}
			else{
				switch(c){
				case '&':
					buf.append("&amp;");
					break;
				case '<':
					buf.append("&lt;");
					break;
				case '>':
					buf.append("&gt;");
					break;
				case '"':
					buf.append("&quot;");
					break;
				case '\'':
					buf.append("&apos;");
					break;
				default:
					buf.append(c);
					break;
				}
			}
		}
		return buf.toString();
	}

	/**
	 * Reads the named file and returns its contents as a String.
	 * @param fileName name of the file to open and read.
	 * @return text of the file's contents.
	 * @throws NullPointerException {@link java.io.File}
	 * @throws FileNotFoundException {@link java.io.File}
	 * @throws IOException {@link java.io.File}
	 * @throws Exception thrown if the named file does not exist or for some reason cannot be opened.
	 */
	public static String getFileAsString(String fileName) throws
	 NullPointerException, FileNotFoundException, IOException, Exception {
		return getFileAsString("./", fileName);
	}
	/**
	 * Reads the named file and returns its contents as a String.
	 * @param fileName name of the file to open and read.
	 * @param workingDir working directory to find "fileName" in.
	 * @return text of the file's contents.
	 * @throws NullPointerException {@link java.io.File}
	 * @throws FileNotFoundException {@link java.io.File}
	 * @throws IOException {@link java.io.File}
	 * @throws Exception thrown if the named file does not exist or for some reason cannot be opened.
	 */
	public static String getFileAsString(String workingDir, String fileName) throws
	 NullPointerException, FileNotFoundException, IOException, Exception {
		String ret = null;
		File file;
		if((fileName == null || fileName == "")){
			throw new Exception("file name missing");
		} else{
			file = getFile(workingDir, fileName);
			if(file == null){throw new Exception("unable to open file ("+fileName+")");}
			if(!file.exists()){throw new Exception(
					"file \""+file.getCanonicalPath()+"\" does not exist");}
		}
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		StringBuffer buf = new StringBuffer();
		String eachLine = br.readLine();
		
		while (eachLine != null) {
		  buf.append(eachLine);
		  buf.append("\n");
		  eachLine = br.readLine();
		}
		ret = buf.toString();
		return ret;
	}
	/**
	 * Return a {@link java.io.File} object for the named file.
	 * @param dir directory to find the file in.
	 * @param filename name of the file to find.
	 * @return the {@link java.io.File} object or null if not the file could not be found
	 */
	public static File getFile(String dir, String filename){
		File ret = null;
		while(true){
			if(filename == null || filename == ""){break;}
			File test = new File(filename);
			if(filename == test.getAbsolutePath()){
				// file name is absolute - use it
				ret = new File(filename);
				break;
			}
			
			if(dir == null || dir == ""){
				// dir is empty - use just the file name
				ret = new File(filename);
				break;
			}

			// use dir + file name
			ret = new File(dir, filename);
			break;
		}
		return ret;
	}

	/**
	 * Displays detailed information on the named file.
	 * @param out the output device to display on.
	 * @param name the name of the file to get information on.
	 * @throws IOException thrown if the file cannot be found or opened.
	 */
	public static void displayFileInfo(PrintStream out, String name) throws IOException {
		displayFileInfo(out, new File(name));
	}
	/**
	 * Displays detailed information on {@link java.io.File}.
	 * @param out the output device to display on.
	 * @param file the File to get information on.
	 * @throws IOException thrown if the file cannot be found or opened.
	 */
	public static void displayFileInfo(PrintStream out, File file) throws IOException {
		if(out != null){
			out.println("========================================");
			out.println("          name:" + file.getName());
			out.println("  is directory:" + file.isDirectory());
			out.println("        exists:" + file.exists());
			out.println("          path:" + file.getPath());
			out.println("        parent:" + file.getParent());
			out.println(" absolute file:" + file.getAbsoluteFile());
			out.println(" absolute path:" + file.getAbsolutePath());
			out.println("canonical file:" + file.getCanonicalFile());
			out.println("canonical path:" + file.getCanonicalPath());
		}
	}
	/**
	 * Get the Exception message and stack trace as a String.
	 * @param e the Exception to display
	 * @return the Exception message and stack trace as a String.
	 */
	public static String displayException(Exception e){
		StringBuffer buf = new StringBuffer();
		buf.append("\n");
		buf.append(e.getClass().getCanonicalName());
		buf.append(":\n");
		buf.append("stack trace:\n");
		StackTraceElement[] stack = e.getStackTrace();
		for(StackTraceElement el:stack){
			buf.append(el);
			buf.append("\n");
		}
		buf.append("\nmessage:\n");
		buf.append(e.getMessage());
		buf.append("\n");
		return buf.toString();
	}
	/**
	 * Get the Error message and stack trace as a String.
	 * @param e the Error to display
	 * @return the Error message and stack trace as a String.
	 */
	public static String displayError(Error e){
		StringBuffer buf = new StringBuffer();
		buf.append("\n");
		buf.append(e.getClass().getCanonicalName());
		buf.append(":\n");
		buf.append("stack trace:\n");
		StackTraceElement[] stack = e.getStackTrace();
		for(StackTraceElement el:stack){
			buf.append(el);
			buf.append("\n");
		}
		buf.append("\nmessage:\n");
		buf.append(e.getMessage());
		buf.append("\n");
		return buf.toString();
	}
	
	// XMLWriter
	/**
	 * A class for assisting in the writing of XML files.
	 * This is an assistant only.
	 * Generating a complete and valid XML file is the user's responsibility.
	 */
	public static class XMLWriter{
		// XMLWriter private
		private final String declaration = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		private PrintStream out;
		
		// XMLWriter public API
		/**
		 * Constructor.
		 * @param out the output device to write the XML file on.
		 */
		public XMLWriter(PrintStream out){
			this.out = out;
		}
		/**
		 * Sets the output device
		 * @param out the output device.
		 */
		public void setOut(PrintStream out){this.out = out;}
		/**
		 * Generates the XML declaration. Version 1.0, UTF-8 encoding only.
		 */
		public void declaration(){
			if(out == null){return;}
			out.println(declaration);
		}
		/**
		 * Generates a start tag.
		 * @param indent the number of spaces to indent before printing the line.
		 * @param name tag name
		 * @param attrs the attributes as name/value pairs of Strings.
		 * May be null or an array of zero length if no attributes are to be included in the start tag. 
		 * @throws Exception thrown if the non-null or non-empty array of attributes does
		 * not have an even number of members (name/value pairs)
		 */
		public void startTag(int indent, String name, String[] attrs) throws Exception{
			if(out == null){return;}
			if(attrs != null && attrs.length > 0){
				if(attrs.length % 2 != 0){throw new Exception("attrs.length must be even");}
			}
			out.print(Utilities.indent(indent));
			out.print("<");
			out.print(name);
			if(attrs != null && attrs.length > 0){
				for(int i = 0; i < attrs.length; i += 2){
					out.print(" ");
					out.print(attrs[i]);
					out.print("=\"");
					out.print(attrs[i+1]);
					out.print("\"");
				}
			}
			out.print(">\n");
		}
		/**
		 * Generates an end tag.
		 * @param indent the number of spaces to indent before printing the line.
		 * @param name tag name
		 */
		public void endTag(int indent, String name){
			if(out == null){return;}
			out.print(Utilities.indent(indent));
			out.print("</");
			out.print(name);
			out.print(">\n");
		}
		/**
		 * Generates a start and end tag pair with text between them.
		 * @param indent the number of spaces to indent before printing the line.
		 * @param name tag name
		 * @param attrs the attributes as name/value pairs of Strings.
		 * May be null or an array of zero length if no attributes are to be included in the start tag. 
		 * @throws Exception thrown if the non-null or non-empty array of attributes does
		 * not have an even number of members (name/value pairs)
		 * @param text the text to include between the start and end tags.
		 */
		public void textTag(int indent, String name, String[] attrs, String text) throws Exception{
			if(out == null){return;}
			if(attrs != null && attrs.length > 0){
				if(attrs.length % 2 != 0){throw new Exception("attrs.length must be even");}
			}
			out.print(Utilities.indent(indent));
			out.print("<");
			out.print(name);
			if(attrs != null && attrs.length > 0){
				for(int i = 0; i < attrs.length; i += 2){
					out.print(" ");
					out.print(attrs[i]);
					out.print("=\"");
					out.print(attrs[i+1]);
					out.print("\"");
				}
			}
			if(text != null && text.length() > 0){
				out.print(">");
				out.print(text);
				out.print("</");
				out.print(name);
				out.print(">\n");
			} else{
				out.print("/>\n");
			}
		}
	}
	// end XMLWriter
	
	// LineCatalog
	/**
	 * A class for reading a text file and cataloging its text lines.
	 * A catalog record is generated for each text line.
	 * The records have information about the line number, the offset to the first character
	 * of the line and the line length.
	 */
	public static class LineCatalog {
		// LineCatalog public API
		/**
		 * Constructor
		 * @param inputString the text whose lines are to be cataloged as a String.
		 * @throws IllegalArgumentException thrown if the input string is null or empty.
		 */
		public LineCatalog(String inputString)	throws IllegalArgumentException{
			char[] inputArray = inputString.toCharArray();
			errors = new Vector<String>();
			warnings = new Vector<String>();
			catalog(inputArray);
		}
		/**
		 * Constructor
		 * @param inputArray the text whose lines are to be cataloged as a character array.
		 * @throws IllegalArgumentException thrown if the input string is null or empty.
		 */
		public LineCatalog(char[] inputArray)	throws IllegalArgumentException{
			errors = new Vector<String>();
			warnings = new Vector<String>();
			catalog(inputArray);
		}
		/**
		 * Class defining a catalog record for an individual line of text.
		 */
		public class Line{
			/**
			 * The line number (1-based, the first line is 1)
			 */
			public final int lineno;
			/**
			 * Offset of the first character of text in the line.
			 */
			public final int offset;
			/**
			 * The number of characters in the line, including the line ending character(s).
			 */
			public final int length;
			/**
			 * The line as a String not encluding the line end character(s).
			 */
			public final String line;
			/**
			 * The line end character(s) formatted as "<code>&lt;LF&gt;</code>" for a new line or line feed,
			 * "<code>&lt;CRLF&gt;</code>" for a carriage return, line feed pair or
			 * "<code>&lt;CR&gt;</code>" for a single carriage return character,
			 */
			public final String lineEnd;
			private Line(int lineno, int offset, int length, String line, String lineEnd){
				this.lineno = lineno;
				this.offset = offset;
				this.length = length;
				this.line = line;
				this.lineEnd = lineEnd;
			}
			/**
			 * Displays the line + lineEnd as a string.
			 */
			@Override public String toString(){
				return line + lineEnd;
			}
		}
		/**
		 * Displays all lines of text with all Line information for each line.
		 * @return the display as a single String.
		 */
		@Override public String toString(){
			StringBuffer buf = new StringBuffer();
			ListIterator<Line> iter = getLineIterator();
			int maxPrefix = 0;
			while(iter.hasNext()){
				Line line = iter.next();
				String prefix = "["+line.lineno+"]["+line.offset+", "+line.length+"] ";
				if(maxPrefix < prefix.length()){
					maxPrefix = prefix.length();
				}
			}
			iter = getLineIterator();
			buf.append("[line no][offset, line len] :line\n");
			while(iter.hasNext()){
				Line line = iter.next();
				String prefix = "["+line.lineno+"]["+line.offset+", "+line.length+"] ";
				buf.append(prefix);
				int indent = maxPrefix - prefix.length();
				for(int i = 0; i < indent; i++){
					buf.append(" ");
				}
				buf.append(":");
				buf.append(line.toString());
				buf.append("\n");
			}
			return buf.toString();
		}
		/**
		 * Retrieves a single catalog Line by line number.
		 * @param lineno the number (1-based) of the line to retrieve.
		 * @return the catalog Line record.
		 */
		public Line getLine(int lineno){
			Line ret = null;
			if(lineno > 0 && lineno < lineStore.size()){
				ret = lineStore.get(lineno -1);
			}
			return ret;
		}
		/**
		 * Retrieves a single catalog Line for the line containing the requested offset character.
		 * @param offset the offset of the first text character to find the line of.
		 * @return the catalog record Line.
		 */
		public Line getLineFromOffset(int offset){
			Line ret = null;
			ListIterator<Line> iter = getLineIterator();
			Line next;
			while(iter.hasNext()){
				next = iter.next();
				if((offset >= next.offset) && (offset < next.offset + next.length)){
					ret = next;
					break;
				}
			}
			return ret;
		}
		/**
		 * Get the number of errors encountered during the cataloging.
		 * @return the number of errors.
		 */
		public int getErrorCount(){return errors.size();}
		/**
		 * Get the number of warnings issuedduring the cataloging.
		 * @return the number of warnings.
		 */
		public int getWarningCount(){return warnings.size();}
		/**
		 * Displays all errors encountered during the cataloging, if any.
		 * @param out the output device to display on.
		 */
		public void displayErrors(PrintStream out){
			for(String error : errors){out.println(error);}
		}
		/**
		 * Displays all warnings issued during the cataloging, if any.
		 * @param out the output device to display on.
		 */
		public void displayWarnings(PrintStream out){
			for(String error : warnings){out.println(error);}
		}
		/**
		 * Get the number of lines in the text file.
		 * @return the number of lines
		 */
		public int getLineCount(){return lineStore.size();}
		/**
		 * Get an iterator over the cataloged record Lines.
		 * @return the iterator.
		 */
		public ListIterator<Line> getLineIterator(){return lineStore.listIterator();}
		/**
		 * Clears the catalog of all lines. Clears the list of errors and warnings.
		 */
		public void clear(){
			originalChars = null;
			errors.clear();
			warnings.clear();
			lineStore.clear();
		}
		
		// LineCatalog private
		private Vector<String> errors = null;
		private Vector<String> warnings = null;
		private Vector<Line> lineStore = new Vector<Line>();
		private char[] originalChars;
		private String subString(char[] array, int fromIndex, int toIndex){
			StringBuffer buf = new StringBuffer();
			int count = toIndex - fromIndex + 1;
			for(int i = 0; i < count; i++){
				buf.append(array[fromIndex + i]);
			}
			return buf.toString();
		}
		
		private void catalog(char[] input) throws IllegalArgumentException{
			if(input == null || input.length == 0){
				throw new IllegalArgumentException("LineCatalog.catalog(String input): input null or empty");
			}
			clear();
			originalChars = input;
			// scans for non-ASCII char, converts tab to space and counts line ends
			boolean lineEndProcessed = false;
			int offset = 0;
			int length = 0;
			int lineCount = 0;
			String lineString = null;
			String lineEndString = null;
			int i;
			for(i = 0; i < originalChars.length; i++){
				int ch = (int)originalChars[i];
				lineEndProcessed = false;
				if(ch == 9){
					originalChars[i] = 32; // convert tab to space
					length++;
					continue;
				}
				if(ch == 10){
					if(length == 0){lineString = "";}
					else{lineString = subString(originalChars, offset, offset + length -1);}
					lineEndString = new String("<LF>");
					length++; // LF line end
					lineStore.add(new Line((lineStore.size()+1), offset, length, lineString, lineEndString));
					lineCount++;
					offset += length;
					length = 0;
					lineString = null;
					lineEndString = null;
					lineEndProcessed = true;
					continue;
				}
				if(ch == 13){
					if(length == 0){lineString = "";}
					else{lineString = subString(originalChars, offset, offset + length -1);}
					length++; // CR line end
					if((i < originalChars.length) && (originalChars[i+1] == 10)){
						lineEndString = new String("<CRLF>");
						length++; // CRLF line end
						i++;
					} else{lineEndString = new String("<CR>");}
					lineStore.add(new Line((lineStore.size()+1), offset, length, lineString, lineEndString));
					lineCount++;
					offset += length;
					length = 0;
					lineString = null;
					lineEndString = null;
					lineEndProcessed = true;
					continue;
				}
				if(ch >= 32 && ch <= 126 ){
					length++; // printable ASCII char
					continue;
				}
				errors.add("non-ASCII char: "+(int)ch+" found in line: "+
						(lineCount+1)+" offset: "+(offset+length));
				length++;
			}
			if(!lineEndProcessed){
				if(length == 0){lineString = "";}
				else{lineString = subString(originalChars, offset, offset + length -1);}
				lineEndString = new String("<LF>");
				char[] temp = new char[originalChars.length + 1];
				length = 0;
				for(char c : originalChars){
					temp[length] = c;
					length++;
				}
				temp[originalChars.length] = 10;
				originalChars = temp;
				length++;
				lineStore.add(new Line((lineStore.size()+1), offset, length, lineString, lineEndString));
				lineString = null;
				lineEndString = null;
				warnings.add("last line: "+(lineCount + 1)+" does not end with new line, LF added");
			}
		}
	}
	// end LineCatalog
}
