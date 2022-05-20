# apiUsuarios
apiUsuarios
Pasos:
1.- Doble click sobre el archivo start.bat
2.- Abrir proyecto Usuarios.postman_collection.json en postman
3.- agregar 1 usuario con Request AgregarUsuario
4.- rescatar token del response del agregarUsuario
5.- Lista usuarios ingresando token en el Header Authorization
6.- Abrir navegador con la siguiente ruta : http://localhost:8080/h2-ui
7.- en el campo JDBC URL, ingresar lo siguiente: jdbc:h2:mem:testdb
8.- Revision de registros creados en el bd:
	select * from usuario
	select * from phone
