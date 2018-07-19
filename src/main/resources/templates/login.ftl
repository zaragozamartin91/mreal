<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />

    <#if message??>
        <meta name="message" content="${message}" />
    </#if>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="/css/bootstrap.min.css"/>

    <style>
        html, body {
            height: 100%;
            width: 100%;
            margin: 0;
        }

        body {
            display: flex;
        }

        #root {
            width: 50%;
            margin: auto;
        }
    </style>

    <title>Login</title>
</head>
<body>

<div id="root"></div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="/js/jquery.3.3.1.min.js"></script>
<script src="/js/bootstrap.bundle.min.js"></script>
<script src="/js/common.bundle.js"></script>
<script src="/js/login.bundle.js"></script>
</body>
</html>