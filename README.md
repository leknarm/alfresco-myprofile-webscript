# Alfresco myprofile webscript

Adding myprofile rest api to alfresco repository webscript for getting current user and container group.

## Getting start
```
mvn install -Pamp-to-war #run test
mvn package #package amp
```

## Test your result

Go to your browser and hit
```
http://localhost:8080/alfresco/service/api/people/myprofile
```
With username: admin, password: admin

## Note.

This project used Alfresco SDK 2.2.0 with Alfresco Repository AMP extension
[http://docs.alfresco.com/5.1/concepts/alfresco-sdk-intro.html](http://docs.alfresco.com/5.1/concepts/alfresco-sdk-intro.html)
