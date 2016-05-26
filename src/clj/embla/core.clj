(ns clj.embla.core
  (:import jv.embla.engine.RenderEngine)
  (:require [clj.embla.signals :as sigs]
            [clj.embla.gom :as gom])
  (:gen-class))

(def re (RenderEngine. 400 400 "Embla"))

(defn embla-run
  "Launch Embla. REPL live coding optional"
  [& args]
  (let [thread (Thread. re)]
    (.start thread)))

