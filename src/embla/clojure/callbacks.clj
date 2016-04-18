(ns embla.callbacks
  (:import org.lwjgl.glfw.GLFW)
  (:gen-class))

(def callback-vector (atom ()))

(defn callback-unset 
  "If user kept a reference to a callback, he can unset it here"
  [callback]
  (swap! @callback-vector (fn [l] (filter (fn [c] (not= c callback)) l)))
  (.release callback))

(defn callback-register 
  "Register Java callbacks to release them on exit"
  [window callback]
  (GLFW/glfwSetKeyCallback window callback)
  (swap! callback-vector (fn [l] (cons callback l))))

(defn keycallback-make
  "Create a Java GLFWKeyCallback with passed key treatment function"
  [window function]
  (let [callback 
        (proxy [org.lwjgl.glfw.GLFWKeyCallback] []
          (invoke [window key scancode action mods]
            (function window key scancode action mods)))]
    (callback-register window callback)
    callback))
