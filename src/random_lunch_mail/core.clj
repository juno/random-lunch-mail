(ns random-lunch-mail.core
  (:use [clojure.xml :only (parse)])
  (:gen-class))

(def api-key-name "LIVEDOOR_GOURMET_API_KEY")
(def api-version "v1.0")
(def station-id 2800)

(defn getenv [name]
  (get (System/getenv) name))

(defn api-key []
  (getenv api-key-name))

(defn station-page-url [station-id]
  (str "http://api.gourmet.livedoor.com/" api-version "/restaurant/?api_key=" (api-key) "&station_id=" station-id "&sort=total"))

(defn get-content-by-tag
     "XMLタグのコンテンツ（<hoge>これ</hoge>）を取得する。tag：コンテンツを取得したいタグ名のキーワード（例 :title）、xml-el：xml-seqから取得したXMLエレメント要素"
     [tag xml-el]
     (first
      (some #(if (= tag (:tag %)) (:content %))
            (:content xml-el))))

(defn -main []
  (let [url (station-page-url station-id)
        xs (xml-seq (parse url))
        rss-seq (for [el xs :when (= :restaurant (:tag el))]
                  (list (get-content-by-tag :name el)
                        (get-content-by-tag :permalink el)))]
    (doseq [entry rss-seq]
      (println (str (first entry) " " (second entry))))))
