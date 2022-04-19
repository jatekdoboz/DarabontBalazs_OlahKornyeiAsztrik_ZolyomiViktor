<!DOCTYPE html>
<html lang="hu">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
   <link rel="stylesheet" href="style3.css">
    <title>bejelentkeztél</title>
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
                            <img src="logo.png" alt="" width="700" height="50">
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="adminjatekok.php">Játékok</a>
                    </li>
                  </li>
                  <li class="nav-item">
                      <a class="nav-link" href="uzenetkuldes.html">Üzenet küldés</a>
                  </li>
                </ul>
            </div>
       </div>
    </nav>
    <?php
$sql="SELECT * FROM uzenet";
$db = mysqli_connect("localhost","u121374417_jatekdoboz","Szeretemafagyit22!","u121374417_jatekdoboz");
$result=mysqli_query($db, $sql);
mysqli_close($db);
?>
<?php
while ($row = mysqli_fetch_array($result)){
    ?>
    <div class="alert alert-success row-cols-sm-1 row-cols-md-2 row-cols-lg-4" role="alert">
  <h4 class="alert-heading">Neve: <?php print($row['nev']); ?></h4>
  <h4>Email cím: <?php print($row['email']); ?></h4>
  <hr>
  <p class="mb-0"><h4>Üzenete: </h4><?php print($row['uzenet']); ?></p>
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