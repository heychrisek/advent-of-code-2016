(ns advent-of-code-2016.day04
  (:require [clojure.string :as str]))

(defn get-input []
  (doall (str/split-lines (slurp "resources/day04/input"))))

(defn sanitize-input [input]
  (map
    (fn [line]
      (let [checksum (filter #(Character/isLetter %) (take-last 7 line))
            sector-id (filter #(Character/isDigit %) (drop-last 7 line))
            encrypted-name (drop-last 1 (filter #(not (Character/isDigit %)) (drop-last 7 line)))]
        [(apply str encrypted-name) (apply str sector-id) (apply str checksum)]))
    input))

(def input (sanitize-input (get-input)))

(defn checksum-from-encrypted-name [encrypted-name]
  (->> (seq encrypted-name)
       (filter #(Character/isLetter %))
       frequencies
       (sort-by key)
       (sort-by val >)
       (map first)
       (take 5)
       str/join))

(defn real-room? [[encrypted-name _ checksum]]
  (= (checksum-from-encrypted-name encrypted-name) checksum))

(defn real-rooms [lines]
  (filter real-room? lines))

(defn sum-sector-ids [rooms]
  (->> rooms
      (map (fn [[_ x _]] (Integer/parseInt x)))
      (reduce +)))

(defn shift-char [letter]
  (let [code (int letter)]
    (case code
      122 (char 97) ; loop z back to a
      45 (char 45)  ; ignore hyphens
      (char (+ 1 code)))))

; create (range n), then reduce through that sequence, shifting char each time
(defn shift-char-n-times [letter n]
  (reduce (fn [acc el] (shift-char acc)) letter (range n)))

(defn decrypt-name [name n]
  (let [n-shifts (mod n 26)] ; (mod n 26) avoids needlessly going through alphabet multiple times 
    (str/join (map #(shift-char-n-times % n-shifts) name))))

(defn decrypt-lines [lines]
  (map #(assoc % 0 (decrypt-name (first %) (read-string (second %)))) lines))

(defn includes-north-pole? [lines]
  (filter #(re-find #"^northpole" (first %)) lines))

(def part1 (sum-sector-ids (real-rooms input)))
(def part2 (includes-north-pole? (decrypt-lines (real-rooms input))))