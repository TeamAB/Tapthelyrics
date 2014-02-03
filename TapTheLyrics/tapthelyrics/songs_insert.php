<?php require_once('header.php'); 

  $controller = new ControllerSong();
  
  $upload_dir = "uploads/";

  if(!empty($_POST['title']) && 
    !empty($_POST['artist']) && 
    !empty($_POST['difficulty']) && 
    !empty( $_FILES["file"]["name"][0] ) && 
    !empty( $_FILES["file"]["name"][1] ) )
  {
    $song = new Song();
    $song->title = $_POST['title'];
    $song->artist = $_POST['artist'];
    $song->difficulty = $_POST['difficulty'];


    $allowedExts = array("mpeg", "mp3", "wav", "MP3", "WAV");
    $temp = explode(".", $_FILES["file"]["name"][0]);

    $extension = end($temp); // mp3

    if ( /*($_FILES["file"]["type"][0] == "audio/mpeg") && */in_array($extension, $allowedExts))
    {
      if ($_FILES["file"]["error"][0] > 0)
      {
        echo "Return Code: " . $_FILES["file"]["error"][0] . "<br>";
      }   
      else
      {
          // echo "Upload: " . $_FILES["file"]["name"][0] . "<br>";
          // echo "Type: " . $_FILES["file"]["type"][0] . "<br>";
          // echo "Size: " . ($_FILES["file"]["size"][0] / 1024) . " kB<br>";
          // echo "Temp file: " . $_FILES["file"]["tmp_name"][0] . "<br>";

          move_uploaded_file($_FILES["file"]["tmp_name"][0],
          $upload_dir . $_FILES["file"]["name"][0]);

          $song->songFile = $upload_dir . $_FILES["file"]["name"][0];
      }
    }
    else
    {
      echo "Invalid Music file";
    }

    //Text File upload
    $allowedExts = array("txt", "TXT");
    $temp = explode(".", $_FILES["file"]["name"][1]);

    $extension = end($temp);

    if ( ($_FILES["file"]["type"][1] == "text/plain") && in_array($extension, $allowedExts))
    {
      if ($_FILES["file"]["error"][1] > 0)
      {
        echo "Return Code: " . $_FILES["file"]["error"][1] . "<br>";
      }   
      else
      {
          // echo "Upload: " . $_FILES["file"]["name"][1] . "<br>";
          // echo "Type: " . $_FILES["file"]["type"][1] . "<br>";
          // echo "Size: " . ($_FILES["file"]["size"][1] / 1024) . " kB<br>";
          // echo "Temp file: " . $_FILES["file"]["tmp_name"][1] . "<br>";

          move_uploaded_file($_FILES["file"]["tmp_name"][1],
          $upload_dir . $_FILES["file"]["name"][1]);

          $song->lyricsFile = $upload_dir . $_FILES["file"]["name"][1];

          // echo "Stored in: " . "uploads/" . $_FILES["file"]["name"][1];
      }
    }
    else
    {
      echo "Invalid Text file";
    }

    

    $controller->insertSong($song);

    echo "<script type='text/javascript'>location.href='songs.php';</script>";
  }

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


    <div class="row-fluid">
      <div class="span10 offset1">

        <div class="container-fluid">
          <div class="row-fluid">
            <div class="span6 offset3">

              <h2>Add New Song</h2>

              <div class="well" style="text-align:center;padding-top:30px;">
                  <form action="" method="POST" enctype="multipart/form-data">

                    <table class="table table-bordered" id="modalUser"> 
                      <tbody>
                          <tr>
                            <td>Title:</td>
                            <td><input type="text" name="title" value="" required id="title"/></td>
                          </tr>
                         <tr>
                          <td>Artist:</td>
                          <td><input type="text" name="artist" value="" required id="artist"/></td>
                        </tr>
                        <tr>
                          <td>Song File:</td>
                          <td><input type="file" name="file[]" id="songfile" required style="padding-left:45px;" /> </td>
                        </tr>
                        <tr>
                          <td>Lyrics File:</td>
                          <td><input type="file" name="file[]" id="lyricsfile" required style="padding-left:45px;" /></td>
                        </tr>
                        <tr>
                          <td>Difficulty:</td>
                          <td>
                              <select class="combobox" name="difficulty" style="height:30px;width:206px;">
                                <option value="easy">Easy</option>
                                <option value="hard">Hard</option>
                              </select>
                          </td>
                        </tr>


                      </tbody>
                    </table>

                    
                    <button class="btn btn-danger" id="btnSave">Add Song</button>
                    <label class="btn btn-danger" role='button' data-toggle='modal' id="btnBack">Back</label>
                    
                </div>
              </form>

              </div>
            </div>
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

    $('#btnBack').click(function(){
        location.href = "songs.php";
     });

  });

</script>


