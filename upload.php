<?php


	if( !empty( $_FILES["file"]["name"][0] ) && !empty( $_FILES["file"]["name"][1] ) )
	{
		$allowedExts = array("mpeg", "mp3", "wav", "MP3", "WAV");
		$temp = explode(".", $_FILES["file"]["name"][0]);

		$extension = end($temp);

		if ( ($_FILES["file"]["type"][0] == "audio/mpeg") && in_array($extension, $allowedExts))
		{
			if ($_FILES["file"]["error"][0] > 0)
			{
				echo "Return Code: " . $_FILES["file"]["error"][0] . "<br>";
			}		
			else
			{
			    echo "Upload: " . $_FILES["file"]["name"][0] . "<br>";
			    echo "Type: " . $_FILES["file"]["type"][0] . "<br>";
			    echo "Size: " . ($_FILES["file"]["size"][0] / 1024) . " kB<br>";
			    echo "Temp file: " . $_FILES["file"]["tmp_name"][0] . "<br>";

			    if (file_exists("upload/" . $_FILES["file"]["name"][0]))
				{
					echo $_FILES["file"]["name"][0] . " already exists. ";
				}
				else
				{
					move_uploaded_file($_FILES["file"]["tmp_name"][0],
					"uploads/" . $_FILES["file"]["name"][0]);
					echo "Stored in: " . "uploads/" . $_FILES["file"]["name"][0];
				}
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
			    echo "Upload: " . $_FILES["file"]["name"][1] . "<br>";
			    echo "Type: " . $_FILES["file"]["type"][1] . "<br>";
			    echo "Size: " . ($_FILES["file"]["size"][1] / 1024) . " kB<br>";
			    echo "Temp file: " . $_FILES["file"]["tmp_name"][1] . "<br>";

			    if (file_exists("upload/" . $_FILES["file"]["name"][1]))
				{
					echo $_FILES["file"]["name"][1] . " already exists. ";
				}
				else
				{
					move_uploaded_file($_FILES["file"]["tmp_name"][1],
					"uploads/" . $_FILES["file"]["name"][1]);
					echo "Stored in: " . "uploads/" . $_FILES["file"]["name"][1];
				}
			}
		}
		else
		{
			echo "Invalid Text file";
		}
	}
	

?> 



<html>
<body>

<form action="" method="post"
enctype="multipart/form-data">
<label for="file">Filename:</label>
<input type="file" name="file[]" id="file1"><br /><br />
<input type="file" name="file[]" id="file1"><br />


<input type="submit" name="submit" value="Submit">
</form>

</body>
</html> 