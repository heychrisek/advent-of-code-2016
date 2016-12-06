; this solution is really slow
  
(ns advent-of-code-2016.day05
  (:require [clojure.string :as str])
  (:import (java.security MessageDigest)
           (javax.xml.bind DatatypeConverter)))

(defn get-input []
  (slurp "resources/day05/input"))

(def input (get-input))

(def ^MessageDigest hasher (MessageDigest/getInstance "MD5"))

(defn md5 [^String what]
  (->> (.getBytes what "utf-8")
       (.digest hasher)
       (DatatypeConverter/printHexBinary)))

(defn has-five-zeros? [hash]
  (clojure.string/starts-with? hash "00000"))

; (defn iterate-door-index [door n]
;   (let [door-length (count door)]
;     (iterate #(str (clojure.string/join (take door-length %))
;                    (inc (read-string (clojure.string/join (drop door-length %)))))
;              (str door n))))

; (def hash-seq (iterate-door-index input 0))

; (defn find-n-valid-hashes [hash-seq n]
;   (take n (filter #(has-five-zeros? (md5 %)) hash-seq)))

; (def valid-hashes (find-n-valid-hashes hash-seq 8))

; (defn password-from-hashes [hashes]
;   (clojure.string/join (map #(nth (md5 %) 5) hashes)))

(defn hash-seq [door n]
  (lazy-seq
    (cons
      (md5 (str door n))
      (lazy-seq (hash-seq door (inc n))))))

(def part1 (->> (hash-seq input 0)
                (filter has-five-zeros?)
                (map #(nth % 5))
                (take 8)
                str/join))