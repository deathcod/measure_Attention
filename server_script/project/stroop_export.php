<?php
require_once 'include/config.php';

if(isset($_POST["stroop_export_btn"])){
      $filename="stroop_".date("YmdHis").".csv";
      header('Content-Type: text/csv; charset=utf-8');  
      header('Content-Disposition: attachment; filename='.$filename);  
      $output = fopen("php://output", "w");  
      fputcsv($output, array('token', 'Score (out of 10)', 'Time (in sec)', 'Average Time (in sec)', 'Upload'));  
      
      $query="select u.token,s.score,s.time,s.avg_time,s.datetime from stroop s left join user u on s.user_id=u.user_id order by s.score_id desc";  
      $result=mysqli_query($con, $query);  
      while($row=mysqli_fetch_assoc($result))  
      {  
           fputcsv($output, $row);  
      }  
      fclose($output);  
} 

?> 