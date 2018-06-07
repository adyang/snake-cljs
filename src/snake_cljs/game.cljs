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

(defn generate-new-head [[head _] direction {width :width height :height}]
  (let [coord (add-coords head direction)]
    (mapv mod coord [width height])))

(defn move [{:keys [body direction] :as snake} board]
  (let [new-head (generate-new-head body direction board)
        new-body (cons new-head (butlast body))]
    (assoc snake :body new-body)))

(defn grow [{:keys [body direction] :as snake} board]
  (let [new-head (generate-new-head body direction board)
        new-body (cons new-head body)]
    (assoc snake :body new-body)))

(defn change-direction [{[head second-seg _] :body, :as snake}
                        direction]
  (if (= (add-coords head direction) second-seg)
    snake
    (assoc snake :direction direction)))

(defn update-direction [state direction]
  (let [snake (:snake state)]
    (assoc state :snake (change-direction snake direction))))

(defn update-positions [{:keys [snake food]
                         :as state}
                        board]
  (if (eats? snake food)
    (assoc state :snake (grow snake board) :food (create-food board))
    (assoc state :snake (move snake board))))
