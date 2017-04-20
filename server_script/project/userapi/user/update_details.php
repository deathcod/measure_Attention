<?php
require_once '../../include/config.php';

// connection();

if(isset($_REQUEST["name"]) && $_REQUEST["name"]!=""){
	if(isset($_REQUEST["ip"]) && $_REQUEST["ip"]!=""){
        $name=filter_var($_REQUEST["name"],FILTER_SANITIZE_STRING);
        $ip=filter_var($_REQUEST["ip"],FILTER_SANITIZE_STRING);
        
        if(!filter_var($ip, FILTER_VALIDATE_IP) === false){
            $query="select * from user where name='{$name}' and ip='{$ip}'";
            $result=mysqli_query($con,$query);

            if(mysqli_num_rows($result)==1){
                //one data, so get the detail
                $row=mysqli_fetch_assoc($result);
                $user_id=$row["user_id"];

                $output=userDetails($user_id);
            }elseif(mysqli_num_rows($result)==0){
                //no data available, so insert into table
                $query="SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='project' AND TABLE_NAME='user'";
                $result=mysqli_query($con,$query);
                if($result){
                    $row=mysqli_fetch_assoc($result);
                    $token=md5($row["AUTO_INCREMENT"]);
                    $datetime=date("Y-m-d H:i:s");

                    $query="insert into `user` (`token`,`name`,`ip`,`datetime`) values ('{$token}', '{$name}', '{$ip}', '{$datetime}')";
                    $result=mysqli_query($con,$query);
                    if($result){
                        $user_id=mysqli_insert_id($con);
                        $output=userDetails($user_id);
                    }else{
                        $output='{"status":"failure", "remark":"Something is wrong"}';
                    }
                }else{
                    $output='{"status":"failure", "remark":"Something is wrong"}';
                }
            }else{
                //through error
                $output='{"status":"failure", "remark":"Something is wrong with data"}';
            }
        }else{
            $output='{"status":"failure", "remark":"Invalid IP Address recieved"}';
        }
	}else{
	  $output='{"status":"failure", "remark":"Invalid or Incomplete IP address recieved"}';
	}
}else{
  $output='{"status":"failure", "remark":"Invalid or Incomplete name recieved"}';
}

echo $output;

mysqli_close($con);
?>