This project is intended to better understand how to build and deploy applications in gcloud/kubernetes stack.

Pipeline for this project is available [here](https://circleci.com/gh/nahknarmi/workflows/com.metlife.provider/tree/master)
 
Dependencies:
----------------------------------------------------------------------

- [Brew](https://brew.sh/)
- [Docker CLI](https://docs.docker.com/docker-for-mac/install/)
- [GCloud CLI](https://cloud.google.com/sdk/) 
- [GPG tools](https://gpgtools.org/)
- JDK 1.8 - ```brew tap caskroom/versions && brew cask install java8```
- Gradle - ```brew install gradle```
- git-secret - ```brew install git-secret```


Accessing secrets
----------------------------------------------------------------------
Ask someone with repo access to add your email address and public gpg key to the project.

They wil need to run: 

```git secret tell your@gpg.email```

And then push the change.

You will then need to run 

```git secret reveal``` 

which will place a decrypted copy of the file for you locally.


Build
---------------------------------------------------------------------- 
You can build the project as follows:

```gradle clean build```


Deploy
----------------------------------------------------------------------
Deployment is captured in code as part of the circle ci pipeline (see ```.circleci/config.yml```). 
The actual implementation of the steps is captured under ```scripts/circleci/```. 


Cluster creation and teardown
---------------------------------------------------------------------- 

You can create a new cluster ready to deploy to with following command:

```./scripts/gcloud/create-cluster.sh```

To save monies you can tear down the cluster with following command:

```./scripts/gcloud/teardown-cluster.sh```

