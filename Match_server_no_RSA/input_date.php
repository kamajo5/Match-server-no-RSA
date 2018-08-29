<?php

include_once 'config.php';
$db = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
$ID_User = "";
$Input_data = "";
$Option = "";

if (isset($_POST['ID_User'])) {
    $ID_User = $_POST['ID_User'];
}

if (isset($_POST['Input_data'])) {
    $Input_data = $_POST['Input_data'];
}

if (isset($_POST['Option'])) {
    $Option = $_POST['Option'];
}

if (!empty($ID_User) && !empty($Input_data) && !empty($Option)) {
    $json = array();
    $query = "insert into input_date (ID_user, Input_data, Select_option) values ('$ID_User', '$Input_data', '$Option')";
    $inserted = mysqli_query($db, $query);
    if ($inserted == 1) {
        $json['success'] = 1;
        $json['message'] = "Done";
    } else {
        $json['success'] = 0;
        $json['message'] = "Error input";
    }
    mysqli_close($db);
    echo json_encode($json);
}