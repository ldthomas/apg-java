## Java APG

**Version:** 1.1.0

*Note:* Version 1.1.0 updates version 1.0 in a couple of ways. Primarily, the license is changed to the more permissive 2-Clause BSD license. Also, in the installation section below are instructions on how to include Java APG in a [maven](https://maven.apache.org/) project. I want to thank [Raviteja Lokineni](https://github.com/bond-) (@bond-) for suggesting these changes and especially [Stephen Sill II](https://github.com/ssill2) (@ssill2) for his friendly and expert nudging of me up the maven learning curve. (See issue #6.)

**Description:**

<ul><li><b>Java APG</b> is APG - an ABNF Parser Generator written completely in the Java language.
</li>
<li>generates parsers in Java
</li>
<li>generates language parsers and translators from a superset of the Augmented Backus-Naur Form (<a href="https://tools.ietf.org/html/rfc5234">RFC5234</a> and <a href="https://tools.ietf.org/html/rfc7405">RFC7405</a>) grammar syntax
</li>
<li>accepts valid ABNF grammars
</li>
<li>accepts <code>AND</code> & <code>NOT</code> syntactic predicate operators for conditional parsing based on specified, look-ahead phrases
</li>
<li>accepts User-Defined Terminals (UDTs) which allow user-written, non-Context-Free phrase recognition operators
</li>
<li>user-written callback functions provide complete monitoring and flow control of the parser</li>
<li>accept case-sensitive literal strings in single quotes</li>
<li>accept multiple input ABNF grammar files</li>
<li>optional generation of an Abstract Syntax Tree (AST)</li>
<li>translation of the AST with user-written callback functions</li>
<li>extensive tracing facilities</li>
<li>statistics gathering for a full picture of parse tree node coverage</li>
<li>extensive attribute generation for an overview of the grammar's characteristics</li>
</ul>
For more details see the documentation or visit the <a href="https://sabnf.com">APG website</a>.

| Directories/files:       | description                                                                                                                                                                                                                         |
| :----------------------- | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| src/main/java/apg                  | Java APG, the generator and the runtime library required by all generated parsers.                                                                                                                                                  |
| src/test/java/examples/            | Examples to demonstrate how to set up and use a Java APG parser. The main function here is the driver for running any and all of the following examples. See javadoc `examples.Main` for a complete list of the tests that it runs. |
| src/test/java/examples/anbn        | Comparisons the CFG and UDT parsers for the a<sup>n</sup>b<sup>n</sup>, n > 0, grammar.                                                                                                                                             |
| src/test/java/examples/anbncn      | Comparisons the CFG and UDT parsers for the a<sup>n</sup>b<sup>n</sup>c<sup>n</sup>, n > 0, grammar.                                                                                                                                |
| src/test/java/examples/demo        | Demonstrates many of the main features of Java APG including UDTs.                                                                                                                                                                  |
| src/test/java/examples/expressions | Comparisons the CFG and UDT parsers for the expressions grammar.                                                                                                                                                                    |
| src/test/java/examples/inifile     | Comparisons the CFG and UDT parsers for the inifile grammar.                                                                                                                                                                        |
| src/test/java/examples/mailbox     | Comparisons the CFG and UDT parsers for the mailbox grammar ().                                                                                                                                                                     |
| src/test/examples/testudtlib  | Comparisons the CFG and UDT parsers for the suite of UdtLib UDTs.                                                                                                                                                                   |
| build/                        | (Deprecated carried over from non-maven build) Scripts and files for compiling and documenting Java APG.                                                                                                                                                                           |
| LICENSE.md                  | 2-Clause BSD license.                                                                                                                                                                                        |
| README.md                | This file.                                                                                                                                                                                                                          |

**Installation:**
The `build/` directory has scripts and files for compiling and documenting Java APG.  
To compile all source code and create the package `.jar` files:

```
git clone https://github.com/ldthomas/apg-java.git
cd apg-java
git checkout maven
mvn clean install

```

This will do the following:
Build the library jar under target.
Execute unit tests.
Generate javadoc under target/site/apidocs
Install the jar in the local maven repository for use by other projects that need apg-java as a dependency.

```
To use apg-java in your project just modify the project's pom.xml file to include the following dependency.
```
<dependencies>
    <dependency>
       <groupId>com.ldthomas.apg</groupId>
       <artifactId>apg-java</artifactId>
       <version>1.1.0</version>
    </dependency>
</dependencies>

``` 
See the [maven.md](maven.md) for details on how to run the examples in a NetBeans maven project.


To see the documentation open the following file in a web-browser.  Or visit the [APG website](https://sabnf.com).

```
target/site/apidocs/index.html
```

**Copyright:**  
 _Copyright &copy; 2021 Lowell D. Thomas, all rights reserved_

**License:**  
 Java APG Version 1.1.0 is released under the [2-Clause BSD license](https://opensource.org/licenses/BSD-2-Clause).
