(ns embla.gom
  (:import [embla.model Circle Model Rectangle Shape]))

;; Allow to dynamically add children.
(defmacro add-children
  ([this]
   (binding [*out* *err*]
     (println "No children ? Just pass.")))
  ([this & children]
   `(do ~@(macroexpand `(add-children-list ~this ~@children)))))

(defmacro add-children-list 
  [this & children]
  (map (fn [child] (list '.addChild this child)) children))

;; Macros for GOM definition.
(defmacro defgom
  "Creates the GOM. Full or Empty."
  ([name]
   `(Model. -1 -1 nil "GOMRoot"))
  ([name & body]
   (let [sym (gensym)]
     `(let [~sym (Model. -1 -1 nil "GOMRoot")]
        ~@(macroexpand `(add-children-list ~sym ~@body))
        ~sym))))

(defmacro defrect
  "Creates a rectangle. Full or Empty."
  ([variables]
   `(Rectangle. ~(variables :x) 
                ~(variables :y) 
                ~(variables :length)
                ~(variables :height)
                ~(variables :id)))
  ([variables & body]
   (let [sym (gensym)]
     `(let [~sym (Rectangle. ~(variables :x) 
                             ~(variables :y) 
                             ~(variables :length)
                             ~(variables :height)
                             ~(variables :id))]
        ~@(macroexpand `(add-children-list ~sym ~@body))
        ~sym))))

(defmacro defcircle
  "Creates a circle. Full or Empty."
  ([variables]
   `(Circle. ~(variables :x)
             ~(variables :y)
             ~(variables :radius)
             ~(variables :id)))
  ([variables & body]
   (let [sym (gensym)]
     `(let [~sym (Circle. ~(variables :x)
                          ~(variables :y)
                          ~(variables :radius)
                          ~(variables :id))]
        ~@(macroexpand `(add-children-list ~sym ~@body))
        ~sym))))

(defmacro defshape
  "Creates a shape. Full or Empty."
  ([variables]
   `(Shape. ~(variables :x)
            ~(variables :y)
            ~(variables :id)))
  ([variables & body]
   (let [sym (gensym)]
     `(let [~sym (Shape. ~(variables :x)
                         ~(variables :y)
                         ~(variables :id))]
        ~@(macroexpand `(add-children-list ~sym ~@body))
        ~sym))))

;; Debug purposes.
(println (.toString (defgom "Root"
                      (defgom "test")
                      (rectangle {:x 50
                                  :y 50
                                  :length 200
                                  :height 200
                                  :id "Rectangle"}
                                 (rectangle {:x 50
                                             :y 50
                                             :length 200
                                             :height 200
                                             :id "Rectangle"}))
                      (rectangle {:x 50
                                  :y 50
                                  :length 200
                                  :height 200
                                  :id "Rectangle"})) 0))

(let [gom (defgom "Root")]
  (add-children gom
                (rectangle {:x 50
                            :y 50
                            :length 200
                            :height 200
                            :id "Rectangle"}
                           (rectangle {:x 50
                                       :y 50
                                       :length 200
                                       :height 200
                                       :id "Rectangle"}))
                (rectangle {:x 50
                            :y 50
                            :length 200
                            :height 200
                            :id "Rectangle"}
                           (rectangle {:x 50
                                       :y 50
                                       :length 200
                                       :height 200
                                       :id "Rectangle"})))
  (println (.toString gom 0)))   
