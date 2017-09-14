(ns leiningen.ftp-static-deploy-test
  (:require [clojure.test :refer :all]
            [leiningen.ftp-static-deploy :refer :all]))

(deftest make-remote-url-test
  (testing "with ftp protocol"
    (is (= "ftp://u:p@h:1" (make-remote-url {:protocol "ftp" :user "u" :pass "p" :host "h" :port 1})))
    (is (= nil (make-remote-url {:protocol "unknown" :user "u" :pass "p" :host "h" :port 1})))))

(deftest trim-path-separator-test
  (testing "removes leading and trailing path separators"
    (is (= "root" (trim-path-separator "/root/")))
    (is (= "root" (trim-path-separator "/root")))
    (is (= "root" (trim-path-separator "root/"))))
  (testing "does not change string with no trailing or leading path separator"
    (is (= "root" (trim-path-separator "root"))))
  (testing "does not change string with path separator in it"
    (is (= "r/p" (trim-path-separator "r/p")))))

(deftest make-remote-path-test
  (testing "with correct path parts"
    (is (= "/mydir/myfile" (make-remote-path "/" "mydir" "myfile")))
    (is (= "/root/mydir/myfile" (make-remote-path "/root" "/mydir" "myfile")))
    (is (= "/root/mydir/myfile" (make-remote-path "/root/" "/mydir/" "/myfile"))))
  (testing "with empty dir part"
    (is (= "/myfile" (make-remote-path "/" "" "myfile")))
    (is (= "/root/myfile" (make-remote-path "/root" "" "myfile")))))
