<?php
require_once 'include/config.php';

if(isset($_POST["card_export_btn"])){
      $filename="card_".date("YmdHis").".csv";
      header('Content-Type: text/csv; charset=utf-8');  
      header('Content-Disposition: attachment; filename='.$filename);  
      $output = fopen("php://output", "w");  
      fputcsv($output, array('token', 'Score L1', 'Time L1', 'Score L2', 'Time L2', 'Score L3', 'Time L3', 'Score L4', 'Time L4', 'Upload'));  
      
      $query="select u.token,c.score_1,c.time_1,c.score_2,c.time_2,c.score_3,c.time_3,c.score_4,c.time_4,c.datetime from card c left join user u on c.user_id=u.user_id order by c.score_id desc";  
      $result=mysqli_query($con, $query);  
      while($row=mysqli_fetch_assoc($result))  
      {  
           fputcsv($output, $row);  
      }  
      fclose($output);  
} 

?> 