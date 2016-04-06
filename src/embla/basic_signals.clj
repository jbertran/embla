(ns embla.basic-signals
  (:require [clojure.core.async
             :as async
             :refer [>! <! >!! <!! go chan buffer close! thread
                     alts! alts!! timeout]]
            [embla.callbacks :as cbacks])
  (:gen-class))

(def timer (go-loop [seconds 1]
  (<! (timeout 1000))
  (print "waited" seconds "seconds")
  (recur (inc seconds))))
