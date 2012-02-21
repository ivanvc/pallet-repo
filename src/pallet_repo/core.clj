(ns pallet-repo.core
  (:require pallet.repl)
  (:use
    [pallet.phase :only [phase-fn]]
    [pallet.utils :only [make-user]]
    [pallet.crate.automated-admin-user :only [automated-admin-user]]
    [pallet.crate.git :only [git]]
    [pallet.crate.ruby :only [ruby]]
    [pallet.crate.nginx :only [nginx]]))

(pallet.repl/use-pallet)

(def local
  (pallet.compute/compute-service
    "node-list"
    :node-list [["vagrant" "vagrant" "33.33.33.10" :ubuntu]]))

(def vagrant-user
  (make-user "vagrant" :password "vagrant"))

(def ps-front
  (group-spec "vagrant"
              :phases {:bootstrap (phase-fn (automated-admin-user))
                       :configure (phase-fn
                                    (git)
                                    (nginx :version "1.0.12")
                                    (ruby "1.9.3-p125"))}))
