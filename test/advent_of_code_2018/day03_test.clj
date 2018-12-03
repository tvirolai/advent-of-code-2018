(ns advent-of-code-2018.day03-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.day03 :refer :all]))

(def test-input
  ["#1 @ 1,3: 4x4"
   "#2 @ 3,1: 4x4"
   "#3 @ 5,5: 2x2"])

(deftest testi
  (testing "Testing with example input"
    (is (= 4 (part-1 test-input)))))
