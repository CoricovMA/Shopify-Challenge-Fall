# Shopify Challenge Fall Backend Internship
The project is divided in two folders - Backend and frontend, indicating each part of it.

Ideally, an image repository would be a CDN (content delivery/distribution network), which would be a dedicated data storage server.

For the scope of this challenge, the pictures are saved locally, and accessed by their URL: url/picture/<picture-name>.

The frontend retrieves a list of the stored pictures, and renders them using the above URL.


## Frontend

The frontend is a basic react app which can be run by doing ```npm start``` while in the root frontend directory.

The frontend makes API calls to the backend using the axios client. <br/>
These API calls, for the scope of this challenge, are made to the default ```http://localhost:8080/path```, assuming the backend instance is running locally.

In the case that the backend instance is deployed, this URL can be changed in ```frontend/src/Config/api.js```.

## Backend

The backend is a Java Spring application. It can be run from an IDE, with a java -jar command, or by 
building the docker image using the provided dockerfile.

To run using docker, execute the following commands in the root backend directory:
1. ```mvn clean isntall```
2. ```docker build -t <image-tag> .``` where "image-tag" is the desired tag for this image
3. ```docker run --name <container-name> -d -p 8080:8080 <image-tag> ``` where "container-name" is the desired container name

This would result in the docker container running, barring any issues (existing containers with the same name, etc.) .


### Live Version

For a live version, please click [here](https://shopify-fall-challenge.herokuapp.com/gallery)