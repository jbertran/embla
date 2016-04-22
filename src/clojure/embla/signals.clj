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
  "push the value to keyboard-input. Little functions to be used in java."
  [key-press state]
  (go (>! keyboard-input (list key-press state))))



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
