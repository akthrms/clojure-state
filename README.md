# Stateモナド in Clojure

あまりよくわかってない

```clojure
(let [{:keys [value state]} ((->> (push-stack 4)
                                  (push-stack 5)
                                  (pop-stack)
                                  (:run-state))
                             [3 2 1])]
  (assert (and (= value 5) (= state [4 3 2 1])))))
```
