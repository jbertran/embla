(ns clj.embla.core
  (:import jv.embla.engine.RenderEngine)
  (:require [clj.embla.gom :as gom])
  (:gen-class))

(def render-engine (RenderEngine. 400 400 "Embla"))

(defn embla-run
  "Launch Embla. REPL live coding optional"
  [& args]
  (.start render-engine))

(defn clear-all
  "Clear the model and the view"
  []
  (.clear render-engine)
)

(defn show-world
  "Show the current model"
  []
  (println (.toString (.getWorld render-engine))))

(defn world
  "Get the world model value"
  []
  (.getWorld render-engine))

(defn show-node
  "Show the given node"
  [node]
  (println (.toString node)))

(defn node-by-id
  "Get the render engine's current model's node by element"
  [id]
  (let [opt (.getElementById (.getWorld render-engine) id)]
    (if (.isPresent opt)
      (.get opt)
      nil)))

(defn get-child
  "Get the pos'th child of the node, or the first if no pos specified"
  ([node]
    (.get (.children node) 0))
  ([node & pos]
    (.get (.children node) pos)))
