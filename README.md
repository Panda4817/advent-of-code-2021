# Advent of Code 2021 - Java

## Create solution
- Add a class for the day you are completing named 'Day<day_number>'
- Make sure class implements Day interface
- Add puzzle input as a text file in resources directory named '<day_number>.txt'
- Add a private data field of chosen type
- Implement processData, part1 and part2 methods of class
- Add a constructor that runs processData method given an input file name
- Add tests

## Build
- ```mvn clean install```

## Run
- ```java -cp target/classes dev.kmunton.Runner <day_number>```
- day_number corresponds to the particular day in December that you want to run puzzles for

## Test
- ```mvn test``` run all tests
- ```mvn test -Dtest=<test_class_name>``` run specific test class