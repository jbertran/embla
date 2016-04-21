(ns embla.gom
  (:import [embla.model Circle Model Rectangle Shape]))

(defmacro add-children-sub
  [this & children]
  (map (fn [child] (list '.addChild this child))
         children))

(defmacro add-children
  [this & children]
  (let [x (macroexpand `(add-children-sub ~this ~children))]
    (println x)))

(macroexpand '(add-children-sub "test" '@("test" "test2")))

(defmacro defgom
  "Creates the GOM."
  ;; If only name is provided, create an empty GOM.
  ([name]
  `(Model. -1 -1 "GOMRoot"))
  ;; If a body is provided, create a full GOM.
  ([name & body]
  `(let [gom# (Model. -1 -1 "GOMRoot")]
     ~@(add-children gom# body))))

(macroexpand '(defgom "Test"))
(let [gom (defgom "Test")]
  (macroexpand '(add-children-sub gom "test" "test2")))

(map (fn [child] `(.addChild this ~child)) '("test" "test2"))

(defmacro rectangle
  "Creates a rectangle."
  ([x y length height & body]
  `(let [rectangle# (Rectangle. x y length height)]
     ~@(add-children rectangle# body)))
  ([x y length height id-class & body]
  `(let [rectangle# (Rectangle. x y length height id-class)]
     ~@(add-children rectangle# body)))
  ([x y length height id classes & body]
  `(let [rectangle# (Rectangle. x y length height ~(join classes) id)]
     ~@(add-children rectangle# body))))
