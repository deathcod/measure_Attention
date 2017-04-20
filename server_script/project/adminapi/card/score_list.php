<?php
require_once '../../include/config.php';

$obj=(object)$_REQUEST;
$output=cardScoreList($obj);

echo $output;

mysqli_close($con);
?>