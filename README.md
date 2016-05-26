# Embla

A simple rendering engine based on FRP.

## Contents

* Definitions for signals using clojure.core.async, not yet fully
  functional
* Definitions for openGL callbacks instances generated on the fly and
  properly released
* A clojure version of the LWJGL example

## Dependencies

### General

Required:

* boot.clj - please install [boot.clj](https://github.com/boot-clj/boot) and add
  it to your system PATH in order to be able to follow the usage directions
  below.

Obtained by boot.clj:

* LWJGL
* LWJGL native libraries -- beware, we download libraries for Linux,
  OSX and Windows for lack of a way to check what your OS is.
* PNGDecoder

## Usage

### Starting an Embla REPL

1. `boot repl` : launch a standard clojure REPL
2. `(boot (build))` : build the project
3. `(boot (run))` : start the Embla engine

### Interacting with Embla

We provide macros that allow you to interact with Embla. These can be nested as
much as desired, to build complex models in a single REPL submission.

##### defgom

`(defgom name)` creates a GOM with root node ID `name`. Error if `name`
is not a String.

##### defrect

```clojure
(defrect {
  :x x
  :y y
  :length l
  :height h
  :id ID
  })
```

Creates a "Rectangle" node you can add to the GOM, with top left corner (x, y),
height and length respectively h and l, and node identifier ID. All arguments
are integers, except ID, which is a String.

##### defcircle

```clojure
(defcircle {
  :x x
  :y y
  :radius r
  :id ID
  })
```

Creates a "Circle" node you can add to the GOM, with center (x, y), radius r,
and node identifier ID. All arguments are integers, except ID, which is a
String.

## License

Distributed under the MIT License.
