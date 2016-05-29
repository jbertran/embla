(ns clj.embla.examples
  (:require [clj.embla.gom :as gom]
            [clj.embla.signals :as sigs]))

(.addChild (.getWorld render-engine)
           (defcircle {:x 100 
                       :y 100
                       :radius 50
                       :color (color 0 0 0)
                       :id "outer"}
             (defcircle {:x 100
                         :y 100
                         :radius 25
                         :color (color 255 255 255)
                         :id "inner"})))

(do
  (defsig  time (timer))
  (defsigf time
    (println "Time ! " msg))
  (defsigf keyboard-input
    (case (fst msg)
      :Z (.relativeMoveTo (node-by-id "inner")  0  1)
      :Q (.relativeMoveTo (node-by-id "inner")  0 -1)
      :Q (.relativeMoveTo (node-by-id "inner") -1  0)
      :Q (.relativeMoveTo (node-by-id "inner")  1  0))))
