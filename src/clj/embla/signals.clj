(ns clj.embla.signals
  (:require [clojure.core.async
             :as async
             :refer [>! <! >!! <!! go go-loop chan buffer close! thread
                     alts! alts!! timeout]])
  (:import [java.util.concurrent Semaphore]) 
  (:gen-class))

;; Basic signals for handling.
(def keyboard-input (chan))
(def custom-signals '())
(def mutex (Semaphore. 1))

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
  (if (empty? custom-signals)
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
    `(go-loop []
       (let [~'msg (<! channel)]
         (.acquire mutex)
         ~@code
         (.release mutex)
         (Model/diff old new))
       (recur))))

(defmacro combine
  [name1 name2 sig-name func]
  (if (search-signal (.toString sig-name))
    (throw (Exception. "Trying to create an already existent signal."))
    (let [channel (chan)
          sig1 (chan)
          sig2 (chan)]
      (signal-register name1 sig1)
      (signal-register name2 sig2)
      (go-loop []
        (let [msg1 (<! sig1)
              msg2 (<! sig2)]
          (>! chan (func msg1 msg2)))
        (recur))
      (alter-var-root (var custom-signals) #(cons (list (.toString sig-name) channel (atom '())) %)))))

(defn broadcast-all
  "Transfer the value from the channel to all functions which need it."
  []
  (loop [[sig & signals] custom-signals]
    (let [input  (second sig)
          output (second (rest sig))]
      (go-loop []
        (let [msg (<! input)]
          (map #(go (>! % msg)) output))
        (recur))
      (if-not (empty? signals)
        (recur signals)))))
