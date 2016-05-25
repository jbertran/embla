(ns embla.tests
  (:require [embla.gom :as gom]))

;; Debug purposes.
(println (.toString (gom/defgom "Root"
                      (gom/defgom "test")
                      (gom/defrect {:x 50
                                  :y 50
                                  :length 200
                                  :height 200
                                  :id "Rectangle"}
                                 (gom/defrect {:x 50
                                             :y 50
                                             :length 200
                                             :height 200
                                             :id "Rectangle"}))
                      (gom/defrect {:x 50
                                  :y 50
                                  :length 200
                                  :height 200
                                  :id "Rectangle"})) 0))

(let [gom (gom/defgom "Root")]
  (gom/add-children gom
                (gom/defrect {:x 50
                            :y 50
                            :length 200
                            :height 200
                            :id "Rectangle"}
                           (gom/defrect {:x 50
                                       :y 50
                                       :length 200
                                       :height 200
                                       :id "Rectangle"}))
                (gom/defrect {:x 50
                            :y 50
                            :length 200
                            :height 200
                            :id "Rectangle"}
                           (gom/defrect {:x 50
                                       :y 50
                                       :length 200
                                       :height 200
                                       :id "Rectangle"})))
  (println (.toString gom 0)))   
