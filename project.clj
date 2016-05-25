(defproject gameloop "0.1.0-SNAPSHOT"
  :description         "2D openGL FRP"
  :url                 ""
  :source-paths        ["src/clj"]
  :java-source-paths   ["src/jv"]
  :license             { :name "MIT License"
                         :url "LICENSE" }
  :dependencies        [[org.clojure/clojure "1.8.0"]
                        [org.clojure/core.async "0.2.374"]]
  :resource-paths      ["lwjgl_jar/lwjgl.jar"]
  :jvm-opts            [;"-XstartOnFirstThread"
                        ~(str "-Djava.library.path=lwjgl_native/:" 
                              (System/getProperty "java.library.path"))]
  :main                ^:skip-aot embla.core
  :target-path         "target/%s"
  :aliases             {"dumbrepl" ["trampoline" "run" "-m" "clojure.main/main"]}
  :timeout             120000
  ;:profiles            { :uberjar }
  )
