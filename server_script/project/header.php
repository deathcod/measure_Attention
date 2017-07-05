<?php require_once 'include/config.php';?>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"/>
  <title id="title"></title>

  <!-- CSS  -->
  <link rel="stylesheet" href="assets/css/w3.css">
  <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
</head>
<body>

<div class="w3-bar w3-black">
  <a href="index.php" class="w3-bar-item w3-button">Home</a>
  <a href="#" class="w3-bar-item w3-button w3-yellow"><?=$SERVER_IP?></a>
  <a href="brain.php" class="w3-bar-item w3-button w3-hover-red w3-right" id="brain">Brain Challenge</a>
  <a href="test.php" class="w3-bar-item w3-button w3-hover-pink w3-right" id="test">Test Your Brain</a>
  <a href="card.php" class="w3-bar-item w3-button w3-hover-brown w3-right" id="card">Card</a>
  <a href="chess.php" class="w3-bar-item w3-button w3-hover-cyan w3-right" id="chess">Chess</a>
  <a href="stroop.php" class="w3-bar-item w3-button w3-hover-teal w3-right" id="stroop">Stroop</a>
  <a href="user.php" class="w3-bar-item w3-button w3-hover-yellow w3-right" id="user">User</a>
</div>