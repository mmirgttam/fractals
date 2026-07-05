(ns fractals.core
  (:gen-class)
  (:require
    [fractals.cantor :as cantor]
    [fractals.koch :as koch]
    [fractals.l-systems :as l-systems]
    [fractals.sierpinski :as sierpinski]
    [fractals.tree :as tree]))

(def sketches
  {"cantor" cantor/run
   "koch" koch/run
   "l-systems" l-systems/run
   "sierpinski" sierpinski/run
   "tree" tree/run})

(defn -main
  [& [sketch system-index]]
  (if-let [runner (get sketches sketch)]
    (if (= sketch "l-systems")
      (if system-index
        (runner system-index)
        (runner))
      (runner))
    (do
      (println "Run a sketch with:")
      (println "  lein run <sketch>")
      (println "  lein run l-systems <system-index>")
      (println "Available sketches:")
      (doseq [name (keys sketches)]
        (println " -" name))
      (System/exit 1))))
