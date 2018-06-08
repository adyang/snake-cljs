(ns snake-cljs.gui
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:require [goog.dom :as dom]
            [goog.graphics :as graphics]
            [goog.events :as events]
            [cljs.core.async :refer [<! chan timeout]]
            [snake-cljs.game :refer [create-snake create-food update-positions update-direction self-collide? up down left right]]))

(def fps 20)
(def scale 10)
(def board {:width 55 :height 35})
(def snake-length 5)
(def snake-color (graphics/SolidFill. "blue"))
(def food-color (graphics/SolidFill. "red"))

(defn create-graphics [elem]
  (doto (graphics/createGraphics "100%" "100%")
    (.render elem)))

(defn scale-to-canvas [[x y] scale]
  (map #(* % scale) [x y 1 1]))

(defn draw-square [graphics coord color]
  (let [[x y width height] (scale-to-canvas coord scale)]
    (.drawRect graphics x y width height nil color)))

(defn draw-snake [graphics {body :body}]
  (doall (map #(draw-square graphics % snake-color) body)))

(defn draw-food [graphics {coord :coord}]
  (draw-square graphics coord food-color))

(defn dir-vec [event]
  (case (.-key event)
    "ArrowUp" up
    "ArrowDown" down
    "ArrowLeft" left
    "ArrowRight" right
    nil))

(defn new-game-state []
  {:snake (create-snake snake-length right)
   :food (create-food board)})

(defn create-direction-handler [state]
  (fn [event]
    (when-let [direction (dir-vec event)]
      (swap! state update-direction direction)
      (.preventDefault event))))

(defn draw [graphics state-snapshot]
  (.clear graphics)
  (draw-snake graphics (:snake state-snapshot))
  (draw-food graphics (:food state-snapshot)))

(defn check-end-game [state]
  (when (self-collide? (:snake @state))
    (js/alert "Game Over!")
    (reset! state (new-game-state))))

(defn game-loop [graphics state]
  (go-loop []
           (draw graphics @state)
           (<! (timeout (/ 1000 fps)))
           (swap! state update-positions board)
           (check-end-game state)
           (recur)))

(defn start []
  (let [canvas (dom/getElement "canvas")
        graphics (create-graphics canvas)
        state (atom (new-game-state))]
    (events/listen js/document "keydown" (create-direction-handler state))
    (game-loop graphics state)))
