(ns advent-of-code-2018.day05
  (:require [advent-of-code-2018.commons :as c]
            [clojure.string :as s]))

(def data
  (s/trim (slurp "./data/day05.txt")))

(defn reacts? [unit]
  (and (= 1 (count (set (map s/lower-case unit))))
       (= 2 (count unit))
       (not= (seq unit) (reverse unit))))

(defn get-reacting-units [text]
  (filter reacts? (map #(apply str %) (partition 2 1 text))))

(defn remove-unit [text unit case-insensitive?]
  (s/join (s/split text (re-pattern (str (when case-insensitive? "(?i)") unit)))))

(defn remove-units [text]
  (let [reacting-units (get-reacting-units text)]
    (if (empty? reacting-units)
      text
      (recur (remove-unit text (first reacting-units) false)))))

(defn part-1 []
  (count (remove-units data)))

(defn part-2 []
  (apply min
         (for [unit (set (map s/upper-case data))
               :let [produced-polymer (remove-unit data unit true)
                     res (count (remove-units produced-polymer))]]
           res)))

