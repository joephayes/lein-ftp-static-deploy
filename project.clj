(defproject lein-ftp-static-deploy "0.1.1-SNAPSHOT"
  :description "A Leiningen plugin to deploy a directory as a static website via FTP."
  :url "http://github.com/joephayes/lein-ftp-static-deploy"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[com.velisco/clj-ftp "0.3.9"]]
  :eval-in-leiningen true
  :scm  {:name  "git"
         :url  "https://github.com/joephayes/lein-ftp-static-deploy"}
  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]
  :repositories [["releases" {:url "[https://clojars.org/repo](https://clojars.org/repo)" :creds :gpg}]
                 ["snapshots" {:url "[https://clojars.org/repo](https://clojars.org/repo)" :creds :gpg}]])
