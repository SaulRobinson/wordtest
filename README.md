# wordtest

#Installation
Run the following commandFrom the project directory {directory}\wordtest mvn package

this will run the tests and build the war file which can be dropped into a tomcat webapps directory.

#Running requests
Navigate to http://localhost:8080/wordtest-0.0.1-SNAPSHOT/swagger-ui/index.html# and use the file option to
select file you wish to analyze.

Or using postman or similar api testing tool you can use the url http://localhost:8080/wordtest-0.0.1-SNAPSHOT/file/analyzeFile
and submit a form-data request with the key file and the file selected.

Or using a curl command as follows

curl --location --request POST 'http://localhost:8080/wordtest-0.0.1-SNAPSHOT/file/analyzeFile' \
--form 'file=@"/C:/Users/saulr/Desktop/bible_daily.txt"'