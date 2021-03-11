**Introduction**

Our task was to design a machine learning model in which will give an overview of cases and deaths from Covid-19. Also, we had to implement a model in which it will predict the future numbers of deaths and cases  of Covid-19. The results were successful because we did what the task was asking and we implement more things for example daily graphs, and we implemented a more detail data, but this is used in the terminal.  


**Testing:**

1. **MAIN MENU TESTING**__

 In the picture below you will see our main menu program. Our aim was to create a main menu in which there will show first some basic functionalities, where this was done with great success. The design in our program will be find in the GenerateGraph and MainFrame classes, there is our styling and how we designed the graphs, you will see also the buttons in which we will show in the next points how they were created and how useful can be for the user. 


![alt text](FinalProduct/img/MainMenu.png)


2.  **GRAPHS TESTING**__

Next part, that we will mention is about the graphs. Our aim was to create 4 graphs, 2 graphs had to represent deaths and cases of Covid-19 in cumulative, and the other 2 were cases and deaths but in daily. 


![alt text](FinalProduct/img/WeeklyGraphs.png)
![alt text](FinalProduct/img/DailyGraphs.png)

3. **Deaths Full Report & Cases Full Report buttons TESTING**__

Since we implemented the graphs, we had to implement the CSV files of the goverment so our graphs could be realistic. In order to do that we created the DataStore, Data and Config classes. Those 3 classes were used in order to choose which variables we wanted to use from the CSV files and also in order to make it more easy to us to call our functions to the graphs. But, what we want to show is that we created 2 more buttons  in which they show in a bigger screen how the pandemic is spreading in the UK. Lastly, when the user open the graphs it will see below some useful information in which we are showing in to the user the peaks and minimum numbers of cases or deaths in these period of time. In order to see that the user has to click in "Deaths Full Report" OR "Cases Full Report" buttons. Something very important that we want to mention is that when the user open the full graphs data it will see another 3 new buttons. The first one is the "SHOW SELECTION", that button is used when the user chooses above from this button if is wishing to see the data daily or weekly after that click what it wants the user has to click the "SHOW SELECTION" button to see the difference. Also, we provide to the user to save the graph as a .PNG on it's laptop in case the user wants to have it for example analyzing. To do that the user needs to click the "SAVE PAGE AS .PNG" button.

![alt text](FinalProduct/img/CasesFullGraph.png)
![alt text](FinalProduct/img/FullDeathsGraph.png)

In this picture below as you can see we are showing an example to be more understood to the user. After that the user click's in one of the 2 buttons to see the full graph data(cases OR deaths), there is an icon above from the "SHOW SELECTION" button to choose the data that wants, for example weekly data or daily data, since in the picture below the user chooses the daily data the only thing that has to do is to click the "SHOW SELECTION" button to see the data. Now the next step is to click the "SAVE PAGE AS .PNG" if we condider the user wants to save it for personal use.

![alt text](FinalProduct/img/Filters_INFULLGRAPH.png)



4. **Predict Deaths & Predict Cases buttons TESTING**__

After that our team implemented the graphs we thinked that now was the time to start making some predictions, since the aim was to help NHS to predict the future cases and deaths our team started creating the model. Our implementation can be found in the LinearRegression class in which we creeated some calculation in order to find the next numbers that will follow. In the next screenshot you will see the forecast line in which it will be in red color and also you will notice that there is a green line in which is our model and we are representing how the calculation is made. To check that the user had to click on the "Predict Deaths" OR "Predict Cases" buttons in order to see our model.

![alt text](FinalProduct/img/CasesPrediction.png)
![alt text](FinalProduct/img/DeathsPrediction.png)

5. **Select A File & Preview Data buttons TESTING**__

Something that our team that we haven't include it in the main menu because we wanted to emphasize on that and show how proud our team is, we created a 2 buttons the first one is called "Select A file" that button gives you the option to add a CSV file in order to see the data in graphs, to see them you only have to click in the next button in which it's called "Preview Data" and all the statistics of Covid-19 will be appeared.

![alt text](FinalProduct/img/PreviewSteps.png)
![alt text](FinalProduct/img/InputtedGraph.png)


6. **Terminal main menu TESTING**__

Lastly, we created a feature for the IT staff of NHS in which if their staff runs our program and check in the terminal a menu will be appeared to ask the user type 1 to see the cases data or type 2 to see the deaths data. The reason that is much more useful that option is because it gives even more detailed data to the users to see the dates of the new cases or deaths more clearly. These feature is created in the Main class in which we are calling the variables that we want and the output will be the data that we called and the result is what we want.


![alt text](FinalProduct/img/Option1Terminal.png)
![alt text](FinalProduct/img/Option2Terminal.png)


