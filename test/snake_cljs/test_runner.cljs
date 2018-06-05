(ns snake-cljs.test-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [snake-cljs.game-test]))

(enable-console-print!)

(doo-tests 'snake-cljs.game-test)