(ns advent-of-code-2016.day03
  (:require [clojure.string :as str]))

(defn get-input []
  (map #(Integer. %) (filter #(not= % "") (str/split (slurp "resources/day03/input") #"\s+"))))

(defn triangle? [sides]
  (let [[a b c] sides]
    (and (> (+ a b) c)
         (> (+ b c) a)
         (> (+ c a) b))))

(def input (get-input))

(def input1 (partition 3 input))

(def input2
  (let [group1 (take-nth 3 input)
        group2 (take-nth 3 (drop 1 input))
        group3 (take-nth 3 (drop 2 input))]
    (concat (partition 3 group1) (partition 3 group2) (partition 3 group3))))

(defn count-triangles [coll]
  (count (filter triangle? coll)))

(def part1 (count-triangles input1))
(def part2 (count-triangles input2))