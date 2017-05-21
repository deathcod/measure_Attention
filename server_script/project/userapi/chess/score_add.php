<?php
require_once '../../include/config.php';

usercheck();

if(isset($_REQUEST["score_1"]) && $_REQUEST["score_1"]!=""){
    if(isset($_REQUEST["time_1"]) && $_REQUEST["time_1"]!=""){
        if(isset($_REQUEST["score_2"]) && $_REQUEST["score_2"]!=""){
            if(isset($_REQUEST["time_2"]) && $_REQUEST["time_2"]!=""){
                if(isset($_REQUEST["score_3"]) && $_REQUEST["score_3"]!=""){
                    if(isset($_REQUEST["time_3"]) && $_REQUEST["time_3"]!=""){
                        if(isset($_REQUEST["score_4"]) && $_REQUEST["score_4"]!=""){
                            if(isset($_REQUEST["time_4"]) && $_REQUEST["time_4"]!=""){
                                $user_id=numOnly($_REQUEST["user_id"]);
                                $score_1=(int)filter_var($_REQUEST["score_1"],FILTER_SANITIZE_STRING);
                                $time_1=(int)filter_var($_REQUEST["time_1"],FILTER_SANITIZE_STRING);
                                $score_2=(int)filter_var($_REQUEST["score_2"],FILTER_SANITIZE_STRING);
                                $time_2=(int)filter_var($_REQUEST["time_2"],FILTER_SANITIZE_STRING);
                                $score_3=(int)filter_var($_REQUEST["score_3"],FILTER_SANITIZE_STRING);
                                $time_3=(int)filter_var($_REQUEST["time_3"],FILTER_SANITIZE_STRING);
                                $score_4=(int)filter_var($_REQUEST["score_4"],FILTER_SANITIZE_STRING);
                                $time_4=(int)filter_var($_REQUEST["time_4"],FILTER_SANITIZE_STRING);

                                $datetime=date("Y-m-d H:i:s");

                                $query="insert into `chess` (`user_id`,`score_1`,`time_1`,`score_2`,`time_2`,`score_3`,`time_3`,`score_4`,`time_4`,`datetime`) values ('{$user_id}', '{$score_1}', '{$time_1}', '{$score_2}', '{$time_2}', '{$score_3}', '{$time_3}', '{$score_4}', '{$time_4}', '{$datetime}')";
                                $result=mysqli_query($con,$query);
                                if($result){
                                    $output='{"status":"success", "remark":"Successfully added"}';
                                }else{
                                    $output='{"status":"failure", "remark":"Something is wrong"}';
                                }
                            }else{
                                $output='{"status":"failure", "remark":"Invalid or Incomplete time level 4 recieved"}';
                            }
                        }else{
                          $output='{"status":"failure", "remark":"Invalid or Incomplete score level 4 recieved"}';
                        }
                    }else{
                        $output='{"status":"failure", "remark":"Invalid or Incomplete time level 3 recieved"}';
                    }
                }else{
                  $output='{"status":"failure", "remark":"Invalid or Incomplete score level 3 recieved"}';
                }
            }else{
                $output='{"status":"failure", "remark":"Invalid or Incomplete time level 2 recieved"}';
            }
        }else{
          $output='{"status":"failure", "remark":"Invalid or Incomplete score level 2 recieved"}';
        }
    }else{
        $output='{"status":"failure", "remark":"Invalid or Incomplete time level 1 recieved"}';
    }
}else{
  $output='{"status":"failure", "remark":"Invalid or Incomplete score level 1 recieved"}';
}

echo $output;

mysqli_close($con);
?>