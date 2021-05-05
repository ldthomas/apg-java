/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.0.
    Java APG Version 1.1.0 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package apg;

import java.util.Vector;

class GeneratorCommandLine {
    enum Flags {
        ATTRIBUTES("/da", "attributes, display grammar attributes"),
        ERRORS("/de", "errors, display parsing errors"),
        GRAMMAR("/dg", "grammar, display grammar file with line numbers and character indexes"),
        HELP("?", "help, display this help screen"),
        HELP2("/help", "help, display this help screen"),
        METRICS("/dm", "metrics, display grammar metrics (opcode counts)"),
        NO_ATTRIBUTES("/na", "no attributes, ommit grammar attribute discovery"),
        OPCODES("/dopcodes", "opcodes, display opcodes in human readable format (may generate large amount of output)"),
        STATE("/dstate", "state, display the state of the grammar parser"),
        STATISTICS("/dstats", "statistics, display the grammar parsing statistics"),
        VALUES("/doptions", "values, display all option values"),
        VERBOSE("/dv", "verbose, display all (except opcodes)"),
        VERSION("/version", "display the Java APG version number and information"),
        WARNINGS("/dw", "warnings, display parsing warnings");
        private String f;
        private String d;

        Flags(String string, String desc) {
            this.f = string;
            this.d = desc;
        }

        String flag() {
            return f;
        }

        String description() {
            return d;
        }
    }

    enum Params {
        C("/c=", null, "if not null, generate a C-language parser at value.c"),
        CPP("/cpp=", null, "if not null, generate a C++ parser at value.cpp"),
        JAVA("/java=", null, "if not null, generate a Java parser at value.java"),
        JAVADOC("/javadoc=", null, "if not null, generate a Java parser with Javadoc comments at value.java"),
        JAVASCRIPT("/js=", null, "if not null, generate a JavaScript parser at value.js"),
        INPUT("/in=", null, "(required)the SABNF grammar definition files"),
        LOGFILE("/log=", null, "name of log file, if null print log file to console"),
        PACKAGE("/package=", "package.name", "package name for the generated grammar"),
        WORKING_DIR("/dir=", "./", "working directory for input and output files");
        private String p;
        private String d;
        private String v;

        Params(String prefix, String value, String desc) {
            this.p = prefix;
            this.d = desc;
            this.v = value;
        }

        String prefix() {
            return p;
        }

        String defaultValue() {
            return v;
        }

        String description() {
            return d;
        }
    }

    String[] paramValues = new String[20];
    boolean[] flagValues = new boolean[20];
    // RHA: list of input files
    Vector<String> inputFiles = new Vector<String>();

    GeneratorCommandLine(String[] args) {

        // set default flags
        for (Flags f : Flags.values()) {
            flagValues[f.ordinal()] = false;
        }

        // set default param values
        for (Params p : Params.values()) {
            paramValues[p.ordinal()] = p.defaultValue();
        }

        try {
            int tokIndex = -1;
            for (String tok : args) {
                tokIndex++;
                if (isHelp(tok)) {
                    throw new Exception(exceptionMsg(tokIndex, tok, "help screen requested"));
                } else if (tok.equals(Flags.ATTRIBUTES.flag())) {
                    flagValues[Flags.ATTRIBUTES.ordinal()] = true;
                } else if (tok.equals(Flags.ERRORS.flag())) {
                    flagValues[Flags.ERRORS.ordinal()] = true;
                } else if (tok.equals(Flags.GRAMMAR.flag())) {
                    flagValues[Flags.GRAMMAR.ordinal()] = true;
                } else if (tok.equals(Flags.METRICS.flag())) {
                    flagValues[Flags.METRICS.ordinal()] = true;
                } else if (tok.equals(Flags.NO_ATTRIBUTES.flag())) {
                    flagValues[Flags.NO_ATTRIBUTES.ordinal()] = true;
                } else if (tok.equals(Flags.OPCODES.flag())) {
                    flagValues[Flags.OPCODES.ordinal()] = true;
                } else if (tok.equals(Flags.STATE.flag())) {
                    flagValues[Flags.STATE.ordinal()] = true;
                } else if (tok.equals(Flags.STATISTICS.flag())) {
                    flagValues[Flags.STATISTICS.ordinal()] = true;
                } else if (tok.equals(Flags.VALUES.flag())) {
                    flagValues[Flags.VALUES.ordinal()] = true;
                } else if (tok.equals(Flags.VERBOSE.flag())) {
                    flagValues[Flags.VERBOSE.ordinal()] = true;
                } else if (tok.equals(Flags.VERSION.flag())) {
                    flagValues[Flags.VERSION.ordinal()] = true;
                } else if (tok.equals(Flags.WARNINGS.flag())) {
                    flagValues[Flags.WARNINGS.ordinal()] = true;
                } // test all parameters
                else if (tok.startsWith(Params.C.prefix())) {
                    getParamValue(Params.C, tokIndex, tok, paramValues);
                } else if (tok.startsWith(Params.CPP.prefix())) {
                    getParamValue(Params.CPP, tokIndex, tok, paramValues);
                } else if (tok.startsWith(Params.JAVA.prefix())) {
                    getParamValue(Params.JAVA, tokIndex, tok, paramValues);
                } else if (tok.startsWith(Params.JAVADOC.prefix())) {
                    getParamValue(Params.JAVADOC, tokIndex, tok, paramValues);
                } else if (tok.startsWith(Params.JAVASCRIPT.prefix())) {
                    getParamValue(Params.JAVASCRIPT, tokIndex, tok, paramValues);
                } else if (tok.startsWith(Params.INPUT.prefix())) {
                    getParamValue(Params.INPUT, tokIndex, tok, paramValues);
                    // RHA: store all input files
                    inputFiles.add(paramValues[GeneratorCommandLine.Params.INPUT.ordinal()]);
                } else if (tok.startsWith(Params.LOGFILE.prefix())) {
                    getParamValue(Params.LOGFILE, tokIndex, tok, paramValues);
                } else if (tok.startsWith(Params.PACKAGE.prefix())) {
                    getParamValue(Params.PACKAGE, tokIndex, tok, paramValues);
                } else if (tok.startsWith(Params.WORKING_DIR.prefix())) {
                    getParamValue(Params.WORKING_DIR, tokIndex, tok, paramValues);
                } else {
                    throw new Exception(exceptionMsg(tokIndex, tok, "unrecognized argument"));
                }
            }
        } catch (Exception e) {
            displayHelpScreen(e.getMessage(), args);
            System.exit(0);
        }

        if (flagValues[Flags.VERBOSE.ordinal()]) {
            flagValues[Flags.ERRORS.ordinal()] = true;
            flagValues[Flags.WARNINGS.ordinal()] = true;
            flagValues[Flags.STATE.ordinal()] = true;
            flagValues[Flags.GRAMMAR.ordinal()] = true;
            flagValues[Flags.STATISTICS.ordinal()] = true;
            flagValues[Flags.METRICS.ordinal()] = true;
            flagValues[Flags.ATTRIBUTES.ordinal()] = true;
            flagValues[Flags.VALUES.ordinal()] = true;
        }
        if (flagValues[Flags.NO_ATTRIBUTES.ordinal()]) {
            flagValues[Flags.ATTRIBUTES.ordinal()] = false;
        }
    }

    void displayOptions() {
        System.out.println("\n*** OPTIONS ***");
        System.out.println("FLAGS");
        for (Flags f : Flags.values()) {
            System.out.println(String.format("%-15s %-10s %b", f.name(), f.flag(), flagValues[f.ordinal()]));
        }
        System.out.println("\nPARAMETERS");
        for (Params p : Params.values()) {
            String value = paramValues[p.ordinal()] == null ? "<null>" : paramValues[p.ordinal()];
            System.out.println(String.format("%-15s %-10s %s", p.name(), p.prefix(), value));
        }
    }

    void displayHelpScreen(String msg, String[] args) {
        System.out.println("\n*** HELP ***");
        System.out.println("reason: " + msg);
        System.out.println(" usage: java -jar apg.jar [args]");
        System.out.print("  args:");
        for (String tok : args) {
            System.out.print(" ");
            System.out.print(tok);
        }
        System.out.print("\n");
        System.out.println("\nflags");
        for (Flags f : Flags.values()) {
            System.out.println(String.format("%-10s %s", f.flag(), f.description()));
        }
        System.out.println("\nparameters");
        for (Params p : Params.values()) {
            String dval = p.defaultValue() == null ? "null" : p.defaultValue();
            System.out.println(String.format("%-10s %s (default: %s)", p.prefix(), p.description(), dval));
        }
        System.out.println("");
        System.out.println("All flags are false by default. Specifying a flag sets it to true.");
        System.out.println("Multiple occurances of a given flag are allowed but redundant. Resulting flag is true.");
        System.out.println("Parameters are of the form parameter value. eg. /in=value.");
        System.out.println("Parameter values may not be empty.");
        System.out.println("Parameter values containing spaces must be quoted.");
        // RHA: multiple input files allowed
        System.out.println("Multiple occurences of a given parameter are allowed.");
        System.out.println("All occurences of /in= are used and the named files are concatenated.");
        System.out.println("For all other parameters, only the last value is used.");
        System.out.println("All flags and parameter names and values are case sensitive.");
    }

    private void getParamValue(Params p, int index, String token, String[] paramValues) throws Exception {
        if (token.length() == p.prefix().length()) {
            throw new Exception(exceptionMsg(index, token, "parameter value may not be empty"));
        }
        paramValues[p.ordinal()] = token.substring(p.prefix().length());
    }

    private boolean isHelp(String tok) {
        boolean ret = false;
        // help == ? or /help
        if ('?' == tok.charAt(0)) {
            ret = true;
        } else if (tok.equalsIgnoreCase("/help")) {
            ret = true;
        }
        return ret;
    }

    private String exceptionMsg(int index, String token, String msg) {
        StringBuffer buf = new StringBuffer();
        if (msg != null) {
            buf.append(msg);
        }
        buf.append(" at arg(");
        buf.append(index + 1);
        buf.append(") ");
        buf.append("'");
        buf.append(token);
        buf.append("'");
        return buf.toString();
    }

}
