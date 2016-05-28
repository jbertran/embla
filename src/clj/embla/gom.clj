(ns clj.embla.gom
  (:import [jv.embla.model Circle Model Rectangle Shape]
           [java.awt Color]))

(defn color
  "Define a Color class."
  [x y z]
  (Color. x y z))

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
  ([]
   `(Model. -1 -1 nil "root"))
  ([& body]
   (let [sym (gensym)]
     `(let [~sym (Model. -1 -1 nil "root")]
        ~@(macroexpand `(add-children-list ~sym ~@body))
        ~sym))))

(defmacro defrect
  "Creates a rectangle. Full or Empty."
  ([variables]
   `(let [model# (Rectangle. ~(variables :x)
                             ~(variables :y)
                             ~(variables :width)
                             ~(variables :height)
                             ~(variables :color)
                             ~(variables :id))]
      (.addGLShape clj.embla.core/render-engine model#)
      model#))
  ([variables & body]
   (let [sym (gensym)]
     `(let [~sym (Rectangle. ~(variables :x)
                             ~(variables :y)
                             ~(variables :width)
                             ~(variables :height)
                             ~(variables :color)
                             ~(variables :id))]
        ~@(macroexpand `(add-children-list ~sym ~@body))
        (.addGLShape clj.embla.core/render-engine ~sym)
        ~sym))))

(defmacro defcircle
  "Creates a circle. Full or Empty."
  ([variables]
   `(let [model# (Circle. ~(variables :x)
                          ~(variables :y)
                          ~(variables :radius)
                          ~(variables :color)
                          ~(variables :id))]
      (.addGLShape clj.embla.core/render-engine model#)
      model#))
  ([variables & body]
   (let [sym (gensym)]
     `(let [~sym (Circle. ~(variables :x)
                          ~(variables :y)
                          ~(variables :radius)
                          ~(variables :color)
                          ~(variables :id))]
        ~@(macroexpand `(add-children-list ~sym ~@body))
        (.addGLShape clj.embla.core/render-engine ~sym)
        ~sym))))

(defmacro defshape
  "Creates a shape. Full or Empty."
  ([variables]
   `(let [model# (Shape. ~(variables :x)
                         ~(variables :y)
                         ~(variables :points)
                         ~(variables :id))]
      (.addGLShape clj.embla.core/render-engine model#)
      model#))
  ([variables & body]
   (let [sym (gensym)]
     `(let [~sym (Shape. ~(variables :x)
                         ~(variables :y)
                         ~(variables :points)
                         ~(variables :id))]
        ~@(macroexpand `(add-children-list ~sym ~@body))
        (.addGLShape clj.embla.core/render-engine ~sym)
        ~sym))))

(defmacro deftriangle
  "Creates a triangle."
  ([variables]
   `(let [model# (Triangle. ~(variables :a)
                            ~(variables :b)
                            ~(variables :c)
                            ~(variables :color)
                            ~(variables :id))]
      (.addGLShape clj.embla.core/render-engine model#)
      model#))
  ([variables & body]
   (let [sym (gensym)]
     `(let [~sym (Triangle. ~(variables :a)
                            ~(variables :b)
                            ~(variables :c)
                            ~(variables :color)
                            ~(variables :id))]
        ~@(macroexpand `(add-children-list ~sym ~@body))
        (.addGLShape clj.embla.core/render-engine ~sym)
        ~sym))))

(defmacro defsprite
  "Creates a circle. Full or Empty."
  ([variables]
   `(let [model# (Sprite. ~(variables :x)
                          ~(variables :y)
                          ~(variables :width)
                          ~(variables :height)
                          ~(variables :path)
                          ~(variables :id))]
      (.addGLShape clj.embla.core/render-engine model#)
      model#))
  ([variables & body]
   (let [sym (gensym)]
     `(let [~sym (Sprite. ~(variables :x)
                          ~(variables :y)
                          ~(variables :width)
                          ~(variables :height)
                          ~(variables :path)
                          ~(variables :id))]
        ~@(macroexpand `(add-children-list ~sym ~@body))
        (.addGLShape clj.embla.core/render-engine ~sym)
        ~sym))))
