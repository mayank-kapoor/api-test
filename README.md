# API-TEST-DEMO

This project provides an example of how to use open source tools like RESTAssured library to write black-box, functional tests for REST APIs in Java.

RESTAssured runs on top of existing Java testing frameworks (JUnit/testNg), and includes a DSL for building API requests and asserting API responses.

In this example project, REST-assured is used to implement a suite of functional tests for a couple of GET REST APIs hosted.


## Installation
 ```
* git clone project : git@github.com:mayank-kapoor/api-test-demo.git

* cd api-test-demo

* mvn clean compile

#RUN TESTS

To compile and run the tests from the command line enter the command:

mvn clean test

#DEBUG TESTS

Import the generated project into your IDE.

Open the project in your IDE and run the tests contained in one of the aforementioned test classes as you would a testNg test.

```

## Reports

```
Html Reports at the end of execution is available in /target folder with the name `extent.html'
with test name, test status, test data and screenshots for failures
```

Sample Report Screenshot

![alt text](./img/example-report/sample-report.png?raw=true "Sample Report")


## CI/CD

```
Can be directely integrated with CI/CD tools like Jenkins, Travis.CircleCI for deployement and 
send notifications on Slack, PagerDuty.

```


 

## Contributing

```

Pull requests and feedbacks are welcome.Please make sure to update tests as appropriate.

```
