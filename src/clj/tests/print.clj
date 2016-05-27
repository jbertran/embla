(ns clj.tests.print
  (:require [clj.embla.signals :as sigs]
            [clojure.core.async :as async]))
(async/go-loop []
  (let [message (async/<! sigs/keyboard-input)]
    (println message)
    (recur)))

