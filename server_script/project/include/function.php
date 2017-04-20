<?php

function clean($input){
  return preg_replace('/[^A-Za-z0-9 ]/', '', $input); // Removes special chars.
}

function rinse($input){
    return preg_replace('/[^A-Za-z0-9\-,@.\ ]/', '', $input); // Removes special chars.
}

function numOnly($input){
  return preg_replace('/[^0-9 ]/', '', $input); // Removes special chars.
}

function securityToken(){
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $randstring = '';
    for ($i = 0; $i < 32; $i++) {
        $randstring.=$characters[rand(0, strlen($characters)-1)];
    }
    return $randstring;
}

function usercheck(){
    $output="";
    global $con;

    if(isset($_REQUEST["user_id"]) && isset($_REQUEST["token"])){    
        $query="select `token` from `user` where `user_id`='".$_REQUEST["user_id"]."'";
        $result=mysqli_query($con,$query);
        $row=mysqli_fetch_assoc($result);

        if($row["token"]==$_REQUEST["token"]){
            $output.='{"status":"success"}';
        }else
            $output.='{"status":"failure","remark":"Incorrect token recieved"}';
    }else{
        $output.='{"status":"failure","remark":"User id or token is missing"}';
    }

    $obj=json_decode($output,true);

    if($obj['status']!="success")
        die($output);
}

function admincheck(){
    if(!isset($_SESSION["user_type"])=="admin"){
        die("You are not authorized for this request");
    }
}

function stroopScoreList($obj){
    global $con,$DATE_FORMAT;

    $score=array();

    $query="select u.token,s.score,s.time,s.avg_time,s.datetime from `stroop` s left join `user` u on s.user_id=u.user_id where ";
     
    if(isset($obj->search)  && $obj->search!=""){
        $search = clean($obj->search);
        $query.="( u.`token` like '%".$search."%' or s.`score` like '%".$search."%' or s.`time` like '%".$search."%' ) and ";
    }

    $query.="1 order by s.`score_id` desc ";

    if(isset($obj->limit) && $obj->limit!=0){
        $limit=$obj->limit;
    }else{
        $limit=10;
    }

    if(isset($obj->page) && $obj->page!=0){
        $page=$obj->page;
    }else{
        $page=1;
    }

    $query.=" limit {$limit} offset ".(($page-1)*$limit);
    $result = mysqli_query($con,$query);

    if(mysqli_num_rows($result) > 0){
        while ($row = mysqli_fetch_assoc($result))
        {
            $row["datetime"]=date($DATE_FORMAT,strtotime($row["datetime"]));
            $score[] = $row;
        }
        $output='{"status":"success", "score":';
        $output.=json_encode($score);
        $output.="}";
    }else{
         $output='{"status":"failure","remark":"No score data found"}';
    }
    return $output;
}

function testScoreList($obj){
    global $con,$DATE_FORMAT;

    $score=array();

    $query="select u.token,t.score_0,t.time_0,t.score_1,t.time_1,t.score_2,t.time_2,t.score_3,t.time_3,t.score_4,t.time_4,t.datetime from `test` t left join `user` u on t.user_id=u.user_id where ";
     
    if(isset($obj->search)  && $obj->search!=""){
        $search = clean($obj->search);
        $query.="( u.`token` like '%".$search."%' or t.`score_0` like '%".$search."%' or t.`time_0` like '%".$search."%' or t.`score_1` like '%".$search."%' or t.`time_1` like '%".$search."%' or t.`score_2` like '%".$search."%' or t.`time_2` like '%".$search."%' or t.`score_3` like '%".$search."%' or t.`time_3` like '%".$search."%' or t.`score_4` like '%".$search."%' or t.`time_4` like '%".$search."%' ) and ";
    }

    $query.="1 order by t.`score_id` desc ";

    if(isset($obj->limit) && $obj->limit!=0){
        $limit=$obj->limit;
    }else{
        $limit=10;
    }

    if(isset($obj->page) && $obj->page!=0){
        $page=$obj->page;
    }else{
        $page=1;
    }

    $query.=" limit {$limit} offset ".(($page-1)*$limit);
    $result = mysqli_query($con,$query);

    if(mysqli_num_rows($result) > 0){
        while ($row = mysqli_fetch_assoc($result))
        {
            $row["datetime"]=date($DATE_FORMAT,strtotime($row["datetime"]));
            $score[] = $row;
        }
        $output='{"status":"success", "score":';
        $output.=json_encode($score);
        $output.="}";
    }else{
         $output='{"status":"failure","remark":"No score data found"}';
    }
    return $output;
}

function chessScoreList($obj){
    global $con,$DATE_FORMAT;

    $score=array();

    $query="select u.token,c.score_1,c.time_1,c.score_2,c.time_2,c.score_3,c.time_3,c.score_4,c.time_4,c.datetime from `chess` c left join `user` u on c.user_id=u.user_id where ";
     
    if(isset($obj->search)  && $obj->search!=""){
        $search = clean($obj->search);
        $query.="( u.`token` like '%".$search."%' or c.`score_0` like '%".$search."%' or c.`time_0` like '%".$search."%' or c.`score_1` like '%".$search."%' or c.`time_1` like '%".$search."%' or c.`score_2` like '%".$search."%' or c.`time_2` like '%".$search."%' or c.`score_3` like '%".$search."%' or c.`time_3` like '%".$search."%' or c.`score_4` like '%".$search."%' or c.`time_4` like '%".$search."%' ) and ";
    }

    $query.="1 order by c.`score_id` desc ";

    if(isset($obj->limit) && $obj->limit!=0){
        $limit=$obj->limit;
    }else{
        $limit=10;
    }

    if(isset($obj->page) && $obj->page!=0){
        $page=$obj->page;
    }else{
        $page=1;
    }

    $query.=" limit {$limit} offset ".(($page-1)*$limit);
    $result = mysqli_query($con,$query);

    if(mysqli_num_rows($result) > 0){
        while ($row = mysqli_fetch_assoc($result))
        {
            $row["datetime"]=date($DATE_FORMAT,strtotime($row["datetime"]));
            $score[] = $row;
        }
        $output='{"status":"success", "score":';
        $output.=json_encode($score);
        $output.="}";
    }else{
         $output='{"status":"failure","remark":"No score data found"}';
    }
    return $output;
}

function cardScoreList($obj){
    global $con,$DATE_FORMAT;

    $score=array();

    $query="select u.token,c.score_1,c.time_1,c.score_2,c.time_2,c.score_3,c.time_3,c.score_4,c.time_4,c.datetime from `card` c left join `user` u on c.user_id=u.user_id where ";
     
    if(isset($obj->search)  && $obj->search!=""){
        $search = clean($obj->search);
        $query.="( u.`token` like '%".$search."%' or c.`score_0` like '%".$search."%' or c.`time_0` like '%".$search."%' or c.`score_1` like '%".$search."%' or c.`time_1` like '%".$search."%' or c.`score_2` like '%".$search."%' or c.`time_2` like '%".$search."%' or c.`score_3` like '%".$search."%' or c.`time_3` like '%".$search."%' or c.`score_4` like '%".$search."%' or c.`time_4` like '%".$search."%' ) and ";
    }

    $query.="1 order by c.`score_id` desc ";

    if(isset($obj->limit) && $obj->limit!=0){
        $limit=$obj->limit;
    }else{
        $limit=10;
    }

    if(isset($obj->page) && $obj->page!=0){
        $page=$obj->page;
    }else{
        $page=1;
    }

    $query.=" limit {$limit} offset ".(($page-1)*$limit);
    $result = mysqli_query($con,$query);

    if(mysqli_num_rows($result) > 0){
        while ($row = mysqli_fetch_assoc($result))
        {
            $row["datetime"]=date($DATE_FORMAT,strtotime($row["datetime"]));
            $score[] = $row;
        }
        $output='{"status":"success", "score":';
        $output.=json_encode($score);
        $output.="}";
    }else{
         $output='{"status":"failure","remark":"No score data found"}';
    }
    return $output;
}

function userDetails($user_id){
    global $con;
    $user=array();

    if($user_id!=""){
        $query="select * from user where user_id='{$user_id}'";
        $result=mysqli_query($con,$query);

        if(mysqli_num_rows($result)==1){
            $row=mysqli_fetch_assoc($result);
            $user[]=$row;
            $output='{"status":"success","remark":"Successfully connected", "user":'.json_encode($user).'}';
        }else{
            $output='{"status":"failure","remark":"Something is wrong with ID"}';
        }
    }else{
        $output='{"status":"failure","remark":"Invalid or Incomplete ID recieved"}';
    }
    return $output;
}
?>