(ns clojure.tests.gl-test
    (:import java.embla.view.glUtils.GameEngine))

(defn gltest []
      (let [ge] (GameEngine. 300 300)
           (. ge run)))