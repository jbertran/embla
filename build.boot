#! /usr/bin/env boot

(set-env!
 :repositories #(conj % ["mavencentral" {:url "https://repo.maven.apache.org/maven2"}])
 :dependencies '[[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.2.374"]
                 [org.lwjgl/lwjgl "3.0.0b"]
                 [org.lwjgl/lwjgl-platform "3.0.0b" :classifier "natives-linux"]
                 [org.lwjgl/lwjgl-platform "3.0.0b" :classifier "natives-osx"]
                 [org.lwjgl/lwjgl-platform "3.0.0b" :classifier "natives-windows"]
                 [org.l33tlabs.twl/pngdecoder "1.0"]]
 :resource-paths #{"src/"}
 :target "classes"
 )

(deftask build
  "Build embla with LWJGL dependencies"
  []
  (comp
   (pom :project 'embla :version "1.0.0")
   (javac "src/jv/*")
   (aot :all)))

(deftask run
  "Start an Embla render engine instance"
  []
  (use 'clj.embla.core)
  (use 'clj.embla.gom)
  (use 'clj.embla.signals)
  (eval '(clj.embla.core/embla-run)))
