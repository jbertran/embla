(ns embla.core
  (:import jv.embla.view.glUtils.GameEngine
           jv.embla.utils.KeyCallbacks)
  (:require [embla.gom     :as gom]
            [embla.signals :as sig]
            [clojure.core.async :as async
             :refer [>! <! >!! <!! go go-loop chan buffer close! thread
                     alts! alts!! timeout]])
  (:gen-class))

(def debug-mode true)

(defn -main
  "Launch Embla. REPL live coding optional"
  [& args]
  (let [ge (GameEngine. 400 400)]
    (.run ge)))

(comment 
  (let [time (sig/timer)]
    (go-loop [times 0]
      (let [bind (<! time)]
        (println "I just waited " times " seconds, and bind = " bind)
        (if (< times 10)
          (recur (inc times)))))
    (if (= debug-mode true)
      (Thread/sleep 1000000)
      (try
        (embla-init)
        (embla-loop)
        (finally
          (embla-terminate))))))
