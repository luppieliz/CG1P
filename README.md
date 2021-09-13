## Hi guys just a note<br> 
I followed a course from udemy to get to this point (i believed i sent it to the tele grp)
if not here's the link if you guys wna watch it to understand how i got to this point
[link](https://www.udemy.com/share/101Wxy3@nc9eKxC5TKjPCHvYfq_EDELAEoRGgHDyofSt_cuLW3mEZmsEwebh0z3PPorBmobO6A==/) alternatively if yall dw to buy the course yall can just use my acc to watch!
but I rushed it so i don't understand every single thing
but I believe its enough for what we want to do :D, if not we'll just pick it up along the way;)

## Dependencies/required tools

* Node v8+ for npm
* Visual Studio Code - Latest Version
* Java 8+
* Eclipse - Oxygen+ - (Embedded Maven From Eclipse) (i didn't use eclipse i just use VSC for both spring and react)

## Debugging and course tools
[link to main page where i referenced the project from!](https://github.com/in28minutes/full-stack-with-react-and-spring-boot)

# HOW TO RUN
if you notice there are two main files frontend and restful-web-services 
# frontend 
is run using REACT so when u click on it there will be another README file explaining how to run it.
but basically just run 
### `npm start`

if it doesn't work try (in that order)
 <br> `npm cache clean --force`
 <br>`npm rebuild`
 <br>`npm install`
then run 
`npm start`

it should auto pop up, if it doesn't go to
*localhost:4200*

# restful-web-services 
navigate to 
```
restful-web-services/src/com/myapp/rest/webservices/restfulwebservices/RestfulWebServicesApplication.java
```
### RestfulWebServicesApplication.java
when u get here, right click and *run java*
make sure you're using cmd , (bash doesn't work as confirmed by minh hahah)
if its sucessful you should see a giant SPRING word.

## Check out the db 
After running the SPRING<br>
go to localhost:8080/h2-console
you'll see some login page 

![image](https://user-images.githubusercontent.com/69102738/130642725-31a43a69-2dc9-4a07-b2f9-77ba636b25ff.png)

kinda like that ^ make sure the JDBC URL: is exactly like that or it won't work 
 ### jdbc:h2:mem:testdb
 


