(ns embla.signals
  (:require [clojure.core.async
             :as async
             :refer [>! <! >!! <!! go go-loop chan buffer close! thread
                     alts! alts!! timeout]]
            ;; [embla.callbacks :as cbacks])
            )
  (:gen-class))

;; Basic signals for handling.
(def keyboard-input (chan))
(def custom-signals '())

(defn timer
  "Gives a timer, starting at 0. Count every second."
  []
  (let [time (chan)]
    (go-loop [times 0]
      (<! (timeout 1000))
      (>! time times)
      (recur (inc times)))
    time))

(defn update-chan 
  "Push the value to keyboard-input. Little functions to be used in java."
  [key-press state]
  (go (>! keyboard-input (list key-press state))))

(defn search-signal
  "Search for the named signal into the existing signals. false if signal does not exist."
  [name]
  (If (empty? custom-signals)
    false
    (loop [[sig & signals] custom-signals]
      (let [sig-name (first sig)]
        (if (not= (.toString name) sig-name)
          (if-not (empty? signals)
            (recur signals)
            false)
          sig)))))

(defmacro create-signal
  "Creates a proper signal for embla."
  [name]
  (let [custom (chan)]
    `(if (search-signal ~(.toString name))
       (throw (Exception. "Trying to create an already existent signal."))
       (alter-var-root (var custom-signals) #(cons (list (.toString name) custom (atom '())) %)))))

(defn signal-register
  "Register the channel fun-sig to the signal named name."
  [name fun-sig]
  (let [signal (search-signal (.toString name))]
    (if-not signal
      (throw (Exception. "Trying to register to inexistent signal."))
      (let [fun-signals (second (rest signal))]
        (swap! fun-signals #(cons fun-sig %))))))

(defmacro defsigf
  "Define a function registered to the signal."
  [name & code]
  (let [channel (chan)]
    (signal-register name channel)
    `(let [~'msg 3]
       ;;(go-loop []
       ;;('p 'gom-sem)
       ~@code
       ;;('v 'gom-sem)
       ;;('GOM/diff)
       ;;)
       )))

(println custom-signals) 
(macroexpand '(create-signal move))
(search-signal "enemy")
(macroexpand '(defsigf enemy
                (if (msg < 3)
                  (println "Gloups")
                  (println "Pas Gloups"))))

(defsigf enemy
  (if (< msg 3)
    (println "Gloups")
    (println "Pas Gloups")))

(defn defn-sig
  "Func have to take one channel in parameter."
  [name func]
  (let [channel (chan)]
    (func channel)
    (signal-register name channel)))

(defn broadcast-all
  "Transfer the value from the channel to all functions which need it."
  []
  (loop [[sig & signals] custom-signals]
    (let [input  (second sig)
          output (second (rest sig))]
      (go-loop []
        (let [msg (<! input)]
          (map #(go (>! % msg)) output)))
      (if-not (empty? signals)
        (recur signals)))))

;;; Historical purposes
(comment
  (defn combine1
    "('a -> 'b) -> signal 'a -> signal 'b"
    [func signal]
    (go-loop []
      (let [msg (<! signal)]
        (<! (func msg)))
      (recur)))

  (defn combine2
    "('a -> 'b -> 'c) -> signal 'a -> signal 'b -> signal 'c"
    [func sig1 sig2]
    (go-loop []
      (let [fst (<! sig1)
            sec (<! sig2)]
        (<! (func fst sec)))
      (recur)))

  (defn combine
    "Generates an async channel listening for signals
  and applying func to their outputs"
    [func & signals]
    (def signal-count (atom 0))
    (let [signal-out (chan)
          siglist-size (count signals)
          ;; [atom signal] vector, pass atom to go for each signal
          siglist (map (fn [s] (vector (atom nil) s)) signals)]
      ;; Define a waiting function for each sig in the argument list
      ;; => (count signals) waiting processes
      (loop [loop-list siglist]
        (if (empty? loop-list)
          signal-out)
        (let [[sig & sigs-recur] loop-list]
          (go
            (let [msg (<! (second sig))
                  storage (first sig)]
              ;; if msg's first arrival, inc signal-count
              (if (nil? storage)
                (swap! signal-count inc))
              ;; Put signal in its place either way
              (swap! storage (fn [x] msg))
              ;; If the signal has sufficient arguments, send it.
              (if (= @signal-count siglist-size)
                (>! signal-out (apply func (map siglist (fn [x] (@(first x)))))))))
          (recur sigs-recur)))))
  )
