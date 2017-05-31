(ns vectorql.core-test
  (:require [clojure.test :refer :all]
            [vectorql.core :refer :all]))

(deftest graphql-compile
  (testing "graphql cmpilation"
    (are [x y] (= x (graphql y))
         ""
         []

         "a b c"
         [:a :b :c]

         "query { folders () { title id } }"
         [:query
          [:folders {}
           [:title :id]]]

         "query { folders { title id } }"
         [:query
          [:folders
           [:title :id]]]

         "query { folder (id: 123) { title id } }"
         [:query
          [:folder {:id 123}
           [:title :id]]]

         "query { folder (id: 123, title: \"lol\") { title id } }"
         [:query
          [:folder {:id 123 :title "lol"}
           [:title :id]]]

         "query { links (tag: \"tag\") { comment created_at id metadata { html { theme_color title } og { title image } twitter { title } oembed { html } } } }"
         [:query
          [:links {:tag "tag"}
           [:comment :created_at :id
            [:metadata
             [[:html [:theme_color :title]]
              [:og [:title :image]]
              [:twitter [:title]]
              [:oembed [:html]]]]]]])))
