This a simple spring boot application using gradle for building.

It uses a gradle pact jvm provider plugin to execute the pact (see build.gradle for configuration).

For more information go to - https://github.com/DiUS/pact-jvm/tree/master/pact-jvm-provider-gradle.

 
Dependencies: 
- git-secret.
- Docker
- GCloud cli 
- Gradle
- Java 1.8

Ask someone with repo access to add your email address and public gpg key.

They wil need to run: 
```git secret tell your@gpg.email```

And then push the change.

You will then need to run ```git secret reveal``` which will decrypt the file 