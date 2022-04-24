Set-Item -Path Env:JDBCURL -Value "url_connection"
Set-Item -Path Env:USERNAME -Value "user"
Set-Item -Path Env:SENHA -Value "password"

mvnw clean package -DskipTests
docker image build -t tools-api .

docker rm -f tools-api
docker run -d --name tools-api -it --rm -p 3000:3000 `
    -e JDBCURL=$env:JDBCURL `
    -e USERNAME=$env:USERNAME `
    -e SENHA=$env:SENHA `
    tools-api
