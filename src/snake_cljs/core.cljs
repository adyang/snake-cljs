(ns snake-cljs.core
    (:require [snake-cljs.gui :refer [start]]))

(enable-console-print!)

(println "This text is printed from src/snake-cljs/core.cljs. Go ahead and edit it and see reloading in action. Really?")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(set! (.-onload js/window) start)

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  (swap! app-state update-in [:__figwheel_counter] inc))

