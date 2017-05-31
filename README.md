# vectorql

Generate Graphql query object thing using valid Clojure hiccup-like syntax.

## Setup

Add to `project.clj`:

```clj
[vectorql "0.1.0-SNAPSHOT"]
```

## Usage

```clj
(:require [vecrotql.core :as vectorql])

(vectorql/compile [:query [:folder {:id 123} [:title :id]]])
;; =>  "query { folder (id: 123) { title id } }"

```

## Changes

### 0.1.0

Initial release

## License

Copyright © 2017 Max Vasiliev

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
