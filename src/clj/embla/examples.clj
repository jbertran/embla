(ns clj.embla.examples
  (:require [clj.embla.gom :as gom
             :refer [defcircle color]]
            [clj.embla.signals :as sigs 
             :refer [defsig defsigf timer broadcast-all]]
            [clj.embla.core :as core]))

(core/add-child (core/get-world)
              (defcircle {:x 200
                          :y 200
                          :radius 100
                          :color (color 0 0 0)
                          :id "outer"}
                (defcircle {:x 200
                            :y 200
                            :radius 25
                            :color (color 255 255 255)
                            :id "inner"})))

(do
  (defsig  time (timer))
  (defsigf time
    (println "Time ! " msg))
  (defsigf keyboard-input
    (let [value (first msg)]
      (case value
        :Z (.relativeMoveTo (core/node-by-id "inner")  0  1)
        :S (.relativeMoveTo (core/node-by-id "inner")  0 -1)
        :Q (.relativeMoveTo (core/node-by-id "inner") -1  0)
        :D (.relativeMoveTo (core/node-by-id "inner")  1  0))))
  (broadcast-all))
