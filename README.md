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
| src/apg                  | Java APG, the generator and the runtime library required by all generated parsers.                                                                                                                                                  |
| src/examples/            | Examples to demonstrate how to set up and use a Java APG parser. The main function here is the driver for running any and all of the following examples. See javadoc `examples.Main` for a complete list of the tests that it runs. |
| src/examples/anbn        | Comparisons the CFG and UDT parsers for the a<sup>n</sup>b<sup>n</sup>, n > 0, grammar.                                                                                                                                             |
| src/examples/anbncn      | Comparisons the CFG and UDT parsers for the a<sup>n</sup>b<sup>n</sup>c<sup>n</sup>, n > 0, grammar.                                                                                                                                |
| src/examples/demo        | Demonstrates many of the main features of Java APG including UDTs.                                                                                                                                                                  |
| src/examples/expressions | Comparisons the CFG and UDT parsers for the expressions grammar.                                                                                                                                                                    |
| src/examples/inifile     | Comparisons the CFG and UDT parsers for the inifile grammar.                                                                                                                                                                        |
| src/examples/mailbox     | Comparisons the CFG and UDT parsers for the mailbox grammar ().                                                                                                                                                                     |
| src/examples/testudtlib  | Comparisons the CFG and UDT parsers for the suite of UdtLib UDTs.                                                                                                                                                                   |
| build/                   | Scripts and files for compiling and documenting Java APG.                                                                                                                                                                           |
| LICENSE.md                  | 2-Clause BSD license.                                                                                                                                                                                        |
| README.md                | This file.                                                                                                                                                                                                                          |

**Installation:**
The `build/` directory has scripts and files for compiling and documenting Java APG.  
To compile all source code and create the package `.jar` files:

```
git clone https://github.com/ldthomas/apg-java.git
cd apg-java/build
./make-jars
```

This will create `apg.jar` and `examples.jar`. To test `apg.jar` run:

```
java -jar apg.jar /version
```
To test `examples.jar` run:
```
java -jar examples.jar /test=all
```
**Maven:**
For maven development, install apg.jar in the local repository.
```
mvn install:install-file -DgroupId=com.sabnf -DartifactId=apg -Dversion=1.1-SNAPSHOT -Dpackaging=jar -Dfile=apg.jar -DgeneratePom=true
```
Then modify the project's pom.xml file to include,
```
<dependencies>
    <dependency>
        <groupId>com.sabnf</groupId>
        <artifactId>apg</artifactId>
        <version>1.1-SNAPSHOT</version>
        <scope>compile</scope>
    </dependency>
</dependencies>

``` 
See the [maven.md](maven.md) for details on how to run the examples in a NetBeans maven project.

_NOTE:_ A second branch, maven, has been added which is a complete maven project for apg and the examples.
It was created by [Stephen Sill II](https://github.com/ssill2) on his [apg-java fork](https://github.com/ssill2/apg-java).
It has updates to satisfy some of the security items flagged by
[Fortify](https://www.microfocus.com/en-us/cyberres/application-security/static-code-analyzer) security scans.
The Fortify updates have not been integrated into the master branch.

**Documentation:**  
To see the documentation run:

```
./make-javadoc
```

Open `../javadoc/index.html` in any web browser. Or visit the [APG website](https://sabnf.com).

**Copyright:**  
 _Copyright &copy; 2021 Lowell D. Thomas, all rights reserved_

**License:**  
 Java APG Version 1.1.0 is released under the [2-Clause BSD license](https://opensource.org/licenses/BSD-2-Clause).

