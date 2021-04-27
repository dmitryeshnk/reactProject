<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Admin</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    </head>
    <style>
          body {
              width: 50% !important;
              margin: 0 auto !important;
          }
    </style>
    <body>
        <div class="alert alert-success" role="alert">
            <form action="/22_webservices/logout" method="POST">
                <input type="submit" value="Sign Out"/>
            </form>
        </div>
        <table class="table">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Username</th>
              <th scope="col">Email</th>
              <th scope="col">Password</th>
              <th scope="col">Birthday</th>
              <th scope="col">Gender</th>
              <th scope="col">Roles</th>
              <th scope="col">Assign role</th>
              <th scope="col">Delete</th>
            </tr>
          </thead>
          <tbody>
          <c:forEach var="user" items="${users}">
            <tr>
              <th scope="row"><c:out value="${user.id}"/></th>
              <td><c:out value="${user.username}"/></td>
              <td><c:out value="${user.email}"/></td>
              <td><c:out value="${user.password}"/></td>
              <td><c:out value="${user.birthday}"/></td>
              <td><c:out value="${user.gender.name}"/></td>
              <td><c:forEach var="role" items="${user.roles}">
                <c:out value="${role.name}"/>
              </c:forEach></td>
              <form method="post" action="/22_webservices/admin/change">
                <td><button name="id" value="${user.id}" type="submit" class="btn btn-primary">change</button></td>
              </form>
              <form method="post" action="/22_webservices/admin/delete">
                <td><button name="id" value="${user.id}" type="submit" class="btn btn-primary">delete</button></td>
              </form>
            </tr>
            </c:forEach>
          </tbody>
        </table>
        <a href="/admin/addUser">Add User</a>
        <a href="/admin/editUser">Edit Users</a>
    </body>
</html>

