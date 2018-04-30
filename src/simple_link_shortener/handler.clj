(ns simple-link-shortener.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.util.response :as response]
            [ring.middleware.json :as middleware]
            [clojure.java.jdbc :as jdbc]))

; Local SQLite database
(def db
  {:classname "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname "db/urls.sqlite3"})

; Make a generic response
(defn make-response [text]
  (response/content-type (response/response {:result text}) "application/json"))

; Make a generic error response
(defn make-error [text]
    (response/status
      (make-response text)
      301)
  )

; Redirect to url from id
(defn get-url-from-id [id]
  (get
    (first
      (jdbc/query db ["select dest from urls where src = ?" id]))
    :dest))

(defn make-redirect-response [id]
  (response/redirect (get-url-from-id id)))

(defn redirect-to [id] 
  (make-redirect-response id))

; List all redirections available
(defn get-all-urls []
  (make-response (jdbc/query db ["select * from urls"])))

; Add a new redirection with a random id
(defn random-id [] (apply str (take 8 (repeatedly #(char (+ (rand 26) 97))))))

(defn add-url-to-db [id url]
  (jdbc/insert! db :urls
      {:src id :dest url}))

(defn url-from-request [request]
  (get (get request :params) :url))

(defn add-url [request]
  (let [id (random-id)]
    (let [url (url-from-request request)]
      (add-url-to-db id url)
      (make-response id))))

; No implementation yet to remove a redirect
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
    (middleware/wrap-json-params)
    (middleware/wrap-json-response)))
