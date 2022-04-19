<?php
foreach($_POST as $key => $value){
    $$key=$value;
}

$db = mysqli_connect("localhost","u121374417_jatekdoboz","Szeretemafagyit22!","u121374417_jatekdoboz");
if (isset($_POST['elkuld'])){
    $errors=array();
    $true= true;
    if(empty($_POST['nev'])){
        $true= false;
        array_push($errors, "A név mezője üres!"); 
    }
    if(empty($_POST['email'])){
        $true= false;
        array_push($errors, "A email mezője üres!"); 
    }
    if(empty($_POST['uzenet'])){
        $true= false;
        array_push($errors, "Az üzenet mezője üres!"); 
    }
    $msg= "Hamarosan válaszolunk!";
    if($true){
        $nev= mysqli_real_escape_string($db, $_POST['nev']);
        $email= mysqli_real_escape_string($db, $_POST['email']);
        $uzenet= mysqli_real_escape_string($db, $_POST['uzenet']);
        $sql="INSERT INTO uzenet (nev,email,uzenet) VALUES ('$nev','$email','$uzenet')";    
        $db->query($sql);
        echo $msg;


        
       
    }
    }
    $db->close();
    if(!empty($errors)){
        foreach ($errors as $key){
            echo $key."<br\>";            
        }
    }

?>
<!DOCTYPE html>
<html lang="hu">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="style3.css">
    <title>Üzenet küldés</title>
</head>
<header>
    <nav class="navbar navbar-expand-lg 12 navbar-light 6 bg-light 6">
        <div 
            class="container-fluid">
            <a class="navbar-brand" href="#"></a>
            <button
                class="navbar-toggler"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navbarNav"
                aria-controls="navbarNav"
                aria-expanded="false"
                aria-label="Toggle navigation"
            >
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="index.html">
                            <img src="logo.png" alt="" width="700" height="50">
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="jatekok3.php">Játékok</a>
                    </li>
                </ul>
            </div>
       </div>
    </nav>
<body>
<script
src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
crossorigin="anonymous"
></script>   
</body>
</html>