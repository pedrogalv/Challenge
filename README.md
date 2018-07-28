# Java Challenge
Java challenge provided by Gamesys

## Tech stack
- Java 8
- Spring Boot
- Spring Shell
- JSR 354
- Apache Commons
- Lombok
- Maven

# Assumptions
- The solution should have an extensible design. Therefore, It was built on top of spring-boot framework. With this framework, any need for database or web service can be easily incorporated into de current architecture with very little or none refactoring.

- I used Martin Fowler's Clean Architecture concept to ensure that any changes in the way to provide the prices or store the books doesn't impact in the business implementation.

- Since providing a list of books as entry parameter can be error prone ( such as mispelling, multiple spaces, and read difficult) I changed it to an list of books id. The system has a custom commands that state all books id's.

## How to build and run
This project already includes a minimized version of maven. All other dependencies will be downloaded by it.

Execute maven build (It may take a while in the first run):
```sh
$ ./mvnw clean install
```

After the message of BUILD SUCCESS, the application is ready to run:
```sh
$ ./mvnw spring-boot:run
```

The execution of the command above should lead to a interactive terminal.

This terminal have two custom commands, plus others already provided by spring shell.

The commands can be described executing the command:
```
shell:>help
```
The help command can be also used to describe a single command with more detail:
```
shell:>help price
```
# Custom Commands

List - Created to list all books stored, showing it's Id, Title, Year and Price:
```
shell:>list
```
Price - Created to give the total value given a list of books id.
```
shell:> price 2,5,11
```
In the example above, It was requested the price of the books 'The Terrible Privacy of Maxwell Sim', 'Three Men in a Boat' and 'Great Expectations', all already stored.