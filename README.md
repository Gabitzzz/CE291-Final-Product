**1. How to compile and run our code**

First of all we must download the repository from GitLab, after the repository is downloaded and extracted we then navigate to the src folder within command prompt and then run the following command.

```
javac Team31\*.java
```

This will compile all of the .java files from within the Team31 folder. Next now run the class files by doing the following.

```
java Team31.Main
```

This is how you compile and run our code. Our main class is called "Main" and "Team31" is the package. The member classes of this package are as follows; Config, Data, DataStore, GenerateGraph, LinearRegression, Main, MainFrame. We decided to compile all classes in the Team31 folder by using the *.java wildcard however these can all be individually listed if necessary like so.

```
cd Team31
javac Main.java Config.java Data.java DataStore.java GenerateGraph.java LinearRegression.java MainFrame.java
cd ../
java Team31.Main
```

**2. Prerequisites**

In order to ensure our code runs you should ensure that the provided csv files are always in the Team31 folder along with all of our java/class files.

**3. How to run the provided .jar executable**

Navigate to the folder containing the .jar file we provided and then run the following command.

```
java -jar ce291_team31.jar
```

The jar can be found [here](https://cseegit.essex.ac.uk/2020_ce291/ce291_team31/-/blob/master/FinalProduct/ce291_team31.jar).

**4. Known issues / bugs**

Currently there is no known issues.
