<?php

require_once './models/Authentication.php';
 
class ControllerAuthentication
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

    public function login($username, $password) 
    {
        $qry = "SELECT auth_id, username, password, name FROM tapthelyrics_authentication WHERE username = '$username' AND password='$password' ";
        $result = mysql_query($qry) or die(mysql_error());
        
        // check for result 
        $no_of_rows = mysql_num_rows($result);

        if ($no_of_rows > 0) 
        {
            while ($row=mysql_fetch_row($result))
            {
                $auth = new Authentication();

                $auth->auth_id = $row[0];
                $auth->username = $row[1];
                $auth->password = $row[2];
                $auth->name = $row[3];
            }

            return $auth;
        } 
        else 
        {
            return null;
        }
    }

}
 
?>