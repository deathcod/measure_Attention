<?php
require_once '../../include/config.php';

$obj=(object)$_REQUEST;
$output=testScoreList($obj);

echo $output;

mysqli_close($con);
?>