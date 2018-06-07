(defproject snake-cljs "0.1.0-SNAPSHOT"
  :description "Snake in ClojureScript"
  :url "https://github.com/adyang/snake-cljs"
  :min-lein-version "2.7.1"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [org.clojure/core.async  "0.4.474"]]
  :plugins [[lein-figwheel "0.5.16"]
            [lein-doo "0.1.10"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]]
  :source-paths ["src"]
  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]
                :figwheel {:open-urls ["http://localhost:3449/index.html"]}
                :compiler {:main snake-cljs.core
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/snake_cljs.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true
                           :preloads [devtools.preload]}}
               {:id "test"
                :source-paths ["src" "test"]
                :compiler {:main snake-cljs.test-runner
                           :output-to "resources/public/js/compiled/snake_cljs_test.js"
                           :output-dir "resources/public/js/compiled/out/test"
                           :optimizations :none}}
               {:id "min"
                :source-paths ["src"]
                :compiler {:output-to "resources/public/js/compiled/snake_cljs.js"
                           :main snake-cljs.core
                           :optimizations :advanced
                           :pretty-print false}}]}
  :figwheel {:css-dirs ["resources/public/css"]}
  :profiles {:dev {:dependencies [[binaryage/devtools "0.9.9"]
                                  [figwheel-sidecar "0.5.16"]
                                  [cider/piggieback "0.3.1"]]
                   :source-paths ["src" "dev"]
                   :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}
                   :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                                     :target-path]}})
