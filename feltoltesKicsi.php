<?php

 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 
 $ImageData = $_POST['utvonal'];
 
 $ImageName = $_POST['kepnev'];
 
 $ImagePath = "imageKicsi/$ImageName";
 
 $ServerURL = "https://localhost/$ImagePath";

 file_put_contents($ImagePath,base64_decode($ImageData));

 echo $ImageName;
 echo "Sikeres feltoltes";
 }else{
 echo "Nem sikerult";
 }

?>