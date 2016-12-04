(ns advent-of-code-2016.day04)

(defn get-input []
  (doall (clojure.string/split-lines (slurp "resources/day04/input"))))

(defn sanitize-input [input]
  (map
    (fn [line]
      (let [checksum (filter #(Character/isLetter %) (take-last 7 line))
            sector-id (filter #(Character/isDigit %) (drop-last 7 line))
            encrypted-name (filter #(Character/isLetter %) (drop-last 7 line))]
        [(apply str encrypted-name) (apply str sector-id) (apply str checksum)]))
    input))

(def input (sanitize-input (get-input)))

(defn checksum-from-encrypted-name [encrypted-name]
  (clojure.string/join (map #(key %) (take 5 (sort-by val #(> %1 %2) (sort-by key (frequencies encrypted-name)))))))

(defn real-room? [line]
  (let [encrypted-name (first line)
        checksum (last line)]
    (= (checksum-from-encrypted-name encrypted-name) checksum)))

(defn real-rooms [lines]
  (filter #(real-room? %) lines))

(defn sum-sector-ids [rooms]
  (reduce (fn [acc el] (+ acc (read-string (second el)))) 0 rooms))

(def part1 (sum-sector-ids (real-rooms input)))