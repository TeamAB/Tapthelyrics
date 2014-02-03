<?php require_once('header.php'); 

  $controller = new ControllerSong();
  $songs = $controller->getSongs();

?>

<body>

  <div class="container-fluid">
    
    <div class="row-fluid">

      <div class="span10 offset1">
        <div class="navbar">
          <div class="navbar-inner">
              <div class="container">
                  <!-- brand class is from bootstrap.css  -->
                  <a class="brand" href="#">Tap The Lyrics - Admin</a>
                  <div class="nav-collapse">
                      <ul class="nav pull-right">
                          <li class="active"><a href="songs.php">Songs</a></li>
                          <li class="dropdown">
                              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Hello! <?php echo $_SESSION['name']; ?> <b class="caret"></b></a>
                              <ul class="dropdown-menu">
                                  <li class="nav-header">User Settings</li>
                                  <li><a href="index.php" id="btnLogout">Logout</a></li>
                              </ul>
                          </li>
                      </ul>
                  </div><!-- /.nav-collapse -->
                  
              </div>
          </div><!-- /navbar-inner -->
        </div><!-- /navbar -->
      </div>
    </div>
  </div>




  <div class="container-fluid">
    
    <div class="row-fluid">

      <div class="span10 offset1">
        <div style="float:left;padding-left:20px;">
          <h2>Songs Panel</h2>
        </div>
        <div style="float:right; padding-top:15px; text-align:left;padding-right:20px;">
          <label class="btn btn-danger" role='button' id="btnAddSong">Add Song</label>
        </div>
      </div>
    </div>

    <div class="row-fluid">

      <div class="span10 offset1" style="text-align:center;">
        <div class="container-fluid">
          <div class="row-fluid">

            <!-- <div class="span8"> -->
              <div class="well" style="text-align:center;padding-top:30px;">

                  <table class="table table-bordered">
                   
                   <thead>
                      <tr>
                        <td>ID</td>
                        <td>Title</td>
                        <td>Artist</td>
                        <td>Song File</td>
                        <td>Lyrics File</td>
                        <td>Difficulty</td>
                        
                        <td>Actions</td>
                      </tr>
                    </thead>

                    <tbody>
                      
                        <?php 

                          if($songs != null)
                          {
                            foreach ($songs as $s) 
                            {
                              echo "<tr>"; 
                              echo "<td>$s->id</td>";
                              echo "<td>$s->title</td>";
                              echo "<td>$s->artist</td>";
                              echo "<td>$s->songFile</td>";
                              echo "<td>$s->lyricsFile</td>";
                              echo "<td>$s->difficulty</td>";

                              echo "<td>
                                      <a href='songs_edit.php?id=$s->id' role='button' class='btn' data-toggle='modal' id=''>Edit</a>
                                      
                                    </td>";
                            }
                            echo "</tr>";
                          }
                          
                        ?>
                      
                    </tbody>
        

                  </table>

              </div>
            <!-- </div> -->

          </div>
        </div>

      </div>
    </div>


</div>


</body>
</html>


<script  type="text/javascript" >
  $(function()
  {
    $('#btnAddSong').click(function(){
        location.href= "songs_insert.php";
     });

  });

</script>