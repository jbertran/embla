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

You may also create a file regrouping macro calls and call (load-file
  <your_file>) within the REPL to achieve the same effect.

See the file `src/clj/embla/examples.clj` for a quick and dirty example of model and signal interaction.

#### Defining model objects

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

```clojure
(add-children target children)
```

Adds nodes in children (may be a list or a single element) to target.

#### Defining signals

We provide a timer signal emitting an integer representing time, as well as a
keyboard-input signal emitting the string corresponding to the key pressed.

```clojure
(defsig name channel)       ;; creates an embla signal.
(defsigf name code)         ;; registers a code snippet to the named signal
(combine name1 name2 code)  ;; combine signals name1 and name2, manage input
                            ;; signals with snippet code

```

## Wishlist

* Sprites - not unavailable for lack of finding a way to display them in a way
that reflects the rest of the shapes

## License

Distributed under the MIT License.
