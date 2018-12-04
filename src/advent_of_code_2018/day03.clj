(ns advent-of-code-2018.day03
  (:require [advent-of-code-2018.commons :as c]
            [clojure.string :as s]))

(def data
  (c/load-data "day03.txt"))

(defrecord Instruction [no left top width length])

(defrecord Coord [x y])

(defn line-to-instruction [line]
  (let [d (for [v (s/split line #"@|:| |,|x")
                :when (pos? (count v))]
            (if (s/includes? v "#")
              v
              (Integer. v)))]
    (apply ->Instruction d)))

(defn instr-to-coords [{:keys [width length left top]}]
  (for [x (map (partial + left) (range width))
        y (map (partial + top) (range length))]
    (Coord. x y)))

(defn- coord->kw [{:keys [x y]}]
  (keyword (format "%s-%s" x y)))

(defn- data-to-coords [data]
  (->> data
       (map (comp instr-to-coords line-to-instruction))
       flatten))

(defn part-1 []
  (->> data
       data-to-coords
       frequencies
       (transduce (map (fn [[k v]] (if (> v 1) 1 0))) +)))

(defn part-2 []
  (let [claim-counts (->> data data-to-coords (map coord->kw) sort frequencies)]
    (first (for [line data
                 :let [i (->> line line-to-instruction instr-to-coords (map coord->kw))]
                 :when (every? (partial = 1) (map #(% claim-counts) i))]
             (-> line line-to-instruction :no)))))
