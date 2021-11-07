# Required Software

### Dependencies/Tools
* Node v8+ for npm
* Java 8+
* Visual Studio Code

### Optional IDE for Java
* Eclipse - Oxygen+ - (Embedded Maven From Eclipse)
* IntelliJ 

# Running the Frontend

### Configure
Go to /frontend/todo-app/package.json and check under "Scripts"
FOR MAC        "start": "PORT=4200 react-scripts start",
FOR WINDOWS    "start": "set PORT=4200 && react-scripts start",

### Run
Navigate back to /frontend/todo-app then run:
`npm install`
`npm start`

It will automatically launch lo
*localhost:4200*

# Running the Backend

### Configure
Inside 
```
CG1P/backend/todo/src/main/java/com/app/todo/scraper/ScraperConfig
```

There is this line
```
System.setProperty("webdriver.chrome.driver", "backend/todo/src/main/java/com/app/todo/scraper/chromedriver.exe");
```

For Windows: Leave it as ```'chromedriver.exe'```<br>
For Mac: Delete the ```'.exe'``` it should just be ```'chromedriver'```

If using IntelliJ -> remove the `backend/todo/` at the start.

### Run
```
restful-web-services/src/com/myapp/rest/webservices/restfulwebservices/RestfulWebServicesApplication.java
```
In VSCode / IntelliJ, just "Run Application / Run Java"

# Database
After running SPRING
Go to localhost:8080/h2-console

![image](https://user-images.githubusercontent.com/69102738/130642725-31a43a69-2dc9-4a07-b2f9-77ba636b25ff.png)

Ensure JDBC config is same as above and URL is
`jdbc:h2:mem:testdb`
