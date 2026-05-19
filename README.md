To run the api:

1. First, build the docker image. `docker build -t book-api .`

2. Run the container `docker run -p 8080:8080 book-api`

3. Hit the endpoints as usual via cURL, Postman etc.  `curl http://localhost:8080/api/books`



