<?php

include_once 'config.php';
$db = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

$Login = "";

$Password = "";

$Email = "";


if (isset($_POST['Login'])) {

    $Login = $_POST['Login'];
}

if (isset($_POST['Password'])) {

    $Password = $_POST['Password'];
}

if (isset($_POST['Email'])) {

    $Email = $_POST['Email'];
}

function isEmailUsernameExist($Login, $Email) {
    $query = "select * from user where Login = '$Login' AND Email = '$Email'";
    $result = mysqli_query(mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME), $query);
    if (mysqli_num_rows($result) > 0) {
        mysqli_close(mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME));
        return true;
    }
    return false;
}

function isValidEmail($Email) {
    return filter_var($Email, FILTER_VALIDATE_EMAIL) !== false;
}

// Registration

if (!empty($Login) && !empty($Password) && !empty($Email)) {
    $json = array();
    $isExisting = isEmailUsernameExist($Login, $Email);
    $Password = md5($Password);
    if ($isExisting) {
        $json['success'] = 0;
        $json['message'] = "Error in registering. Probably the Login/Email already exists";
    } else {
        $isValid = isValidEmail($Email);
        if ($isValid) {
            $query = "insert into user (Login, Password, Email) values ('$Login', '$Password', '$Email')";
            $inserted = mysqli_query($db, $query);
            $query = "select ID from user where Login = '$Login' and Password = '$Password' Limit 1";
            $result = mysqli_query($db, $query);
            $row = $result->fetch_array(MYSQLI_NUM);
            if ($inserted == 1) {
                $json['how_id'] = 1;
                $json['message'] = "Done";
                $json['how_id'] = $row[0];
            } else {
                $json['success'] = 0;
                $json['message'] = "Error in registering. Probably the Login/Email already exists tu";
            }    
        } else {
            $json['success'] = 0;
            $json['message'] = "Error in registering. Email Address is not valid";
        }
    }
    mysqli_close($db);
    echo json_encode($json);
}

// Login

if (!empty($Login) && !empty($Password)) {
    $Password = md5($Password);
    $json = array();
    $query = "select ID, Login, Password, Email ,Activation from user where Login = '$Login' and Password = '$Password' Limit 1";
    $result = mysqli_query($db, $query);
    $row = $result->fetch_array(MYSQLI_NUM);
    if (mysqli_num_rows($result) > 0) {
        $json['success'] = 1;
        $json['message'] = "Done";
        $json['m_i'] = $row[0];
        $json['m_l'] = $row[1];
        $json['m_p'] = $row[2];
        $json['m_e'] = $row[3];
        $json['token'] = $row[4];
    } else {
        $json['success'] = 0;
        $json['message'] = "Wrong Input";
    }
    mysqli_close($db);
    echo json_encode($json);
}