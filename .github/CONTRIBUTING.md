Thank you for considering to help us on this project. This guidelines presents: 
* **How to configure your IDE**
* **How to contribute to Twitch4J**

## How to configure your IDE
Configuring IDE is important to starting contribute. We do like preferred use [IntelliJ IDEA](https://www.jetbrains.com/idea/). 
Considering other IDE is possible if:
* Have annotation processor support like plugins, extension etc.
* Supporting Gradle project
* Having installed Git (optional supported as plugin or extension)
* Supporting Java Development Kit 8 and late

### Prepare your IDE
Before start we need a [Java Development Kit 8 (JDK8)](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html). Install it and configure environmental variables: `JAVA_HOME` if not exist, create and adding to `PATH` - `$JAVA_HOME/bin`.
#### IntelliJ IDEA
[Jetbrains](https://www.jetbrains.com/) provides a most popular IDE for JVM referenced programming language.
It is a great choice to starting journey with this IDE. That's why we recommending it for people who's like programming in Java.

Before starting configuration this IDE we need [download IDE](https://www.jetbrains.com/idea/download/) and [Lombok plugin](https://plugins.jetbrains.com/plugin/6317-lombok-plugin) (you can install it from the **Settings** menu)
* Import project using link for git project. 
	
	Go to **File** > **New** > **Project from Version Control** > **Git**
	Paste your link in **URL** field and press **Clone** to start cloning repository.

* In *bottom-right corner* will shows pop-out which inform the Gradle project has been founded and ready to link. Click **Import Gradle Project**.

	If you lost this pop-out you can find them in **Event Log** (default: *bottom-right tab*)

* Then will shows a window **Import Module from Gradle**. 
	* Uncheck **Create separate module per source set** - this one makes issue in annotation processing.
	* Select **Use gradle 'wrapper' task configuration** - cause we provide gradle wrapper for this repository. We not recommend using a different gradle version cause about tasks works with bundled wrapper.
	* Set **Gradle JVM** if module couldn't found `JAVA_HOME` variable we should add it manually using three dots (`...`) button.
	* Press **OK** and wait till IDE finished importing dependencies
	
* Next step is install [Lombok Plugin](https://plugins.jetbrains.com/plugin/6317-lombok-plugin).
	
	Go to **File** > **Settings** and **Plugins** tab. Click **Browse repositories...** button. Use search bar typing `Lombok Plugin`. Install it and restart IDE.
	
* Final step is **Annotation Processors**
	
	Project will not import all configurations to the IDE. We should do manually.
	Same way like the Lombok (**File** > **Settings**) but we going to **Build, Execution, Deployment** > **Compiler** > **Annotation Processors**.
	* Check **Enable annotation processing**
	* Select **Module content root**
	* Change sources directory to:
		* **Production sources directory**: `build\generated\source\apt\main`
		* **Test sources directory**: `build\generated\source\apt\test`
	* Press **OK**
	
#### Eclipse
	Soon
#### Netbeans
	Soon


## How to contribute to Twitch4J
Of course you can contribute to our repository in 3 ways:
* Creating Issue
* Joining to our [Discord Server](https://discord.gg/FQ5vgW3)
* Create Pull Request

### Issue
All your ideas, features, bug reports, etc. must be documented in following comments on [issue](https://github.com/twitch4j/twitch4j/issues/new) tab.
Questions in issue are not welcome. Use Discord server below to asking developers for your question.

### Discord Server
Your problem is a hardest to reproduce? Having a questions, how to and where? Come, join to our [Discord Server](https://discord.gg/FQ5vgW3)

### Pull Requests
So, you are thinking about sending a pull request? Awesome! But... before starting on your pull request, you should read up on
the [pull request](https://github.com/twitch4j/twitch4j/compare) comment.

Before starting pull request make sure if:

1. All your code will be subject to the project's licence, in this case [MIT](https://github.com/twitch4j/twitch4j/blob/master/LICENSE).
2. Your code follows a style requirements mentioned below, and you didn't modified project style (ex. indentation style, bracket style, naming, comments, etc). 
Rewrites of certain systems are kindly welcome too.
3. *Your pull-request must be created **ONLY** against the `develop` branch!* 

#### Style Requirements
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
