<?php
require_once '../../include/config.php';

usercheck();

if(isset($_REQUEST["score"]) && $_REQUEST["score"]!=""){
    if(isset($_REQUEST["time"]) && $_REQUEST["time"]!=""){
        $user_id=numOnly($_REQUEST["user_id"]);
        $score=(int)filter_var($_REQUEST["score"],FILTER_SANITIZE_STRING);
        $time=(double)filter_var($_REQUEST["time"],FILTER_SANITIZE_STRING);
        $avg_time=$time/10;

        $datetime=date("Y-m-d H:i:s");

        $query="insert into `stroop` (`user_id`,`score`,`time`,`avg_time`,`datetime`) values ('{$user_id}', '{$score}', '{$time}', '{$avg_time}', '{$datetime}')";
        $result=mysqli_query($con,$query);
        if($result){
            $output='{"status":"success", "remark":"Successfully added"}';
        }else{
            $output='{"status":"failure", "remark":"Something is wrong"}';
        }
    }else{
        $output='{"status":"failure", "remark":"Invalid or Incomplete time recieved"}';
    }
}else{
  $output='{"status":"failure", "remark":"Invalid or Incomplete score recieved"}';
}

echo $output;

mysqli_close($con);
?>