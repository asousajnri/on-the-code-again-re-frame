(ns on-the-code-again-re-frame.events
  (:require [re-frame.core :as rf]
            [on-the-code-again-re-frame.db :as db]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]
            [day8.re-frame.tracing :refer-macros [fn-traced]]))

(rf/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
   db/default-db))

(rf/reg-event-db
  ::update-name
  (fn [db [_ val]]
    (assoc db :name val)))

(rf/reg-event-db
  ::fetch-users-success
  (fn [db [_ {:keys [data]}]]
    (-> db
        (assoc :loading false)
        (assoc :users data))))

(rf/reg-event-fx
  ::fetch-users
  (fn [{:keys [db]} _]
    {:db   (assoc db :loading true)
     :http-xhrio {:method          :get
                  :uri             "https://reqres.in/api/users?page=1"
                  :timeout         8000                                           ;; optional see API docs
                  :response-format (ajax/json-response-format {:keywords? true})  ;; IMPORTANT!: You must provide this.
                  :on-success      [::fetch-users-success]
                  :on-failure      [:bad-http-result]}}))
