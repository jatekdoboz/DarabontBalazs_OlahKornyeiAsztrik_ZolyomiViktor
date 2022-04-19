<!DOCTYPE html>
<html lang="hu">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
   <link rel="stylesheet" href="style3.css">
    <title>Admin oldal</title>
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
                        <a class="nav-link" href="jatekok3.php">Játékok</a>
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
if( ! empty( $_POST ) )
    {
        if( empty( $_POST["felhasznalonev"] ) || empty( $_POST["jelszo"] ) )
        {
            echo "<p>Minden mező kitöltése kötelező!</p>";
        }
        elseif( $_POST["felhasznalonev"] == "admin" && $_POST["jelszo"] == "admin" )
        {
          echo "<script>location.href='admin.php'</script>";
            echo "<h2>Sikeresen bejelentkeztél!</h2>";
        }
    }
    ?>
    <section class="position-relative py-4 py-xl-5">
    <div class="container">
        <div class="row mb-5">
            <div class="col-md-8 col-xl-6 text-center mx-auto">
                <h2>Belépés</h2>
            </div>
        </div>
        <div class="row d-flex justify-content-center">
            <div class="col-md-6 col-xl-4">
                <div class="card mb-5">
                    <div class="card-body d-flex flex-column align-items-center">
                        <form action="belepes.php" class="text-center" method="post">
                            <div class="mb-3"><input id="felhasznalonev" class="form-control" type="text" name="felhasznalonev" placeholder="felhasználónév" /></div>
                            <div class="mb-3"><input id="jelszo" class="form-control" type="password" name="jelszo" placeholder="jelszó" /></div>
                            <div class="mb-3"><button class="btn btn-primary d-block w-100" type="submit">Belépés</button></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
    <script
src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
crossorigin="anonymous"
></script>  
</body>
</html>