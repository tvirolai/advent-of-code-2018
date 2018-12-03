(ns advent-of-code-2018.day01
  (:require [advent-of-code-2018.commons :as c]))

(def data
  (c/load-data "day01.txt" read-string))

(defn part-1 []
  (reduce + data))

(defn part-2 []
  (loop [d (reductions + (cycle data))
         seen #{}
         curr 0]
    (or (seen curr)
        (recur (rest d)
               (conj seen curr)
               (first d)))))
