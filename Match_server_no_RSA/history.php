<?php

include_once 'config.php';
$db = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
$ID = "";

if (isset($_POST['ID'])) {
    $ID = $_POST['ID'];
}

if (!empty($ID)) {
    $json = array();
    $query = "select Input_data, Output_data from input_date where ID_user = '$ID'";
    $result = mysqli_query($db, $query);
    if (mysqli_num_rows($result) > 0) {
        $json['success'] = 1;
        $json['message'] = "success" ;
        $json['count'] = mysqli_num_rows($result);
        $i = 0;
        while ($row = mysqli_fetch_array($result)) {
            $json[$i] = $row['Input_data'];
            $i++;
            $json[$i] = $row["Output_data"];
            $i++;
        }
    } else {
        $json['succes'] = 0;
        $json['message'] = "error";
    }
    mysqli_close($db);
    echo json_encode($json);
}