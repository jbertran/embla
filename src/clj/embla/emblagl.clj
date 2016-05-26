(ns clj.embla.emblagl
  (:import org.lwjgl.glfw.GLFW
           org.lwjgl.opengl.GL
           org.lwjgl.opengl.GL11
           org.lwjgl.Version
           )
  (:require [embla.signals :as sig]
            [embla.callbacks :as cback])
  (:gen-class))


(def glfw-win (atom ()))

(defn versions
  "Get LWJGL/GLFW versions and print"
  []
  (println (str "Using GLFW v" (GLFW/GLFW_VERSION_MAJOR) "."
                (GLFW/GLFW_VERSION_MINOR) "." (GLFW/GLFW_VERSION_REVISION)))
  (println (str "Using LWJGL v" (Version/VERSION_MAJOR) "."
                (Version/VERSION_MINOR) "." (Version/VERSION_REVISION))))

(defn escape-to-quit
  [window key scancode action mods]
  (if (and (= key GLFW/GLFW_KEY_ESCAPE) (= action GLFW/GLFW_RELEASE))
    (GLFW/glfwSetWindowShouldClose window GLFW/GLFW_TRUE)))

(defn opengl-init
  "Initialize context etc for openGL display"
  []
  (let [height 300 width 300 init-result (GLFW/glfwInit)]
    (if (=  init-result GLFW/GLFW_TRUE)
      (java.lang.IllegalStateException. "Unable to initialize GLFW"))
    ;; Window behaviour hints
    (GLFW/glfwDefaultWindowHints)
    (GLFW/glfwWindowHint GLFW/GLFW_VISIBLE GLFW/GLFW_FALSE)
    (GLFW/glfwWindowHint GLFW/GLFW_RESIZABLE GLFW/GLFW_TRUE)
    ;;
    ;; Window creation
    (let [window (GLFW/glfwCreateWindow width height "Hello World" 0 0)]
      (if (nil? window)
        (RuntimeException. "Unable to create the GLFW window")
        ;; Assign window value to atom if created
        (swap! glfw-win (fn [x] window)))
      ;; ESC quits the window
      (let [esccallback
            (cback/keycallback-make @glfw-win escape-to-quit)]
        (GLFW/glfwSetKeyCallback @glfw-win esccallback))
      (let [video-mode (GLFW/glfwGetVideoMode (GLFW/glfwGetPrimaryMonitor))]
        (GLFW/glfwSetWindowPos @glfw-win
                               (/ (- (.width video-mode) width) 2)
                               (/ (- (.height video-mode) height) 2)))
      (GLFW/glfwMakeContextCurrent @glfw-win)
      (GLFW/glfwSwapInterval 1)
      (GLFW/glfwShowWindow @glfw-win))))

(defn embla-draw
  []
  (GL11/glBegin GL11/GL_TRIANGLES)
  ;; Draw here
  (GL11/glEnd)
)

"""
(defn embla-triangle
  "Lolilol."
  [colorTriplet positionTuple]
  (let [(r g b) colorTriplet (x y) positionTuple]
    (GL11/glColor3f r g b)
    (GL11/glVertex2f x y)))
"""

(defn opengl-terminate
  "Clean up after the window"
  []
  ;; Release callbacks
  (doseq [callback @cback/callback-vector] (try (.release callback)))
  (println "Terminated")
  (GLFW/glfwTerminate))
