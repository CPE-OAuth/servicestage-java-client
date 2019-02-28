@echo off
title Build JAR and Copy to Eclipse plugin library folder
cls
rem build jar file without test
echo submit build request
call mvn clean package -f pom.xml -DskipTests -DfinalName=servicestage-client
echo build completed
rem result jar to the plugin lib folder under the same parent conatainer
echo copy servicestage-client.jar to plugin\lib folder 
copy /Y target\servicestage-client.jar ..\servicestage-eclipse-plugin\lib\