<?php require_once 'header.php';?>
<div class="w3-container">
  <div class="w3-row w3-padding-8">
    <div class="w3-col w3-half">
      <h4>Test Your Brain Game Score</h4>
    </div>
    <div class="w3-col w3-half">
      <form class="form-horizontal" action="export/test.php" method="post" name="upload_excel"   
                        enctype="multipart/form-data">
        <input type="submit" name="test_export_btn" class="w3-btn w3-right" value="Export to CSV"/>                    
      </form> 
    </div>
  </div>
  <div class="w3-row">
    <div id="score_list" class="w3-responsive"></div>
  </div>
</div>

<div id="detail_modal" class="modal fade" role="dialog">
  <div class="modal-dialog modal-lg">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Click Details</h4>
      </div>
      <div class="modal-body">
        <div id="click_list" class="w3-responsive"></div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>

<?php require_once 'footer.php'; ?>

<script>
$("document").ready(function(){
  $("#title").text("Test Your Brain");
  $("#test").addClass("w3-pink");
  score_list();
});

function score_list(){
  $.post("adminapi/test/score_list.php",
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
        out+='<th>Score (C/W) L1</th>';
        out+='<th>Time L1</th>';
        out+='<th>Score (C/W) L2</th>';
        out+='<th>Time L2</th>';
        out+='<th>Score (C/W) L3</th>';
        out+='<th>Time L3</th>';
        out+='<th>Score (C/W) L4</th>';
        out+='<th>Time L4</th>';
        out+='<th>Score (C/W) L5</th>';
        out+='<th>Time L5</th>';
        out+='<th>Datetime</th>';
        out+='<th>Status</th>';
      out+='</tr>';
      
      for(i=0;i<arr["score"].length;i++){
        out+='<tr>';
          out+='<td>'+(i+1)+'</td>';
          out+='<td>';
            out+=arr["score"][i]["score_1"]+' ('+arr["score"][i]["correct_1"]+'/'+arr["score"][i]["wrong_1"]+') ';
            out+='<a href="#" class="w3-btn w3-brown click_btn" data-score_id="'+arr["score"][i]["score_id_1"]+'" data-toggle="modal" data-target="#detail_modal"></a>';
          out+='</td>';
          out+='<td>'+arr["score"][i]["time_1"]+'</td>';
          out+='<td>';
            out+=arr["score"][i]["score_2"]+' ('+arr["score"][i]["correct_2"]+'/'+arr["score"][i]["wrong_2"]+') ';
            out+='<a href="#" class="w3-btn w3-brown click_btn" data-score_id="'+arr["score"][i]["score_id_2"]+'" data-toggle="modal" data-target="#detail_modal"></a>';
          out+='</td>';
          out+='<td>'+arr["score"][i]["time_2"]+'</td>';
          out+='<td>';
            out+=arr["score"][i]["score_3"]+' ('+arr["score"][i]["correct_3"]+'/'+arr["score"][i]["wrong_3"]+') ';
            out+='<a href="#" class="w3-btn w3-brown click_btn" data-score_id="'+arr["score"][i]["score_id_3"]+'" data-toggle="modal" data-target="#detail_modal"></a>';
          out+='</td>';
          out+='<td>'+arr["score"][i]["time_3"]+'</td>';
          out+='<td>';
            out+=arr["score"][i]["score_4"]+' ('+arr["score"][i]["correct_4"]+'/'+arr["score"][i]["wrong_4"]+') ';
            out+='<a href="#" class="w3-btn w3-brown click_btn" data-score_id="'+arr["score"][i]["score_id_4"]+'" data-toggle="modal" data-target="#detail_modal"></a>';
          out+='</td>';
          out+='<td>'+arr["score"][i]["time_4"]+'</td>';
          out+='<td>';
            out+=arr["score"][i]["score_5"]+' ('+arr["score"][i]["correct_5"]+'/'+arr["score"][i]["wrong_5"]+') ';
            out+='<a href="#" class="w3-btn w3-brown click_btn" data-score_id="'+arr["score"][i]["score_id_5"]+'" data-toggle="modal" data-target="#detail_modal"></a>';
          out+='</td>';
          out+='<td>'+arr["score"][i]["time_5"]+'</td>';
          out+='<td>'+arr["score"][i]["datetime"]+'</td>';
          out+='<td>';
            
            color=(arr["score"][i]["status"]=='1')?'w3-green':'w3-red';
            out+='<button class="w3-btn status_btn '+color+'" data-game_id="'+arr["score"][i]["game_id"]+'"></button>&nbsp;';

          out+='</td>';
        out+='</tr>';
      }
    out+='</table>';
  }else{
    out+='<p class="w3-text-red w3-center">'+arr["remark"]+'</p>';
  }
  $("#score_list").html(out);
}

$("body").on("click",".status_btn",function(){
  var game_id=$(this).data("game_id");

  $.post("adminapi/test/status_update.php",
  {
    game_id:game_id
  },function(data){
    // console.log(data);
    var arr=JSON.parse(data);
    if(arr["status"]=="success"){
      score_list();
    }else{
      alert(arr["remark"]);
    }
  });
});

$("body").on("click",".click_btn",function(){
  var score_id=$(this).data("score_id");
  var out="";

  $.post("adminapi/click/click_list.php",
  {
    score_id:score_id
  },function(data){
    console.log(data);
    var arr=JSON.parse(data);
    if(arr["status"]=="success"){
      out+='<table class="w3-table w3-striped">';
        out+='<tr>';
          out+='<th>S/no</th>';
          out+='<th>Time of click</th>';
          out+='<th>Status</th>';
        out+='</tr>';
        for(i=0;i<arr["click"].length;i++){
          out+='<tr>';
            out+='<td>'+(i+1)+'</td>';
            out+='<td>'+arr["click"][i]["datetime"]+'</td>';
            out+='<td>'+arr["click"][i]["status"]+'</td>';
          out+='</tr>';
        }
      out+='</table>';
    }else{
      out+='<p class="w3-text-red w3-center">'+arr["remark"]+'</p>';
    }
    $("#click_list").html(out);
  });
});

</script>