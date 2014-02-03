<?php session_start(); 
  $_SESSION['name'] = null;
  require_once('controllers/ControllerAuthentication.php'); 
?>

<html>
<head>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<!-- <link rel="stylesheet" type="text/css" href="css/bootstrap-responsive.min.css"> -->
<link rel="stylesheet" href="js/datepicker/css/datepicker.css" type="text/css" />
<link rel="stylesheet" href="js/datepicker/less/datepicker.css" type="text/css" />

<link rel="stylesheet" href="css/style.css" type="text/css" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript"  src="js/bootstrap-transition.js"></script>
<script src="js/bootstrap-tooltip.js"></script>
<script src="js/bootstrap-popover.js"></script>
<script type="text/javascript" src="js/datepicker/js/bootstrap-datepicker.js"></script>

<script  type="text/javascript" >
  $(function()
  {
      $('#dp1').datepicker({
        format: 'yyyy-mm-dd'
      });
       $('#dp2').datepicker({
          format: 'yyyy-mm-dd'
      });

  });

</script>

</head>

<?php 
  $controllerAuth = new ControllerAuthentication();

  $error = null;

  if( !empty($_POST['username']) && !empty($_POST['password']) )
  {
      $auth = $controllerAuth->login($_POST['username'], $_POST['password']);
      
      if($auth != null)
      {
        $_SESSION['name'] = $auth->name;
        
        header("Location:songs.php");
      }
      else
      {
        $error = "Invalid Username or Password.";
      }
  }

?>

<body>


  <div class="container-fluid">
    
    <div class="row-fluid">

      <div class="span6 offset3">
        <div class="navbar">
          <div class="navbar-inner">
              <div class="container">
                  <!-- brand class is from bootstrap.css  -->
                  <a class="brand" href="#">Tap The Lyrics - Admin</a>
                  
              </div>
          </div><!-- /navbar-inner -->
        </div><!-- /navbar -->
      </div>
    </div>
  </div>
  

  <!--Begin container-fluid -->
  <div class="container-fluid">
    
      <div class="row-fluid">
        <div class="span6 offset3">
          <div class="container-fluid">
            <div class="row-fluid">
              <div class="span12">
                <div class="well" style="text-align:center;padding-top:30px;">
                    <!-- Modal -->
                  <form action="" method="POST">
                    <table class="table table-bordered" id="modalUser">
                            <tbody>
                               <tr>
                                <td>User Name:</td>
                                <td><input type="text" name="username" value="" required id="username"/></td>
                              </tr>
                              <tr>
                                <td>Password:</td>
                                <td><input type="password" name="password" value="" required id="password"/></td>
                              </tr>

                              <?php if($error != null) { ?>

                              <tr>
                                <td>Error:</td>
                                <td style = "color: #ff0000;"><?php echo $error; ?></td>
                              </tr>

                              <?php } ?>
                              
                            </tbody>
                          </table>
                          <input class="btn btn-danger" role='button' type="submit" id="btnLogin" name="submit" value="Log In"></input>
                    
                  </form>
                  <!-- end Modal -->
                </div>  
              </div>
            </div>
          </div>

      
        </div>
      </div>

  </div>
  <!--End container-fluid -->
</body>

</html>