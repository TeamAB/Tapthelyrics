<?php

  require_once 'application/DB_Connect.php';
  // connecting to database
  $db = new DB_Connect();
  $db->connect();

  $result = mysql_query("SELECT * FROM tapthelyrics_songs");

  $json = array();
  $total_records = mysql_num_rows($result);

  if($total_records >= 1){
    while ($row = mysql_fetch_array($result, MYSQL_ASSOC)){
      $json[] = $row;
    }
  }

  echo json_encode($json);
?>
