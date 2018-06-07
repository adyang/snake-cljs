(ns snake-cljs.game)

(def up [0 -1])
(def down [0 1])
(def left [-1 0])
(def right [1 0])

(defn create-snake [length direction]
  {:body (->> (range length)
              (map (fn [x] [x 0]))
              (reverse)
              (vec))
   :direction direction})

(defn create-food [{width :width height :height}]
  {:coord [(rand-int width) (rand-int height)]})

(defn eats? [{[head & _] :body} food]
  (= head (:coord food)))

(defn self-collide? [{[head & remaining-body] :body}]
  (some #(= % head) remaining-body))

(defn add-coords [coordOne coordTwo]
  (mapv + coordOne coordTwo))

(defn move [{:keys [body direction] :as snake}]
  (let [new-head (add-coords (first body) direction)
        new-body (cons new-head (butlast body))]
    (assoc snake :body new-body)))

(defn grow [{:keys [body direction] :as snake}]
  (let [new-head (add-coords (first body) direction)
        new-body (cons new-head body)]
    (assoc snake :body new-body)))

(defn change-direction [{[head second-seg _] :body, :as snake}
                        direction]
  (if (= (add-coords head direction) second-seg)
    snake
    (assoc snake :direction direction)))

(defn update-positions [{:keys [snake food]
                         :as state}
                        board]
  (if (eats? snake food)
    (assoc state :snake (grow snake) :food (create-food board))
    (assoc state :snake (move snake))))
