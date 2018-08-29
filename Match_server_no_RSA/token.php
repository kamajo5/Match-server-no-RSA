<?php

include_once 'config.php';
$db_table = "user";
$db = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
$ID = "";
$Token = "";

if (isset($_POST['ID'])) {
    $ID = $_POST['ID'];
}

if (isset($_POST['Token'])) {
    $Token = $_POST['Token'];
}

if (!empty($ID) && !empty($Token)) {
    $json = array();
    $query = "select Token from user where ID = '$ID' Limit 1";
    $result = mysqli_query($db, $query);
    $row = $result->fetch_array(MYSQLI_NUM);
    if ($row[0] == $Token) {
        $query = "UPDATE user SET Activation = 1 WHERE ID = '$ID'";
        $result = mysqli_multi_query($db, $query);
        $json['success'] = 1;
        $json['message'] = "Active";
        $json['isActive'] = 1;
    } else {
        $json['success'] = 0;
        $json['message'] = "Wrong token";
    }
    mysqli_close($db);
    echo json_encode($json);
}