
## A Maven Project for the Java APG 1.1.0 Examples
The examples are meant to illustrate the basic usage of Java APG with emphasis on the new UDT feature, first introduced in Java APG Version 1.0. Building the jar file and executing the examples is fine, as far as it goes. But to be really useful, you need to be able to follow along in a debugger, make changes, play with your own modifications, etc.

As released, Java APG 1.1.0 is not set up for an "in place" maven project. If you are already a maven expert, you should have no trouble doing this. (See, for example, the branch ssill2/apg-java.) However, here one way to convert just the examples to a maven project. I'll assume that we are in the directory `/my-path` and using maven 3.8.1,  JDK 11 and NetBeans 12.3. Other versions will surely work and you can adjust these instructions accordingly.
```
git clone https://github.com/ldthomas/apg-java.git
cd apg-java/build
./make-jars
mvn install:install-file -DgroupId=com.sabnf -DartifactId=apg -Dversion=1.1-SNAPSHOT -Dpackaging=jar -Dfile=apg.jar -DgeneratePom=true
```
In NetBeans open a new project with `File->New Project->Java with maven->Project from Archetype`.  Click `next` and select `maven-archetype-quickstart`. There may be more than one selection with that name. Just select the first one. Click `next` and set,
```
Project Name: examples
Project Location: browse to /my-path
Group Id: examples
Version: 1.1-SNAPSHOT
Package: examples
Click finish
```
At this point you should have a complete "Hello World" project. Click `Run->Run Project (examples)` and you should see "Hello Wold" in the output. If it fails, check `Tools->Options->Java->Maven` and make sure the Maven Home points to your installation of maven 3.8.1 and that the Default JDK selection is JDK 11. _(Note that the selection JDK 11 (Default) may not actually be pointing to a valid JDK installation._

From here, we just need to get the examples into the project shell and update the Project Object Model.
```
rm /my-path/examples/src/main/java/examples/*
cp -r /my-path/apg-java/src/examples/* /my-path/examples/src/main/java/examples
```
In NetBeans, open the pom.xml file. In the `<properties>` section, if JDK 11 is not already specified, replace the maven compiler directives with
```
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
```
In the `<dependencies>` section add
```
<dependency>
    <groupId>com.sabnf</groupId>
    <artifactId>apg</artifactId>
    <version>1.1-SNAPSHOT</version>
    <scope>compile</scope>
</dependency>
```
In NetBeans, right-click on the examples project and select `Properties->Run` and set,
```
Main Class: examples.Main
Arguments   /test=all /reps=1
click OK
```
_(Thanks to @ssill2 for showing me this obscure way of setting the runtime arguments.)_
Now `Run->Run Project (examples)` should run all of the tests. At this point you can now set break points and run `Debug->Debug Project (examples)` as well.

There is another clever way to debug with varying runtime arguments, this again thanks to @ssill2. Open the file `Test Packages/examples/AppTest.java` and replace the simple assertion test with,
```
examples.Main.main((new String[] {"/test=demo-ast", "/reps=1"}));
```
Toggle a break point on the line. To debug right click on the AppTest.java file and select `Debug Test File`.

There you have it. You are on your way to fully exploring the Java APG, Version 1.1.0 examples. 



