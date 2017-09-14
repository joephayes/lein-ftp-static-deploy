(ns leiningen.ftp-static-deploy
  (:require [clojure.java.io :as io]
            [miner.ftp :refer [with-ftp client-put client-mkdirs]]))

;;
;; add this to project.clj and the set the appropriate env variables
;;
;; :ftp { :host ~(System/getenv "FTP_DEPLOY_HOST") :user ~(System/getenv "FTP_DEPLOY_USER") :pass ~(System/getenv "FTP_DEPLOY_PASSWORD") :port ~(or (System/getenv "FTP_DEPLOY_PORT") 21) :ftp-static-deploy {:remote-root "THE REMOTE DIRECTORY YOU WANT TO DEPLOY TO" :local-root "LOCAL DIRECTORY YOU WANT TO DEPLOY FROM"}}

(defmulti make-remote-url :protocol)
(defmethod make-remote-url "ftp" [url-data]
  (let [{:keys [protocol user host pass port]} url-data]
    (str protocol "://" user ":" pass "@" host ":" port)))
(defmethod make-remote-url :default [{:keys [protocol]}]
  (println (str "Unknown protocol '" protocol "'" ))
  nil)

(defn trim-path-separator
  "Trim path separator character from beginning and end of a string"
  [s]
  (-> s
      (clojure.string/replace #"^/" "")
      (clojure.string/replace #"/$" "")))

(defn make-remote-path
  [remote-root remote-dir remote-file]
  (let [valid-root (str "/" (trim-path-separator remote-root))
        valid-dir (trim-path-separator remote-dir)
        valid-file (trim-path-separator remote-file)]
    (-> valid-root
        (io/file valid-dir valid-file)
        (.getPath))))

(defn upload-directory
  "Upload a whole directory (including its nested sub directories and files) to a FTP server"
  [url remote-root local-root remote-parent-path]
  (let [dirFiles (.listFiles (io/file local-root))]
    (doseq [f dirFiles]
      (let [remote-item (make-remote-path remote-root remote-parent-path (.getName f))]
        (if (.isFile f)
          (do
            (with-ftp [client url :file-type :binary]
              (client-put client f remote-item)
              (println (str "Put " (.getName f) " to " remote-item))))
          (do
            (with-ftp [client url]
              (client-mkdirs client remote-item))
            (upload-directory
              url
              remote-root
              (.getAbsolutePath f)
              (str remote-parent-path "/" (.getName f)))))))))

(defn ftp-static-deploy
  "Deploy a directory to a remote server folder via FTP"
  [project & args]
  (let [ftp (:ftp project)
        {:keys [host user password port ftp-static-deploy]} ftp
        {:keys [remote-root local-root]} ftp-static-deploy]
    (if-let [url (make-remote-url (assoc (select-keys ftp [:host :user :pass :port]) :protocol "ftp") )]
      (upload-directory url remote-root local-root "")
      (println (str "Bad remote server URL. Params: " ftp)))))
