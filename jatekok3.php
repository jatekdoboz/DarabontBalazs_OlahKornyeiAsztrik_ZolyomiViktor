<?php


$db = mysqli_connect("localhost","u121374417_jatekdoboz","Szeretemafagyit22!","u121374417_jatekdoboz");
foreach($_POST as $key => $value){
    $$key=$value;
}

if (isset($_POST['upload'])){
    $errors=array();
    $true= true;
    if(empty($_POST['jateknev'])){
        $true= false;
        array_push($errors, "A játéknév mezője üres!"); 
    }
    if(empty($_POST['korhatar'])){
        $true= false;
        array_push($errors, "A korhatár mezője üres!"); 
    }
    if(empty($_POST['varos'])){
        $true= false;
        array_push($errors, "A város mezője üres!"); 
    }
    if(empty($_POST['kategoria'])){
        $true= false;
        array_push($errors, "A kategoria mezője üres!"); 
    }
    if(empty($_POST['leiras'])){
        $true= false;
        array_push($errors, "A leiras mezője üres!"); 
    } 
    if(empty($_POST['datum'])){
        $true= false;
        array_push($errors, "A datum mezője üres!"); 
    } 
    if(empty($_POST['telefon'])){
        $true= false;
        array_push($errors, "A telefon mezője üres!"); 
    } 
    if(empty($_POST['email'])){
        $true= false;
        array_push($errors, "Az email mezője üres!"); 
    } 
     
    $image = $_FILES['image']['name'];
    $tempname = $_FILES["image"]["tmp_name"];
    $folder = "image/".$image;
    $folder2 = "imageKicsi/".$image;
    
    if($true){
        $jateknev= mysqli_real_escape_string($db, $_POST['jateknev']);
        $korhatar= mysqli_real_escape_string($db, $_POST['korhatar']);
        $varos= mysqli_real_escape_string($db, $_POST['varos']);
        $kategoria= mysqli_real_escape_string($db, $_POST['kategoria']);
        $datum= mysqli_real_escape_string($db, $_POST['datum']);
        $telefon= mysqli_real_escape_string($db, $_POST['telefon']);
        $email= mysqli_real_escape_string($db, $_POST['email']);
        $sql="INSERT INTO jatekok3 (jateknev,korhatar,varos,kategoria,leiras,datum,image,telefon, email) VALUES ('$jateknev','$korhatar','$varos','$kategoria','$leiras','$datum','$image','$telefon','$email')";    
    $db->query($sql);
    compressImage($_FILES["image"]["tmp_name"],$folder2,20);
    if (move_uploaded_file($tempname, $folder)){
        $errors = "sikerült feltölteni a képet!";
    }else{
        $errors = "Nem sikerült feltölteni a képet!";
      
    } 
   
header("Location:jatekok3.php");
}
if(!empty($errors)){
    foreach ($errors as $kulcs){
        echo $kulcs."<br\>";            
    }
}
}

$result = mysqli_query($db, "SELECT * FROM jatekok3");



//keptomorites
function compressImage($source, $destination, $quality) {

  $info = getimagesize($source);

  if ($info['mime'] == 'image/jpeg') 
    $image = imagecreatefromjpeg($source);

  elseif ($info['mime'] == 'image/gif') 
    $image = imagecreatefromgif($source);

  elseif ($info['mime'] == 'image/png') 
    $image = imagecreatefrompng($source);

  imagejpeg($image, $destination, $quality);

}

?>
<html lang="hu">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="style4.css">
    
    <title>Játékok</title>
</head>
<body>
  
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
                            <img src="logofeher2.png" alt="" width="100" height="50">
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="jatekok3.php">Játékok</a>
                    </li>
                      <li class="nav-item">
                      <a class="nav-link" href="uzenetkuldes.html">Üzenet küldés</a>
                  </li>
                </ul>
            </div>
       </div>
    </nav>
</style>
</head>
<body>

<div id="content">
  <?php


  
    while ($row = mysqli_fetch_array($result)) {
      
      	
          ?>
          <!-- <div class="" style="max-width: 50px;">
          <div class="card-img-overlay">
          
        
      </div>
    </div> -->

    <div class="row-cols-sm-1 row-cols-md-2 row-cols-lg-4">
    <?php echo "<img height=100 width=100 src='image/".$row['image']."' >";?>
  <div class="card-body">
  <ul class="list-group list-group-flush">
  <h6 class="list-group-item">játekneve: <?php print($row['jateknev']); ?></h6>
    <li class="list-group-item">éves kortól <?php print($row['korhatar']); ?></li>
    <li class="list-group-item">város: <?php print($row['varos']); ?></li>
    <li class="list-group-item">kategoria: <?php print($row['kategoria']); ?></li>
    <li class="list-group-item">dátum: <?php print($row['datum']); ?></li>
    <li class="list-group-item">telefon: <?php print($row['telefon']); ?></li>
    <li class="list-group-item">leirás: <?php print($row['leiras']); ?></li> 
  </ul>
  </div>
</div>
  
    <?php
    }
    ?>
  <script
  src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
  integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
  crossorigin="anonymous"
  ></script>    
  </body>
  </html>