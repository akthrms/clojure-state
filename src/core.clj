(ns core)

;; State

(defrecord State [run-state])

(defn bind-state [func state]
  (->State (fn [s]
             (let [{:keys [value state]} ((:run-state state) s)]
               ((:run-state (func value)) state)))))

(defn get-state []
  (->State (fn [s] {:value s :state s})))

(defn put-state [state]
  (->State (fn [_] {:value nil :state state})))

;; Stack

(defn pop-stack
  ([] (->State (fn [s] {:value (first s) :state (rest s)})))
  ([stack] (bind-state (fn [_] (pop-stack)) stack)))

(defn push-stack
  ([value] (bind-state (fn [s] (put-state (cons value s))) (get-state)))
  ([value stack] (bind-state (fn [_] (push-stack value)) stack)))

;; Main

(defn -main []
  (let [{:keys [value state]} ((->> (push-stack 4)
                                    (push-stack 5)
                                    (pop-stack)
                                    (:run-state))
                               [3 2 1])]
    (assert (and (= value 5) (= state [4 3 2 1])))))
