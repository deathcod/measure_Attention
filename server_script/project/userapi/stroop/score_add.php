<?php
require_once '../../include/config.php';

usercheck();

if(isset($_REQUEST["json"]) && $_REQUEST["json"]!=""){
    $json=json_decode($_REQUEST["json"],true);

    $output=scoreInsert($json,'stroop');
}else{
    $output='{"status":"failure", "remark":"Invalid or Incomplete json recieved"}';
}

echo $output;

mysqli_close($con);
?>