(ns embla.core
  ;(:import org.lwjgl.glfw.GLFW
  ;         org.lwjgl.opengl.GL
  ;         org.lwjgl.opengl.GL11)
  (:require [embla.gom     :as gom]
            [embla.signals :as sig])
  (:gen-class))

;(defn embla-init
;  []
;  (egl/versions)
;  (egl/opengl-init)
;  (sig/default-signals-init))

;(defn embla-terminate
;  []
;  (egl/opengl-terminate)
;  (sig/signals-terminate))

;(defn embla-loop
;  "OpenGL loop."
;  []
;  (GL/createCapabilities)
;  (GL11/glClearColor 1.0 0.0 0.0 0.0)
;  (while (= (GLFW/glfwWindowShouldClose @egl/glfw-win) GLFW/GLFW_FALSE)
;    (GL11/glClear (bit-or GL11/GL_COLOR_BUFFER_BIT GL11/GL_DEPTH_BUFFER_BIT))
;    (GLFW/glfwSwapBuffers @egl/glfw-win)
;    (GLFW/glfwWaitEv ents))
;  (GLFW/glfwDestroyWindow @egl/glfw-win))

(defn -main
  "Launch Embla. REPL live coding optional"
  [& args]
  (println "Coucou"))

;  (try
;    (embla-init)
;    (embla-loop)
;    (finally
;      (embla-terminate))))
