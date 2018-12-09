(ns advent-of-code-2018.day04
  (:require [advent-of-code-2018.commons :as c]
            [clojure.string :as s]))

(def data
  (sort (c/load-data "day04.txt")))

(defn row-type [line]
  (condp #(s/includes? %2 %1) line
    "Guard" :guard
    "falls" :fall
    "wakes" :wake))

(defn get-guard-no [line]
  (when (= :guard (row-type line))
    (-> (re-find #"#\d+" line)
        (subs 1)
        Integer.)))

(defn get-minutes [line]
  (as-> line l
       (re-find #"\d{2}:\d{2}" l)
       (s/split l #":")
       (last l)
       (Integer. l)))

(defn parse [data]
  (loop [rows data
         curr {}
         results '()]
    (if (empty? rows)
      (filter #(= 3 (count %)) results)
      (let [curr-row (first rows)
            rowt (row-type curr-row)]
        (condp = rowt
          :guard (recur (rest rows)
                        (assoc {} :guard (get-guard-no curr-row))
                        (if (empty? curr)
                          results
                          (conj results curr)))
          :fall (recur (rest rows)
                       (assoc curr :fall (get-minutes curr-row))
                       (if-not (nil? (:fall curr))
                         (conj results curr)
                         results))
          :wake (recur (rest rows)
                       (assoc curr :wake (get-minutes curr-row))
                       results))))))

(defn merge-datas []
  (let [with-ranges (for [{:keys [guard fall wake]} (parse data)]
                      {:guard guard
                       :minutes (range fall wake)})]
    (loop [items with-ranges
           merged {}]
      (if (empty? items)
        merged
        (let [{:keys [guard minutes]} (first items)
              k (keyword (str "g-" guard))
              v (k merged)]
          (recur (rest items)
                 (if (nil? v)
                   (assoc merged k minutes)
                   (assoc merged k (concat v minutes)))))))))

(defn find-sleepiest [merged-datas]
  (->> merged-datas
       (map (fn [[k v]] [k (count v)]))
       (apply max-key last)
       first))

(defn part-1 []
  (let [data (merge-datas)
        k (-> data find-sleepiest)
        v (k data)
        sleepiest-min (key (apply max-key val (frequencies v)))
        guard (-> k name (s/split #"-") last Integer.)]
    (* guard sleepiest-min)))

(defn part-2 []
  (let [data (merge-datas)
        sleepiest-minutes (for [[k v] data]
                            [k (apply max-key val (frequencies v))])
        [g [v _]] (apply max-key (comp val last) sleepiest-minutes)
        guard (-> g name (s/split #"-") last Integer.)]
    (* guard v)))
