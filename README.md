# Train-Station
# Pre-requisites
* Java 1.8/1.11/1.15
* Maven

# How to run the code

The package includes scripts to execute the code. 

Use `run.sh` if you are Linux/Unix/macOS Operating systems and `run.bat` if you are on Windows.  Both the files run the commands silently and prints only output from the input file `sample_input/input1.txt`. You are supposed to add the input commands in the file from the appropriate problem statement. 

Internally both the scripts run the following commands 


 * `mvn clean install -DskipTests assembly:single -q` - This will create a jar file in the `target` folder.
 * `java -jar target/jarName.jar sample_input/input1.txt` - This will execute the jar file passing in the sample input file and the configuration file as command line arguments
