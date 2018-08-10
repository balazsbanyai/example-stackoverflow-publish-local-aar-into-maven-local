# Demo project for [question](https://stackoverflow.com/questions/51783925/how-to-publish-in-my-maven-local-repository-an-existing-aar-with-gradle)

## Original question
### How to publish in my maven local repository an existing aar with gradle?

I have a project that have the following structure:

projectRoot

build.gradle

module1/

build.gradle

artifact1.aar

module2/ ....

My artifact1.aar is a compiled artifact and i have no access to the sources.

my module 1 gradle build file is the following:

configurations.maybeCreate("default")
artifacts.add("default", file('artifact1.aar'))
With this the code contains in the .aar is available in module 2 by simply reference a gradle project dependency.

But i want to publish the .aar in my maven local, in order that it can be accessible for other android project.

I check the maven-publish plugin and the android-maven-publish plugin but the two seems to be called after java-library plugin or com.android.library plugin.

So my question is how to publish in my maven local repository an existing aar with gradle ?

I'm using gradle in version 4.8.

## Solution

I used an rxbinding aar for this demo. It is very important to mention any aar published this way, will not bring it's dependencies, so you have to know what libs are needed to actually use the published aar.

1. As you correctly mentioned, there has to be a subproject in the "publisher" project, which must contain the aar, and a build file with the following content:
```groovy
// rxbinding/build.gradle
apply plugin: "maven-publish"

configurations.maybeCreate("default")
def publishArtifact = artifacts.add("default", file('rxbinding-2.1.1.aar'))

publishing {
    publications {
        aar(MavenPublication) {
            groupId = 'my.sample.artifact'
            artifactId = 'somerandomname'
            version = '1.0.0'
            artifact publishArtifact
        }
    }
}
```

2. Now run `./gradlew rxbinding:publishToMavenLocal` - This should place the artifact into your local repo
3. Add `implementation 'my.sample.artifact:somerandomname:1.0.0'` to the consumer app, in any project on your machine.

This way, you should be able to use the published aar in any project in your machine. Again, keep in mind, that this dependency is unaware of what dependencies are actually needed for itself to run properly.


