(ns vectorql.core)

(defn join-commas [v]
  (clojure.string/join ", " v))

(defn join-spaces [v]
  (clojure.string/join " " v))

(defn stringify
  "Graphql only has numbers and strings"
  [v]
  (if (number? v)
    (str v)
    (str "\"" (name v) "\"")))

(defn map->str
  "Convert map to js-like pairs:
  {:a 1 :b \"2\"} => (\"a: 1\" \"b: \\\"2\\\"\")"
  [m]
  (map (fn [[k v]]
         (str (name k) ": " (stringify v)))
       m))

(defn fields->str [m]
  (join-commas (map->str m)))

(defn query? [v]
  (and (= 3 (count v))
       (keyword? (first v))
       (map? (second v))))

(defn shape? [v]
  (and (= 2 (count v))
       (keyword? (first v))
       (vector? (second v))))

(defn wrap-braces [s]
  (str "(" s ")"))

(defn wrap-curlies [s]
  (str "{ " s " }"))

(declare graphql)

(defn compile-body
  ([fname rest] (compile-body fname nil rest))
  ([fname props rest]
   (filter (complement nil?)
           [(name fname)
            (when props
              (wrap-braces (fields->str props)))
            (wrap-curlies (graphql rest))])))

(defn graphql [v]
  (if (vector? v)
    (join-spaces
     (if (or (query? v) (shape? v))
      (apply compile-body v)
      (map graphql v)))
    (name v)))
