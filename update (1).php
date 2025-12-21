<?php

$id = $_GET['id'];
$status = $_GET['status'];

$connect = mysqli_connect("localhost" , "ckivzmoj_ar_sarkar" , "xpbGKOF0NarbrngX" , "ckivzmoj_ar_sarkar_db");

$mysql = "UPDATE dse_table SET status = '$status' WHERE id= '$id'";
$result = mysqli_query($connect , $mysql);


?>