/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.0.
    Java APG Version 1.1.0 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package examples;

class Utils {

    static class Timer {

        long start;
        long end;
        long duration;
        long durationSec;
        boolean started;

        public Timer() {
            clear();
        }

        public long getDurationSec() {
            return durationSec;
        }

        public long getDurationMillis() {
            return duration;
        }

        public void start() {
            clear();
            start = System.currentTimeMillis();
            started = true;
        }

        public void stop() {
            if (started) {
                started = false;
                end = System.currentTimeMillis();
                duration = end - start;
                durationSec = duration / 1000;
            } else {
                clear();
            }
        }

        public final void clear() {
            started = false;
            start = 0;
            end = 0;
            duration = 0;
            durationSec = 0;;
        }
    }

    static class CommandLineParser {

        private final Parameter[] params;
        private final Flag[] flags;
        private final boolean caseSensitive;
        private final String cmd;

        CommandLineParser(String cmd, Flag[] flags, Parameter[] params, boolean caseSensitive) {
            if (flags == null) {
                this.flags = new Flag[0];
            } else {
                this.flags = flags;
            }
            if (params == null) {
                this.params = new Parameter[0];
            } else {
                this.params = params;
            }
            if (cmd == null || cmd.equals("")) {
                this.cmd = "command";
            } else {
                this.cmd = cmd;
            }
            this.caseSensitive = caseSensitive;
        }

        CommandLineParser(String cmd, Flag[] flags, Parameter[] params) {
            if (flags == null) {
                this.flags = new Flag[0];
            } else {
                this.flags = flags;
            }
            if (params == null) {
                this.params = new Parameter[0];
            } else {
                this.params = params;
            }
            if (cmd == null || cmd.equals("")) {
                this.cmd = "command";
            } else {
                this.cmd = cmd;
            }
            this.caseSensitive = true;
        }

        static class Flag {

            private final String name;
            private final String arg;
            private final String description;
            private boolean value;

            public Flag(String name, String arg, String description) throws IllegalArgumentException {
                boolean ok = true;
                if (name == null || name.equals("")) {
                    ok = false;
                }
                if (description == null || description.equals("")) {
                    ok = false;
                }
                if (!ok) {
                    throw new IllegalArgumentException();
                }
                this.name = name;
                this.arg = arg;
                this.description = description;
                this.value = false;
            }

            public String getName() {
                return name;
            }

            public String getArg() {
                return arg;
            }

            public String getDescription() {
                return description;
            }

            public boolean getValue() {
                return value;
            }
        }

        static class Parameter {

            private final String name;
            private final String arg;
            private final String description;
            private final String defaultValue;
            private String value;

            public Parameter(String name, String prefix, String description, String defaultValue) throws IllegalArgumentException {
                boolean ok = true;
                if (name == null || name.equals("")) {
                    ok = false;
                }
                if (prefix == null || prefix.equals("")) {
                    ok = false;
                }
                if (description == null || description.equals("")) {
                    ok = false;
                }
                if (!ok) {
                    throw new IllegalArgumentException();
                }
                this.name = name;
                this.arg = prefix;
                this.description = description;
                this.defaultValue = defaultValue;
                this.value = defaultValue;
            }

            public String getName() {
                return name;
            }

            public String getArg() {
                return arg;
            }

            public String getDescription() {
                return description;
            }

            public String getDefaultValue() {
                return defaultValue;
            }

            public String getValue() {
                return value;
            }
        }

        static Flag[] getDefaultHelpFlags() {
            Flag[] ret = new Flag[6];
            String name = "HELP";
            String desc = "if true, print the help screen";
            ret[0] = new Flag(name, "?", desc);
            ret[1] = new Flag(name, "/?", desc);
            ret[2] = new Flag(name, "help", desc);
            ret[3] = new Flag(name, "/help", desc);
            ret[4] = new Flag(name, "-help", desc);
            ret[5] = new Flag(name, "--help", desc);
            return ret;
        }

        boolean parse(String[] args) {
            boolean ret = true;
            try {
                int tokIndex = -1;
                for (String tok : args) {
                    tokIndex++;
                    boolean matched = false;

                    // check flags
                    for (Flag fg : flags) {
                        if (caseSensitive) {
                            if (tok.equals(fg.getArg())) {
                                fg.value = true;
                                matched = true;
                                break;
                            }
                        } else {
                            if (0 == tok.compareToIgnoreCase(fg.getArg())) {
                                fg.value = true;
                                matched = true;
                                break;
                            }
                        }
                    }
                    if (matched) {
                        continue;
                    }

                    // check parameters
                    for (Parameter pm : params) {
                        if (caseSensitive) {
                            if (tok.toLowerCase().startsWith(pm.getArg().toLowerCase())) {
                                getParamValue(pm, tokIndex, tok);
                                matched = true;
                                break;
                            }
                        } else {
                            if (tok.startsWith(pm.getArg())) {
                                getParamValue(pm, tokIndex, tok);
                                matched = true;
                                break;
                            }
                        }
                    }
                    if (!matched) {
                        throw new Exception(exceptionMsg(tokIndex, tok, "unrecognized argument"));
                    }
                }
            } catch (Exception e) {
                displayHelpScreen(e.getMessage(), args);
                ret = false;
            }
            return ret;
        }

        void getParamValue(Parameter p, int index, String token) throws Exception {
            int len = p.getArg().length();
            if (token.length() == len) {
                throw new Exception(exceptionMsg(index, token, "parameter value may not be empty"));
            }
            p.value = token.substring(len);
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

        void displayOptions() {
            System.out.println("\n*** COMMAND LINE OPTIONS ***");
            System.out.println("FLAGS");
            for (Flag f : flags) {
                System.out.println(String.format("%-15s %-10s %b",
                        f.getName(), f.getArg(), f.getValue()));
            }
            System.out.println("\nPARAMETERS");
            for (Parameter p : params) {
                String value = p.getValue() == null ? "<null>" : p.getValue();
                System.out.println(String.format("%-15s %-10s %s", p.getName(), p.getArg(), value));
            }
        }

        void displayHelpScreen(String reason, String[] args) {
            System.out.println("\n*** COMMAND LINE HELP ***");
            System.out.println("reason: " + reason);
            System.out.println(" usage: " + cmd + " [args]");
            System.out.print("  args:");
            for (String tok : args) {
                System.out.print(" ");
                System.out.print(tok);
            }
            System.out.println();
            System.out.println("\nflags");
            for (Flag f : flags) {
                System.out.println(String.format("%-10s %s", f.getArg(), f.getDescription()));
            }
            System.out.println("\nparameters");
            for (Parameter p : params) {
                String dval = p.getDefaultValue() == null ? "null" : p.getDefaultValue();
                System.out.println(String.format("%-10s %s (default: %s)",
                        p.getArg(), p.getDescription(), dval));
            }
            System.out.println("");
            System.out.println("All flags are false by default. Specifying a flag one or more times sets it to true.");
            System.out.println("Parameters are of the form \"arg value\" (no space). eg. /in=value.");
            System.out.println("Parameter values may not be empty.");
            System.out.println("Parameter values containing spaces must be quoted.");
            System.out.println("All command line arguments must be a valid flag or parameter.");
        }
    }
}
//public static String trimTrailingNewlines(String input){
//String ret = null;
//int lineEndChars = 0;
//int len = 0;
//char lastChar = (int)0;
//if(input != null && input.length() > 0){
//	len = input.length();
//	for(int i = len-1; i >= 0; i--){
//		lastChar = input.charAt(i);
//		if(lastChar == '\n' || lastChar == '\r'){
//			lineEndChars++;
//			len--;
//		} else{break;}
//	}
//}
//if(lineEndChars > 0){ret = input.substring(0, len);}
//else{ret = input;}
//return ret;
//}
