(ns snake-cljs.game-test
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]
            [snake-cljs.game :refer [create-snake create-food eats? self-collide? move grow change-direction update-direction update-positions up down left right]]))

(deftest create-snake-test
  (is (= {:body      [[2 0] [1 0] [0 0]]
          :direction right} (create-snake 3 right))))

(deftest create-food-test
  (let [food (create-food {:width 50 :height 100})]
    (is (< (first (:coord food)) 50))
    (is (< (second (:coord food)) 100))))

(deftest eats?-test
  (let [snake {:body [[1 2] [1 3] [1 4]]}
        food-in-head {:coord [1 2]}
        food-outside-head {:coord [1 3]}]
    (is (eats? snake food-in-head))
    (is (not (eats? snake food-outside-head)))))

(deftest self-collide?-test
  (is (self-collide? {:body [[1 2] [1 3] [0 3] [0 2] [1 2]]}))
  (is (not (self-collide? {:body [[1 2] [1 3] [0 3] [0 2]]}))))

(deftest move-test
  (is (= [[3 0] [2 0] [1 0]] (:body (move (create-snake 3 right)))))
  (is (= [[2 1] [2 0] [1 0]] (:body (move (create-snake 3 down))))))

(deftest grow-test
  (is (= [[3 0] [2 0] [1 0] [0 0]] (:body (grow (create-snake 3 right))))))

(deftest change-direction-test
  (is (= down (:direction (change-direction (create-snake 3 right) down))))
  (is (= right (:direction (change-direction (create-snake 3 right) left)))))

(deftest update-direction-test
  (let [state {:snake (create-snake 3 right)
               :food {:coord [3 3]}}]
    (is (= up (get-in (update-direction state up) [:snake :direction])))))

(deftest update-positions-move-snake-test
  (let [state {:snake (create-snake 3 right)
               :food {:coord [3 3]}}
        updated-state (update-positions state {:width 50 :height 100})]
    (is (= [[3 0] [2 0] [1 0]] (:body (:snake updated-state))))
    (is (= [3 3] (:coord (:food updated-state))))))

(deftest update-positions-grow-snake-test
  (let [state {:snake (create-snake 3 right)
               :food {:coord [2 0]}}
        updated-state (update-positions state {:width 50 :height 100})]
    (is (= [[3 0] [2 0] [1 0] [0 0] (:body (:snake updated-state))]))
    (is (not (identical? (:coord (:food state)) (:coord (:food updated-state)))))))
