; this solution is really slow
  
(ns advent-of-code-2016.day05)

(defn get-input []
  (slurp "resources/day05/input"))

(def input (get-input))

; https://gist.github.com/jizhang/4325757
(defn md5 [s]
  (let [algorithm (java.security.MessageDigest/getInstance "MD5")
        size (* 2 (.getDigestLength algorithm))
        raw (.digest algorithm (.getBytes s))
        sig (.toString (java.math.BigInteger. 1 raw) 16)
        padding (apply str (repeat (- size (count sig)) "0"))]
    (str padding sig)))

(defn has-five-zeros? [hash]
  (clojure.string/starts-with? hash "00000"))

; lazy sequence of (str door n) with incrementing n
; i.e., (take 3 (iterate-door-index "abc" 0)) returns ("abc0" "abc1" "abc2")
(defn iterate-door-index [door n]
  (let [door-length (count door)]
    (iterate #(str (clojure.string/join (take door-length %))
                   (inc (read-string (clojure.string/join (drop door-length %)))))
             (str door n))))

(def hash-seq (iterate-door-index input 0))

(defn find-n-valid-hashes [hash-seq n]
  (take n (filter #(has-five-zeros? (md5 %)) hash-seq)))

(def valid-hashes (find-n-valid-hashes hash-seq 8))

(defn password-from-hashes [hashes]
  (clojure.string/join (map #(nth (md5 %) 5) hashes)))

(def part1 (password-from-hashes valid-hashes))