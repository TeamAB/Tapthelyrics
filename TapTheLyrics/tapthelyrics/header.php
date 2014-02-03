<?php session_start(); 

  if( empty($_SESSION['name']) )
  {
    header("Location:expire.php");

  }

require_once('controllers/ControllerSong.php'); 
require_once('controllers/ControllerAuthentication.php');
    

?>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<!-- <link rel="stylesheet" type="text/css" href="css/bootstrap-responsive.min.css"> -->
<link rel="stylesheet" href="js/datepicker/css/datepicker.css" type="text/css" />

<link rel="stylesheet" href="css/style.css" type="text/css" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript"  src="js/bootstrap-transition.js"></script>
<script src="js/bootstrap-tooltip.js"></script>
<script src="js/bootstrap-popover.js"></script>
<script type="text/javascript" src="js/datepicker/js/bootstrap-datepicker.js"></script>

</head>