# Workshop_1
CodersLab Workshop 1
# What is it?
This is a console application similar to a to-do list. 
It will allow you to simplify the planning of your day, 
as well as help you remember the important things that 
you need to do.

The application was written using basic knowledge,
maven and a third-party library org.apache.commons.lang3.
To connect the library, you need to add a dependency:
<dependency>
<groupId>org.apache.commons</groupId>
<artifactId>commons-lang3</artifactId>
<version>3.12.0</version>
</dependency>

#What are the features of the application?
With the help of the application, you can add, 
delete one thing, delete the whole to-do list, 
display the to-do list on the screen. 
All cases are written to the file, as well as read from it.

add - add task;
remove - remove one from the to-do list;
delete - delete the whole list;
list - display the entire to-do list;
exit - exit the application.

Additionally, all actions are checked for correct input. 
And there is also an additional class for changing the color of the displayed fragments.