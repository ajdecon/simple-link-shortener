# simple-link-shortener

A really, really simple link shortener in Clojure.

NOTE: You should absolutely not use this in
production, as it has no concept of authentication, no rate limiting,
uses an SQLite DB... Basically, it's a toy program. :-)

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To set up the SQLite DB, run:

    lein migratus migrate

To start a web server for the application, run:

    lein ring server

To create a new shortened link, POST a request to the `/` endpoint:

    $ curl -H "Content-Type: application/json" -X POST -d '{"url":"http://www.google.com"}' http://localhost:3000/
    {"result":"tkgbedts"}

The result is the id for the short link. So you can then go to `http://localhost:3000/<id>`
to go to the destination link.

## License

Copyright © 2018 Adam DeConinck.
All rights reserved.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
