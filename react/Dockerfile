FROM node:13.12.0-alpine AS build

WORKDIR /app
COPY . /app
RUN npm install 
ENV PATH /app/node_modules/.bin:$PATH
RUN npm run build


FROM nginx:1.17.1-alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /app/build /usr/share/nginx/html
COPY --from=build /app/index.html /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
