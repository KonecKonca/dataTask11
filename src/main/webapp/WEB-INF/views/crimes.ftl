<html>
<head>
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"/>
    <title>Crimes list</title>
</head>
<body>

    <h2>Crimes list</h2><br>

    <#list crimes as crime>
        <h5>${crime}</h5>
    </#list>

</body>
</html>
