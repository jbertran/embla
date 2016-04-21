#! /usr/bin/env boot

(set-env!
 :repositories #(conj % ["mavencentral" {:url "https://repo.maven.apache.org/maven2"}])
 :dependencies '[[org.lwjgl/lwjgl "3.0.0b"] [org.lwjgl/lwjgl-platform "3.0.0b" :classifier "natives-linux"]]
 :resource-paths #{"src/"}
 )

(comment
  (deftask run_file
  "Run embla with a file"
    [filepath]
    (println filepath)
    ))

(deftask build
  "Build embla with LWJGL dependencies"
  []
  (comp
   (pom :project 'embla :version "0.1.0")
   (aot :all)
   ))
