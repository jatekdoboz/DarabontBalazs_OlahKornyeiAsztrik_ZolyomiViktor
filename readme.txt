Felhasznált / ** Nem használt **
 
Github

domain: http://jatekdoboz.xyz
 
jatekhaboru6.zip - a JátékDoboz felhasználói alkalmazás projektje
 
jatekhaboru6.apk - felhasználói applikáció

jatekhaboru6 mappa - felhasználói projekt

 
Jatekdoboz.zip - a JátékDoboz admin alkalmazás projektje
 
JatekAdmin.apk - admin applikáció

JatekDoboz mappa - admin projekt
 
 
 
A szerveren található fájlok:
 
admin.php - itt megy végbe az ellenőrzés. Az adminok helyesen írták-e be a felhasználó nevet és a jelszót - a beléptetés, és az üzenetek is itt jelennek meg.
 
adminjatekok.php - csak adminok érhetik el az oldalt. Lehetőség van törölni az adományokat az adatbázisból és a honlapról.
 
belepes.php - itt tudnak belépni az adminok felhasználó névvel és jelszóval ami admin.php.-ba van megadva
 
index.html - itt lehet adományozni a játékokat, ez a fő oldal.
 
jatekok3.php - ebben a file-ben megy végbe az adományozás. Itt kerülnek fel az adatbázisba az adatok. Ellenőrzi a mezők helyes kitöltését. Kilistázza a játékokat a honlapra.
 
style3.css - ez a file ellátja dizájnnal a következő weblapokat (uzenet.php, uzenetkuldes.html, index.html, admin.php. belepes.php)
 
style4.css - ez a file ellátja dizájnnal a következő weblapokat (jatekok3.php, adminjatekok.php)
 
toresz.php - ez a file biztosítja az adományok törlését, az adatbázisból
 
uzenet.php - az üzenetek bekerülnek az adatbázisba, és a felhasználónak automatikusan jelzést küld
 
uzenetkuldes.html - biztosítja az adminokkal a kontaktot
 
feltoltes3.php - az image mappába tölti fel a http-n keresztul kapott base64 enkodolású képet
 
feltoltesKicsi.php - ugyan azt teszi mint a feltöltés3.php, csak mindezt az imageKicsi mappába



 
Az alkalmazásban megtalálható fájlok:

 
Hirek.java  -  Hírek kilistázása
 
Kepfeltoltes.java - Játék feltöltése
 
NotificationsFragment.java
 
** Adatok.java
 
** Bejelent.java
 
BevittAdat.java - Hirek lista létrehoyása custom layoutra
 
Fomenu.java - fragmentek activityje
 
** HirekThread.java
 
** HirKilistazas.java
 
Jatek.java - maga a kilistayott jatek objektum ami a recyclerviewba kerül (itt is vannak kilistázva)
 
JatekListaAdapter.java - az egyedi lista layouthoz
 
** Jatekok2.java
 
** Jatekok3.java
 
** Jatekok4.java
 
Jatekok5.java - játékok kilistázása, szűrése, törlése, kontakt
 
** Kilistazas.java
 
** ListaAdapter.java
 
ListaAdapter2.java  - az egyedi hírek lista feltöltésére
 
MainActivity.java - logo megjelenítése, fomeni activity hívása
 
** Oldal.java
 
** OldalAdapter.java
 
RecyclerItemClickListener.java - a recycler lista OnItemClikListener-je
 
** Regisztral.java
 
SqlKapcsolat.java - sql kapcsolat létrehozása, lekérdezésre és a kapcsolat ellenőrzésére használtuk
 
** Szunet.java
 
** tesztactivity.java
 
** Valaszto.java
 
** Weboldal.java
 
DashboardFragment.java (jatekdoboz - JatekAdmin app) - ez a jatekhaboru6  
   	Hirek.java megfelelője, több funkcióval, hír 
   	hozzáadása, szerkesztése
 
Layoutok
 
** activity_bejelent.xml
 
activity_fomenu.xml - (Fomenu.java) layoutja, igayából a bottom navbar 
      stílusáthivatott megszabni
 
** activity.main.xml 
 
** activity_regisztral.xml
 
** activity_tesztactivity.xml
 
** activity_valaszto.xml
 
** activity_weboldal.xml
 
felugroablak.xml  - (Jatekok5.java) Recyclerview OnclickListenerjére ezt a 
  	layoutot veszi fel a dialogbuilder 
 
** felugroablak2.xml
 
felugrovisszavonas.xml - (Jatekok5.java) -> felugroablak.xml (visszavonás 
  	gomb) -> dialogbuilder (felugrovisszavonas.xml)
 
fragment_dashboard.xml - (Hirek.java) fragment layoutja
fragment_home.xml - (Kepfeltoltes.java) layoutja
 
** fragment_jatekok2.xml
 
** fragment_jatekok3.xml
 
** fragment_notifications.xml
 
** jatekok4_fragment.xml
 
jatekok5_fragment.xml - (Jatekok5.java) layoutja
 
lenyilomenu.xml—
    	            }	
lenyilomenu2.xml—>>>    A (Kepfeltoltes.java) lenyilólistájának és a 
    	     }         Jatekok5.java szűrőlistájának layoutjai
lenyilomenu3.xml—
 
lista_layout.xml - a (Jatekok5.java) recyclerview-jának layoutja
 
** lista_layout2.xml
 
** nincskapcs.xml
 
** nyitoanimacio.xml - Az indítóképernyő, logoval (MainActivity.java)
 
** oldalelemek.xml
 
** oldalkinezet.xml
 
szuromenu.xml - szűrőelemek layoutja
 
uzenofal_layout.xml - a (Hirek.java) ListView-jának layoutja
 
Menu
 
bottom_nav_menu.xml - a bottom nav menü tartalma (cime, ikonja, sorban valo 
  	elhelyezkedése)
 
popup.xml
 
 
Navigation
 
mobile_navigation.xml - fragmentek osszecsoportosítása egy navigatin layoutba
 
Values
 
színek és témák 
 

 


