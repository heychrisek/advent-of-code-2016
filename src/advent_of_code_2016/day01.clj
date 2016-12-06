(ns advent-of-code-2016.day01
  (:require [clojure.string :as str]))

;   2   _  _  _  _  _ 
;   1   _  _  _  _  _ 
;   0   _  _  x  _  _ 
;  -1   _  _  _  _  _ 
;  -2   _  _  _  _  _ 
;      -2 -1  0  1  2
; x is currently at [0 0] facing north. two steps north takes it to [0 2]. turning
; right and taking one step takes it to [1 2].

(defn get-input []
  (str/split (slurp "resources/day01/input") #", "))

(def instructions (get-input))

(def initial-pos {:x 0
                  :y 0
                  :direction 0
                  :history [[0 0]]})

; directions: [north 0] [east 1] [south 2] [west 3]
;         N0
;     W3      E1
;         S2

(defn turn-left [pos]
  (let [new-direction (mod (- (:direction pos) 1) 4)]
  (assoc pos :direction new-direction)))

(defn turn-right [pos]
  (let [new-direction (mod (+ (:direction pos) 1) 4)]
  (assoc pos :direction new-direction)))

; this does too much. conditional based on which value has changed, create a range from
; old position to new position, realize the sequence, map through to create new tuples,
; put into vector from list, and conj the final new position.
(defn- find-diff-range [last-x last-y new-x new-y]
  (cond
    (< last-x new-x) (conj (into [] (map #(vector % last-y) (doall (range last-x new-x)))) [new-x new-y])
    (> last-x new-x) (conj (into [] (map #(vector % last-y) (doall (range last-x new-x -1)))) [new-x new-y])
    (< last-y new-y) (conj (into [] (map #(vector last-x %) (doall (range last-y new-y))))[new-x new-y])
    (> last-y new-y) (conj (into [] (map #(vector last-x %) (doall (range last-y new-y -1)))) [new-x new-y])))

(defn update-history [pos new-coord]
  (let [last-x (first (last (:history pos)))
        last-y (last (last (:history pos)))
        new-x (first new-coord)
        new-y (last new-coord)]
    (concat (:history pos) (rest (find-diff-range last-x last-y new-x new-y)))))

(defn proceed [pos n-steps]
  (let [direction (:direction pos)
        x (:x pos)
        y (:y pos)]
    (case direction
      0 (assoc pos :x (+ x n-steps) :history (update-history pos [(+ x n-steps) (:y pos)]))
      1 (assoc pos :y (+ y n-steps) :history (update-history pos [(:x pos) (+ y n-steps)]))
      2 (assoc pos :x (- x n-steps) :history (update-history pos [(- x n-steps) (:y pos)]))
      3 (assoc pos :y (- y n-steps) :history (update-history pos [(:x pos) (- y n-steps)])))))

; using `str` and `read-string` too much. gotta figure out how to deal with chars better
(defn apply-instruction [pos instruction]
  (let [[direction & n-steps] instruction]
    (if (= (str direction) "R")
      (proceed (turn-right pos) (read-string (apply str n-steps)))
      (proceed (turn-left pos) (read-string (apply str n-steps))))))

(defn apply-instructions [pos instructions]
  (reduce apply-instruction pos instructions))

(defn blocks-away [pos1 pos2]
  (let [diff-x (- (:x pos1) (:x pos2))
        diff-y (- (:y pos1) (:y pos2))]
    (+ (Math/abs diff-x) (Math/abs diff-y))))

; could use reductions here?
(defn find-first-duplicate [coll]
  (let [freq (frequencies coll)]
    (first (filter #(> (get freq %) 1) coll))))

(def final-position (apply-instructions initial-pos instructions))
(def first-duplicate (find-first-duplicate (:history final-position)))

(def part1 (blocks-away initial-pos final-position))
(def part2 (blocks-away initial-pos {:x (first first-duplicate) :y (last first-duplicate)}))