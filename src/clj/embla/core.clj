(ns clj.embla.core
  (:import jv.embla.view.glUtils.GameEngine)
  (:gen-class))

(defn embla-run
  "Launch Embla. REPL live coding optional"
  [& args]
  (let [ge (GameEngine. 400 400)]
    (let [thread (Thread. ge)]
      (.start thread))))
