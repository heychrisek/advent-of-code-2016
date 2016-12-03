(ns advent-of-code-2016.day02)

(defn get-input []
  (doall (clojure.string/split-lines (slurp "resources/day02/input"))))

(def instructions (get-input))

; would be better to do this programmatically instead of hardcoding
(def part1-valid-moves {1 {"D" 4 "R" 2}
                        2 {"D" 5 "R" 3 "L" 1}
                        3 {"D" 6 "L" 2}
                        4 {"D" 7 "R" 5 "U" 1}
                        5 {"D" 8 "R" 6 "U" 2 "L" 4}
                        6 {"D" 9 "U" 3 "L" 5}
                        7 {"R" 8 "U" 4}
                        8 {"R" 9 "U" 5 "L" 7}
                        9 {"U" 6 "L" 8}})

(def part2-valid-moves {1 {"D" 3}
                        2 {"D" 6 "R" 3}
                        3 {"D" 7 "R" 4 "U" 1 "L" 2}
                        4 {"D" 8 "L" 3}
                        5 {"R" 6}
                        6 {"D" "A" "R" 7 "U" 2 "L" 5}
                        7 {"D" "B" "R" 8 "U" 3 "L" 6}
                        8 {"D" "C" "R" 9 "U" 4 "L" 7}
                        9 {"L" 8}
                        "A" {"R" "B" "U" 6}
                        "B" {"D" "D" "R" "C" "U" 7 "L" "A"}
                        "C" {"U" 8 "L" "B"}
                        "D" {"U" "B"}})

(defn interpret-line [start instructions valid-moves]
  (reduce (fn [acc el] 
            (get-in valid-moves [acc (str el)] acc)) start instructions))

(defn interpret-lines [lines valid-moves]
  (let [accumulator {:code [] :num 5}]
    (:code (reduce (fn [acc line]
              (let [new-num (interpret-line (:num acc) line valid-moves)]
                (assoc acc :code (conj (:code acc) new-num) :num new-num)))
            accumulator
            lines))))

(def part1 (interpret-lines instructions part1-valid-moves))
(def part2 (interpret-lines instructions part2-valid-moves))