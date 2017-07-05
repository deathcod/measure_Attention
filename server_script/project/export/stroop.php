<?php
require_once '../include/config.php';

if(isset($_POST["stroop_export_btn"])){
      $filename="stroop_".date("YmdHis").".csv";
      header('Content-Type: text/csv; charset=utf-8');  
      header('Content-Disposition: attachment; filename='.$filename);  
      $output = fopen("php://output", "w");  
      fputcsv($output, array('token', 'Score', 'Correct', 'Wrong', 'Time (in millisecond)', 'Datetime'));  
      
      $query="select u.token,
        s1.score as score_1, s1.correct as correct_1, s1.wrong as wrong_1, s1.time as time_1,
        g.datetime
        from `stroop` g 
        left join `user` u on g.user_id=u.user_id 
        left join score s1 on g.score_id_1=s1.score_id 
        where g.status=1 order by g.game_id asc";
      $result=mysqli_query($con, $query);  
      while($row=mysqli_fetch_assoc($result))  
      {  
           fputcsv($output, $row);  
      }  
      fclose($output);  
} 

?> 