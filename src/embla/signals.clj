(ns embla.signals
  (:require [clojure.core.async
             :as async
             :refer [>! <! >!! <!! go chan buffer close! thread
                     alts! alts!! timeout]]
            [embla.callbacks :as cbacks])
  (:gen-class))

(def signal-vector (atom ()))
(def sig-kb-output (chan))
(def sig-time (chan))

;; Ça sert à quoi ça ?
(defn kb-listener-action
  [window key scancode action mods]
  (>! sig-kb-output (vector key action)))

(defn signal-register
  "Add a signal to the list of channels to close
  when terminating"
  [signal]
  (swap! signal-vector conj signal))

(defn signals-terminate
  "Close every signal."
  []
  (doseq [signal @signal-vector] (try (close! signal))))

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

(defn default-signals-init
  []
  ;; TODO: actual callback-to-signal management (?) 65-90
  (combine
   (fn [key action]
     (case key
       GLFW/GLFW_KEY_A (print "Key: A ")
       GLFW/GLFW_KEY_Z (print "Key: Z "))
     (case action
       GLFW/GLFW_RELEASE (println "Action: release")
       GLFW/GLFW_PRESS (println "Action: press")))
   sig-kb-output))
