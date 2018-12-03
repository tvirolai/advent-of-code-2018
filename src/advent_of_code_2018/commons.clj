(ns advent-of-code-2018.commons
  (:require [clojure.string :as s]))

(defn load-data
  ([filename]
   (as-> (format "./data/%s" filename) data
     (slurp data)
     (s/split data #"\n")))
  ([filename f]
   (map f (load-data filename))))
