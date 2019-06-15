#Crawler
Short description over here.

##Reasoning and description of any trade offs
- ignore subdomains
- ignore infinity scroll
- ignore links from css
- ignore redirects
##Build
`mvn clean package`

##Test
`
mvn test
`
##Run
Start app:
``mvn spring:boot run``

Execute crawler:
``
curl -XPOST localhost:19090/sitemap -d '{"url":"google.com"}'
``



