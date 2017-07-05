<?php
require_once '../../include/config.php';

$obj=(object)$_REQUEST;
$output=clickList($obj);

echo $output;

mysqli_close($con);
?>