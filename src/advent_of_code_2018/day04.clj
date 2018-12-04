(ns advent-of-code-2018.day04
  (:require [advent-of-code-2018.commons :as c]
            [clojure.string :as s]))

(def data
  (sort (c/load-data "day04.txt")))

(defn parse-data [data]
  (partition-by #(s/includes? % "Guard #") data))
