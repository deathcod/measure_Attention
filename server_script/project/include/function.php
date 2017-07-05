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

    $json=json_decode($_REQUEST["json"],true);

    if(isset($json["user_id"]) && isset($json["token"])){    
        $query="select `token` from `user` where `user_id`='".$json["user_id"]."'";
        $result=mysqli_query($con,$query);
        $row=mysqli_fetch_assoc($result);

        if($row["token"]==$json["token"]){
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

    $query="select u.token,
        s1.score_id as score_id_1, s1.score as score_1, s1.correct as correct_1, s1.wrong as wrong_1, s1.time as time_1,
        g.*
        from `stroop` g 
        left join `user` u on g.user_id=u.user_id 
        left join score s1 on g.score_id_1=s1.score_id where ";
     
    if(isset($obj->search)  && $obj->search!=""){
        $search = clean($obj->search);
        $query.="( u.`token` like '%".$search."%' ) and ";
    }

    $query.="1 order by g.`datetime` desc ";

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

    $query="select u.token,
        s1.score_id as score_id_1, s1.score as score_1, s1.correct as correct_1, s1.wrong as wrong_1, s1.time as time_1,
        s2.score_id as score_id_2, s2.score as score_2, s2.correct as correct_2, s2.wrong as wrong_2, s2.time as time_2,
        s3.score_id as score_id_3, s3.score as score_3, s3.correct as correct_3, s3.wrong as wrong_3, s3.time as time_3,
        s4.score_id as score_id_4, s4.score as score_4, s4.correct as correct_4, s4.wrong as wrong_4, s4.time as time_4,
        s5.score_id as score_id_5, s5.score as score_5, s5.correct as correct_5, s5.wrong as wrong_5, s5.time as time_5,
        g.*
        from `test` g left join `user` u on g.user_id=u.user_id 
        left join score s1 on g.score_id_1=s1.score_id 
        left join score s2 on g.score_id_2=s2.score_id
        left join score s3 on g.score_id_3=s3.score_id
        left join score s4 on g.score_id_4=s4.score_id
        left join score s5 on g.score_id_5=s5.score_id where ";
     
    if(isset($obj->search)  && $obj->search!=""){
        $search = clean($obj->search);
        $query.="( u.`token` like '%".$search."%' ) and ";
    }

    $query.="1 order by g.`datetime` desc ";

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

    $query="select u.token,
        s1.score_id as score_id_1, s1.score as score_1, s1.correct as correct_1, s1.wrong as wrong_1, s1.time as time_1,
        s2.score_id as score_id_2, s2.score as score_2, s2.correct as correct_2, s2.wrong as wrong_2, s2.time as time_2,
        s3.score_id as score_id_3, s3.score as score_3, s3.correct as correct_3, s3.wrong as wrong_3, s3.time as time_3,
        s4.score_id as score_id_4, s4.score as score_4, s4.correct as correct_4, s4.wrong as wrong_4, s4.time as time_4,
        g.*
        from `chess` g left join `user` u on g.user_id=u.user_id 
        left join score s1 on g.score_id_1=s1.score_id 
        left join score s2 on g.score_id_2=s2.score_id
        left join score s3 on g.score_id_3=s3.score_id
        left join score s4 on g.score_id_4=s4.score_id where ";
     
    if(isset($obj->search)  && $obj->search!=""){
        $search = clean($obj->search);
        $query.="( u.`token` like '%".$search."%' ) and ";
    }

    $query.="1 order by g.`datetime` desc ";

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

    $query="select u.token,
        s1.score_id as score_id_1, s1.score as score_1, s1.correct as correct_1, s1.wrong as wrong_1, s1.time as time_1,
        s2.score_id as score_id_2, s2.score as score_2, s2.correct as correct_2, s2.wrong as wrong_2, s2.time as time_2,
        s3.score_id as score_id_3, s3.score as score_3, s3.correct as correct_3, s3.wrong as wrong_3, s3.time as time_3,
        s4.score_id as score_id_4, s4.score as score_4, s4.correct as correct_4, s4.wrong as wrong_4, s4.time as time_4,
        g.*
        from `card` g left join `user` u on g.user_id=u.user_id 
        left join score s1 on g.score_id_1=s1.score_id 
        left join score s2 on g.score_id_2=s2.score_id
        left join score s3 on g.score_id_3=s3.score_id
        left join score s4 on g.score_id_4=s4.score_id where ";
     
    if(isset($obj->search)  && $obj->search!=""){
        $search = clean($obj->search);
        $query.="( u.`token` like '%".$search."%' ) and ";
    }

    $query.="1 order by g.`datetime` desc ";

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

function clickList($obj){
    global $con,$DATE_FORMAT;

    $click=array();

    $query="select * from `click` where ";
     
    if(isset($obj->score_id)  && $obj->score_id!=""){
        $query.=" `score_id`='{$obj->score_id}' and ";
    }

    $query.="1 order by `datetime` asc ";

    if(isset($obj->limit) && $obj->limit!=0){
        $limit=$obj->limit;
    }else{
        $limit=50;
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
            $click[] = $row;
        }
        $output='{"status":"success", "click":';
        $output.=json_encode($click);
        $output.="}";
    }else{
         $output='{"status":"failure","remark":"No click data found"}';
    }
    return $output;
}


function userList($obj){
    global $con,$DATE_FORMAT;

    $user=array();

    $query="select * from `user` where ";
     
    if(isset($obj->search)  && $obj->search!=""){
        $search = clean($obj->search);
        $query.="( `token` like '%".$search."%' or `name` like '%".$search."%' or `email` like '%".$search."%' ) and ";
    }

    $query.="1 order by `datetime` desc ";

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
            $user[] = $row;
        }
        $output='{"status":"success", "user":';
        $output.=json_encode($user);
        $output.="}";
    }else{
         $output='{"status":"failure","remark":"No user data found"}';
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

function userInsert($obj){
    global $con;
    $output="";

    $name=$obj->name;
    $email=$obj->email;
    $sex=$obj->sex;
    $age=$obj->age;
    $hand=$obj->hand;
    $college=$obj->college;
    $semester=$obj->semester;
    $token=$obj->token;
    $datetime=date("Y-m-d H:i:s");

    $query="select * from user where token='{$token}'";
    $result=mysqli_query($con,$query);

    if(mysqli_num_rows($result)==1){
        //one data, so update it, and get detail
        $row=mysqli_fetch_assoc($result);
        $user_id=$row["user_id"];

        $query="update `user` set name='{$name}', email='{$email}', sex='{$sex}', age='{$age}', hand='{$hand}', college='{$college}', semester='{$semester}', datetime='{$datetime}' where user_id='{$user_id}'";
        $result=mysqli_query($con,$query);
        if($result){
            $output=userDetails($user_id);
        }else{
            $output='{"status":"failure", "remark":"Something is wrong"}';
        }
    }elseif(mysqli_num_rows($result)==0){
        //no data available, so insert into table
        $query="insert into `user` (`token`,`name`,`email`,`sex`,`age`,`hand`,`college`,`semester`,`datetime`) values ('{$token}','{$name}', '{$email}', '{$sex}', '{$age}', '{$hand}', '{$college}', '{$semester}', '{$datetime}')";
        $result=mysqli_query($con,$query);
        if($result){
            $user_id=mysqli_insert_id($con);
            $output=userDetails($user_id);
        }else{
            $output='{"status":"failure", "remark":"Something is wrong"}';
        }
    }else{
        $output='{"status":"failure", "remark":"Something is wrong with data"}';
    }

    return $output;
}

function validate($json){
    for($i=0;$i<sizeof($json["score"]);$i++){
        if(isset($json["score"][$i]["score"]) && $json["score"][$i]["score"]!=""){
            if(isset($json["score"][$i]["correct"]) && $json["score"][$i]["correct"]!=""){
                if(isset($json["score"][$i]["wrong"]) && $json["score"][$i]["wrong"]!=""){
                    if(isset($json["score"][$i]["time"]) && $json["score"][$i]["time"]!=""){

                    }else{
                        $output='{"status":"failure", "remark":"Invalid or Incomplete time level '.($i+1).' recieved"}';
                        die($output);
                    }
                }else{
                    $output='{"status":"failure", "remark":"Invalid or Incomplete wrong score level '.($i+1).' recieved"}';
                    die($output);
                }
            }else{
              $output='{"status":"failure", "remark":"Invalid or Incomplete correct score level '.($i+1).' recieved"}';
              die($output);
            }
        }else{
            $output='{"status":"failure", "remark":"Invalid or Incomplete score level '.($i+1).' recieved"}';
            die($output);
        }
    }
}

function scoreInsert($json,$table){
    global $con;
    validate($json);

    $user_id=numOnly($json["user_id"]);
    $score_id=array();
    for($i=0;$i<sizeof($json["score"]);$i++){
        $score=filter_var($json["score"][$i]["score"],FILTER_SANITIZE_STRING);
        $correct=filter_var($json["score"][$i]["correct"],FILTER_SANITIZE_STRING);
        $wrong=filter_var($json["score"][$i]["wrong"],FILTER_SANITIZE_STRING);
        $time=filter_var($json["score"][$i]["time"],FILTER_SANITIZE_STRING);

        $query="insert into `score` (`score`,`correct`,`wrong`,`time`) values ('{$score}', '{$correct}', '{$wrong}', '{$time}')";
        $result=mysqli_query($con,$query);
        $score_id[$i]=mysqli_insert_id($con);

        //insert into click detail
        for($j=0;$j<sizeof($json["score"][$i]["click"]);$j++){
            $datetime=date("Y-m-d H:i:s", $json["score"][$i]["click"][$j]["datetime"]);
            $status=$json["score"][$i]["click"][$j]["status"];
            $query="insert into `click` (`score_id`, `datetime`, `status`) values ('".$score_id[$i]."', '{$datetime}', '{$status}')";
            mysqli_query($con,$query);
        }
    }
    $datetime=date("Y-m-d H:i:s");
    $status=1;

    $query="insert into `".$table."` values ('', '{$user_id}'";
    for($i=0;$i<sizeof($score_id);$i++){
        $query.=", ".$score_id[$i];
    }
    $query.=", '{$datetime}', '{$status}')";
    $result=mysqli_query($con,$query);
    if($result){
        $output='{"status":"success", "remark":"Successfully added"}';
    }else{
        $output='{"status":"failure", "remark":"Something is wrong"}';
    }

    return $output;
}
?>