<?php

require_once './models/Song.php';
 
class ControllerSong
{
 
    private $db;
 
    //put your code here
    // constructor
    function __construct() 
    {
        require_once './application/DB_Connect.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }
 
    // destructor
    function __destruct() 
    {
         
    }
 

    public function insertSong($s) 
    {
        
        $result = mysql_query("INSERT INTO tapthelyrics_songs(title, artist, songFile, lyricsFile, difficulty) VALUES( '$s->title', '$s->artist', '$s->songFile', '$s->lyricsFile', '$s->difficulty' )");
        // check for successful store
        if ($result) 
        {
            return true;
        } 
        else 
        {
            return false;
        }
    }

    public function updateSong($s) 
    {
        $sql = "UPDATE tapthelyrics_songs SET title = '$s->title', artist = '$s->artist', songFile = '$s->songFile', lyricsFile = '$s->lyricsFile', difficulty = '$s->difficulty' WHERE id = $s->id";

        $result = mysql_query($sql);
        // check for successful store
        
        if ($result) 
        {
            return true;
        } 
        else 
        {
            return false;
        }
    }
 
    
    public function getSongs() 
    {
        $result = mysql_query("SELECT id, title, artist, songFile, lyricsFile, difficulty FROM tapthelyrics_songs") or die(mysql_error());
        // check for result 
        $no_of_rows = mysql_num_rows($result);

        if ($no_of_rows > 0) 
        {
            $array = array();

            $ind = 0;
            while ($row=mysql_fetch_row($result))
            {
                $s = new Song();
                $s->id = $row[0];
                $s->title = $row[1];
                $s->artist = $row[2];
                $s->songFile = $row[3];
                $s->lyricsFile = $row[4];
                $s->difficulty = $row[5];
                

                $array[$ind] = $s;
                $ind++;
            }

            return $array;
        } 
        else 
        {
            return null;
        }
    }

    public function getSongById($id) 
    {
        $result = mysql_query("SELECT id, title, artist, songFile, lyricsFile, difficulty FROM tapthelyrics_songs WHERE id=$id") or die(mysql_error());
        // check for result 
        $no_of_rows = mysql_num_rows($result);

        if ($no_of_rows > 0) 
        {
   
            while ($row=mysql_fetch_row($result))
            {
                $s = new Song();
                $s->id = $row[0];
                $s->title = $row[1];
                $s->artist = $row[2];
                $s->songFile = $row[3];
                $s->lyricsFile = $row[4];
                $s->difficulty = $row[5];
                
            }

            return $s;
        } 
        else 
        {
            return null;
        }
    }

}
 
?>