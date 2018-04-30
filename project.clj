(defproject simple-link-shortener "0.1.0"
  :description "A really simple link shortener in Clojure"
  :url "https://www.ajdecon.org"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring/ring-defaults "0.2.1"]
                 [ring/ring-json "0.4.0"]
                 [org.clojure/java.jdbc "0.7.6"]
                 [org.xerial/sqlite-jdbc "3.21.0"]
                 [migratus "1.0.6"]]
  :plugins [[lein-ring "0.9.7"]
            [migratus-lein "0.5.7"]]
  :ring {:handler simple-link-shortener.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}}
  :migratus {:store :database
             :migration-dir "migrations"
             :db {:classname "org.sqlite.JDBC"
                  :subprotocol "sqlite"
                  :subname "db/urls.sqlite3"}})
