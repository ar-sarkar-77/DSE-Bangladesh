<?php

$con = mysqli_connect('localhost','ckivzmoj_ar_sarkar','xpbGKOF0NarbrngX','ckivzmoj_ar_sarkar_db');

$html = file_get_contents('https://arsarkar.xyz/apps/dse_sample.html');

libxml_use_internal_errors(true);

$dom = new DOMDocument;
$dom->loadHTML($html);
libxml_clear_errors();

$xPath = new DOMXPath($dom);

$values = $xPath->query('//div[contains(@class, "scroll-item")]//a');

foreach($values as $item){
    $text = $item->textContent;
    
    $cleantext = str_ireplace('\xC5\xA0',' ',$text);
    $cleantext = preg_replace('/\s+/u',' ',$cleantext);
    $cleantext = trim($cleantext);
    
    //echo $cleantext;

    $info = explode(' ' , $cleantext);

    if(count($info)>=4){
        $name = $info[0];
        $price = $info[1];
        $change_price = $info[2];
        $change_percent = $info[3];

        $sql = "SELECT * FROM dse_table WHERE name LIKE '$name' ";
        $result = mysqli_query($con , $sql);
        $count = mysqli_num_rows($result);

        if($count>=1){
            $sql2 = "UPDATE dse_table SET price='$price' , change_price='$change_price' , change_percent='$change_percent' WHERE name LIKE '$name'";
            $result2 = mysqli_query($con , $sql2);
            if($result2) echo 'Updated successful.';
        }else{
            $sql2 = "INSERT INTO dse_table(name , price , change_price , change_percent) VALUES('$name' , '$price' , '$change_price' , '$change_percent')";
            $result2 = mysqli_query($con , $sql2);
            if($result2) echo 'Insert successful.';
        }
        

    }

    echo '<br><br>';
}

//print_r($xPath);

?>