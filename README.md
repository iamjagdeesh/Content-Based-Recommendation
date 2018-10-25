# Content Based Recommendation

## CSE 591: Adaptive Web (Assignment 2)

## Description

## Steps to run the application

### To crawl the web
1. CD into the "ScrapeWiki" folder.
2. Run "mvn clean install" (Maven has to be installed)
3. Run "java -jar target/ScrapeWiki-0.0.1-SNAPSHOT.jar".
4. The application now crawls the web and writes into the directory names "crawledWikiContent" folder.
5. The application exits itself after crawling. It takes a few seconds as it has to crawl the web.
6. Make sure the "crawledWikiContent" folder is present in the root befpre you do the above steps.

### Java environment for backend service
1. CD into the "RestLuceneRecommendation" folder.
2. Run "mvn clean install" (Maven has to be installed)
3. Run "java -jar target/RestLuceneRecommendation-0.0.1-SNAPSHOT.jar" and the backend server will be up and running on http://localhost:8080

### Front-end react application
1. Yarn has to be installed (https://yarnpkg.com/en/docs/install#windows-stable)
2. CD into the "webapp-recommender" folder.
3. Run "yarn install"
4. Run "yarn start" and now the front-end application will be running at http://localhost:3000/

### Usage
1. Go ahead and hit the application at http://localhost:3000/
2. Click "load recommendations" button.
3. It takes a few seconds (4-5) as it has to index and get recommendations for all 10 posts.
4. You can view the posts and the recommendations.
5. You can see the explanation as well in the UI.