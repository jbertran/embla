# Embla - WIP
A tentative FRP tool for 2D openGL games in Clojure, with the end goal of
supporting live-coding.

## Contents
* Definitions for signals using clojure.core.async, not yet fully
  functional
* Definitions for openGL callbacks instances generated on the fly and
  properly released
* A clojure version of the LWJGL example

## Usage

Embla requires boot.clj to run, in order to download LWJGL's native libraries for Linux.

* `boot repl` 
* `(boot (build))`

* `use <namespace>` To have access to a namespace's files

## Options
None

## Examples
None

### Bugs
Many

## License

Distributed under the MIT License.
