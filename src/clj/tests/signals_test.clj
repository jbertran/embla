(ns clj.tests.signals-test
  (:require [clj.embla.signals :as sigs]))

(create-signal enemy)

(macroexpand '(defsigf enemy
                (if (msg < 3)
                  (println "Gloups")
                  (println "Pas Gloups"))))

(defsigf enemy
  (if (< msg 3)
    (println "Gloups")
    (println "Pas Gloups")))

