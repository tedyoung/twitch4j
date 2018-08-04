# Coding Conventions

There are a few coding conventions in place to keep the project maintainable.

* This project uses [1tbs](https://en.wikipedia.org/wiki/Indentation_style#1TBS), with the exception that single 
line if statements do not need brackets.
* All instanced objects should have **getters** and **setters** using [Project Lombok](https://projectlombok.org/) (where reasonable)
* All object instances, methods and fields must be documented for getting sure what does things are doing.
* Documented notes (backslashes `//` or slashed stars `/* */`) is optional into inside methods for getting sure what does things are doing.
* If some parts of code is unofficial and they are using non-documented part of [Twitch API](https://dev.twitch.tv/docs), mark them using `@Unofficial("<link to source>")` annotation adding inside the source
* Documenting object instances (classes, interfaces, enums) must have a same format below:
```java
/**
 * {Your short description}
 * <p>
 * {Your longest description}
 * @author {Full name or Username} [{Github link or e-mail address}]
 * @version %I%, %G%
 * @since {next minor version (eg. 0.11.0)}
 */
public class Example {}
```
