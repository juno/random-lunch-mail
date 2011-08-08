(ns random-lunch-mail.test.core
  (:use [random-lunch-mail.core])
  (:use [clojure.test]))

(deftest test-constants
  (is (= api-key-name "LIVEDOOR_GOURMET_API_KEY"))
  (is (= api-version "v1.0"))
  (is (= station-id 2800)))

(deftest test-station-page-url
  (is (= (station-page-url 2800)
         "http://api.gourmet.livedoor.com/v1.0/restaurant/?api_key=&station_id=2800&sort=total")))
