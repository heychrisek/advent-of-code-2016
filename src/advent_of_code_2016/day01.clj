(ns advent-of-code-2016.day01)

;   2   _  _  _  _  _ 
;   1   _  _  _  _  _ 
;   0   _  _  x  _  _ 
;  -1   _  _  _  _  _ 
;  -2   _  _  _  _  _ 
;      -2 -1  0  1  2
;
; x is currently at [0 0] facing north. two steps north takes it to [0 2]. turning
; right and taking one step takes it to [1 2].

(defn get-input []
  (clojure.string/split (slurp "resources/day01/input") #", "))

(def instructions (get-input))

(def initial-pos {:x 0
                  :y 0
                  :direction 0})

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

(defn proceed [pos n-steps]
  (let [direction (:direction pos)
        x (:x pos)
        y (:y pos)]
    (case direction
      0 (assoc pos :x (+ x n-steps))
      1 (assoc pos :y (+ y n-steps))
      2 (assoc pos :x (- x n-steps))
      3 (assoc pos :y (- y n-steps)))))

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

(def part1 (blocks-away initial-pos (apply-instructions initial-pos instructions)))