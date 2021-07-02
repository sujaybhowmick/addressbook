# Address Book API

## Prerequisties

1. Java 11
2. Docker engine to build the docker image

## Technology Stack

1. Java 11
2. SpringBoot 2.5.1
3. H2 database
4. Flyway database migration tool

## Run Tests

```bash
addressbook % ./gradlew check


BUILD SUCCESSFUL in 996ms
4 actionable tasks: 4 up-to-date
```

## Create Boot Jar

```bash
addressbook % ./gradlew clean bootJar

BUILD SUCCESSFUL in 1s
5 actionable tasks: 5 executed
```

## Build Docker Image

```bash
addressbook % docker build . -t reecegroup/addressbook:1.0
[+] Building 1.4s (13/13) FINISHED
 => [internal] load build definition from Dockerfile                                                                                                                                                                                                                  0.0s
 => => transferring dockerfile: 37B                                                                                                                                                                                                                                      0.0s
 => [internal] load .dockerignore                                                                                                                                                                                                                                        0.0s
 => => transferring context: 2B                                                                                                                                                                                                                                          0.0s
 => [internal] load metadata for docker.io/library/adoptopenjdk:11-jre-hotspot                                                                                                                                                                                           1.3s
 => [stage-1 1/6] FROM docker.io/library/adoptopenjdk:11-jre-hotspot@sha256:8ec8ac091f127f9a41b96bf06e2f0404bd86a516326e208234c9cd18a5955eb9                                                                                                                             0.0s
 => [internal] load build context                                                                                                                                                                                                                                        0.0s
 => => transferring context: 120B                                                                                                                                                                                                                                        0.0s
 => CACHED [stage-1 2/6] WORKDIR application                                                                                                                                                                                                                             0.0s
 => CACHED [builder 3/4] COPY build/libs/*.jar application.jar                                                                                                                                                                                                           0.0s
 => CACHED [builder 4/4] RUN java -Djarmode=layertools -jar application.jar extract                                                                                                                                                                                      0.0s
 => CACHED [stage-1 3/6] COPY --from=builder application/dependencies/ ./                                                                                                                                                                                                0.0s
 => CACHED [stage-1 4/6] COPY --from=builder application/spring-boot-loader/ ./                                                                                                                                                                                          0.0s
 => CACHED [stage-1 5/6] COPY --from=builder application/snapshot-dependencies/ ./                                                                                                                                                                                       0.0s
 => CACHED [stage-1 6/6] COPY --from=builder application/application/ ./                                                                                                                                                                                                 0.0s
 => exporting to image                                                                                                                                                                                                                                                   0.0s
 => => exporting layers                                                                                                                                                                                                                                                  0.0s
 => => writing image sha256:5a156697f4b9e1b6f519d1fe7fbf6682dc77e020f0ed29df5b0f3c76d949b906                                                                                                                                                                             0.0s
 => => naming to docker.io/reecegroup/addressbook:1.0
```

## Run the Docker Image

```bash
addressbook % docker run -it -p8080:8080 reecegroup/addressbook:1.0
```



## cURL commands for API

#### Create a User

###### API Request

```bash
curl --location --request POST 'http://localhost:8080/addressbook/api/v1/user' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "Foo",
    "lastName": "Bar",
    "middleName": "Fighter",
    "userName": "FooFighter1"
}'
```

###### API Responses

```json
# 200 OK
{
    "data": {
        "createdAt": "2021-07-02T01:01:58.856802Z",
        "updatedAt": "2021-07-02T01:01:58.856811Z",
        "version": null,
        "id": 1,
        "userName": "FooFighter1",
        "firstName": "Foo",
        "lastName": "Bar",
        "middleName": "Fighter"
    }
}
```

```json
# 400 BAD REQEUST - Cannot create user with same Username
{
    "status": "BAD_REQUEST",
    "message": "UserExistsException",
    "errors": [
        "User FooFighter1 already exists"
    ]
}
```



#### Create a Address book

###### API Request

```bash
# Provide the userName of the User for the owner property
curl --location --request POST 'http://localhost:8080/addressbook/api/v1/addressBook' \
--header 'Content-Type: application/json' \
--data-raw '{
    "addressBookName": "Personal Contacts",
    "owner": "FooFighter1"
}'
```

###### API Responses

```JSON
# 200 OK
{
    "data": {
        "createdAt": "2021-07-02T01:05:27.330298Z",
        "updatedAt": "2021-07-02T01:05:27.330303Z",
        "version": null,
        "id": 1,
        "addressBookName": "Personal Contacts",
        "owner": {
            "createdAt": "2021-07-02T01:01:58.856802Z",
            "updatedAt": "2021-07-02T01:01:58.856811Z",
            "version": null,
            "id": 1,
            "userName": "FooFighter1",
            "firstName": "Foo",
            "lastName": "Bar",
            "middleName": "Fighter"
        }
    }
}
```

```JSON
# 400 BAD REQUEST - Cannot create Address book with same name
{
    "status": "BAD_REQUEST",
    "message": "AddressBookExistsException",
    "errors": [
        "Address Book with name Personal Contacts already exists for user FooFighter1"
    ]
}
```

```JSON
# 404 NOT FOUND - Trying to create Address book for non existent User FooFighter2
{
    "status": "NOT_FOUND",
    "message": "UserNotFoundException",
    "errors": [
        "User FooFighter2 not found"
    ]
}
```



#### Create a Contact in an Address book

###### API Request

```bash
# Provide the Address book Id of the Address book for which we want to add the contact in the JSON property addressBookId
curl --location --request POST 'http://localhost:8080/addressbook/api/v1/contact' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "+61999333999",
    "addressBookId": 1
}'
```

###### API Responses

```Json
# 200 OK
{
    "data": {
        "createdAt": "2021-07-02T01:08:41.802529Z",
        "updatedAt": "2021-07-02T01:08:41.802580Z",
        "version": null,
        "id": 1,
        "contactHash": "19ba7146a1f2c5d5d6f38a5f1bd56141",
        "firstName": "John",
        "lastName": "Doe",
        "middleName": null,
        "phoneNumber": "+61999333999",
        "addressBook": {
            "createdAt": "2021-07-02T01:05:27.330298Z",
            "updatedAt": "2021-07-02T01:05:27.330303Z",
            "version": null,
            "id": 1,
            "addressBookName": "Personal Contacts",
            "owner": {
                "createdAt": "2021-07-02T01:01:58.856802Z",
                "updatedAt": "2021-07-02T01:01:58.856811Z",
                "version": null,
                "id": 1,
                "userName": "FooFighter1",
                "firstName": "Foo",
                "lastName": "Bar",
                "middleName": "Fighter"
            }
        }
    }
}
```

```JSON
# 400 BAD REQUEST - Cannot create a contact with same First name, Last name, Middle name and Phone number in the same address book
{
    "status": "BAD_REQUEST",
    "message": "ContactExistsException",
    "errors": [
        "Contact with following details already exists - John, Doe, +61999333999"
    ]
}
```

```JSON
# 404 NOT FOUND - Address book with Id 2 is not found, hence cannot create a Contact for a non existent Address book
{
    "status": "NOT_FOUND",
    "message": "AddressBookNotFoundException",
    "errors": [
        "Address Book with Id 2 not found"
    ]
}
```



#### Delete a Contact in an Address book

###### API Request

```bash
# URL Scheme - http://localhost:8080/addressbook/api/v1/contact/{contactId}
# Replace {contactId} with Id of the Contact in the Address book
curl --location --request DELETE 'http://localhost:8080/addressbook/api/v1/contact/1' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json'
```

###### API Responses

```JSON
	# 200 OK - Return the deleted Contact information
	{
    "data": {
        "createdAt": "2021-07-02T01:08:41.802529Z",
        "updatedAt": "2021-07-02T01:08:41.802580Z",
        "version": null,
        "id": 1,
        "contactHash": "19ba7146a1f2c5d5d6f38a5f1bd56141",
        "firstName": "John",
        "lastName": "Doe",
        "middleName": null,
        "phoneNumber": "+61999333999",
        "addressBook": {
            "createdAt": "2021-07-02T01:05:27.330298Z",
            "updatedAt": "2021-07-02T01:05:27.330303Z",
            "version": null,
            "id": 1,
            "addressBookName": "Personal Contacts",
            "owner": {
                "createdAt": "2021-07-02T01:01:58.856802Z",
                "updatedAt": "2021-07-02T01:01:58.856811Z",
                "version": null,
                "id": 1,
                "userName": "FooFighter1",
                "firstName": "Foo",
                "lastName": "Bar",
                "middleName": "Fighter"
            }
        }
    }
}
```

```JSON
# 404 NOT FOUND - Trying to delete and deleted or non existent contact
{
    "status": "NOT_FOUND",
    "message": "ContactNotFoundException",
    "errors": [
        "Contact with Id 1 not found"
    ]
}
```



#### Get All Contacts in an Address book

###### API Request

```bash
# URL Scheme - http://localhost:8080/addressbook/api/v1/addressBook/{addressBookId}/contact/list
# Replace {addressBookId} with Id of the address book
curl --location --request GET 'http://localhost:8080/addressbook/api/v1/addressBook/1/contact/list' \
--header 'Accept: application/json'
```

###### API Response

```JSON
# 200 OK
{
    "data": [
        {
            "createdAt": "2021-07-02T01:08:41.802529Z",
            "updatedAt": "2021-07-02T01:08:41.802580Z",
            "version": null,
            "id": 1,
            "contactHash": "19ba7146a1f2c5d5d6f38a5f1bd56141",
            "firstName": "John",
            "lastName": "Doe",
            "middleName": null,
            "phoneNumber": "+61999333999",
            "addressBook": {
                "createdAt": "2021-07-02T01:05:27.330298Z",
                "updatedAt": "2021-07-02T01:05:27.330303Z",
                "version": null,
                "id": 1,
                "addressBookName": "Personal Contacts",
                "owner": {
                    "createdAt": "2021-07-02T01:01:58.856802Z",
                    "updatedAt": "2021-07-02T01:01:58.856811Z",
                    "version": null,
                    "id": 1,
                    "userName": "FooFighter1",
                    "firstName": "Foo",
                    "lastName": "Bar",
                    "middleName": "Fighter"
                }
            }
        }
    ]
}
```

#### Get Unique Contacts across all Address books of a User

###### API Request

```bash
# URL Scheme -  http://localhost:8080/addressbook/api/v1/user/{userId}/contact/list 
# Replace {userId} with Id of the User
curl --location --request GET 'http://localhost:8080/addressbook/api/v1/user/1/contact/list' \
--header 'Accept: application/json'
```

###### API Response

```JSON
# 200 OK
{
    "data": [
        {
            "firstName": "John",
            "lastName": "Doe",
            "middleName": null,
            "phoneNumber": "+61999333999"
        }
    ]
}
```

