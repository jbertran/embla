#! /usr/bin/env boot

(set-env!
 :repositories #(conj % ["mavencentral" {:url "https://repo.maven.apache.org/maven2"}])
 :dependencies '[[org.lwjgl/lwjgl "3.0.0b"] 
                 [org.lwjgl/lwjgl-platform "3.0.0b" :classifier "natives-linux"]
                 [org.l33tlabs.twl/pngdecoder "1.0"]]
 :resource-paths #{"src/"}
 :target "classes"
 )

(deftask build
  "Build embla with LWJGL dependencies"
  []
  (comp
   (pom :project 'embla :version "1.0.0")
   (javac "src/java/embla/model/*.java")
   (javac "src/java/embla/utils/*.java")
   (javac "src/java/embla/view/glShapes/*.java")
   (javac "src/java/embla/view/glUtils/*.java")
   (aot :all)
   ))
