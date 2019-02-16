Guava Converter
===============

A `Converter` which supports Guava's `Optional<T>` by delegating to other converters for `T`
and then wrapping it into `Optional`.


Download
--------

Download [the latest JAR][1] or grab via [Maven][2]:
```xml
<dependency>
  <groupId>com.squareup.retrofit2</groupId>
  <artifactId>converter-guava</artifactId>
  <version>latest.version</version>
</dependency>
```
or [Gradle][2]:
```groovy
implementation 'com.squareup.retrofit2:converter-guava:latest.version'
```

Snapshots of the development version are available in [Sonatype's `snapshots` repository][snap].



 [1]: https://search.maven.org/remote_content?g=com.squareup.retrofit2&a=converter-guava&v=LATEST
 [2]: http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.squareup.retrofit2%22%20a%3A%22converter-guava%22
 [snap]: https://oss.sonatype.org/content/repositories/snapshots/
