<?php require_once 'header.php';?>
<div class="w3-container">
  <div class="w3-row w3-padding-8">
    <div class="w3-col w3-half">
      <h4>User List</h4>
    </div>
    <div class="w3-col w3-half">
      
    </div>
  </div>
  <div class="w3-row">
    <div id="user_list" class="w3-responsive"></div>
  </div>
</div>

<?php require_once 'footer.php'; ?>

<script>
$("document").ready(function(){
  $("#title").text("User");
  $("#user").addClass("w3-yellow");
  user_list();
});

function user_list(){
  $.post("adminapi/user/user_list.php",
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
        out+='<th>Name</th>';
        out+='<th>Email</th>';
        out+='<th>Sex</th>';
        out+='<th>Age</th>';
        out+='<th>Hand</th>';
        out+='<th>College</th>';
        out+='<th>Semester</th>';
        out+='<th>Datetime</th>';
        out+='<th>Action</th>';
      out+='</tr>';
      
      for(i=0;i<arr["user"].length;i++){
        out+='<tr>';
          out+='<td>'+(i+1)+'</td>';
          out+='<td>'+arr["user"][i]["token"]+'</td>';
          out+='<td>'+arr["user"][i]["name"]+'</td>';
          out+='<td>'+arr["user"][i]["email"]+'</td>';
          out+='<td>'+arr["user"][i]["sex"]+'</td>';
          out+='<td>'+arr["user"][i]["age"]+'</td>';
          out+='<td>'+arr["user"][i]["hand"]+'</td>';
          out+='<td>'+arr["user"][i]["college"]+'</td>';
          out+='<td>'+arr["user"][i]["semester"]+'</td>';
          out+='<td>'+arr["user"][i]["datetime"]+'</td>';
          out+='<td></td>';
        out+='</tr>';
      }
    out+='</table>';
  }else{
    out+='<p class="w3-text-red w3-center">'+arr["remark"]+'</p>';
  }
  $("#user_list").html(out);
}

</script>