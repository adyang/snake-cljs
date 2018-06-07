(ns snake-cljs.core
    (:require [snake-cljs.gui :refer [start]]))

(enable-console-print!)

(set! (.-onload js/window) start)
