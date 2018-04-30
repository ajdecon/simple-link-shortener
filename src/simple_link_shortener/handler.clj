(ns simple-link-shortener.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.util.response :as response]
            [ring.middleware.json :as middleware]
            [clojure.java.jdbc :refer :all :as jdbc]))

; Local SQLite database
(def db
  {:classname "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname "db/urls.db"})

; Make a generic error response
(defn make-error [text]
  (response/content-type
    (response/status
      (response/response {:error text})
      301)
    "application/json"
  ))

; Make a generic response
(defn make-response [text]
  (response/content-type (response/response {:text text}) "application/json"))

;(defn get-all-urls [] (make-error "not implementend"))
(defn get-all-urls [] (make-response db))

(defn add-url [request] (make-error "not implemented"))

(defn redirect-to [id] (make-error "not implemented"))

(defn remove-url [id] (make-error "not implemented"))

; Application routes
(defroutes app-routes
  (context "/urls" []
    (defroutes urls-routes
      (GET "/" [] (get-all-urls))
      (POST "/" request (add-url request))
      (context "/:id" [id]
        (GET "/" [] (redirect-to id))
        (DELETE "/" [] (remove-url id)))))
  (route/not-found "Not Found"))

; Define the app to actually run!
(def app
  (->
    (wrap-defaults app-routes api-defaults)
    ; JSON-ify bodies and responses
    (middleware/wrap-json-body)
    (middleware/wrap-json-response)))
