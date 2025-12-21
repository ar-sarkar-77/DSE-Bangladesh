<?php

header('Content-Type: application/json; charset=utf-8');

$name = $_GET['name'];

$connect = mysqli_connect("localhost" , "ckivzmoj_ar_sarkar" , "xpbGKOF0NarbrngX" , "ckivzmoj_ar_sarkar_db");

$mysql = "SELECT * FROM dse_table WHERE name LIKE '%$name%'";
$result = mysqli_query($connect , $mysql);

$data = array();

foreach ($result as $item){
    $id = $item['id'];
    $name = $item['name'];
    $price = $item['price'];
    $change_price = $item['change_price'];
    $change_percent = $item['change_percent'];
    $time = $item['time'];
    $status = $item['status'];
    
    $userdata['id'] = $id;
    $userdata['name'] = $name;
    $userdata['price'] = $price;
    $userdata['change_price'] = $change_price;
    $userdata['change_percent'] = $change_percent;
    $userdata['time'] = $time;
    $userdata['status'] = $status;

    array_push($data , $userdata);
}
echo json_encode($data);

?>