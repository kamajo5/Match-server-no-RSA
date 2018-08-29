<?php

include_once 'config.php';
$db = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
$ID = "";
$Input = "";

if (isset($_POST['ID'])) {
    $ID = $_POST['ID'];
}

if (isset($_POST['Input'])) {
    $Input = $_POST['Input'];
}

if (!empty($ID) && !empty($Input)) {
    $json = array();
    $query = "select Output_data from input_date where Input_data = '$Input' and ID_User = '$ID'";
    $result = mysqli_query($db, $query);
    $row = $result->fetch_array(MYSQLI_NUM);
    if (mysqli_num_rows($result) > 0) {
        $json['success'] = 1;
        $json['message'] = $row[0];
    } else {
        $json['succes'] = 0;
        $json['message'] = "Error";
    }
    mysqli_close($db);
    echo json_encode($json);
}