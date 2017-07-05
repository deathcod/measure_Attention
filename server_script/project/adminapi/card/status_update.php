<?php
require_once '../../include/config.php';

if(isset($_REQUEST["game_id"]) && $_REQUEST["game_id"]!=""){
	$game_id=numOnly($_REQUEST["game_id"]);

	$query="select * from `card` where `game_id`='{$game_id}'";
	$result=mysqli_query($con,$query);
	if(mysqli_num_rows($result)==1){
		$row=mysqli_fetch_assoc($result);
		$status=$row["status"]=="1"?"0":"1";
		$query="update `card` set `status`='{$status}' where `game_id`='{$game_id}'";
		$result=mysqli_query($con,$query);
		if($result){
			$output='{"status":"success", "remark":"Successfully updated"}';
		}else{
			$output='{"status":"failure", "remark":"Something is wrong"}';
		}
	}else{
		$output='{"status":"failure", "remark":"Something is wrong with data"}';
	}
}else{
	$output='{"status":"failure", "remark":"Invalid or Incomplete game id recieved"}';
}

echo $output;

mysqli_close($con);
?>