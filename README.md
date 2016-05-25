# Embla

A simple rendering engine based on FRP.

## Contents

* Definitions for signals using clojure.core.async, not yet fully
  functional
* Definitions for openGL callbacks instances generated on the fly and
  properly released
* A clojure version of the LWJGL example

## Usage

Embla requires [boot.clj](https://github.com/boot-clj/boot). to run, in order to download LWJGL's native libraries for Linux.

1. Launch a clojure REPL - `boot repl` 
2. Build the project - `(boot (build))`
3. Access the userspace macros - `(use 'embla.gom)`

## License

Distributed under the MIT License.
