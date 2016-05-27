(ns clj.embla.core
  (:import jv.embla.engine.RenderEngine)
  (:require [clj.embla.gom :as gom])
  (:gen-class))

(def render-engine (RenderEngine. 400 400 "Embla"))

(defn embla-run
  "Launch Embla. REPL live coding optional"
  [& args]
  ;; (let [thread (Thread. render-engine)]
    (.start render-engine))

