FROM node:16
WORKDIR /usr/src/app
COPY package.json ./
RUN npm install
COPY * ./
RUN mkdir static
COPY ./static/* static/
EXPOSE 8080
CMD ["node","server.js"]
