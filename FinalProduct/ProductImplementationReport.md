

**Technical Description**
Class Diagram: FinalProduct/img/ClassDiagram.png

**General Implementation**

Primarily, before implementing any algorithms or data structures, a language and a development area should be chosen. Java is selected as the programming language for development of the product. Although there are some claims about Integrated Development Environments that they are not good to improve coding skills, as a team it is the upmost necessity to choose an IDE as it is crucial to manage the whole development and speed the process up. In fact, IntelliJ is chosen for our team since it is one of the most advanced IDEs for standard Java application development and GitLab is used to manage all of the changes in code. In addition, Java Swing libraries are used for Graphical User Interface of the product. All APIs used in the development phase of the product are built-in APIs in Java standard libraries included in Java JDK version 15. Furthermore, there is not any dependencies for the products software to run.

- Language and Libraries: Java JDK 15, Java Swing
- Development Area: IntelliJ IDE
- Management Tool: GitLab online and private repositories
- Third Party Libraries and/or Dependencies: Non

**Data Structures**

Since NHS, the client, wants the product to read publically available Covid-19 cases and deaths data represented as csv files, a custom data structure needed to be created to represent the data as objects in Java. Therefore, we coded a simple data structure that includes three variables to hold dates as String variables, new data each day as long variables and cumulative data as long variables. When each line is read from the files, the mentioned data is stored as objects of mentioned custom data structure.

- Program reads three main data from the csv files
- All of them is stored in a single object of custom data structure for each set of data
- Date as date(String), new data each day as newToday(long) and cumulative as cumulative(long)

**Algorithms**

For the second most crucial functionality of the product, we were told to predict the future cases and deaths based on the current data. To achieve this, we decided to use linear regression method which is one of the most popular machine learning models. We successfully implemented and predicted the future 10 weeks of data, which is considerably near future, by training the model with last 5 weeks of data from the original files.

- Trained model with last 5 weeks of data
- Attempted to predict 10 weeks of future data
- Visualized the model with different colors of lines drawn on to the original graph of data

Linear regression is machine learning model that calculates a y value given an x value using straight line equation. In order to do that, the model should first find the value of m (gradient) and c (y-intercept, y value when x equals to 0) and this is achieved by training the model by giving it to a set of x and y values related to each other. The mentioned equation is written below as;

_Y = X \* M + C_



**Imported Libraries**




**Known Issues**
