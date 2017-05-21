<?php
require_once '../../include/config.php';

$obj=(object)$_REQUEST;
$output=chessScoreList($obj);

echo $output;

mysqli_close($con);
?>