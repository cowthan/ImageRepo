git add ./
git commit -m "add picture and refresh index.html"
git push origin gh-pages
git checkout master
git merge gh-pages
git add ./
git commit -m "no"
git push origin master