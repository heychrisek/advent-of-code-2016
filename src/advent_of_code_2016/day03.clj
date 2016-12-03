(ns advent-of-code-2016.day03)

(defn get-input []
  (partition 3 (map #(Integer. %) (filter #(not= % "") (clojure.string/split (slurp "resources/day03/input") #"\s+")))))

(defn triangle? [sides]
  (let [[a b c] sides]
    (and (> (+ a b) c)
         (> (+ b c) a)
         (> (+ c a) b))))

(def input (get-input))

(defn count-triangles [coll]
  (count (filter triangle? coll)))

(def part1 (count-triangles input))