(ns advent-of-code-2016.day06
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn transpose [matrix]
  (apply map list matrix))

(def input (-> "day06/input"
               io/resource
               io/file
               slurp
               str/split-lines
               transpose))

(defn char-by-frequency [cmp s]
  (->> s
       frequencies
       (sort-by val cmp)
       first
       key))

(def part1 (map (partial char-by-frequency >) input))
(def part2 (map (partial char-by-frequency <) input))