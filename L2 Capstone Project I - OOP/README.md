This project is used to demonstrate an understanding of object-oriented programming to create a solution for a real-world problem.

**Context**

The task is to create a project management system for a small structural engineering firm called “Poised”. Poised wants a Java program that they can use to keep track of the many projects on which they work. Poised stores the following information for each project that they work on: 

* Project number. 

* Project name. 

* The type of building is being designed. E.g., House, apartment block or store, etc.

* The physical address for the project. 

* ERF number. 

* The total fee being charged for the project. 

* The total amount paid to date. 

* Deadline for the project. 

* The name, telephone number, email address and physical address of the architect for the project. 

* The name, telephone number, email address and physical address of the contractor for the project. 

* The name, telephone number, email address and physical address of the customer for the project. 

Poised wants the program to do the following: 

* Capture information about new projects. If a project name is not provided when the information is captured, the name of the project will use the name of the customer and the building type.

* Update information about existing projects. Information may need to be adjusted at different stages throughout the lifecycle of a project. 

* Finalise existing projects. When a project is finalised: 

    * An invoice is generated for the client. This invoice contains the customer’s contact details and the total amount that the customer must still pay. If the customer has already paid the full fee, an invoice will not be generated. 

    * The project is marked as “finalised” and the completion date should be added. All the information about the project is be added to a text file called CompletedProject.txt.

* See a list of projects that still need to be completed. 

* See a list of projects that are past the due date. 

* Find and select a project by entering either the project number or project name.
