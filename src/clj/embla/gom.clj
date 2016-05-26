(ns clj.embla.gom
  (:import [jv.embla.model Circle Model Rectangle Shape]))

(defmacro add-children
  "Allow to dynamically add children. Use it to add children to an existent node."
  ([this]
   (binding [*out* *err*]
     (println "No children ? Just pass.")))
  ([this & children]
   `(do ~@(macroexpand `(add-children-list ~this ~@children)))))

(defmacro add-children-list
  "Expand the children to add every of them to this. Uses only in further macros."
  [this & children]
  (map (fn [child] (list '.addChild this child)) children))

(defmacro defgom
  "Creates the GOM. Full or Empty."
  ([name]
   `(Model. -1 -1 nil "root"))
  ([name & body]
   (let [sym (gensym)]
     `(let [~sym (Model. -1 -1 nil "root")]
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
