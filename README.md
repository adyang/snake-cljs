# Snake in ClojureScript

Snake game written in ClojureScript as part of the exercise in chapter 6 of
the book [Seven Concurrency Models in Seven Weeks][pb7con].

[pb7con]: https://pragprog.com/book/pb7con/seven-concurrency-models-in-seven-weeks

## Overview

Simple Snake game that is playable on the browser. Use the arrow keys to move the snake.
The snake will teleport to the opposite wall when it reaches one of the walls. The game
ends when the head of the snake touches itself.

A large part of the logic is written using pure functions which makes it easy to test.

## Setup

To get an interactive development environment run:

    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/) to play the game.

Or to create a production build run:

    lein do clean, cljsbuild once min

And open your browser in `resources/public/index.html` to play the game.

To run all the tests, first install [PhantomJS][phantomjs]. For macOS:

    brew install phantomjs
    
Then run:

    lein doo phantom test once

Or to have the test run automatically after every file change:

    lein doo phantom test

[phantomjs]: http://phantomjs.org/
