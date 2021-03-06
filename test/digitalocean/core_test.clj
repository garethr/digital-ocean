(ns digitalocean.core-test
  (:use midje.sweet)
  (:require [digitalocean.core :as core]))

(facts "about url encoding params"
  (core/url-encode-params {:name "A B"}) => {:name "A+B"}
  (core/url-encode-params {:name "Foo"}) => {:name "Foo"}
  (core/url-encode-params {:name "Foo & Bar"}) => {:name "Foo+%26+Bar"})

(facts "about generatoring query params"
  (core/make-query-params "A" "B") => "?client_id=A&api_key=B"
  (core/make-query-params "A" "B" {"C" "D"}) => "?client_id=A&api_key=B&C=D")

(facts "about url-with-params"
  (core/url-with-params "droplets" "A" "B" {"C" "D"})
    => "https://api.digitalocean.com/droplets?client_id=A&api_key=B&C=D")

(facts "about pluralization"
  (core/pluralize 1 "droplet") => "droplet"
  (core/pluralize 2 "droplet") => "droplets")

(facts "about requests with incorrect credentials"
  (core/request "droplets" "FOO" "BAR") =>
    {:error_message "Access Denied", :message "Access Denied", :status "ERROR"})
