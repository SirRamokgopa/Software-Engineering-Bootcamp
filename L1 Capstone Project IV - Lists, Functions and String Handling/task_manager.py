# Import datetime library to get dates for tasks
import datetime
# Import Counter from collections for user report
import collections 

#===============================================================================
# Helper functions for programme.
#===============================================================================
def get_users():
    """ Gets all users from users.txt. Returns list of 
        [[username, password], ...]
    """ 
    # Get users from user.txt
    users_file = open("user.txt", "r", encoding="utf-8")
    # Initialze empty list to store users 
    users = []
    # Store users in list -- [[username, password], ...]
    for user in users_file:
        users.append(user.strip().split(", "))

    # Close file
    users_file.close()

    return users

def get_tasks():
    """ Gets all tasks from tasks.txt. Returns list of 
        [
            [task_username, 
            title, 
            description, 
            assigned_date, 
            due_date,
            task_complete], 
        ...]
    """ 
    # Open tasks.txt for reading
    tasks_file = open("tasks.txt", "r", encoding="utf-8")  
        
    # Store tasks in list -- 
    tasks = [line.strip().split(", ") for line in tasks_file]

    # Close tasks file
    tasks_file.close()

    return tasks

def write_task(tasks, method = "a+"):
    """ Write task to tasks.txt. Accepts a list of tasks. Method argument can be set
        to 'a+' for appending (default) or 'w' for overwriting.
    """
    # Open tasks.txt 
    tasks_file = open("tasks.txt", method, encoding="utf-8")  

    for task in tasks:
        task_username, title, description, assigned_date, due_date, task_complete = task
        # Writing the task data to tasks.txt
        tasks_file.write(f"{task_username}, {title}, {description}, {assigned_date}, {due_date}, {task_complete}\n")

    # Close tasks file
    tasks_file.close()

def update_tasks(updated_task):
    """ Accepts a list containing a task and the index of the task. Then 
        updates the task at task_index and writes the task to tasks.txt 
    """
    tasks = get_tasks()
    # Get the index in tasks of where the title and the assignment date of tasks match that of updated task
    task_index = [ind for ind, task in enumerate(tasks) if updated_task[1] == task[1] and updated_task[3] == task[3]]
    # Update the task at task_index
    if task_index[0]:
        tasks[task_index[0]] = updated_task

    write_task(tasks, "w")
    
def generate_reports():
    """ Generates a report about tasks and another about users. Writes the data
        to task_overview.txt and user_overview.txt.
    """
    # Get tasks
    tasks = get_tasks()
    # Get users
    users = get_users()

    # Calculate stats for tasks report
    total_tasks = len(tasks)
    complete_tasks = 0
    incomplete_tasks = 0
    overdue_tasks = 0

    for task in tasks:
        # Count number of completed tasks
        if task[-1] == "Yes":
            complete_tasks += 1
        else: 
            incomplete_tasks += 1
        # Count number of overdue tasks
        if datetime.datetime.strptime(task[4], "%d %b %Y").date() < datetime.date.today() and task[-1] == "No":
            overdue_tasks += 1

    incomplete_tasks_percentage = 100*incomplete_tasks/total_tasks
    overdue_tasks_percentage = 100*overdue_tasks/total_tasks

    # Calculate stats for users report
    total_users = len(users)
    users_tasks_count = {}
    users_tasks_count_percentage = {}
    users_completed_tasks_percentage = {}
    users_incomplete_tasks_percentage = {}
    users_overdue_tasks_percentage = {}
    
    for user in users:
        task_count = 0
        completed_task_count = 0
        overdue_task_count = 0
        for task in tasks:
            if task[0] == user[0]:
                task_count += 1
            if task[0] == user[0] and task[-1] == "Yes":
                completed_task_count += 1
            if task[0] == user[0] and task[-1] == "No" and datetime.datetime.strptime(task[4], "%d %b %Y").date() < datetime.date.today():
                overdue_task_count += 1
            
        users_tasks_count[user[0]] = task_count  
        users_tasks_count_percentage[user[0]] = int(round(100*task_count/total_tasks))
        if task_count:
            users_completed_tasks_percentage[user[0]] = int(round(100*completed_task_count/task_count))
            users_incomplete_tasks_percentage[user[0]] = 100 - users_completed_tasks_percentage[user[0]]
            users_overdue_tasks_percentage[user[0]] = int(round(100*overdue_task_count/task_count))
        else:
            users_completed_tasks_percentage[user[0]] = "No tasks assigned"
            users_incomplete_tasks_percentage[user[0]] = "No tasks assigned"
            users_overdue_tasks_percentage[user[0]] = "No tasks assigned"
  
    # Write stats to tasks_overview.txt and users_overview.txt
    tasks_output = f"""
    Total number of tasks:                   {total_tasks}
    Number of completed tasks:               {complete_tasks}
    Number of incomplete tasks:              {incomplete_tasks}
    Number of overdue tasks:                 {overdue_tasks}
    Percentage of tasks that are incomplete: {int(round(incomplete_tasks_percentage))}%
    Percentage of tasks that are overdue:    {int(round(overdue_tasks_percentage))}%
    """
    users_output = []
    for user in users:
        user_output = f"""
    {user[0]}
    Total number of tasks assigned:         {users_tasks_count[user[0]]}
    Percentage of the total tasks assigned: {users_tasks_count_percentage[user[0]]}
    Percentage of the tasks completed:      {users_completed_tasks_percentage[user[0]]}
    Percentage of the tasks incomplete:     {users_incomplete_tasks_percentage[user[0]]}
    Percentage of the tasks overdue:        {users_overdue_tasks_percentage[user[0]]}
    """
        users_output.append(user_output)
        
    # Open tasks_overview.txt and users_overview.txt
    tasks_overview_file = open("tasks_overview.txt", "w", encoding="utf-8")  
    users_overview_file = open("users_overview.txt", "w", encoding="utf-8")  

    # Write to file
    tasks_overview_file.write(tasks_output)
    for item in users_output:
            users_overview_file.write(item)

    # Close files
    tasks_overview_file.close()
    users_overview_file.close()


#===============================================================================
# Views for programme.
#===============================================================================

def login():
    """ Loga in a user with pass word and username. Returns strings - username. """ 

    users = get_users()

    # Prompt user for login credentials
    username = input("Username: ")
    password = input("Password: ")

    # find if username exists
    user_index = [index1 for index1, user_credentials in enumerate(users) for index2, user_name in enumerate(user_credentials) if user_name==username]
    """ 
        Using list comprehention (https://docs.python.org/3/tutorial/datastructures.html), 
        we can iterate throught the users list.
        And with enumerate function (https://docs.python.org/3/library/functions.html#enumerate), 
        we can get the indicies of the user in the users list.
        The output is a list.
    """

    # Check for correct login correct credentials
    while not user_index or users[user_index[0]][1] != password:
        print("Username does not exist on reccord. Or Password is incorrect.")
        # Prompt user for login credentials again
        username = input("Username: ")
        password = input("Password: ")
        # find if username exists
        user_index = [index1 for index1, user_credentials in enumerate(users) for index2, user_name in enumerate(user_credentials) if user_name==username]

    return username

def main_menu():
    """ Main menu for the app"""

    message = """
    Please select of the following options:
    r  - register user
    a  - add task
    va - view all tasks
    vm - view my tasks
    e  - exit """
    print(message)
    if username == "admin":
        print("    s  - view stats\n    g  - generate reports")

    # Get user option
    users_option = input("\n:: ")
    while users_option not in ["r","a","va","vm","e","s","g"]:
        print(" ====\nSoz, bro. That was not a valid option.\n", message)
        users_option = input(":: ")

    if users_option == "r":
        reg_user()
    elif users_option == "a":
        add_task()
    elif users_option == "va":
        view_all()
    elif users_option == "vm":
        view_mine()
    elif users_option == "s":
        display_stats()
    elif users_option == "g":
        generate_reports()
    else:
        # If the user chooses e 
        pass  # pass statement (https://docs.python.org/3/tutorial/controlflow.html)


def reg_user():
    """ Registers a new user. Only admin users can register a new user. """

    if username == "admin":
        print("\nYou are about to register a new user:")
        new_user = input("Username: ")

        # Check if user exists
        users = [user[0] for user in get_users()]
        if new_user in users:
            print("\nThat user already exists on record.") 
            print("================================")

            # Go back to main menu
            return main_menu()

        else:
            new_password = input("Password: ")
            confirm_password = input("Confirm password: ")
            while confirm_password != new_password:
                print("\nPassword does not match.")
                new_password = input("Re-enter password: ")
                confirm_password = input("Confirm password: ")

            # Open user.txt for appending
            users_file = open("user.txt", "a+", encoding="utf-8")
            users_file.write(f"\n{new_user}, {new_password}")

            # Close user file
            users_file.close()

            # Let user know that it works
            print("\nAll done! User added. :)")
            print("================================")

            # Go back to main menu
            return main_menu()

    else:
        print("\nAccess Denied")
        print("================================")

        # Go back to main menu
        return main_menu()


def add_task():
    """ Adds a new task to tasks.txt """

    print("\nYou are about to add a new task:")
    task_username = input("Username of the person the task is assigned to: ")
    title = input("Task title: ")
    description = input("Task description: ")
    task_complete = "No"
    # Below using the datetime module (https://docs.python.org/3/library/datetime.html) to get the current date and format
    due_date = input("Task due date: ")
    assigned_date = datetime.date.today().strftime("%d %b %Y")

    write_task([[task_username, title, description, assigned_date, due_date, task_complete]])

    # Let user know that it works
    print("\nAll done! Task added. :)")
    print("================================")

    # Go back to main menu
    return main_menu()


def view_all():
    """ Display info for all tasks """

    print("\n========== All tasks ==========")
    for task in get_tasks():
        task_username, title, description, assigned_date, due_date, task_complete = task
        print(f"\nAssigned to: {task_username}")
        print(f"Title:       {title}")
        print(f"Description: {description}")
        print(f"Assigned on: {assigned_date}")
        print(f"Due date:    {due_date}")
        print(f"Completed:   {task_complete}")
    print("================================")

    user_option =(input("Press Enter to go back.")) 
    return main_menu()


def view_mine():
    """ Displays only the user's tasks. """

    # Get user's tasks
    tasks = [task for task in get_tasks() if task[0] == username]

    print("\n========== My tasks ==========")
    if tasks:
        for ind, task in enumerate(tasks):
            task_username, title, description, assigned_date, due_date, task_complete = task
            print(f"\n{ind+1}.")
            print(f"Title:       {title}")
            print(f"Description: {description}")
            print(f"Assigned on: {assigned_date}")
            print(f"Due date:    {due_date}")
            print(f"Completed:   {task_complete}")
        print("==============================")

        # Allow user to select a task or go to main menu
        user_option = int(input("Select task or type '-1' to go back.\n:: ")) 
        if user_option == -1:
            return main_menu()
        else:
            # Go to selected task
            return view_task(tasks[user_option-1])


    else:
        print("\nWoo! You don't have any tasks")
        print("==============================")
        return main_menu()


def view_task(task):
    """ Accepts a list containing a task and then displays 
        the task. Also allows the user to update due date and assigned person for 
        the task.
    """
    # Unpack task variables and display task
    task_username, task_title, task_description, task_assigned_date, task_due_date, task_complete = task
    print(f"\n==============================\n{task_title}\n")
    print(f"Description:  {task_description}")
    print(f"Assigned on:  {task_assigned_date}")
    print(f"Due date:     {task_due_date}")
    print(f"Completed:    {task_complete}")
    print("==============================")

    print("1  - edit task\n2  - mark task as completed\n-1 - go back")
    user_option = int(input(":: ")) 

    if user_option == 1:
        # Ask user to update task
        task_username = input("Assign task to: ")
        task_due_date = input("Due date: ")
        return view_task([
            task_username, 
            task_title, task_description, 
            task_assigned_date, 
            task_due_date, 
            task_complete] )
    elif user_option == 2:
        # Mark task as complete
        task_complete = "Yes"
        return view_task([
            task_username, 
            task_title, task_description, 
            task_assigned_date, 
            task_due_date, 
            task_complete])
    else:
        ### Need to update tasks.txt
        update_tasks(task)
        return view_mine()



def display_stats():
    """ Displays stats from app. """
    generate_reports()

    # Read task_overview.txt and users_overview.txt 
    tasks_overview_file = open("tasks_overview.txt", "r", encoding="utf-8")  
    users_overview_file = open("users_overview.txt", "r", encoding="utf-8") 

    print("\n======== Task Manager stats ========\n")
    for line in tasks_overview_file:
        print(line.strip())
    print("============ User stats ============")
    for line in users_overview_file:
        print(line.strip())
    print("==============================")
    
    user_option =(input("Press Enter to go back.")) 
    return main_menu()
    


#===============================================================================
# Programme starts here.
#===============================================================================

# User must login
username = login()

# Once logged in, present options to user
main_menu()


# notes
################################################################################
# Can be updated to acommodate wehn user inputs an invalid option in view_task()
# and view_mine()
# ATM get_users() returns usernames and passwords. Maybe have an option that 
# only returns usernames