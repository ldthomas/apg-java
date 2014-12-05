##Java APG

**Version:** 1.0

**Description:**  

<ul><li><b>Java APG</b> is APG - an ABNF Parser Generator written completely in the Java language.
</li>
<li>generates parsers in Java
</li>
<li>generates language parsers and translators from a superset of the Augmented Backus-Naur Form (ABNF <a href="https://tools.ietf.org/html/rfc5234">RFC5234</a>) grammar syntax
</li>
<li>accepts valid ABNF grammars
</li>
<li>accepts <code>AND</code> & <code>NOT</code> syntactic predicate operators for conditional parsing based on specified, look-ahead phrases
</li>
<li>accepts User-Defined Terminals (UDTs) which allow user-written, non-Context-Free phrase recognition operators
</li>
<li>user-written callback functions provide complete monitoring and flow control of the parser</li>
<li>optional generation of an Abstract Syntax Tree (AST)</li>
<li>translation of the AST with user-written callback functions</li>
<li>extensive tracing facilities</li>
<li>statistics gathering for a full picture of parse tree node coverage</li>
<li>extensive attribute generation for an overview of the grammar's characteristics</li>
</ul>
For more details see the documentation or visit the <a href="http://www.coasttocoastresearch.com">APG website</a>.

|Directories/files:|description|
|:--------|:----------|  
|  src/apg                   |Java APG, the generator and the runtime library required by all generated parsers.
|  src/examples/                   |Examples to demonstrate how to set up and use a Java APG parser. The main function here is the driver for running any and all of the following examples. See javadoc `examples.Main` for a complete list of the tests that it runs. 
|  src/examples/anbn                |Comparisons the CFG and UDT parsers for the a<sup>n</sup>b<sup>n</sup>, n > 0, grammar.
|  src/examples/anbncn                 |Comparisons the CFG and UDT parsers for the a<sup>n</sup>b<sup>n</sup>c<sup>n</sup>, n > 0, grammar.
|  src/examples/demo             |Demonstrates many of the main features of Java APG including UDTs.
|  src/examples/expressions         |Comparisons the CFG and UDT parsers for the expressions grammar. 
|  src/examples/inifile          |Comparisons the CFG and UDT parsers for the inifile grammar.
|  src/examples/mailbox        |Comparisons the CFG and UDT parsers for the mailbox grammar ().
|  src/examples/testudtlib        |Comparisons the CFG and UDT parsers for the suite of UdtLib UDTs.
| build/ | Scripts and files for compiling and documenting Java APG.
|  LICENSE                     |Version 2 of the GNU General Public License.|
|  README.md                   |This file.|


**Installation:**
The `build/` directory has scripts and files for compiling and documenting Java APG.  
To compile all source code and create the package `.jar` files:  
```
cd (repo directory)
cd build
./make-jars
```
This will create `apg.jar` and `examples.jar`. To test `apg.jar` run:
```
java -jar apg.jar /version
```  
To test `examples.jar` run:
```  
java -jar examples.jar /help
```  
or better
```  
java -jar examples.jar /test=demo-ast
```  

**Documentation:**  
To see the documentation run:
```
./make-javadoc
```  
Open `../javadoc/index.html` in any web browser. Or visit the [APG website](http://www.coasttocoastresearch.com).

**Copyright:**  
  *Copyright &copy; 2011 Lowell D. Thomas, all rights reserved*  

**License:**  
  Java APG Version 1.0 is released under Version 2.0 or higher of the
  <a href="https://www.gnu.org/licenses/licenses.html">GNU General Public License</a>.
