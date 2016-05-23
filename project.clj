(defproject playgrond "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.8.40" :scope "provided"]
                 [ring "1.4.0"]
                 [ring/ring-defaults "0.2.0"]
                 [bk/ring-gzip "0.1.1"]
                 [ring.middleware.logger "0.5.0"]
                 [compojure "1.5.0"]
                 [environ "1.0.2"]
                 [rum "0.8.3"]
                 [datascript "0.15.0"]
                 [org.clojure/core.async "0.2.374"]
                 [devcards "0.2.1"]]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-environ "1.0.1"]]

  :min-lein-version "2.6.1"

  :source-paths ["src/clj" "src/cljs" "dev"]

  :test-paths ["test/clj"]

  :clean-targets ^{:protect false} [:target-path :compile-path "resources/public/js"]

  :uberjar-name "playground.jar"

  :main playground.server

  :repl-options {:init-ns user}

  :cljsbuild {:builds [{:id "devcards"
                        :source-paths ["src/cljs"]
                        :figwheel {:devcards true}
                        :compiler {:main playground.devcards.devcards
                                   :asset-path "js/compiled/out"
                                   :output-to "resources/public/js/compiled/devcards.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :source-map-timestamp true}}
                       {:id "dev"
                        :source-paths ["src/cljs"]
                        :figwheel true
                        :compiler {:main playground.core
                                   :asset-path "js/compiled/out"
                                   :output-to "resources/public/js/compiled/playground.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :source-map-timestamp true}}]}

  :figwheel {:css-dirs ["resources/public/css"]  ;; watch and update CSS
              :ring-handler user/http-handler
              :server-logfile "log/figwheel.log"}

  :doo {:build "test"}

  :profiles {:dev
             {:dependencies [[figwheel "0.5.2"]
                             [figwheel-sidecar "0.5.2"]
                             [com.cemerick/piggieback "0.2.1"]
                             [org.clojure/tools.nrepl "0.2.12"]]

              :plugins [[lein-figwheel "0.5.2"]
                        [lein-doo "0.1.6"]]}

             :uberjar
             {:source-paths ^:replace ["src/clj"]
              :hooks [leiningen.cljsbuild]
              :omit-source true
              :aot :all
              :cljsbuild {:builds
                          {:app
                           {:source-paths ^:replace ["src/cljs"]
                            :compiler
                            {:optimizations :advanced
                             :pretty-print false}}}}}})
