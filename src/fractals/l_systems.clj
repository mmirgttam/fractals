(ns fractals.l-systems
  (:require
    [quil.core :as q]
    [quil.middleware :as qm]))

(def systems
  [{:name "quadratic koch island" :n 5 :start-x 500 :start-y 450 :angle 90 :len -5 :axiom "F-F-F-F" :rules {\F "F-F+F+FF-F-F+F"}}
   {:name "quadratic snowflake curve" :n 6 :start-x 675 :start-y 500 :angle 90 :len -8 :axiom "-F" :rules {\F "F+F-F-F+F"}}
   {:name "pentagons of pentagons" :n 6 :start-x 110 :start-y 325 :angle 72 :len -6 :axiom "F-F-F-F-F" :rules {\F "F-F++F+F-F-F"}}
   {:name "islands and lakes" :n 4 :start-x 200 :start-y 450 :angle 90 :len -8 :axiom "F+F+F+F" :rules {\F "F+f-FF+F+FF+Ff+FF-f+FF-F-FF-Ff-FFF" \f "ffffff"}}
   {:name "sierpinski gasket with edge rewriting" :n 8 :start-x 250 :start-y 500 :angle 60 :len -7 :axiom "r" :rules {\l "r+l+r" \r "l-r-l"}}
   {:name "hexagonal gosper curve with edge rewriting" :n 6 :start-x 150 :start-y 500 :angle 60 :len -10 :axiom "r" :rules {\l "l+r++r-l--ll-r+" \r "-l+rr++r+l--l-r"}}
   {:name "tree with node rewriting" :n 6 :start-x 350 :start-y 600 :angle 22.5 :len -10 :axiom "F" :rules {\F "FF+[+F-F-F]-[-F+F+F]"}}
   {:name "tree 2" :n 9 :start-x 350 :start-y 600 :angle 20 :len -2 :axiom "f" :rules {\f "F[+f]F[-f]+f" \F "FF"}}
   {:name "tree 3" :n 9 :start-x 350 :start-y 600 :angle 25.7 :len -2 :axiom "f" :rules {\f "F[+f][-f]Ff" \F "FF"}}
   {:name "tree 4" :n 10 :start-x 350 :start-y 600 :angle 25.7 :len -1 :axiom "f" :rules {\f "F-[[f]+f]+F[+Ff]-f" \F "FF"}}])

(defn commands [system]
  {\F #(do (q/line 0 0 0 (:len system)) (q/translate 0 (:len system)))
   \f #(q/translate 0 (:len system))
   \+ #(q/rotate (* (:angle system) (/ Math/PI 180)))
   \- #(q/rotate (* (:angle system) (/ Math/PI 180) -1))
   \[ #(q/push-matrix)
   \] #(q/pop-matrix)
   ; copies of F for edge rewriting
   \l #(do (q/line 0 0 0 (:len system)) (q/translate 0 (:len system)))
   \r #(do (q/line 0 0 0 (:len system)) (q/translate 0 (:len system)))})

(defn setup [system]
  (fn []
    (q/frame-rate 2)
    (:axiom system)))

(defn step [system]
  (fn [sentence]
    (if (< (q/frame-count) (:n system))
      (apply str (replace (:rules system) sentence))
      sentence)))

(defn draw [system]
  (fn [sentence]
    (q/background 255)
    (q/translate (:start-x system) (:start-y system))
    ((apply juxt (map (commands system) sentence)))))

(defn parse [system-index]
  (when system-index
    (try
      (Integer/parseInt system-index)
      (catch NumberFormatException _
        nil))))

(defn help []
  (println "Run an L-system with:")
  (println "  lein run l-systems <system-index>")
  (println "Supported systems:")
  (doseq [[i system] (map-indexed vector systems)]
    (println " -" i (:name system))))

(defn run
  ([]
   (help)
   (System/exit 1))
  ([index-arg]
   (let [index (when index-arg (Integer/parseInt index-arg))
         system (when index (get systems index))]
     (if system
       (q/defsketch l-systems
         :title (str "L-Systems: " (:name system))
         :size [700 600]
         :setup (setup system)
         :draw (draw system)
         :update (step system)
         :middleware [qm/fun-mode])
       (do
         (help)
         (System/exit 1))))))
