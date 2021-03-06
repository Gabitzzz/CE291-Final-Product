**Introduction**

Our task was to design a machine learning model in which will give an overview of cases and deaths from Covid-19. Also, we had to implement a model in which it will predict the future numbers of deaths and cases  of Covid-19. The results were successful because we did what the task was asking and we implement more things for example daily graphs, and we implemented a more detail data, but this is used in the terminal.  


**Testing:**

1. In the picture below you will see our main menu program. The design in our program will be find in the GenerateGraph and MainFrame classes, there is our styling and how we designed the graphs, you will see also the buttons in which we will show in the next points how they were created and how useful can be for the user. 

_INCLUDE SCREENSHOT WITH THE MAIN MENU_

2. Next part, that we will mention is about the graphs. Our aim was to create 4 graphs, 2 graphs had to represent deaths and cases of Covid-19 in cumulative, and the other 2 were cases and deaths but in daily. 


_INCLUDE A SCREENSHOT WITH DAILY AND CUMULATIVE GRAPHS_


3. Since we implemented the graphs, we had to implement the CSV files of the goverment so our graphs could be realistic. In order to do that we created the DataStore, Data and Config classes. Those 3 classes were used in order to choose which variables we wanted to use from the CSV files and also in order to make it more easy to us to call our functions to the graphs. But, what we want to show is that we created 2 more buttons  in which they show in a bigger screen how the pandemic is spreading in the UK. Lastly, when the user open the graphs it will see below some useful information in which we are showing in to the user the peaks and minimum numbers of cases or deaths in these period of time. In order to see that the user has to click in "Deaths Full Report" OR "Cases Full Report" buttons. 

_INCLUDE A SCREENSHOT OF DEATHS FULL REPORT BUTTON AND CASES FULL REPORT BUTTON_

4. After that our team implemented the graphs we thinked that now was the time to start making some predictions, since the aim was to help NHS to predict the future cases and deaths our team started creating the model. Our implementation can be found in the LinearRegression class in which we creeated some calculation in order to find the next numbers that will follow. In the next screenshot you will see the forecast line in which it will be in red color and also you will notice that there is a green line in which is our model and we are representing how the calculation is made. To check that the user had to click on the "Predict Deaths" OR "Predict Cases" buttons in order to see our model.

_INCLUDE A SCREENSHOT WITH THE GRAPHS THAT SHOW THE PREDICTIONS_


5.


