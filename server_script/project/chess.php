<?php require_once 'header.php';?>
<div class="w3-container">
  <div class="w3-row w3-padding-8">
    <div class="w3-col w3-half">
      <h4>Chess Game Score</h4>
    </div>
    <div class="w3-col w3-half">
      <form class="form-horizontal" action="chess_export.php" method="post" name="upload_excel"   
                        enctype="multipart/form-data">
        <input type="submit" name="chess_export_btn" class="w3-btn w3-right" value="Export to CSV"/>                    
      </form> 
    </div>
  </div>
  <div class="w3-row">
    <div id="score_list" class="w3-responsive"></div>
  </div>
</div>
<?php require_once 'footer.php'; ?>

<script>
$("document").ready(function(){
  $("#title").text("Chess");
  $("#chess").addClass("w3-cyan");
  score_list();
});

function score_list(){
  $.post("adminapi/chess/score_list.php",
  {
    limit:1000,
    page:1
  },function(data){
    console.log(data);
    print_data(data);
  });
}

function print_data(data){
  var arr=JSON.parse(data);
  var out="";
  if(arr["status"]=="success"){
    out+='<table class="w3-table w3-striped">';
      out+='<tr>';
        out+='<th>S/no</th>';
        out+='<th>Token</th>';
        out+='<th>Score L1</th>';
        out+='<th>Time L1</th>';
        out+='<th>Score L2</th>';
        out+='<th>Time L2</th>';
        out+='<th>Score L3</th>';
        out+='<th>Time L3</th>';
        out+='<th>Score L4</th>';
        out+='<th>Time L4</th>';
        out+='<th>Upload</th>';
      out+='</tr>';
      
      for(i=0;i<arr["score"].length;i++){
        out+='<tr>';
          out+='<td>'+(i+1)+'</td>';
          out+='<td>'+arr["score"][i]["token"]+'</td>';
          out+='<td>'+arr["score"][i]["score_1"]+'</td>';
          out+='<td>'+arr["score"][i]["time_1"]+'</td>';
          out+='<td>'+arr["score"][i]["score_2"]+'</td>';
          out+='<td>'+arr["score"][i]["time_2"]+'</td>';
          out+='<td>'+arr["score"][i]["score_3"]+'</td>';
          out+='<td>'+arr["score"][i]["time_3"]+'</td>';
          out+='<td>'+arr["score"][i]["score_4"]+'</td>';
          out+='<td>'+arr["score"][i]["time_4"]+'</td>';
          out+='<td>'+arr["score"][i]["datetime"]+'</td>';
        out+='</tr>';
      }
    out+='</table>';
  }else{
    out+='<p class="w3-text-red w3-center">'+arr["remark"]+'</p>';
  }
  $("#score_list").html(out);
}
</script>