<?php 
    $id=$_REQUEST['id'];   
    $sql="DELETE FROM jatekok3 WHERE id=$id";
    $db = mysqli_connect("localhost","u121374417_jatekdoboz","Szeretemafagyit22!","u121374417_jatekdoboz");
    mysqli_query($db, $sql);
    mysqli_close($db);
    header("Location:adminjatekok.php");

?>