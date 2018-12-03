(ns advent-of-code-2018.day02
  (:require [advent-of-code-2018.commons :as c]
            [clojure.string :as s]
            [clj-fuzzy.metrics :as m]))

(def data
  (c/load-data "day02.txt"))

(defn has-same-letter [c word]
  (contains? (set (vals (frequencies word))) c))

(defn part-1 []
  (let [two-chars (filter (partial has-same-letter 2) data)
        three-chars (filter (partial has-same-letter 3) data)]
    (* (count two-chars)
       (count three-chars))))

(defn- correct-distance? [[w1 w2]]
  (= 1 (m/levenshtein w1 w2)))

(defn part-2 []
  (let [[w1 w2] (->> data sort (partition 2 1) (filter correct-distance?) first)]
    (->> w1
         (filter (partial contains? (set w2)))
         (apply str))))
