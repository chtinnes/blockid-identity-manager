# BlockId Identity Manager

## What is BlockId
BlockId is a set of applications which can be used together to create a **distributed identity provider** which stores identities based on the Tendermint Blockchain.

BlockId currently consists of 3 repos:
1. Identity Storage based on Tendermint at https://github.com/chtinnes/blockid-identity-storage/. This handles the connection to a blockchain node and stores so called identity assertions.
2. Identity Manager at https://github.com/chtinnes/blockid-identity-manager/. This handles handles creation, encryption, decryption and verification of identity assertions.
3. Deployment artifacts at https://github.com/chtinnes/blockid-deployment/

BlockId is a very lighweight and uses so called identity assertions, so that someone can only expose as less details as necessary about his identity.
These assertions are of the form:
[Someone] has [attribute] of value [attribute_value] - signed by [someone else].

Since the identity assertions are broadcasted to everyone in the distributed network the attribute_value is encrypted and can only be decrypted by the identity receiver(*someone* in the above terminology).
If the receiver needs to show this identity assertion to someone else he can reference the assertion and give him the one-time key used to encrypt the attribute_value.

The following Component Diagram shows the main componenets, their relationships and interfaces.
![Component Diagram should appear here](https://user-images.githubusercontent.com/17828327/29003245-878a89e8-7ab3-11e7-8bee-3eb2a111307b.png)

## BlockId Identity Manager
This repository contains the application which manages identity assertions, i.e. creates, encrypts, decrypts and verifies identity assertions and communicates to an identity storage.

It is using the following technologies:

1. SpringBoot and Spring Framework for Applications (https://projects.spring.io/spring-boot/).
2. JDK 1.8 (http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) especially Java Security.
3. Swagger for API documentation (https://swagger.io/)

As of now, the identity manager does not use an own database for backing assertions or a cache. For a large application, it might be usefull to use some kind of cache.

### What does this Identity Manager?

The identity manager consists of three main components:

1. Identity Giver - Used to create, encrypt and sign identity assertions and send them to the identity storage.
2. Identity Validator - Used to decrypt identity assertions and verify the assertions signature.
3. Identity Receiver - Used to fetch, decrypt and verify identity assertions wich belong to the configured own address. 

Theoretically, all off them could also be own applications, but in many they should be bounded together and build something which then can be called an identity manager. 

It is (almost) possible to run the application which each combination of the components active using Spring Profiles (https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-profiles.html). For this, just run the application which the desired profiles active, e.g.: 

```
spring.profiles.active=idgiver,idreceiver,idvalidator
```
or via an environment variable 
```shellscript
export SPRING_PROFILES_ACTIVE=idgiver,idreceiver,idvalidator
```
to switch on all components.

Note: Currently The identity receiver is using the identity validator to decrupt and verify the signature of Identity Assertion Messages. In future, this could be decoupled as well, but for now, if you run identiy receiver you have to run the identity validator as well.

The Identity manager comes with an example app as well. 
You can imaging, that in an use case, you want to verify that someone has age > 18.
This example app contains a veriy ligthweihgt rule engine. One can define so called Business Rules and then validate identity assertions against this Business Rules. You can then define a Business Rule for that and ask for a identity assertion, claiming a certain age for someone.

The example app can also be switched on and off using Spring Profiles.

The Application provides a REST Api to fetch identity assertions from the database or to broadcast messages to the blockchain network.

## Remarks on the used technologies

### Swagger

Swagger is used here to have an overview over the REST API of the identity manager and an easy way to access the API. It comes also with a GUI, so it is very handy for a first playing around with the identity manger application.

### Sprint Boot

It is very easy to built microservice applications in Spring Boot. Spring Boot is very well documented and provides utilties and autoconfiguration for things one uses very often in web application development.
Since we like to built microservice environments or SOA environments, a good framework to built the "small" applications needs to be choosen.
Since Spring Boot Applications can be started as simple *java jars*, spring boot applications are very easy to deploy and don't need a heavy weight application container.

## Deployment of the block-id application.

For how to deploy the block-id application see [blockid-deployment](https://github.com/chtinnes/blockid-deployment/).
That Repository contains deployment artifacts to deploy the application on various environments.
