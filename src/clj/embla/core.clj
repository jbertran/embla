(ns clj.embla.core
  (:import jv.embla.engine.RenderEngine)
  (:gen-class))

(defn embla-run
  "Launch Embla. REPL live coding optional"
  [& args]
  (let [re (RenderEngine. 400 400 "Embla")]
    (let [thread (Thread. re)]
      (.start thread))))
