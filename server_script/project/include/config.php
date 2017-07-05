<?php
    session_start();

    $server="localhost";
    $username="root";
    $password="";
    $database="project";
       
    date_default_timezone_set('Asia/Kolkata');
    $con=mysqli_connect($server,$username,$password,$database) or die ("could not connect to mysql");

    $HOSTNAME="http://localhost/";
    $DATE_FORMAT="j M Y, g:i:s A";
    $SERVER_IP=gethostbyname(gethostname());

    require_once 'function.php';
?>