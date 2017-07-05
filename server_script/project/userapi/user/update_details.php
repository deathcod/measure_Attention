<?php
require_once '../../include/config.php';

// connection();
$output="";
$MAX_LEN=50;
$MIN_LEN=2;

if(isset($_REQUEST["json"]) && $_REQUEST["json"]!=""){
	$json=json_decode($_REQUEST["json"],true);

	if(isset($json["name"]) && $json["name"]!=""){
		if(isset($json["email"]) && $json["email"]!=""){
			if(isset($json["sex"]) && $json["sex"]!=""){
				if(isset($json["age"]) && $json["age"]!=""){
					if(isset($json["hand"]) && $json["hand"]!=""){
						if(isset($json["college"]) && $json["college"]!=""){
							if(isset($json["semester"]) && $json["semester"]!=""){
								if(isset($json["token"]) && $json["token"]!=""){
									$name=filter_var(trim($json["name"]),FILTER_SANITIZE_STRING);
									$email=filter_var(trim($json["email"]),FILTER_SANITIZE_EMAIL);
									$sex=filter_var(trim($json["sex"]),FILTER_SANITIZE_STRING);
									$age=(int)filter_var(trim($json["age"]),FILTER_SANITIZE_STRING);
									$hand=filter_var(trim($json["hand"]),FILTER_SANITIZE_STRING);
									$college=filter_var(trim($json["college"]),FILTER_SANITIZE_STRING);
									$semester=(int)filter_var(trim($json["semester"]),FILTER_SANITIZE_STRING);
									$token=filter_var(trim($json["token"]),FILTER_SANITIZE_STRING);

									if(strlen($name)>=$MIN_LEN && strlen($name)<=$MAX_LEN){
										if(strlen($email)>=$MIN_LEN && strlen($email)<=$MAX_LEN){
											if(filter_var($email,FILTER_VALIDATE_EMAIL)){
												if($sex=="male" || $sex=="female"){
													if($age>=1 && $age<=100){
														if($hand=="left" || $hand=="right"){
															if(strlen($college)>=$MIN_LEN && strlen($college)<=$MAX_LEN){
																if($semester>=1 && $semester<=10){
																	//all function is in include/function.php file
																	$obj=new stdClass();
																	$obj->name=$name;
																	$obj->email=$email;
																	$obj->sex=$sex;
																	$obj->age=$age;
																	$obj->hand=$hand;
																	$obj->college=$college;
																	$obj->semester=$semester;
																	$obj->token=$token;
																	$output=userInsert($obj);
																}else{
																	$output='{"status":"failure", "remark":"Semester must be 1-10"}';
																}
															}else{
																$output='{"status":"failure", "remark":"College must be '.$MIN_LEN.'-'.$MAX_LEN.' characters long"}';
															}
														}else{
															$output='{"status":"failure", "remark":"Invalid Hand Mode recieved"}';
														}
													}else{
														$output='{"status":"failure", "remark":"Age must be 1-100 years old"}';
													}
												}else{
													$output='{"status":"failure", "remark":"Invalid Sex recieved"}';
												}
											}else{
												$output='{"status":"failure", "remark":"Invalid Email recieved"}';
											}
										}else{
											$output='{"status":"failure", "remark":"Email must be '.$MIN_LEN.'-'.$MAX_LEN.' characters long"}';
										}
									}else{
										$output='{"status":"failure", "remark":"Name must be '.$MIN_LEN.'-'.$MAX_LEN.' characters long"}';
									}
								}else{
									$output='{"status":"failure", "remark":"Invalid or Incomplete token recieved"}';
								}
							}else{
								$output='{"status":"failure", "remark":"Invalid or Incomplete Semester recieved"}';
							}
						}else{
							$output='{"status":"failure", "remark":"Invalid or Incomplete College recieved"}';
						}
					}else{
						$output='{"status":"failure", "remark":"Invalid or Incomplete Hand recieved"}';
					}
				}else{
					$output='{"status":"failure", "remark":"Invalid or Incomplete Age recieved"}';
				}
			}else{
				$output='{"status":"failure", "remark":"Invalid or Incomplete Sex recieved"}';
			}
		}else{
			$output='{"status":"failure", "remark":"Invalid or Incomplete Email recieved"}';
		}	
	}else{
		$output='{"status":"failure", "remark":"Invalid or Incomplete Name recieved"}';
	}
}else{
	$output='{"status":"failure", "remark":"Invalid or Incomplete json recieved"}';
}

echo $output;

mysqli_close($con);
?>