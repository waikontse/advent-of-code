# advent-of-code

This is my take on the advent-of-code 2020.
My learning purpose was to learn kotlin, and what better way than to write algorithms to solve these little puzzles.
Another learning purpose was to write kotlin in a functional style, which offers benefits compared to the imperative style.
If I had to name a benefit which I strongly believe in, it would be the improved readability by using function composition.
Also no IDE was used, as I believe an IDE would hamper the learning process.
An editor was used with a terminal.


## folder structure

Each day would lie in its own folder. E.g day 1 would be in folder name 'day-1'.

## Running and compiling

Go to the corresponding day folder before compiling and running.

### Compiling

``` shell
kotlinc ../util/util.kt main.kt -include-runtime -d main.jar
```

### Running

After you have successfully compiled the code with the command above, a 'main.jar' file should have been created.

``` shell
java -jar main.jar
```

## Test cases

There are no test cases provided, not that I don't believe in unit tests, but because it wasn't a learning purpose for me.
