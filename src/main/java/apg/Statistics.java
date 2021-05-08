/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.0.
    Java APG Version 1.1.0 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package apg;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.Arrays;
import java.io.PrintStream;

/**
 * The Statistics class is used to collect and display syntax tree node
 * statistics. The statistics count the number of times each node was visited
 * (hit) by the parser in its journey through the syntax tree and the result of
 * each hit - that is, whether the node matched a phrase of the input string or
 * not.
 */
public class Statistics {

    private boolean cumulate = false;
    private int ruleCount;
    private int udtCount;
    private Details alt;
    private Details cat;
    private Details rep;
    private Details and;
    private Details not;
    private Details trg;
    private Details tbs;
    private Details tls;
    private Details udt;
    private Details[] udtList;
    private Details rnm;
    private Details[] rnmList;

    Statistics(Grammar grammar) {
        ruleCount = grammar.getRuleCount();
        udtCount = grammar.getUdtCount();
        alt = new Details();
        cat = new Details();
        rep = new Details();
        and = new Details();
        not = new Details();
        trg = new Details();
        tbs = new Details();
        tls = new Details();
        rnm = new Details();
        rnmList = new Details[ruleCount];
        for (int i = 0; i < ruleCount; i++) {
            rnmList[i] = new Details();
            rnmList[i].name = grammar.getRule(i).NAME;
        }
        udt = new Details();
        udtList = new Details[udtCount];
        for (int i = 0; i < udtCount; i++) {
            udtList[i] = new Details();
            udtList[i].name = grammar.getUdt(i).NAME;
        }
    }

    // public API
    /**
     * Called to enable or disable the cumulation of statistics. Instances of a
     * Parsers will sometimes be rerun several or many times on different
     * strings or with different parameters of some sort. Occasionally the user
     * will wish to see the total statistics of all parses. If cumulation is
     * enabled, the Statistics class will collect a running sum of the
     * statistics of all parses of the same instance of the Parser.
     *
     * @param enable if <code>true</code>, Statistics will cumulate statistics
     * for all parses of the Parser instance. If <code>false</code>, the
     * statistics will be reset to zero (0) for each parse. The default value is
     * <code>enable=false</code>.
     */
    public void enableCumulate(boolean enable) {
        cumulate = enable;
    }

    /**
     * Returns the hit count for the requested node type.
     *
     * @param type determines the type of hit count to return.<br>
     * <code>"match"</code> - hit count for nodes that were matched with
     * non-empty phrases<br>
     * <code>"empty"</code> - hit count for nodes that were matched with empty
     * phrases<br>
     * <code>"nomatch"</code> - hit count for nodes that were did not match any
     * phrase at all<br>
     * <code>"total"</code> - total of all hits of all of the above types<br>
     * @return the hit count for the requested type
     * @throws Exception thrown if the type parameter is not valid
     */
    public int getHits(String type) throws Exception {
        int ret = -1;
        while (true) {
            if (type == null || type.length() == 0) {
                throw new Exception("Statistics: displayStats: type: may not be null or empty");
            }
            Details totals = getTotals();
            char first = type.charAt(0);
            if (first == 'm' || first == 'M') {
                ret = totals.match;
                break;
            }
            if (first == 'n' || first == 'N') {
                ret = totals.nomatch;
                break;
            }
            if (first == 'e' || first == 'E') {
                ret = totals.empty;
                break;
            }
            if (first == 't' || first == 'T') {
                ret = totals.empty + totals.match + totals.nomatch;
                break;
            } else {
                throw new Exception("Statistics: displayStats: "
                        + "type must be \"empty\""
                        + " \"match\""
                        + " \"nomatch\""
                        + "or \"total\"");
            }
        }
        return ret;
    }

    // display operator stats
    /**
     * Displays the statistics on the output device.
     *
     * @param out the output device.
     * @param type the type of statistics to display<br>
     * <code>"operators"</code> - display node hits for all operator types
     * except rule and UDT<br>
     * <code>"rules"</code> - display node hits for rule name nodes only<br>
     * <code>"udt"</code> - display node hits for UDT nodes only<br>
     * Rule name or UDT node names are ordered descending on hit count.
     * @throws Exception thrown if the type is none of the above.
     */
    public void displayStats(PrintStream out, String type) throws Exception {
        displayStats(out, type, false);
    }

    /**
     * Displays the statistics on the output device.
     *
     * @param out the output device.
     * @param type the type of statistics to display<br>
     * <code>"operators"</code> - display node hits for all operator types
     * except rule and UDT<br>
     * <code>"rules"</code> - display node hits for rule name nodes only<br>
     * <code>"udt"</code> - display node hits for UDT nodes only<br>
     * @param alpha if <code>true</code>, the rule or UDT nodes are displayed in
     * alphabetical order, if <code>false</code>, rule or UDT nodes are ordered
     * descending on hit count.
     * @throws Exception thrown if the type is none of the above.
     */
    public void displayStats(PrintStream out, String type, boolean alpha) throws Exception {
        while (true) {
            if (out == null) {
                throw new Exception("Statistics: displayStats: out: may not be null");
            }
            if (type == null || type.length() == 0) {
                throw new Exception("Statistics: displayStats: type: may not be null or empty");
            }
            char first = type.charAt(0);
            if (first == 'o' || first == 'O') {
                displayOperatorDetails(out);
                break;
            }
            if (first == 'r' || first == 'R') {
                displayRnmListDetails(out, alpha);
                break;
            }
            if (first == 'u' || first == 'U') {
                displayUdtListDetails(out, alpha);
                break;
            } else {
                throw new Exception("Statistics: displayStats: "
                        + "type must be \"operators\""
                        + " \"rules\""
                        + "or \"udts\"");
            }
        }
    }

    /**
     * Displays a detailed table of hit counts for all operator and hit count
     * types.
     *
     * @param out the output device to display on.
     */
    private void displayOperatorDetails(PrintStream out) {
        Details totals = getTotals();
        int tmatch = totals.match;
        int tnomatch = totals.nomatch;
        int tempty = totals.empty;
        int total = totals.total;
        out.print(String.format("%10s %10s %10s %10s %10s\n",
                "operator", "MATCH", "EMPTY", "NOMATCH", "hits"));
        out.print(String.format("%10s %10d %10d %10d %10d\n",
                "ALT", alt.match, alt.empty, alt.nomatch, alt.total));
        out.print(String.format("%10s %10d %10d %10d %10d\n",
                "REP", rep.match, rep.empty, rep.nomatch, rep.total));
        out.print(String.format("%10s %10d %10d %10d %10d\n",
                "AND", and.match, and.empty, and.nomatch, and.total));
        out.print(String.format("%10s %10d %10d %10d %10d\n",
                "NOT", not.match, not.empty, not.nomatch, not.total));
        out.print(String.format("%10s %10d %10d %10d %10d\n",
                "CAT", cat.match, cat.empty, cat.nomatch, cat.total));
        out.print(String.format("%10s %10d %10d %10d %10d\n",
                "TRG", trg.match, trg.empty, trg.nomatch, trg.total));
        out.print(String.format("%10s %10d %10d %10d %10d\n",
                "TBS", tbs.match, tbs.empty, tbs.nomatch, tbs.total));
        out.print(String.format("%10s %10d %10d %10d %10d\n",
                "TLS", tls.match, tls.empty, tls.nomatch, tls.total));
        out.print(String.format("%10s %10d %10d %10d %10d\n",
                "UDT", udt.match, udt.empty, udt.nomatch, udt.total));
        out.print(String.format("%10s %10d %10d %10d %10d\n",
                "RNM", rnm.match, rnm.empty, rnm.nomatch, rnm.total));
        out.print(String.format("%10s %10s %10s %10s %10s\n",
                "-----", "-----", "-----", "-----", "-----"));
        out.print(String.format("%10s %10d %10d %10d %10d\n",
                "TOTAL", tmatch, tempty, tnomatch, total));
    }

    // displays individual rule name operator stats
    // alpha = true, rule names listed ascending alphabetically
    // alpha = false, rule names listed descending on hit count
    private void displayRnmListDetails(PrintStream out, boolean alpha) {
        List<Details> list = Arrays.asList(rnmList);
        Comparator<Details> compare;
        if (alpha) {
            compare = new NameComparator();
            Collections.sort(list, compare);
            out.print("\nRNM node statistics: sorted alphabetically, omitted if no hits\n");
        } else {
            compare = new HitCountComparator();
            Collections.sort(list, compare);
            out.print("\nRNM node statistics: sorted by hit count, omitted if no hits\n");
        }
        out.print(String.format("%10s %10s %10s %10s  %-10s%n",
                "MATCH", "EMPTY", "NOMATCH", "hits", "rule name"));
        for (int i = 0; i < ruleCount; i++) {
            if (list.get(i).total > 0) {
                out.print(String.format("%10d %10d %10d %10d  %-10s%n",
                        list.get(i).match, list.get(i).empty, list.get(i).nomatch,
                        list.get(i).total, list.get(i).name));
            }
        }
    }

    private void displayUdtListDetails(PrintStream out, boolean alpha) {
        if (udtCount > 0) {
            List<Details> list = Arrays.asList(udtList);
            Comparator<Details> compare;
            if (alpha) {
                compare = new NameComparator();
                Collections.sort(list, compare);
                out.print("\nUDT node statistics: sorted alphabetically, omitted if no hits\n");
            } else {
                compare = new HitCountComparator();
                Collections.sort(list, compare);
                out.print("\nUDT node statistics: sorted by hit count, omitted if no hits\n");
            }
            out.print(String.format("%10s %10s %10s %10s  %-10s%n",
                    "MATCH", "EMPTY", "NOMATCH", "hits", "rule name"));
            for (int i = 0; i < udtCount; i++) {
                if (list.get(i).total > 0) {
                    out.print(String.format("%10d %10d %10d %10d  %-10s%n",
                            list.get(i).match, list.get(i).empty, list.get(i).nomatch,
                            list.get(i).total, list.get(i).name));
                }
            }
        }
    }

    private class Details {

        private int total = 0;
        private int match = 0;
        private int nomatch = 0;
        private int empty = 0;
        private String name = null;

        private Details() {
            clear();
        }

        private Details(Details d) {
            this.total = d.total;
            this.match = d.match;
            this.nomatch = d.nomatch;
            this.empty = d.empty;
            this.name = d.name;
        }

        private void add(Details d) {
            this.total += d.total;
            this.match += d.match;
            this.nomatch += d.nomatch;
            this.empty += d.empty;
        }

        private void clear() {
            if (!cumulate) {
                total = 0;
                match = 0;
                nomatch = 0;
                empty = 0;
            }
        }
    }

    private Details getTotals() {
        Details ret = new Details(alt);
        ret.add(cat);
        ret.add(rep);
        ret.add(and);
        ret.add(not);
        ret.add(trg);
        ret.add(tbs);
        ret.add(tls);
        ret.add(rnm);
        ret.add(udt);
        return ret;
    }

    void clear() {
        if (!cumulate) {
            alt.clear();
            cat.clear();
            rep.clear();
            not.clear();
            and.clear();
            trg.clear();
            tbs.clear();
            tls.clear();
            rnm.clear();
            for (int i = 0; i < ruleCount; i++) {
                rnmList[i].clear();
            }
            udt.clear();
            for (int i = 0; i < udtCount; i++) {
                udtList[i].clear();
            }
        }
    }

    void incrementOperator(Opcode.Type type, boolean match, int length) {
        switch (type) {
            case ALT:
                incrementDetails(alt, match, length);
                break;
            case CAT:
                incrementDetails(cat, match, length);
                break;
            case REP:
                incrementDetails(rep, match, length);
                break;
            case AND:
                incrementDetails(and, match, length);
                break;
            case NOT:
                incrementDetails(not, match, length);
                break;
            case TRG:
                incrementDetails(trg, match, length);
                break;
            case TBS:
                incrementDetails(tbs, match, length);
                break;
            case TLS:
                incrementDetails(tls, match, length);
                break;
            case UDT:
            case RNM:
                // this function *WILL* be called from opcode, ignore it here
                break;
        }
    }

    void incrementRnmOperator(boolean match, int length) {
        incrementRnmDetails(rnm, match, length);
    }

    void incrementRuleNameOperator(int ruleID, boolean match, int length) {
        incrementRnmDetails(rnmList[ruleID], match, length);
    }

    void incrementUdtOperator(boolean match, int length) {
        incrementUdtDetails(udt, match, length);
    }

    void incrementUdtNameOperator(int udtID, boolean match, int length) {
        incrementUdtDetails(udtList[udtID], match, length);
    }

    // should be called only after node has been traversed
    private static void incrementRnmDetails(Details stats, boolean match, int length) {
        if (match) {
            if (length == 0) {
                stats.empty += 1;
            } else {
                stats.match += 1;
            }

        } else {
            stats.nomatch += 1;
        }
        stats.total += 1; // total count for all states
    }

    // should be called only after node has been traversed
    private static void incrementUdtDetails(Details stats, boolean match, int length) {
        if (match) {
            if (length == 0) {
                stats.empty += 1;
            } else {
                stats.match += 1;
            }

        } else {
            stats.nomatch += 1;
        }
        stats.total += 1; // total count for all states
    }

    private static void incrementDetails(Details stats, boolean match, int length) {
        stats.total += 1;
        if (match) {
            if (length == 0) {
                stats.empty += 1;
            } else {
                stats.match += 1;
            }

        } else {
            stats.nomatch += 1;
        }
    }

    // sort descending comparator
    private class HitCountComparator implements Comparator<Details> {

        public int compare(Details lhs, Details rhs) {
            if (lhs.total > rhs.total) {
                return -1;
            }
            if (lhs.total < rhs.total) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    // sort alphabetically comparator
    private class NameComparator implements Comparator<Details> {

        public int compare(Details lhs, Details rhs) {
            String lhsLower = lhs.name.toLowerCase();
            String rhsLower = rhs.name.toLowerCase();
            if (lhsLower.compareTo(rhsLower) < 0) {
                return -1;
            }
            if (lhsLower.compareTo(rhsLower) > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
