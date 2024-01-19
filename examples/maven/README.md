# Logfire Maven example 

* Get your source token at [Logfire -> Sources](http://logs.logfire.com/team/0/sources).
* Edit [src/main/resources/logback.xml](src/main/resources/logback.xml) and replace `<!-- YOUR SOURCE TOKEN -->` with your source token.
* Run `mvn compile -e exec:java -Dexec.mainClass="com.logfire.example.App"` from this directory.
* You should see a `Hello World!` log along with a few other examples in [Logfire -> Live tail](https://logs.logfire.com/team/0/tail).
