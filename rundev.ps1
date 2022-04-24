Set-Item -Path Env:JDBCURL -Value "url_connection"
Set-Item -Path Env:USERNAME -Value "user"
Set-Item -Path Env:SENHA -Value "password"

mvnw spring-boot:run
