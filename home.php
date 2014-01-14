<?php require_once('header.php');

  
?>

<body>
  <div class="navbar navbar">
    <div class="navbar-inner">
        <div class="container">
            <!-- brand class is from bootstrap.css  -->
            <a class="brand" href="#">Mobile Kiosk</a>
            <div class="nav-collapse">
                <ul class="nav pull-right">
                    <li ><a href="assessments.php">Categories</a></li>
                    <li ><a href="announcements.php">Locations</a></li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Hello! <?php echo $_SESSION['name']; ?> <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li class="nav-header">User Settings</li>
                            <li><a id="btnLogout">Logout</a></li>
                        </ul>
                    </li>
                </ul>
            </div><!-- /.nav-collapse -->
        </div>
    </div><!-- /navbar-inner -->
  </div><!-- /navbar -->

  <!--Begin container-fluid -->
  <div class="container-fluid">
      

      <div class="row-fluid">
        <div class="span10 offset1">
          <div class="container-fluid">
            <div class="row-fluid">
              <div class="span12">
                <div class="well" style="text-align:center;padding-left:60px;padding-top:30px;">
                    <ul class="thumbnails">

            
                    </ul>
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


<script  type="text/javascript" >
  $(function()
  {
      $('#btnLogout').click(function(){
        location.href = "index.php";
      });


  });

</script>