(ns advent-of-code-2018.day06
  (:require [advent-of-code-2018.commons :as c]
            [clojure.math.numeric-tower :refer [abs]]))

(defrecord Point [x y id])

(def data
  (->> (c/load-data "day06.txt")
       (map (partial re-seq #"\d+"))
       (map (fn [item]
              (let [[x y] item]
                (Point. (Integer. x) (Integer. y) (str (gensym "P"))))))))

(defn max-coordinate [points]
  (apply max (mapcat (juxt :x :y) points)))

(defn manhattan-distance [{:keys [y x id]} px py]
  (+ (abs (- x px)) (abs (- y py))))

(defn closest-point [points px py]
  (let [distances (->> points
                       (map #(assoc % :dist (manhattan-distance % px py)))
                       (sort-by :dist))
        min-dist (take-while #(= (:dist (first distances)) (:dist %)) distances)]
    (if (> (count min-dist) 1)
      nil
      (-> min-dist first (dissoc :dist)))))

(defn is-not-infinite? [data {:keys [x y]}]
  (let [xsorted (sort-by :x data)
        ysorted (sort-by :y data)
        maxx (-> xsorted last :x)
        minx (-> xsorted first :x)
        maxy (-> ysorted last :y)
        miny (-> ysorted first :y)]
    (and (< minx x maxx)
         (< miny y maxy))))

(defn build-distance-matrix [data]
  (let [maxpoint (max-coordinate data)]
    (for [x (range maxpoint)
          y (range maxpoint)]
      (closest-point data x y))))

(defn distance-to-all [data {:keys [x y]}]
  (transduce (map #(manhattan-distance % x y)) + data))

(defn part-1 [data]
  (->> data
       build-distance-matrix
       (filter record?)
       (filter (partial is-not-infinite? data))
       (map :id)
       frequencies
       (sort-by val)))

(defn part-2 [data]
  (let [maxpoint (max-coordinate data)
        distances (for [x (range maxpoint)
                        y (range maxpoint)]
                    (distance-to-all data (Point. x y (str (gensym "P")))))]
    (->> distances
         (filter (partial > 10000))
         count)))
