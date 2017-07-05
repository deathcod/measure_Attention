<?php
require_once '../include/config.php';

if(isset($_POST["test_export_btn"])){
      $filename="test_".date("YmdHis").".csv";
      header('Content-Type: text/csv; charset=utf-8');  
      header('Content-Disposition: attachment; filename='.$filename);  
      $output = fopen("php://output", "w");  
      fputcsv($output, array('token', 'Score L1', 'Correct L1', 'Wrong L1', 'Time L1', 'Score L2', 'Correct L2', 'Wrong L2', 'Time L2', 'Score L3', 'Correct L3', 'Wrong L3', 'Time L3', 'Score L4',  'Correct L4', 'Wrong L4', 'Time L4', 'Score L5',  'Correct L5', 'Wrong L5', 'Time L5', 'Datetime'));  
      
      $query="select u.token,
        s1.score as score_1, s1.correct as correct_1, s1.wrong as wrong_1, s1.time as time_1,
        s2.score as score_2, s2.correct as correct_2, s2.wrong as wrong_2, s2.time as time_2,
        s3.score as score_3, s3.correct as correct_3, s3.wrong as wrong_3, s3.time as time_3,
        s4.score as score_4, s4.correct as correct_4, s4.wrong as wrong_4, s4.time as time_4,
        s5.score as score_5, s5.correct as correct_5, s5.wrong as wrong_5, s5.time as time_5,
        g.datetime
        from `test` g left join `user` u on g.user_id=u.user_id 
        left join score s1 on g.score_id_1=s1.score_id 
        left join score s2 on g.score_id_2=s2.score_id
        left join score s3 on g.score_id_3=s3.score_id
        left join score s4 on g.score_id_4=s4.score_id
        left join score s5 on g.score_id_5=s5.score_id
        where g.status=1 order by g.game_id asc"; 
      $result=mysqli_query($con, $query);  
      while($row=mysqli_fetch_assoc($result))  
      {  
           fputcsv($output, $row);  
      }  
      fclose($output);  
} 

?> 