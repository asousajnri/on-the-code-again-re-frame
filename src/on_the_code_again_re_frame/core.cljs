(ns on-the-code-again-re-frame.core
  (:require [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [on-the-code-again-re-frame.events :as events]
            [on-the-code-again-re-frame.views :as views]
            [on-the-code-again-re-frame.config :as config]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (rf/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn init []
  (rf/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
