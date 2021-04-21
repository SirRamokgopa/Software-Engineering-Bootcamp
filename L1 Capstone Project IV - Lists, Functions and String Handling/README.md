This project is meant to display an understanding of string handling, lists, dictionaries, functions, and working with external data sources. This project focuses on incorporating these subjects to build a task management application.

**Context**

The task was to create a program for an imaginary small business that can help it to manage tasks assigned to each member of the team. 
The program works with four text files, user.txt, tasks.txt, task_overview.txt, and user_overview.txt. 
tasks.txt: Stores a list of all the tasks that the team is working on. Each line contains the following data about a task in this order: 

*	The username of the person to whom the task is assigned.
*	The title of the task. 
*	A description of the task.
*	The date that the task was assigned to the user. 
*	The due date for the task.
*	A ‘Yes’ or ‘No’ value that specifies if the task has been completed yet. 

user.txt: Stores the username and password for each user that has permission to use the programme. The username and password for each user is written to this file in the following format: One username and corresponding password per line, separated by a comma and a space.
task_overview.txt contains: 
*	The total number of tasks that have been generated and tracked using the task manager. 
*	The total number of completed tasks.
*	The total number of uncompleted tasks. 
*	The total number of tasks that haven’t been completed and that are overdue. 
*	The percentage of tasks that are incomplete.
*	 The percentage of tasks that are overdue. 
user_overview.txt contains: 
*	The total number of users registered with the task manager. 
*	For each user: 
    *	The total number of tasks assigned to that user. 
    *	The percentage of the total number of tasks have been assigned to that user. 
    *	What percentage of the tasks assigned to that user have been completed? 
    *	What percentage of the tasks assigned to that user must still be completed? 
    *	What percentage of the tasks assigned to that user have not yet been completed and are overdue? 

The programme allows users to do the following:

*Login*. The user is prompted to enter a username and password. A list of valid usernames and passwords are stored in user.txt. An error message is displayed if the user enters a username that is not listed in user.txt or enters a valid username and an invalid password. The user is repeatedly be asked to enter a valid username and password until they provide appropriate credentials. The main menu is displayed upon successful login. 

*Register user*. If the user chooses ‘r, the user is prompted to enter a new username and password. The user is then asked to confirm the password. Upon successful registration, the new username and password is written to user.txt. Only a user with the username ‘admin’ can register users.

*Add task*. If the user chooses ‘a’, the user is prompted to enter the username of the person the task is assigned to, the title of the task, a description of the task and the due date of the task. The new task is then written to tasks.txt. The date on which the task is assigned will be the current date. The value that indicates whether the task has been completed or not defaults to ‘No’.

*View all tasks*. If the user chooses ‘va’, the information for every task is displayed on the screen.

*View my tasks*. If the user chooses ‘vm’ the information for the tasks that are assigned to her are displayed on the screen. The user can select either a specific task by selecting a corresponding task number or input ‘-1’ to return to the main menu.

*View specific task*. If the user selects a specific task, they can either mark the task as complete or edit the task. When the user chooses to edit a task, the username of the person to whom the task is assigned and/or the due date of the task can be edited. 

The admin user is provided with two additional menu options that allows them to display statistics, ‘s’, and generate reports, ‘g’. 

*Generate reports*. If the user chooses ‘g’, task_overview.txt and user_overview.txt, are generated. 

*Display Statistics*. If ‘s’ is selected, the reports generated from generate reports are read from task_overview.txt and user_overview.txt and displayed on the screen.

======================================================================

Functions in the programme:

**login()** – Logs a user in.

**main_menu()** – Displays the main menu.

**reg_user()** — Allows a user to add a new user.

**add_task()** — Allows user to add a new task. 

**view_all()** — Displays all the tasks listed in tasks.txt. 

**view_mine()** — Displays all the tasks assigned to logged in user. Also allows user to view a specific task. 

**view_task(task)** – Displays a user-selected task. Allows user to edit or mark task as complete.

**display_stats()** – Reads and displays info from task_overview.txt, and user_overview.txt

Helper functions:

**get_users()** – Reads and returns users from users.txt.

**get_tasks()** – Reads and returns tasks from tasks.txt.

**write_task([tasks], method)** – Writes or appends tasks to tasks.txt.

**update_tasks(updated_task)** – Finds updated_task in tasks list, edits the value. Then calls write_task() with method=”w”.

**generate_reports()** – Writes data to task_overview.txt, and user_overview.txt.
