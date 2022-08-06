@echo off

mvn clean install -DskipTests assembly:single -q

:: geektrust as jar name for online automatic analysis
java -jar target\geektrust.jar sample_input\input1.txt