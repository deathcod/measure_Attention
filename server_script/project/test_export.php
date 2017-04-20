<?php
require_once 'include/config.php';

if(isset($_POST["test_export_btn"])){
      $filename="test_".date("YmdHis").".csv";
      header('Content-Type: text/csv; charset=utf-8');  
      header('Content-Disposition: attachment; filename='.$filename);  
      $output = fopen("php://output", "w");  
      fputcsv($output, array('token', 'Score L0', 'Time L0', 'Score L1', 'Time L1', 'Score L2', 'Time L2', 'Score L3', 'Time L3', 'Score L4', 'Time L4', 'Upload'));  
      
      $query="select u.token,t.score_0,t.time_0,t.score_1,t.time_1,t.score_2,t.time_2,t.score_3,t.time_3,t.score_4,t.time_4,t.datetime from test t left join user u on t.user_id=u.user_id order by t.score_id desc";  
      $result=mysqli_query($con, $query);  
      while($row=mysqli_fetch_assoc($result))  
      {  
           fputcsv($output, $row);  
      }  
      fclose($output);  
} 

?> 