Domain crawler
====================
The goal of this application is generating sitemap. The crawler is limited to one domain. Sitemmap contains list of web pages. Each web page contains list of: domain links, links to static content, links to external sites.

The crawler does not support:
* subdomains
* infinity scroll
* redirects

For given input:
```json
{"url":"https://www.google.com"}
```
returns:
```json
{
  "id": "d87cfb82-7fe1-4f5d-abe6-b21fb3011260",
  "webPageDocuments": [
    {
      "url": "https://buildit.wiprodigital.com/",
      "domainLinks": [
        "https://buildit.wiprodigital.com/about/",
        "https://buildit.wiprodigital.com/careers/",
        "https://buildit.wiprodigital.com/locations/",
        "https://buildit.wiprodigital.com/"
      ],
      "staticLinks": [
        "https://buildit.wiprodigital.com/scripts/bundle.min.js",
        "https://www.googletagmanager.com/ns.html?id=GTM-WC9XF5V&gtm_auth=HCE83Z7j2NN-BpF0vcvrvQ&gtm_preview=env-2&gtm_cookies_win=x",
        "https://buildit.wiprodigital.com/styles/style.css",
        "https://buildit.wiprodigital.com/apple-touch-icon.png",
        "https://buildit.wiprodigital.com/favicon-32x32.png",
        "https://buildit.wiprodigital.com/favicon-16x16.png",
        "https://buildit.wiprodigital.com/site.webmanifest",
        "https://buildit.wiprodigital.com/safari-pinned-tab.svg"
      ],
      "externalLinks": [
        "https://medium.com/buildit",
        "https://medium.com/buildit/",
        "https://www.instagram.com/buildit_tech",
        "https://twitter.com/buildit_tech",
        "https://www.linkedin.com/company/buildit.",
        "https://github.com/buildit",
        "https://wiprodigital.com/privacy-policy"
      ]
    },
    {
      "url": "https://buildit.wiprodigital.com/about/",
      "domainLinks": [
        "https://buildit.wiprodigital.com/",
        "https://buildit.wiprodigital.com/careers/",
        "https://buildit.wiprodigital.com/locations/"
      ],
      "staticLinks": [
        "https://buildit.wiprodigital.com/images/spaceman.svg",
        "https://buildit.wiprodigital.com/images/cube.svg",
        "https://buildit.wiprodigital.com/images/bull.svg",
        "https://buildit.wiprodigital.com/images/r2d2.svg",
        "https://buildit.wiprodigital.com/scripts/bundle.min.js",
        "https://www.googletagmanager.com/ns.html?id=GTM-WC9XF5V&gtm_auth=HCE83Z7j2NN-BpF0vcvrvQ&gtm_preview=env-2&gtm_cookies_win=x",
        "https://buildit.wiprodigital.com/styles/style.css",
        "https://buildit.wiprodigital.com/apple-touch-icon.png",
        "https://buildit.wiprodigital.com/favicon-32x32.png",
        "https://buildit.wiprodigital.com/favicon-16x16.png",
        "https://buildit.wiprodigital.com/site.webmanifest",
        "https://buildit.wiprodigital.com/safari-pinned-tab.svg"
      ],
      "externalLinks": [
        "https://medium.com/buildit",
        "https://medium.com/buildit/technology/home",
        "https://medium.com/buildit/design-systems/home",
        "https://medium.com/buildit/org-change/home",
        "https://www.wipro.com/",
        "https://wiprodigital.com/",
        "https://designit.com",
        "https://www.instagram.com/buildit_tech",
        "https://twitter.com/buildit_tech",
        "https://www.linkedin.com/company/buildit.",
        "https://github.com/buildit",
        "https://wiprodigital.com/privacy-policy"
      ]
    }
  ]
}
```

Solution
------
#####High level solution

![screen](https://user-images.githubusercontent.com/15219684/59570442-6a0a4f00-9098-11e9-8f4e-53035b1e51aa.jpg)

New CrawlDomainTask task instance is created for each request. Page processing uses thread pool to fetch pages. 
Processing will be terminated if:
* limit of pages has been exceeded
* no more pages to fetch

Crawler generate sitemap for given url. Crawler use limit of number of pages to fetch to prevent timeouts and OutOfMemoryException.

#####Page fetching

Application use jsoup library (https://jsoup.org/) to fetch and parse pages. For each page, crawler extract links:
* static content
* links:
    * domain - with the same domain
    * external - links to different domain (including subdomains)

Crawler process only URLs with protocols:
* https
* http

Different protocols will be skip.

####Integration tests
Application use FileSystemPageCrawler in test environment. This class fetch pages from disk instead if the Internet. All html files are stored in `src/test/resources/pages`.

If you want to add new page for testing purpose, you should add new html file and update mappings url->file (FileSystemPageCrawler::pages)


System requirements
------
To build and run you need to have Java 8 na Maven 3 installed and configured correctly.

Configuration
------
You can configure basic parameters for building a site map. The configuration is saved in the properties file - `src/main/resources/application.properties`.

Key | Description | Default value
--- | --- | ---
app.fetch-page-client.threads.pool-size | Number of threads used to featch pages. | 1
app.fetch-page-client.fetch-timeout-in-seconds | Timeout (in seconds) of fetching a single page | 10

Build
------
```
./mvn clean install
```

Run
------
```
java -jar target/domain-crawler.jar
```

Api
------
#####Generate site map

```
POST /sitemaps
{
   "url":"https://www.google.com",
   "limit":20
}
```

Input field | Description | Required | Default value
--- | --- | --- | ---
url | Url of root page | Yes | -
limit | The limit of pages to be fetch | No | 50


Usage example:
```
curl -d '{"url":"https://www.google.com"}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:19090/sitemaps 
```
Future extensions
------
Possible future extensions:
* Generate sitemap asynchronous and return the id of the task. Then get sitemap based on result of task. 
This approach allows us to remove problem wilth http client timeouts (when client wait for the response from the api).
* Add repository for sitemaps, then allow to get sitemap based on id
* Return sitemap with pagination and removed the limit of fetch pages
* Use asynchronous http
* Provide api throttling
* Add security layer
* Api documentation (swagger)
* Api versioning
* Use proxy servers to bypass blocks
* Add User-Agent Headers to pretend to be a browser
* Add dockerfile


